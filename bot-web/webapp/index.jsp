<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>会议预定系统</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<!-- Toastr style -->
<link href="css/plugins/toastr/toastr.min.css" rel="stylesheet">
<!-- Ladda style -->
<link href="css/plugins/ladda/ladda-themeless.min.css" rel="stylesheet">
</head>

<body class="top-navigation" onload="initWebSocket();">
	<div id="wrapper">
		<div id="page-wrapper" class="gray-bg">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top white-bg" role="navigation"
					style="margin-bottom: 0">
					<div class="navbar-header">
						<a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
							href="#"><i class="fa fa-bars"></i> </a>
						<form role="search" class="navbar-form-custom"
							action="search_results.html">
							<div class="form-group">
								<input type="text" placeholder="搜一搜乐趣在其中..."
									class="form-control" name="top-search" id="top-search">
							</div>
						</form>
					</div>
					<ul class="nav navbar-top-links navbar-right">
						<li><span class="m-r-sm text-muted welcome-message">欢迎使用会议预定系统.</span>
						</li>
						<li class="dropdown"><a class="dropdown-toggle count-info"
							data-toggle="dropdown" href="#"> <i class="fa fa-weixin"></i>
							<span class="label label-primary">16</span>
						</a>
						</li>
							
						<li class="dropdown"><a class="dropdown-toggle count-info"
							data-toggle="dropdown" href="#"> <i class="fa fa-calendar"></i>
						</a>
							<ul class="dropdown-menu dropdown-messages">
								<li>
									<div class="dropdown-messages-box">
										<div class="media-body">
											<small class="pull-right">20分钟前</small> 完成<strong>日历控件</strong>
											开发. <br> <small class="text-muted">2016年8月6日 上午
												9:30 - 下午 5:30</small>
										</div>
									</div>
								</li>
								<li class="divider"></li>
								<li>
									<div class="dropdown-messages-box">
										<div class="media-body">
											<small class="pull-right">20分钟前</small> 完成<strong>日历控件</strong>
											开发. <br> <small class="text-muted">2016年8月6日 上午
												9:30 - 下午 5:30</small>
										</div>
									</div>
								</li>
								<li class="divider"></li>
								<li>
									<div class="dropdown-messages-box">
										<div class="media-body">
											<small class="pull-right">20分钟前</small> 完成<strong>日历控件</strong>
											开发. <br> <small class="text-muted">2016年8月6日 上午
												9:30 - 下午 5:30</small>
										</div>
									</div>
								</li>
								<li class="divider"></li>
								<li>
									<div class="text-center link-block">
										<a href="mailbox.html"> <i class="fa fa-calendar"></i> <strong>全部日历信息</strong>
										</a>
									</div>
								</li>
							</ul></li>
						<li class="dropdown"><a class="dropdown-toggle count-info"
							data-toggle="dropdown" href="#"> <i class="fa fa-envelope"></i>
								<span class="label label-warning">16</span>
						</a>
							<ul class="dropdown-menu dropdown-messages">
								<li>
									<div class="dropdown-messages-box">
										<a href="profile.html" class="pull-left"> <img alt="image"
											class="img-circle" src="img/a7.jpg">
										</a>
										<div class="media-body">
											<small class="pull-right">46h ago</small> <strong>Mike
												Loreipsum</strong> started following <strong>Monica Smith</strong>. <br>
											<small class="text-muted">3 days ago at 7:58 pm -
												10.06.2014</small>
										</div>
									</div>
								</li>
								<li class="divider"></li>
								<li>
									<div class="dropdown-messages-box">
										<a href="profile.html" class="pull-left"> <img alt="image"
											class="img-circle" src="img/a4.jpg">
										</a>
										<div class="media-body ">
											<small class="pull-right text-navy">5h ago</small> <strong>Chris
												Johnatan Overtunk</strong> started following <strong>Monica
												Smith</strong>. <br> <small class="text-muted">Yesterday
												1:21 pm - 11.06.2014</small>
										</div>
									</div>
								</li>
								<li class="divider"></li>
								<li>
									<div class="dropdown-messages-box">
										<a href="profile.html" class="pull-left"> <img alt="image"
											class="img-circle" src="img/profile.jpg">
										</a>
										<div class="media-body ">
											<small class="pull-right">23h ago</small> <strong>Monica
												Smith</strong> love <strong>Kim Smith</strong>. <br> <small
												class="text-muted">2 days ago at 2:30 am -
												11.06.2014</small>
										</div>
									</div>
								</li>
								<li class="divider"></li>
								<li>
									<div class="text-center link-block">
										<a href="mailbox.html"> <i class="fa fa-envelope"></i> <strong>Read
												All Messages</strong>
										</a>
									</div>
								</li>
							</ul></li>
						<li><a href="login.html"> <i class="fa fa-sign-out"></i>
								Log out
						</a></li>
					</ul>

				</nav>
			</div>
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="row">
					<div class="col-lg-12">
						<div class="ibox chat-view">
							<div class="ibox-content">
								<div class="row">
									<div class="col-md-9 ">
										<div id="messageContent" class="chat-discussion"></div>
									</div>
									<div class="col-md-3">
										<div class="tabs-container">
											<ul class="nav nav-tabs chat-statistic">
												<li class="active"><a data-toggle="tab"
													href="#persontalk"><i class="fa fa-user"></i> 个人</a></li>
												<li class=""><a data-toggle="tab" href="#grouptalk"><i
														class="fa fa-group"></i> 群聊</a></li>
											</ul>
											<div class="chat-users tab-content">
												<div class="tab-pane active" id="persontalk"></div>

												<div class="tab-pane" id="grouptalk">
													<div class="chat-user">
														<div class="chat-user-name">
															<a href="#">群聊暂不可用</a>
														</div>
													</div>
													<div class="chat-user">
														<div class="chat-user-name">
															<a href="#">群聊暂不可用</a>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>

							</div>

						</div>

					</div>

				</div>


			</div>
			<div class="footer">
				<div class="col-lg-12">
					<div class="chat-message-form">
						<div class="form-group">
							<input id="question" type="text"
								class="form-control message-input"
								onkeypress="if(event.keyCode==13){sendMsg()}"> <input
								id="personAccountId" type="hidden">
						</div>
					</div>
				</div>
			</div>

		</div>
		<div class="small-chat-box fadeInRight animated">

			<div class="heading" draggable="true">
				<small class="chat-date pull-right"> 02.19.2015 </small> Small chat
			</div>

			<div class="content">

				<div class="left">
					<div class="author-name">
						Monica Jackson <small class="chat-date"> 10:02 am </small>
					</div>
					<div class="chat-message active">Lorem Ipsum is simply dummy
						text input.</div>

				</div>
				<div class="right">
					<div class="author-name">
						Mick Smith <small class="chat-date"> 11:24 am </small>
					</div>
					<div class="chat-message">Lorem Ipsum is simpl.</div>
				</div>
				<div class="left">
					<div class="author-name">
						Alice Novak <small class="chat-date"> 08:45 pm </small>
					</div>
					<div class="chat-message active">Check this stock char.</div>
				</div>
				<div class="right">
					<div class="author-name">
						Anna Lamson <small class="chat-date"> 11:24 am </small>
					</div>
					<div class="chat-message">The standard chunk of Lorem Ipsum</div>
				</div>
				<div class="left">
					<div class="author-name">
						Mick Lane <small class="chat-date"> 08:45 pm </small>
					</div>
					<div class="chat-message active">I belive that. Lorem Ipsum
						is simply dummy text.</div>
				</div>


			</div>
			<div class="form-chat">
				<div class="input-group input-group-sm">
					<input type="text" class="form-control"> <span
						class="input-group-btn">
						<button class="btn btn-primary" type="button">Send</button>
					</span>
				</div>
			</div>

		</div>
		<div id="small-chat">

			<span class="badge badge-warning pull-right">5</span> <a
				class="open-small-chat"> <i class="fa fa-comments"></i>

			</a>
		</div>
	</div>
	<div id="adduser-form" class="modal fade" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="row">
						<div class="full-height-scroll">
							<div class="table-responsive">
								<table class="table table-striped table-hover">
									<tbody id="adduser-form-contacts">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Mainly scripts -->
	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

	<!-- Custom and plugin javascript -->
	<script src="js/inspinia.js"></script>
	<script src="js/plugins/pace/pace.min.js"></script>

	<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<!-- Toastr -->
	<script src="js/plugins/toastr/toastr.min.js"></script>
	<script src="static/js/reconnecting-websocket.min.js"></script>
	<script src="static/js/bot_sock.js"></script>

	<!-- Ladda -->
	<script src="js/plugins/ladda/spin.min.js"></script>
	<script src="js/plugins/ladda/ladda.min.js"></script>
	<script src="js/plugins/ladda/ladda.jquery.min.js"></script>

	<script type="text/javascript">
		$(document).ready(
				function() {
					$("#adduser-form-button").click(
							function() {
								resp = $.ajax({
									type : 'GET',
									url : "/getContacts.bot",
									async : false
								});

								var response = eval('(' + resp.responseText
										+ ')');

								$("#adduser-form-contacts").empty();
								for (var i = 0; i < response.length; i++) {
									getContacts(response[i].accountId,
											response[i].name,
											response[i].email,
											response[i].state);
								}

								$('#adduser-form').modal('show');
							});
				});
	</script>
</body>

</html>
