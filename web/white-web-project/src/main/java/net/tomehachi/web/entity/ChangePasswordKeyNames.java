package net.tomehachi.web.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import net.tomehachi.web.entity.UserAuthNames._UserAuthNames;
import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link ChangePasswordKey}のプロパティ名の集合です。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.NamesModelFactoryImpl"}, date = "2016/02/29 22:52:07")
public class ChangePasswordKeyNames {

    /**
     * userIdのプロパティ名を返します。
     * 
     * @return userIdのプロパティ名
     */
    public static PropertyName<Integer> userId() {
        return new PropertyName<Integer>("userId");
    }

    /**
     * changePasswordKeyのプロパティ名を返します。
     * 
     * @return changePasswordKeyのプロパティ名
     */
    public static PropertyName<String> changePasswordKey() {
        return new PropertyName<String>("changePasswordKey");
    }

    /**
     * doneのプロパティ名を返します。
     * 
     * @return doneのプロパティ名
     */
    public static PropertyName<Boolean> done() {
        return new PropertyName<Boolean>("done");
    }

    /**
     * expiredAtのプロパティ名を返します。
     * 
     * @return expiredAtのプロパティ名
     */
    public static PropertyName<Timestamp> expiredAt() {
        return new PropertyName<Timestamp>("expiredAt");
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
     * userAuthのプロパティ名を返します。
     * 
     * @return userAuthのプロパティ名
     */
    public static _UserAuthNames userAuth() {
        return new _UserAuthNames("userAuth");
    }

    /**
     * @author S2JDBC-Gen
     */
    public static class _ChangePasswordKeyNames extends PropertyName<ChangePasswordKey> {

        /**
         * インスタンスを構築します。
         */
        public _ChangePasswordKeyNames() {
        }

        /**
         * インスタンスを構築します。
         * 
         * @param name
         *            名前
         */
        public _ChangePasswordKeyNames(final String name) {
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
        public _ChangePasswordKeyNames(final PropertyName<?> parent, final String name) {
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
         * changePasswordKeyのプロパティ名を返します。
         *
         * @return changePasswordKeyのプロパティ名
         */
        public PropertyName<String> changePasswordKey() {
            return new PropertyName<String>(this, "changePasswordKey");
        }

        /**
         * doneのプロパティ名を返します。
         *
         * @return doneのプロパティ名
         */
        public PropertyName<Boolean> done() {
            return new PropertyName<Boolean>(this, "done");
        }

        /**
         * expiredAtのプロパティ名を返します。
         *
         * @return expiredAtのプロパティ名
         */
        public PropertyName<Timestamp> expiredAt() {
            return new PropertyName<Timestamp>(this, "expiredAt");
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
         * userAuthのプロパティ名を返します。
         * 
         * @return userAuthのプロパティ名
         */
        public _UserAuthNames userAuth() {
            return new _UserAuthNames(this, "userAuth");
        }
    }
}