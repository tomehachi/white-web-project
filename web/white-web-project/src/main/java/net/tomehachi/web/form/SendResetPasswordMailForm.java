package net.tomehachi.web.form;

import java.io.Serializable;

import org.seasar.struts.annotation.Arg;
import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Required;

/**
 * ログイン用アクションフォームクラス.<br>
 *
 * @author nakaoka.kentarou
 */
public class SendResetPasswordMailForm implements Serializable {

    @Required(arg0 = @Arg(key = "メールアドレス", resource = false))
    @Maxlength(maxlength = 128, arg0 = @Arg(key = "メールアドレス", resource = false))
    public String email;

}
