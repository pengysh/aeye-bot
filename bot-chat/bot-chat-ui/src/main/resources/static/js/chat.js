if (!window.WebSocket) {
	toastrShow("浏览器不支持WebSocket");
}
function initWebSocket() {
	console.log("initWebSocket");
	messagews = new ReconnectingWebSocket("ws://www.aeye.com:8104/ws/sendmessage");
	messagews.onmessage = function(event) {
		var response = eval('(' + event.data + ')');

		if ('ChatMessage' == response.Cmd) {
			console.log("收到消息：" + event.data);
			showMessage(response);
		} else if ('GetChatRecord' == response.Cmd) {
			console.log("收到消息：" + event.data);
			var messageList = response.messageList;
			for (i in messageList) {
				var messageContent = messageList[i];
				messageContent.userId = getCookie("userId");
				showMessage(messageContent);
			}
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
function sendMsg() {
	var input = $("#input_message").val();

	var content = {};
	content.message = input;
	content.receiver = $("#receiver").val();
	content.sender = getCookie("userId");

	sendMessage("PersonChat", content);
	$("#input_message").val("");
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

function showMessage(data) {
	var template = $.templates("#messageTemp");
	var htmlOutput = template.render(data);
	var messageContentDiv = $("#div_messageContent");
	messageContentDiv.append(htmlOutput);
	
	messageContentDiv[0].scrollTop = messageContentDiv[0].scrollHeight;
}

$(document).ready(function() {
	toastrShow("欢迎使用会议预定系统");
	$.ajaxSetup({
		cache : false
	});

	initWebSocket();

	$("#div_addresslisttemp").load("/chat/addresslist.html");
	$("#div_messagetemp").load("/chat/message.html");

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