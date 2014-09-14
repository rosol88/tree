Ext.define('Tree.service.RemoteMode', {
    singleton:true,
    save:function(service,node){
    	service.saveNode(node);
    },
	deleteNode:function(service,node){
		service.deleteNode(node.get('id'));
	},
	sync:function(){
		
	},
	updateNode:function(service,node){
		service.updateNode(node);
	}
});