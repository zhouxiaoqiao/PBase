<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="css/common.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工档案</title>
<frameset cols="200,*">
	<frame id="display_dept" name="display_dept"
		src="<%=basePath%>employee_admin/on_job/display_dept.jsp">
	<frame id="display_employee" name="display_employee"
		src="<%=basePath%>employee_admin/on_job/display_employee.jsp?action=/adminEmployee/employeeFile_">
</frameset>
<body>
</body>

</html>
