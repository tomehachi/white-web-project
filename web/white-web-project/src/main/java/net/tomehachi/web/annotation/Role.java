package net.tomehachi.web.annotation;

/**
 * システム内に存在するロールを定義した列挙型クラス.<br>
 *
 * @author tomehachi
 */
public enum Role {
    /** 最高権限 */
    admin("管理者"),

    /** 一般権限 */
    user("一般利用者");

    Role(String name) {
        this.name = name;
    }

    public String name;
    public String getName() {
        return name;
    }
}
