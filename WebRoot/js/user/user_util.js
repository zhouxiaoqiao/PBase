/**
 * 普通异步请求
 * 
 * @param url
 * @returns
 */
function genAjax(url, ret_object) {

	$.ajax({
		type : 'POST',
		url : url,
		dataType : 'json',
		success : function(msg) {
			if (msg.success)
				ret_object.innerHTML = msg.result;
			else
				alert('返回失败！');
		}
	});

}
/**
 * 获取后台数据
 * @param url
 * 异步执行
 * 
 *  */
function getDataByAjax(url,functionName,data_ret) {

	$.ajax({
		type : 'POST',
		url : url,
		dataType : 'json',
		success : function(msg) {			
			if (msg!=null){
				data_ret=msg;
				window[functionName](data_ret);
				 
			}
				
			else
				alert('返回失败！');
		}
	});
 
 
}
/**
 * 选择部门
 * 
 * 注意父页面需要dept_id ,dept_name 2个对象
 */

function selectDept() {
	var url =  getBasePath()  + "/sys_admin/select_dept.jsp";
	var width =100;
	var height = 500;
	var showy = 100;
	var showx = 100;  	
	win_child = window
			.open(	url,
					"选择部门",
					"width="
							+ width
							+ ",height="
							+ height
							+ ",top="
							+ showy
							+ ",left="
							+ showx
							+ ",dependent=yes,location=no,titlebar=no,menubar=no,scrollbars=no,toolbar=no,resizable=no,status=yes");

	 
}
/*******************************************************************************
 * 
 * @param selectName
 */
function getSelectAjax(selectID, selectName) {
	var url = getBasePath() + '/main.do?method=getSelectData&selectName='+ selectName;
	var sel = document.getElementById(selectID);
	clearSelect(selectID);
	$.ajax({
		type : 'POST',
		url : url,
		dataType : 'json',
		success : function(msg) {
			if (msg.success) {
				var list = msg.selectData;
				for ( var i = 0; i < list.length; i++) {
					var k_v = list[i];
					var op = window.document.createElement("option");
					op.value = k_v.opt_val;
					op.innerHTML = k_v.opt_name;
					sel.appendChild(op);
				}
			} else
				alert('返回失败！');
		}
	});

}

function clearSelect(selectName) {
	var sel = document.getElementById(selectName);
	for ( var i = 0; i < sel.options.length; i++) {
		sel.options.remove(i);
	}
}
/***
 * 获取基本路径
 * @returns
 */
function getBasePath() {
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href; // 获取主机地址之后的目录，如：
													 
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName); // 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos); // 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);

}
