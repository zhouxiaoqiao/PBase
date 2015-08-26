<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String url = basePath + "/adminUser/admin_";
	String sql = null;
	if (request.getAttribute("sql") != null)
		sql = (String) request.getAttribute("sql");
	String whereSQL_print = "no";
	if (request.getAttribute("whereSQL_print") != null) {
		whereSQL_print = (String) request.getAttribute("whereSQL_print");
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
	var sql="<%=sql%>"	; 
	var url="<%=url%>";
	///功能权限控制
	var add_display=false;
	var edit_display=false;
	var query_display=true;
	var del_display=false;
	var print_display=false;
	var init_url=url+'initPage.action?sql='+sql;
  	$(function(){	
  		$("#dataTableId").jqGrid({
  			url:init_url,
			datatype: "json",
			type:'post',
			height: "auto", 
			colNames:['序号','用户账号','用户名','部门ID','所属部门','授权'],
			colModel:[						
				{name:'id',index:'id',sortable:false,editable:false,width:2,hidden:true},
				{name:'username',index:'username',sortable:true,editable:true,width:10,editable:true},
				{name:'fullname',index:'fullname',sortable:true,editable:true,width:10},
				{name:'org',index:'org',sortable:true,editable:true,width:10,hidden:true},
				{name:'dept_name',index:'dept_name',sortable:true,editable:true,width:10},
				{name:'code',index:'code',formatter: cLink, width:10,editable:true} 
				
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
					caption : "用户信息表"
				});

		jQuery("#dataTableId").jqGrid('navGrid', '#pagerId', {
			add : false,
			del : false,
			search : false,
			edit : false
		}), initOperate(add_display, edit_display, query_display, del_display,
				print_display,'id')
	});
  	function cLink(cellvalue, options, rowObject){
  		var username=rowObject.username;
  		return "<a href=\"javascript:void(0)\" onclick=\"authUser('"+username+"')\">打开</a>";} 
  
</script>
</head>
<body onload="initUserWin()">
	<table id="dataTableId"></table>
	<input type="hidden" id="user_id_hidden" name="user_id_hidden" />
	<div id="pagerId" class="scroll"></div>
	<input type="hidden" id="method" value="">
	<input type="hidden" id="printURL"
		value="<%=basePath%>frameset?__report=report_admin/design/emp.rptdesign&whereSQL=<%=whereSQL_print%>">
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
							<td><input type='text' name='user_id' id='user_id' value=''
								onBlur="checkUserIsExits()" /> <span id="user_id_sp">英文字符或数字</span></td>
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
							<td><input type='password' name='password_esure'
								id='password_esure' value='' /></td>
						</tr>
						<tr>
							<td width="120" align="center">所属部门</td>
							<td>	
							<input type='text' name='dept_name' id='dept_name' value=''onclick="selectDept()"/>
					        <input type='hidden' name='dept_id' id='dept_id' />
					        </td>
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
				<br> <input type='button' onclick="checkAndsave()"
					style='width: 50px' value='保存'> <input type='button'
					onclick="clearWin();" style='width: 50px' value='清除'>

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
					action="<%=url%>doFind.action"
					name="findForm" method="post">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="80" align="center">用户帐号</td>
							<td width="120">
							<input type='text' name='user_id_find' id='user_id_find'  value='' /></td>
						</tr>
						<tr>
							<td align="center">用户名</td>
							<td width="120"><input type='text' name='user_name_find' id='user_name_find' value='' />
							</td>
						</tr>
						<tr>
							<td align="center">部门</td>
							<td><input type='text' name='dept_id_find' id='dept_id_find' value='' /></td>
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
	$('#dept_name').attr('value', rowData.dept_name);
	$('#password').attr('value', rowData.password);
	$('#password_esure').attr('value', rowData.password);
}
//新增时清除--扩展
function clearWin() {
	$('#user_id').attr('value', '');
	$('#user_name').attr('value', '');
	$('#dept_id').attr('value', '');
	$('#dept_name').attr('value', '');
	$('#password').attr('value', '');

}
//查找时使用--扩展有命名规则,数据库字段_find
function doFind() {
	var where = "";
	where = getWhere(where, "user_id_find");
	where = getWhere(where, "user_name_find");
	where = getWhere(where, "dept_id_find");
	if (where.length > 0)
		where = " where " + where;
	var url = document.getElementById("findForm").action + "?where="
			+ where;
	submitFrom("findForm", url);

}
function onClickSel(rowid) {

	}

function authUser(userID){

	       document.getElementById('user_id_hidden').value=userID;
		   window.parent.frames['treeCheckRight'].location.reload();
	
	}
	
function checkUserIsExits() {
	var user_id=document.getElementById('user_id').value;
	var checkUser_url=url+'checkUserExits.action?user_id='+user_id;
	var ret=document.getElementById('user_id_sp');
	//异步
	genAjax(checkUser_url,ret);

	}
	
function checkAndsave(){
	var ret=document.getElementById('user_id_sp').innerHTML;
	var password=document.getElementById('password').value;
	var password_esure=document.getElementById('password_esure').value;
	var method=document.getElementById('method').value;
	if(ret.length<6||method=='update'){		 
		 if(password_esure==password){			
				save('editForm');				
			}
		 else{
			 alert("密码与确认密码不同！");
			 return;
		 }
	}			    
	else
		alert("帐号已经存在，不能创建！");
	
}


</script>
</html>
