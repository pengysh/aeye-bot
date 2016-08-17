var staffdata;

$(document).ready(function() {
	$("#div_stafflisttemp").load("/chat/friend/staff.html");
	
	$.ajax({
		type : "POST",
		url : "/user/depart/getUserInDept",
		data : {deaprtId:"5"},
		contentType : "application/x-www-form-urlencoded",
		dataType : "json",
		success : function(data) {
			staffdata = data;
			var template = $.templates("#staffListTemp");
			template.link("#div_stafflist", data);
		},
	});
});