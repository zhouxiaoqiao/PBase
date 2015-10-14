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
			colNames:['序号','员工名','年龄','入职日期','毕业院校','职业状态','毕业时间','部门名','部门','薪资','工作经验'],
			colModel:[						
				{name:'eid',index:'rowindex',sortable:false,editable:false,width:30,hidden:true},				
				{name:'staff_name',index:'staff_name',sortable:true,editable:true,width:30,editoptions:{readonly:false,rows:"1",cols:"65"}},
				{name:'age',index:'age',sortable:true,editable:true,width:30},
				{name:'join_time',index:'join_time',sortable:true,editable:true,width:30},
				{name:'college',index:'college',sortable:true,editable:true,width:30},
				{name:'status',index:'status',sortable:true,edittype:"select",editable:true,formatter:'select',editoptions:{value:"1:在职状态;0:离职状态;2:试用期;3:延长试用期"},width:30},
				{name:'graduate_time',index:'graduate_time',sortable:true,width:30},			
				{name:'dept_name',index:'dept_name',sortable:true,editable:true,width:30},
				{name:'dept_id',index:'dept_id',sortable:true,editable:true,hidden:true},
				{name:'salary_month',index:'salary_month',sortable:true,editable:true,width:30},
				{name:'job',index:'job',sortable:false,editable:true,width:60,edittype:'textarea'}
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
						if(document.getElementById("editUnitDiv").style.visibility != "visible")
					        document.getElementById("editUnitDiv").style.visibility = "visible";
						else
							document.getElementById("editUnitDiv").style.visibility = "hidden";
 						  var rowData = $("#dataTableId").getRowData(rowid);
							$('#staff_name').attr('value', rowData.staff_name);
							$('#eid').attr('value', rowData.rowid);

					},
					caption : "员工转正表"
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
		value="<%=pu.getBasePath()%>frameset?__report=report_admin/design/emp.rptdesign&whereSQL=<%=pu.getWhereSQL_print()%>">
	<div id="editUnitDiv"style="visibility: hidden">
	<form id="editForm" action="<%=pu.getUrl()%>" name="editForm"
			method="post">
	 <table width="100%" border="0" align="center" cellpadding="0"
				class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">
					转正管理
				</td>
			</tr>
		</table>
		 <table   class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">中文姓名</td>
					<td class="td_table_2"><input type='text' name='staff_name'
						id='staff_name' disabled /> <input type="hidden" id="eid" name='eid'
						value="" /></td>
					<td class="td_table_1">职员转正</td>
					<td class="td_table_2"><select name="status"
							id="status">
								<option value="1">同意转正</option>
								<option value="2">不同意转正</option>
								<option value="3">延长试用期</option>
						</select></td>
					<td class="td_table_1">转正日期</td>
					<td class="td_table_2"><input type='text' name='regular_date'
						id='regular_date' onFocus="WdatePicker()"/></td>
					<td class="td_table_1">转正说明</td>
					<td class="td_table_2"><textarea
							name="regular_inf" id="regular_inf" cols="50"></textarea></td>
				</tr>
				<tr>
					<td colspan="8" class="td_table_2"><div align="center">
							<input name="button" type='button' class="button_70px"
								onclick='save("editForm")' value='转 正'> <input
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
