package net.tomehachi.web.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * UserAuthエンティティクラス
 * 
 */
@Entity
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl"}, date = "2016/02/29 22:50:40")
public class UserAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    /** userIdプロパティ */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(precision = 10, nullable = false, unique = true)
    public Integer userId;

    /** emailプロパティ */
    @Column(length = 128, nullable = false, unique = true)
    public String email;

    /** passwordプロパティ */
    @Column(length = 128, nullable = false, unique = false)
    public String password;

    /** createdAtプロパティ */
    @Column(nullable = false, unique = false)
    public Timestamp createdAt;

    /** updatedAtプロパティ */
    @Column(nullable = false, unique = false)
    public Timestamp updatedAt;

    /** changePasswordKey関連プロパティ */
    @OneToOne(mappedBy = "userAuth")
    public ChangePasswordKey changePasswordKey;

    /** userRoleList関連プロパティ */
    @OneToMany(mappedBy = "userAuth")
    public List<UserRole> userRoleList;
}