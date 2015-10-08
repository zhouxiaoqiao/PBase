<%@ page language="java" import="java.util.*,com.yunyuan.util.PageUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
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
    var dept_id ='200';
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
					caption : "应聘者信息"
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
			caption : "导入简历",
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
///editCol名固定需要修改的字段--扩展	
function display_importPath( ) {
	
	document.getElementById('import_path').value=document.getElementById('upload_file').value;
	document.getElementById('upload_file').style.display= "none";
	document.getElementById('fileName').value =document.getElementById('upload_file').value.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,"$1")+'.'+document.getElementById('upload_file').value.replace(/.+\./,"");
 
}

</script>
</html>
