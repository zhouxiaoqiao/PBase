<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Title</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="余额,账务,帐务">
<meta http-equiv="description" content="余额管理界面">

<link rel="stylesheet" type="text/css"
	href="js/jquery-easyui-1.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="js/jquery-easyui-1.4.2/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="js/jquery-easyui-1.4.2/themes/demo.css">
<script type="text/javascript"
	src="js/jquery-easyui-1.4.2/jquery.min.js"></script>

<script type="text/javascript"
	src="js/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(function() {
		base_Bath = '${pageContext.request.contextPath}';
	});
</script>
</head>
<body class="easyui-layout">

	<script type="text/javascript"
		src="js/jquery-easyui-1.4.2/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/user/base_user.js"></script>
	<div id="top_div" data-options="region:'north'" style="height: 80px;">
	</div>
	<div data-options="region:'west',split:true" style="width: 200px">
	<input type="hidden" id="user_id" name="user_id"
			value="<%=request.getSession().getAttribute("user_id")%>" />
		<jsp:include page="/workflow/content/system/left.jsp"></jsp:include>
		
	</div>
	<div id="index_center" data-options="region:'center'">
		<jsp:include page="/workflow/content/system/middle.jsp"></jsp:include>
	</div>
	<div data-options="region:'south'" style="height: 20px;align:center"><jsp:include
			page="/workflow/content/system/bottom.jsp"></jsp:include></div>
</body>
<script type="text/javascript">
	$("#top_div").load("${pageContext.request.contextPath}/top");
</script>
</html>