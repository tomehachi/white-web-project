<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@taglib prefix="s" uri="http://sastruts.seasar.org" %>
<%@taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>
<%@taglib prefix="com" uri="/WEB-INF/common.tld" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="siteName" value="tomehachi.net" />

<%-- ユーザ情報をページコンテキストに追加 --%>
<% pageContext.setAttribute("userDataDto", request.getSession().getAttribute("userDataDto")); %>
