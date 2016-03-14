package net.tomehachi.web.service;

import static net.tomehachi.web.entity.ChangePasswordKeyNames.*;
import static org.seasar.extension.jdbc.operation.Operations.*;

import java.util.List;

import javax.annotation.Generated;

import net.tomehachi.web.entity.ChangePasswordKey;
import net.tomehachi.web.form.PasswdAuthForm;
import net.tomehachi.web.util.SecurityUtil;

import org.seasar.extension.jdbc.where.SimpleWhere;

/**
 * {@link ChangePasswordKey}のサービスクラスです。
 *
 */
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl"}, date = "2016/02/27 4:58:00")
public class ChangePasswordKeyService extends AbstractService<ChangePasswordKey> {

    /**
     * 識別子でエンティティを検索します。
     *
     * @param userId
     *            識別子
     * @return エンティティ
     */
    public ChangePasswordKey findById(Integer userId) {
        return select().id(userId).getSingleResult();
    }

    /**
     * 識別子の昇順ですべてのエンティティを検索します。
     *
     * @return エンティティのリスト
     */
    public List<ChangePasswordKey> findAllOrderById() {
        return select().orderBy(asc(userId())).getResultList();
    }

    /**
     * 認証キーから対象のユーザを検索する.<br>
     *
     * @param passwdForm
     * @return
     */
    public ChangePasswordKey findUserByKey(PasswdAuthForm passwdForm) {
        return select().where(
                new SimpleWhere()
                .eq("changePasswordKey", SecurityUtil.encode(passwdForm.key))
        ).getSingleResult();
    }
}
