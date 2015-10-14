<%@ page language="java" import="java.util.*,com.yunyuan.util.PageUtil"
	pageEncoding="UTF-8"%>
<%
	String action = (String) request.getParameter("action");
	//System.out.println("action==="+action);
	PageUtil pu = new PageUtil(request, action);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=pu.getBasePath()%>">
<title>员工档案</title>
<%@include file="/common/use_js.jsp"%>
<link rel="stylesheet" href="<%=pu.getBasePath()%>/styles/css/style.css"
	type="text/css" media="all" />
<link rel="stylesheet" type="text/css"
	href="<%=pu.getBasePath()%>/styles/wbox/wbox/wbox.css" />
<style type="text/css">
.file-box {
	position: relative;
	width: 340px
}

.txt {
	height: 22px;
	border: 1px solid #cdcdcd;
	width: 200px;
	border-right: none;
}

.btn {
	background-color: #FFF;
	border: 1px solid #CDCDCD;
	height: 26px;
	width: 70px;
}

.file {
	position: absolute;
	top: 0;
	right: 80px;
	height: 24px;
	filter: alpha(opacity :   0);
	opacity: 0;
	width: 260px
}
</style>

<script type="text/javascript">
	var init_url='<%=pu.getUrl()%>initLoginAuthor.action';
  	$(function(){ 		
  		$("#dataTableId").jqGrid({
  			url:init_url,
			datatype: "json",
			type:'post',
			height: "auto", 
			colNames:['序号','员工名','员工账号','性别','年龄','邮箱','入职日期','毕业院校','职业状态','毕业时间','部门名','部门','薪资','职务','工作经验'],
			colModel:[						
				{name:'eid',index:'rowindex',sortable:false,editable:false,hidden:true},				
				{name:'staff_name',index:'staff_name',sortable:true,editable:true},
				{name:'user_account',index:'user_account',sortable:true,editable:true},
				{name:'sex',index:'sex',sortable:true,editable:true},
				{name:'age',index:'age',sortable:true,editable:true},
				{name:'email',index:'email',sortable:true,editable:true},
				{name:'join_time',index:'join_time',sortable:true,editable:true},
				{name:'college',index:'college',sortable:true,editable:true},
				{name:'status',index:'status',sortable:true,edittype:"select",formatter:'select',editoptions:{value:"1:在职状态;0:离职状态;2:试用期;3:延长试用期"}},
				{name:'graduate_time',index:'graduate_time',sortable:true},			
				{name:'dept_name',index:'dept_name',sortable:true,editable:true},
				{name:'dept_id',index:'dept_id',sortable:true,editable:true,hidden:true},
				{name:'salary_month',index:'salary_month',sortable:true,editable:true},				
				{name:'job_name',index:'job_name',sortable:false,editable:true},
				{name:'work_history',index:'work_history',sortable:false,editable:true}
			],
			viewrecords:true,  
			page:'<%=pu.getPage()%>',
			rows:20,
			sord:'<%=pu.getSortorder()%>',
			sidx:'<%=pu.getSortname()%>',
			jsonReader:{		 
			     repeatitems : false 	      
			},  
			pager:"#pagerId",  
			pginput:true,
			autowidth: true,
			gridview: true,
					rowNum : 15,
					rowList : [ 10, 15, 20 ], //可调整每页显示的记录数 
					multiselect : true,
					onSelectRow : function(rowid, status) {
						//onClickSel(rowid, status);
					},
					caption : "员工信息表"
				});
		jQuery("#dataTableId").jqGrid('navGrid', '#pagerId', {
			add : false,
			del : false,
			search : false,
			edit : false
		}), initUserOperate();
	});
  	
	function initUserOperate() {
   		      jQuery("#dataTableId")
				.navButtonAdd(
						'#pagerId',
						{
							caption : "员工授权",
							onClickButton : function() {
								document.getElementById("editUnitDiv").style.visibility = "visible";
								document.getElementById('select_path').style.display = "";
							}
						}).trigger("reloadGrid"); // 重新载入;

	}
	
	function getSelectEmp() {
	   var jsonstr="";
	   var mulsel = $("#dataTableId").getGridParam('selarrrow');
		if (mulsel.length == 0) {
		 alert("你没有选中!");
		 return jsonstr;
		} else {
			var arrayObj = new Array();	
			var plainPassword=document.getElementById("plainPassword").value;
			var password=document.getElementById("password").value;
			 if(plainPassword==''||password==''){
					alert("密码不能空!");
					return jsonstr;
				}else if(plainPassword!=password){
		    	 alert("密码不一致!");
		    	 return jsonstr;
		    }
			
			for ( var i = 0; i < mulsel.length; i++) {
			 	data = $("#dataTableId").jqGrid('getRowData',
						mulsel[i]);
		
				//var 	user_account=eval("data." + user_account) ;
				if(data.user_account==null||data.user_account=='')
				{ 
					alert(data.staff_name+"帐户空请添加!");
				    return jsonstr;
				 }
				else{
					var emp = new Object(); 
					emp.plainPassword=document.getElementById("plainPassword").value;
					emp.password=document.getElementById("password").value;
					emp.username =data.user_account; 
				    emp.sex=data.sex; 
				    emp.org =data.dept_id; 
				    emp.fullname=data.staff_name;
				    emp.enabled="1";				   
				    emp.roleID=document.getElementById("roleID").value;
				    emp.age=data.age;
				    emp.email=data.email;
				    arrayObj.push(emp);
				}
			}
			jsonstr= JSON.stringify(arrayObj); 
			
	}
		return  jsonstr;
	}
	
