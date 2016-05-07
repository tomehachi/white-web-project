package net.tomehachi.web.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * UserProfileエンティティクラス
 * 
 */
@Entity
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl"}, date = "2016/04/30 12:56:39")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    /** userIdプロパティ */
    @Id
    @Column(precision = 10, nullable = false, unique = true)
    public Integer userId;

    /** firstNameプロパティ */
    @Column(length = 64, nullable = false, unique = false)
    public String firstName;

    /** familyNameプロパティ */
    @Column(length = 64, nullable = false, unique = false)
    public String familyName;

    /** createdAtプロパティ */
    @Column(nullable = false, unique = false)
    public Timestamp createdAt;

    /** updatedAtプロパティ */
    @Column(nullable = false, unique = false)
    public Timestamp updatedAt;

    /** userAuth関連プロパティ */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UserAuth userAuth;
}