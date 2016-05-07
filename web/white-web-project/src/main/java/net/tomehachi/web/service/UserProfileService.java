package net.tomehachi.web.service;

import java.util.List;
import javax.annotation.Generated;
import net.tomehachi.web.entity.UserProfile;

import static net.tomehachi.web.entity.UserProfileNames.*;
import static org.seasar.extension.jdbc.operation.Operations.*;

/**
 * {@link UserProfile}のサービスクラスです。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl"}, date = "2016/04/30 12:56:44")
public class UserProfileService extends AbstractService<UserProfile> {

    /**
     * 識別子でエンティティを検索します。
     * 
     * @param userId
     *            識別子
     * @return エンティティ
     */
    public UserProfile findById(Integer userId) {
        return select().id(userId).getSingleResult();
    }

    /**
     * 識別子の昇順ですべてのエンティティを検索します。
     * 
     * @return エンティティのリスト
     */
    public List<UserProfile> findAllOrderById() {
        return select().orderBy(asc(userId())).getResultList();
    }
}