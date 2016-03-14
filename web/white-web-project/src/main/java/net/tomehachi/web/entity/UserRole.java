package net.tomehachi.web.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * UserRoleエンティティクラス
 * 
 */
@Entity
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl"}, date = "2016/02/29 22:50:40")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /** userIdプロパティ */
    @Id
    @Column(precision = 10, nullable = false, unique = false)
    public Integer userId;

    /** roleプロパティ */
    @Id
    @Column(length = 6, nullable = false, unique = false)
    public String role;

    /** createdAtプロパティ */
    @Column(nullable = false, unique = false)
    public Timestamp createdAt;

    /** updatedAtプロパティ */
    @Column(nullable = false, unique = false)
    public Timestamp updatedAt;

    /** userAuth関連プロパティ */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UserAuth userAuth;
}