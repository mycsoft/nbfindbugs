<%-- 
    Document   : TestExt
    Created on : 2010-3-8, 16:41:31
    Author     : MaYichao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
    <body>
        <div id="loading-mask" style=""></div>
        <div id="loading">
            <div class="loading-indicator"><img src="../fw/scripts/ext/examples/shared/extjs/images/extanim32.gif" width="32" height="32" style="margin-right:8px;float:left;vertical-align:top;"/>Ext 2.2 - <a href="http://extjs.com">extjs.com</a><br /><span id="loading-msg">Loading styles and images...</span></div>
        </div>
        <script type="text/javascript">document.getElementById('loading-msg').innerHTML = 'Loading Core API...';</script>
        <script type="text/javascript" src="../fw/scripts/ext/adapter/ext/ext-base.js"></script>
        <script type="text/javascript">document.getElementById('loading-msg').innerHTML = 'Loading UI Components...';</script>
        <script type="text/javascript" src="../fw/scripts/ext/ext-core.js"></script>
        <script type="text/javascript" src="../fw/scripts/ext/ext-all.js"></script>
        <script type="text/javascript">document.getElementById('loading-msg').innerHTML = 'Initializing...';</script>

        <!--script type="text/javascript" src="../fw/scripts/ext/examples/shared/extjs/site.js"></script-->
        <div id="testDiv">
            <h1>111111</h1>
            <input type="button" value="Test" onclick="test()"/>
        </div>

        <script>
            Ext.get('loading').remove();
            Ext.get('loading-mask').fadeOut({remove:true});

            /**
             * test
             */
            function test() {


                Ext.Ajax.request({
                    url: 'sample.jsp',
                    method: 'post',
                    params: {testname: 'test1234', testvalue: 'test4321'},
                    callback: function(options, success, response) {
                        //Ext.get("testDiv").innerHTML = response.innerHTML;
                        document.getElementById("testDiv").innerHTML = response.responseText;
                        Ext.MessageBox.alert("测试", response);
                    }
                });
            }
        </script>
    </body>
</html>
