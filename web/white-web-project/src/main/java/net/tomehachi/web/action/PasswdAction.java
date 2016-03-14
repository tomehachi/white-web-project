package net.tomehachi.web.action;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.tomehachi.web.annotation.TemporaryMethod;
import net.tomehachi.web.dto.UserDataDto;
import net.tomehachi.web.entity.UserAuth;
import net.tomehachi.web.form.PasswdForm;
import net.tomehachi.web.service.UserAuthService;
import net.tomehachi.web.util.SecurityUtil;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.RequestUtil;

/**
 * パスワード変更アクションクラス.<br>
 *
 * @author tomehachi
 */
public class PasswdAction {

    /** パスワード変更入力フォーム */
    @ActionForm
    public PasswdForm passwdForm;

    /** ユーザ認証サービス */
    @Resource
    public UserAuthService userAuthService;

    /** ユーザ情報 */
    @Resource
    public UserDataDto userDataDto;

    @TemporaryMethod
    @Execute(validator = false)
    public String index() {
        return "index.jsp";
    }

    @TemporaryMethod
    @Execute(validator = true, validate = "validatePassword", input = "index.jsp")
    public String done() {
        // ユーザ
        UserAuth userAuth = userAuthService.findById(userDataDto.userId);
        userAuth.password = SecurityUtil.encode(passwdForm.newPassword1);
        userAuth.updatedAt = new Timestamp((new Date()).getTime());
        userAuthService.update(userAuth);

        HttpSession session = RequestUtil.getRequest().getSession();
        session.invalidate();

        // 新しいセッションを取得.
        session = RequestUtil.getRequest().getSession();
        session.setAttribute("userDataDto", new UserDataDto());

        return "done.jsp";
    }

    /**
     * パスワードバリデーション.<br>
     *
     * @return アクションメッセージ
     */
    public ActionMessages validatePassword() {
        ActionMessages messages = new ActionMessages();

        // 確認用との不一致
        if(!passwdForm.newPassword1.equals(passwdForm.newPassword2)) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.password.notSame"));
        }

        // アルファベット(小文字)を含むかどうかチェック.
        if(!SecurityUtil.includesLower(passwdForm.newPassword1)) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.password.lowerCase"));
        }

        // アルファベット(大文字)を含むかどうかチェック.
        if(!SecurityUtil.includesUpper(passwdForm.newPassword1)) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.password.upperCase"));
        }

        // 数字を含むかどうかチェック.
        if(!SecurityUtil.includesNumber(passwdForm.newPassword1)) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.password.number"));
        }

        // 記号を含むかどうかチェック.
        if(!SecurityUtil.includesSymbol(passwdForm.newPassword1)) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.password.symbols"));
        }

        // 未許可の文字列を含むかどうかチェック.
        if(SecurityUtil.includesInvalidPasswordChar(passwdForm.newPassword1)) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.password.invalidChar"));
        }
        return messages;
    }
}
