$(document).ready(function() {
	$("#main_middle").load("/chat/chat.html");
});

function gotoMenu(menuPath) {
	console.log("menuPath:" + menuPath);
	$("#main_middle").load(menuPath);
}
function logout() {
	$.ajax({
		type : "POST",
		url : "/user/userLogout",
		data : {},
		contentType : "application/x-www-form-urlencoded",
		dataType : "text",
		success : function(data) {
			setTimeout("javascript:location.href='/index.html'", 100);
		},
	});
}