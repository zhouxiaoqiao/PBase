<%@ page language="java" import="java.util.*,com.yunyuan.util.PageUtil" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
String action=(String)request.getParameter("action");
PageUtil pu=new PageUtil(request,action);
 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=pu.getBasePath()%>">
<title>员工转正</title>
<%@include file="/common/use_js.jsp" %> 
<link rel="stylesheet" href="<%=basePath%>/styles/css/style.css"
	type="text/css" media="all" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/styles/wbox/wbox/wbox.css" />
<style type="text/css">
#pagerId input {
	height: 20px;
}
</style>
<script type="text/javascript">
	var sql="<%=pu.getSql()%>"; 
	var js_basePath='<%=pu.getBasePath()%>';
	///功能权限控制
	var add_display=true;
	var edit_display=true;
	var query_display=true;
	var del_display=true;
	var print_display=true;
	var init_url='<%=pu.getUrl()%>initPage.action?sql='+sql;
  	$(function(){ 		
  		$("#dataTableId").jqGrid({
  			url:init_url,
			datatype: "json",
			type:'post',
			height: "auto", 
			colNames:['序号','员工名','性 别','年 龄','联系电话','毕业院校','录用状态','毕业时间','专业','面试人','到达日期','兴趣爱好','工作经验'],
			colModel:[						
				{name:'eid',index:'rowindex',sortable:false,editable:false,hidden:true},				
				{name:'china_name',index:'china_name',sortable:true,editable:true},
				{name:'sex',index:'sex',sortable:true,editable:true,formatter:'select',editoptions:{value:"1:男;-1:女"}},
				{name:'age',index:'age',sortable:true,editable:true},
				{name:'tel',index:'tel',sortable:true,editable:true},
				{name:'college',index:'college',sortable:true,editable:true},
				{name:'emp_status',index:'emp_status',sortable:true,formatter:'select',editoptions:{value:"1:录用状态;2:淘汰状态;3:观望"}},
				{name:'graduate_time',index:'graduate_time',sortable:true},			
				{name:'professional',index:'professional',sortable:true,editable:true},
				{name:'interview_person',index:'interview_person',sortable:true,editable:true},
				{name:'come_date',index:'come_date',sortable:true,editable:true},				
				{name:'interest',index:'interest',sortable:false,editable:true},
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
					onSelectRow : function(rowid) {
						
						document.getElementById("editUnitDiv").style.visibility = "visible";
						  var rowData = $("#dataTableId").getRowData(rowid);
							$('#china_name').attr('value', rowData.china_name);
							$('#eid').attr('value', rowData.rowid);

					},
					caption : "员工信息表"
				});
		jQuery("#dataTableId").jqGrid('navGrid', '#pagerId', {
			add : false,
			del : false,
			search : false,
			edit : false
		});
	});
</script>
</head>
<body>

	<table id="dataTableId"></table>
	<div id="pagerId" class="scroll"></div>
	<input type="hidden" id="method" value="saveRegular">
	<input type="hidden" id="printURL"
		value="<%=pu.getBasePath()%>frameset?__report=report/design/emp.rptdesign&whereSQL=<%=pu.getWhereSQL_print()%>">
	<div id="editUnitDiv"style="visibility: hidden">
	<form id="editForm" action="<%=pu.getUrl()%>" name="editForm"
			method="post">
	 <table width="100%" border="0" align="center" cellpadding="0"
				class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">
					录用管理
				</td>
			</tr>
		</table>
		 <table   class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">中文姓名</td>
					<td class="td_table_2"><input type='text' name='china_name'
						id='china_name' disabled /> <input type="hidden" id="eid" name='eid'
						value="" /></td>
					<td class="td_table_1">职员录取</td>
					<td class="td_table_2"><select name="emp_status"
							id="emp_status">
								<option value="1">同意录取</option>
								<option value="2">不合格淘汰</option>
								 
						</select></td>
					<td class="td_table_1">录取日期</td>
					<td class="td_table_2"><input type='text' name='regular_date'
						id='regular_date' onFocus="WdatePicker()"/></td>
					<td class="td_table_1">录取说明</td>
					<td class="td_table_2"><textarea
							name="hire_inf" id="hire_inf" cols="50"></textarea></td>
				</tr>
				<tr>
					<td colspan="8" class="td_table_2"><div align="center">
							<input name="button" type='button' class="button_70px"
								onclick='save("editForm")' value='录 取'> <input
								name="button" type='button' class="button_70px"
								onClick="clearWin();" value='清 除'> 
						</div></td>
				</tr>
				</table>
				</form>
	</div>
	 
</body>
<script type="text/javascript">
///需要修改的字段--扩展	
function editCol(rowData) {
	$('#method').attr('value', 'update');
	$('#eid').attr('value', rowData.eid);
	$('#china_name').attr('value', rowData.china_name);
	$('#age').attr('value', rowData.age);

	$('#join_time').attr('value', rowData.join_time);
	$('#college').attr('value', rowData.college);
	$('#graduate_time').attr('value', rowData.graduate_time);
	$('#salary_month').attr('value', rowData.salary_month);
	$('#job').attr('value', rowData.job);
	$('#dept_id').attr('value', rowData.dept_id);
	$('#dept_name').attr('value', rowData.dept_name);
	
	var sel = document.getElementById("status");
	 clearSelect("status");
	var op = window.document.createElement("option");
	if(rowData.status=='1')	
	   op.innerHTML ='在职';
	else if(rowData.status=='0')	
		op.innerHTML ='离职';
	op.value = rowData.status;	
	sel.appendChild(op);
	sel.options[0].selected = true;
	getBasePath();
}
//新增时清除--扩展
function clearWin() {
	$('#name').attr('value', '');
	$('#age').attr('value', '');
	$('#college').attr('value', '');
	$('#graduate_time').attr('value', '');
	$('#job').attr('value', '');
	$('#status').attr('value', '');
	$('#join_time').attr('value', '');
	$('#dept_id').attr('value', '');
	$('#dept_name').attr('value','');
	$('#salary_month').attr('value','');
}
//查找时使用--扩展有命名规则,数据库字段_find
function doFind() {
	var where = "";
	where = getWhere(where, "status_find");
	where = getWhere(where, "college_find");
	where = getWhere(where, "join_time_find");
	where = getWhere(where, "age_find");
	if (where.length > 0)
		where = " where " + where;
	var url = document.getElementById("findForm").action + "?where="
			+ where;
	alert(url);
	submitFrom("findForm", url);

}
 


</script>
</html>
