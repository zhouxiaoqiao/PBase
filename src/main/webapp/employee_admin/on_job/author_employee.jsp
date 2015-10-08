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
.file-box{ position:relative;width:340px}  
.txt{ height:22px; border:1px solid #cdcdcd; width:200px;border-right:none;}  
.btn{ background-color:#FFF; border:1px solid #CDCDCD;height:26px; width:70px;}  
.file{ position:absolute; top:0; right:80px; height:24px; filter:alpha(opacity:0);opacity: 0;width:260px } 

</style>

<script type="text/javascript">
	var init_url='<%=pu.getUrl()%>initLoginAuthor.action';
  	$(function(){ 		
  		$("#dataTableId").jqGrid({
  			url:init_url,
			datatype: "json",
			type:'post',
			height: "auto", 
			colNames:['序号','员工名','员工账号','年龄','入职日期','毕业院校','职业状态','毕业时间','部门名','部门','薪资','职务','工作经验'],
			colModel:[						
				{name:'eid',index:'rowindex',sortable:false,editable:false,hidden:true},				
				{name:'staff_name',index:'staff_name',sortable:true,editable:true},
				{name:'user_account',index:'user_account',sortable:true,editable:true},
				{name:'age',index:'age',sortable:true,editable:true},
				{name:'join_time',index:'join_time',sortable:true,editable:true},
				{name:'college',index:'college',sortable:true,editable:true},
				{name:'status',index:'status',sortable:true,edittype:"select",formatter:'select',editoptions:{value:"1:在职状态;0:离职状态;2:试用期;3:延长试用期"}},
				{name:'graduate_time',index:'graduate_time',sortable:true},			
				{name:'dept_name',index:'dept_name',sortable:true,editable:true},
				{name:'org',index:'org',sortable:true,editable:true,hidden:true},
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
 		 
		jQuery("#dataTableId").navButtonAdd('#pagerId', {
			caption : "批量授权",
			onClickButton : function() {
				var arrayObj = new Array();
				var student = new Object(); 
				student.name = "Lanny"; 
				student.age = "25"; 
				student.location = "China";
				arrayObj.push(student);
				var jsonstr = JSON.stringify(arrayObj); 
				document.getElementById("auth_json").value=jsonstr;
				
				submitFrom("editForm",'<%=pu.getUrl()%>authEmployeeLogin.action');
					}
				}).trigger("reloadGrid"); // 重新载入;
		jQuery("#dataTableId")
				.navButtonAdd(
						'#pagerId',
						{
							caption : "详细授权",
							onClickButton : function() {
								document.getElementById("editUnitDiv").style.visibility = "visible";
								document.getElementById('select_path').style.display = "";
							}
						}).trigger("reloadGrid"); // 重新载入;

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
				<td class="td_table_top" align="center">登录授权管理</td>
			</tr>
		</table>
		<form id="editForm"
			action="<%=pu.getUrl()%>authEmployeeLogin.action?basePath=<%=pu.getBasePath()%>"
			name="editForm" method="post" enctype="multipart/form-data">
			<table class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">选择导入文件</td>
					<td class="td_table_2"><input type='hidden' name='auth_json'
						id='auth_json' /> <input type='text' name='import_path'
						id='emp_json' /> <input type="file" id="upload_file"
						name="upload_file" onchange="display_importPath()" /> <input
						name="button" type='submit' class="button_70px" value='导 入'>
						<input name="button" type='button' class="button_70px"
						onClick="clearWin();" value='清 除'></td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
	
</script>
</html>
