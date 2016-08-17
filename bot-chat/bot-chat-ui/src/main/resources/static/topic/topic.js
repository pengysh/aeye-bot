function addFriend(userId) {
	$.ajax({
		type : "POST",
		url : "/chat/friendsdata/addFriend",
		data : {
			"friendsUserId" : userId
		},
		contentType : "application/x-www-form-urlencoded",
		dataType : "text",
		success : function(data) {
			console.log("添加好友成功");
			$(staffdata).each(function() {
				if(userId == this.id){
					this.isFriend = true;
				}
			});
			var template = $.templates("#staffListTemp");
			template.link("#div_addfriend", staffdata);
		},
	});
}

function removeFriend(userId) {
	$.ajax({
		type : "POST",
		url : "/chat/friendsdata/removeFriend",
		data : {
			"friendsUserId" : userId
		},
		contentType : "application/x-www-form-urlencoded",
		dataType : "text",
		success : function(data) {
			$(staffdata).each(function() {
				if(userId == this.id){
					this.isFriend = false;
				}
			});

			var template = $.templates("#staffListTemp");
			template.link("#div_addfriend", staffdata);
		},
	});
}