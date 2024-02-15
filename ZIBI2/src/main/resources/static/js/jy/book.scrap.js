$(function(){
	/*-------- 스크랩 읽기 함수 --------*/
	function selectScrap(book_num){
		$.ajax({
			url:'getScrap',
			type:'post',
			data:{book_num:book_num},
			dataType:'json',
			success:function(param){
				processScrap(param);
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
	}
	
	/*-------- 스크랩 등록/삭제 --------*/
	$('#output_scrap').click(function(){
		$.ajax({
			url:'clickScrap',
			type:'post',
			data:{book_num:$('#output_scrap').attr('data-num')},
			dataType:'json',
			success:function(param){
				if(param.result == 'success'){
					processScrap(param);
				}else if(param.result == 'bookWriter'){
					alert('내가 작성한 글은 스크랩할 수 없어요!');
				}else if(param.result == 'logout'){
					alert('로그인 후 이용하세요!');
				}else{
					alert('스크랩 등록/삭제 시 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
	});
	
	/*-------- 스크랩 표시 공통 함수 --------*/
	function processScrap(param){
		let output;
		if(param.status == 'yesScrap'){
			output = '../images/jy/yesScrap.png';
		}else if(param.status == 'noScrap'){
			output = '../images/jy/noScrap.png';
		}else{
			alert('스크랩 표시 오류 발생');
		}
		//문서 객체에 추가
		$('#output_scrap').attr('src',output);
		$('#output_scount').text(param.count);
	}
	
	//초기 데이터 표시
	selectScrap($('#output_scrap').attr('data-num'));
});