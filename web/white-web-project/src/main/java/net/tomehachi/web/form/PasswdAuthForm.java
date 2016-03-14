package net.tomehachi.web.form;

import org.seasar.struts.annotation.Arg;
import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Required;

public class PasswdAuthForm {

    @Required(arg0 = @Arg(key = "認証キー", resource = false))
    @Maxlength(maxlength = 128, arg0 = @Arg(key = "認証キー", resource = false))
    public String key;

}
