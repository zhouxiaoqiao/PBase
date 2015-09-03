var base_Bath;
// /////////////////////////////////////////////业务需要//////////////////////////////////////////////////
var deviceNO;
var contractCheck;
var radioCheck;
var queryType;
//日期格式化
function formatDate(date) {
	var month = date.getMonth() + 1;
	if ("" != date) {

		if (date.getMonth() + 1 < 10) {
			month = '0' + (date.getMonth() + 1);
		}
		var day = date.getDate();
		if (date.getDate() < 10) {
			day = '0' + date.getDate();
		}

		// 返回格式化后的时间
		return date.getFullYear() + '-' + month + '-' + day;
	} else {
		return "";
	}
}
function formatDateHMS(date) {
	var month = date.getMonth() + 1;
	if ("" != date) {

		if (date.getMonth() + 1 < 10) {
			month = '0' + (date.getMonth() + 1);
		}
		var day = date.getDate();
		if (date.getDate() < 10) {
			day = '0' + date.getDate();
		}

		// 返回格式化后的时间
		return date.getFullYear() + '-' + month + '-' + day + " "
				+ date.getHours() + ":" + date.getMinutes() + ":"
				+ date.getSeconds();
	} else {
		return "";
	}
}

// //分页显示控件
function pagControl(mainDivID, functionName,pageNumber, pageSize,id,billId) {
	var p = $('#' + mainDivID).datagrid('getPager');
	$(p).pagination({
		pageSize : 5,// 每页显示的记录条数，默认为10
		pageList : [ 5, 10, 15 ],// 可以设置每页记录条数的列表
		beforePageText : '第',// 页数文本框前显示的汉字
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'

	});

	$(p).pagination({
		onSelectPage : function(pageNumber, pageSize) {
			window[functionName](pageNumber, pageSize,id,billId);
		}
	});

	$(p).pagination('refresh', {
		pageNumber : pageNumber,
		pageSize : pageSize
	});
}

function Open_Dialog(dialog_divID) {
	$('#'+dialog_divID).show();
	$('#'+dialog_divID).dialog({
		collapsible : true,
		minimizable : true,
		maximizable : true
		 
	});
}

/**
 * 争议请求对象，用于封装争议处理服务请求参数
 * @returns
 */
function DisputeService()
{
	this.req = "";
	this.acctId = "";
	this.reasonDesc = "";
	this.dealType = "";
	this.staffId = "";
	this.feeGroup = new Array();
}

/**
 * 账目对象
 * @returns
 */
function feeGroupObj(){
	
	this.acctItemId = "";
	this.acctItemTypeId = "";
	this.itemSourceId = "";
	this.amount = "";
}

/**
 * 封装调账录入服务输入参数
 * @returns
 */
function AdjustInService()
{
	this.REQ = "";
	this.ACCTID = "";
	this.REASONID = "";
	this.REASONMARK = "";
	this.STAFFID = "";
	this.ORGID = "";
	this.ORDERSRCTYPE = "";
	this.ORDERSRCNBR = "";
	this.CUSTNBR = "";
	this.APPNBR = "";
	this.ACCTITEMGROUP = new Array();
}

/**  
 * 扩展树表格级联勾选方法：  
 * @param {Object} container  
 * @param {Object} options  
 * @return {TypeName}   
 */
