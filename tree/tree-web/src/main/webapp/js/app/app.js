Ext.Loader.setConfig({
	enabled : false
});
Ext.application({
    name: 'Tree',
	controllers:['TreeController'],
	models:['Node'],
    appFolder: 'app',
    launch: function() {
       Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [
                {
                    xtype: 'panel',
                    title: 'Tree',
                    layout: 'fit',
                    items:[
						{
							xtype:'tree'
						}
					]
                }
            ]
        });
    }
});