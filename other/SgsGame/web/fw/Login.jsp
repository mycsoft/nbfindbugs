<%-- 
    Document   : Login
    Created on : 2010-3-3, 13:15:43
    Author     : MaYichao
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags/"  prefix="sgs" %>


<%--
<html:html lang="true">
    <head>
<%--
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title><bean:message key="welcome.title"/>(<bean:message key="sys.version"/>)</title>
<html:base/>

<jsp:include page="/fw/Head.jsp"/>
</head>
<body style="background-color: white">

        <logic:notPresent name="org.apache.struts.action.MESSAGE" scope="application">
            <div  style="color: red">
                ERROR:  Application resources not loaded -- check servlet container
                logs for error messages.
            </div>
        </logic:notPresent>

        <h3><bean:message key="welcome.heading"/></h3>
        <p><bean:message key="welcome.message"/></p>
        <>
        <p>当前版本:<bean:message key="sys.version"/></p>
        <p>版权所有:<bean:message key="sys.author"/></p>
    </body>
</html:html>
--%>
<sgs:DefaultView>
    <h3><bean:message key="welcome.heading"/></h3>
    <%--
    <html:form action="Login">
        
    </html:form>
    --%>
    <p><html:link action="Login.do"><bean:message key="welcome.message"/></html:link></p>
</sgs:DefaultView>