package net.tomehachi.web.action;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import net.tomehachi.web.annotation.PublicMethod;
import net.tomehachi.web.entity.UserAuth;
import net.tomehachi.web.form.SendResetPasswordMailForm;
import net.tomehachi.web.mail.MailDto;
import net.tomehachi.web.mail.MailUtil;
import net.tomehachi.web.service.ChangePasswordKeyService;
import net.tomehachi.web.service.UserAuthService;
import net.tomehachi.web.util.AppException;
import net.tomehachi.web.util.SecurityUtil;
import net.tomehachi.web.util.AppUtil;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.RequestUtil;

/**
 * パスワード忘れ関連アクションクラス.<br>
 *
 * @author tomehachi
 */
public class ForgotAction {

    @Resource
    protected UserAuthService userAuthService;

    @Resource
    protected ChangePasswordKeyService changePasswordKeyService;

    @Resource
    protected ServletContext application;

    @ActionForm
    public SendResetPasswordMailForm sendResetPasswordMailForm;

    /**
     * パスワードリセットURL送信画面<br>
     *
     * @return パスワードリセットURL送信画面
     */
    @Execute(validator = false)
    @PublicMethod
    public String index() {
        return "sendPasswordResetMail.jsp";
    }

    /**
     * パスワードリセットメール送信アクション<br>
     *
     * @return 送信完了画面
     * @throws AppException
     */
    @Execute(validator = true, validate = "validate", input = "sendPasswordResetMail.jsp")
    @PublicMethod
    public String sendPasswordResetMail() throws AppException {
        // 認証キーの生成
        String changePasswordKey = SecurityUtil.getPasswordForgotPassword();

        UserAuth userAuth = userAuthService.findByEmail(sendResetPasswordMailForm.email);
        // DBへは暗号化した認証キーを格納
        userAuth.changePasswordKey.changePasswordKey = SecurityUtil.encode(changePasswordKey);
        userAuth.changePasswordKey.done = false;

        // 有効期限には、同一ユーザは翌日の0時を設定(本操作は1日1回しか実行できない).
        userAuth.changePasswordKey.expiredAt = AppUtil.getTomorrow0Oclock();

        userAuth.changePasswordKey.createdAt = new Timestamp(new Date().getTime());

        // update 実行
        changePasswordKeyService.update(userAuth.changePasswordKey);

        if(userAuth != null) {
            // 存在するユーザなのでパスワードリセットURLを通知する.
            MailDto mailDto = new MailDto();
            mailDto.setTo(sendResetPasswordMailForm.email);
            mailDto.setSubject("パスワードリセットURL通知");
            mailDto.put("email", sendResetPasswordMailForm.email);
            mailDto.put("key", changePasswordKey);
            mailDto.put("expiredAt", AppUtil.dateTimeFormat(userAuth.changePasswordKey.expiredAt));

            // メール送信
            (new MailUtil()).sendMail(mailDto, application.getRealPath(
                    "/WEB-INF/mail_template/password_reset_mail_send_url.txt"));
        }
        return "passwordResetMailSent.jsp";
    }


    /**
     * パスワード忘れ認証バリデーション.<br>
     *
     * @return アクションメッセージ
     * @throws AppException reCAPTCHA認証でHTTP-GETした際の例外
     */
    public ActionMessages validate() throws AppException {
        ActionMessages messages = new ActionMessages();

        // g-reCAPTCHA認証
        String gRecaptchaResponse = RequestUtil.getRequest().getParameter("g-recaptcha-response");
        String endUserIp = RequestUtil.getRequest().getRemoteAddr();
        if(!AppUtil.isValidReCaptchaResponse(gRecaptchaResponse, endUserIp)) {
            // reCAPTCHA認証に失敗
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.changePasswordMailFailed"));
            return messages;
        }

        // 入力されたメールアドレスが存在するかチェック.
        UserAuth userAuth = userAuthService.findByEmail(sendResetPasswordMailForm.email);
        if(userAuth == null) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.changePasswordMailFailed"));
            return messages;
        }

        // パスワード変更認証キーの有効期限内ならば再発行しない.
        if(userAuth.changePasswordKey != null
                && userAuth.changePasswordKey.expiredAt.after(new Timestamp(new Date().getTime()))) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.changePasswordMailFailed"));
            return messages;
        }

        return messages;
    }
}
