<tiles:insert template="/WEB-INF/view/default-parts/layout.jsp" flush="true">

    <tiles:put name="title">${pageTitle} | ${siteName }</tiles:put>

    <tiles:put name="css" type="string">
    <style>
    input[type=checkbox].btn-switch {
        display: none;
    }
    input[type=checkbox]:checked.btn-switch + button {
        background-color: #058;
        color: #fff;
    }
    </style>
    </tiles:put>

    <tiles:put name="javascript" type="string">
    <script>
    $(function() {
        $(".btn-switch + button").click(function() {
            $(this).prev().click();
        });
    });
    </script>
    </tiles:put>

    <tiles:put name="content" type="string">
    <span class="label label-danger">admin only</span>
    <h2 class="page-title">
        <i class="fa fa-wrench"></i>
        <c:if test="${ empty userId }">ユーザ登録</c:if>
        <c:if test="${!empty userId }">ユーザ編集</c:if>
    </h2>

    <form <c:if test="${ empty userId }">action="${contextPath }/maintain/user/addConfirm"</c:if>
          <c:if test="${!empty userId }">action="${contextPath }/maintain/user/editConfirm"</c:if>
          method="POST" autocomplete="off">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <html:errors property="org.apache.struts.action.GLOBAL_MESSAGE" />
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 col-md-offset-3">

                <c:if test="${ empty userId }">
                <div class="form-group">
                    <label for="email" class="control-label">メールアドレス(ログインID)</label>
                    <input type="text" id="email" class="form-control" name="email" placeholder="メールアドレス" value="${email }">
                    <html:errors property="email" />
                </div>
                </c:if>
                <c:if test="${!empty userId }">
                <div class="form-group">
                    <label for="email" class="control-label">メールアドレス(ログインID)</label><br>
                    ${f:h(email) }
                    <input type="hidden" name="email" value="${email }">
                </div>
                </c:if>

                <c:if test="${ empty userId }">
                <div class="alert alert-warning">
                    作成するユーザの初期パスワードは、自動生成されます。<br>
                    生成されたパスワードは、ご本人に直接メールで通知されます。
                </div>
                </c:if>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="familyName" class="control-label">姓</label>
                            <input type="text" id="familyName" class="form-control" name="familyName" placeholder="姓" value="${familyName }">
                            <html:errors property="familyName" />
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="firstName" class="control-label">名</label>
                            <input type="text" id="firstName" class="form-control" name="firstName" placeholder="名" value="${firstName }">
                            <html:errors property="firstName" />
                        </div>
                    </div>
                </div>

                <div class="panel panel-info">
                    <div class="panel-heading">
                        権限
                    </div>
                    <div class="panel-body">
                        付与する権限を以下から選んでください。
                        <div class="checkbox">
                            <c:forEach items="${roleEnum }" var="roleElement">
                            <input type="checkbox" class="btn-switch" name="roles" value="${roleElement }"
                                <c:if test="${com:collectionIncludes(userForm.roles, roleElement) }">checked="checked"</c:if>>
                                <button type="button" class="btn btn-default">${roleElement} (${roleElement.name })</button>
                            </c:forEach>
                        </div>
                        <html:errors property="roles" />
                    </div>
                </div>
                <c:if test="${!empty userId }">
                    <input type="hidden" name="userId" value="${userId }">
                </c:if>
                <button type="submit" class="btn btn-primary">
                    <c:if test="${ empty userId }">登録確認</c:if>
                    <c:if test="${!empty userId }">編集確認</c:if>
                </button>
                <input type="reset" class="btn btn-default" value="リセット">

            </div>
        </div>
    </form>

    </tiles:put>

</tiles:insert>
