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
    <h2 class="page-title"><i class="fa fa-wrench"></i>&nbsp;ユーザ登録 | 入力内容確認</h2>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="alert alert-info"><i class="fa fa-question-circle"></i> 以下の入力内容で間違いないですか？</div>
        </div>
        <div class="col-md-6 col-md-offset-3">
            <div class="form-group">
                ログインID<br>
                ${userForm.email }
            </div>

            <div class="form-group">
                お名前<br>
                ${userForm.familyName } ${userForm.firstName }
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    権限
                </div>
                <div class="panel-body">
                    このユーザに付与する権限は以下です。
                    <div class="checkbox">
                        <c:forEach items="${roleEnum }" var="roleElement">
                            <c:if test="${com:collectionIncludes(userForm.roles, roleElement) }">${roleElement.name }</c:if>
                        </c:forEach>
                    </div>
                    <html:errors property="roles" />
                </div>
            </div>

            <form <c:if test="${ empty userId }">action="${contextPath }/maintain/user/addCommit"</c:if>
                  <c:if test="${!empty userId }">action="${contextPath }/maintain/user/editCommit"</c:if> method="POST" autocomplete="off">
                <div class="row">
                    <div class="col-md-6 col-md-offset">
                        <c:if test="${!empty userId }">
                            <input type="hidden" name="userId" value="${userForm.userId }">
                        </c:if>
                        <input type="hidden" name="email" value="${userForm.email }">
                        <input type="hidden" name="familyName" value="${userForm.familyName }">
                        <input type="hidden" name="firstName" value="${userForm.firstName }">
                        <c:forEach items="${userForm.roles }" varStatus="stat" var="role">
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
