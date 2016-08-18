if (!window.WebSocket) {
	toastrShow("浏览器不支持WebSocket");
}

var messageSendTimeFirst;
function initWebSocket() {
	console.log("initWebSocket");
	messagews = new ReconnectingWebSocket("ws://www.aeye.com:8104/ws/sendmessage");
	messagews.onmessage = function(event) {
		var response = eval('(' + event.data + ')');
		console.log("收到消息：" + event.data);

		if ('ChatMessage' == response.Cmd) {
			showNewMessage(response);
		} else if ('GetChatRecord' == response.Cmd) {
			var messageList = response.messageList;
			if ("his" == response.newOrHis) {
				for (i = 0; i < messageList.length; i++) {
					console.log("收到消息：" + messageList[i]);
					var messageContent = messageList[i];
					messageContent.userId = getCookie("userId");
					mergeSendTime(messageContent.sendTime);
					showHisMessage(messageContent);
				}
			} else {
				if (messageList.length == 0) {
					showMessageWithNewFriend();
				}

				for (i = messageList.length - 1; i >= 0; i--) {
					var messageContent = messageList[i];
					messageContent.userId = getCookie("userId");
					mergeSendTime(messageContent.sendTime);
					showNewMessage(messageContent);
				}
			}
		} else if ('UserStateNotice' == response.Cmd) {
			var stateUserId = response.userId;
			var state = response.state;
		}
	};
	messagews.onclose = function(event) {
		console.log("messagews:onclose", messagews.readyState);
	};
	messagews.onopen = function(event) {
		console.log("messagews:onopen", messagews.readyState);
	};
	messagews.onerror = function(event) {
		console.log("messagews:onerror", messagews.readyState);
	};
}

function sendMsgToFriend() {
	var input = $("#input_message_friend").val();

	var content = {};
	content.message = input;
	content.receiver = $("#receiver").val();
	content.sender = getCookie("userId");

	sendMessage("PersonChat", content);
	$("#input_message_friend").val("");
}

function sendMsgToTopic() {
	var input = $("#input_message_topic").val();

	var content = {};
	content.message = input;
	content.topicId = $("#topicId").val();
	content.sender = getCookie("userId");

	sendMessage("TopicChat", content);
	$("#input_message_topic").val("");
}

function sendMessage(cmd, content) {
	var data = {};
	data.cmd = cmd;
	data.content = content;

	var jsonStr = JSON.stringify(data);
	console.log("sendMessage:", jsonStr);
	console.log("messagews:send", messagews.readyState);
	messagews.send(jsonStr);
}

function laddaSendMessage(buttonId, meesage, source) {
	var ladda = $('#' + buttonId).ladda();
	console.log(ladda);

	ladda.ladda('start');

	sendMessage(meesage, source);

	setTimeout(function() {
		ladda.ladda('stop');
	}, 12000)
}

function showHisMessage(data) {
	var template = $.templates("#messageTemp");
	var htmlOutput = template.render(data);
	var messageContentDiv = $("#div_messageContent");
	messageContentDiv.prepend(htmlOutput);
}

function showNewMessage(data) {
	var template = $.templates("#messageTemp");
	var htmlOutput = template.render(data);
	var messageContentDiv = $("#div_messageContent");
	messageContentDiv.append(htmlOutput);
	messageContentDiv[0].scrollTop = messageContentDiv[0].scrollHeight;
}

function mergeSendTime(sendTime) {
	console.log("messageSendTimeFirst:" + moment(messageSendTimeFirst, 'x').format("YYYY-MM-DD HH:mm:SS") + ",sendTime:" + moment(sendTime, 'x').format("YYYY-MM-DD HH:mm:SS"));
	if (!messageSendTimeFirst) {
		messageSendTimeFirst = sendTime;
	} else if (messageSendTimeFirst > sendTime) {
		messageSendTimeFirst = sendTime;
	}
}

function showMessageWithNewFriend() {
	var userId = $("#input_click_friend_userId").val();
	var userName = $("#input_click_friend_userName").val();
	var headImage = $("#input_click_friend_headImage").val();
	var data = {
		"messageId" : 1,
		"sender" : userId,
		"senderName" : userName,
		"headImage" : headImage,
		"message" : "长夜漫漫无心睡眠，聊两句吧",
		"sendTime" : moment().format('x'),
		"sendTimeFormat" : moment().format("YYYY-MM-DD HH:mm:SS")
	};
	showNewMessage(data);
}

$(document).ready(function() {
	toastrShow("欢迎使用会议预定系统");
	$.ajaxSetup({
		cache : false
	});

	initWebSocket();

	$("#input_message_friend").hide();
	$("#input_message_topic").hide();
	$("#div_addresslisttemp").load("/chat/addresslist.html");
	$("#div_messagetemp").load("/chat/message.html");
	$("#div_stafflisttemp").load("/chat/friend/staff.html");
	$("#div_addfriend_container").hide();

	$("#div_addtopictemp").load("/chat/topic/addtopic.html");
	$("#div_topicListtemp").load("/chat/topic/topic.html");

	$("#div_addtopic_container").hide();

	$("#btn_addfriend_back").hide();
	$("#btn_addfriend_add").click(function() {
		$("#div_chat_container").hide();
		addFriendShow();
		$("#btn_addfriend_back").show();
		$("#btn_addfriend_add").hide();
	});

	$("#btn_addfriend_back").click(function() {
		$("#div_addfriend_container").hide();
		$("#div_chat_container").show();
		$("#btn_addfriend_back").hide();
		getUserFriends();
		$("#btn_addfriend_add").show();
	});

	getUserFriends();

	$("#btn_addtopic_back").hide();
	$("#btn_addtopic_add").click(function() {
		$("#div_topicdown_container").hide();
		$("#div_addtopic_container").show();
		addTopicShow();
		$("#btn_addtopic_back").show();
		$("#btn_addtopic_add").hide();
	});

	$("#btn_addtopic_back").click(function() {
		$("#div_addtopic_container").hide();
		$("#div_topicdown_container").show();
		$("#btn_addtopic_back").hide();
		getUserFriends();
		$("#btn_addtopic_add").show();
	});
	getUserTopics();
});

var userFriendsData;
function getUserFriends() {
	$.ajax({
		type : "POST",
		url : "/chat/friend/getUserFriends",
		data : {},
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		success : function(data) {
			userFriendsData = data;
			console.log("/chat/friend/getUserFriends返回的数据:" + data);
			var template = $.templates("#addressListTemp");
			template.link("#div_userlist", data);
		},
	});
}

function getUserTopics() {
	$.ajax({
		type : "POST",
		url : "/chat/topic/getUserTopic",
		data : {},
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		success : function(data) {
			console.log("/chat/topic/getUserTopic返回的数据:" + data);
			var template = $.templates("#topicListTemp");
			template.link("#ul_topic_container", data);
		},
	});
}