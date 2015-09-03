<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<base href="<%=basePath%>">

<link rel="stylesheet" href="css/base.css">

	<script src="js/jquery-1.4.2.min.js"></script>
	
	
	<link rel="stylesheet" href="js/jquery-ui/btn_dialog/development-bundle/themes/redmond/jquery-ui-1.8.13.custom.css">
	<script src="js/jquery-ui/btn_dialog/js/jquery-ui-1.8.13.custom.min.js"></script>
	
	<style type="text/css">
	.btnH{height:22px;}
	</style>
	
	 
	<script>
	$(function() {
		
				
		initbugDialog();
		
		$("button").button();
	});
	
	function initbugDialog(){
		$( "#dialog:ui-dialog" ).dialog( "destroy" );	
		$( "#diaDiv" ).dialog({
			autoOpen: false,
			height:140,
			modal: false,
			buttons: {
				
				"确定": function() {
					$( this ).dialog( "close" );
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
	
	function showDialog(){
		$( "#diaDiv" ).dialog( "open" );
	}
	</script>
</head>
<body>

<div >

<button class="btnH" onclick='showDialog()'>测试</button>

<div id="diaDiv" title="请选择日期">
	
	These items will be permanently deleted and cannot be recovered. Are you sure?
	
</div>



</div><!-- End demo -->





</body>
</html>