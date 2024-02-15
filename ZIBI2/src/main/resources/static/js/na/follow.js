$(document).ready(function() {
	let urlParams = new URL(location.href).searchParams; //get 방식으로 넘겨저온 파라미터를 읽음
	let mem_num = urlParams.get('mem_num');
	
	selectFollow(mem_num);
	
	function followBtn(value,button){
		$('#follow_btn').attr('value',value);
		$('#follow_btn').attr('class',button);
	}
	
	function displayFollow(param){ //팔로우 표시
		if(param.status == 'yesFollow'){ //팔로우 중
			followBtn('언팔로우','btn mem-btn');
		} else if(param.status == 'noFollow'){ //팔로우 하지 않음
			followBtn('팔로우','btn mem-btn-green');
		} else if(param.status == 'disabledFollow'){ //나 자신인 경우
			$('#follow_btn').hide();
		} else if(param.status == 'logout'){ //로그인하지 않은 경우
			$('#follow_btn').hide();
		} else { //오류일 경우
			alert('팔로우 표시 오류');
		}
		$('#follower').text(param.count);
	}
	
	function selectFollow(mem_num){ //팔로우 유무
		$.ajax({
			url:'getFollow',
			type:'post',
			data:{mem_num:mem_num},
			dataType:'json',
			success:function(param){
				displayFollow(param);
			},
			error:function(){
				alert('팔로우 읽기 네트워크 통신 오류');
			}
		});
	}
	
	$('#follow_btn').click(function(){ //팔로우 버튼 클릭 시
		$.ajax({
			url:'writeFollow',
			type: 'post',
			data:{mem_num:mem_num},
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해주세요');
				} else if(param.result=='fail'){
					alert('본인은 팔로우할 수 없습니다');
				} else if(param.result=='success'){
					displayFollow(param);
				} else {
					alert('팔로우 처리 오류 발생');
				}
			},
			error:function(){
				alert('팔로우 처리 네트워크 오류 발생');
			}
		});
	});
});