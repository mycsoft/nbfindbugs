<%-- 
    Document   : TestHead
    Created on : 2010-3-7, 16:05:34
    Author     : MaYichao
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags/"  prefix="sgs" %>

<sgs:DefaultView>
    <script>
        /**
         * test
         */
        function test() {
            log(111);
            AjaxUpdater.update("GET","sample.jsp",callback);
            log(222);

        }

        /**
         * callback
         */
        function callback() {
            //if (Ajax.checkReadyState('loading') == 200) {
                //this.isUpdating = false;
                document.getElementById("testDiv").innerHTML = Ajax.getResponse();
            //}
     
        }
    </script>
    <div id="testDiv1">
        <input type="button" value="TEST" onclick="test()"/>
        <div id="testDiv"></div>
    </div>
    

</sgs:DefaultView>
