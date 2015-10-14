<%@ page language="java" import="java.util.*,com.yunyuan.util.PageUtil" pageEncoding="UTF-8"%>
<%

String action=(String)request.getParameter("action");
//System.out.println("action==="+action);
PageUtil pu=new PageUtil(request,action); 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=pu.getBasePath()%>">
<title>员工档案</title>
<%@include file="/common/use_js.jsp" %> 
<link rel="stylesheet" href="<%=pu.getBasePath()%>/styles/css/style.css"
	type="text/css" media="all" />
<link rel="stylesheet" type="text/css"
	href="<%=pu.getBasePath()%>/styles/wbox/wbox/wbox.css" />
<style type="text/css">
#pagerId input {
	height: 20px;
}
</style>
<script type="text/javascript">
var dept_id ='';
if(parent['display_dept'].document.getElementById('pid')!=null)
	{
	 dept_id=parent['display_dept'].document.getElementById('pid').value;
	}

	var sql="<%=pu.getSql()%>"; 
	var js_basePath='<%=pu.getBasePath()%>';
	
	///功能权限控制
	var add_url=js_basePath+"/employee_admin/on_job/edit_employee.jsp?action="+"<%=action%>"+"&method=add";
	var edit_url=js_basePath+"/employee_admin/on_job/edit_employee.jsp?action="+"<%=action%>"+"&method=update";
	var query_display=true;
	var del_url=js_basePath+"<%=action%>"+"delete.action";
	var print_display=true;
	var init_url='<%=pu.getUrl()%>initPage.action?sql='+sql+'&dept_id='+dept_id;
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
		}), initOperateWindow(add_url, edit_url, query_display, del_url,
				print_display,"eid")
	});
</script>
</head>
<body>
	<table id="dataTableId"></table>
	<div id="pagerId" class="scroll"></div>
	<input type="hidden" id="method" value="">
	<input type="hidden" id="printURL"
		value="<%=pu.getBasePath()%>frameset?__report=report_admin/design/emp.rptdesign&whereSQL=<%=pu.getWhereSQL_print()%>">
 
	 
<div id="findUnitDiv"style="visibility: hidden">
	<form id="findForm" action="<%=pu.getUrl()%>doFind.action" name="findForm"
			method="post">
		 <table   class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">员工姓名</td>
					<td class="td_table_2"><input type='text' name='staff_name'
						id='staff_name'   /> <input type="hidden" id="eid" name='eid'
						value="" /></td>
					<td class="td_table_1">职员状态</td>
					<td class="td_table_2"><select name="status"
							id="status">
						        <option value="0">离职状态</option>
								<option value="1">正式在职</option>
								<option value="2">试用期</option>
								<option value="3">延长试用期</option>
						</select></td>
					<td class="td_table_1">入职日期</td>
					<td class="td_table_2"><input type='text' name='regular_date'
						id='regular_date' onFocus="WdatePicker()"/></td>
					<td class="td_table_1">年龄</td>
					<td class="td_table_2"><input type='text' name='age'
						id='age'  />  </td>
				</tr>
				<tr>
					<td colspan="8" class="td_table_2"><div align="center">
							<input name="button" type='button' class="button_70px"
								onclick='doFind();' value='查 询'> <input
								name="button" type='button' class="button_70px"
								onClick="clearWin();" value='清 除'> 
						</div></td>
				</tr>
				</table>
				</form>
	</div>
</body>
<script type="text/javascript">

//查找时使用--扩展有命名规则,数据库字段_find
function doFind() {
	var where = "";
	where = getWhere(where, "staff_name");
	where = getWhere(where, "status");
	where = getWhere(where, "join_time");
	where = getWhere(where, "age");
	if (where.length > 0)
		where = " where " + where;
	var url = document.getElementById("findForm").action + "?where="
			+ where+"&dept_id="+dept_id;
	submitFrom("findForm", url);

}

</script>
</html>
