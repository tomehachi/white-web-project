<tiles:insert template="/WEB-INF/view/default-parts/layout-no-menu.jsp" flush="true">

    <tiles:put name="title" value="パスワード変更認証 | ${siteName }" />

    <tiles:put name="css" type="string">
    </tiles:put>

    <tiles:put name="javascript" type="string">
    </tiles:put>

    <tiles:put name="content" type="string">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="alert alert-info">
                メールでお知らせしたパスワード変更 認証キーを入力して下さい。
            </div>
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3><i class="fa fa-sign-in"></i> パスワード変更認証</h3>
                </div>
                <div class="panel-body">
                    <form action="${contextPath }/passwdAuth/auth" method="post" autocomplete="off">
                        <html:errors property="org.apache.struts.action.GLOBAL_MESSAGE" />
                        <div class="form-group">
                            <label class="control-label" for="key">パスワード</label>
                            <input class="form-control" id="key" type="password" placeholder="認証キー" name="key" value="${passwdAuthForm.key }" autocomplete="off">
                            <html:errors property="key" />
                        </div>
                        <script src='https://www.google.com/recaptcha/api.js'></script>
                        <div class="g-recaptcha" data-sitekey="6Ld4WhkTAAAAAPwtLgvaMZxINi5KMjVU22b_lAAu"></div>
                        <div class="form-group">
                            <input class="btn btn-primary" type="submit" value="認証">
                            <input class="btn btn-default" type="reset" value="クリア">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </tiles:put>

</tiles:insert>
