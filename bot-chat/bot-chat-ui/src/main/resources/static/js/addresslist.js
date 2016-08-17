function addressUserClick(userId, userName, headImage) {
	console.log("点击的用户：" + userId);
	$("#receiver").val(userId);
	if (1 == userId) {
		$("#main_middle").load("/chat/friend/addfriend.html");
	} else {
		var content = {};
		content.fromSendTime = moment().format('x');
		content.chatAboutId = userId + "-" + getCookie("userId");
		content.userId = getCookie("userId");
		content.newOrHis = "new";
		sendMessage("GetChatRecord", content);

		$("#div_messageContent").empty();
		$("#div_message_box").show();
		
		$("#input_click_friend_userId").val(userId);
		$("#input_click_friend_userName").val(userName);
		$("#input_click_friend_headImage").val(headImage);
	}
}