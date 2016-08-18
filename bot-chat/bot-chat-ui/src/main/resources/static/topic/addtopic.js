function addTopicShow() {
	$.fn.bootstrapSwitch.defaults.onColor = 'info';
	var template = $.templates("#addtopicTemp");

	var data = {
		member : userFriendsData
	};
	template.link("#div_addtopic_container", data);

	$("[name='publicOrPrivate']").bootstrapSwitch();

	var config = {
		'.chosen-select' : {},
		'.chosen-select-deselect' : {
			allow_single_deselect : true
		},
		'.chosen-select-no-single' : {
			disable_search_threshold : 10
		},
		'.chosen-select-no-results' : {
			no_results_text : 'Oops, nothing found!'
		},
		'.chosen-select-width' : {
			width : "95%"
		}
	}
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	}

	$('#chosen_select_userJson').change(function() {
		var value = $(this).val();
		$("#input_userJson").val(value);
	});
}

$(document).ready(function() {
	$("#addtopic_form").on('submit', function() {
		console.log($("#addtopic_form").serializeJSON());
		
		$.ajax({
			type : "POST",
			url : "/chat/group/createChatGroup",
			data : $("#addtopic_form").serializeJSON(),
			contentType : "application/x-www-form-urlencoded",
			dataType : "text",
			success : function(data) {
				getUserTopics();
				$("#btn_addtopic_back").click();
			},
		});

		return false;
	});
});