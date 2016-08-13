$(document).ready(function() {
	$("#main_middle").load("/chat/chat.html");
});

function gotoMenu(menuPath){
	$("#main_middle").load(menuPath);
}