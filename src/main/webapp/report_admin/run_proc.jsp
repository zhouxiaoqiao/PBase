<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String url = basePath + "/adminReport/adminReport_";
	String sql = null;
	if (request.getAttribute("sql") != null)
		sql = (String) request.getAttribute("sql");
	String whereSQL_print = "no";
	if (request.getAttribute("whereSQL_print") != null) {
		whereSQL_print = (String) request
				.getAttribute("whereSQL_print");
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>jquery demo</title>
<!-- 自定义css js-->
<%@include file="/common/use_js.jsp" %> 
<style type="text/css">
#pagerId input {
	height: 20px;
}
</style>
<script type="text/javascript">
	var sql="<%=sql%>"; 
	///功能权限控制
	var add_display=false;
	var edit_display=true;
	var query_display=true;
	var del_display=false;
	var print_display=false;
	var init_url='<%=url%>initPage.action?sql=' + sql + '&report_type=2';
	$(function() {
		$("#dataTableId").jqGrid({
			url : init_url,
			datatype : "json",
			type : 'post',
			height : "auto",
			colNames : [ '序号', '存储过程名', '参数值', '被执行过程名', '执行' ],
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
				width : 30,
				editable : true,
				editoptions : {
					readonly : false,
					rows : "1",
					cols : "65"
				}
			}, {
				name : 'where_sql',
				index : 'where_sql',
				sortable : true,
				editable : true,
				width : 20
			}, {
				name : 'excute_sql',
				index : 'excute_sql',
				sortable : true,
				editable : true,
				width : 30
			}, {
				name : 'code',
				index : 'code',
				formatter : cLink,
				width : 10,
				editable : true
			} ],
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
			caption : "员工信息表"
		});
		jQuery("#dataTableId").jqGrid('navGrid', '#pagerId', {
			add : false,
			del : false,
			search : false,
			edit : false
		}), initOperate(add_display, edit_display, query_display, del_display,
				print_display, "report_id")
	});
	function cLink(cellvalue, options, rowObject) {

		var templet_path = rowObject.templet_path;
		var excute_sql = rowObject.excute_sql;
		var where_sql = rowObject.where_sql;
		return "<a href=\"javascript:void(0)\" onclick=\"excuteProc('" + excute_sql + "','" + where_sql
				+ "')\">开始执行</a>";
	}
