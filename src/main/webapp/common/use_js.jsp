<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String use_path = request.getContextPath();
	String use_basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ use_path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="css/user_win.css">
<script type="text/javascript" src="<%=use_basePath%>/js/user/user_win.js"></script>
<script type="text/javascript" src="<%=use_basePath%>/js/user/user_util.js"></script>
<!-- jQuery UI css,jqGrid需要用到 -->
<link rel="stylesheet" type="text/css" href="css/base.css"/>
<link rel="stylesheet" type="text/css"
	href="<%=use_basePath%>/js/jquery-ui/css/redmond/jquery-ui-1.8.2.custom.css"/>
<link rel="stylesheet" type="text/css"
	href="<%=use_basePath%>/js/jqGrid/css/ui.jqgrid.css"/>
<script type="text/javascript" src="<%=use_basePath%>js/jquery-1.4.2.js"></script>
<script src="<%=use_basePath%>/js/jqGrid/js/i18n/grid.locale-cn.js"
	type="text/javascript"></script>
<!-- jqGrid js 文件 -->
<script type="text/javascript"
	src="<%=use_basePath%>/js/jqGrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="<%=use_basePath%>/js/My97DatePicker/WdatePicker.js"></script>
<body>

</body>
</html>

