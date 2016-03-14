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
import net.tomehachi.web.entity.UserRole;
import net.tomehachi.web.form.maintain.MaintainUserForm;
import net.tomehachi.web.mail.MailDto;
import net.tomehachi.web.mail.MailUtil;
import net.tomehachi.web.service.ChangePasswordKeyService;
import net.tomehachi.web.service.UserAuthService;
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
    public MaintainUserForm maintainUserForm;

    /** ユーザ認証サービス */
    @Resource
    protected UserAuthService userAuthService;

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

    @RoleLimited(role = Role.admin)
    @Execute(validator = false)
    public String add() {
        return "add.jsp";
    }

    @RoleLimited(role = Role.admin)
    @Execute(validator = true, validate = "validateUserAuth, validateUserRole", input = "add.jsp")
    public String addConfirm() {
        return "addConfirm.jsp";
    }

    @RoleLimited(role = Role.admin)
    @Execute(validator = true, validate = "validateUserAuth, validateUserRole", input = "add.jsp")
    public String addCommit() throws AppException {
        if(maintainUserForm.submit.equals("戻る")) {
            return "add.jsp";
        }

        // ユーザの初期パスワードを生成
        String initPassword = SecurityUtil.randomString(64);

        // ユーザ認証情報の作成
        UserAuth userAuth = new UserAuth();
        userAuth.email = maintainUserForm.email;
        userAuth.password = SecurityUtil.encode(initPassword);
        userAuth.createdAt = new Timestamp(new Date().getTime());
        userAuth.updatedAt = new Timestamp(new Date().getTime());
        userAuthService.insert(userAuth);

        // 自動発行されたユーザIDを取得.
        Integer userId = jdbcManager.selectBySql(
                Integer.class, "SELECT LAST_INSERT_ID() FROM user_auth LIMIT 1").getSingleResult();

        for(String role : maintainUserForm.roles) {
            // ユーザロールの作成
            UserRole userRole = new UserRole();
            userRole.userId = userId;
            userRole.role = role;
            userRole.createdAt = new Timestamp(new Date().getTime());
            userRole.updatedAt = new Timestamp(new Date().getTime());
            userRoleService.insert(userRole);
        }

        // パスワード変更認証キーの初期レコード作成
        ChangePasswordKey changePasswordKey = new ChangePasswordKey();
        changePasswordKey.userId = userId;
        changePasswordKey.changePasswordKey = "-";
        changePasswordKey.done = true;
        changePasswordKeyService.insert(changePasswordKey);

        MailDto mailDto = new MailDto();
        mailDto.setTo(userAuth.email);
        mailDto.setSubject("アカウント発行通知");
        mailDto.put("name", userAuth.email);
        mailDto.put("id", userAuth.email);
        mailDto.put("password", initPassword);
        (new MailUtil()).sendMail(mailDto, application.getRealPath("/WEB-INF/mail_template/account_create_mail.txt"));
        return "/maintain/user/addDone?redirect=true";
    }

    @RoleLimited(role = Role.admin)
    @Execute(validator=false)
    public String addDone() {
        return "addDone.jsp";
    }

    /**
     * ユーザ認証情報の入力チェック.<br>
     *
     * @return ActionMessage
     */
    public ActionMessages validateUserAuth() {
        ActionMessages result = new ActionMessages();

        /* -- ユーザ重複チェック -- */
        if(userAuthService.userExists(maintainUserForm.email)) {
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
        if(maintainUserForm.roles == null || maintainUserForm.roles.length == 0) {
            result.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.roleEmpty"));
        }

        /* -- ロール存在チェック -- */
        for(String role: maintainUserForm.roles) {
            boolean isValidRoleName = false;
            for(Role roleMaster : Role.values()) {
                if(role.equals(roleMaster.toString())) {
                    isValidRoleName = true;
                }
            }
            if(!isValidRoleName) {
                result.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidRole"));
            }
        }

        /* -- ロール一意チェック -- */
        Set<String> uniqueRoles = new HashSet<String>();
        for(String role: maintainUserForm.roles) {
            if(uniqueRoles.contains(role)) {
                result.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.roleDuplication"));
            } else {
                uniqueRoles.add(role);
            }
        }
        return result;
    }

    /* -- 編集 ------------------------------------------------------------------------------------------------------ */

    @RoleLimited(role = Role.admin)
    @Execute(validator = false, validate="validateUserEdit", input="index.jsp")
    public String edit() throws AppException {
        maintainUserForm.email = userAuthService.findById(maintainUserForm.userId).email;
        maintainUserForm.roles = userRoleService.getRoleNames(maintainUserForm.userId);
        return "edit.jsp";
    }

    @RoleLimited(role = Role.admin)
    @Execute(validator = false, validate="validateUserRole", input="edit.jsp")
    public String editConfirm() {
        return "editConfirm.jsp";
    }

    @RoleLimited(role = Role.admin)
    @Execute(validator = true, validate = "validateUserEdit, validateUserRole", input = "edit.jsp")
    public String editCommit() {
        if(maintainUserForm.submit.equals("戻る")) {
            return "edit.jsp";
        }
        // ロール削除
        List<UserRole> roles = userRoleService.findByUserId(maintainUserForm.userId);
        for(UserRole role : roles) {
            userRoleService.delete(role);
        }
        // ロール追加
        for(String roleName : maintainUserForm.roles) {
            UserRole roleDto = new UserRole();
            roleDto.userId = maintainUserForm.userId;
            roleDto.role = roleName;
            roleDto.createdAt = new Timestamp((new Date()).getTime());
            roleDto.updatedAt = new Timestamp((new Date()).getTime());
            userRoleService.insert(roleDto);
        }
        return "/maintain/user/editDone?redirect=true";
    }

    @RoleLimited(role = Role.admin)
    @Execute(validator = false)
    public String editDone() {
        return "editDone.jsp";
    }

    public ActionMessages validateUserEdit() {
        ActionMessages result = new ActionMessages();

        // ユーザID未指定
        if(maintainUserForm == null || maintainUserForm.userId == null) {
            result.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.undefined.userId"));
            return result;
        }

        // ユーザID存在チェック
        if(userAuthService.findById(maintainUserForm.userId) == null) {
            result.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.notFound.userId"));
            return result;
        }
        return result;
    }
}
