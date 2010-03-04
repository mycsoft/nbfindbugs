<%-- 
    Document   : SgsMain
    Created on : 2010-3-3, 15:45:04
    Author     : MaYichao
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags/"  prefix="sgs" %>

<sgs:DefaultView>

    <p align="center">
        <applet archive="SgsGameApplet.jar" code="com.myc.game.sgs.applet.SgsAppletMain.class" width='800' height='600'>
	alt="Your browser understands the &lt;APPLET&gt; tag but isn't running the applet, for some reason."
	Your browser is completely ignoring the &lt;APPLET&gt; tag!
      </applet>
</p>
</sgs:DefaultView>