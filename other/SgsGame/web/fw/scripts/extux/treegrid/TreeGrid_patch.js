
Ext.override(Ext.ux.maximgb.treegrid.GridView,{
   // private
	onAdd : function(ds, records, index){
		this.insertRows(ds, index, index + (records.length-1));
 	}
});