<%-- 
    Document   : Header
    Created on : 2010-3-3, 13:13:26
    Author     : MaYichao
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title><bean:message key="welcome.title"/>(<bean:message key="sys.version"/>)</title>
<html:base/>
<script type="text/javascript" src="<%=request.getContextPath()%>/fw/js/Ajax.js" charset="utf-8"></script>
<%--
<script type="text/javascript" src="js/Ajax.js"/>
--%>
