$(document).ready(function() {
	$('#quitSocial').click(function(){
		$('#passwordModal').show();
	});
	$('#cancel-btn').click(function(){
		$('#passwordModal').hide();	
	});
	
	//네이버 탈퇴
	function naverQuit(){
		//사이트 회원 탈퇴 후 컨트롤러에서 naver 연동 해제 페이지로 이동
		location.replace('quitMember');
	}
	
	function unlinkApp() {
		Kakao.API.request({
			url: '/v1/user/unlink',
			})
			.then(function(res) {
				location.replace('/member/quitMember');
			})
			.catch(function(err) {
				alert('카카오 연동 해제 실패');
			});
	}
});