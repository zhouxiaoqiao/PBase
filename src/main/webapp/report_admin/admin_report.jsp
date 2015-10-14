<%@ page language="java" import="java.util.*,com.yunyuan.util.PageUtil" pageEncoding="UTF-8"%>
<%
	
	PageUtil pu=new PageUtil(request,"/sysAdmin/adminReport_");
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=pu.getBasePath()%>">
<title>jquery demo</title>
<!-- 自定义css js--> 
<%@include file="/common/use_js.jsp" %> 
<style type="text/css">
#pagerId input {
	height: 20px;
}
</style>
<script type="text/javascript">
	var sql="<%=pu.getSql()%>"; 
	///功能权限控制
	var add_display=true;
	var edit_display=true;
	var query_display=true;
	var del_display=true;
	var print_display=true;
	var init_url='<%=pu.getUrl()%>initPage.action?sql=' + sql+'&report_type=1';
	$(function() {
		$("#dataTableId").jqGrid({
			url : init_url,
			datatype : "json",
			type : 'post',
			height : "auto",
			colNames : [ '序号', '报表名', '模板路径', '参数值','执行SQl', '备注' ],
			colModel : [ {
				name : 'report_id',
				index : 'report_id',
				sortable : false,
				editable : false,
				width : 20,
				hidden : true
			}, {
				name : 'report_name',
				index : 'report_name',
				sortable : true,
				editable : true,
				width : 20,
				editable : true,
				editoptions : {
					readonly : false,
					rows : "1",
					cols : "65"
				}
			}, {
				name : 'templet_path',
				index : 'templet_path',
				sortable : true,
				editable : true,
				width : 30
			}, {
				name : 'where_sql',
				index : 'where_sql',
				sortable : true,
				editable : true,
				width : 30
			}, {
				name : 'excute_sql',
				index : 'excute_sql',
				sortable : true,
				editable : true,
				width : 50
			}, {
				name : 'remark',
				index : 'remark',
				sortable : true,
				editable : true,
				width : 30
			}

			],
			viewrecords : true,
			jsonReader : {
				repeatitems : false
			},
			pager : "#pagerId",
			pginput : true,
			autowidth : true,
			gridview : true,
			rowNum : 20,
			rowList : [ 10, 20, 30 ], //可调整每页显示的记录数 
			multiselect : true,
			onSelectRow : function(rowid, status) {
				//onClickSel(rowid, status);
			},
			caption : "报表信息表"
		});

		jQuery("#dataTableId").jqGrid('navGrid', '#pagerId', {
			add : false,
			del : false,
			search : false,
			edit : false
		}), initOperate(add_display, edit_display, query_display, del_display,
				print_display, "report_id")
	});
