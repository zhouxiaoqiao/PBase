<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<title>树形菜单</title>
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/js/xloadtree/xtree.css" />
<script type="text/javascript" src="<%=basePath%>/js/user/user_util.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/xloadtree/xtree.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/xloadtree/xloadtree.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/xloadtree/xmlextras.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/xloadtree/menuTreeCheck.js"></script>
<style type="text/css">
body {
	background: white;
	color: black;
}

#editTreeDiv {
	border: 0px solid #CCC;
	float: left;
	margin-left: 10px;
}

#radioDiv {
	border: 0px solid #CCC;
	float: left;
}
</style>

</head>
<script type="text/javascript">
 var user_id ='';
  if(parent['admin_user'].document.getElementById('user_id_hidden')!=null)
   user_id =''+parent['admin_user'].document.getElementById('user_id_hidden').value;

   $(function() {
   	loadTreeUrl=loadTreeUrl+ "?user_id="+user_id;
   	initTreeUrl=initTreeUrl+"?user_id="+user_id;
   	if ( user_id!=undefined&&user_id != '')		 
   		loadTree();
   });
</script>
<body>
	<div id="con"></div>
	<input type="hidden" id="user_id" name="user_id" />
	<button onclick="saveUserRightNode()">授权保存</button>
	<button onclick="JSRefresh()">刷新</button>
	<div id="editTreeDiv" style="display: none">
		<table style="width: 100%; font-size: 12px;">
			<tr id="pid_tr">
				<td>父节菜单名</td>
				<td><input type="text" id="pname" name="pname"
					readonly="readonly" /> <input type="hidden" id="pid" name="pid" /></td>
			</tr>
			<tr>
				<td id="editID_td">新建菜单名</td>
				<td><input type="text" id="title" name="title" /></td>
			</tr>
			<tr>
				<td>链接URL</td>
				<td><input type="text" id="url" name="url" />
					<button onclick="submitOper()">确定</button></td>
			</tr>

		</table>
	</div>
	<div id="checkDiv"></div>


</body>

</html>