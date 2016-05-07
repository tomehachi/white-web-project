<!DOCTYPE html>
<html lang="ja">
    <head>
        <tiles:insert template="/WEB-INF/view/default-parts/head.jsp" flush="true">
            <tiles:put name="title"><tiles:getAsString name="title" /></tiles:put>
            <tiles:put name="css" type="string"><tiles:getAsString name="css" /></tiles:put>
        </tiles:insert>
    </head>
    <body>
        <header>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xs-12 header-container">
                    <tiles:insert page="/WEB-INF/view/default-parts/header.jsp" />
                    </div>
                </div>
            </div>
        </header>

        <!-- メニューバー -->
        <tiles:insert page="/WEB-INF/view/default-parts/menu.jsp" />

        <div class="container-fluid content-container">
            <div class="row">
                <div class="col-md-12">
                <tiles:insert attribute="content" />
                </div>
            </div>
        </div>

        <footer>
            <tiles:insert page="/WEB-INF/view/default-parts/footer.jsp" />
        </footer>

        <script src="${contextPath }/assets/jquery/jquery-2.1.4.min.js"></script>
        <script src="${contextPath }/assets/bootstrap/js/bootstrap.min.js"></script>
        <script>
        var userStatusHtml = (function() {/*
            {0}さん、こんにちは<br>
            white-web-project へようこそ！
        */}).toString().match(/(?:\/\*(?:[\s\S]*?)\*\/)/).pop().replace(/^\/\*/, "").replace(/\*\/$/, "").replace(/\r\n/g, "").replace(/^[ ]*/g, "");

        $(function() {
            $.ajax({
                url: "${contextPath}/userStatus",
                type: "GET",
                dataType: "json",
                success: function(data) {
                    $("#user-status").html(
                            userStatusHtml
                                .replace(/\{0\}/, data.result.name)
                    );
                }
            });
        });
        </script>
        <tiles:insert attribute="javascript" />
    </body>
</html>
