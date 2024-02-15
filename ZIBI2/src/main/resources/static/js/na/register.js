
	let email_checked = 0; //이메일 중복 체크 
	let nickname_checked = 0; //닉네임 중복 체크
	let password_checked = 0; //비밀번호 일치 체크 
	let code; //이메일 인증 코드 저장용
	
	//이메일 인증버튼 클릭
	$('#email_check').click(function(){
		
		$('#email_check').hide();
		$('#spiner').show();
		
		if($('#mem_email').val().trim()==''){ //이메일 공란 입력 체크
			emailControll('이메일을 입력해주세요');
			return;
		}
		
		$.ajax({
			url: 'checkEmail', //이메일 유효성 체크
			type: 'post',
			data: {mem_email:$('#mem_email').val()},
			dataType: 'json',
			success:function(param){
				if(param.result=='emailDuplicated'){
					emailControll('이메일 중복');
				} else if(param.result=='notMatchPattern'){
					emailControll('이메일 형식 맞지 않음');
				} else if(param.result=='emailNotFound'){ //이메일 중복이 없는 경우
					emailAuthSend($('#mem_email').val()); //인증 메일 전송
				} else {
					alert('이메일 중복 체크 오류');
					email_checked = 0;
				}
			},
			error:function(){
				alert('이메일 중복 체크 네트워크 통신 오류');
				email_checked = 0;
			}
		});//end of ajax	
	});//end of email check click
	
	//이메일 에러 메세지 표시, 입력 버튼 숨기기, 로딩 이미지 노출
	function emailControll(message){
		$('#email_area').text(message);
		$('#mem_email').val('').focus();
		$('#email_check').show();
		$('#spiner').hide();
		email_checked = 0;
	}
	
	//인증메일 전송 함수
	function emailAuthSend(mem_email){
		$.ajax({
			url: 'emailAuth', //인증 메일 전송 컨트롤러와 통신
			type: 'post',
			data: {mem_email:mem_email},
			dataType: 'json',
			success:function(param){ //인증 메일 전송 성공 시
				code = param.code; //인증 코드를 전역에 저장
				$('#emailAuthModal').show(); //모달창을 호출해 인증 코드 확인 진행
			},
			error:function(){
				alert('인증 메일 네트워크 통신 오류');
			}
		});//end of ajax
	}//end of emailAuth
	
	//모달창에서 인증하기 버튼 누르면
	$('#email_check_btn').click(function(){ 
		if(code==$('#inputEmail').val()){ //인증코드와 입력코드가 동일하면
			$('#email_check').attr('value','인증 완료').attr('disabled','disabled'); //인증 버튼 비활성화
			email_checked = 1; //이메일 중복체크 및 인증 완료 저장
			$('#email_area').text('');
			$('#emailAuthError').text('이메일 인증에 성공했습니다. 3초 뒤 창이 닫힙니다!');
			setTimeout(function() {
				closeModalAction();
			}, 3000);
		} else {
			$('#emailAuthError').text('인증 코드가 불일치합니다. 재입력해주세요!');
			email_checked = 0;
			return;
		}
	});
	
	//모달창 클릭 시 닫기
	function closeModalAction(){ 
		if(email_checked==0 && confirm('이메일 인증이 완료되지 않았습니다. 그래도 닫으시겠습니까?')==false){ 
			//이메일 인증 미진행 > 닫기 > 경고창 생성
			return false; //아니오 클릭 시 모달창을 빠져나가지 않음
		}
		
		$('#email_check').show();
		$('#spiner').hide();
		$('#emailAuthModal').hide(); //모달창 닫기
	}
	
	//이메일 인증 후 이메일 값 변경 시 중복체크, 인증 초기화
	$('#mem_email').keydown(function(){
		$('#email_area').text('');
		$('#email_check').attr('value','인증').removeAttr('disabled');
		email_checked = 0;
	});//end of keydown
	
	//닉네임 중복 체크
	$('#nickname_check').click(function(){
		if($('#mem_nickname').val().trim()==''){
			$('#nickname_area').text('닉네임을 입력해주세요');
			$('#mem_nickname').val('').focus();
			return;
		}
		$.ajax({
			url: 'registerNickname',
			type: 'post',
			data: {mem_nickname:$('#mem_nickname').val()},
			dataType: 'json',
			success:function(param){
				if(param.result=='nicknameDuplicated'){
					$('#nickname_area').text('닉네임 중복');
					$('#mem_nickname').val('').focus();
					nickname_checked = 0;
				} else if(param.result=='notMatchPattern'){
					$('#nickname_area').text('닉네임 형식 맞지 않음');
					$('#mem_nickname').val('').focus();
					nickname_checked = 0;
				} else if(param.result=='nicknameNotFound'){
					$('#nickname_area').text('닉네임 사용 가능');
					nickname_checked = 1;
				} else {
					alert('닉네임 중복 체크 오류');
					nickname_checked = 0;
				}
			},
			error:function(){
				alert('네트워크 통신 오류');
				nickname_checked = 0;
			}
		});//end of ajax
	});//end of click
	
	//닉네임 중복 체크 후 값 변경 시 초기화
	$('#mem_nickname').keydown(function(){
		$('#nickname_area').text('');
		nickname_checked = 0;
	});
	
	//비밀번호 일치 여부
	$('#c_password').keyup(function(){
		$('#cpassword_area').text('');
		password_checked = 0;
		
		if($('#mem_password').val()==$('#c_password').val()){
			$('#cpassword_area').text('비밀번호가 일치합니다');
			password_checked = 1;
		}
	});//end of keyup
	
	//submit 이벤트 발생 시 이메일, 닉네임, 비밀번호 체크
	$('#register_member').submit(function(){
		let count = 0;
		
		if(email_checked==0)	{
			$('#email_area').text('이메일 중복 체크를 진행해주세요');
			count++;
		}
		if(nickname_checked==0){
			$('#nickname_area').text('닉네임 중복 체크를 진행해주세요');
			count++;
		}	
		if(password_checked==0){
			$('#password_area').text('비밀번호를 올바르게 입력해주세요');
			count++;
		}	
		
		if(count>0)	return false;
	});//end of submit
