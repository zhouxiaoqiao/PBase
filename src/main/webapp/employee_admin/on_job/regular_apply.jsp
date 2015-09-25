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
	String keyID = request.getParameter("keyID");
	String getEditData_url = pu.getUrl();
	getEditData_url = getEditData_url + "editData.action?keyID="
			+ keyID;
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
						id='staff_name' /> <input type="hidden" id="eid" name='eid'
						value="" /></td>
					<td class="td_table_1">入职时间</td>
					<td class="td_table_2"><input type="text" name='join_time'
						id='join_time' value='' onFocus="WdatePicker()" /></td>
					<td class="td_table_1">应转正日期</td>
					<td class="td_table_2"><input type='text' name='born_date'
						id='born_date' onFocus="WdatePicker()" /></td>
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
 if("<%=method%>"=="update"){
	var data_ret; 
      getDataByAjax("<%=getEditData_url%>", "initDataRet", data_ret);

	}

	function initDataRet(data_ret) {
		document.getElementById("eid").value = data_ret.eid;
		document.getElementById("staff_name").value = data_ret.staff_name;
		document.getElementById("user_account").value = data_ret.user_account;
		document.getElementById("age").value = data_ret.age;
		document.getElementById("college").value = data_ret.college;
		document.getElementById("graduate_time").value = data_ret.graduate_time;
		document.getElementById("dept_name").value = data_ret.dept_name;
		document.getElementById("dept_id").value = data_ret.dept_id;
		document.getElementById("salary_month").value = data_ret.salary_month;
		document.getElementById("graduate_time").value = data_ret.graduate_time;
		document.getElementById("status").value = data_ret.status;
		document.getElementById("work_history").value = data_ret.work_history;
		document.getElementById("born_date").value = data_ret.born_date;
		document.getElementById("professional").value = data_ret.professional;
		document.getElementById("work_year").value = data_ret.work_year;
		document.getElementById("job_name").value = data_ret.job_name;
		document.getElementById("address").value = data_ret.address;
		document.getElementById("join_time").value = data_ret.join_time;
		
		

	}

	function clearWin() {

		document.getElementById("staff_name").value = "";
		document.getElementById("user_account").value = "";
		document.getElementById("age").value = "";
		document.getElementById("college").value = "";
		document.getElementById("graduate_time").value = "";
		document.getElementById("dept_name").value = "";
		document.getElementById("dept_id").value = "";
		document.getElementById("salary_month").value = "";

		document.getElementById("status").value = "";
		document.getElementById("work_history").value = "";
		document.getElementById("born_date").value = "";
		document.getElementById("professional").value = "";
		document.getElementById("work_year").value = "";
		document.getElementById("job_name").value = "";
		document.getElementById("address").value = "";

	}
</script>
</html>
