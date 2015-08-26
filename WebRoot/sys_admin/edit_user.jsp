<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String url = basePath + "sys_admin/admin_user.do";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="css/user_win.css"/>
<script type="text/javascript" src="<%=basePath%>js/user/user_util.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.js"></script>
<title>用户信息修改</title>
<style type="text/css">

</style>
 
<script type="text/javascript">
  //  alert(parent.document.getElementById('user_id').value);
    var init_url="<%=url%>?method=getUserInf&user_id="+ parent.document.getElementById('user_id').value;
    var js_basePath='<%=basePath%>';
	$.ajax({
		type : 'POST',
		url : init_url,
		dataType : 'json',
		success : function(msg) {
			if (msg.success) {
				var rs = msg.record;
				$('#user_name').val(rs[0].user_name);
				$('#password_bak').val(rs[0].password);
				$('#dept_id').val(rs[0].dept_id);
				$('#dept_name').val(rs[0].dept_name);
				
			}

			else
				alert('返回失败！');
		}
	});
</script>
</head>
<body>
	<input type="hidden" id="method" value="">
	<input type="hidden" id="password_bak" value="">
	<div id="tbdiv" class="ewContent" style="text-align: center;">
		<form id="editForm" action="<%=url%>" name="editForm" method="post">
			<table border="0" class="imagetable">
				<tr>
					<td width="120" align="center">用户姓名</td>
					<td><input type='text' name='user_name' id='user_name' size="30" style="width:180px;"
						value='' />
						<input type='hidden' name='user_id' id='user_id' value='' /></td>
				</tr>
				<tr>
					<td width="120" align="center">之前密码</td>
					<td><input type='password' name='password_before'
						id='password_before' value='' size="30" style="width:180px;"/></td>
				</tr>
				<tr>
					<td width="120" align="center">新密码</td>
					<td><input type='password' name='password' id='password'
						value=''  size="30" style="width:180px;"/></td>
				</tr>
				<tr>
					<td width="120" align="center">确认密码</td>
					<td><input type='password' name='password_esure'
						id='password_esure' value=''size="30" style="width:180px;" /></td>
				</tr>
				<tr>
					<td width="120" align="center">所属部门</td>
					<td>
					<input type='text' name='dept_name' id='dept_name' value=''onclick="selectDept()"/>
					 <input type='hidden' name='dept_id' id='dept_id' />
					</td>
				</tr>
				<tr>
					<td align="center">邮 箱</td>
					<td><input type="text" id="email" name="email"
						value="" size="30" style="width:180px;"></td>
				</tr>
				<tr>
					<td align="center">上传照片</td>
					<td><input type="file" id="problems_FJ3" name="problems_FJ3"
						value="上传图片" ></td>
				</tr>
				<tr>
					<td align="center"><input type='button'
						onclick="checkAndsave()" style='width: 50px' value='保存'></td>
					<td><input type='button' onclick="clearWin();"
						style='width: 50px' value='清除'></td>
				</tr>
			</table>
		</form>
	</div>

</body>
<script type="text/javascript">
	function checkAndsave() {
		 $('#user_id').val(parent.document.getElementById('user_id').value);
		var password_before = document.getElementById('password_before').value;
		var password_esure = document.getElementById('password_esure').value;
		var password_new = document.getElementById('password').value;
		if (document.getElementById('user_name').value == '') {
			alert("用户名不能为空！");
			return;
		} else if ($('#password_bak').val() != password_before) {
			alert("旧密码不正确！");
			return;
		} else if (password_esure!=''&&password_esure != password_new) {

			alert("新密码与确认密码不一致！");
			return;
		} else{
			// /提交至后台
		
				var url = document.getElementById("editForm").action ;
				url = url + "?method=update&submitMode=alone" ;
				document.getElementById("editForm").action = url;
				document.getElementById("editForm").submit();
				alert(" 成功！");
		}
			
	}
 
</script>
</html>
