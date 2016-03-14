<%@page import="net.tomehachi.web.util.SecurityUtil"%>
<%@page import="java.util.ResourceBundle"%>
<tiles:insert template="/WEB-INF/view/default-parts/layout.jsp" flush="true">

    <tiles:put name="title">トップ | ${siteName }</tiles:put>

    <tiles:put name="css" type="string">
    <link rel="stylesheet" href="${contextPath }/assets/bootstrap-table/bootstrap-table.css">
    <style>
    .label-symbols {
        color: inherit;
        background-color: inherit;
        font-size: 14px;
        margin-bottom: 0px;
    }
    .check-ok {
        background-color: #eef;
        color: #55f;
    }
    .check-ng {
        color: #f00;
        font-weight: bold;
    }
    </style>
    </tiles:put>

    <tiles:put name="javascript" type="string">
    <script src="${contextPath}/assets/jquery/jquery.pwdMeasure.min.js"></script>
    <script src="${contextPath}/assets/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${contextPath}/assets/bootstrap-table/locale/bootstrap-table-ja-JP.min.js"></script>
    <script>
    var setFlag = function(evaluation, selector) {
        if(evaluation) {
            $("." + selector).addClass("check-ok");
            $("." + selector).removeClass("check-ng");
            $("." + selector + "-mark").removeClass("fa-times");
            $("." + selector + "-mark").addClass("fa-check");
            return 1;
        } else {
            $("." + selector).addClass("check-ng");
            $("." + selector).removeClass("check-ok");
            $("." + selector).removeClass("active");
            $("." + selector + "-mark").removeClass("fa-check");
            $("." + selector + "-mark").addClass("fa-times");
            return 0;
        }
    };
    var checkNewPassword = function(dom) {
        // チェックスコア
        var score = 0;
        // 長さチェック
        score += setFlag($(dom).val().length >= <%= ResourceBundle.getBundle("security").getString("password.policy.length") %>, "flag-length");
        // 小文字含有チェック
        score += setFlag($(dom).val().match(/[a-z]/), "flag-lower");
        // 大文字含有チェック
        score += setFlag($(dom).val().match(/[A-Z]/), "flag-upper");
        // 数字含有チェック
        score += setFlag($(dom).val().match(/[0-9]/), "flag-number");
        // 記号含有チェック
        score += setFlag($(dom).val().match(/<%= SecurityUtil.getPasswordSymbolRegexp() %>/), "flag-symbol");
        return score;
    };
    $(function() {
        // 新しいパスワード入力イベント
        $("#newPassword1").keyup(function() {
            if(checkNewPassword(this) < 5) {
                $("#newPassword2").attr("disabled", "disabled");
            } else {
                $("#newPassword2").removeAttr("disabled");
            }
        });
        // 新しいパスワード入力イベント
        $("#newPassword2").keyup(function() {
            if($("#newPassword1").val() == $("#newPassword2").val()) {
                $(".btn-submit").removeAttr("disabled");
                $(".flag-equals").addClass("check-ok");
                $(".flag-equals").removeClass("check-ng");
                $(".flag-equals-mark").removeClass("fa-times");
                $(".flag-equals-mark").addClass("fa-check");

            } else {
                $(".btn-submit").attr("disabled", "disabled");
                $(".flag-equals").addClass("check-ng");
                $(".flag-equals").removeClass("check-ok");
                $(".flag-equals-mark").removeClass("fa-check");
                $(".flag-equals-mark").addClass("fa-times");
            }
        });
    });
    </script>
    </tiles:put>

    <tiles:put name="content" type="string">
    <span class="label label-danger">only you</span>
    <h2 class="page-title"><i class="fa fa-wrench"></i>&nbsp;パスワード変更</h2>

    <form action="${contextPath }/passwd/done" method="post" autocomplete="off">
        <div class="row">
            <div class="col-md-6">
                <html:errors property="org.apache.struts.action.GLOBAL_MESSAGE" />
                <html:errors property="key" />
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="newPassword1" class="form-label"><span class="label label-warning">必須</span> 新しいパスワード</label><br>
                    <input type="password" id="newPassword1" class="form-control" name="newPassword1" value="${passwdForm.newPassword1 }" placeholder="新しいパスワード">
                    <html:errors property="newPassword1" />
                </div>
                <div class="form-group">
                    <label for="newPassword2" class="form-label"><span class="label label-warning">必須</span> 新しいパスワード（確認用）</label><br>
                    <input type="password" id="newPassword2" class="form-control" name="newPassword2" value="${passwdForm.newPassword2 }" placeholder="新しいパスワード (確認用)" disabled="disabled">
                    <html:errors property="newPassword2" />
                </div>
                <div class="form-group">
                    <label class="form-label">新しいパスワードの強度</label><br>
                    ※ 以下の条件をすべて満たしたパスワードのみが許容されます。
                    <ul class="list-group">
                        <li class="list-group-item flag-length"><i class="fa fa-times flag-length-mark"></i> 長さ: 8文字以上</li>
                        <li class="list-group-item flag-lower" ><i class="fa fa-times flag-lower-mark"></i> アルファベット小文字を1文字以上含む</li>
                        <li class="list-group-item flag-upper" ><i class="fa fa-times flag-upper-mark"></i> アルファベット大文字を1文字以上含む</li>
                        <li class="list-group-item flag-number"><i class="fa fa-times flag-number-mark"></i> 数字を1文字以上含む</li>
                        <li class="list-group-item flag-symbol"><i class="fa fa-times flag-symbol-mark"></i> 記号を1文字以上含む<br><pre class="label-symbols"><%= SecurityUtil.getPasswordSymbols() %></pre></li>
                        <li class="list-group-item flag-equals"><i class="fa fa-times flag-equals-mark"></i> 確認用の完全一致</li>
                    </ul>
                </div>
                <input type="submit" class="btn btn-primary btn-submit" value="登録確認" disabled="disabled">
            </div>

            <div class="col-md-6">
            </div>
        </div>
    </form>

    </tiles:put>

</tiles:insert>
