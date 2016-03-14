package net.tomehachi.web.service;

import static net.tomehachi.web.entity.UserAuthNames.*;
import static org.seasar.extension.jdbc.operation.Operations.*;

import java.util.List;

import javax.annotation.Generated;

import net.arnx.jsonic.JSON;
import net.tomehachi.web.entity.UserAuth;

import org.seasar.extension.jdbc.where.SimpleWhere;
import org.seasar.framework.beans.util.BeanMap;

/**
 * {@link UserAuth}のサービスクラスです。
 *
 */
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl"}, date = "2016/02/06 1:17:27")
public class UserAuthService extends AbstractService<UserAuth> {

    /**
     * 識別子でエンティティを検索します。
     *
     * @param userId
     *            識別子
     * @return エンティティ
     */
    public UserAuth findById(Integer userId) {
        return select().id(userId).getSingleResult();
    }

    /**
     * 識別子の昇順ですべてのエンティティを検索します。
     *
     * @return エンティティのリスト
     */
    public List<UserAuth> findAllOrderById() {
        return select().orderBy(asc(userId())).getResultList();
    }

    public UserAuth findByEmail(String email) {
        return select()
                .leftOuterJoin("changePasswordKey")
                .where((new SimpleWhere()).eq("email", email))
                .getSingleResult();
    }

    public String getUserListJson() {
        List<UserAuth> userAuthList =
                jdbcManager
                .from(UserAuth.class)
                .leftOuterJoin("userRoleList")
                .orderBy(desc(userId()))
                .getResultList();

        // パスワードは削除
        for(UserAuth userAuth : userAuthList) {
            userAuth.password = null;
        }
        return JSON.encode(userAuthList);
    }

    /**
     * 指定された email のユーザが既に存在するかどうかを返す.<br>
     *
     * @param email メールアドレス
     * @return 存在する true
     */
    public boolean userExists(String email) {
        BeanMap conditions = new BeanMap();
        conditions.put("email", email);
        List<UserAuth> userAuthList = findByCondition(conditions);
        if(userAuthList != null && userAuthList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
