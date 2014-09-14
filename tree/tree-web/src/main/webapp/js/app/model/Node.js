Ext.define('Tree.model.Node', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'value',type:'int'},
        'id'
    ],
    getObject:function(deep){
    	var obj= {
    		value:this.get('value'),
    		id:this.get('id'),
    		leaf:this.get('leaf')
    	};
    	if(deep==true && !Ext.isEmpty(this.parentNode) && !this.parentNode.isRoot()){
    		obj.parent=this.parentNode.getObject(false);
    	}
    	return obj;
    },
    copy:function(){
    	var obj= {
    		value:this.get('value'),
    		leaf:this.get('leaf')
    	};
    	if(!Ext.isEmpty(this.childNodes)){
    		obj.children=[];
    		for(var i =0;i<this.childNodes.length;i++){
    			var node=this.childNodes[i];
    			obj.children.push(node.copy());
    		}
    	}
    	return obj;
    }
});