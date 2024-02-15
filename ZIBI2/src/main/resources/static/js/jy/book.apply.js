$(function(){
	/*-------- 모임 참여 --------*/
	//참여하기 버튼 클릭
	$('#book_apply').click(function(){
		$('#apply_loading').hide();
		$(window).scrollTop(0);
		$('body').css('overflow-y','hidden');
		$('#bookApplyModal').show();
	});//end of click
	
	//참여 신청하기 버튼 클릭
	$('#apply_btn').click(function(){
		
		//버튼 여러 번 누르지 않도록 숨김 처리
		$('#apply_btn').hide();
		$('#apply_loading').show();
		
		//입력 유효성 체크
		if($('#mem_name').val().trim()==''){
			$('#nameValid').text('이름을 입력해 주세요.');
			$('#apply_btn').show();
			$('#apply_loading').hide();
			return;
		}else{
			$('#nameValid').text('');
		}
		
		if($('#mem_email').val().trim()=='' || $('#mem_email').val().indexOf('@') == -1){
			$('#emailValid').text('@이 포함된 올바른 이메일 주소를 입력해 주세요.');
			$('#apply_btn').show();
			$('#apply_loading').hide();
			return;
		}else{
			$('#emailValid').text('');
		}
		
		if($('#mem_phone').val().trim()=='' || !$('#mem_phone').val().match(/^010-([0-9]{4})-([0-9]{4})$/)){
			$('#phoneValid').text('연락처를 입력해 주세요. 예)010-1234-5678');
			$('#apply_btn').show();
			$('#apply_loading').hide();
			return;
		}else{
			$('#phoneValid').text('');
		}
		
		if(!$('input[id="agree"]').is(":checked")){
			$('#agreeValid').text('개인 정보 수집에 동의해 주세요.');
			$('#apply_btn').show();
			$('#apply_loading').hide();
			return;
		}else{
			$('#agreeValid').text('');
		}
			
		if(!$('input[id="notify"]').is(":checked")){
			$('#notifyValid').text('안내 사항 확인에 체크해 주세요.');
			$('#apply_btn').show();
			$('#apply_loading').hide();
			return;
		}else{
			$('#notifyValid').text('');
		}
			
		let email = $('#mem_email').val();
		let book_num = $(this).attr('data-num');
		let apply_num = $(this).attr('data-apply');
		let book_state = $(this).attr('data-state');
		let apply_gatheringDate = $(this).attr('data-g');
		let apply_title = $(this).attr('data-title');
		let apply_address1 = $(this).attr('data-addr');
		
		emailApplySend(email, book_num, apply_num, book_state,apply_gatheringDate,apply_title,apply_address1);
		
	});
	
	//참여 완료 메일 전송 함수
	function emailApplySend(email, book_num, apply_num, book_state,apply_gatheringDate,apply_title,apply_address1){
		$.ajax({
			url:'bookApply',
			type:'post',
			data:{email:email,book_num:book_num,apply_num:apply_num,book_state:book_state,apply_gatheringDate:apply_gatheringDate,apply_title:apply_title,apply_address1:apply_address1},
			dataType:'json',
			success:function(param){
				if(param.result == 'apply'){
					closeModalAction();
					location.replace('list');
				}else if(param.result == 'duplicated'){
					alert('이 모임과 중복된 일정이 존재하여 예약이 불가합니다.');
					closeModalAction();
					location.replace('list');
				}else if(param.result == 'logout'){
					alert('로그인 후 이용하세요!');
					closeModalAction();
					location.replace('/member/login');
				}else{
					alert('참여 신청 메일 전송 오류');
					$('#apply_btn').show();
					$('#apply_loading').hide();
				}
			},
			error:function(){
				alert('네트워크 오류');
				$('#apply_btn').show();
				$('#apply_loading').hide();
			}
		});
	}
	
	//모달창 닫기 함수
	function closeModalAction(){
		$('body').css('overflow-y','');
		$('#bookApplyModal').hide();
	}
	
	/*-------- 모임 참여 취소 --------*/
	$('.apply-cancel').click(function(){
		if(confirm('선택한 모임의 참여를 취소하시겠습니까?')==false){
			return;
		}
		
		let book_num = $(this).attr('data-num');
		let apply_num = $(this).attr('data-apply');
		
		$.ajax({
			url:'applyCancel',
			type:'post',
			data:{book_num:book_num,apply_num:apply_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'cancel'){
					alert('모임 참여가 취소되었습니다.');
					location.replace('list');
				}else if(param.result == 'logout'){
					alert('로그인 후 이용하세요!');
					location.replace('/member/login');
				}else{
					alert('모임 참여 취소 오류');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
	});
	
	/*-------- 모임 참여 승인 --------*/
	$('.apply-approve').click(function(){
		if(confirm('참여를 승인하시겠습니까? 승인 시 참여자의 예약 내역도 변경됩니다.')==false){
			return;
		}
		
		let book_num = $(this).attr('data-num');
		let apply_num = $(this).attr('data-apply');
		
		$.ajax({
			url:'applyApprove',
			type:'post',
			data:{book_num:book_num,apply_num:apply_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'approve'){
					alert('참여가 승인되었습니다.');
					location.replace('list');
				}else if(param.result == 'logout'){
					alert('로그인 후 이용하세요!');
					location.replace('/member/login');
				}else{
					alert('모임 참여 승인 오류');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
	});
	
	/*-------- 모임 참여 거절 --------*/
	$('.apply-deny').click(function(){
		if(confirm('참여를 거절하시겠습니까? 거절 시 참여자의 예약 내역도 변경됩니다.')==false){
			return;
		}
		
		let book_num = $(this).attr('data-num');
		let apply_num = $(this).attr('data-apply');
		
		$.ajax({
			url:'applyDeny',
			type:'post',
			data:{book_num:book_num,apply_num:apply_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'deny'){
					alert('참여가 거절되었습니다.');
					location.replace('list');
				}else if(param.result == 'logout'){
					alert('로그인 후 이용하세요!');
					location.replace('/member/login');
				}else{
					alert('모임 참여 거절 오류');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
	});
	
	/*-------- 모집 마감(모임 참여 일괄 거절하기) --------*/
	$('#complete_btn').click(function(){
		if(confirm('마감 시 대기 중인 참여 신청은 일괄 거절됩니다. 마감하시겠습니까?')==false){
			return;
		}
		
		let book_num = $(this).attr('data-num');
		if($(this).attr('data-head') == 0){
			alert('모임 참여자가 존재하지 않아 모집 마감이 불가합니다.');
			return;
		}
		
		$.ajax({
			url:'applyClose',
			type:'post',
			data:{book_num:book_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'close'){
					alert('모집이 마감되었습니다.');
					location.replace('list');
				}else if(param.result == 'logout'){
					alert('로그인 후 이용하세요!');
					location.replace('/member/login');
				}else{
					alert('모집 마감 오류');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
	});
	
	/*-------- 모임 완료하기 --------*/
	$('.book-complete').click(function(){
		alert('즐거운 모임 되셨나요? 이용해 주셔서 감사합니다.');
		
		let book_num = $(this).attr('data-num');
		
		$.ajax({
			url:'bookComplete',
			type:'post',
			data:{book_num:book_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'complete'){
					alert('모임이 완료되었습니다. 새로운 모임을 원하시면 새로 모집하기 버튼을 클릭해 주세요.');
					location.replace('list');
				}else if(param.result == 'logout'){
					alert('로그인 후 이용하세요!');
					location.replace('/member/login');
				}else{
					alert('모임 완료 오류');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
	});
	
	/*-------- 새로 모집하기 --------*/
	$('#reset_btn').click(function(){
		if(confirm('새로 모집하시겠습니까?')==false){
			return;
		}
		
		let book_num = $(this).attr('data-num');
		
		$.ajax({
			url:'bookReset',
			type:'post',
			data:{book_num:book_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'reset'){
					alert('새로운 모임이 시작됩니다. 일정을 변경해 주세요.');
					history.go(0);
				}else if(param.result == 'logout'){
					alert('로그인 후 이용하세요!');
					location.replace('/member/login');
				}else{
					alert('새로 모집 오류');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
	});
});