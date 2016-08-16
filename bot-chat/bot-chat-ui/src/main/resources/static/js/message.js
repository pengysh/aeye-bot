$(document).ready(function() {
	$("#div_messageContent").scroll(function() {
		var scrollTop = $(this)[0].scrollTop;
		if (scrollTop == 0) {
			console.log(scrollTop);
			getMessageRecord();
		}
	});
});

function getMessageRecord() {
	var userId = getCookie("userId");
	var receiver = $("#receiver").val();
	var content = {};
	content.fromSendTime = messageSendTimeFirst;
	content.chatAboutId = receiver + "-" + userId;
	content.userId = userId;
	content.newOrHis = "his";
	sendMessage("GetChatRecord", content);
}