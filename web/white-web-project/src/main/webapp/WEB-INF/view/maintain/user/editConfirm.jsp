<%@page import="net.tomehachi.web.annotation.Role"%>

<tiles:insert template="/WEB-INF/view/default-parts/layout.jsp" flush="true">

    <tiles:put name="title">${pageTitle} | ${siteName }</tiles:put>

    <tiles:put name="css" type="string">
    <link rel="stylesheet" href="${contextPath }/assets/bootstrap-table/bootstrap-table.css">
    <style>
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
    <h2 class="page-title"><i class="fa fa-wrench"></i>&nbsp;ユーザ編集 | 入力内容確認</h2>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="alert alert-info"><i class="fa fa-question-circle"></i> 以下の入力内容で間違いないですか？</div>
        </div>
        <div class="col-md-6 col-md-offset-3">
            <div class="form-group">
                ログインID<br>
                ${maintainUserForm.email }
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    権限
                </div>
                <div class="panel-body">
                    このユーザに付与する権限は以下です。
                    <c:set var="roleModel"><%= Role.admin %></c:set>
                    <div class="checkbox">
                        <c:forEach items="${maintainUserForm.roles }" var="role">
                            <%= Role.valueOf((String)pageContext.getAttribute("role")).name %>
                        </c:forEach>
                    </div>
                    <html:errors property="roles" />
                </div>
            </div>

            <form action="${contextPath }/maintain/user/editCommit" method="POST" autocomplete="off">
                <div class="row">
                    <div class="col-md-6 col-md-offset">
                        <input type="hidden" name="email" value="${maintainUserForm.email }">
                        <c:forEach items="${maintainUserForm.roles }" varStatus="stat" var="role">
                            <input type="hidden" name="roles[${stat.index }]" value="${role }">
                        </c:forEach>
                        <input type="submit" class="btn btn-primary" name="submit" value="登録確認">
                        <input type="submit" class="btn btn-default" name="submit" value="戻る">
                    </div>
                </div>
            </form>

        </div>
    </div>

    </tiles:put>

</tiles:insert>
