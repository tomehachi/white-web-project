package net.tomehachi.web.form.maintain;

import java.io.Serializable;

import net.tomehachi.web.annotation.Role;

import org.seasar.struts.annotation.Arg;
import org.seasar.struts.annotation.EmailType;
import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Required;

public class UserForm implements Serializable {

    @Required(arg0 = @Arg(key = "メールアドレス", resource = false))
    @EmailType(arg0 = @Arg(key = "メールアドレス", resource = false))
    @Maxlength(maxlength = 128, arg0 = @Arg(key = "メールアドレス", resource = false))
    public String email;

    /** 編集時のみ使用(登録時は使用しない) */
    public Integer userId;

    public String[] roles = new String[]{};

    /** 存在する全ロール */
    public Role[] roleEnum = Role.values();

    public String submit;
}