$.extend($.fn.treegrid.methods, {
	/**  
	 * 级联选择  
	 * @param {Object} target  
	 * @param {Object} param   
	 *      param包括两个参数:  
	 *          id:勾选的节点ID  
	 *          deepCascade:是否深度级联  
	 * @return {TypeName}   
	 */
	cascadeCheck : function(target, param) {
		var opts = $.data(target[0], "treegrid").options;
		if (opts.singleSelect)
			return;
		var idField = opts.idField;//这里的idField其实就是API里方法的id参数   
		var status = false;//用来标记当前节点的状态，true:勾选，false:未勾选   
		var selectNodes = $(target).treegrid('getSelections');//获取当前选中项   
		for ( var i = 0; i < selectNodes.length; i++) {
			if (selectNodes[i][idField] == param.id)
				status = true;
		}
		//级联选择父节点   

		selectParent(target[0], param.id, idField, status);
		selectChildren(target[0], param.id, idField, param.deepCascade,
				status);

		/**  
		 * 级联选择父节点  
		 * @param {Object} target  
		 * @param {Object} id 节点ID  
		 * @param {Object} status 节点状态，true:勾选，false:未勾选  
		 * @return {TypeName}   
		 */
		function selectParent(target, id, idField, status) {
			var parent = $(target).treegrid('getParent', id);

			if (parent) {
				var parentId = parent[idField];
				if (status)
					$(target).treegrid('select', parentId);
				else
					$(target).treegrid('unselect', parentId);
				selectParent(target, parentId, idField, status);
			}
		}
		/**  
		 * 级联选择子节点  
		 * @param {Object} target  
		 * @param {Object} id 节点ID  
		 * @param {Object} deepCascade 是否深度级联  
		 * @param {Object} status 节点状态，true:勾选，false:未勾选  
		 * @return {TypeName}   
		 */
		function selectChildren(target, id, idField, deepCascade,
				status) {
			
			//深度级联时先展开节点   
			if (!status && deepCascade)
				$(target).treegrid('expand', id);
			//根据ID获取下层孩子节点   
			var children = $(target).treegrid('getChildren', id);
//			alert("selectChildren");
//			alert(children.length);
			if (children) {
				for ( var i = 0; i < children.length; i++) {
					var childId = children[i][idField];
					if (status)
						$(target).treegrid('select', childId);
					else
						$(target).treegrid('unselect', childId);
					selectChildren(target, childId, idField,
							deepCascade, status);//递归选择子节点   
				}
			}
		}
	}
});

function getAccountInfo(pageNumber, pageSize,id,billId) {
	$.ajax({
				async : false,
				type : "POST",
				url : base_Bath + "/canceAdmin/cashQuery.action",
				dataType : "json",
				cache : false,
				data : "pageNumber=" + pageNumber+"&pageSize=" + pageSize+"&radioCheck=" + radioCheck+"&deviceNO=" + deviceNO+"&contractCheck=" + contractCheck +"&queryType="+ queryType,
				success : function(datas) {
					document.getElementById(id+"_tip").innerHTML="提示:"+datas.http_contract_ret; 
					if (contractCheck&&datas.contract) {
						$('#'+id).datagrid(
										{
											striped : true,
											height : 150,
											singleSelect : false,
											loadMsg : '数据加载中请稍后……',
											pagination : true,// 分页控件
											rownumbers : true,// 行号
											fitColumns : true,
											onClickRow : function(rowIndex,
													rowData) {
												
											},
											onCheck : function(rowIndex,rowData){
												acctItemQuery(
														document
																.getElementById("deviceNO").value,
														rowData.accountId, 1, 3,billId);
											},
											title : "合同信息",
											columns : [ [
													{
														field : 'checked',
														width : 60,
														checkbox : true
													}, {
														field : 'accountGroup',
														title : '帐户列表',
														align : 'left',
														width : 100
													}, {
														field : 'accountId',
														title : '帐户标识',
														align : 'left',
														width : 100
													}, {
														field : 'custId',
														title : '客户标识',
														align : 'left',
														width : 100
													}, {
														field : 'accountNbr',
														title : '帐户编码',
														align : 'left',
														width : 100
													}, {
														field : 'accountName',
														title : '帐户名称',
														align : 'left',
														width : 100
													}, {
														field : 'bankId',
														title : '银行标识',
														align : 'left',
														width : 100
													}, {
														field : 'bankAcct',
														title : '银行账号',
														align : 'left',
														width : 100
													} ] ]
										});
						$('#'+id)
								.datagrid('loadData', {
									total : datas.contract_total,
									rows : datas.contract
								});

						// 设置分页控件
						pagControl(
								id,"getAccountInfo",
								pageNumber, pageSize,id,billId);

					} else  if(contractCheck){
						// 隐藏显示

						$('#'+id)
								.datagrid('loadData', {
									total : 0,
									rows : []
								});// 清空下方DateGrid

					}
				}
			});

}

