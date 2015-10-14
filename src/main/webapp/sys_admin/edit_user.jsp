<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String url = basePath + "/sysAdmin/adminUser_getUserInf.action";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="css/user_win.css" />
<script type="text/javascript" src="<%=basePath%>js/user/user_util.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.js"></script>
<title>用户信息修改</title>
<%@ include file="/common/meta.jsp"%>
<link rel="stylesheet" href="<%=basePath%>/styles/css/style.css"
	type="text/css" media="all" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/styles/wbox/wbox/wbox.css" />

<script type="text/javascript">
   //alert(parent.document.getElementById('user_id').value);
    var init_url="<%=url%>?user_id="+ parent.document.getElementById('user_id').value;
	$.ajax({
		type : 'POST',
		url : init_url,
		dataType : 'json',
		success : function(msg) {
			if (msg.success) {
				var rs = msg.record;
				$('#user_name').val(rs[0].username);
				$('#full_name').val(rs[0].fullname);
				$('#password').val(rs[0].password);
				$('#password_esure').val(rs[0].password);
				$('#dept_id').val(rs[0].org);
				$('#dept_name').val(rs[0].dept_name);
				$('#email').val(rs[0].email);

			}

			else
				alert('返回失败！');
		}
	});
</script>
</head>
<body>
	<table width="100%" border="0" align="center" cellpadding="0"
		class="table_all_border" cellspacing="0"
		style="margin-bottom: 0px; border-bottom: 0px">
		<tr>
			<td class="td_table_top" align="center">用户信息修改</td>
		</tr>
	</table>
	<form id="editForm" action="<%=basePath%>/sysAdmin/adminUser_updateUser.action" name="editForm" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			class="table_all_border" cellspacing="0"
			style="margin-bottom: 0px; border-bottom: 0px">

			<tr>
				<td class="td_table_1">用户姓名</td>
				<td class="td_table_2"><input type='text' name='full_name'
					id='full_name' value='' /> <input type='hidden' name='user_name'
					id='user_name' /></td>
				<td class="td_table_1">所属部门</td>
				<td class="td_table_2"><input type='text' name='dept_name' 
					id='dept_name' disabled onclick="selectDept()" /> <input
					type='hidden' name='dept_id' id='dept_id' /></td>
				<td class="td_table_1">邮 箱</td>
				<td class="td_table_2"><input type="text" id="email"
					name="email"></td>
			</tr>
			<tr>
				<td class="td_table_1">之前密码</td>
				<td class="td_table_2"><input type='password'
					name='password_before' id='password_before' disabled /></td>
				<td class="td_table_1">新密码</td>
				<td class="td_table_2"><input type='password' name='password'
					id='password' /></td>
				<td class="td_table_1">确认密码</td>
				<td class="td_table_2"><input type='password'
					name='password_esure' id='password_esure' /></td>
			</tr>
			<tr>
				<td colspan="6" class="td_table_2"><div align="center">
				<input type="submit" class="button_70px" name="submit" onclick="return checkForm() ;" value="保存">
	           <input type='button' onclick="clearWin();" style='width: 50px' value='清除'>
					</div></td>
			</tr>
		</table>
	</form>


</body>
<script type="text/javascript">
	function checkForm() {
		
		var password_esure=document.getElementById("password_esure").value;
		var password=document.getElementById("password").value;
		 if(password_esure==''||password==''){
				alert("密码不能空!");
				 return false;
			}else if(password_esure!=password){
	    	 alert("密码不一致!");
	    	 return false;
	    }
	 
	}
</script>
</html>
