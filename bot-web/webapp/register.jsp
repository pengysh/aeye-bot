<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>会议预定系统 | 注册</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

</head>

<body class="gray-bg">

	<div class="middle-box text-center loginscreen animated fadeInDown">
		<div style="vertical-align: middle">
			<h3>注册会议预定系统用户</h3>
			<form id="registerForm" class="m-t" role="form" action="/register.bot" method="post">
				<div class="form-group">
					<input id="accountId" name="accountId" type="text"
						class="form-control" placeholder="账号" required="">
				</div>
				<div class="form-group">
					<input id="name" name="name" type="text" class="form-control"
						placeholder="姓名" required="">
				</div>
				<div class="form-group">
					<input id="email" name="email" type="email" class="form-control"
						placeholder="邮箱" required="">
				</div>
				<div class="form-group">
					<input id="password" name="password" type="password"
						class="form-control" placeholder="密码" required="">
				</div>
				<button type="submit" class="btn btn-primary block full-width m-b">注册</button>

				<p class="text-muted text-center">
					<small>如果您已有系统账号?</small>
				</p>
				<a class="btn btn-sm btn-white btn-block" href="login.jsp">登录</a>
			</form>
			<p class="m-t">
				<small>会议预定系统第一版3 &copy; 2016</small>
			</p>
		</div>
	</div>

	<!-- Mainly scripts -->
	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<!-- iCheck -->
	<script src="js/plugins/iCheck/icheck.min.js"></script>
	<script>
		$(document).ready(function() {
			$('.i-checks').iCheck({
				checkboxClass : 'icheckbox_square-green',
				radioClass : 'iradio_square-green',
			});
		});
	</script>
</body>

</html>
