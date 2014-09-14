Ext.define('Tree.service.TreeService', {
    singleton:true,
    mode:Tree.service.LocalMode,
    storage:null,
    setMode:function(mode){
    	this.mode=mode;
    },
    addNode:function(tree){
    	var node=this.getSelectedNode(tree);
    	var me=this;
    	node.expand(false,function(){
    		var child=node.appendChild({
    			value:0
    		});
    		me.mode.save(me,child);
    	});
    },
    addLeaf:function(tree){
    	var node=this.getSelectedNode(tree);
    	var sum=this.sumParentNodes(node);
		var me=this;
		node.expand(false,function(){
			var child=node.appendChild({
				value:sum,
				leaf:true
			});
			me.mode.save(me,child);
		});
    },
    deleteNodes:function(tree){
    	var selectedNodes=tree.getSelectionModel().getSelection();
    	Ext.Array.each(selectedNodes,function(node){
    		this.mode.deleteNode(this,node);
    		node.remove();
    	},this)
    },
    sumParentNodes:function(node){
    	if(Ext.isEmpty(node.parentNode)){
    		return node.get('value');
    	}
    	else{
    		return node.get('value')+this.sumParentNodes(node.parentNode);
    	}
    },
    recalculateChildren:function(node,sum){
    	var me=this;
    	if(!node.isLoaded()){
    		node.expand(false,function(){
    			me._recalculateChildren(node,sum);
    		});
    	}else{
    		me._recalculateChildren(node,sum);
    	}
    },
    _recalculateChildren:function(node,sum){
    	for(var i=0;i<node.childNodes.length;i++){
    		var child=node.childNodes[i];
    		if(child.isLeaf()){
    			child.set('value',sum);
    			this.mode.save(this,child);
    		}
    		else{
    			this.recalculateChildren(child,sum+child.get('value'));
    		}
    	}
    },
    readData:function(nodes){
    	var data=[];
    	Ext.Array.each(nodes,function(node){
    		var n={
    				id:node.data.id,
    				value:node.data.value,
    				leaf:node.isLeaf(),
    				children:this.readData(node.childNodes)
    		};
    		data.push(n);
    	},this);
    	return data;
    },
    getSelectedNode:function(tree){
    	var selectedNodes=tree.getSelectionModel().getSelection();
		if(Ext.isEmpty(selectedNodes)){
			return tree.getRootNode();
		}else{
			return selectedNodes[0]
		}
    },
    saveTree:function(tree){
    	var root=tree.getRootNode();
    	for(var i=0;i<root.childNodes.length;i++){
    		this.saveNode(root.childNodes[i]);
    	}
    	this.mode.sync(this);
    },
    saveNode:function(node){
    	var me=this;
		Ext.Ajax.request({
    		url:'rest/nodes',
    		method:'POST',
    		jsonData:node.getObject(true),
    		success:function(resp){
    			var id=Ext.decode(resp.responseText);
    			node.set('id',id);
    			for(var i=0;i<node.childNodes.length;i++){
    	    		me.saveNode(node.childNodes[i]);
    	    	}
    		}
    	});
    },
    moveNode:function(node,parent,oldParent){
    	var parent=node.parentNode;
    	var oldParent=node.oldParent;
    	this.recalculateNode(parent);
    	this.recalculateNode(oldParent);
    	this.mode.updateNode(node);
    	node.oldParent=null;
    },
    updateNode:function(node){
		Ext.Ajax.request({
    		url:'rest/nodes',
    		method:'POST',
    		jsonData:node.getObject(true)
    	});
    },
    deleteNode:function(nodeId){
    	Ext.Ajax.request({
    		url:'rest/nodes/'+nodeId,
    		method:'DELETE'
    	});
    },
    recalculateNode:function(node){
    	var sum=this.sumParentNodes(node);
    	this.recalculateChildren(node,sum);
    	this.mode.save(this,node);
    },
    recalculateLeaf:function(leaf,prevValue){
    	var value=leaf.get('value');
    	var diff=value-prevValue;
    	var parent=leaf.parentNode;
    	if(parent.isRoot())
    		return;
    	parent.set('value',diff+parent.get('value'));
    	this.recalculateNode(parent);
    },
    copy:function(node,tree){
    	this.storage=node.copy();
    },
    paste:function(node){
    	node.appendChild(this.storage);
    	this.storage=null;
    	this.recalculateNode(node);
    }
});