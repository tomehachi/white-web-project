package net.tomehachi.web.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.struts.util.RequestUtil;

/**
 * ユーザの一挙手一投足を記録することを目的にしたロギングインターセプタークラス.<br>
 * 客先の要件に基づいて修正下さい.<br>
 * JdbcManagerやサービスクラスをインジェクションできます.<br>
 *
 * @author tomehachi
 */
public class ActionLogInterceptor extends AbstractInterceptor {

    private Logger logger = Logger.getLogger(this.getClass());

    /*
     * (非 Javadoc)
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = null;
        try {
            /*
             * デフォルトではLog4Jで, アクセスされたアクションクラス, アクションメソッド
             * またはバリデーションメソッド, リクエストパラメータをタブ区切りで出力.
             */
            StringBuffer log = new StringBuffer();
            log.append(invocation.getClass().getName().split("\\$\\$")[0])
            .append("\t")
            .append(invocation.getMethod().getName())
            .append("\t")
            .append(RequestUtil.getRequest().getParameterMap());

            logger.info(log);

            result = invocation.proceed();

        } catch (Exception e) {
            logger.error("アクションから例外が検出されました.", e);
            throw e;
        }
        return result;
    }

}
