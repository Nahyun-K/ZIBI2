$(function(){

	//--------------------------- 지역 선택 시작 ----------------------------
	let loc1 = 'location1';
	let loc2 = 'location2';
	
	selectLocation(loc1);
	
	var title;
	// table row 선택 함수
	function selectLocation(location){
		// 기본 영화 선택 리스트 지우기
		var table = document.getElementById(location);
		var rowList = table.rows;
		
		// table (지역) 클릭
		for (i=1; i<rowList.length; i++) {
			var row = rowList[i];     //thead 부분을 제외하기 위해 i가 1부터 시작됩니다.
			var tdsNum = row.childElementCount;     //아래 for문에서 사용하기 위해 row 하위에 존재하는 td의 갯수를 구합니다.
			row.onclick = function() {
				return function() {
				
				// 상영관 선택할 때 영화 초기화해주기
				$('#performance_hidden').attr('value','');
				var str = '';
				for (var j=0; j<tdsNum; j++) {
					var row_value = this.cells[j].innerHTML;
					
					//색 추가------------------
					if(location == loc1){
						$('.searchCinema1').css("background-color","");
					} else if(location == loc2){
						$('.searchCinema2').css("background-color","");
					}

					$(this.cells[j]).css("background-color","#A3C9AA");
					console.log(this.cells[j]);
					
					//-------------------------
					str += row_value + '';
				};
				
				console.log(str); // 지역 출력
				if(location == loc1){
					title = row_value;
					$('#selectCinemaTitle').text(title);
					console.log('지역1');
					location2(str); // 지역2의 테이블이 나오게
				} else if(location == loc2){
					var title2 = title + ' > ' + row_value;
					$('#selectCinemaTitle').text(title2);
					console.log('지역2');
					console.log(str);
					$('#cinema_hidden').attr('value',str); // form의 hidden 값 넣어주기
					// ===== [지역1, 지역2 모두 선택하면 선택할 수 있는 영화 고르기] =====
					ent(); // form hidden 값 
				}
				
				};
			}(row);
		} // end of for
	}
	
	
	
	// 지역2 table 생성 메소드
	function location2(location1){
		$.ajax({
			url:'selectLocation',
			type:'post',
			data:{location1:location1},
			dataType:'json',
			success:function(param){
				if(param.result=='success'){
					$('#location2 tbody').empty();
					let output = '';
					for(var i=0; i<param.listLoc2.length; i++){
						console.log("값 : " + param.listLoc2[i].cinema_num);
						output += '<tr class="searchCinema2"  id="'+param.listLoc2[i].cinema_num+'"><td class="searchCinema2 searchCinemaTable">' + param.listLoc2[i].cinema_location2 + '</td></tr>';
					}
					$('#location2 tbody').append(output);
					selectLocation(loc2);
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	
	
	//--------------------------- 지역 선택 끝 ----------------------------
	
	//--------------------------- 날짜 선택 시작 ----------------------------
	// 날짜 선택
	$('#date_list > input').on('click',function(){
		let dayValue = $(this).attr('value');
		$('#day_hidden').attr('value',dayValue); // form의 hidden 값 넣어주기
		$('#performance_hidden').attr('value', '');
		ent(); // form hidden 값 
		
	});
	
	//--------------------------- 날짜 선택 끝 ----------------------------

	//--------------------------- 상영관 날짜 영화 선택 시작 ----------------------------
	
	function ent(){
		let cinema = $('#cinema_hidden').val(); // 상영관
		let performance_num = $('#performance_hidden').val(); // 영화
		let day = $('#day_hidden').val(); // 날짜
		
		// 상영관이 없으면 아무것도 선택할 수 없음
		if(cinema==''){ // 상영관X 날짜O
			alert('상영관을 선택해주세요');
			$('#performance_hidden').attr('value','');
		} else { // 상영관O 날짜O
			// 상영관O 날짜O
			cinemaAndDay(cinema, day, performance_num);
		}
	}
	
	
	// 상영관 + 영화 + 날짜 선택 창
	function cinemaAndDay(cinema, day, performance_num){
			console.log('상영관 : ' + cinema + ' 날짜 : ' + day + ' 영화 : ' + performance);
			
			$.ajax({
			url:'resultPerformance',
			type:'post',
			data:{cinema:cinema,day:day,performance_num:performance_num}, // ajax 통신 4개 이상 안되는듯
			dataType:'json',
			success:function(param){
				if(param.result=='success'){
					// 초기화
					$('#resultSelect').empty(); // 최종 선택할 수 있는 영화 나옴 : 최종 선택
					$('#ticketing_Ent').empty(); // 두번쨀 영화 리스트 나옴 : 영화 선택
					$('newspan').empty();
					
					for(let i=0; i<param.resultCinema.length; i++){
						
						
						// 최종[날짜+시간] 목록 --> 영화 제목 / 상영관 / 여석 / 시간
						// output
						let outputResult = '<div class="ent" id="'+param.resultTicketing[i].ticketing_num+'" style="">'; // vertical-align이 안되어서 padding:0.5em; 했더니 됨
						outputResult += '<div>'+param.resultPerformance[i].performance_title+'</div>';
						outputResult += '<div>'+param.resultCinema[i].cinema_theater+'</div>';
						outputResult += '<div>'+param.resultTicketing[i].ticketing_start_time+'</div>';
						outputResult += '<div>여석:'+param.resultCinema[i].cinema_total+'/'+(param.resultCinema[i].cinema_row*param.resultCinema[i].cinema_col)+'</div>';
						outputResult += '</div>';
						// output 추가
						$('#resultSelect').append(outputResult);
						
						console.log(param.resultCinema[i].cinema_theater);
						console.log(param.resultTicketing[i].ticketing_start_time);
						
						// 영화 목록 --> 영화 제목 / 영화 포스터 / 연령제한
						// output
						let outputPerformance = '<tr class="ticketing-ent-row" id='+param.resultTicketing[i].performance_num+'>';
						outputPerformance += '<td class="ticketing-poster"><img style="" id="ticketing-poster-img"  src="https://image.tmdb.org/t/p/w500/'+param.resultPerformance[i].performance_poster+'"></td>'; // js에서 파일 읽어오는 것! .. 이용
						outputPerformance += '<td class="ticketing-info">'+param.resultPerformance[i].performance_title+'<br>'+param.resultPerformance[i].performance_age+'</td>';
						outputPerformance += '</tr>';
						// output 추가
						$('#ticketing_Ent').append(outputPerformance);
						console.log(param.resultPerformance[i].performance_title);
						console.log(param.resultPerformance[i].performance_poster);
						console.log(param.resultPerformance[i].performance_age);
						
						
					}
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	
	//--------------------------- 상영관 날짜 영화 선택 끝 ----------------------------
	
	// 영화 선택 클릭 이벤트
	$(document).on('click','.ticketing-ent-row',function(){ // script로 만든 태그는 document로 접근해야 함!!!!!
		let rowId = $(this).attr('id');
		if($('#cinema_hidden').val()==''){
			alert('상영관부터 선택해주세요');
		} else {
			$('#performance_hidden').attr('value',rowId);
			ent();
		}
	});
	
	
	$(document).on('click','.ent',function(){ // script로 만든 태그는 document로 접근해야 함!!!!!
		var ent_id = $(this).attr("id");
		$('#ent_hidden').attr('value',ent_id);
		$('.ent').css("background-color","");
		$(this).css("background-color","#A3C9AA");
	});
	
	
	
});