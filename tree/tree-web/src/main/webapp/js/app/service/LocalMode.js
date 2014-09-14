Ext.define('Tree.service.LocalMode', {
    singleton:true,
    toDelete:[],
    save:function(service,node){
    },
    deleteNode:function(service,node){
    	if(!Ext.isEmpty(node.get('id'))){
    		this.toDelete.push(node.get('id'));
    	}
    },
	sync:function(service){
		for(var i=0;i<this.toDelete.length;i++){
			service.deleteNode(this.toDelete[i]);
		}
		this.toDelete=[];
	},
	updateNode:function(service,node){
	}
});