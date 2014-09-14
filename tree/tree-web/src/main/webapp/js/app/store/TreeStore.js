Ext.define('Tree.store.TreeStore', {
    extend: 'Ext.data.TreeStore',
    model:'Tree.model.Node',
	proxy: {
        type: 'rest',
        url : 'rest/nodes'
    },
    root: {
        id: 'root',
        expanded: true
    }
});