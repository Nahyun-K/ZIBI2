$(function(){
	let rowCount = 50;
	let currentPage;
	let rpcount;
	
	/*-------- 댓글 목록 --------*/
	//댓글 목록
	function selectList(pageNum){
		currentPage = pageNum;
		$('#loading').show();
		
		$.ajax({
			url:'listReply',
			type:'post',
			data:{pageNum:pageNum,rowCount:rowCount,book_num:$('#book_num').val()},
			dataType:'json',
			success:function(param){
				$('#loading').hide();
				rpcount = param.rpcount;
				
				if(pageNum == 1){
					$('#output').empty();
				}
				
				//댓글 수 읽어오기
				displayReplyCount(param);
				
				//댓글 목록 UI
				$(param.rplist).each(function(index,item){
					let output = '<div class="item">';
						output += '<input type="hidden" value="'+item.rep_num+'" id="hidden_num">';
						output += '<ul class="detail-info">';
						output += '<div class="rep-info"><li>';
						output += '<img src="../member/viewProfile?mem_num='+item.mem_num+'" class="rep-profile">';
						output += '</li>';
						output += '<li id="re_nick">'+'<a href="../member/mypageOpen?mem_num='+item.mem_num+'" class="nick-open">'+item.mem_nickname+'</a></li>';
						
						//공개
						if(item.book_deleted == 0){
							output += '<img src="../images/jy/rep_menu.png" class="rep-menu" data-num='+item.rep_num+'></div>';
						
							if(param.user_num == item.mem_num && item.ref_level == 1){//원댓글
								output += '<div class="rep-delete" id="rep_delete'+item.rep_num+'" data-num="'+item.rep_num+'">삭제</div>';
							}
							
							if(param.user_num == item.mem_num && item.ref_level == 2){//대댓글
								output += '<div class="rerep-delete" id="rep_delete'+item.rep_num+'" data-num="'+item.rep_num+'">삭제</div>';
							}
							
							output += '<li>';
							output += '<div class="sub-item" id="sub_item'+item.rep_num+'">';
							output += '<p>'+item.book_rep.replace(/\r\n/g,'<br>')+'</p>';
							output += '<span class="rep-date">'+item.book_repDate+'</span>';
							
						
							if(item.ref_level == 1){
								output += '<br><input type="button" value="답글" class="rerep-btn" data-num="'+item.rep_num+'" data-nick="'+param.mem_nick+'" data-mem="'+param.user_num+'"></div>';
							}
							
							output += '<div class="re-item" id="item_re'+item.rep_num+'"></div>';
						}
						
						//삭제 처리
						if(item.book_deleted == 1){
							output += '<div class="deleted-div">ⓘ 작성자에 의해 삭제된 댓글입니다.</div>';
							output += '<div class="re-item2" id="item_re'+item.rep_num+'"></div>';
						}
						
						
						
					if(item.ref_level == 1){//원댓글
						//문서 객체에 추가
						$('#output').prepend(output);
					}else if(item.ref_level == 2){//대댓글
						$('#item_re'+item.ref_rep_num).append('<span class="re-span">ㄴ </span>'+output)
													.css('border-top','1px solid #c2c2c2')
													.css('border-bottom','1px solid #c2c2c2')
													.css('padding-top','10px');
					}
				});
					
					//paging button 처리
					if(currentPage>=Math.ceil(rpcount/rowCount)){
						//다음 페이지가 없음
						$('.paging-button').hide();
					}else{
						//다음 페이지가 존재
						$('.paging-button').show();
					}
			},
			error:function(){
				$('#loading').hide();
				alert('네트워크 오류 발생');
			}
		});
	}
	//다음 댓글 보기 버튼 클릭 시 데이터 추가
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	
	
	/*-------- 댓글 등록 --------*/
	//댓글 등록
	$('#re_form').submit(function(event){
		if($('#book_rep').val().trim()==''){
			alert('댓글 내용을 입력하지 않았습니다.');
			$('#book_rep').val('').focus();
			return false;
		}
		
		let form_data = $(this).serialize();
		
		$.ajax({
			url:'insertReply',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'success'){
					//폼 초기화
					initForm();
					selectList(1);
				}else if(param.result == 'logout'){
					alert('로그인 후 이용하세요!');
					location.replace('/member/login');
				}else{
					alert('댓글 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		//기본 이벤트 제거
		event.preventDefault();
	});
	
	//댓글 작성 폼 초기화 함수
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-rpcount').text('300/300');
	}
	
	/*-------- 대댓글 등록 --------*/
	$(document).on('click','.rerep-btn',function(){
		
		let nick = $(this).attr('data-nick');
		let mem_num = $(this).attr('data-mem');
		
		if(nick == "undefined"){
			alert('로그인 후 이용하세요!');
			return;
		}
		
		//대댓글 UI
		let rerepUI = '<span class="re-span2">ㄴ </span>';
			rerepUI += '<form id="rere_form">';
			rerepUI += '<div class="rerep-info">'+'<img src="../member/viewProfile?mem_num='+ mem_num +'" class="rep-profile">' + nick+'</div>';
			rerepUI += '<textarea rows="3" cols="50" name="book_rep" id="book_rerep" class="rerep-content" placeholder="한 번 작성한 댓글은 수정이 불가합니다. 서로를 배려하는 댓글 문화에 동참해 주세요."></textarea>';
			rerepUI += '<div id="rere_first"><span class="letter-rpcount">300/300</span>';
			rerepUI += ' <input type="submit" value="등록" id="re_btn">';
			rerepUI += ' <input type="button" value="취소" class="re-reset" id="reset_btn">';
			rerepUI += '</div>';
			rerepUI += '</form>';
		
		//이전에 작업 중이던 대댓글 폼 초기화
		initReForm();
		$(this).parents('.item').append(rerepUI);
		
		//입력한 글자수 세팅
		let inputLength = $('#book_rerep').val().length;
		let remain = 300 - inputLength;
		remain += '/300';
		
		//문서 객체에 반영
		$('#rere_first .letter-rpcount').text(remain);
	});
	
	//취소 버튼 클릭 시 초기화
	$(document).on('click','.re-reset',function(){
		initReForm();
	});
	
	//대댓글 폼 초기화 함수
	function initReForm(){
		$('.re-span2').remove();
		$('#rere_form').remove();
	}
	
	//대댓글 등록
	$(document).on('submit','#rere_form',function(event){
		if($('#book_rerep').val().trim()==''){
			alert('댓글 내용을 입력하지 않았습니다.');
			$('#book_rerep').val('').focus();
			return false;
		}
		
		let ref_rep_num = $(this).parent().find('input').val();
		let book_rerep = $('#book_rerep').val();
		
		//서버와 통신
		$.ajax({
			url:'insertReReply',
			type:'post',
			data:{ref_rep_num:ref_rep_num,book_rerep:book_rerep,book_num:$('#book_num').val()},
			dataType:'json',
			success:function(param){
				if(param.result == 'success'){
					$('#rere_form').parent().find('p').html('#book_rerep').val().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\r\n/g,'<br>').replace(/\r/g,'<br>').replace(/\n/g,'<br>');
					initReForm();
					selectList(1);
				}else if(param.result == 'logout'){
					alert('로그인 후 이용하세요!');
					location.replace('/member/login');
				}else{
					alert('대댓글 등록 오류');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		//기본 이벤트 제거
		event.preventDefault();	
	});
	
	/*-------- 댓글 삭제 --------*/
	//삭제 버튼 노출하기/감추기
	$(document).on('click','.rep-menu',function(){
		let rep_num = $(this).attr('data-num');
		$('#rep_delete'+rep_num).show();
		
		$('.item').click(function(){
			$('#rep_delete'+rep_num).hide();
		});
	});
	
	//댓글 삭제
	$(document).on('click','.rep-delete, .rerep-delete',function(){
		if(confirm('댓글을 삭제하시겠습니까?')==false){
			return;
		}
		
		let rep_num = $(this).attr('data-num');
		
		//서버와 통신
		$.ajax({
			url:'deleteReply',
			type:'post',
			data:{rep_num:rep_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'success'){
					selectList(1);
				}else if(param.result == 'wrongAccess'){
					alert('타인의 댓글은 삭제할 수 없어요!');
				}else if(param.result == 'logout'){
					alert('로그인 후 이용하세요!');
					location.replace('/member/login');
				}else{
					alert('댓글 삭제 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}		
		});
	});
	
	/*-------- 댓글 글자수 체크 --------*/
	$(document).on('keyup','textarea',function(){
		let inputLength = $(this).val().length;
		
		if(inputLength>300){
			$(this).val($(this).val().substring(0,300));
		}else{//300자 이하인 경우
			//남은 글자수 구하기
			let remain = 300 - inputLength;
			remain += '/300';
			if($(this).attr('id')=='book_rep'){
				//등록폼 글자수
				$('#re_first .letter-rpcount').text(remain);
			}else if($(this).attr('id')=='book_rerep'){
				//대댓글 등록폼 글자수
				$('#rere_first .letter-rpcount').text(remain);
			}
		}
	});
	
	/*-------- 댓글수 표시 함수 --------*/
	function displayReplyCount(param){
		let count = param.rpcount;
		let output;
		if(count > 0){
			output = '💭 ' + count + ' Comments';
		}else{
			output = '💭 0 Comments';
		}
		//문서 객체에 추가
		$('#reply_count').text(output);
	}
	
	/*-------- 초기 데이터(목록) 호출 --------*/
	selectList(1);
});