package net.tomehachi.web.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * AuthLogエンティティクラス
 * 
 */
@Entity
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl"}, date = "2016/04/30 12:56:39")
public class AuthLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /** authLogIdプロパティ */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(precision = 10, nullable = false, unique = true)
    public Integer authLogId;

    /** userIdプロパティ */
    @Column(precision = 10, nullable = true, unique = false)
    public Integer userId;

    /** passwordプロパティ */
    @Column(length = 128, nullable = true, unique = false)
    public String password;

    /** resultプロパティ */
    @Column(nullable = true, unique = false)
    public Boolean result;

    /** ipAddressプロパティ */
    @Column(length = 128, nullable = true, unique = false)
    public String ipAddress;

    /** hostプロパティ */
    @Column(length = 256, nullable = true, unique = false)
    public String host;

    /** createdAtプロパティ */
    @Column(nullable = false, unique = false)
    public Timestamp createdAt;
}