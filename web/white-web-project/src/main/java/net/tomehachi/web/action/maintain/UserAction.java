package net.tomehachi.web.action.maintain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import net.tomehachi.web.annotation.Role;
import net.tomehachi.web.annotation.RoleLimited;
import net.tomehachi.web.entity.ChangePasswordKey;
import net.tomehachi.web.entity.UserAuth;
import net.tomehachi.web.entity.UserProfile;
import net.tomehachi.web.entity.UserRole;
import net.tomehachi.web.form.maintain.UserForm;
import net.tomehachi.web.mail.MailDto;
import net.tomehachi.web.mail.MailUtil;
import net.tomehachi.web.service.ChangePasswordKeyService;
import net.tomehachi.web.service.UserAuthService;
import net.tomehachi.web.service.UserProfileService;
import net.tomehachi.web.service.UserRoleService;
import net.tomehachi.web.util.AppException;
import net.tomehachi.web.util.SecurityUtil;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.ResponseUtil;

/**
 * ユーザメンテナンスアクションクラス.<br>
 *
 * @author tomehachi
 */
public class UserAction {

    /** ユーザメンテナンス */
    @ActionForm
    public UserForm userForm;

    /** ユーザ認証サービス */
    @Resource
    protected UserAuthService userAuthService;

    /** ユーザプロフィールサービス */
    @Resource
    protected UserProfileService userProfileService;

    /** ユーザロールサービス */
    @Resource
    protected UserRoleService userRoleService;

    /** パスワード変更キーサービス */
    @Resource
    protected ChangePasswordKeyService changePasswordKeyService;

    /** DB接続マネージャ */
    @Resource
    protected JdbcManager jdbcManager;

    /** サーブレットコンテキスト */
    @Resource
    protected ServletContext application;

    /* -- index ----------------------------------------------------------------------------------------------------- */

    /**
     * ユーザ一覧
     *
     * @return ユーザ一覧
     */
    @RoleLimited(role = Role.admin)
    @Execute(validator = false)
    public String index() {
        return "index.jsp";
    }

    /**
     * AjaxAPI ユーザ一覧JSONを返す.<br>
     *
     * @return JSON
     */
    @RoleLimited(role = Role.admin)
    @Execute(validator = false)
    public String userListJson() {
        ResponseUtil.write(userAuthService.getUserListJson());
        return null;
    }

    /* -- 追加 ------------------------------------------------------------------------------------------------------ */

    /**
     * ユーザ情報の追加入力画面
     *
     * @return ユーザの追加入力画面
     */
    @RoleLimited(role = Role.admin)
    @Execute(validator = false)
    public String add() {
        return "edit.jsp";
    }

    /**
     * ユーザ情報の追加確認画面
     *
     * @return ユーザの追加確認画面
     */
    @RoleLimited(role = Role.admin)
    @Execute(validator = true, validate = "validateUserAuth, validateUserRole", input = "add.jsp")
    public String addConfirm() {
        return "confirm.jsp";
    }

    /**
     * ユーザ情報の追加処理
     *
     * @return 完了画面へのリダイレクト
     * @throws AppException
     */
    @RoleLimited(role = Role.admin)
    @Execute(validator = true, validate = "validateUserAuth, validateUserRole", input = "add.jsp")
    public String addCommit() throws AppException {

        // 戻るボタンが押されたときの処理
        if(userForm.submit.equals("戻る")) {
            return "edit.jsp";
        }

        // ユーザの初期パスワードを生成
        String initPassword = SecurityUtil.randomString(64);

        // ユーザ認証情報の登録
        UserAuth userAuth = new UserAuth();
        userAuth.email = userForm.email;
        userAuth.password = SecurityUtil.encode(initPassword);
        userAuth.createdAt = new Timestamp(new Date().getTime());
        userAuth.updatedAt = new Timestamp(new Date().getTime());
        userAuthService.insert(userAuth);

        // 自動発行されたユーザIDを取得.
        Integer userId = jdbcManager.selectBySql(
                Integer.class, "SELECT LAST_INSERT_ID() FROM user_auth LIMIT 1").getSingleResult();

        // ユーザロールの登録
        for(String role : userForm.roles) {
            UserRole userRole = new UserRole();
            userRole.userId = userId;
            userRole.role = role;
            userRole.createdAt = new Timestamp(new Date().getTime());
            userRole.updatedAt = new Timestamp(new Date().getTime());
            userRoleService.insert(userRole);
        }

        // ユーザプロフィールの登録
        UserProfile userProfile = new UserProfile();
        userProfile.userId = userId;
        userProfile.familyName = userForm.familyName;
        userProfile.firstName = userForm.firstName;
        userProfileService.insert(userProfile);

        // パスワード変更認証キーの初期レコード作成
        ChangePasswordKey changePasswordKey = new ChangePasswordKey();
        changePasswordKey.userId = userId;
        changePasswordKey.changePasswordKey = "-";
        changePasswordKey.done = true;
        changePasswordKeyService.insert(changePasswordKey);

        // アカウント発行通知の送信
        MailDto mailDto = new MailDto();
        mailDto.setTo(userAuth.email);
        mailDto.setSubject("アカウント発行通知");
        mailDto.put("name", userAuth.email);
        mailDto.put("id", userAuth.email);
        mailDto.put("password", initPassword);
        (new MailUtil()).sendMail(mailDto, application.getRealPath("/WEB-INF/mail_template/account_create_mail.txt"));

        // 完了画面にリダイレクト
        return "/maintain/user/done?redirect=true";
    }

