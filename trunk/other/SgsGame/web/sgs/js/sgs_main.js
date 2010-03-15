/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Sgs = {};
//Sgs.PlayerPanel = new Ext.Panel({
//    title: 'My Panel',
//    collapsible:true,
//    renderTo: 'panel-basic',
//    width:400,
//    html: Ext.example.bogusMarkup
//});
/**
 * init
 */
function init(userId) {
    Ext.onReady(function(){

        //======= 工具栏 ===========
        Ext.QuickTips.init();
        var tb = new Ext.Toolbar();
        tb.render('toolbar');

        tb.add(new Ext.Action({
            text: '退出房间',
            handler: function(){
                //提示是否确认退出.
                Ext.MessageBox.confirm('退出房间', '是否要退出房间?', function(btn,text){
                    
                    if (btn == 'yes') {
                        location = 'exitRoom.do';
                        //Ext.MessageBox.alert("ok", 'ok');
                    }
                })
            },
            tooltip: '<b>退出房间</b><br/>退出后将无法再次参与本次游戏'
        }));

        //var ps = new Array();
        //for (i = 0; i < 10; i++) {

        //}

        //Sgs.playerPanels = ps;

        //显示主画面
        var html = '';
        Ext.Ajax.request({
            url : "MainBox.jsp",
            method:'GET',
            callback : function(options, success, response) {
                //reflushPlayer(idx, response.responseText);
                html = response.responseText;
                Sgs.mainPanel = new Ext.Panel({
                    title: '三国杀',
                    collapsible:false,
                    renderTo: 'panel-basic',
                    width:1000,
                    html: html
                });

                loadHandDiv();
                updateAllPlayers(userId);
            }
        })
    });
}


/**
 * 加载手牌区
 */
function loadHandDiv() {
    //log("更新手牌"+ userId);
    var store = new Ext.data.Store({
        // load using HTTP
        url: 'HandBox.jsp',

        // the return will be XML, so lets set up a reader
        reader: new Ext.data.XmlReader({
            // records will have an "Item" tag
            record: 'Card'
        }
        , [
        // set up the fields mapping into the xml doc
        // The first needs mapping, the others are very basic
        {
            name:'name' ,
            mapping:'@name'
        }
        ]
        )
    });

    // create the grid
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
        {
            header: "名称",
            //width: '100%',
            dataIndex: 'name',
            sortable: true
        }
        //{header: "Title", width: 180, dataIndex: 'Title', sortable: true},
        //{header: "Manufacturer", width: 115, dataIndex: 'Manufacturer', sortable: true},
        //{header: "Product Group", width: 100, dataIndex: 'ProductGroup', sortable: true}
        ],
        renderTo:'handCardList_Div',
        width:'30%',
        height:'100%'
    });

    store.load();
}
/**
 * 更新玩家信息
 * @param index 玩家的相对位置号.
 * @param idx 玩家的相对当前玩家的位置号.
 */
function updatePlayer(idx,index) {
    log("更新玩家信息"+ idx + "|" + index);
    Ext.Ajax.request({
        url : "PlayerBox.jsp?id="+index,
        callback : function(options, success, response) {
            reflushPlayer(idx, response.responseText);
        }
    })

}

/**
 * 刷新玩家区
 */
function reflushPlayer(index,content) {
    log("刷新玩家区"+index);
    var div = "player_div_"+index;
    document.getElementById(div).innerHTML=content;
}
/**
 * 更新所有玩家
 */
function updateAllPlayers(userId) {
    var max = 4;
    for (var i = 0;i < max ; i ++){
        updatePlayer(i,(i+userId)%(max));
    }
}

/**
 * 刷新手牌区
 */
function updateHand(userId) {
    log("更新手牌"+ userId);
    var store = new Ext.data.Store({
        // load using HTTP
        url: 'HandBox.jsp',

        // the return will be XML, so lets set up a reader
        reader: new Ext.data.XmlReader({
            // records will have an "Item" tag
            record: 'Card'
        }
        , [
        // set up the fields mapping into the xml doc
        // The first needs mapping, the others are very basic
        {
            name:'name' ,
            mapping:'@name'
        }
        ]
        )
    });

    // create the grid
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
        {
            header: "名称",
            width: 120,
            dataIndex: 'name',
            sortable: true
        }
        //{header: "Title", width: 180, dataIndex: 'Title', sortable: true},
        //{header: "Manufacturer", width: 115, dataIndex: 'Manufacturer', sortable: true},
        //{header: "Product Group", width: 100, dataIndex: 'ProductGroup', sortable: true}
        ],
        renderTo:'handCardList_Div',
        width:'30%',
        height:'100%'
    });

    store.load();
/*
            Ext.Ajax.request({
                url : "HandBox.jsp",
                callback : function(options, success, response) {
                    doc = response.responseXML.documentElement;
                    var cards = doc.getElementsByTagName("Card");
                    for (i = 0; i < cards.length; i++) {

                    }


                    //grid.render('handCardList_Div');

                    //grid.getSelectionModel().selectFirstRow();
                }
            })*/
}



//updateHand();