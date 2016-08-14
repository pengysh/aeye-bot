if (!window.WebSocket) {
	toastrShow("浏览器不支持WebSocket");
}
function initWebSocket() {
	console.log("initWebSocket");
	messagews = new ReconnectingWebSocket("ws://www.aeye.com:8104/ws/sendmessage");
	messagews.onmessage = function(event) {
		var response = eval('(' + event.data + ')');

		if ('ChatMessage' == response.contentId) {
			var messageList = response.messageList;
			for (var i = 0; i < messageList.length; i++) {
				showMessage(messageList[i].fromAccount,
						messageList[i].fromAccountName,
						messageList[i].sendTime, messageList[i].message,
						messageList[i].fromAccountHeadImage);
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
	var input = $("#input_message").value;
	sendMessage(input, "person");
	element.value = "";
}

function sendMessage(meesage, source) {
	console.log("source:", source);
	var data = {};
	data.cmd = "SendMessageCmd";
	data.message = meesage;
	data.myAccountId = myAccountId;
	data.personAccountId = $("#personAccountId").val();
	data.source = source;

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

	setTimeout(function(){
		ladda.ladda('stop');
    },12000)
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