<tiles:insert template="/WEB-INF/view/default-parts/layout.jsp" flush="true">

    <tiles:put name="title">ユーザ一覧 | ${siteName }</tiles:put>

    <tiles:put name="css" type="string">
    <link rel="stylesheet" href="${contextPath }/assets/bootstrap-table/bootstrap-table.css">
    </tiles:put>

    <tiles:put name="javascript" type="string">
    <script src="${contextPath}/assets/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${contextPath}/assets/bootstrap-table/locale/bootstrap-table-ja-JP.min.js"></script>
    <script src="${contextPath}/assets/bootstrap-table/extensions/filter-control/bootstrap-table-filter-control.js"></script>
    <script>
    var roleFormatter = function(value, row, index) {
        var adminLabel = "<span class=\"label label-danger\">admin</span>&nbsp;";
        var userLabel = "<span class=\"label label-info\">user</span>&nbsp;";
        var result = "";
        $.each(row.userRoleList, function(i, d) {
            if(d.role == 'user') {
                result += userLabel;
            } else if(d.role == 'admin') {
                result += adminLabel;
            }
        });
        return result;
    };
    var dateFormatter = function(value, row, index) {
        var date = new Date(parseInt(value));
        return date.getFullYear()
            + "/" + ((date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1))
            + "/" + (date.getDay() < 10 ? "0" + date.getDay() : date.getDay())
            + " " + (date.getHours() < 10 ? "0" + date.getHours() : date.getHours())
            + ":" + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes())
            + ":" + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds());
    };
    var controls = function(value, row, index) {
        return controlsHtml.replace(/\{0\}/, value);
    };
    var controlsHtml = (function() {/*
        <a href="${contextPath }/maintain/user/edit/?userId={0}" class="btn btn-primary btn-sm">編集</a>
    */}).toString().match(/(?:\/\*(?:[\s\S]*?)\*\/)/).pop().replace(/^\/\*/, "").replace(/\*\/$/, "");

    $(function() {
        $("#user-list").bootstrapTable({
            url: "${contextPath}/maintain/user/userListJson",
            toolbar: "#toolbar",
            search: true,
            showToggle: true,
            showColumns: true,
            mobileResponsive: true,
            filterControl: true
        });
    });
    </script>
    </tiles:put>

    <tiles:put name="content" type="string">
    <span class="label label-danger">admin only</span>
    <a href="${contextPath }/maintain/user/add" class="btn btn-primary pull-right">新規作成</a>
    <h2 class="page-title"><i class="fa fa-wrench"></i>&nbsp;ユーザ一覧</h2>

    <html:errors property="org.apache.struts.action.GLOBAL_MESSAGE" />

    <div id="toolbar"></div>
    <table id="user-list">
        <thead>
            <tr>
                <th data-field="userId" data-sortable="true">ID</th>
                <th data-field="email" data-sortable="true">email</th>
                <th data-field="userRoleList" data-formatter="roleFormatter" data-filter-control="input">ロール</th>
                <th data-field="createdAt" data-formatter="dateFormatter" data-sortable="true">登録日</th>
                <th data-field="updatedAt" data-formatter="dateFormatter" data-sortable="true">更新日</th>
                <th data-field="userId" data-formatter="controls">操作</th>
            </tr>
        </thead>
    </table>
    </tiles:put>

</tiles:insert>
