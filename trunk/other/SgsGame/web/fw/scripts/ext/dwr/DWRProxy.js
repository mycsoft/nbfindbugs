Ext.data.DWRProxy = function(dwrFunction){
    Ext.data.HttpProxy.superclass.constructor.call(this);

    this.dwrFunction = dwrFunction;
};

Ext.extend(Ext.data.DWRProxy, Ext.data.DataProxy, {

    load : function(params, reader, callback, scope, arg){
        if(this.fireEvent("beforeload", this, params) !== false){
            this.dwrFunction.apply(null, arguments[0, -1], arguments[arguments.length-1]);
        }
    }

});