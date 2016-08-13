$(document).ready(function() {
	$("#login_form").on('submit', function() {
		var ladda = $('#btn_login').ladda();
		ladda.ladda('start');
		$.ajax({
		type : "POST",
		url : "/user/userLogin",
		data : $("#login_form").serializeJSON(),
		contentType : "application/x-www-form-urlencoded",
		dataType : "text",
		success : function(data) {
			console.log(data);
			if ("Success" == data) {
				toastrShow("账号验证通过，请等待系统自动跳转");
				setTimeout("javascript:location.href='/main/main.html'", 4000);
			} else {
				setTimeout(function() {
					toastrShow(data);
					ladda.ladda('stop');
				}, 200)
			}
		},
		});
		return false;
	});
});