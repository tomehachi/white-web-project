<tiles:insert template="/WEB-INF/view/default-parts/layout-no-menu.jsp" flush="true">

    <tiles:put name="title">パスワード変更完了 | ${siteName }</tiles:put>

    <tiles:put name="css" type="string">
    </tiles:put>

    <tiles:put name="javascript" type="string">
    </tiles:put>

    <tiles:put name="content" type="string">
    <span class="label label-danger">only you</span>
    <h2 class="page-title"><i class="fa fa-wrench"></i>&nbsp;パスワード変更完了</h2>

    <div class="alert alert-info">
        パスワードを変更しました.<br>
        パスワード変更に伴い、自動でサインアウトしました.<br>
    </div>

    <div class="text-center">
        <a class="btn btn-primary" href="${contextPath }/signIn">認証画面へ</a>
    </div>

    </tiles:put>

</tiles:insert>
