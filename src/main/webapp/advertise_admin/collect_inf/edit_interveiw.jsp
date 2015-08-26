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
<title>用户信息修改</title>
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
				class="table_all_border" cellspacing="0"
				style="margin-bottom: 0px; border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">应聘人员信息编辑</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0"
				cellspacing="0" style="margin-top: 0px">

				<tr>
					<td class="td_table_1">中文姓名</td>
					<td class="td_table_2"><input type='text' name='china_name'
						id='china_name' /> <input type="hidden" id="eid" name='eid'
						value="" /></td>
					<td class="td_table_1">学历学位</td>
					<td class="td_table_2"><input type='text' name='degree'
						id='degree' value='' /></td>
					<td class="td_table_1">期望月薪</td>
					<td class="td_table_2"><input name="hope_salary" id="hope_salary"/></td>
				</tr>
				<tr>
					<td class="td_table_1">入职年龄</td>
					<td class="td_table_2"><input type='text' name='age' id='age'
						value='' /></td>
					<td class="td_table_1">毕业院校</td>
					<td class="td_table_2"><input type='text' name='college'
						id='college' value='' /></td>
					<td class="td_table_1">实际月薪</td>
					<td class="td_table_2"><input type="text" name='fact_salary'
						id='fact_salary' /></td>
				</tr>
				<tr>
				  <td class="td_table_1">出生日期</td>
					<td class="td_table_2"><input type='text' name='born_date'
						id='born_date' onFocus="WdatePicker()" /></td>
					<td class="td_table_1">毕业专业</td>
					<td class="td_table_2"><input type='text' name='professional'
						id='professional' value='' /></td>				
					<td class="td_table_1">兴趣爱好</td>
					<td class="td_table_2"><input type="text" name='interest'
						id='interest' value='' /></td>
				</tr>
				<tr>
					<td class="td_table_1">性 别</td>
					<td class="td_table_2"><select name="sex" id="sex">
                      <option value="1">男</option>
                      <option value="-1">女</option>
                    </select></td>
					<td class="td_table_1">毕业时间</td>
					<td class="td_table_2"><input name='graduate_time'
						id='graduate_time' value='' onFocus="WdatePicker();" /></td>
					<td class="td_table_1">到岗时间</td>
					<td class="td_table_2"><input type='text' name='come_date'
						id='come_date' value='' onFocus="WdatePicker();"/></td>
				</tr>
				<tr>
					<td class="td_table_1">民族 </td>
					<td class="td_table_2"><input type='text' name='nationality' id='nationality' /></td>
					<td class="td_table_1">证书登记</td>
					<td class="td_table_2"><input type="text" name='qual_cert'
						id='qual_cert' /></td>
					<td class="td_table_1">招聘方式</td>
					<td class="td_table_2"><select name="adver_way" id="adver_way">
                      <option value="1">校园招聘</option>
                      <option value="2">社会招聘</option>
                      <option value="3">内部推荐</option>
                    </select></td>
				</tr>
				<tr>
					<td class="td_table_1">联系电话</td>
					<td class="td_table_2"><input type='text' name='tel' id='tel' /></td>
					<td class="td_table_1">面试人</td>
					<td class="td_table_2"><input type="text"
						name='interview_person' id='interview_person' value='' /></td>
					<td class="td_table_1">面试日期</td>
					<td class="td_table_2"><input type="text"
						name='interview_date' id='interview_date' value='' onFocus="WdatePicker();" /></td>
				</tr>
				<tr>
					<td class="td_table_1">通讯地址</td>
					<td class="td_table_2"><textarea name='address'  cols="40"
						id='address'></textarea></td>
					<td class="td_table_1">工作经验</td>
					<td class="td_table_2"><textarea name='work_history' cols="40"
						id='work_history'></textarea></td>
					<td class="td_table_1">面试人评价</td>
					<td class="td_table_2"><label>
					  <textarea name="textarea" name='interview_evaluate'
						id='interview_evaluate'  cols="40"></textarea>
					</label></td>
				</tr>
				<tr>
					<td colspan="6" class="td_table_2"><div align="center">
							<input name="button" type='button' class="button_70px"
								onclick='save("editForm")' value='保存'> <input
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
      getDataByAjax("<%=getEditData_url%>", "initEditDataRet", data_ret);

	}

	function initEditDataRet(data_ret) {
		document.getElementById("eid").value = data_ret.eid;
		document.getElementById("china_name").value = data_ret.china_name;
		document.getElementById("hope_salary").value = data_ret.hope_salary;
		document.getElementById("age").value = data_ret.age;
		document.getElementById("college").value = data_ret.college;
		document.getElementById("graduate_time").value = data_ret.graduate_time;
		document.getElementById("fact_salary").value = data_ret.fact_salary;
		document.getElementById("born_date").value = data_ret.born_date;
		document.getElementById("professional").value = data_ret.professional;

		document.getElementById("interest").value = data_ret.interest;
		document.getElementById("work_history").value = data_ret.work_history;
		document.getElementById("come_date").value = data_ret.come_date;		
		document.getElementById("nationality").value = data_ret.nationality;	
		document.getElementById("address").value = data_ret.address;		
		document.getElementById("qual_cert").value = data_ret.qual_cert;
		document.getElementById("interview_person").value = data_ret.interview_person;

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
