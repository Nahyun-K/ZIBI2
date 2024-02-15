$(function(){
	let message_socket; //웹소켓 식별자
	
	/*-----------------------------
	* 웹소켓 연결
	*------------------------------*/
	//웹소켓 생성
	if($('#chatDetail').length>0){
		connectWebSocket();
	}	
	//웹소켓 연결
	function connectWebSocket(){		//지정해주기		포트	/식별자
		message_socket = new WebSocket("ws://localhost:8000/message-ws");
		message_socket.onopen=function(evt){//evt는 event
			console.log("채팅페이지 접속 : " + $('#chatDetail').length);//jquery는 배열로 접근함 length가 0이면 없는거. 1이면 detail 페이지에 들어가 있는거다
			if($('#chatDetail').length == 1){
				message_socket.send("msg:");//msg:이면 메시지를 날려서 읽어오게끔 = 초기 데이터 호출
				//msg: 이면 채팅을 하고 있다는 얘기라서 데이터를 계속 읽어야한다.
			}//msg뒤에 내용이 없고 msg:라는 식별자만 보내는 이유는 우리는 db에 데이터를 저장하기 때문이다.  
		};//onopen해서 세션의 메시지를 읽어오기 때문에
		//서버로부터 메시지를 받으면 호출되는 함수 지정
		message_socket.onmessage=function(evt){
			let data = evt.data;	
			//chatDetail 페이지에 있고, msg: 라는 메시지가 만들어지면 그 데이터는 '세팅데이터를 보내는구나' 라고 알 수 있다.
			if($('#chatDetail').length == 1 && data.substring(0,4) == 'msg:'){
				selectMsg();
			}
		};
		//소켓 종료시
		message_socket.onclose=function(evt){
			//소켓이 종료된 후 부가적인 작업이 있을 경우 명시
			console.log('chat close');
		}
	}
	/*-----------------------------
	* 채팅하기
	*------------------------------*/
	//채팅 데이터 읽기
	function selectMsg(){
		//서버와 통신
		$.ajax({
			url:'../secondchat/chatDetailAjax',
			type:'post',
			data:{chatroom_num:$('#chatroom_num').val()},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 사용하세요!');
					//로그아웃되었으니 소켓을 끊어줘야 한다
					message_socket.close();
				}else if(param.result == 'success'){
					//메시지 표시 UI 초기화
					$('#chatting_message').empty();
					
					let chat_reg_date='';
					$(param.list).each(function(index,item){
						let output = '';
						if(chat_reg_date != item.chat_reg_date.split(' ')[0]){
							chat_reg_date = item.chat_reg_date.split(' ')[0];
							output += '<div class="date-position"><span>'+chat_reg_date+'</span></div>';
						}
						//일반 메시지
						if(item.mem_num == param.user_num){//작성자와 로그인한 사람이 같은 경우 오른쪽 배치
							output += '<div class="from-position">'+item.mem_nickname;
							output += '<div>';
						}else{//다를 경우 왼쪽 배치
							output += '<div class="to-position">';
							output += '<div class="space-photo">';
							output += '<img src="../member/viewProfile?mem_num='+item.mem_num+'" width="40" height="40" class="my-photo">';
							output += '</div><div class="space-message">';
							output += item.mem_nickname;
						}
						
						
						output += '<div class="item">';
						if(item.mem_num==param.user_num){
							if(item.chat_read_check==0){
								output+='';
							}else{
								output+= '<span class="chat-read-check">' + item.chat_read_check + '</span>';
							}
						}
						output += '<span>' + item.chat_message.replace(/\r\n/g,'<br>').replace(/\r/g,'<br>').replace(/\n/g,'<br>') + '</span>';
						
						//시간 추출
						output += '<div class="align-right">'+item.chat_reg_date.split(' ')[1]+'</div>';
						output += '</div>';
						output += '</div><div class="space-clear"></div>';
						output += '</div>';
						
						//문서 객체에 추가
						$('#chatting_message').append(output);
						//스크롤을 하단에 위치시킴  - 데이터 많을 때 스크롤 처리 됨 
						$('#chatting_message').scrollTop($('#chatting_message')[0].scrollHeight);
						
					});//end of each
				}else{
					alert('채팅 메시지 읽기 오류 발생');
					//소켓 중단
					message_socket.close();
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
				//소켓 중단  - 에러나는 경우
				message_socket.close();
			}
		});//end of ajax
	}
	//메시지 입력 후 enter 이벤트 처리  - 채팅에서는 엔터가 전송처리가 된다.
	$('#chat_message').keydown(function(event){
		if(event.keyCode == 13 && !event.shiftKey){//shift가 눌리지 않고
			$('#chatdetail_form').trigger('submit');//submit이벤트가 발생한 것처럼 한다. 
		}
	});
	//채팅 메시지 등록
	$('#chatdetail_form').submit(function(event){
		if($('#chat_message').val().trim()==''){
			alert('메시지를 입력하세요');
			$('#chat_message').val('').focus();
			return false;
		}
		//글자수 제한
		if($('#chat_message').val().length>1333){ //너무 긴 메시지는 보내지 않게
			alert('메시지를 1333자까지만 입력 가능합니다.');//숫자는 알아서 지정
			return false; //submit은 return false 이다.
		}	
		//form 이하의 태그에 입력한 데이터를 모두 읽어옴
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'../secondchat/writeChat',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
					//소켓 중단
					message_socket.close();
				}else if(param.result == 'success'){
					//폼 초기화      - 데이터가 전송되었기 때문에 폼 초기화
					$('#chat_message').val('').focus();
					//다른 사람것도 바꾸기 위해서는 
					//메시지가 저장되었다고 소켓에 신호를 보냄  
					message_socket.send('msg:');//msg:는 메시지 보내는 식별자					
					//모든 회원들에게 신호를 보내는 거다. 그러면 msg:가 동작하면서 회원들은 새로 갱신한 데이터를 보는거다
				}else{//잘못 명시했을 때 
					alert('채팅 메시지 등록 오류');
					//소켓 중단
					message_socket.close();
				}
			},
			error:function(){
				alert('네트워크 오류');
				//소켓 중단
				message_socket.close();
			}
		});
		//기본 이벤트 제거
		event.preventDefault();
	});
});