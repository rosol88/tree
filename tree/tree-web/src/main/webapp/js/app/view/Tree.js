Ext.define('Tree.view.Tree', {
    extend: 'Ext.tree.Panel',
    alias:'widget.tree',
    plugins: 'cellediting',
    hideHeaders:true,
    rootVisible:false,
    viewConfig: {
	    markDirty:false,
	    plugins: {
            ptype: 'treeviewdragdrop'
        }
    },
    columns: [{
        xtype: 'treecolumn',
        flex:1,
        dataIndex: 'value',
        editor: {
            xtype: 'numberfield',
            allowBlank: false
        }
    }],
    tbar:[
          {
        	  text:'Add node',
        	  xtype:'button',
        	  action:'addNode'
          },'-',
          {
        	  text:'Add leaf',
        	  xtype:'button',
        	  action:'addLeaf'
          },'-',
          {
        	  text:'Save',
        	  xtype:'button',
        	  action:'save'
          },'-',
          {
        	  text:'AutoSave',
        	  xtype:'button',
        	  enableToggle:true,
        	  action:'autoSave'
          },'-',
          {
        	  text:'Delete',
        	  xtype:'button',
        	  action:'deleteNode'
          },'-',
          {
        	  text:'Reload',
        	  xtype:'button',
        	  action:'reload'
          }
    ],
    store: Ext.create('Tree.store.TreeStore')
});