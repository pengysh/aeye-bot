$(document).ready(function() {
	$("#register_form").on('submit', function() {
		var ladda = $('#btn_register').ladda();
		ladda.ladda('start');
		$.ajax({
		type : "POST",
		url : "/userdata/userRegister",
		data : $("#register_form").serializeJSON(),
		contentType : "application/x-www-form-urlencoded",
		dataType : "text",
		success : function(data) {
			console.log(data);
			if ("Success" == data) {
				toastrShow("注册成功，请等待系统自动跳转");
				setTimeout("javascript:location.href='index.html'", 4000);
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