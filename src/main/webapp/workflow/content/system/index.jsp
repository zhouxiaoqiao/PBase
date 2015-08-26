<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>人力资源管理</title>
<base href="<%=basePath%>">
<!-- jQuery UI css,jqGrid需要用到 -->
<link rel="stylesheet" type="text/css" href="css/base.css" />

<base href="<%=basePath%>">
<style type="text/css">
* {
	margin: 0;
	padding: 0;
	border: 0;
}

body {
	font: 12px/130% verdana, geneva, arial, helvetica, sans-serif, 宋体;
}

li {
	list-style: none;
}

.clearfix:after {
	content: " ";
	display: block;
	height: 0;
	clear: both;
	visibility: hidden;
}

.clearfix {
	display: inline-block;
}

a:link {
	color: #000;
	text-decoration: none;
}

a:visited {
	color: #000;
	text-decoration: none;
}

a:hover {
	color: #000;
	text-decoration: none;
}

.menu {
	width: 100%;
	height: 30px;
	background: #AEE1E3;
	margin: 0 auto;
}

.menusel {
	float: left;
	width: 100px;
	position: relative;
	height: 25px;
	background: #AEE1E3;
	line-height: 25px;
	margin-left: 1px;
	*margin-left: 0px;
	_margin-left: -1px;
}

.menusel h2 {
	font-size: 12px;
}

.menusel a {
	display: block;
	text-align: center;
	width: 100px;
	border: 1px solid #a4a4a4;
	height: 25px;
	border-bottom: 1px solid #a4a4a4;
	position: relative;
	z-index: 2;
}

.menusel a:hover {
	border: 1px solid #a4a4a4;
	border-bottom: 1px dashed #eeeeee;
	position: relative;
	z-index: 2;
	height: 25px;
}

.ahover a {
	border-bottom: 1px dashed #eeeeee;
	background: #eeeeee;
}

.position {
	position: absolute;
	z-index: 1;
}

.menusel ul {
	width: 125px;
	background: #C0EBDF;
	border: 1px solid #a4a4a4;
	margin-top: -1px;
	position: relative;
	z-index: 1;
	display: none;
}

.menusel .block {
	display: block;
}

.typeul li {
	border-bottom: 1px dashed #a4a4a4;
	width: 125px;
	position: relative;
	float: left;
}

.typeul li a {
	border: none;
	width: 125px;
}

.typeul li a:hover {
	border: none;
	background: #7ED6BD;
}

.typeul {
	margin-left: 0;
}

.typeul ul {
	left: 125px;
	top: 0;
	position: absolute;
}

.fli {
	margin-left: -1px;
	border-left: #eeeeee solid 1px;
}

.menusel .lli {
	border: none;
}
</style>

</head>
<body>
	
	
	<iframe src="${ctx }/top" id="topFrame" name="topFrame" scrolling="no"
		id="topFrame" title="topFrame" height="80" width="100%"></iframe>
	<div align="center">
		<input type="hidden" id="user_id" name="user_id"
			value="<%=request.getSession().getAttribute("user_id")%>" />
	</div>
	<%
		out.print(request.getSession().getAttribute("strSub"));
	%>
	<div id='path_chin' style='background: #C0EBDF;font-weight:bold;font-size: 14px;height: 20px;'>
		登录用户:<%=request.getSession().getAttribute("userName")%>
	</div>
	<iframe src="<%=basePath%>workflow/content/system/main.jsp" id="main_Frame"
		name="main_Frame" height="450" width="100%"></iframe>
  <iframe src="<%=basePath%>workflow/content/system/bottom.jsp" id="bottom_Frame"
		name="bottom_Frame" height="60" width="100%"></iframe>

 
</body>

<script type="text/javascript">   
     var menuLen='<%=request.getSession().getAttribute("menuLen")%>';
	 for(var x = 1; x < menuLen; x++){
		 var menuid = document.getElementById("menu"+x);
		 menuid.num = x;
		 type();
		 }
	 function type(){
		 var menuh2 = menuid.getElementsByTagName("h2");
		 var menuul = menuid.getElementsByTagName("ul");
		 var menuli = menuul[0].getElementsByTagName("li");
		 menuh2[0].onmouseover = show;
		 menuh2[0].onmouseout = unshow;
		 menuul[0].onmouseover = show;
		 menuul[0].onmouseout = unshow;
	  function show(){
		    menuul[0].className = "clearfix typeul block"
		 }
	  function unshow(){
		 menuul[0].className = "typeul"
		 }
		 for(var i = 0; i < menuli.length; i++)
		  {
		    menuli[i].num = i;
		    var liul = menuli[i].getElementsByTagName("ul")[0];
		    if(liul){
		   typeshow()
		   }
		  }
	 function typeshow(){
		 menuli[i].onmouseover = showul;
		 menuli[i].onmouseout = unshowul;
		 }
	 function showul(){
		 menuli[this.num].getElementsByTagName("ul")[0].className = "block";
		 }
	 function unshowul(){
		  menuli[this.num].getElementsByTagName("ul")[0].className = "";
		   }
	 }
	 
	 
     // 打开新页
 function openPage(url,action,path_chin){
  var s=' '+url;
  if(s.indexOf("java")>0){
     url='#';
     return false;
  }
  else 
    {
	    document.getElementById("path_chin").innerText=path_chin; 
        url='<%=basePath%>' + url;
			document.getElementById("main_Frame").src = url+"?action="+action;
	 
			
			//SetWinHeight(document.getElementById("mainFrame"));
		}
	}
	//自适应高度
	function SetWinHeight(obj) {
		var win = obj;
		if (document.getElementById) {
			if (win && !window.opera) {
				if (win.contentDocument
						&& win.contentDocument.body.offsetHeight) {
					win.height = win.contentDocument.body.offsetHeight;
				} else if (win.Document && win.Document.body.scrollHeight) {
					win.height = win.Document.body.scrollHeight;
				}
				// alert(win.height);
				//   alert(win.contentDocument.body.offsetHeight);
				//    alert(win.Document.body.scrollHeight);
			}
		}
	}
</script>

</html>
