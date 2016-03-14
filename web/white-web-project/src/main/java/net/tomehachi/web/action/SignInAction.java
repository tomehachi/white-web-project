package net.tomehachi.web.action;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import net.tomehachi.web.annotation.PublicMethod;
import net.tomehachi.web.dto.UserDataDto;
import net.tomehachi.web.entity.UserAuth;
import net.tomehachi.web.form.SignInForm;
import net.tomehachi.web.service.AuthLogService;
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
 * サインインアクションクラス.<br>
 *
 * @author tomehachi
 */
public class SignInAction {

    /** サインイン入力フォーム */
    @ActionForm
    public SignInForm signInForm;

    /** サーブレットコンテキスト */
    @Resource
    protected ServletContext application;

    /** ユーザ認証サービス */
    @Resource
    protected UserAuthService userAuthService;

    /** 認証ログサービス */
    @Resource
    protected AuthLogService authLogService;

    /** ユーザ情報 */
    @Resource
    public UserDataDto userDataDto;

    /**
     * サインイン画面.<br>
     *
     * @return サインイン画面(サインイン済みの場合はトップページへリダイレクト)
     */
    @PublicMethod
    @Execute(validator = false)
    public String index() {
        // サインイン済みの場合はトップページへリダイレクト.
        if(userDataDto.isSignedIn()) {
            return "/?redirect=true";
        } else {
            return "index.jsp";
        }
    }

    /**
     * サインイン処理.<br>
     *
     * @return 認証成功したら行こうとしていたページへリダイレクト
     */
    @PublicMethod
    @Execute(validator = true, validate = "authenticate", input = "index.jsp")
    public String auth() {
        HttpSession session = RequestUtil.getRequest().getSession();

        // セッション破棄
        session.invalidate();

        // 新しいセッションを取得.
        session = RequestUtil.getRequest().getSession();
        session.setAttribute("userDataDto", userDataDto);

        return userDataDto.requestedUrl + "?redirect=true";
    }

    /**
     * 認証処理.<br>
     *
     * @return アクションメッセージ
     * @throws AppException reCAPTCHA認証でHTTP-GETした際の例外
     */
    public ActionMessages authenticate() throws AppException {
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

        // 入力された email が登録されていなければ認証エラー
        UserAuth userAuthFromDb = userAuthService.findByEmail(signInForm.email);
        if(userAuthFromDb == null) {
            authLogService.writeNewLog(null, false);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.authentication"));
            return messages;
        }

        // パスワードが一致しなければ認証エラー
        if(!SecurityUtil.encode(signInForm.password).equals(userAuthFromDb.password)) {
            authLogService.writeNewLog(userAuthFromDb.userId, false);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.authentication"));
            return messages;
        }

        // 成功ログを書き込み
        authLogService.writeNewLog(userAuthFromDb.userId, true);
        // セッションにユーザIDを書き込み
        userDataDto.userId = userAuthFromDb.userId;
        userDataDto.isTemporary = Boolean.FALSE;
        return messages;
    }
}
