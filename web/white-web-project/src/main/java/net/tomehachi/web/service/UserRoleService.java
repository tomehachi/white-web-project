package net.tomehachi.web.service;

import static net.tomehachi.web.entity.UserRoleNames.*;
import static org.seasar.extension.jdbc.operation.Operations.*;

import java.util.List;

import javax.annotation.Generated;

import net.tomehachi.web.entity.UserRole;

/**
 * {@link UserRole}のサービスクラスです。
 *
 */
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl"}, date = "2016/02/10 17:51:20")
public class UserRoleService extends AbstractService<UserRole> {

    /**
     * 識別子でエンティティを検索します。
     *
     * @param userId
     *            識別子
     * @param role
     *            識別子
     * @return エンティティ
     */
    public UserRole findById(Integer userId, String role) {
        return select().id(userId, role).getSingleResult();
    }

    /**
     * 識別子の昇順ですべてのエンティティを検索します。
     *
     * @return エンティティのリスト
     */
    public List<UserRole> findAllOrderById() {
        return select().orderBy(asc(userId()), asc(role())).getResultList();
    }
}