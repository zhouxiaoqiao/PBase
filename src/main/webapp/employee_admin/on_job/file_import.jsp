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
.file-box{ position:relative;width:340px}  
.txt{ height:22px; border:1px solid #cdcdcd; width:200px;border-right:none;}  
.btn{ background-color:#FFF; border:1px solid #CDCDCD;height:26px; width:70px;}  
.file{ position:absolute; top:0; right:80px; height:24px; filter:alpha(opacity:0);opacity: 0;width:260px } 

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
					jsonReader : {
						repeatitems : false
					},
					pager : "#pagerId",
					pginput : true,
					autowidth : true,
					gridview : true,
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
			edit : false,
			refresh : false
		}), initUserOperate();
	});

	function initUserOperate() {
 
		jQuery("#dataTableId").navButtonAdd('#pagerId', {
			caption : "下载模板",
			onClickButton : function() {
				submitFrom("editForm",'<%=pu.getUrl()%>getDownloadFile.action');
			}
		}).trigger("reloadGrid"); // 重新载入;
		jQuery("#dataTableId").navButtonAdd('#pagerId', {
			caption : "导入档案",
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
	 
	 	<div id="editUnitDiv"style="visibility: hidden">
	
	 <table width="100%" border="0" align="center" cellpadding="0"
				class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">
					导入管理
				</td>
			</tr>
		</table>
		<form id="editForm" action="<%=pu.getUrl()%>importExcel.action?basePath=<%=basePath%>"  name="editForm" method="post"  enctype="multipart/form-data">
			<table class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">选择导入文件</td>
					<td class="td_table_2">
					<input type='hidden' name='fileName' id='fileName'/> 
					<input type='text' name='import_path' id='import_path'/> 
					<input type="file" id="upload_file" name="upload_file" onchange="display_importPath()" />                      
                    <input name="button" type='submit' class="button_70px"  value='导 入'>
                    <input name="button" type='button' class="button_70px" onClick="clearWin();" value='清 除'> 
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
function display_importPath( ) {
	
	document.getElementById('import_path').value=document.getElementById('upload_file').value;
	document.getElementById('upload_file').style.display= "none";
	document.getElementById('fileName').value =document.getElementById('upload_file').value.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,"$1")+'.'+document.getElementById('upload_file').value.replace(/.+\./,"");
 
}
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
		if (rowData.status == '1')
			op.innerHTML = '在职';
		else if (rowData.status == '0')
			op.innerHTML = '离职';
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
		$('#dept_name').attr('value', '');
		$('#salary_month').attr('value', '');
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
