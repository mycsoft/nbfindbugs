/**
 * log
 */
function log(message) {
    debugDiv = document.getElementById("debug_message")
    //alert(debugDiv)
    if (debugDiv == null) {
        //alert(message);
    }else{
        debugDiv.innerHTML+='<br>[调试]:'+message;
    }
}

/* 
 * Ajax核心框架
 */
Ajax = {};

/*
     *检查XHR对象的就绪状态.
     */
Ajax.checkReadyState=function(_id){
    switch(this.request.readyState){
        default:
            break;

        case 4:
            AjaxUpdater.isUpdating = false;
            return this.request.status;
    }
}

/*
     * 发送请求
     */
Ajax.makeRequest=function(method,url,callbackMethod) {
    this.request=(window.XMLHttpRequest)?
    new XMLHttpRequest():
    new ActiveXObject("MSXML2.XMLHTTP");
    this.request.onreadystatechange=callbackMethod;
    this.request.open(method,url,true);
    this.request.send(url);
}

/**
     * 获得正常的响应
     */
Ajax.getResponse=function() {
    if(this.request.getResponseHeader('Content-Type').indexOf('xml') != -1){
        return this.request.responseXML.documentElement;
    }else{
        return this.request.responseText;
    }
}

/**
 * Ajax请求对象
 */
function AjaxRequest(method,url,callbackMethod) {
    this.method = method;
    this.url = url;
    this.callbackMethod = callbackMethod;
}

/**
 * 队列
 */
function Queue() {
    this.list = new A;
    this.put = function(p){
        this.list.a
    }
}
var ajaxList = new Array
    
//};

//Ajax = new AjaxTemplate();

/**
 * Ajax更新管理器
 */
AjaxUpdater={};
AjaxUpdater.isUpdating = false;
/**
     * 初始化
     */
AjaxUpdater.initialize = function() {
    log("初始化");
    this.isUpdating = false;
}

/**
     * 更新
     */
AjaxUpdater.update=function(method,service,callback) {
    //如果当前有Ajax请求正在进行中,则将此请求加入队列.
    if (this.isUpdating) {
        
    }else{
        if (callback == undefined || callback == "") {
            callback = this.onResponse;
        }
        onResponse=function() {
            if (Ajax.checkReadyState('loading') == 200) {
                this.isUpdating = false;
                callback();
            }
        }
        Ajax.makeRequest(method,service,onResponse);
        this.isUpdating = true;
    }
}

/**
     * onResponse
     */
AjaxUpdater.onResponse=function(callback) {
    }

//au = new AjaxUpdater();
AjaxUpdater.initialize();