function excuteAuth(){

	var url='<%=pu.getUrl()%>authEmployeeLogin.action';
		var auth_json = getSelectEmp();
		if (auth_json == "")
			return;
		$.ajax({
			type : "POST",
			url : url,
			data : {
				"auth_json" : auth_json
			},
			cache : false,
			dataType : 'json',
			success : function(data) {
				var message = data.success;
				if (message == "success" && message.length > 0) {
					alert("赋权成功!");
					$("#dataTableId").trigger("reloadGrid");
				} else {
					alert(data.ret_auth);
				}

			}
		}); // 重新载入;

	}
function clearWin(){
	document.getElementById("plainPassword").value="";
	document.getElementById("password").value="";
}	
	
</script>
</head>
<body>
	<table id="dataTableId"></table>
	<div id="pagerId" class="scroll"></div>
	<input type="hidden" id="method" value="">
	<input type="hidden" id="printURL"
		value="<%=pu.getBasePath()%>frameset?__report=report_admin/design/emp.rptdesign&whereSQL=<%=pu.getWhereSQL_print()%>">

	<div id="editUnitDiv" style="visibility: hidden">
		<table width="100%" border="0" align="center" cellpadding="0"
			class="table_all_border" cellspacing="0"
			style="margin-bottom: 0px; border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">授权配置</td>
			</tr>
		</table>
		<form id="editForm"
			action="<%=pu.getUrl()%>authEmployeeLogin.action?basePath=<%=pu.getBasePath()%>"
			name="editForm" method="post" enctype="multipart/form-data">
			<table class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">登录密码</td>
					<td class="td_table_2"><input type='text' name='plainPassword'
						id='plainPassword' /></td>
					<td class="td_table_1">确认密码</td>
					<td class="td_table_2"><input type='text' name='password'
						id='password' /></td>
					<td class="td_table_1">配置角色</td>
					<td class="td_table_2"><select name="roleID" id="roleID">
							<option value="2">普通角色</option>
							<option value="1">管理员</option>
					</select></td>
				</tr>
				<tr>
					<td colspan="6" class="td_table_2"><div align="center">
							<input name="button_bath" type='button' class="button_70px"
								onClick="excuteAuth();" value='批量赋权'> <input
								name="button" type='button' class="button_70px"
								onClick="clearWin();" value='清 除'>
						</div></td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
	
</script>
</html>
