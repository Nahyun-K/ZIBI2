	//프로필 사진 변경 선택 시 버튼 노출
	$('#photo_btn').click(function() {
		$('#photo_choice').show();
	});
	
	let old_photo = $('.my-photo').attr('src'); //기존 이미지 저장
	let new_photo; //변경할 이미지 변수 선언
	
	//파일 선택 시
	$('#upload').change(function() {
		new_photo = this.files[0]; //변경할 이미지 저장
	
		if (!new_photo) { //선택창에서 파일 선택을 취소할 경우
			$('.my-photo').attr('src', old_photo); //기존 이미지로
			return;
		}
	
		//용량체크
		if (new_photo.size > 1024 * 1024) {
			$('#photo-_check').text('1MB 이하의 이미지만 등록 가능합니다');
			$('.my-photo').attr('src', old_photo); //기존 이미지로
			$(this).val(''); //파일명 지우기
		}
	
		//미리보기
		let reader = new FileReader(); //사진 파일을 읽어오기 위한 객체 생성
		reader.readAsDataURL(new_photo); //객체를 이용해 선택한 파일을 읽어 옴
		reader.onload = function() { //파일을 다 읽어오면
			$('.my-photo').attr('src', reader.result); //이미지 미리보기 처리
		};
	});
	
	//전송 클릭 시 업로드
	$('#photo_submit').click(function() {
	
		//유효성 체크
		if ($('#upload').val() == '') {
			$('#photo_check').text('사진을 선택해주세요');
			$('#upload').focus();
			return;
		}
	
		//전송할 파일
		let form_data = new FormData();
		form_data.append('upload', new_photo);
	
		//서버와 통신
		$.ajax({
			url : '../member/updateMyPhoto',
			type : 'post',
			data : form_data,
			dataType : 'json',
			contentType : false,
			processData : false,
			success : function(param) {
				if (param.result == 'logout') {
					alert('로그인 세션이 만료되었습니다! 로그인해주세요');
				} else if (param.result == 'success') {
					old_photo = $('.my-photo').attr('src');
					$('#photo_choice').hide();
				} else {
					alert('등록 오류');
				}
			},
			error : function() {
				alert('네트워크 오류');
			}
		});
	
	});
	
	//취소 버튼 클릭 시 리셋
	$('#photo_reset').click(function() {
		$('.my-photo').attr('src', old_photo);
		$('#photo_choice').hide();
		$('#photo_check').hide();
	});
