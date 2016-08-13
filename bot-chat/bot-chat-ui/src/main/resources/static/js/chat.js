$(document).ready(function() {
	$("#div_addresslisttemp").load("/chat/addresslist.html");

	$.ajax({
		type : "POST",
		url : "/user/friend/getUserFriends",
		data : {},
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		success : function(data) {
			console.log(data);
			var addfriend = [ {
				"id" : 1,
				"name" : "添加新的朋友",
				"headImage" : "5.png"
			} ];
			var newdata = addfriend.concat(data);
			var template = $.templates("#addressListTemp");
			template.link("#div_userlist", newdata);
		},
	});
});