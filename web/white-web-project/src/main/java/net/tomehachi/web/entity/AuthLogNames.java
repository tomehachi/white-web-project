package net.tomehachi.web.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link AuthLog}のプロパティ名の集合です。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.NamesModelFactoryImpl"}, date = "2016/04/30 12:57:22")
public class AuthLogNames {

    /**
     * authLogIdのプロパティ名を返します。
     * 
     * @return authLogIdのプロパティ名
     */
    public static PropertyName<Integer> authLogId() {
        return new PropertyName<Integer>("authLogId");
    }

    /**
     * userIdのプロパティ名を返します。
     * 
     * @return userIdのプロパティ名
     */
    public static PropertyName<Integer> userId() {
        return new PropertyName<Integer>("userId");
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
     * resultのプロパティ名を返します。
     * 
     * @return resultのプロパティ名
     */
    public static PropertyName<Boolean> result() {
        return new PropertyName<Boolean>("result");
    }

    /**
     * ipAddressのプロパティ名を返します。
     * 
     * @return ipAddressのプロパティ名
     */
    public static PropertyName<String> ipAddress() {
        return new PropertyName<String>("ipAddress");
    }

    /**
     * hostのプロパティ名を返します。
     * 
     * @return hostのプロパティ名
     */
    public static PropertyName<String> host() {
        return new PropertyName<String>("host");
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
     * @author S2JDBC-Gen
     */
    public static class _AuthLogNames extends PropertyName<AuthLog> {

        /**
         * インスタンスを構築します。
         */
        public _AuthLogNames() {
        }

        /**
         * インスタンスを構築します。
         * 
         * @param name
         *            名前
         */
        public _AuthLogNames(final String name) {
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
        public _AuthLogNames(final PropertyName<?> parent, final String name) {
            super(parent, name);
        }

        /**
         * authLogIdのプロパティ名を返します。
         *
         * @return authLogIdのプロパティ名
         */
        public PropertyName<Integer> authLogId() {
            return new PropertyName<Integer>(this, "authLogId");
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
         * passwordのプロパティ名を返します。
         *
         * @return passwordのプロパティ名
         */
        public PropertyName<String> password() {
            return new PropertyName<String>(this, "password");
        }

        /**
         * resultのプロパティ名を返します。
         *
         * @return resultのプロパティ名
         */
        public PropertyName<Boolean> result() {
            return new PropertyName<Boolean>(this, "result");
        }

        /**
         * ipAddressのプロパティ名を返します。
         *
         * @return ipAddressのプロパティ名
         */
        public PropertyName<String> ipAddress() {
            return new PropertyName<String>(this, "ipAddress");
        }

        /**
         * hostのプロパティ名を返します。
         *
         * @return hostのプロパティ名
         */
        public PropertyName<String> host() {
            return new PropertyName<String>(this, "host");
        }

        /**
         * createdAtのプロパティ名を返します。
         *
         * @return createdAtのプロパティ名
         */
        public PropertyName<Timestamp> createdAt() {
            return new PropertyName<Timestamp>(this, "createdAt");
        }
    }
}