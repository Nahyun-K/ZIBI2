$(document).ready(function() {
	$('#quit_btn').click(function(event){
		
		event.preventDefault();
		
		if($('#mem_password').val().trim()==''){ //유효성 체크
			$('#password_area').val('').focus();
			$('#password_area').text('비밀번호를 입력해주세요');
			return;
		}
		
		$.ajax({ //비밀번호 일치 여부 체크
			url: 'quitPassword',
			type: 'post',
			data: {input_password:$('#mem_password').val()},
			dataType: 'json',
			success: function(param){
				if(param.result=='logout'){
					alert('세션이 만료되어 로그아웃 처리되었습니다. 로그인 후 이용해주세요!');
				} else if (param.result=='passwordNotMatch'){ //비밀번호 불일치 >
					$('#password_area').val('').focus();
					$('#password_area').text('비밀번호가 일치하지 않습니다');
				} else if(param.result=='passwordMatch') { //비밀번호 일치
					$('#passwordModal').show(); //모달창
				} else{
					alert('비밀번호 일치 여부 오류 발생');
				}
			},
			error: function(){
				alert('비밀번호 일치 여부 네트워크 오류 발생');
			}
		});
	});
	
	$('#cancel-btn').click(function(event){
		event.preventDefault();
		location.replace("/main/home");
	});
});