package net.tomehachi.web.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import net.tomehachi.web.entity.ChangePasswordKeyNames._ChangePasswordKeyNames;
import net.tomehachi.web.entity.UserProfileNames._UserProfileNames;
import net.tomehachi.web.entity.UserRoleNames._UserRoleNames;
import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link UserAuth}のプロパティ名の集合です。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.NamesModelFactoryImpl"}, date = "2016/04/30 12:57:22")
public class UserAuthNames {

    /**
     * userIdのプロパティ名を返します。
     * 
     * @return userIdのプロパティ名
     */
    public static PropertyName<Integer> userId() {
        return new PropertyName<Integer>("userId");
    }

    /**
     * emailのプロパティ名を返します。
     * 
     * @return emailのプロパティ名
     */
    public static PropertyName<String> email() {
        return new PropertyName<String>("email");
    }

    /**
     * passwordのプロパティ名を返します。
     * 
     * @return passwordのプロパティ名
     */
    public static PropertyName<String> password() {
        return new PropertyName<String>("password");
    }

    /**
     * createdAtのプロパティ名を返します。
     * 
     * @return createdAtのプロパティ名
     */
    public static PropertyName<Timestamp> createdAt() {
        return new PropertyName<Timestamp>("createdAt");
    }

    /**
     * updatedAtのプロパティ名を返します。
     * 
     * @return updatedAtのプロパティ名
     */
    public static PropertyName<Timestamp> updatedAt() {
        return new PropertyName<Timestamp>("updatedAt");
    }

    /**
     * changePasswordKeyのプロパティ名を返します。
     * 
     * @return changePasswordKeyのプロパティ名
     */
    public static _ChangePasswordKeyNames changePasswordKey() {
        return new _ChangePasswordKeyNames("changePasswordKey");
    }

    /**
     * userProfileのプロパティ名を返します。
     * 
     * @return userProfileのプロパティ名
     */
    public static _UserProfileNames userProfile() {
        return new _UserProfileNames("userProfile");
    }

    /**
     * userRoleListのプロパティ名を返します。
     * 
     * @return userRoleListのプロパティ名
     */
    public static _UserRoleNames userRoleList() {
        return new _UserRoleNames("userRoleList");
    }

    /**
     * @author S2JDBC-Gen
     */
    public static class _UserAuthNames extends PropertyName<UserAuth> {

        /**
         * インスタンスを構築します。
         */
        public _UserAuthNames() {
        }

        /**
         * インスタンスを構築します。
         * 
         * @param name
         *            名前
         */
        public _UserAuthNames(final String name) {
            super(name);
        }

        /**
         * インスタンスを構築します。
         * 
         * @param parent
         *            親
         * @param name
         *            名前
         */
        public _UserAuthNames(final PropertyName<?> parent, final String name) {
            super(parent, name);
        }

        /**
         * userIdのプロパティ名を返します。
         *
         * @return userIdのプロパティ名
         */
        public PropertyName<Integer> userId() {
            return new PropertyName<Integer>(this, "userId");
        }

        /**
         * emailのプロパティ名を返します。
         *
         * @return emailのプロパティ名
         */
        public PropertyName<String> email() {
            return new PropertyName<String>(this, "email");
        }

        /**
         * passwordのプロパティ名を返します。
         *
         * @return passwordのプロパティ名
         */
        public PropertyName<String> password() {
            return new PropertyName<String>(this, "password");
        }

        /**
         * createdAtのプロパティ名を返します。
         *
         * @return createdAtのプロパティ名
         */
        public PropertyName<Timestamp> createdAt() {
            return new PropertyName<Timestamp>(this, "createdAt");
        }

        /**
         * updatedAtのプロパティ名を返します。
         *
         * @return updatedAtのプロパティ名
         */
        public PropertyName<Timestamp> updatedAt() {
            return new PropertyName<Timestamp>(this, "updatedAt");
        }

        /**
         * changePasswordKeyのプロパティ名を返します。
         * 
         * @return changePasswordKeyのプロパティ名
         */
        public _ChangePasswordKeyNames changePasswordKey() {
            return new _ChangePasswordKeyNames(this, "changePasswordKey");
        }

        /**
         * userProfileのプロパティ名を返します。
         * 
         * @return userProfileのプロパティ名
         */
        public _UserProfileNames userProfile() {
            return new _UserProfileNames(this, "userProfile");
        }

        /**
         * userRoleListのプロパティ名を返します。
         * 
         * @return userRoleListのプロパティ名
         */
        public _UserRoleNames userRoleList() {
            return new _UserRoleNames(this, "userRoleList");
        }
    }
}