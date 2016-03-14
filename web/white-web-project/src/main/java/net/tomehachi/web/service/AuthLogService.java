package net.tomehachi.web.service;

import static net.tomehachi.web.entity.AuthLogNames.*;
import static org.seasar.extension.jdbc.operation.Operations.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Generated;

import net.tomehachi.web.entity.AuthLog;
import net.tomehachi.web.util.AppUtil;

import org.seasar.extension.jdbc.where.SimpleWhere;
import org.seasar.struts.util.RequestUtil;

/**
 * {@link AuthLog}のサービスクラスです。
 *
 */
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl"}, date = "2016/02/06 1:17:27")
public class AuthLogService extends AbstractService<AuthLog> {

    /**
     * 識別子でエンティティを検索します。
     *
     * @param authLogId
     *            識別子
     * @return エンティティ
     */
    public AuthLog findById(Integer authLogId) {
        return select().id(authLogId).getSingleResult();
    }

    /**
     * 識別子の昇順ですべてのエンティティを検索します。
     *
     * @return エンティティのリスト
     */
    public List<AuthLog> findAllOrderById() {
        return select().orderBy(asc(authLogId())).getResultList();
    }

    /**
     * 特定IPアドレスの認証ログを直近の指定時刻に遡って取得し、返す.<br>
     *
     * @return エンティティのリスト
     */
    public List<AuthLog> findRecentFailureByIp(Timestamp time) {
        return select()
                .where(and(
                        new SimpleWhere().eq("ipAddress", AppUtil.getHostName()),
                        new SimpleWhere().eq("result", false),
                        new SimpleWhere().gt("createdAt", time))
                ).getResultList();
    }

    /**
     * 認証失敗を履歴に書き込む.<br>
     *
     * @param userId ユーザID
     * @param password パスワード
     */
    public void writeNewLog(Integer userId, boolean result) {
        AuthLog authLog = new AuthLog();
        authLog.userId = userId;
        authLog.result = result;
        authLog.ipAddress = RequestUtil.getRequest().getRemoteAddr();
        authLog.host = AppUtil.getHostName();
        insert(authLog);
    }

    /**
     * 同一IPアドレスからの認証試行回数が多すぎるかどうかを返す.<br>
     *
     * @param message
     * @return 多すぎる=true
     */
    public boolean isTooManyAuthFailure() {
        //TODO 設定ファイル化
        List<AuthLog> failureLogs = findRecentFailureByIp(
                new Timestamp((new Date()).getTime() - 600000));
        return failureLogs.size() > 10;
    }

}
