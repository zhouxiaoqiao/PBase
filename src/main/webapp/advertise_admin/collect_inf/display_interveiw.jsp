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
<style type="text/css">
#pagerId input {
	height: 20px;
}
</style>

<script type="text/javascript">
var dept_id ='200';
//if(parent['display_dept'].document.getElementById('pid')!=null)
//	{
//	 dept_id=parent['display_dept'].document.getElementById('pid').value;
//	}

	var sql="<%=pu.getSql()%>"; 
	var js_basePath='<%=pu.getBasePath()%>';
	
	///功能权限控制
	var add_url=js_basePath+"/advertise_admin/collect_inf/edit_interveiw.jsp?action="+"<%=action%>"+"&method=add";
	var edit_url=js_basePath+"/advertise_admin/collect_inf/edit_interveiw.jsp?action="+"<%=action%>"+"&method=update";
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
					onSelectRow : function(rowid, status) {
						//onClickSel(rowid, status);
					},
					caption : "员工信息表2"
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
<body onload="initUserWin()">
	<table id="dataTableId"></table>
	<div id="pagerId" class="scroll"></div>
	<input type="hidden" id="method" value="">
	<input type="hidden" id="printURL"
		value="<%=pu.getBasePath()%>frameset?__report=report_admin/design/emp.rptdesign&whereSQL=<%=pu.getWhereSQL_print()%>">
 
	<div id="findDiv" class="findWin" style="height: 180px;">
		<div id="tite_findWin" class="fwTop" style="float: left;">
			<span id="titeSpan">查找</span>
		</div>
		<div style="float: left; width:5%; height: 15px;background: #F0FFFF;"
			onclick='closeWin(".findWin")'>
			<img src="<%=pu.getBasePath()%>image/close.png" align="middle" />
		</div>
		<div id="find_div" class="fwContent" style="height: 180px;">
			<div style="text-align: left;">
				<br>
				<form id="findForm"
					action="<%=pu.getUrl()%>doFind.action"
					name="findForm" method="post">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="80" align="center">毕业院校</td>
							<td width="120"><input type='text' name='college_find'
								id='college_find' value='' /></td>
						</tr>
						<tr>
							<td align="center">职业状态</td>
							<td width="120">
							<select name="status_find" id="status_find" style="width:80px" size="1" ondblclick='getSelectAjax("status_find","status")'>							
							</select><span>双击</span>
							</td>
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
///editCol名固定需要修改的字段--扩展	
function editCol(rowData) {
	$('#method').attr('value', 'update');
	 
	$('#eid').attr('value', rowData.eid);
	$('#china_name').attr('value', rowData.china_name);
	$('#tel').attr('value', rowData.tel);
	$('#age').attr('value', rowData.age);

	 
	$('#college').attr('value', rowData.college);
	
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
	submitFrom("findForm", url);

}

</script>
</html>
