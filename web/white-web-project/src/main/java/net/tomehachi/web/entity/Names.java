package net.tomehachi.web.entity;

import javax.annotation.Generated;
import net.tomehachi.web.entity.AuthLogNames._AuthLogNames;
import net.tomehachi.web.entity.ChangePasswordKeyNames._ChangePasswordKeyNames;
import net.tomehachi.web.entity.UserAuthNames._UserAuthNames;
import net.tomehachi.web.entity.UserRoleNames._UserRoleNames;

/**
 * 名前クラスの集約です。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.NamesAggregateModelFactoryImpl"}, date = "2016/02/29 22:52:07")
public class Names {

    /**
     * {@link AuthLog}の名前クラスを返します。
     * 
     * @return AuthLogの名前クラス
     */
    public static _AuthLogNames authLog() {
        return new _AuthLogNames();
    }

    /**
     * {@link ChangePasswordKey}の名前クラスを返します。
     * 
     * @return ChangePasswordKeyの名前クラス
     */
    public static _ChangePasswordKeyNames changePasswordKey() {
        return new _ChangePasswordKeyNames();
    }

    /**
     * {@link UserAuth}の名前クラスを返します。
     * 
     * @return UserAuthの名前クラス
     */
    public static _UserAuthNames userAuth() {
        return new _UserAuthNames();
    }

    /**
     * {@link UserRole}の名前クラスを返します。
     * 
     * @return UserRoleの名前クラス
     */
    public static _UserRoleNames userRole() {
        return new _UserRoleNames();
    }
}