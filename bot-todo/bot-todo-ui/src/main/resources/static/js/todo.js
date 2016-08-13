$(document).ready(function() {
	$("#div_todoitem_modal").load("/todo/todoitem.html");

	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();

	$('#div_todo_main').fullCalendar({
	lang : 'zh-cn',
	editable : true,
	selectable : true,
	droppable : true,
	eventLimit : true,
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

	eventDrop : function(event, delta, revertFunc) {
		console.log("event.end：" + delta.days());
		console.log("event.end：" + delta.hours());
		console.log("event.end：" + delta.minutes());

		$.ajax({
		type : "POST",
		url : "/todo/todoitem/drogToDoItem",
		data : {
		id : event.id,
		dayOffset : delta.days(),
		hourOffset : delta.hours(),
		minuteOffset : delta.minutes()
		},
		contentType : "application/x-www-form-urlencoded",
		dataType : "text",
		success : function(data) {
		},
		});
	},

	eventResize : function(event, delta, revertFunc) {
	},

	events : '/todo/todoitem/getUserToDoItem',
	dayClick : function(date, jsEvent, view, resourceObj) {
		var selDate = $.fullCalendar.moment(date);
		console.log("Clicked on：" + date.format('YYYY年MM月DD日') + selDate);
		console.log('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
		console.log('Current view: ' + view.name);

		var dataSrouce = [ {
		dialogHead : '待办事项-创建',
		startDate : date.format('YYYY年MM月DD日'),
		startTime : date.format('HH:mm'),
		endDate : date.format('YYYY年MM月DD日'),
		endTime : date.format('HH:mm')
		} ];

		var html = $("#todoItemModalDialogTemp").render(dataSrouce);
		$("#modal-form").html(html);
		$("#modal-form").modal("toggle");

		$('#todoitem_delete').hide();

		todoItemTemplateEventBind();

		$("#todoitem_form").on('submit', function() {
			$.ajax({
			type : "POST",
			url : "/todo/todoitemdata/saveTodoItem",
			data : $("#todoitem_form").serializeJSON(),
			contentType : "application/x-www-form-urlencoded",
			dataType : "text",
			success : function(data) {
				$('#div_todo_main').fullCalendar('refetchEvents');
				$("#modal-form").modal("toggle");
			},
			});

			return false;
		});
	},

	eventClick : function(calEvent, jsEvent, view) {
		console.log(calEvent.start.format('HH:mm'));
		console.log(calEvent.end.format('HH:mm'));
		var dataSrouce = [ {
		dialogHead : '待办事项-编辑',
		id : calEvent.id,
		title : calEvent.title,
		allDay : calEvent.allDay,
		startDate : calEvent.start.format('YYYY年MM月DD日'),
		startTime : calEvent.start.format('HH:mm'),
		endDate : calEvent.end.format('YYYY年MM月DD日'),
		endTime : calEvent.end.format('HH:mm')
		} ];

		var html = $("#todoItemModalDialogTemp").render(dataSrouce);
		$("#modal-form").html(html);
		$("#modal-form").modal("toggle");

		todoItemTemplateEventBind();

		$("#todoitem_form").on('submit', function() {
			console.log($("#todoitem_form").serializeJSON());
			$.ajax({
			type : "POST",
			url : "/todo/todoitemdata/modifyToDoItem",
			data : $("#todoitem_form").serializeJSON(),
			contentType : "application/x-www-form-urlencoded",
			dataType : "text",
			success : function(data) {
				$('#div_todo_main').fullCalendar('refetchEvents');
				$("#modal-form").modal("toggle");
			},
			});

			return false;
		});
	}
	});
});