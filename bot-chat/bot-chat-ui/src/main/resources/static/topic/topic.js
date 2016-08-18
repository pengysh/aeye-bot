function showTopicChatRecord(groupId) {
	console.log("点击的话题：" + groupId);
	$("#topicId").val(groupId);
	var content = {};
	content.fromSendTime = moment().format('x');
	content.chatAboutId = groupId;
	content.userId = getCookie("userId");
	content.newOrHis = "new";
	content.isTopic = true;
	sendMessage("GetChatRecord", content);

	$("#div_messageContent").empty();
	$("#input_message_friend").hide();
	$("#input_message_topic").show();
	
	$("#input_click_friend_userId").val(1);
	$("#input_click_friend_userName").val("系统");
	$("#input_click_friend_headImage").val("10.png");
}