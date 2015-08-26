<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
<title>拖放</title>



<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.js"></script>
<style type="text/css">
.win {
	width: 500px;
	height: 600px;
	background: #70a8d2;
	border-radius: 8px;
	box-shadow: 0px 0px 5px 10px;
	opacity: 0.8;
	position: absolute;
	left: 0;
	top: 0;
	display: none
}

.win .wTop {
	height: 30px;
	width: 100%;
	cursor: move
}

.win .content {
	height: 570px;
	width: 100%;
	border-radius: 5px;
	background: white
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>

<script language="javascript" type="text/javascript">
	$(function() {
		//拖拽 
		dragAndDrop();
		//初始化位置 
		initPosition();
		//点击按钮 
		clickShowBtn();
	});
	//拖拽 
	function dragAndDrop() {
		var _move = false;//移动标记 
		var _x, _y;//鼠标离控件左上角的相对位置 
		$(".wTop").mousedown(function(e) {
			_move = true;
			_x = e.pageX - parseInt($(".win").css("left"));
			_y = e.pageY - parseInt($(".win").css("top"));
			//$(".wTop").fadeTo(20,0.5);//点击开始拖动并透明显示 
		});
		$(document).mousemove(function(e) {
			if (_move) {
				var x = e.pageX - _x;//移动时鼠标位置计算控件左上角的绝对位置 
				var y = e.pageY - _y;
				$(".win").css({
					top : y,
					left : x
				});//控件新位置 
			}
		}).mouseup(function() {
			_move = false;
			//$(".wTop").fadeTo("fast",1);//松开鼠标后停止移动并恢复成不透明 
		});
	}
	//初始化拖拽div的位置 
	function initPosition() {
		//计算初始化位置 
		var itop = ($(document).height() - $(".win").height()) / 2;
		var ileft = ($(document).width() - $(".win").width()) / 1.8;
		//设置被拖拽div的位置 
		$(".win").css({
			top : itop,
			left : ileft
		});
	}
	//点击显示按钮 
	function clickShowBtn() {
		$("#show").click(function() {
			$(".win").show(1000);
		});

		$("#hidden").click(function() {
			$(".win").hide(1000);
		});
	}
</script>
<body>

	<button id="show">显示</button>
	<div class="win">
		<div class="wTop">
			<p style="float: right; margin: 5px 5px 0px 0px; color: white"
				id="hidden">X</p>
		</div>
		<div class="content"></div>
	</div>

</body>
</html:html>
