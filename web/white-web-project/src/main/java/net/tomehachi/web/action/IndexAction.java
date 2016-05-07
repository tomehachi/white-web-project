/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package net.tomehachi.web.action;

import javax.annotation.Resource;

import net.arnx.jsonic.JSON;
import net.tomehachi.web.dto.AjaxResultDto;
import net.tomehachi.web.dto.UserDataDto;
import net.tomehachi.web.dto.UserStatusDto;
import net.tomehachi.web.entity.UserAuth;
import net.tomehachi.web.service.UserAuthService;

import org.seasar.framework.aop.annotation.InvalidateSession;
import org.seasar.framework.aop.annotation.RemoveSession;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.ResponseUtil;

/**
 * トップ画面アクションクラス.<br>
 *
 * @author tomehachi
 */
public class IndexAction {

    @Resource
    public UserAuthService userAuthService;

    @Resource
    UserDataDto userDataDto;

    /**
     * トップ画面表示
     *
     * @return
     */
    @Execute(validator = false)
    public String index() {
        return "index.jsp";
    }

    /**
     * サインアウト処理.<br>
     *
     * @return [redirect] トップページへ
     */
    @Execute(validator = false)
    @RemoveSession(name = "userDataDto")
    @InvalidateSession
    public String signout() {
        return "/?redirect=true";
    }

    /**
     * Ajax ユーザの状態
     * @return
     */
    @Execute(validator = false)
    public String userStatus() {
        if(userDataDto.isSignedIn()) {
            UserAuth userAuth = userAuthService.findFullRelationById(userDataDto.userId);
            AjaxResultDto ajaxResult = new AjaxResultDto();
            UserStatusDto userStatus = new UserStatusDto();
            userStatus.email = userAuth.email;
            userStatus.name = userAuth.userProfile.familyName + userAuth.userProfile.firstName;
            ajaxResult.result = userStatus;

            ResponseUtil.write(JSON.encode(ajaxResult));
            return null;

        } else {
            return null;
        }
    }
}
