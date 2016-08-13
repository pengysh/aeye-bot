var request;
var myAccountId;

function initWebSocket() {
	myAccountId = getCookie("AccountId");

	initMessageSocket();
	initFunctionSocket();
}

if (!window.WebSocket) {
	toastrShow("浏览器不支持WebSocket");
}
var messagews;
function initMessageSocket() {
	toastrShow("欢迎使用会议预定系统");
	messagews = new ReconnectingWebSocket("ws://localhost:8888/message.bot");
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
	var element = document.getElementById("question");
	var input = element.value;
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

var functionws;
function initFunctionSocket() {
	functionws = new ReconnectingWebSocket("ws://localhost:8888/function.bot");
	functionws.onmessage = function(event) {
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

		if ('PersonContacts' == response.contentId) {
			var accountList = response.accountList;
			$("#persontalk").empty();
			for (var i = 0; i < accountList.length; i++) {
				updatePersonContacts(accountList[i].accountId,
						accountList[i].name, accountList[i].headimage,
						accountList[i].state)
			}
		}
	};
	functionws.onclose = function(event) {
		console.log("functionws:onclose", functionws.readyState);
	};
	functionws.onopen = function(event) {
		console.log("functionws:onopen", functionws.readyState);
	};
	functionws.onerror = function(event) {
		console.log("functionws:onerror", functionws.readyState);
	};
}

function getPersonMessage(personAccountId) {
	$("#personAccountId").val(personAccountId);

	console.log("personAccountId:", personAccountId);
	$("#messageContent").empty();
	var cmd = "{'cmd':'InitPersonMessageCmd','myAccountId':'" + myAccountId
			+ "', 'personAccountId':'" + personAccountId + "'}";
	functionws.send(cmd);
}

function toastrShow(message) {
	toastr.options = {
		closeButton : true,
		progressBar : true,
		showMethod : 'slideDown',
		timeOut : 4000
	};
	toastr.success(message);
}

function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}

function showMessage(accountId, userName, sendtime, message, image) {
	var direction = "left";
	if (myAccountId == accountId) {
		direction = "right"
	}

	var messageContentDiv = $("#messageContent");

	messageContentDiv
			.append("<div class='chat-message "
					+ direction
					+ "'><img class='message-avatar' src='"
					+ image
					+ "' alt=''><div class='message'><a class='message-author' href='#'>"
					+ userName + "</a> <span class='message-date'> " + sendtime
					+ " </span><span class='message-content'> " + message
					+ " </span></div></div>");

	console.log("scrollHeight:", messageContentDiv[0].scrollHeight);
	messageContentDiv[0].scrollTop = messageContentDiv[0].scrollHeight;
}

function getContacts(accountid, name, email, state) {
	$("#adduser-form-contacts")
			.append(
					"<tr><td class='client-avatar'><img alt='image' src='img/a2.jpg'></td><td><a data-toggle='tab' href='#contact-1' class='client-link'>"
							+ accountid
							+ "</a></td><td>"
							+ name
							+ "</td><td class='contact-type'><i class='fa fa-envelope'></i></td><td>"
							+ email
							+ "</td><td class='client-status'><span class='label label-primary'>"
							+ state
							+ "</span></td><td><button class='btn btn-primary btn-xs btn-block' type='button'>加入</button></td></tr>");
}

function updatePersonContacts(accountid, name, headimage, state) {
	var labelClass = "label-warning";
	if ("Online" == state) {
		labelClass = "label-primary";
	}

	$("#persontalk")
			.append(
					"<div class='chat-user'><img class='chat-avatar' src='"
							+ headimage
							+ "' alt=''><div class='chat-user-name' onclick='getPersonMessage(\""
							+ accountid
							+ "\");'><small class='pull-right text-navy'><span class='label "
							+ labelClass + "'>" + state + "</span></small>"
							+ name + "</div></div>");
}