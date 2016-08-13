function todoItemTemplateEventBind() {
	$('.i-checks').iCheck({
	checkboxClass : 'icheckbox_flat-green',
	radioClass : 'iradio_flat-green'
	});

	$('.clockpicker').clockpicker({
	'default' : 'now',
	parentEl : '#div_todoitem_modal'
	});

	$('#todoitem_delete').click(function() {
		$.ajax({
		type : "GET",
		url : "/toDoItem/deprecatedToDoItem/" + $('#todoitem_id').val(),
		success : function(data) {
			$('#div_todo_main').fullCalendar('refetchEvents');
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