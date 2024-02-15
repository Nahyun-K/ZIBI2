$(document).ready(function() {

	//이메일 인증버튼 클릭
	$('#find_email').submit(function(event){
		
		$('#email_check').hide();
		$('#spiner').show();
		
		if($('#mem_email').val().trim()==''){ //이메일 공란 입력 체크
			$('#email_area').text('이메일을 입력해주세요');
			$('#mem_email').val('').focus();
			$('#spiner').hide();
			$('#email_check').show();
			return false;
		}
		
		$.ajax({
			url: 'findPassword', //임시 비밀번호 전송 컨트롤러와 통신
			type: 'post',
			data: {mem_email:$('#mem_email').val()},
			dataType: 'json',
			success:function(param){ //메일 전송 성공 시
				if(param.result=="success"){
					$('#passwordModal').show(); //모달창 호출, 로그인 유도
					$(window).scrollTop(0);
					$('body').css('overflow-y','hidden');
				} //이메일 유출을 막기 위해 없는 이메일이더라도 사용자에게 보여주지 않음
			},
			error:function(){
				alert('임시 비밀번호 전송 네트워크 통신 오류');
			}
		});//end of ajax
		
		event.preventDefault();
	});//end of email check click
});	

function loginDirect(){//모달창 클릭 시 로그인 페이지로 이동
	//히스토리를 지우고 이동
	location.replace('/member/login');
}

function replaceEmailForm(){
	location.replace('/member/findPassword');
	$(window).scrollTop(0);
	$('body').css('overflow-y','');
}