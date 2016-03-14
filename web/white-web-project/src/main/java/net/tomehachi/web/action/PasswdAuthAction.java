package net.tomehachi.web.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.tomehachi.web.annotation.PublicMethod;
import net.tomehachi.web.dto.UserDataDto;
import net.tomehachi.web.entity.ChangePasswordKey;
import net.tomehachi.web.form.PasswdAuthForm;
import net.tomehachi.web.service.AuthLogService;
import net.tomehachi.web.service.ChangePasswordKeyService;
import net.tomehachi.web.util.AppException;
import net.tomehachi.web.util.AppUtil;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.RequestUtil;

/**
 * パスワード忘れ認証アクションクラス.<br>
 *
 * @author tomehachi
 */
public class PasswdAuthAction {

    /** パスワード忘れ認証入力フォーム */
    @ActionForm
    public PasswdAuthForm passwdAuthForm;

    /** ユーザ情報 */
    @Resource
    public UserDataDto userDataDto;

    /** 認証ログサービス */
    @Resource
    public AuthLogService authLogService;

    /** パスワード変更認証キーサービス */
    @Resource
    public ChangePasswordKeyService changePasswordKeyService;

    /**
     * パスワード変更認証画面.<br>
     *
     * @return パスワード変更認証画面
     */
    @PublicMethod
    @Execute(validator = false)
    public String index() {
        return "index.jsp";
    }

    /**
     * パスワード変更認証処理.<br>
     *
     * @return パスワード変更画面
     */
    @PublicMethod
    @Execute(validator = true, validate = "validateUser", input = "index.jsp")
    public String auth() {
        HttpSession session = RequestUtil.getRequest().getSession();

        // セッション破棄
        session.invalidate();

        // 新しいセッションを取得.
        session = RequestUtil.getRequest().getSession();
        session.setAttribute("userDataDto", userDataDto);

        return "/passwd/?redirect=true";
    }

    /**
     * パスワード変更認証処理.<br>
     *
     * @return アクションメッセージ
     * @throws AppException reCAPTCHA認証でHTTP-GETした際の例外
     */
    public ActionMessages validateUser() throws AppException {
        ActionMessages messages = new ActionMessages();

        // g-reCAPTCHA
        String gRecaptchaResponse = RequestUtil.getRequest().getParameter("g-recaptcha-response");
        String endUserIp = RequestUtil.getRequest().getRemoteAddr();
        if(!AppUtil.isValidReCaptchaResponse(gRecaptchaResponse, endUserIp)) {
            authLogService.writeNewLog(null, false);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.authentication"));
            return messages;
        }

        // 過去指定時間の間に指定回数以上、認証失敗している場合は、認証を行なわない.
        if(authLogService.isTooManyAuthFailure()) {
            authLogService.writeNewLog(null, false);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.authlock"));
            return messages;
        };

        ChangePasswordKey changePasswordKey = changePasswordKeyService.findUserByKey(passwdAuthForm);

        // 対象ユーザが見つからない場合は認証エラー
        if(changePasswordKey == null) {
            authLogService.writeNewLog(null, false);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.authFailed"));
            return messages;
        }

        // 対象ユーザの認証キーがnullの場合は認証エラー
        if(changePasswordKey.changePasswordKey == null) {
            authLogService.writeNewLog(null, false);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.authFailed"));
            return messages;
        }

        // 使用済みフラグがtrueの場合は認証エラー
        if(changePasswordKey.done) {
            authLogService.writeNewLog(null, false);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.authFailed"));
            return messages;
        }

        // 成功ログを書き込み
        authLogService.writeNewLog(changePasswordKey.userId, true);
        // セッションにユーザIDを書き込み
        userDataDto.userId = changePasswordKey.userId;
        userDataDto.isTemporary = Boolean.TRUE;
        return messages;
    }
}
