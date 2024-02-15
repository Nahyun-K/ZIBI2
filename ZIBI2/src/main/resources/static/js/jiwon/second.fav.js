$(function(){
	/*-----------------------
	* 찜 읽기
	*------------------------*/
	//찜 선택 여부와 선택한 총 개수 표시
	function sc_selectFav(sc_num){
		$.ajax({
			url:'sc_getFav',
			type:'post',
			data:{sc_num:sc_num},
			dataType:'json',
			success:function(param){//param에 데이터 담겨옴
				sc_displayFav(param); //param에 status와 count가 담겨온다
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
	}
	
	/*-----------------------
	* 찜 등록/삭제
	*------------------------*/
	$('#output_fav').click(function(){
		$.ajax({
			url:'sc_writeFav',
			type:'post',
			data:{sc_num:$('#output_fav').attr('data-num')}, //sc_num 넘기기
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 찜을 눌러주세요');
				}else if(param.result == 'success'){
					sc_displayFav(param);
				}else{
					alert('등록/삭제 시 오류 발생')
				}
			},
			error:function(){
				alert('네트워크 오류!');
			}	
		})
	});
	/*-----------------------
	* 찜 표시 공통 함수
	*------------------------*/
	function sc_displayFav(param){
		let output;
		if(param.status == 'yesFav'){//좋아요를 선택함
			output = '../images/jiwon/sc_fav_full.png';
		}else if(param.status == 'noFav'){
			output = '../images/jiwon/sc_fav.png';
		}else{
			alert('찜 표시 오류 발생');
		}
		//문서 객체에 추가
		$('#output_fav').attr('src',output);
		$('#output_fcount').text(param.count);
	}
	
	//초기 데이터 표시
	sc_selectFav($('#output_fav').attr('data-num'));
	
});