</script>
</head>
<body onload="initUserWin()">
	<table id="dataTableId"></table>
	<div id="pagerId" class="scroll"></div>
	<input type="hidden" id="method" value="">
	<input type="hidden" id="printURL"
		value="<%=pu.getBasePath()%>frameset?__report=report/emp.rptdesign&whereSQL=<%=pu.getWhereSQL_print()%>">
	<div id="editUnitDiv" class="editWin" style="height: 280px;">
		<div id="titeWin" class="ewTop" style="float: left;">
			<span id="titeSpan">新 增</span>
		</div>
		<div
			style="float: left; width: 3%; height: 15px; background: #F0FFFF;"
			onclick='closeWin(".editWin")'>
			<img src="<%=pu.getBasePath()%>image/close.png" align="middle" />
		</div>

		<div id="tbdiv" class="ewContent" style="height: 280px;">
			<div style="text-align: left;">
				<br>
				<form id="editForm" action="<%=pu.getUrl()%>" name="editForm" method="post">

					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="120" align="center"><input type="hidden"
								id="report_id" name='report_id' value="" />报表名</td>
							<td>
							<input type='text' name='report_name' id='report_name' style="width:250px;" value='' />
							<input type='hidden' name='report_type' id='report_type' value='1' />
							</td>
						</tr>
						<tr>
							<td align="center">模板路径</td>
							<td><input type='text' name='templet_path' id='templet_path'
								value='' style="width:250px;" /><span>/xx/xx.rptdesign</span></td>
						</tr>
						<tr>
							<td align="center">设置参数</td>		
							<td><input  name='where_sql' id='where_sql' style="width:250px;" /><span>例如: int:33,string:rere</span></td>
						</tr>
						<tr>
							<td align="center">执行SQL</td>
							<td><textarea name='excute_sql' id='excute_sql' rows='5' style="width:250px;"/></textarea><span>与报表模板相符</span></td>
						</tr>
					
						<tr>
							<td align="center">备注说明</td>
							<td><textarea name='remark' id='remark' rows='2' style="width:250px;"></textarea></td>
						</tr>
					</table>
				</form>
			</div>
			<div style="text-align: center;">
				<br> <input type='button' onclick="checkSave()" style='width: 50px'
					value='保存'> <input type='button' onclick="clearWin();"
					style='width: 50px' value='清除'>

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
			<img src="<%=pu.getBasePath()%>image/close.png" align="middle" />
		</div>
		<div id="find_div" class="fwContent" style="height: 180px;">
			<div style="text-align: left;">
				<br>
				<form id="findForm" action="<%=pu.getUrl()%>doFind.action?" name="findForm"
					method="post">
					<table border="0" cellpadding="0" cellspacing="0">
					  <tr>
							<td width="80" align="center">报表名</td>
							<td width="120"><input type='text' name='report_name_find'
								id='report_name_find' value='' /></td>
						</tr>
						<tr>
							<td align="center">模板路径</td>
							<td><input type='text' name='templet_path_find' id='templet_path_find'
								value='' /></td>
						</tr>
						<tr>
							<td align="center">参数值</td>
							<td><input type='text' name='where_sql_find' id='where_sql_find'
								value='' />
							</td>
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
		$('#report_id').attr('value', rowData.report_id);
		$('#report_name').attr('value', rowData.report_name);
		$('#templet_path').attr('value', rowData.templet_path);
		$('#excute_sql').attr('value', rowData.excute_sql);
		$('#where_sql').attr('value', rowData.where_sql);
		$('#remark').attr('value', rowData.remark);


	}
	//新增时清除--扩展
	function clearWin() {
		$('#report_name').attr('value', '');
		$('#templet_path').attr('value',  '');
		$('#excute_sql').attr('value',  '');
		$('#where_sql').attr('value',  '');
		$('#remark').attr('value', '');
	}
	function checkSave() {
		
		var report_name=$('#report_name').val() ;
		var templet_path=$('#templet_path').val() ;
		
		var excute_sql=$('#excute_sql').val() ;
 
		if(report_name==''||report_name==undefined){
			alert("报表名不能空!");
			return;
		}		
		else if(templet_path==''||templet_path==undefined){
			alert("报表路径不能空!");
			return;
		}
		else if(excute_sql==''||excute_sql==undefined){
			alert("报表执行语句不能空!");
			return;
		}
		var where_sql=$('#where_sql').val();
		var sql_params=excute_sql.split("?");
		var params=where_sql.split(",");
		if(sql_params.length-1==params.length||(sql_params.length==1&&params.length==1)){
			save('editForm');
		}
		else
			alert("输入的参数个数不匹配!");
	}
	//查找时使用--扩展
	function doFind() {
		var where = "";	
		where = getWhereLike(where, "report_name_find",true);
		where = getWhereLike(where, "templet_path_find",true);
		where = getWhereLike(where, "where_sql_find",true);	
		if (where.length > 0)
			where = " where " + where+" and report_type=1";
		else
			where =" where report_type=1";
		var url = document.getElementById("findForm").action + "&where="
				+ where;
		submitFrom("findForm", url);
	}
</script>
</html>
