<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<title>树形菜单</title>
<%@include file="/common/use_js.jsp" %> 
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>js/xloadtree/xtree.css" />
<script type="text/javascript" src="<%=basePath%>js/xloadtree/xtree.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/xloadtree/xloadtree.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/xloadtree/xmlextras.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/xloadtree/menuTreeRadio.js"></script>

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
	<div id="tree_operDiv">
	<div id="con" style="font-size: 12px;"></div>
	<button onclick="displayAdd()">新增</button>
	<button onclick="displayEdit()">修改</button>
	<button onclick="delNode()">删除</button>
	<button onclick="flushNode()">刷新</button>
	</div>
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
				<td><input type="text" id="url" name="url" /><input
					type="hidden" id="url_bak" value=""></td>
			</tr>
			<tr>
				<td>action</td>
				<td><input type="text" id="action" name="action" />
				 </td>
			</tr>
			<tr>
				<td><button onclick="submitOper()">确定</button>
					<button onclick="editCancel()">取消</button></td>
			</tr>
		</table>
	</div>
	<div id="radioDiv"  style="font-size: 12px;"></div>


</body>
<script type="text/javascript">
	var oper = '';
	function displayAdd() {
		document.getElementById("tree_operDiv").style.display = "none";
		document.getElementById("editTreeDiv").style.display = "";
		document.getElementById("title").value = "";
		oper = 'add';
		document.getElementById("pid_tr").style.display = "";
		document.getElementById("editID_td").innerHTML = "新建菜单名";
		$('#url').val("");
	}
	function displayEdit() {		
		document.getElementById("tree_operDiv").style.display = "none";
		document.getElementById("editTreeDiv").style.display = "";
		document.getElementById("pid_tr").style.display = "none";
		oper = 'edit';
		document.getElementById("editID_td").innerHTML = "编辑菜单名";
		document.getElementById("title").value = document
				.getElementById("pname").value;
		//$('#action').val($('#action_bak').val());

	}
	function submitOper() {
		document.getElementById("tree_operDiv").style.display = "";
		var pid = document.getElementById("pid").value;
		if (pid != '') {
			if (oper == 'add')
				addNode();
			else if (oper == 'edit')
				editNode();
			oper = '';
			document.getElementById("editTreeDiv").style.display = "none";
		} else
			alert("父菜单名不能为空，请选择！");
	}

	function editCancel() {
		document.getElementById("title").value = "";
		document.getElementById("url").value = "";
		document.getElementById("editTreeDiv").style.display = "none";
	}
	function setRadioData(data) {
		if (typeof (data) == 'string') {
			data = jQuery.parseJSON(data);
		}
		$('#pname').val(data.title);
		$('#pid').val(data.id);
		if (oper != 'add') {
			$('#title').val(data.title);
			$('#url').val(data.url);
			$('#action').val(data.action);
			$('#url').val(data.url);
		}
	
		window.parent.frames['right_belong'].location.reload();
	}
</script>
</html>