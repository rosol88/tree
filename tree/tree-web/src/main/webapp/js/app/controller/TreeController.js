Ext.define('Tree.controller.TreeController', {
    extend: 'Ext.app.Controller',
    refs: [
           {
        	   ref:'tree',
        	   selector:'tree'
           },
           {
        	   ref:'saveBtn',
        	   selector:'button[action=save]'
           }
    ],
    treeService:Tree.service.TreeService,
    init: function() {
        this.control({
            'button[action=addNode]': {
                click: this.addNode
            },
            'button[action=addLeaf]': {
                click: this.addLeaf
            },
            'button[action=deleteNode]': {
                click: this.deleteNode
            },
            'button[action=save]': {
                click: this.save
            },
            'button[action=reload]': {
                click: this.reload
            },
            'button[action=autoSave]': {
                toggle: this.autoSave
            },
            'tree':{
            	edit:this.nodeEdit
            },
            'tree dataview':{
            	itemkeydown:this.keyPress,
            	beforedrop:this.beforeNodeDrop,
            	drop:this.nodeDrop
            }
        });
    },
    addNode: function() {
		this.treeService.addNode(this.getTree());
    },
    addLeaf: function() {
    	this.treeService.addLeaf(this.getTree());
    },
    deleteNode:function(){
    	this.treeService.deleteNodes(this.getTree());
    	
    },
    nodeEdit:function(editor,e,opts){
    	var node=e.record;
    	if(node.isLeaf())
    		this.treeService.recalculateLeaf(node,e.originalValue);
    	else
    		this.treeService.recalculateNode(node);
    },
    beforeNodeDrop:function(el,data){
    	var parent=data.records[0].parentNode;
    	data.records[0].oldParent=parent;
    },
    nodeDrop:function(el,data){
    	this.treeService.moveNode(data.records[0]);
    },
    save:function(){
    	this.treeService.saveTree(this.getTree());
    },
    reload:function(){
    	this.getTree().getSelectionModel().deselectAll();
    	this.getTree().getRootNode().removeAll();
    	this.getTree().getStore().load();
    },
    autoSave:function(btn,pressed){
    	if(pressed){
    		this.treeService.saveTree(this.getTree());
    		this.getSaveBtn().setDisabled(true);
    		this.treeService.setMode(Tree.service.RemoteMode);
    	}
    	else{
    		this.getSaveBtn().setDisabled(false);
    		this.treeService.setMode(Tree.service.LocalMode);
    	}
    },
    keyPress:function(tree,record,item,index,e){
    	if(e.ctrlKey && e.getCharCode()==Ext.EventObject.C){
    		this.treeService.copy(record,this.getTree());
    	}
    	else if(e.ctrlKey && e.getCharCode()==Ext.EventObject.V){
    		this.treeService.paste(record,this.getTree());
    	}
    	
    }
});