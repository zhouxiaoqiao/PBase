<%@ page language="java" import="java.util.*,com.yunyuan.util.PageUtil" pageEncoding="UTF-8"%>
<%	
String action=(String)request.getParameter("action");
PageUtil pu=new PageUtil(request,action);
 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=pu.getBasePath()%>">
<title>jquery demo</title>
<%@include file="/common/use_js.jsp" %> 
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
				{name:'name',index:'name',sortable:true,editable:true,width:30,editable:true,editoptions:{readonly:false,rows:"1",cols:"65"}},
				{name:'age',index:'age',sortable:true,editable:true,width:30},
				{name:'join_time',index:'join_time',sortable:true,editable:true,width:30},
				{name:'college',index:'college',sortable:true,editable:true,width:30},
				{name:'status',index:'status',sortable:true,edittype:"select",formatter:'select',editoptions:{value:"1:在职;0:离职"},width:30},
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
		}), initOperate(add_display, edit_display, query_display, del_display,
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
	<div id="editUnitDiv" class="editWin" style="height: 300px;">
		<div id="titeWin" class="ewTop" style="float: left;">
			<span id="titeSpan">新 增</span>
		</div>
		<div style="float: left; width: 3%; height: 15px;background: #F0FFFF;"
			onclick='closeWin(".editWin")'>
			<img src="<%=pu.getBasePath()%>image/close.png" align="middle" />
		</div>

		<div id="tbdiv" class="ewContent" style="height: 280px;">
			<div style="text-align: left;">
				<br>
				<form id="editForm" action="<%=pu.getUrl()%>"
					name="editForm" method="post">

					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="120" align="center"><input type="hidden" id="eid"
								name='eid' value="" />员工姓名</td>
							<td><input type='text' name='name' id='name' value='' /></td>
						</tr>
						<tr>
							<td align="center">员工年龄</td>
							<td><input type='text' name='age' id='age' value='' /></td>
						</tr>
						<tr>
							<td align="center">入职时间</td>
							<td><input type="text" name='join_time' id='join_time'
								value='' onfocus="WdatePicker()" /></td>
						</tr>
						<tr>
							<td align="center">毕业院校</td>
							<td><input name='college' id='college' value='' /></td>
						</tr>
						<tr>
							<td align="center">职业状态</td>
							<td><select name="status" id="status" style="width:120px" size="1" ondblclick='getSelectAjax("status","status")'>							
							</select><span>双击加载数据</span></td>
						</tr>
						<tr>
							<td align="center">毕业时间</td>
							<td><input type="text" name='graduate_time'
								id='graduate_time' value='' onfocus="WdatePicker();" /></td>
						</tr>
						<tr>
							<td  align="center">所属部门</td>
							<td>	
							<input type='text' name='dept_name' id='dept_name' value=''onclick="selectDept()"/>
					        <input type='hidden' name='dept_id' id='dept_id' />
					        </td>
						</tr>
						<tr>
							<td align="center">每月薪资</td>
							<td><input name='salary_month' id='salary_month' value='' /></td>
						</tr>
						<tr>
							<td align="center">工作经验</td>
							<td><textarea cols=40 rows=5 id="job" name="job"></textarea>
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
///需要修改的字段--扩展	
function editCol(rowData) {
	$('#method').attr('value', 'update');
	$('#eid').attr('value', rowData.eid);
	$('#name').attr('value', rowData.name);
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
