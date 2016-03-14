<tiles:insert template="/WEB-INF/view/default-parts/layout.jsp" flush="true">

    <tiles:put name="title">トップ | ${siteName }</tiles:put>

    <tiles:put name="css" type="string">
    </tiles:put>

    <tiles:put name="javascript" type="string">
    </tiles:put>

    <tiles:put name="content" type="string">
        <h1>認可エラー</h1>
        <hr>
        使用できない機能です。
    </tiles:put>

</tiles:insert>
