package net.tomehachi.web.action;

import org.seasar.struts.annotation.Execute;

/**
 * エラー画面表示アクションクラス.<br>
 *
 * @author tomehachi
 */
public class ErrorAction {

    /**
     * 認可エラー画面
     *
     * @return 認可エラー画面
     */
    @Execute(validator = false)
    public String notallowed() {
        return "notallowed.jsp";
    }

}
