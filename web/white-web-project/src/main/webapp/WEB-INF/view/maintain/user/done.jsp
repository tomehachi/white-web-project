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
    <h2 class="page-title"><i class="fa fa-wrench"></i>&nbsp;完了</h2>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="alert alert-info"><i class="fa fa-info-circle"></i> ユーザの操作が完了しました</div>
        </div>
        <div class="col-md-6 col-md-offset-3">
            <a href="${contextPath }/maintain/user/" class="btn btn-default">ユーザ一覧に戻る</a>
        </div>
    </div>

    </tiles:put>

</tiles:insert>
