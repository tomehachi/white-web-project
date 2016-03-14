package net.tomehachi.web.form;

import org.seasar.struts.annotation.Arg;
import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Required;

public class PasswdForm {

    @Required(arg0 = @Arg(key = "新しいパスワード", resource = false))
    @Maxlength(maxlength = 128, arg0 = @Arg(key = "新しいパスワード", resource = false))
    public String newPassword1;

    @Required(arg0 = @Arg(key = "新しいパスワード(確認用)", resource = false))
    @Maxlength(maxlength = 128, arg0 = @Arg(key = "新しいパスワード(確認用)", resource = false))
    public String newPassword2;
}
