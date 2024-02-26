$(function(){ // performanceSeat.jsp 
	
	let ticketing_num = $('#ticketing-num').text();
	let rev_cnt; //인원수
	const set_choice = {}; // 객체 -> 두 개 이상의 좌석을 선택할 때 사용 key value로
	let count = 0; // 선택한 인원 수(rev_cnt와 비교)
	
	/* ----------------------------------
	 * 인원 선택 시작
	 * ---------------------------------- */
	
	$('.adult-minus').on('click',function(){
		underEight(-1, 0, 0);
	});
	$('.adult-plus').on('click',function(){
		underEight(1, 0, 0);
	});
	
	$('.teenage-minus').on('click',function(){
		underEight(0, -1, 0);
	});
	$('.teenage-plus').on('click',function(){
		underEight(0, 1, 0);
	});

	$('.treatement-minus').on('click',function(){
		underEight(0, 0, -1);
	});
	$('.treatement-plus').on('click',function(){
		underEight(0, 0, 1);
	});
	
	// 전체 인원수 확인 0명 이상 8명 이하 (음수가 될 수 없고 전체 인원은 8명 이하만 가능)
	function underEight(adult_num, teenage_num, treatement_num){
		// 리셋해주기
		rowAndCol(ticketing_num); // 리셋
		
		let adult = Number($('.adult-num').text())+adult_num;
		let teenage = Number($('.teenage-num').text())+teenage_num;
		let treatement = Number($('.treatement-num').text())+treatement_num;
		
		if(adult<0 || teenage<0 || treatement<0){
			alert('0 미만은 선택할 수 없습니다');
		} else if(adult + teenage + treatement > 8) {
			alert('8 이하의 인원만 선택할 수 있습니다');
		} else {
			// 값 업데이트
			$('.adult-num').text(adult);
			$('.teenage-num').text(teenage);
			$('.treatement-num').text(treatement);
			
			// 폼에 값 넣어주기
			$('#adult_money').attr('value',adult);
			$('#teenage_money').attr('value',teenage);
			$('#treatement_money').attr('value',treatement);
			
			// 인원 수 업데이트
			rev_cnt = calculatePeopleNum();
		}
		
		totalMoney(adult,teenage,treatement); // 가격 정보
	}
	
	// 가격 정보
	function totalMoney(adult,teenage,treatement){
		
		let multiplication = ' X ';
		// UI 업데이트
		$('.adult_money').text(multiplication + adult);
		$('.teenage_money').text(multiplication + teenage);
		$('.treatement_money').text(multiplication + treatement);
		
		// form에 전송
		$('#adult_money').attr('value',adult);
		$('#teenage_money').attr('value',teenage);
		$('#treatement_money').attr('value',treatement);
		
	}
	
	
	// 전체 인원 수 계산
	function calculatePeopleNum(){
		let result = Number($('.adult-num').text()) + Number($('.teenage-num').text()) + Number($('.treatement-num').text());
		console.log('전체 인원 수 : ' + result);
		return result;
	}
	
	/* ----------------------------------
	 * 좌석
	 * ---------------------------------- */
	
	rowAndCol(ticketing_num); // 맨 처음 실행 됨
	
	// ticketing-num으로 row, col 알아내기
	function rowAndCol(ticketing_num){
		console.log('진입');
		$.ajax({
			url:'drawSeat',
			type:'post',
			data:{ticketing_num:ticketing_num},
			dataType:'json',
			success:function(param){
				seat(param);
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	
	// 좌석 그리기
	// 클릭 가능한 seat 출력
	function seat(param){
		count = 0;

		$('#seat').empty();
		let output = '';
		for(let i=0; i<param.pickCinema[0].cinema_row; i++){
			for(let j=0; j<param.pickCinema[0].cinema_col; j++){
				output += '<div id="'+i+'_'+j+'" class="seat-style" data-row="'+param.pickCinema[0].cinema_row+'" data-col="'+param.pickCinema[0].cinema_col+'"></div>';
			}
			output += '<br>';
		}
		$('#seat').append(output);
		
		// 이미 선택된 좌석은 선택할 수 없음
		for(let i=0; i<param.choosenSeat.length; i++){
			console.log(param.choosenSeat[i].seat_row+'_'+param.choosenSeat[i].seat_col);
			$('#'+(param.choosenSeat[i].seat_row)+'_'+(param.choosenSeat[i].seat_col)).addClass("seat-full");
		}
		
		
				
	}
	
	$(document).on('mouseover','.seat-style',function(){
		$(this).css("cursor","pointer");
		$(this).addClass('rev-over-style');
	});
	
	$(document).on('mouseout','.seat-style',function(){
		$('.seat-style').removeClass('rev-over-style');
	});
	
	
	/* ----------------------------
	 * 좌석 선택
	 * ---------------------------- */
	
	 
	$(document).on('click','.seat-style',function(){ // 토글 형태
		var totalPeopleNum = calculatePeopleNum();
		if(totalPeopleNum == 0){
			alert('인원을 선택해주세요');
		}
	
		// 좌석 취소
		if($(this).hasClass('seat-click')) { // 좌석 취소
			$(this).removeClass('seat-click'); // 선택한 좌석 class 제거
			let value = set_choice[$(this).attr('id')];
			count -= 1;
			
			if(value != ''){ // 좌석이 2자리일 때
				$('#'+value).removeClass('seat-click'); // 선택한 좌석(key) = 옆 좌석(value) 삭제
				count -= 1;
			}

		} else { // 좌석 선택
			if((rev_cnt-count) == 1){ // 인원 : 1
				if(!$(this).hasClass('seat-click') && !$(this).hasClass('seat-full')){ // 선택 가능한 좌석 -> 선택
					$(this).addClass('seat-click');
					set_choice[$(this).attr('id')] = '';
					count += 1;
				}
			} else if (rev_cnt > count) { // 인원수 > 선택한 좌석            // 선택한 좌석이 선택할 인원 수와 같거나 크다면 더 이상 선택할 수 없다
				if(rev_cnt >= 2 && rev_cnt <= 8){ // 인원 : 2 이상 8 이하
					if($(this).next()[0].tagName != 'BR'){ // 맨 끝자리 X
						if(!$(this).hasClass('seat-click') && !$(this).next().hasClass('seat-click') && !$(this).hasClass('seat-full') && !$(this).next().hasClass('seat-full')){ // 두 좌석 모두 선택할 수 있을 때
							$(this).addClass('seat-click'); // 선택한 좌석에 class 추가
							$(this).next().addClass('seat-click'); // 선택 옆 좌석 class 추가
							set_choice[$(this).attr('id')] = $(this).next().attr('id'); // key(선택좌석) : value(오른쪽 좌석) 
							set_choice[$(this).next().attr('id')] = $(this).attr('id'); // key(오른쪽 좌석) : value(선택좌석)
							count += 2;
						}
						
					} else { // 맨 끝자리 O
						if(!$(this).hasClass('seat-click') && !$(this).prev().hasClass('seat-click') && !$(this).hasClass('seat-full') && !$(this).next().hasClass('seat-full')){ // 두 좌석 모두 선택할 수 있을 때
							$(this).addClass('seat-click'); // 선택한 좌석에 class 추가
							$(this).prev().addClass('seat-click'); // 선택 옆 좌석 class 추가
							set_choice[$(this).attr('id')] = $(this).prev().attr('id'); // key(선택좌석) : value(왼쪽 좌석)
							set_choice[$(this).prev().attr('id')] = $(this).attr('id'); // key(왼쪽 좌석) : value(선택좌석)
							count += 2;
						}
					}
				}
			}	
		}	
		
		let row = $('.seat-style').data('row');
		let col = $('.seat-style').data('col');
		resultChoiceSeat(row, col)
		
	});
	
	// 선택한 좌석 값 form에 넣기
	function resultChoiceSeat(row, col){
		let output = '';
		for(let i=0; i<row; i++){
			for(let j=0; j<col; j++){
				if($('#'+i+'_'+j).hasClass('seat-click')){
					let thisSeat = i+'_'+j+' ';
					output += thisSeat;
				}
			}
		}
		$('#seat_info').attr('value',output);
	}
	
	
	
});