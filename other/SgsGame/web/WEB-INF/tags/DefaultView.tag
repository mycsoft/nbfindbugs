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
        <style type="text/css">
            #all-demos {

            }
            #all-demos dd {
                float:left;
                width:300px;
                height:100px;
                margin:5px 5px 5px 10px;
                cursor:pointer;
                zoom:1;
            }
            #all-demos dd img {
                width:120px;
                height:90px;
                margin:5px 0 0 5px;
                float:left;
            }

            #all-demos dd div {
                float:left;
                width:160px;
                margin-left:10px;
            }

            #all-demos dd h4 {
                font-family:tahoma,arial,san-serif;
                color:#555;
                font-size:11px;
                font-weight:bold;
            }
            #all-demos dd p {
                color:#777;
            }
            #all-demos dd.over {
                background: #F5FDE3 url(shared/extjs/images/sample-over.gif) no-repeat;
            }
            #loading-mask{
                position:absolute;
                left:0;
                top:0;
                width:100%;
                height:100%;
                z-index:20000;
                background-color:white;
            }
            #loading{
                position:absolute;
                left:45%;
                top:40%;
                padding:2px;
                z-index:20001;
                height:auto;
            }
            #loading a {
                color:#225588;
            }
            #loading .loading-indicator{
                background:white;
                color:#444;
                font:bold 13px tahoma,arial,helvetica;
                padding:10px;
                margin:0;
                height:auto;
            }
            #loading-msg {
                font: normal 10px arial,tahoma,sans-serif;
            }

            #all-demos .x-panel-body {
                background-color:#fff;
                border:1px solid;
                border-color:#fafafa #fafafa #fafafa #fafafa;
            }
            #sample-ct {
                border:1px solid;
                border-color:#dadada #ebebeb #ebebeb #dadada;
                padding:2px;
            }

            #all-demos h2 {
                border-bottom: 2px solid #99bbe8;
                cursor:pointer;
                padding-top:6px;
            }
            #all-demos h2 div {
                background:transparent url(../resources/images/default/grid/group-expand-sprite.gif) no-repeat 3px -47px;
                padding:4px 4px 4px 17px;
                color:#3764a0;
                font:bold 11px tahoma, arial, helvetica, sans-serif;
            }
            #all-demos .collapsed h2 div {
                background-position: 3px 3px;
            }
            #all-demos .collapsed dl {
                display:none;
            }
            .x-window {
                text-align:left;
            }
        </style>
        <link rel="stylesheet" type="text/css" href="../fw/scripts/ext/resources/css/ext-all.css" />
    </head>
    <body style="background-color: white">
        <div id="loading-mask" style=""></div>
        <div id="loading">
            <div class="loading-indicator"><img src="../fw/scripts/ext/examples/shared/extjs/images/extanim32.gif" width="32" height="32" style="margin-right:8px;float:left;vertical-align:top;"/>Ext 2.2 - <a href="http://extjs.com">extjs.com</a><br /><span id="loading-msg">Loading styles and images...</span></div>
        </div>
        <script type="text/javascript">document.getElementById('loading-msg').innerHTML = '载入内核...';</script>
        <script type="text/javascript" src="../fw/scripts/ext/adapter/ext/ext-base.js"></script>
        <script type="text/javascript">document.getElementById('loading-msg').innerHTML = '载入画面组件...';</script>
        <script type="text/javascript" src="../fw/scripts/ext/ext-core.js"></script>
        <script type="text/javascript" src="../fw/scripts/ext/ext-all.js"></script>
        <script type="text/javascript">document.getElementById('loading-msg').innerHTML = '初始化...';</script>


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
                        <div id="loading"></div>

                    </td>
                </tr>
                <tr>
                    <td height="300" align="center">
                        <jsp:doBody/>
                        <div id="debug_message" style="color: red"></div>
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
    <script>
        //关闭载入画面.
        Ext.get('loading').remove();
        Ext.get('loading-mask').fadeOut({remove:true});
    </script>
</html:html>
