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
                    あなたの登録メールアドレス宛てに、パスワードリセットメールを送信いたしました。<br>
                </p>
                <p>
                    <i class="fa fa-info-circle"></i> セキュリティ上の問題で、本画面で誤った情報を入力してもお知らせすることができません。<br>
                    <i class="fa fa-info-circle"></i> 本機能は1日に1回しかご利用できません。
                </p>
            </div>
            <a href="${contextPath }/" class="btn btn-primary">認証画面へ</a>
        </div>
    </div>
    </tiles:put>

</tiles:insert>
