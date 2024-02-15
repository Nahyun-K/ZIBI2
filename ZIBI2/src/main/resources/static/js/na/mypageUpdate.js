
	if($('#mem_name').val()==''){ //추가 정보 첫 입력의 경우 버튼 활성화
		$('#phone_check').show();
	}
	
	let phone_checked = 1; //닉네임 변경 변수 체크
	let nickname_checked = 1; //닉네임 변경 변수 체크
	
	//연락처 변경을 위해 입력 시 중복 버튼 활성화, 체크 시행
	$('#mem_phone').on('keyup',function(){
		
		console.log('Key up event occurred');
		
		phone_checked = 0;
		
		$('#phone_check').show();
		$('#phone_area').text('');
		$('#mem_phone\\.errors').text(' 123');
					
		//연락처 중복 체크 버튼 클릭 시
		$('#phone_check').click(function(){
			$('#mem_phone\\.errors').text(' ');
			
			if($('#mem_phone').val().trim()==''){ //유효성 체크
				phoneCheck('연락처를 입력해주세요');
				return;
			}
			
			$.ajax({
				url: 'checkPhone',
				type: 'post',
				data: {mem_phone:$('#mem_phone').val()},
				dataType: 'json',
				success:function(param){
					if(param.result=='logout'){
						phoneCheck('로그인 후 변경 가능합니다');
					} else if(param.result=='phoneDuplicated'){
						phoneCheck('연락처가 중복됩니다');
						$('#mem_phone').val('').focus();
					} else if(param.result=='notMatchPattern'){
						phoneCheck('연락처 형식이 불일치합니다');
						$('#mem_phone').val('').focus();
					} else if(param.result=='phoneNotFound'){
						$('#phone_area').text('연락처 사용 가능합니다');
						phone_checked = 1;
					} else if(param.result=='originalPhone'){
						$('#phone_area').text('원래 연락처 사용 가능합니다');
						phone_checked = 1;
					} else {
						phoneCheck('연락처 중복 체크 오류가 발생했습니다');
						phone_checked = 0;
					}
				},
				error:function(){
					alert('네트워크 통신 오류');
					phone_checked = 0;
				}
			});//end of ajax
		});
	});
	
	//닉네임 변경을 위해 키다운 시 중복 버튼 활성화 및 중복 체크 시행
	$('#mem_nickname').on('keyup',function(){
	
		nickname_checked = 0;
		
		$('#nickname_check').show();
		$('#nickname_area').text('');
		$('#mem_nickname\\.errors').text('');
					
		//닉네임 중복 체크 버튼 클릭 시
		$('#nickname_check').click(function(){
			$('#mem_nickname\\.errors').text('');
			
			if($('#mem_nickname').val().trim()==''){ //유효성 체크
				nicknameCheck('닉네임을 입력해주세요');
				return;
			}
			
			$.ajax({
				url: 'checkNickname',
				type: 'post',
				data: {mem_nickname:$('#mem_nickname').val()},
				dataType: 'json',
				success:function(param){
					if(param.result=='logout'){
						nicknameCheck('로그인 후 변경 가능합니다');
					} else if(param.result=='nicknameDuplicated'){
						nicknameCheck('닉네임이 중복됩니다');
						$('#mem_nickname').val('').focus();
					} else if(param.result=='notMatchPattern'){
						nicknameCheck('닉네임은 한글만 입력 가능합니다');
						$('#mem_nickname').val('').focus();
					} else if(param.result=='nicknameNotFound'){
						$('#nickname_area').text('닉네임 사용 가능');
						nickname_checked = 1;
					} else if(param.result=='originalNickName'){
						$('#nickname_area').text('원래 닉네임 사용 가능');
						nickname_checked = 1;
					} else {
						nicknameCheck('닉네임 중복 체크 오류가 발생했습니다');
						nickname_checked = 0;
					}
				},
				error:function(){
					alert('네트워크 통신 오류');
					nickname_checked = 0;
				}
			});//end of ajax
		});
	});
	
	$('#mem_zipcode').on('click',function(){
		$('#mem_code\\.errors').text('');
	});
	
	//submit 이벤트 발생 시 닉네임, 연락처 체크
	$('#update_member').submit(function(){
		let count = 0;
		
		if(nickname_checked==0){
			count++;
			nicknameCheck('닉네임 중복 체크를 진행해주세요');
		}
		if(phone_checked==0){
			count++;
			phoneCheck('연락처 중복 체크를 진행해주세요');
		}
		
		if(count>0) return false;
	});
	
	function phoneCheck(message){
		$('#phone_check').show();
		$('#mem_phone\\.errors').text('');
		$('#phone_area').text(message);
		phone_checked = 0;
	}
	function nicknameCheck(message){
		$('#nickname_check').show();
		$('#mem_nickname\\.errors').text('');
		$('#nickname_area').text(message);
		nickname_checked = 0;
	}
