/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

	Ext.BLANK_IMAGE_URL = 'global/themes/default/styles/images/default/s.gif';

    Ext.onReady(function(){

       Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

       var viewport = new Ext.Viewport({
            layout:'border',
            items:[
                new Ext.BoxComponent({ 
                    region:'north',
                    el: 'top',
                    height:95
                }),{
                region:'west',
                id:'west-panel',
                title:'功能导航',
                split:true,
                width: 200,
                minSize: 175,
                maxSize: 400,
                collapsible: true,
                margins:'0 -2 5 0',
                cmargins:'0 3 3 5',
                layout:'accordion',
                layoutConfig:{
                    animate:true
                },
                items: [{
                    contentEl: 'busi-proc-tree',
                    title:'业务处理',
                    autoScroll:false,
                    border:false,
                    iconCls:'nav'
                },{
                    title:'系统管理',
				    contentEl: 'sys-manage',
                    border:false,
                    autoScroll:false,
                    iconCls:'settings'
                },
				{
                    title:'自动平台',
                    contentEl: 'self-help',
                    border:false,
                    autoScroll:false,
                    iconCls:'settings'
                }]
            },{
                region:'center',
                margins:'0 0 3 0',
                border:false,
                autoScroll:false,
				layout:'fit',
                items:[{
                    baseCls:'x-plain',
                    bodyStyle:'padding:0px',
					layout:'fit',
                    items:{
					   border:false,
                       contentEl: 'workflow'
                       
                    }
                }]
            }]
        });
    });