function accountInfoQuery(id,billId) {
	setQueryValue();
	getAccountInfo(1, 5,id,billId);
	
}

function setQueryValue() {
	if (document.getElementById("device_rb").checked) {
		document.getElementById("radioCheck").value = "1";
	} else if (document.getElementById("no_rb").checked) {
		document.getElementById("radioCheck").value = "2";
	} else {
		document.getElementById("radioCheck").value = "3";
	}
	
	// 赋值
	radioCheck = document.getElementById("radioCheck").value;
	deviceNO = document.getElementById("deviceNO").value;
	contractCheck = document.getElementById("contractCheck").checked;
	queryType = document.getElementById("queryType").value;
}

/////////////////////////////////////////////////////////////////////初始化账单数据//////////////////////////////////////////////////
function acctItemQuery(device_no, contract_no, pageNumber, pageSize,id) {

	$('#'+id).treegrid(
			{
				url : base_Bath
						+ '/canceAdmin/accountTreeQuery.action?pageNumber='
						+ pageNumber + '&pageSize=' + pageSize + "&device_no="
						+ device_no + "&contract_no=" + contract_no,
				title : '账单信息',
				striped : true,
				height : 220,
				singleSelect : false,
				collapsible : true,
				pageSize : 3,
				pageNumber : pageNumber,
				loadMsg : '数据加载中请稍后……',
				pagination : true,// 分页控件
				pageList : [ 3, 8, 10 ],
				rownumbers : true,// 行号
				fit : true,
				fitColumns : true,
				checkbox : true,
				idField : 'id',
				treeField : 'acctName',
				toolbar : '#tb',
				selectOnCheck : true,
				cascadeCheck : true,
				onCheckAll : function(rows) {
					
				},
				onUncheckAll : function(rows) {
					
				},
				onClickRow : function(row) {
					
					// 级联选择
					$('#'+id).treegrid('cascadeCheck', {
						id : row.id, // 节点ID
						deepCascade : true
					// 深度级联
					});
					
					
					
				},
				onLoadSuccess : function() {
					$('#'+id).treegrid('collapseAll');
				},
				onCheck :function(row) {
					
				},
				onUncheck :function(row){
					
				}
			});

	// /账单分页显示
	$('#'+id).datagrid('getPager').pagination({

		onSelectPage : function(pageNumber, pageSize) {

			acctItemQuery(device_no, contract_no, pageNumber, pageSize,id);
		}
	});

}

function initAccountInfo(id){
	$('#'+id).datagrid({
		title:'合同信息',
		striped : true,
		height : 150,
		singleSelect : true,
		fitColumns : true,
		loadMsg : '数据加载中请稍后……',
		rownumbers : false,
		pagination:true,
		onClickRow : function(rowIndex,
				rowData) {
		 
		},
		columns : [ [
				{
					field : 'checked',
					width : 60,
					formatter : function(
							value, row,
							index) {
						if (row.checked) {
							return '<input type="checkbox" name="DataGridCheckbox" checked="checked">';
						} else {
							return '<input type="checkbox" name="DataGridCheckbox">';
						}
					}
				},
				{
					field : 'accountGroup',
					title : '帐户列表',
					align : 'left',
					width : 300
				},
				{
					field : 'accountId',
					title : '帐户标识',
					align : 'left',
					width : 300
				},
				{
					field : 'custId',
					title : '客户标识',
					align : 'left',
					width : 300
				}, {
					field : 'accountNbr',
					title : '帐户编码',
					align : 'left',
					width : 300
				}, 
				 {
					field : 'accountName',
					title : '帐户名称',
					align : 'left',
					width : 300
				}, 													
				{
					field : 'bankId',
					title : '银行标识',
					align : 'left',
					width : 300
				}, {
					field : 'bankAcct',
					title : '银行账号',
					align : 'left',
					width : 300
				} ] ]
	});
}