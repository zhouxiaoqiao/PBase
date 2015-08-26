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
	<button onclick="displayAdd()">新增</button>
	<button onclick="displayEdit()">修改</button>
	<button onclick="delNode()">删除</button>
	<button onclick="flushNode()">刷新</button>
	<div id="editTreeDiv" style="display: none">
		<table style="width: 100%; font-size: 12px;">
			<tr id="pid_tr">
				<td>上级部门名</td>
				<td><input type="text" id="pname" name="pname"
					readonly="readonly" /> <input type="hidden" id="pid" name="pid" /></td>
			</tr>
			<tr>
				<td id="editID_td">新建部门名</td>
				<td><input type="text" id="title" name="title" /></td>
			</tr>
			<tr>
				<td><button onclick="submitOper()">确定</button>
					<button onclick="editCancel()">取消</button></td>
			</tr>
		</table>
	</div>
	<div id="radioDiv"></div>


</body>
<script type="text/javascript">
	var oper = '';
	function displayAdd() {
		document.getElementById("editTreeDiv").style.display = "";
		document.getElementById("title").value = "";
		oper = 'add';
		document.getElementById("pid_tr").style.display = "";
		document.getElementById("editID_td").innerHTML = "新建部门名";
 
	}
	function displayEdit() {
		document.getElementById("editTreeDiv").style.display = "";
		document.getElementById("pid_tr").style.display = "none";
		oper = 'edit';
		document.getElementById("editID_td").innerHTML = "编辑部门名";
		document.getElementById("title").value =document.getElementById("pname").value;
	}
	function submitOper() {
		var pid = document.getElementById("pid").value;
		if (pid != '') {
			if (oper == 'add')
				addNode();
			else if (oper == 'edit')
				editNode();
			oper = '';
			document.getElementById("editTreeDiv").style.display = "none";
		} else
			alert("上级部门名不能为空，请选择！");
	}

	function editCancel() {
		document.getElementById("title").value = "";
		document.getElementById("editTreeDiv").style.display = "none";
	}
	function setRadioData(data) {
		if (typeof (data) == 'string') {
			data = jQuery.parseJSON(data);
		}
		$('#pname').val(data.name);
		$('#pid').val(data.id);
		if(oper=='edit')
		{  
		  $('#title').val(data.title);	  
		 }
		window.parent.frames['dept_belong'].location.reload();
	}
</script>
</html>