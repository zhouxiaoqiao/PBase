<%@ page language="java"
	import="java.util.*,com.yunyuan.util.PageUtil,net.sf.json.JSONObject"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String action = (String) request.getParameter("action");
	String method = (String) request.getParameter("method");
	PageUtil pu = new PageUtil(request, action);
	 
	String getInitEmp_url = basePath+"/adminEmployee/employeeLeave_getEmpInf.action";

	//System.out.println("getEditData_url===" + getEditData_url);
%>
<head>
<base href="<%=basePath%>">
<%@ include file="/common/meta.jsp"%>
<link rel="stylesheet" href="<%=basePath%>/styles/css/style.css"
	type="text/css" media="all" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/styles/wbox/wbox/wbox.css" />
<%@include file="/common/use_js.jsp"%>
<title>职员转正申请</title>
<style type="text/css">
</style>

<script type="text/javascript">
var init_url="<%=getInitEmp_url%>?user_account="+ parent.document.getElementById('user_id').value;
$.ajax({
	type : 'POST',
	url : init_url,
	dataType : 'json',
	success : function(msg) {
		if (msg.success) {
			var rs = msg.record;
			$('#eid').val(rs[0].eid);
			$('#staff_name').val(rs[0].staff_name);			
			$('#dept_name').val(rs[0].dept_name);
			$('#dept_id').val(rs[0].dept_id);
			$('#join_time').val(rs[0].join_time);

		}

		else
			alert('返回失败！');
	}
});
</script>
</head>
<body>
	<input type="hidden" id="method" value="<%=method%>">
	<div id="tbdiv">
		<form id="editForm" action="<%=pu.getUrl()%>" name="editForm"
			method="post">
			 <table width="100%" border="0" align="center" cellpadding="0"
					class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">
						员工转正申请
					</td>
				</tr>
	     	</table>
			<table  class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0" style="margin-top: 0px">

				<tr>
					<td class="td_table_1">中文姓名</td>
					<td class="td_table_2"><input type='text' name='staff_name'
						id='staff_name' disabled/> <input type="hidden" id="eid" name='eid'
						value="" /></td>
					<td class="td_table_1">入职时间</td>
					<td class="td_table_2"><input type="text" name='join_time'
						id='join_time' disabled onFocus="WdatePicker()" /></td>
					<td class="td_table_1">应转正日期</td>
					<td class="td_table_2"><input type='text' name='join_date'
						id='join_date' onFocus="WdatePicker()" /></td>
				</tr>
			
		
				<tr>
					<td class="td_table_1">所做项目</td>
					<td class="td_table_2"><input type="text" name='address'
						id='address' /></td>
					<td class="td_table_1">使用期总结</td>
					<td colspan="3" class="td_table_2"><textarea
							name="work_history" id="work_history" cols="60"></textarea></td>
				</tr>
				<tr>
					<td colspan="6" class="td_table_2"><div align="center">
							<input name="button" type='button' class="button_70px"
								onclick='save("editForm")' value='申请'> <input
								name="button" type='button' class="button_70px"
								onClick="clearWin();" value='清除'> <input type="button"
								class="button_70px" name="reback" value="返回"
								onClick="history.back()">
						</div></td>
				</tr>
			</table>
		</form>
	</div>

</body>
<script type="text/javascript">
 

	function clearWin() {

		document.getElementById("join_date").value = "";
		document.getElementById("staff_name").value = "";
	 

	}
</script>
</html>
