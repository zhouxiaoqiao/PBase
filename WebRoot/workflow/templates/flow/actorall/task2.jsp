<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>测试流程</title>
<%@ include file="/common/meta.jsp"%>
</head>
<%
	String processId = request.getParameter("processId");
	String processName = request.getParameter("processName");
%>

<body>
	<form id="inputForm" action="${ctx}/flow/actorall/task1/save"
		method="post">
		<input type="hidden" name="processId" value="<%=processId%>" /> <input
			type="hidden" name="processName" value="<%=processName%>" />
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1"><span>任务2处理列表：</span></td>
				<td class="td_table_2" colspan="3"><input type="checkbox"
					name="actorIds" value="snaker" />snaker <input type="checkbox"
					name="actorIds" value="test" />test</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr align="left">
				<td colspan="1"><input type="submit" class="button_70px"
					name="submit" value="提交">&nbsp;&nbsp; <input type="button"
					class="button_70px" name="reback" value="取消"
					onclick="history.back()"></td>
			</tr>
		</table>
	</form>
</body>
</html>