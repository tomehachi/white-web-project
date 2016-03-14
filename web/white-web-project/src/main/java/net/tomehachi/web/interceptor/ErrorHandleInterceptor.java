package net.tomehachi.web.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;

/**
 * 認証用インターセプタークラス.<br>
 *
 * @author nakaoka.kentarou
 */
public class ErrorHandleInterceptor extends AbstractInterceptor {

    private Logger logger = Logger.getLogger(this.getClass());

    /*
     * (非 Javadoc)
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = null;
        try {
            result = invocation.proceed();
        } catch (Exception e) {
            logger.error("アクションから例外が検出されました.", e);
            throw e;
        }
        return result;
    }

}
