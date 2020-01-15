<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 	String path = request.getContextPath();  
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>登陆页面</title>
		<link href="<%=basePath %>assets/css/login/style.css" rel="stylesheet" type="text/css" media="all"/>
		<link rel="icon" href="<%=basePath %>assets/image/sys/logo.png" type="image/x-icon"/>
		<script type="text/javascript" src="<%=basePath %>assets/js/common/jquery-3.1.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>assets/js/login/login.js"></script> 
	</head>
	<body>
		<div class="login">
			<h2>管理系统后台登录</h2>
			<div class="login-top">
				<h1>LOGIN FORM</h1>
				<form id="login_form" action="<%=basePath %>login" method="post">
					<input type="text" name="username" value="User ID" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'User Id';}">
					<input type="password" name="password" value="password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'password';}">
			    </form>
			    <div class="forgot">
			    	<a href="#">forgot Password</a>
			    	<input type="submit" value="Login" onclick="login()">
			    </div>
			</div>
			<div class="login-bottom">
				<h3>New User &nbsp;<a href="#">Register</a>&nbsp Here</h3>
			</div>
		</div>	
	</body>
</html>

