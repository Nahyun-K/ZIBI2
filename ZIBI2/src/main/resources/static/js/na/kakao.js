
	const KaApiKey = $('#kakao_apikey').val();
	Kakao.init(KaApiKey); //카카오 초기화
	Kakao.isInitialized(); // 초기화 판단
	
	function loginWithKakao() { //로그인 버튼 클릭 시 실행되는 함수
		Kakao.Auth.login({ //카카오 서버에 로그인 요청
			success : function(authObj) { // 성공 시 access 토큰 값(authObj) 발급 받음
				Kakao.Auth.setAccessToken(authObj.access_token); // access 토큰 값 저장
				getInfoKakao(); //사용자 정보 요청 함수 실행
			},
			fail : function() {
				alert('카카오 토큰 값 가져오기 실패');
			}
		});
	}
	
	function getInfoKakao() { //사용자 정보 요청 함수
		Kakao.API.request({ //카카오 서버로부터 응답을 받음
			url : '/v2/user/me',
			success : function(res) { //성공 시
				let email = res.kakao_account.email; //이메일 정보 받아옴
				$.ajax({
					url:'loginSocial',
					type:'post',
					data:{mem_email:email,mem_social:1},
					dataType:'json',
					success: function(param){
						if(param.result=='success'){
							location.replace("/main/home");
						} else if(param.result=='socialNotMatch'){
							alert('이미 가입된 이메일입니다. 다른 방법으로 로그인하세요');
							window.close();
						} else{
							alert('카카오 로그인 오류');
						}
					},
					error:function(){
						alert('네트워크 통신 오류');
					}
				});
			},
			fail : function() {
				alert('카카오 로그인/회원가입에 실패했습니다. 관리자에게 문의하세요.');
			}
		});
	}
	
	function logoutKakao() { //카카오 서버에 접속하는 액세스 토큰을 만료시킴
		if (!Kakao.Auth.getAccessToken()) {
			alert("로그인을 먼저 하세요.");
			return;
		}
		 Kakao.Auth.logout(() => {
			location.replace('/member/logout');
		});
	}
	
	function quitKakao() { //카카오 서버에 접속하는 액세스 토큰을 만료시킴
		if (!Kakao.Auth.getAccessToken()) {
			alert("로그인을 먼저 하세요.");
			return;
		}
		 Kakao.Auth.logout(() => {
			location.replace('/member/quitMember');
		});
	}
