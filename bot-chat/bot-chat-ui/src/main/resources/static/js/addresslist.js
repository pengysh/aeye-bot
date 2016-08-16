function addressUserClick(userId) {
	$("#receiver").val(userId);
	if (1 == userId) {
		$("#main_middle").load("/friend/addfriend.html");
	} else {
		var content = {};
		content.fromSendTime = moment().format('x');
		content.chatAboutId = userId + "-" + getCookie("userId");
		content.userId = getCookie("userId");
		content.newOrHis = "new";
		sendMessage("GetChatRecord", content);

		$("#div_messageContent").empty();
		$("#div_message_box").show();
	}
}