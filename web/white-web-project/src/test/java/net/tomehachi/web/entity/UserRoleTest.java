package net.tomehachi.web.entity;

import javax.annotation.Generated;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.unit.S2TestCase;

import static net.tomehachi.web.entity.UserRoleNames.*;

/**
 * {@link UserRole}のテストクラスです。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.EntityTestModelFactoryImpl"}, date = "2016/02/10 16:58:09")
public class UserRoleTest extends S2TestCase {

    private JdbcManager jdbcManager;

    /**
     * 事前処理をします。
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("s2jdbc.dicon");
    }

    /**
     * 識別子による取得をテストします。
     * 
     * @throws Exception
     */
    public void testFindById() throws Exception {
        jdbcManager.from(UserRole.class).id(1).getSingleResult();
    }

    /**
     * userAuthとの外部結合をテストします。
     * 
     * @throws Exception
     */
    public void testLeftOuterJoin_userAuth() throws Exception {
        jdbcManager.from(UserRole.class).leftOuterJoin(userAuth()).id(1).getSingleResult();
    }
}