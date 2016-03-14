package net.tomehachi.web.processor;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tomehachi.web.annotation.PublicMethod;
import net.tomehachi.web.annotation.RoleLimited;
import net.tomehachi.web.annotation.TemporaryMethod;
import net.tomehachi.web.dto.UserDataDto;
import net.tomehachi.web.service.UserRoleService;
import net.tomehachi.web.util.AppException;
import net.tomehachi.web.util.AppUtil;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.struts.action.S2RequestProcessor;
import org.seasar.struts.util.S2ExecuteConfigUtil;

public class TomehachiRequestProcessor extends S2RequestProcessor {

    private Logger logger = Logger.getLogger(this.getClass());

    /*
     * (非 Javadoc)
     * @see org.seasar.struts.action.S2RequestProcessor#processRoles(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.ActionMapping)
     */
    @Override
    protected boolean processRoles(HttpServletRequest req, HttpServletResponse res, ActionMapping map)
            throws IOException, ServletException {

        Object userDataDtoObject = SingletonS2Container.getComponent(UserDataDto.class);
        Object userRoleServiceObject = SingletonS2Container.getComponent(UserRoleService.class);

        // PublicMethodアノテーションが付いているならば、無条件に実行.
        if(findAnnotation(PublicMethod.class) != null) {
            return super.processRoles(req, res, map);
        }

        try {
            // TemporaryMethodアノテーションが付いているならば、isTemporaryのときのみ実行
            if(findAnnotation(TemporaryMethod.class) != null) {
                if(isSignedInTemporary(userDataDtoObject) || isSignedIn(userDataDtoObject)) {
                    return super.processRoles(req, res, map);

                } else {
                    res.sendRedirect(req.getContextPath() + "/signIn");
                    return false;
                }
            }

        } catch (AppException e) {
            logger.error(e.appMessage, e);
            return false;
        }

        try {
            // PublicMethodアノテーションが付いていないので認証チェック.
            if(!isSignedIn(userDataDtoObject)) {
                String url = req.getRequestURL().toString();
                String query = req.getQueryString();

                // 閲覧しようとしていたURLを記録する.
                AppUtil.setObjectField(userDataDtoObject, "requestedUrl",
                        (url == null || url.length() == 0 ? "" : url) +
                        (query == null || query.length() == 0 ? "" : "?" + query)
                );

                // 認証画面にリダイレクトする.
                res.sendRedirect(req.getContextPath() + "/signIn");
                return false;
            }

            Annotation roleAnnotation = findAnnotation(RoleLimited.class);
            if(roleAnnotation != null) {
                // ロール
                Object roleObject = AppUtil.invoke(roleAnnotation, "role", null, null);
                Object userRole = AppUtil.invoke(
                        userRoleServiceObject,
                        "findById",
                        new Object[] {
                                AppUtil.getObjectField(userDataDtoObject, "userId"),
                                roleObject.toString()
                        },
                        new Class<?>[] {
                                Integer.class,
                                String.class
                        }
                );

                if(userRole != null) {
                    // このユーザが指定ロールを保持している場合は、そのままActionに処理を移行.
                    return  super.processRoles(req, res, map);

                } else {
                    // 指定ロールを保持していない場合は、notallowedページにリダイレクト.
                    res.sendRedirect(req.getContextPath() + "/error/notallowed");
                    return false;
                }

            } else {
                return super.processRoles(req, res, map);
            }

        } catch (AppException e) {
            logger.error(e.appMessage, e);
            return false;
        }

    }

    /**
     * Executeアノテーションが付いているかどうかを返す.<br>
     *
     * @param invocation MethodInvocation
     * @return アノテーションが付いているかどうか
     */
    private Annotation findAnnotation(Class<?> clazz) {
        // 対象メソッドのアノテーションを取得.
        Annotation[] annotations = S2ExecuteConfigUtil.getExecuteConfig().getMethod().getAnnotations();
        for(Annotation annotation : annotations) {
            if(annotation.annotationType().getName().equals(clazz.getName())) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * サインイン済みのユーザかどうかを返す.<br>
     *
     * @return
     * @throws AppException
     */
    private boolean isSignedIn(Object obj) throws AppException {
        if(obj == null) {
            return false;
        }
        if(AppUtil.getObjectField(obj, "userId") == null) {
            return false;
        }
        if(AppUtil.getObjectField(obj, "isTemporary").equals(Boolean.FALSE)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSignedInTemporary(Object obj) throws AppException {
        if(obj == null) {
            return false;
        }
        if(AppUtil.getObjectField(obj, "userId") == null) {
            return false;
        }
        if(AppUtil.getObjectField(obj, "isTemporary").equals(Boolean.TRUE)) {
            return true;
        } else {
            return false;
        }
    }
}
