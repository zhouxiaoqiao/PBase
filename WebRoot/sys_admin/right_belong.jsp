<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String url = basePath + "/Controller/queryMenuUser_MenuTree.action";
	String whereSQL_print = "no";
	if (request.getAttribute("whereSQL_print") != null) {
		whereSQL_print = (String) request
				.getAttribute("whereSQL_print");
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>jquery demo</title>
<%@include file="/common/use_js.jsp" %> 


<style type="text/css">
#pagerId input {
	height: 20px;
}
</style>
<script type="text/javascript">
    var menu_id ='';
    if(parent['admin_menu'].document.getElementById('pid')!=null)
    	menu_id=parent['admin_menu'].document.getElementById('pid').value;

	var url="<%=url%>"+"?menu_id="+menu_id;
	///功能权限控制
	var add_display=false;
	var edit_display=false;
	var query_display=true;
	var del_display=false;
	var print_display=true;

  	$(function(){
  		$("#dataTableId").jqGrid({
  			url:url,
			datatype: "json",
			type:'post',
			height: "auto", 
			colNames:['序号','用户账号','菜单名'],
			colModel:[						
				{name:'rowindex',index:'rowindex',sortable:false,editable:false,width:2,hidden:true},
				{name:'user_id',index:'user_id',sortable:false,editable:false,width:10},
				{name:'name',index:'name',sortable:true,editable:true,width:10,editable:true}				
				
			],
			viewrecords:true,   
			jsonReader:{		 
			     repeatitems : false 	      
			},  
			pager:"#pagerId",  
			pginput:true,
			autowidth: true,
			gridview: true,
					rowNum : 20,
					rowList : [ 10, 20, 30 ], //可调整每页显示的记录数 
					multiselect : true,
					onSelectRow : function(rowid, status) {
						onClickSel(rowid,status);
					},
					caption : "权限归属"
				});

		jQuery("#dataTableId").jqGrid('navGrid', '#pagerId', {
			add : false,
			del : false,
			search : false,
			edit : false
		}), initOperate(add_display, edit_display, query_display, del_display,
				print_display)
	});
	
</script>
</head>
<body onload="initUserWin()">
	<table id="dataTableId"></table>
	<input type="hidden" id="user_id_hidden" name="user_id_hidden" />
	<div id="pagerId" class="scroll"></div>
	<input type="hidden" id="method" value="">
	<input type="hidden" id="printURL"
		value="<%=basePath%>frameset?__report=report/emp.rptdesign&whereSQL=<%=whereSQL_print%>">
	<div id="editUnitDiv" class="editWin" style="height: 180px;">
		<div id="titeWin" class="ewTop" style="float: left;">
			<span id="titeSpan">新 增</span>
		</div>
		<div
			style="float: left; width: 3%; height: 15px; background: #F0FFFF;"
			onclick='closeWin(".editWin")'>
			<img src="<%=basePath%>image/close.png" align="middle" />
		</div>

		<div id="tbdiv" class="ewContent" style="height: 180px;">
			<div style="text-align: left;">
				<br>
				<form id="editForm" action="<%=url%>" name="editForm" method="post">

					<table border="0" cellpadding="0" cellspacing="0">
						<tr id="tr_user_id">
							<td align="center">用户账号</td>
							<td><input type='text' name='user_id' id='user_id' value='' /></td>
						</tr>
						<tr>
							<td width="120" align="center">用户姓名</td>
							<td><input type='text' name='user_name' id='user_name'
								value='' /></td>
						</tr>
						<tr>
							<td width="120" align="center">用户密码</td>
							<td><input type='password' name='password' id='password'
								value='' /></td>
						</tr>
						<tr>
							<td width="120" align="center">确认密码</td>
							<td><input type='password' name='password_esure' id='password_esure'
								value='' /></td>
						</tr>
							<tr>
							<td width="120" align="center">所属部门</td>
							<td><input type='text' name='dept_id' id='dept_id'
								value='' /></td>
						</tr>
						<tr>
							<td align="center">上传照片</td>
							<td><input type="file" id="problems_FJ3" name="problems_FJ3"
								value="上传图片"></td>
						</tr>
					</table>
				</form>
			</div>
			<div style="text-align: center;">
				<br> <input type='button' onclick='save("editForm")' style='width: 50px'
					value='保存'> <input type='button' onclick="clearWin();"
					style='width: 50px' value='清除'>

			</div>
		</div>
	</div>
	<div id="findDiv" class="findWin" style="height: 180px;">
		<div id="tite_findWin" class="fwTop" style="float: left;">
			<span id="titeSpan">查找</span>
		</div>
		<div
			style="float: left; width: 5%; height: 15px; background: #F0FFFF;"
			onclick='closeWin(".findWin")'>
			<img src="<%=basePath%>image/close.png" align="middle" />
		</div>
		<div id="find_div" class="fwContent" style="height: 180px;">
			<div style="text-align: left;">
				<br>
				<form id="findForm"
					action="<%=basePath%>test/testEdit.do?method=doFind"
					name="findForm" method="post">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="80" align="center">毕业院校</td>
							<td width="120"><input type='text' name='college_find'
								id='college_find' value='' /></td>
						</tr>
						<tr>
							<td align="center">职业状态</td>
							<td width="120"><select name="status_find" id="status_find"
								style="width: 80px" size="1">
									<option value="">空值</option>
									<option value="离职">离职</option>
									<option value="在职">在职</option>
							</select></td>
						</tr>
						<tr>
							<td align="center">入职时间</td>
							<td><input type="text" name='join_time_find'
								id='join_time_find' value='' onfocus="WdatePicker()" /></td>
						</tr>
						<tr>
							<td align="center">员工年龄</td>
							<td><input type='text' name='age_find' id='age_find'
								value='' /></td>
						</tr>
					</table>
				</form>
			</div>
			<div style="text-align: center;">
				<br> <input type='button' onclick="doFind()"
					style='width: 50px' value='查找 '> <input type='button'
					onclick="clearWin();" style='width: 50px' value='清除'>

			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
///需要修改的字段--扩展	
function editCol(rowData) {

	$('#method').attr('value', 'update');
	$('#user_id').attr('value', rowData.user_id);
	document.getElementById("tr_user_id").style.display = "none";
	$('#user_name').attr('value', rowData.user_name);
	$('#dept_id').attr('value', rowData.dept_id);
	$('#password').attr('value', rowData.password);
}
//新增时清除--扩展
function clearWin() {
	$('#user_id').attr('value', '');
	$('#user_name').attr('value', '');
	$('#dept_id').attr('value', '');
	$('#password').attr('value', '');

}
//查找时使用--扩展
function doFind() {
	var where = "";
	where = getWhere(where, "status_find");
	where = getWhere(where, "college_find");
	where = getWhere(where, "join_time_find");
	where = getWhere(where, "age_find");
	if (where.length > 0)
		where = " where " + where;
	var url = document.getElementById("findForm").action + "&where="
			+ where;
	submitFrom("findForm", url);

}
function onClickSel(rowid) {
	var list=$("#dataTableId").jqGrid('getRowData',rowid)
    document.getElementById('user_id_hidden').value=list.user_id;
	window.parent.frames['treeCheckRight'].location.reload();

	}
</script>
</html>
