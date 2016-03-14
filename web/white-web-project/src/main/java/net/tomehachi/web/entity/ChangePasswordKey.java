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
 * ChangePasswordKeyエンティティクラス
 * 
 */
@Entity
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl"}, date = "2016/02/29 22:50:40")
public class ChangePasswordKey implements Serializable {

    private static final long serialVersionUID = 1L;

    /** userIdプロパティ */
    @Id
    @Column(precision = 10, nullable = false, unique = true)
    public Integer userId;

    /** changePasswordKeyプロパティ */
    @Column(length = 128, nullable = false, unique = false)
    public String changePasswordKey;

    /** doneプロパティ */
    @Column(nullable = false, unique = false)
    public Boolean done;

    /** expiredAtプロパティ */
    @Column(nullable = false, unique = false)
    public Timestamp expiredAt;

    /** createdAtプロパティ */
    @Column(nullable = false, unique = false)
    public Timestamp createdAt;

    /** userAuth関連プロパティ */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UserAuth userAuth;
}