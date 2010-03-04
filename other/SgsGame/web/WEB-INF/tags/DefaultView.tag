<%-- 
    Document   : DefaultView
    Created on : 2010-3-3, 13:20:56
    Author     : MaYichao
--%>

<%@tag description="put the tag description here" pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:html lang="true">
    <head>
        <%--
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title><bean:message key="welcome.title"/>(<bean:message key="sys.version"/>)</title>
        <html:base/>
        --%>
        <jsp:include page="/fw/Head.jsp"/>
    </head>
    <body style="background-color: white">


        <table border="0" width="100%">
            <thead>
                <tr>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <logic:notPresent name="org.apache.struts.action.MESSAGE" scope="application">
                            <div  style="color: red">
                                ERROR:  Application resources not loaded -- check servlet container
                                logs for error messages.
                            </div>
                        </logic:notPresent>
                    </td>
                </tr>
                <tr>
                    <td height="300" align="center">
                        <jsp:doBody/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p align="center"><html:link action="Welcome.do">回首页</html:link> </p>
                        <p align="center">当前版本:<bean:message key="sys.version"/>, 版权所有:<bean:message key="sys.author"/></p>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html:html>
