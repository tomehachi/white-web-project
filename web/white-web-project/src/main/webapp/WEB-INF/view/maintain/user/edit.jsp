<tiles:insert template="/WEB-INF/view/default-parts/layout.jsp" flush="true">

    <tiles:put name="title">ユーザ編集 | ${siteName }</tiles:put>

    <tiles:put name="css" type="string">
    <link rel="stylesheet" href="${contextPath }/assets/bootstrap-table/bootstrap-table.css">
    <style>
    .label-symbols {
        font-size: 14px;
    }
    </style>
    </tiles:put>

    <tiles:put name="javascript" type="string">
    <script src="${contextPath}/assets/jquery/jquery.pwdMeasure.min.js"></script>
    <script src="${contextPath}/assets/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${contextPath}/assets/bootstrap-table/locale/bootstrap-table-ja-JP.min.js"></script>
    <script>
    $(function() {

    });
    </script>
    </tiles:put>

    <tiles:put name="content" type="string">
    <span class="label label-danger">admin only</span>
    <h2 class="page-title"><i class="fa fa-wrench"></i>&nbsp;ユーザ編集</h2>

    <form action="${contextPath }/maintain/user/editConfirm" method="POST" autocomplete="off">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <html:errors property="org.apache.struts.action.GLOBAL_MESSAGE" />
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="form-group">
                    <label for="email" class="form-label">ログインID</label><br>
                    ${email }
                    <html:errors property="email" />
                </div>

                <div class="panel panel-info">
                    <div class="panel-heading">
                        権限
                    </div>
                    <div class="panel-body">
                        付与する権限を以下から選んでください。
                        <div class="checkbox">
                            <%--
                                !!!ロール所有チェックは部分一致としているので、ロールの名称は要注意です!!!
                                例)
                                roleEnum = [admin, user, superadmin]
                                としてしまった場合、ユーザが所有するロールが admin となっていると
                                admin, superadmin どちらもチェックONと表示されてしまいます。

                                名称に注意するか、実装を変更下さい
                            --%>
                            <c:forEach items="${roleEnum }" var="roleElement">
                            <label><input type="checkbox" name="roles" value="${roleElement }" <c:if test="${fn:contains(maintainUserForm.roles, roleElement) }">checked="checked"</c:if>> ${roleElement.name }</label>　
                            </c:forEach>
                        </div>
                        <html:errors property="roles" />
                    </div>
                </div>
                <input type="hidden" name="userId" value="${userId }">
                <input type="hidden" name="email" value="${email }">
                <input type="submit" class="btn btn-primary" value="登録確認">
                <input type="reset" class="btn btn-default" value="リセット">

            </div>
        </div>
    </form>

    </tiles:put>

</tiles:insert>
