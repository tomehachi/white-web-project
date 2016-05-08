<%@page import="java.util.ResourceBundle"%>
<tiles:insert template="/WEB-INF/view/default-parts/layout-no-menu.jsp" flush="true">

    <tiles:put name="title" value="認証 | ${siteName }" />

    <tiles:put name="css" type="string">
        <link rel="stylesheet" href="${contextPath }/assets/introjs/introjs.css">
        <style>
        .introjs-helperNumberLayer {
            width: 35px;
            height: 35px;
        }
        </style>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script src="${contextPath }/assets/introjs/intro.js"></script>
        <script>
        var help = function() {
            var intro = introJs();
            intro.setOptions({
                showProgress: true,
                showBullets: false,
                steps: [
                    {intro: "<h3><i class='fa fa-check-square-o'></i> 画面の目的</h3>このシステムにサインインをするための画面です。"},
                    {
                        element: document.querySelector("#email"),
                        intro: "サインインIDを入力します。"
                    },
                    {
                        element: document.querySelector("#password"),
                        intro: "パスワードを入力します。"
                    },
                    {
                        element: document.querySelector("#g-recaptcha"),
                        intro: "あなたがロボットでないことを証明するため、チェックボックスをONにし、場合によっては指示がありますので、それに従って画像などを選択して下さい。<br>毎回異なる内容の操作を求められます。"
                    },
                    {
                        element: document.querySelector("#btn-submit"),
                        intro: "このボタンを押すと、認証を開始します。"
                    },
                    {
                        element: document.querySelector("#forgot"),
                        intro: "パスワードを忘れてしまった場合は、ここをクリックして下さい。<br>※ 1日1回しか使えません。"
                    }
                ]
            });
            intro.start();
        };
        </script>
    </tiles:put>

    <% pageContext.setAttribute("reCaptchaSiteKey", ResourceBundle.getBundle("security").getString("reCAPTCHA.siteKey")); %>

    <tiles:put name="content" type="string">
    <a href="javascript:help();" class="btn btn-default pull-right">ヘルプ</a>
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3><i class="fa fa-sign-in"></i> サインイン</h3>
                </div>
                <div class="panel-body">
                    <form action="${contextPath }/signIn/auth" method="post" autocomplete="off">
                        <html:errors property="org.apache.struts.action.GLOBAL_MESSAGE" />
                        <div class="form-group">
                            <label class="control-label" for="email">サインインID</label>
                            <input class="form-control" id="email" type="text" placeholder="サインインID入力" name="email" value="${email }" autocomplete="off" autofocus="autofocus">
                            <html:errors property="email" />
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="password">パスワード</label>
                            <input class="form-control" id="password" type="password" placeholder="パスワード入力" name="password" value="${password }" autocomplete="off">
                            <html:errors property="password" />
                        </div>
                        <script src='https://www.google.com/recaptcha/api.js'></script>
                        <div class="g-recaptcha" id="g-recaptcha" data-sitekey="${reCaptchaSiteKey }"></div>
                        <div class="form-group">
                            <input class="btn btn-primary" type="submit" id="btn-submit" value="認証">
                            <input class="btn btn-default" type="reset" value="クリア">
                        </div>
                    </form>
                </div>
                <div class="panel-footer">
                    <a href="${contextPath }/forgot" id="forgot"><i class="fa fa-question-circle"></i>&nbsp;パスワードを忘れた</a>
                </div>
            </div>
        </div>
    </div>
    </tiles:put>

</tiles:insert>
