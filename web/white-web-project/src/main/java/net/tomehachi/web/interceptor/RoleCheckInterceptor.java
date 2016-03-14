package net.tomehachi.web.interceptor;

import javax.annotation.Resource;

import net.tomehachi.web.annotation.RoleLimited;
import net.tomehachi.web.dto.UserDataDto;
import net.tomehachi.web.entity.UserRole;
import net.tomehachi.web.service.UserRoleService;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.struts.annotation.Execute;

/**
 * 認証用インターセプタークラス.<br>
 *
 * @author nakaoka.kentarou
 */
public class RoleCheckInterceptor extends AbstractInterceptor {

    @Resource
    protected UserRoleService userRoleService;

    @Resource
    protected UserDataDto userDataDto;

    /*
     * (非 Javadoc)
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result;

        // Execute と RoleLimited アノテーションがあればロールチェックを実行
        if(hasExecuteAnnotation(invocation) && hasRoleLimitedAnnotation(invocation)) {

            // ロール
            String role = invocation.getMethod().getAnnotation(RoleLimited.class).role().toString();
            UserRole userRole = userRoleService.findById(userDataDto.userId, role);

            // このユーザが指定ロールを保持している場合は、そのままinvocate
            if(userRole != null) {
                result = invocation.proceed();

            } else {
                result = "/error/notallowed";
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

    private boolean hasRoleLimitedAnnotation(MethodInvocation invocation) {
        // PublicMethodアノテーションが付いているかどうか
        return invocation.getMethod().isAnnotationPresent(RoleLimited.class);
    }
}
