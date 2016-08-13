<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal-dialog">
	<div class="panel panel-success">
		<div class="panel-heading">编辑事件</div>
		<div class="panel-body">
			<div class="col-lg-12">
				<form class="form-horizontal" id="todoitem_form">
					<div class="form-group">
						<label class="col-lg-3 control-label">日程内容：</label>
						<div class="col-lg-9">
							<input type="text" class="form-control" id="title" name="title">
						</div>
					</div>
					<div class="form-group" id="event_start_data">
						<label class="col-lg-3 control-label">开始时间：</label>
						<div class="col-lg-5">
							<div class="input-group date">
								<span class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</span>
								<input type="text" class="form-control" id="startDate" name="startDate">
							</div>
						</div>
						<div class="col-lg-4">
							<div class="input-group clockpicker" data-autoclose="true">
								<input type="text" class="form-control" value="" id="startTime" name="startTime">
								<span class="input-group-addon">
									<span class="fa fa-clock-o"></span>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group" id="event_end_data">
						<label class="col-lg-3 control-label">结束时间：</label>
						<div class="col-lg-5">
							<div class="input-group date">
								<span class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</span>
								<input type="text" class="form-control" id="endDate" name="endDate">
							</div>
						</div>
						<div class="col-lg-4">
							<div class="input-group clockpicker" data-autoclose="true" data-align="top">
								<input type="text" class="form-control" value="" id="endTime" name="endTime">
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
							<button type="button" class="btn btn-warning">删除</button>
						</label>
						<div class="col-lg-9 control-label">
							<button class="btn btn-primary" type="button" id="event_confirm">确定</button>
							<button class="btn btn-white" type="button" id="event_cancel">取消</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		$('.i-checks').iCheck({
			checkboxClass : 'icheckbox_square-green',
			radioClass : 'iradio_square-green',
		});

		$('.clockpicker').clockpicker({
			'default' : 'now',
			parentEl : '#modal-form'
		});

		$('#event_cancel').click(function(e) {
			$("#modal-form").modal("toggle");
		});

		$("#event_confirm").click(function() {
			console.log($.toJSON($("#todoitem_form").serializeObject()));
			$.ajax({
				type : "POST",
				url : "/toDoItem/addToDoItem",
				data : $.toJSON($("#todoitem_form").serializeObject()),
				contentType : "application/json",
				dataType : "json",
				success : function(data) {
					$("#modal-form").modal("toggle");
				}
			})
		});

		$('#event_start_data .input-group.date').datepicker({
			todayBtn : "linked",
			keyboardNavigation : false,
			forceParse : false,
			calendarWeeks : true,
			autoclose : true,
			todayHighlight : true
		});

		$('#event_end_data .input-group.date').datepicker({
			todayBtn : "linked",
			keyboardNavigation : false,
			forceParse : false,
			calendarWeeks : true,
			autoclose : true,
			todayHighlight : true
		});
	});
</script>