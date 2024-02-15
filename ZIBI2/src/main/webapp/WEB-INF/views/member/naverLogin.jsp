<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>네이버 로그인</title>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<input type="hidden" id="naver_apikey" value="${naver_apikey}"/>">
</body>
	<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
	<script type="text/javascript">
		
		var naver_id_login = new naver_id_login( $('#apikey').val() , "http://localhost:8000/member/loginNaver");
		// 네이버 사용자 프로필 조회
		naver_id_login.get_naver_userprofile("naverSignInCallback()");
		// 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
		
		function naverSignInCallback() {
			$.ajax({
				url:'loginSocial',
				type:'post',
				data:{mem_email:naver_id_login.getProfileData('email'),mem_social:'2'},
				dataType:'json',
				success: function(param){
					if(param.result=='success'){
						window.opener.location.href = "/main/home";
						window.close();
					} else if(param.result=='socialNotMatch'){
						alert('이미 가입된 이메일입니다. 다른 방법으로 로그인하세요');
						window.close();
					} else{
						alert('네이버 로그인 오류');
					}
				},
				error:function(){
					alert('네이버 로그인 네트워크 통신 오류');
				}
			});
		}
	</script>
</html>