<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<title>树形菜单</title>
	
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>js/xloadtree/xtree.css" />
<script type="text/javascript" src="<%=basePath%>js/user/user_util.js"></script>
<script type="text/javascript" src="<%=basePath%>js/xloadtree/xtree.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/xloadtree/xloadtree.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/xloadtree/xmlextras.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/xloadtree/deptTreeRadio.js"></script>	
	
<style type="text/css">
body {
	background: white;
	color: black;
}

#editTreeDiv {
	border: 0px solid #CCC;
	float: left;
	margin-left: 10px;
	align: left;
}

#radioDiv {
	border: 0px solid #CCC;
	float: left;
}
</style>

</head>
<body>
	<div id="con"></div>

	<div id="radioDiv"></div>

<button onclick="closeWin()">关闭</button>
</body>
<script type="text/javascript">

	
	function setRadioData(data) {
		if (typeof (data) == 'string') {
			data = jQuery.parseJSON(data);
		}

        window.opener.document.getElementById('dept_id').value=data.id;
        window.opener.document.getElementById('dept_name').value=data.name;
		window.close();
	}
	
	function closeWin(){
		
		window.close();
	}
</script>
</html>