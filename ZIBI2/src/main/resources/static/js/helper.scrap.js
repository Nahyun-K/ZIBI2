$(function(){
	/*------------------
	 * 스크랩 읽기
	 *------------------*/
	//스크랩 선택 여부와 선택한 총 개수 표시
	function selectScrap(helper_num){
		$.ajax({
			url:'getScrap',
			type:'post',
			data:{helper_num:helper_num},
			dataType:'json',
			success:function(param){
				displayScrap(param);
			},
			error:function(){
				alert('네트워크 오류');
			}
			
		});//end of ajax
	}//end of selectScrap	
	
	/*------------------
	 * 스크랩 등록 삭제
	 *------------------*/
	$('#output_scrap').click(function(){
		$.ajax({
			url:'writeScrap',
			type:'post',
			data:{helper_num:$('#output_scrap').attr('data-num')},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 스크랩 할 수 있습니다.');
				}else if(param.result == 'success'){
					displayScrap(param);
				}else{
					alert('등록/삭제 시 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		});//end of ajax
	});//end of click

	/*------------------
	 * 스크랩 표시 공통 함수
	 *------------------*/
	function displayScrap(param){
		let output;
		if(param.status == 'yesScrap'){
			output = '../images/de/heart2.png';
		}else if(param.status == 'noScrap'){
			output = '../images/de/heart1.png';
		}else{
			alert('스크랩 표시 오류');
		}
		//문서 객체에 추가
		$('#output_scrap').attr('src',output);
		$('#output_scount').text(param.count);
	}//end of displayScrap
	
	//초기 데이터 표시
	selectScrap($('#output_scrap').attr('data-num'));
});//end of function