/* 
 * Ajax核心框架
 */
function AjaxTemplate() {

    /*
     * 发送请求
     */
    function makeRequest(method,url,callbackMethod) {
        this.request=(window.XMLHttpRequest)?
            new XMLHttpRequest():
            new ActiveXObject("MSXML2.XMLHTTP");
        this.request.onreadystatechange=callbackMethod;
        this.request.open(method,url,true);
        this.request.send(url);
    }
};

Ajax = new AjaxTemplate();