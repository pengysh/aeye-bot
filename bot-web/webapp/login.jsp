<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>会议预定系统 | 登录</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="font-awesome/css/font-awesome.css" rel="stylesheet">
<!-- Toastr style -->
<link href="css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
</head>

<body class="gray-bg">
	<div class="loginColumns animated fadeInDown">
		<div class="row">

			<div class="col-md-6">
				<h2 class="font-bold">会议室预定系统</h2>

				<p>交互式界面设计</p>

				<p>自然语言沟通</p>

				<p>人工智能</p>

				<p>
					<small>敬请关注.</small>
				</p>

			</div>
			<div class="col-md-6">
				<div class="ibox-content">
					<form id="loginForm" class="m-t" role="form" method="post"
						action="/login.bot">
						<div class="form-group">
							<input id="accountId" name="accountId" type="accountId"
								class="form-control" placeholder="账号" required="">
						</div>
						<div class="form-group">
							<input id="password" name="password" type="password"
								class="form-control" placeholder="密码" required="">
						</div>
						<button type="submit" class="btn btn-primary block full-width m-b">登录</button>

						<a href="#"> <small>忘记密码?</small>
						</a>

						<p class="text-muted text-center">
							<small>您是否已经注册账号?</small>
						</p>
						<a class="btn btn-sm btn-white btn-block" href="register.jsp">创建账号</a>
					</form>
					<p class="m-t"></p>
				</div>
			</div>
		</div>
		<hr />
		<div class="row">
			<div class="col-md-6">Copyright AsiaInfo Company </div>
			<div class="col-md-6 text-right">
				<small>Â© 2016-2017</small>
			</div>
		</div>
	</div>

	<!-- Mainly scripts -->
	<script src="js/jquery-2.1.1.js"></script>
	<!-- Toastr -->
	<script src="js/plugins/toastr/toastr.min.js"></script>
	<script>
		var messageStr = "${message}";
		if(messageStr){
			toastr.options = {
				closeButton : true,
				progressBar : true,
				showMethod : 'slideDown',
				timeOut : 4000
			};
			toastr.error(messageStr);
		}
	</script>
</body>

</html>
