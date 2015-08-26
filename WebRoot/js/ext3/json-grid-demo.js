var baseURL;

function initGrid(basePath){
	baseURL = basePath;
	
	var url=basePath+"report/baseReport.do?method=initPage";
	var sm=new Ext.grid.CheckboxSelectionModel();
	var column = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:"序号",dataIndex:"id",width:40,sortable:true,editor: new Ext.form.NumberField({
	   								allowBlank: false,
	   								allowNegative: false,
	   							maxValue: 120})	
	   	},
		{header:"姓名",dataIndex:"s_userName",
				sortable:true,editor: new Ext.form.TextField({
	           					allowBlank: false})
	    },
		{header:"年龄",dataIndex:"s_age",
			sortable:true,editor: new Ext.form.NumberField({
	   								allowBlank: false,
	   								allowNegative: false,
	   							maxValue: 120})	
	   	},
		{header:"姓别",dataIndex:"s_sex",sortable:true,editor: new Ext.form.TextField({
	           					allowBlank: false})
	    },
		{header:"日期",dataIndex:"s_borthday",sortable:true	}, 
		{header:"城市",dataIndex:"s_city",sortable:true,editor: new Ext.form.TextField({
	           					allowBlank: false})
	    }
	]);
	
//	var store = new Ext.data.JsonStore({
//		url:url,
//		root:"datas",
//		totalProperty:"totalRows",
//		fields:[
//			{name:"id"},
//			{name:"s_userName"},
//			{name:"s_age"},
//			{name:"s_sex"},
//			{name:"s_borthday"},
//			{name:"s_city"}
//		]
//	});
	
	 var userInfo = Ext.data.Record.create([
	   {name: 'id', type: 'int'},
	   {name: 's_userName', type: 'string'},
	   {name: 's_age',type: 'int'},
	   {name: 's_sex', type: 'string'},
	   {name: 's_borthday', type: 'string'}, // automatic date conversions
	   {name: 's_city',type: 'string'}
	   
	]);
	//分组数据
	 var store = new Ext.data.GroupingStore({
	 	reader:new Ext.data.JsonReader({totalProperty:"totalRows",
				root:"datas",
				id:"id"
			},userInfo),
	 	proxy:new Ext.data.HttpProxy({url:url}),
//		data:"datas",
		groupField:"s_sex",
		sortInfo:{field:"id",direction:"ASC"},
		fields:[
			{name:"id"},
			{name:"s_userName"},
			{name:"s_age"},
			{name:"s_sex"},
			{name:"s_borthday"},
			{name:"s_city"}
		]
	 });
	var grid = new Ext.grid.EditorGridPanel({
		id:"userInfoGrid",
		title:"用户信息",
		renderTo:"json-grid_two",
		store:store,
		sm:sm,
		cm:column,
		width:800,
		height:400,
		tools:[{id:"help"},{id:"minimize",handler:function(){grid.setHeight(10);}},{id:"maximize"}],
		view:new Ext.grid.GroupingView({
			forceFit:true,columnsText:"显示的列",
			sortAscText:'升序',
			sortDescText:'降序'
		}),//分组视图
		loadMask:true,//显示加载.....
//		viewConfig:{forceFit:true},
		tbar:[
			{xtype:"tbseparator"},
			{passed:true,text:"新增",buttonAlign:"right",handler:function(){
				//新建一条默认的记录
				var initValue ={
	                id: '0',
	                s_userName: 'Mostly Shade',
	                s_age: 26,
	                s_sex: '男',
	                s_borthday:'',
	                s_city:'广州'
	            };
				 var user_info = new userInfo(initValue);
				
				var gridObj=Ext.getCmp('userInfoGrid');//得到grid对象
				
				gridObj.stopEditing();//禁止对表格修改
				store.insert(0,user_info);//向表格中的第一行加一条数据
				gridObj.startEditing(0,0);//编辑第一行的第一列
				
				user_info.dirty = true;
				user_info.modified = initValue;
				if(store.modified.indexOf(user_info)==-1){
					store.modified.push(user_info);
				}
			}},
			{xtype:"tbseparator"},
			{passed:true,text:"保存" ,id:"saveUser",handler:function(){
				var m = store.modified.slice(0);//得到所有被修改的数据
				var jsonArray = [];
				for(var i=0;i<m.length;i++){//对记录进行迭代
					var record = m[i];
					var fields = record.fields.keys;//得到记录中的属性名集合
					for(var j=0;j<fields.length;j++){
						var field = fields[j];
						var value = record.data[field];
						Ext.MessageBox.alert("提示",field+"="+value);
					}
					jsonArray.push(record.data);
				}
				Ext.MessageBox.alert("提示",Ext.encode(jsonArray));
				Ext.Ajax.request({
					url:baseURL+"/grid/userInfo_updateUserInfo.action",
					success:function(){
						
					},
					failure:function(){
						
					},
					method:"post",
					params:{user:Ext.encode(jsonArray)}
				});
			}},
			{xtype:"tbseparator"},
			{passed:true,text:"修改",id:"editButton",handler:editUser},
			{xtype:"tbseparator"},
			{passed:true,text:"删除",handler:deleteUser}
		],
		bbar: new Ext.PagingToolbar({
	        pageSize: 15,
	        store:store,
	        displayInfo: true,
	        displayMsg: '当前记录为{0}-{1}条，共{2}条数据',
	        emptyMsg: "没有记录"
		})
	});
	grid.addListener("click",handlerSelect);
	grid.render();
	store.load({params:{limit:15,start:0}});
	grid.getSelectionModel().selectFirstRow();
}
function handlerSelect(e){
	var gridObj=Ext.getCmp('userInfoGrid');//得到grid对象
	var rows = gridObj.getSelectionModel().getSelections(); //得到选中的行
	if(rows.length>1){
		Ext.getCmp("editButton").setDisabled(true);
	}else{
		Ext.getCmp("editButton").setDisabled(false);
	}
}
function editUser(){
	var form = getUserForm();
	
	var gridObj= Ext.getCmp('userInfoGrid');//得到grid对象
	var store = gridObj.getStore();
	var rows = gridObj.getSelectionModel().getSelections(); //得到选中的行
	var params ="["+Ext.util.JSON.encode(rows[0].data)+"]";
	form.getForm().setValues(rows[0].data);
	if(rows.length==1){
		var editWindow = new Ext.Window({
			title:'修改用户信息',
			id:'editUserInfo',
			width:300,
			autoHeight:true,
			items:[form],
			buttons:[{
				text:"修改",
				handler:function(){
					Ext.Ajax.request({
						url:baseURL+"/grid/userInfo_updateUserInfo.action",
						success:function(){
							Ext.MessageBox.alert('提示',"数据修改成功！");
							editWindow.close();
							store.reload();
						},
						failure:function(){
							Ext.MessageBox.alert('提示',"操作失败！");
						},
						method:"post",
						params:{user:params}
					});
				}}]
		});
		editWindow.show();
	}
}
function deleteUser(){
	var gridObj=Ext.getCmp('userInfoGrid');//得到grid对象
	var store = gridObj.getStore();
	var rows = gridObj.getSelectionModel().getSelections();//得到选中的行
	if (rows.length>=1){
		Ext.MessageBox.confirm("删除用户信息","确定要删除选中的用户吗？",
			function(btn,text){
				if(btn=='yes'){
					for(var i=0;i<rows.length;i++){
					 	store.remove(rows[i]);
					 	store.reload();
				 	}
				}
			}
		)
	 }else{
	 	Ext.MessageBox.alert('删除提示','请至少选择一项！');
	 }
}
function getUserForm(){
//	var sexData = [["男","男"],["女","女"]];
//	var proxy = new Ext.data.MemoryProxy(sexData);
//	var sexMeta = new Ext.data.Record.create([{name:"sex",type:"String",mapping:0},
//																		{name:"sexName",type:"String",mapping:1}]);
//	var reader = new Ext.data.ArrayReader({},sexMeta);
//	var store = new Ext.data.Store({
//		proxy:proxy,
//		reader:reader
//	});
//	store.load();
	var form = new Ext.form.FormPanel({
		width:400,
		autoHeight:true,
		frame:true,
		renderTo:Ext.getBody(),
		labelAlign:"right",
		labelWidth:80,
		defaultType:"textfield",
		items:[{
			name:"s_userName",
			fieldLabel:"用户名",
			width:150
		},{
			name:"s_age",
			fieldLabel:"年龄",
			width:150
		},{
			name:"s_sex",
			xtype:"combo",
			fieldLabel:"姓别",
			store:new Ext.data.SimpleStore({
				fields:["sex","sexName"],
				data:[["男","男"],["女","女"]]
			}),
			mode:"local",
			displayField:"sexName",
			valueField:"sex",
			width:150,
			emptyText:"请选择"
		},{
			format:"Y-m-d",
			xtype:"datefield",
			name:"s_borthday",
			fieldLabel:"出生日期",
			width:150
		},{
			name:"s_city",
			fieldLabel:"城市",
			width:150
		}]
	});
	return form;
}