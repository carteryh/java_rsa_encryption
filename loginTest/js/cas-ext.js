$(document).ready(function() {
	
	//	2017/12/07 登录页面切换手机/账号登录
	/*
	var switchs = $(".zs-login-body .form .switch");
	var fwmmForm = $(".zs-login-body .form .fwmm-form");
	var sjsjmForm = $(".zs-login-body .form .sjsjm-form");
	var modeBtn = document.getElementById("switchBtn");
	switchs.click(function(e){
		e.stopPropagation();
		if($(this).hasClass("show-sjsjm")){
			fwmmForm.show();
			sjsjmForm.hide();
			modeBtn.value="0";
			$(this).html("手机随机码登录");
		}else{
			fwmmForm.hide();
			sjsjmForm.show();
			modeBtn.value="1";
			$(this).html("服务密码登录");
		}
		$(this).toggleClass("show-sjsjm");
	})*/
	
	// 手机验证码发送
	$("#sjyzm").on("click", function(evt) {
		var phoneNo = document.getElementById("phonenumber").value;
		if ($.trim(phoneNo) == "") {
			alert("手机号不能为空！");
			return;
		}
		if (!$(this).hasClass("not-click"))
		{
			var domThis = this;
			$.ajax({
				url : 'smsCode',
				data : {
					data : JSON.stringify({
						telNo : $("#phonenumber").val()
					})
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(value) {
					if (value.result == "error" && value.message=="该手机号码不存在！") {
						$("#errorMsg").html("该手机号码不存在！");
						$("#errorMsg").css("display","block");
						countdown=0;
					}else if (value.result == "error" && parseInt(value.message)>0) {
						$("#errorMsg").html("随机码发送频繁，请稍后再试！");
						$("#errorMsg").css("display","block");
					}
				},
				error : function(evt) {
					if (evt.responseText == "timeout") {
					}
				}
			});
		}
	});
	$("#sjyzm-i").on("click",function changeImg (){     
	    var imgSrc = $("#yzm-img");     
	    var src = imgSrc.attr("src");     
	    imgSrc.attr("src",chgUrl(src));     
	});     
	//时间戳     
	//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳     
	 function chgUrl(url){     
	    var timestamp = (new Date()).valueOf();     
	    var url = "verifyCode";//url.substring(0,17);     
	    if((url.indexOf("&")>=0)){     
	        url ="verifyCode×tamp=" + timestamp;     
	    }else{     
	        url = "verifyCode?timestamp=" + timestamp;     
	    }     
	    return url;     
	};
});	