$(document).ready(function() {
	$("#main_middle").load("/todo/todo.html");
});

function gotoMenu(menuPath){
	$("#main_middle").load(menuPath);
}