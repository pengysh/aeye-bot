function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
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