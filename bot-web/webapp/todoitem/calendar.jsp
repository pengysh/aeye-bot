<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>会议预定系统</title>

<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../font-awesome/css/font-awesome.css" rel="stylesheet">

<link href="../css/plugins/iCheck/custom.css" rel="stylesheet">

<link href="../css/plugins/fullcalendar/fullcalendar.min.css" rel="stylesheet">
<link href="../css/plugins/fullcalendar/fullcalendar.print.css" rel='stylesheet' media='print'>

<link href="../css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="../css/plugins/clockpicker/clockpicker.css" rel="stylesheet">

<link href="../css/animate.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<style>
#calendar {
	max-width: 900px;
	margin: 0 auto;
}
</style>
</head>

<body class="top-navigation">
	<div id="page-wrapper" class="gray-bg">
		<div class="wrapper wrapper-content">
			<div class="row animated fadeInDown">
				<div class="col-lg-12">
					<div class="ibox float-e-margins">
						<div class="ibox-content">
							<div id="calendar"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="modal-form" class="modal fade" aria-hidden="true">
	</div>
	<!-- Mainly scripts -->
	<script src="../js/plugins/fullcalendar/moment.min.js"></script>
	<script src="../js/jquery-2.1.1.js"></script>
	<script src="../js/jquery.json.js"></script>
	<script src="../js/jquery.serialize.js"></script>
	
	<script src="../js/jsrender.min.js"></script>
	<script src="../js/jsviews.min.js"></script>
	
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="../js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	
	<script src="../static/js/application.js"></script>

	<!-- Custom and plugin javascript -->
	<script src="../js/inspinia.js"></script>
	<script src="../js/plugins/pace/pace.min.js"></script>

	<!-- jQuery UI custom -->
	<script src="../js/jquery-ui.custom.min.js"></script>

	<!-- Full Calendar -->
	<script src="../js/plugins/fullcalendar/fullcalendar.min.js"></script>
	<script src="../js/plugins/fullcalendar/zh-cn.js"></script>

	<!-- iCheck -->
    <script src="../js/plugins/iCheck/icheck.min.js"></script>
   <!-- Data picker -->
   <script src="../js/plugins/datapicker/bootstrap-datepicker.js"></script>
   <script src="../js/plugins/datapicker/locales/bootstrap-datepicker.zh-CN.js"></script>
    <!-- Clock picker -->
    <script src="../js/plugins/clockpicker/clockpicker.js"></script>

	<script>
		$(document).ready(function() {
			var accountId = getCookie("AccountId");
			
			var date = new Date();
			var d = date.getDate();
			var m = date.getMonth();
			var y = date.getFullYear();

			$('#calendar').fullCalendar({
				lang : 'zh-cn',
				editable : true,
				selectable : true,
				droppable : true,
				eventLimit: true,
				header : {
					left : 'prev,next today',
					center : 'title',
					right : 'month,agendaWeek,agendaDay'
				},
				businessHours : {
					dow : [ 1, 2, 3, 4, 5 ],
					start : '9:00',
					end : '18:00',
				},
				
				eventDrop: function(event, delta, revertFunc) {
					console.log("event.end：" + delta.days());
					console.log("event.end：" + delta.hours());
					console.log("event.end：" + delta.minutes());
					
					$.ajax({
						type : "POST",
						url : "/toDoItem/drogToDoItem",
						data : $.toJSON({itemId : event.id,dayOffset : delta.days(),hourOffset : delta.hours(),minuteOffset : delta.minutes()}),
						contentType : "application/json",
						dataType : "text",
						success : function(data) {
						},
					});
		    	},
				
				eventResize: function(event, delta, revertFunc) {
					alert(1111);
		    	},

				events: '/toDoItem/getToDoItem',
				dayClick: function(date, jsEvent, view, resourceObj) {
					var selDate =$.fullCalendar.moment(date);
					console.log("Clicked on：" + date.format('YYYY年MM月DD日') + selDate);
					console.log('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
					console.log('Current view: ' + view.name);
					
					var dataSrouce = [{
						dialogHead : '待办事项-创建',
						startDate : date.format('YYYY年MM月DD日'),
						startTime : date.format('HH:mm'),
						endDate : date.format('YYYY年MM月DD日'),
						endTime : date.format('HH:mm')
					}];
					
					var html = $("#todoItemTemplate").render(dataSrouce);
					$("#modal-form").html(html);
					$("#modal-form").modal("toggle");
					
					$("#todoitem_accountId").val(accountId);
					$('#todoitem_delete').hide();
					
					todoItemTemplateEventBind();
					
					$("#todoitem_form").on('submit', function(){
						$.ajax({
							type : "POST",
							url : "/toDoItem/addToDoItem",
							data : $.toJSON($("#todoitem_form").serializeObject()),
							contentType : "application/json",
							dataType : "text",
							success : function(data) {
								$('#calendar').fullCalendar( 'refetchEvents' );
								$("#modal-form").modal("toggle");
							},
						});
						
						return false;
					});
		    	},
		    	
				eventClick: function(calEvent, jsEvent, view) {
					console.log(calEvent.start.format('HH:mm'));
					console.log(calEvent.end.format('HH:mm'));
					var dataSrouce = [{
						dialogHead : '待办事项-编辑',
						itemId : calEvent.id,
						title : calEvent.title,
						allDay : calEvent.allDay,
						startDate : calEvent.start.format('YYYY年MM月DD日'),
						startTime : calEvent.start.format('HH:mm'),
						endDate : calEvent.end.format('YYYY年MM月DD日'),
						endTime : calEvent.end.format('HH:mm')
					}];
					
					var html = $("#todoItemTemplate").render(dataSrouce);
					$("#modal-form").html(html);
					$("#modal-form").modal("toggle");
					
					$("#todoitem_accountId").val(accountId);
					
					todoItemTemplateEventBind();
					
					$("#todoitem_form").on('submit', function(){
						console.log($.toJSON($("#todoitem_form").serializeObject()));
						$.ajax({
							type : "POST",
							url : "/toDoItem/editToDoItem",
							data : $.toJSON($("#todoitem_form").serializeObject()),
							contentType : "application/json",
							dataType : "text",
							success : function(data) {
								$('#calendar').fullCalendar( 'refetchEvents' );
								$("#modal-form").modal("toggle");
							},
						});
						
						return false;
					});
				}
			});
			
			function todoItemTemplateEventBind(){
				$('.i-checks').iCheck({
					checkboxClass : 'icheckbox_square-green',
					radioClass : 'iradio_square-green',
				});

				$('.clockpicker').clockpicker({
					'default' : 'now',
					parentEl : '#modal-form'
				});

				$('#todoitem_delete').click(function() {
					$.ajax({
						type : "GET",
						url : "/toDoItem/deprecatedToDoItem/" + $('#todoitem_itemId').val(),
						success : function(data) {
							$('#calendar').fullCalendar( 'refetchEvents' );
							$("#modal-form").modal("toggle");
						},
					});
				});
				
				$('#todoitem_cancel').click(function(e) {
					$("#modal-form").modal("toggle");
				});
				
				$('#todoitem_start_data .input-group.date').datepicker({
					todayBtn : "linked",
					keyboardNavigation : false,
					forceParse : false,
					calendarWeeks : true,
					autoclose : true,
					todayHighlight : true
				});

				$('#todoitem_end_data .input-group.date').datepicker({
					todayBtn : "linked",
					keyboardNavigation : false,
					forceParse : false,
					calendarWeeks : true,
					autoclose : true,
					todayHighlight : true
				});
			}
		});
	</script>
<!-- 定义JsRender模版 -->
<script id="todoItemTemplate" type="text/x-jsrender">
<div class="modal-dialog">
	<div class="panel panel-success">
		<div class="panel-heading">{{:dialogHead}}</div>
		<div class="panel-body">
			<div class="col-lg-12">
				<form class="form-horizontal" id="todoitem_form">
					<div class="form-group">
						<label class="col-lg-3 control-label">日程内容：</label>
						<div class="col-lg-9">
							<input type="text" class="form-control" id="title" name="title" required="" value="{{:title}}">
							<input type="hidden" class="form-control" id="todoitem_accountId" name="accountId">
							<input type="hidden" class="form-control" id="todoitem_itemId" name="itemId" value="{{:itemId}}">
						</div>
					</div>
					<div class="form-group" id="todoitem_start_data">
						<label class="col-lg-3 control-label">开始时间：</label>
						<div class="col-lg-5">
							<div class="input-group date">
								<span class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</span>
								<input type="text" class="form-control" id="startDate" name="startDate" required="" value="{{:startDate}}">
							</div>
						</div>
						<div class="col-lg-4">
							<div class="input-group clockpicker" data-autoclose="true">
								<input type="text" class="form-control" id="startTime" name="startTime" required="" value="{{:startTime}}">
								<span class="input-group-addon">
									<span class="fa fa-clock-o"></span>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group" id="todoitem_end_data">
						<label class="col-lg-3 control-label">结束时间：</label>
						<div class="col-lg-5">
							<div class="input-group date">
								<span class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</span>
								<input type="text" class="form-control" id="endDate" name="endDate" required="" value="{{:endDate}}">
							</div>
						</div>
						<div class="col-lg-4">
							<div class="input-group clockpicker" data-autoclose="true" data-align="top">
								<input type="text" class="form-control" id="endTime" name="endTime" required="" value="{{:endTime}}">
								<span class="input-group-addon">
									<span class="fa fa-clock-o"></span>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-3 col-lg-9">
							<div class="i-checks">
								<label>
									<input type="radio" value="option1" name="a"> 全天
								</label>
								<label>
									<input type="radio" checked="" value="option2" name="a"> 结束时间
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">
							<button type="button" class="btn btn-warning" id="todoitem_delete">删除</button>
						</label>
						<div class="col-lg-9 control-label">
							<button class="btn btn-primary" type="submit" id="todoitem_confirm">确定</button>
							<button class="btn btn-white" type="button" id="todoitem_cancel">取消</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</script>
</body>
</html>