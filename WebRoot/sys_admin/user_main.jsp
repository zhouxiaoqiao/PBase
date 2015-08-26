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
<title>菜单管理</title>
<frameset cols="65%,*">
	    <frame id="admin_user" name="admin_user" src="<%=basePath%>sys_admin/admin_user.jsp">
		<frame id="treeCheckRight" name="treeCheckRight" src="<%=basePath%>sys_admin/admin_right.jsp">

</frameset>
 
</html>
