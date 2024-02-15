$(function(){
	//입력 유효성 체크
	$('#book_review').submit(function(){
		//평점
		let check = false;
		for(let i=0;i<book_review.book_grade.length;i++){
			if(book_review.book_grade[i].checked == true){
				check = true;
			}
		}
		if(check == false){
			$('#gradeValid').text('평점을 선택해 주세요.');
			return false;
		}else{
			$('#gradeValid').text('');
		}
		
		//후기 내용
		if($('#book_rev').val().trim()==''){
			$('#revValid').text('후기 내용을 입력해 주세요.');
			return false;
		}else{
			$('#revValid').text('');
		}
	});
	
	//후기 입력 글자수 체크
	$('#book_rev').keyup(function(){
		let inputLength = $(this).val().length;
		
		if(inputLength > 100){
			$(this).val($(this).val().substring(0,100));
		}else{
			let remain = 100 - inputLength;
			remain += '/100';
			$('.letter-count').text(remain);
		}
	});
});
