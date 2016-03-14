<tiles:insert template="/WEB-INF/view/default-parts/layout-no-menu.jsp" flush="true">

    <tiles:put name="title">パスワードリセット | ${siteName }</tiles:put>

    <tiles:put name="css" type="string">
    </tiles:put>

    <tiles:put name="javascript" type="string">
    </tiles:put>

    <tiles:put name="content" type="string">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="alert alert-info">
                <p>
                    パスワードをお忘れですか？<br>
                    下記の情報を入力し、送信ボタンを押下してください。<br>
                </p>
                <p>
                    システムからあなた宛てにパスワードリセット用のURLをメールでお送りいたします。
                </p>
                <p>
                    <i class="fa fa-info-circle"></i> セキュリティ上の問題で、本画面で誤った情報を入力してもお知らせすることができません。<br>
                    <i class="fa fa-info-circle"></i> 本機能は1日に1回しかご利用できません。
                </p>
            </div>
            <html:errors property="org.apache.struts.action.GLOBAL_MESSAGE" />
            <form action="${contextPath }/forgot/sendPasswordResetMail" method="POST" autocomplete="off">
                <fieldset>
                    <div class="form-group">
                        <label for="email" class="form-label">ご登録メールアドレス</label><br>
                        <input type="text" id="email" class="form-control" name="email" value="${sendPasswordResetMailForm.email }" placeholder="メールアドレス">
                        <html:errors property="email" />
                    </div>
                    <script src='https://www.google.com/recaptcha/api.js'></script>
                    <div class="g-recaptcha" data-sitekey="6Ld4WhkTAAAAAPwtLgvaMZxINi5KMjVU22b_lAAu"></div>
                    <input type="submit" class="btn btn-primary" value="送信する">
                    <input type="reset" class="btn btn-default" value="リセット">
                </fieldset>
            </form>
        </div>
    </div>
    </tiles:put>

</tiles:insert>