    /**
     * ユーザ認証情報の入力チェック.<br>
     *
     * @return ActionMessage
     */
    public ActionMessages validateUserAuth() {
        ActionMessages result = new ActionMessages();

        /* -- ユーザ重複チェック -- */
        if(userAuthService.userExists(userForm.email)) {
            result.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.userDuplication"));
        }
        return result;
    }

    /**
     * ユーザロールの入力チェック.<br>
     *
     * @return ActionMessages
     */
    public ActionMessages validateUserRole() {
        ActionMessages result = new ActionMessages();

        /* -- 未選択チェック -- */
        if(userForm.roles == null || userForm.roles.length == 0) {
            result.add("roles", new ActionMessage("errors.roleEmpty"));
        }

        /* -- ロール存在チェック -- */
        for(String role: userForm.roles) {
            boolean isValidRoleName = false;
            for(Role roleMaster : Role.values()) {
                if(role.equals(roleMaster.toString())) {
                    isValidRoleName = true;
                }
            }
            if(!isValidRoleName) {
                result.add("roles", new ActionMessage("errors.invalidRole"));
            }
        }

        /* -- ロール一意チェック -- */
        Set<String> uniqueRoles = new HashSet<String>();
        for(String role: userForm.roles) {
            if(uniqueRoles.contains(role)) {
                result.add("roles", new ActionMessage("errors.roleDuplication"));
            } else {
                uniqueRoles.add(role);
            }
        }
        return result;
    }

    /* -- 編集 ------------------------------------------------------------------------------------------------------ */

    /**
     * ユーザ情報の編集入力画面<br>
     *
     * @return ユーザ情報の編集入力画面
     * @throws AppException
     */
    @RoleLimited(role = Role.admin)
    @Execute(validator = false, validate="validateUserEdit", input="index.jsp")
    public String edit() throws AppException {
        // ユーザ認証情報からメールアドレスを取得
        userForm.email = userAuthService.findById(userForm.userId).email;

        // ユーザプロフィール情報を取得
        UserProfile userProfile = userProfileService.findById(userForm.userId);
        userForm.familyName = userProfile.familyName;
        userForm.firstName = userProfile.firstName;

        // ユーザロール情報から保持ロールを取得
        userForm.roles = userRoleService.getRoleNames(userForm.userId);
        return "edit.jsp";
    }

    /**
     * ユーザ情報の編集確認画面<br>
     *
     * @return ユーザ情報の編集確認画面
     */
    @RoleLimited(role = Role.admin)
    @Execute(validator = false, validate="validateUserRole", input="edit.jsp")
    public String editConfirm() {
        return "confirm.jsp";
    }

    /**
     * ユーザ情報の編集コミット処理
     *
     * @return
     */
    @RoleLimited(role = Role.admin)
    @Execute(validator = true, validate = "validateUserEdit, validateUserRole", input = "edit.jsp")
    public String editCommit() {

        // 戻るボタンが押されたときの処理.
        if(userForm.submit.equals("戻る")) {
            return "edit.jsp";
        }

        // ユーザプロフィール情報の更新
        UserProfile userProfile = userProfileService.findById(userForm.userId);
        userProfile.familyName = userForm.familyName;
        userProfile.firstName = userForm.firstName;
        userProfile.updatedAt = null;
        userProfileService.update(userProfile);

        // ロール削除
        List<UserRole> roles = userRoleService.findByUserId(userForm.userId);
        for(UserRole role : roles) {
            userRoleService.delete(role);
        }

        // ロール追加
        for(String roleName : userForm.roles) {
            UserRole roleDto = new UserRole();
            roleDto.userId = userForm.userId;
            roleDto.role = roleName;
            roleDto.createdAt = new Timestamp((new Date()).getTime());
            roleDto.updatedAt = new Timestamp((new Date()).getTime());
            userRoleService.insert(roleDto);
        }

        // 完了画面にリダイレクト
        return "/maintain/user/done?redirect=true";
    }

    /**
     * 編集用バリデーション<br>
     *
     * @return
     */
    public ActionMessages validateUserEdit() {
        ActionMessages result = new ActionMessages();

        // ユーザID未指定
        if(userForm == null || userForm.userId == null) {
            result.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.undefined.userId"));
            return result;
        }

        // ユーザID存在チェック
        if(userAuthService.findById(userForm.userId) == null) {
            result.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notFound.userId"));
            return result;
        }
        return result;
    }

    /* -- 共通画面 -------------------------------------------------------------------------------------------------- */

    /**
     * ユーザメンテナンス完了画面(追加/変更共通)
     *
     * @return ユーザメンテナンス完了画面(追加/変更共通)
     */
    @RoleLimited(role = Role.admin)
    @Execute(validator=false)
    public String done() {
        return "done.jsp";
    }

}