</script>
</head>
<body onload="initUserWin()">
	<table id="dataTableId"></table>
	<div id="pagerId" class="scroll"></div>
	<input type="hidden" id="method" value="">
	<input type="hidden" id="printURL"
		value="<%=basePath%>frameset?__report=report/emp.rptdesign&whereSQL=<%=whereSQL_print%>">
	<div id="editUnitDiv" class="editWin" style="height: 50px;">
		<div id="titeWin" class="ewTop" style="float: left;">
			<span id="titeSpan">新 增</span>
		</div>
		<div
			style="float: left; width: 3%; height: 15px; background: #F0FFFF;"
			onclick='closeWin(".editWin")'>
			<img src="<%=basePath%>image/close.png" align="middle" />
		</div>

		<div id="tbdiv" class="ewContent" style="height: 50px;">
			<div style="text-align: middle;">
				<br>
				<form id="editForm" action="<%=url%>" name="editForm" method="post">

					<table border="0" cellpadding="0" cellspacing="0" width="240px">
						<tr>
							<td align="center">
							    <input type='hidden' name='report_id' id='report_id' /> 
								<input type='hidden' name='report_name'id='report_name' /> 
								<input type='hidden' name='excute_sql' id='excute_sql' /> 
								<input name='where_sql' id='where_sql' type='hidden' /> 
								<input type='hidden' name='report_type' id='report_type' value='2' />
								</td>
							<td>
								<div id="where_sql_div" style="width: 300px; align: middle;">
								</div>
							</td>
						</tr>

					</table>
				</form>
			</div>
			<div style="text-align: center;">
				<br> <input type='button' onclick="checkSave()"
					style='width: 50px' value='保存'> <input type='button'
					onclick="clearWin();" style='width: 50px' value='清除'>

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
			<img src="<%=basePath%>image/close.png" align="middle" />
		</div>
		<div id="find_div" class="fwContent" style="height: 180px;">
			<div style="text-align: left;">
				<br>
				<form id="findForm" action="<%=url%>doFind.action" name="findForm"
					method="post">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="80" align="center">报表名</td>
							<td width="120"><input type='text' name='report_name_find'
								id='report_name_find' value='' /></td>
						</tr>
						<tr>
							<td align="center">模板路径</td>
							<td><input type='text' name='templet_path_find'
								id='templet_path_find' value='' /></td>
						</tr>
						<tr>
							<td align="center">参数值</td>
							<td><input type='text' name='where_sql_find'
								id='where_sql_find' value='' /></td>
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
		$('#method').attr('value', 'updateRun');
		$('#report_id').attr('value', rowData.report_id);
		$('#report_name').attr('value', rowData.report_name);
		$('#excute_sql').attr('value', rowData.excute_sql);
		$('#where_sql').attr('value', rowData.where_sql);
		$('#remark').attr('value', rowData.remark);

		///
		var w_a = rowData.where_sql.split(',');
		var div = document.getElementById("where_sql_div");
		div.innerHTML = "";
		var w_sql = '';
		//div.appendChild(aElement);//将input file加入div容器
		for ( var i = 0; i < w_a.length; i++) {
			var key_val = w_a[i].split(':');
			if (key_val.length == 2) {
				var div_child = document.createElement("div"); //
				var span_s = document.createElement("span"); // 
				span_s.innerHTML = '第 ' + (i + 1) + '个参数';
				div_child.appendChild(span_s);
				var aElement = document.createElement("input"); //创建input
				aElement.name = '' + key_val[0];
				aElement.id = 'input_id' + i;
				aElement.type = "text";//设置类型为
				if (key_val[0] == 'date')
					aElement.onfocus = function() {
						WdatePicker();
					};
				aElement.value = key_val[1];
				div_child.appendChild(aElement);
				var span_e = document.createElement("span"); //创建input
				span_e.innerHTML = '类型:' + key_val[0];
				div_child.appendChild(span_e);
				div.appendChild(div_child);

			} else
				alert("参数列表不合法！");
		}

	}
	//新增时清除--扩展
	function clearWin() {
		var where_sql = '' + $('#where_sql').val();
		var w_a = where_sql.split(',');
		for ( var i = 0; i < w_a.length; i++) {
			$('#input_id' + i).attr('value', '');
		}
	}
	//查找时使用--扩展
	function doFind() {
		var where = "";
		where = getWhereLike(where, "report_name_find", true);
		where = getWhereLike(where, "where_sql_find", true);
		if (where.length > 0)
			where = " where " + where;
		var url = document.getElementById("findForm").action + "?where="
				+ where;
		submitFrom("findForm", url);
	}

	function checkSave() {
		var excute_sql = $('#excute_sql').val();
		var where_sql = $('#where_sql').val();
		var w_a = where_sql.split(',');
		where_sql = '';
		for ( var i = 0; i < w_a.length; i++) {
			if (where_sql == '')
				where_sql = document.getElementById('input_id' + i).name + ':'
						+ document.getElementById('input_id' + i).value;
			else
				where_sql = where_sql + ','
						+ document.getElementById('input_id' + i).name + ':'
						+ document.getElementById('input_id' + i).value;

		}
		document.getElementById("where_sql").value = where_sql;
		var sql_params = excute_sql.split("?");
		var params = where_sql.split(",");
		if (sql_params.length - 1 == params.length) {
			save('editForm');
		} else
			alert("输入的参数个数不匹配!");
	}

	function excuteProc(excute_sql, where_sql) {
		var sql_params = excute_sql.split("?");
		var params = where_sql.split(","); 
		var excuteProc_url='<%=url%>?method=excuteProc';
		if (sql_params.length - 1 == params.length
				|| (sql_params.length == 1 && params.length == 1)) {
			excute_sql = excute_sql.replace('(', '');
			excute_sql = excute_sql.replace('?', '');
			excute_sql = excute_sql.replace(',', '');
			excute_sql = excute_sql.replace(')', '');
			excuteProc_url = excuteProc_url + "&procedureName=" + excute_sql
					+ "&inparams=" + where_sql;
			$.ajax({
				type : 'POST',
				url : excuteProc_url,
				dataType : 'json',
				success : function(msg) {
					if (msg.success)
						alert(msg.success);
					else
						alert('返回失败！');
				}
			});

		} else {
			alert("参数个数不对应!");
			return;
		}

	}
</script>
</html>
