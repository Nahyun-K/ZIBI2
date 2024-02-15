$(function(){
	let rowCount = 10;
	let currentPage;
	let count;
	
	/*---------------------------
	 * 댓글 목록
	 *---------------------------*/
	//댓글 목록
	function selectList(pageNum){
		currentPage = pageNum;
		//로딩 이미지 노출
		$('#loading').show();
		
		$.ajax({
			url:'listReply',
			type:'post',
				//key		변수		key		변수			key		변수-태그에 있는 아이디를 불러옴
			data:{pageNum:pageNum,rowCount:rowCount,helper_num:$('#helper_num').val()},
			dataType:'json',
			success:function(param){
				//로딩 이미지 감추기
				$('#loading').hide();
				count = param.count;
				
				if(pageNum == 1){
					//처음 호출시는 해당 ID의 div의 내부 내용물을 제거
					$('#output').empty();
				}
				
				//1 읽어오기
				displayReplyCount(param);
				
				//댓글 목록 작업				item:한 건의 레코드
				$(param.list).each(function(index,item){
					let output = '<div class="item">';
					output += '<ul class="detail-info">';
					output += '<li>';
					output += '<img src="../member/viewProfile?mem_num='+item.mem_num+'" width="40" height="40" class="my-photo radius margin-photo">';
					output += '</li>';
					output += '<li>';
					
					if(item.mem_nickname){
						output += item.mem_nickname + '<br>';
					}
					
					if(item.re_mdate){
						output += '<span class="modify-date">최근 수정일 : ' + item.re_mdate + '</span>';
					}else{
						output += '<span class="modify-date">최근 등록일 : ' + item.re_date + '</span>';
					}
					
					output += '</li>';
					output += '</ul>';
					output += '<div class="sub-item">';//\r이랑 \n을 발견하면 <br>로 바꾸겠다(줄바꿈 처리)
					output += '<br>';
					output += '<p class="margin-content">' + item.re_content.replace(/\r\n/g,'<br>') + '</p>';
					
					if(param.user_num==item.mem_num){
						//로그인한 회원번호와 댓글 작성자 회원번호가 같으면
						output += ' <input type="button" data-num="'+item.re_num+'" value="수정" class="modify-btn d-inline-block text-dark text-uppercase bg-light border rounded-pill px-2 py-1 mb-1" style="margin-left:5px;">';
						output += ' <input type="button" data-num="'+item.re_num+'" value="삭제" class="delete-btn d-inline-block text-dark text-uppercase bg-light border rounded-pill px-2 py-1 mb-1">';
					}
					
					output += '<hr size="3" noshade>';
					output += '</div>';//<div class="sub-item">닫는 대그
					output += '</div>';//<div class="item">닫는 태그
					
					//문서 객체에 추가
					$('#output').append(output);
					
				});//end of each
				
				//paging button 처리
				if(currentPage>=Math.ceil(count/rowCount)){
					//다음 페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음 페이지가 존재
					$('.paging-button').show();
				}
				
			},
			error:function(){
				//로딩 이미지 감추기
				$('#loading').hide();
				alert('네트워크 오류 발생');
			}
		});//end of ajax
	}//end of selectList
	
	//다음 댓글 보기 버튼 클릭시 데이터 추가
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	
	/*---------------------------
	 * 댓글 등록
	 *---------------------------*/
	//댓글 등록
	$('#re_form').submit(function(event){
		//댓글 입력하지 않았을 때 알림
		if($('#re_content').val().trim()==''){
			alert('내용을 입력해주세요');
			$('#re_content').val('').focus();
			return false;
		}
		
		let form_data = $(this).serialize();
		//서버와 통신
		$.ajax({
			url:'writeReply',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 사용해주세요!');
				}else if(param.result == 'success'){
					//폼 초기화
					initForm();
					//댓글 작성이 성공하면 새로 삽입한 글을 포함해서 
					//첫번째 페이지의 게시글들을 다시 호출
					selectList(1);
				}
				
			},
			error:function(){
				alert('네트워크 오류');
			}
		});//end of ajax
		//기본 이벤트 제거
		event.prventDefault();
	})//end of submit
	
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}
	
	/*---------------------------
	 * 댓글 수정
	 *---------------------------*/
	//댓글 수정 버튼 클릭시 수정폼 노출
	$(document).on('click','.modify-btn',function(){
		//댓글 번호
		let re_num = $(this).attr('data-num');
		//댓글 내용
		let content = $(this).parent().find('p').html().replace(/<br>/gi,'\r\n');
		
		//댓글 수정폼
		let modifyUI = '<form id="mre_form">';
			modifyUI += '<input type="hidden" name="re_num" id="mre_num" value="'+re_num+'">';				  //미리보기
			modifyUI += '<textarea rows="3" cols="60" name="re_content" id="mre_content" class="rep-content2">'+content+'</textarea><br>';
			modifyUI += '<div id="mre_first" class="margin-span"><span class="letter-count">300/300</span></div>';//글자수 체크
			modifyUI += '<div id="mre_second" class="align-right">';
			modifyUI += ' <input type="submit" value="수정" id="modify_btn" class="d-inline-block text-dark text-uppercase bg-light border rounded-pill px-2 py-1 mb-1">';
			modifyUI += ' <input type="button" value="취소" class="re-reset d-inline-block text-dark text-uppercase bg-light border rounded-pill px-2 py-1 mb-1">';	
			modifyUI += '</div>';
			modifyUI += '<hr size="1" noshade width="96%">';
			modifyUI += '</form>';
			
		//이전에 이미 수정하는 댓글이 있을 경우 수정 버튼을 클릭하면 숨김
		//sub-item을 환원시키고(수정,삭제 버튼 보이는 수정폼) 수정폼을 초기화
		initModifyForm();
		//지금 클릭해서 수정하고자 하는 데이터는 감추기
		//수정 버튼을 감싸고 있는 div 감추기
		$(this).parent().hide();
		
		//수정폼을 수정하고자하는 데이터가 있는 div에 노출
				//parents:이벤트가 발생한 부모들 중에서 찾는것
		$(this).parents('.item').append(modifyUI);
		
		//입력한 글자수 셋팅
		let inputLength = $('#mre_content').val().length;
		let remain = 300 - inputLength;
		remain += '/300';
		
		//문서 객체에 반영
		$('#mre_first .letter-count').text(remain);

	});//end of click
	
	//수정폼에서 취소 버튼 클릭시 수정폼 초기화
	$(document).on('click','.re-reset',function(){
		initModifyForm();
	});//end of click
	
	//댓글 수정폼 초기화
	function initModifyForm(){
		$('.sub-item').show();//수정,삭제 버튼 보이는 수정폼 보이게
		$('#mre_form').remove();//수정,취소 버튼 보이는 수정폼 안보이게
	}
	
	//댓글 수정
	$(document).on('submit','#mre_form',function(event){
		//댓글 내용이 있는지 확인
		if($('#mre_content').val().trim()==''){
			alert('내용을 입력해주세요');
			$('#mre_content').val('').focus();
			return false;
		}
		//댓글 수정 alert
		if(!confirm('수정하시겠습니까?')){
        	return false; // 취소를 선택한 경우 폼 제출을 막음
    	}
			
		//폼에 입력한 데이터 반환
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'updateReply',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 수정 가능합니다.');
				}else if(param.result == 'success'){
					//내용 읽기																HTML태그 변환			HTML태그 변환				줄바꿈처리~
					$('#mre_form').parent().find('p').html($('#mre_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\r\n/,'<br>').replace(/\r/g,'<br>').replace(/\n/g,'<br>'));
					//최근 수정일 처리
					$('#mre_form').parent().find('.modify-date').text('최근 수정일 : 5초 미만');
					//수정폼 초기화
					initModifyForm();
				}else if(param.result == 'wrongAccess'){
					alert('타인의 글은 수정 불가합니다.');
				}else{
					alert('댓글 수정 오류(오타)');
				}
			},
			error:function(){
					alert('네트워크 오류');
			}
		});//end of ajax
		//기본 이벤트 제거
		event.preventDefault();
	});//end of submit
	
	
	/*---------------------------
	 * 댓글 등록, 수정 공통
	 *---------------------------*/
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 구하기
		let inputLength = $(this).val().length;
		
		if(inputLength>300){//300자 넘어선 경우
			$(this).val($(this).val().substring(0,300));
		}else{//300자 이하인 경우 남은 글자수 구하기
			let remain = 300 - inputLength;
			remain += '/300';
			if($(this).attr('id')=='re_content'){
				//등록폼 글자수
				$('#re_first .letter-count').text(remain);
			}else if($(this).attr('id')=='mre_content'){
				//수정폼 글자수
				$('#mre_first .letter-count').text(remain);
			}
		}
	});//end of keyup

	/*---------------------------
	 * 댓글 삭제
	 *---------------------------*/
	//댓글 삭제 alert
	
	$(document).on('click','.delete-btn',function(){
		//댓글 번호
		let re_num = $(this).attr('data-num');
		
		if(!confirm('댓글을 삭제하시겠습니까?')){
			return;
		}
		
		//서버와 통신
		$.ajax({
			url:'deleteReply',
			type:'post',
			data:{re_num:re_num},
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인 후 삭제 가능합니다.');
				}else if(param.result=='success'){
					alert('삭제 완료되었습니다.');
					selectList(1);
				}else if(param.result=='wrongAccess'){
					alert('타인의 글은 삭제할 수 없습니다.');
				}else{
					alert('댓글 삭제 오류(오타)');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		})//end of ajax
	});//end of click
	
	/*---------------------------
	 * 댓글수 표시
	 *---------------------------*/
	function displayReplyCount(param){
		let count = param.count;
		let output;
		if(count>0){
			output = '댓글수('+count+')';
		}else{
			output = '댓글수(0)';
		}
		//문서 객체의 추가
		$('#output_rcount').text(output);
	}

	/*---------------------------
	 * 초기 데이터(목록) 호출
	 *---------------------------*/
	selectList(1);

})//end of all function