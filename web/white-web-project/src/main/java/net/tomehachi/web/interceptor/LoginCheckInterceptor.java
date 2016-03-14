package net.tomehachi.web.interceptor;

import javax.annotation.Resource;

import net.tomehachi.web.annotation.PublicMethod;
import net.tomehachi.web.dto.UserDataDto;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.RequestUtil;

/**
 * 認証用インターセプタークラス.<br>
 *
 * @author nakaoka.kentarou
 */
public class LoginCheckInterceptor extends AbstractInterceptor {

    /**
     * セッションに保存されたユーザ情報.<br>
     */
    @Resource
    protected UserDataDto userDataDto;

    /*
     * (非 Javadoc)
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result;

        // Execute が付いていて PublicMethod アノテーションがなければ認証チェックを実行
        if(hasExecuteAnnotation(invocation) && !hasPublicAnnotation(invocation)) {

            // ユーザ情報がセッションに無ければ認証画面へ.
            if(hasUserData()) {
                result = invocation.proceed();

            } else {
                String url = RequestUtil.getRequest().getRequestURL().toString();
                String query = RequestUtil.getRequest().getQueryString();

                // 閲覧しようとしていたURLを記録する.
                userDataDto.requestedUrl = (url == null || url.length() == 0 ? "" : url)
                        + (query == null || query.length() == 0 ? "" : "?" + query);

                result = "/auth?redirect=true";
            }

        } else {
            result = invocation.proceed();
        }
        return result;
    }

    /**
     * Executeアノテーションが付いているかどうかを返す.<br>
     *
     * @param invocation MethodInvocation
     * @return アノテーションが付いているかどうか
     */
    private boolean hasExecuteAnnotation(MethodInvocation invocation) {
        // Executeアノテーションが付いているかどうか
        return invocation.getMethod().isAnnotationPresent(Execute.class);
    }

    private boolean hasPublicAnnotation(MethodInvocation invocation) {
        // PublicMethodアノテーションが付いているかどうか
        return invocation.getMethod().isAnnotationPresent(PublicMethod.class);
    }

    /**
     * ユーザ情報がセッションに格納されているかどうかを返す.<br>
     *
     * @return
     */
    private boolean hasUserData() {
        return (userDataDto != null && userDataDto.userId != null);
    }
}
