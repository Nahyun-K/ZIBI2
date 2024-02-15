<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input type="hidden" value="${kakao_apikey}" id="kakao_apikey">
<div class="member-page mypageMain">
	<div class="row profile-detail">
		<div class="col-6 event text-center align-self-center">
			<div class="photo-area">
				<div class="event-img position-relative">
					<img class="my-photo img-fluid" src="${pageContext.request.contextPath}/member/photoView">
					<div class="event-overlay">
						<a data-lightbox="event-1">
							<img id="photo_btn" src="${pageContext.request.contextPath}/images/na/mypage-change.png" width="40px">
						</a>
					</div>
				</div>
				<div id="photo_choice" style="display: none;">
					<input type="file" id="upload" accept="image/gif,image/png,image/jpeg">
					<input type="button" value="변경" class="btn mem-btn" id="photo_submit"> 
					<input type="button" value="취소" class="btn mem-btn" id="photo_reset">
					<div id="photo_check"></div>
				</div>
			</div>
		</div>
		<div class="col-6 align-self-center">
			<h6>
				회원 가입일
				<c:if test="${!empty memberVO.mem_modidate}"> / 정보 수정일</c:if>
			</h6>
			<p>
				${memberVO.mem_regdate}
				<c:if test="${!empty memberVO.mem_modidate}"> / ${memberVO.mem_modidate}</c:if>
			</p>
			<h6>이메일</h6>
			<p>${memberVO.mem_email}</p>
			<h6>닉네임</h6>
			<p>${memberVO.mem_nickname}</p>
			<c:if test="${!empty memberVO.mem_name}">
				<h6>이름</h6>
				<p>${memberVO.mem_name}</p>
				<h6>연락처</h6>
				<p>${memberVO.mem_phone}</p>
				<h6>주소</h6>
				<p>${memberVO.mem_address1} ${memberVO.mem_address2} (${memberVO.mem_zipcode})</p>
			</c:if>
			
		</div>
	</div>
	<div class="row">
		<div class="col-6">
			<input type="button" value="나의 오픈 프로필" class="btn  mem-btn-green w-100" onclick="location.href='${pageContext.request.contextPath}/member/mypageOpen?mem_num=${user.mem_num}'">
		</div>
		<div class="col-6 text-center">
			<input type="button" class="btn mem-btn" value="정보 수정하기" onclick="location.href='${pageContext.request.contextPath}/member/mypageUpdate'">
			<c:if test="${user.mem_social==0}">
				<input type="button" class="btn mem-btn" value="비밀번호 변경" onclick="location.href='${pageContext.request.contextPath}/member/passwordUpdate'">
				<input type="button" class="btn mem-btn" value="회원 탈퇴" onclick="location.href='${pageContext.request.contextPath}/member/checkPassword'">
			</c:if>
			<c:if test="${user.mem_social!=0}">
				<input type="hidden" id="apikey" value="${apikey}"/>
				<input type="button" class="btn mem-btn" id="quitSocial" value="연동 해제 및 회원 탈퇴">
				<div class="wrap" id="passwordModal" style="display: none">
					<div class="modal_box">
						<p>
							<img src="${pageContext.request.contextPath}/images/logo_mini.png" >
							정말로 탈퇴하시겠습니까?
						</p>
						<span id="quit"></span>
						<div>
							<input type="button" class="btn mem-btn-green" value="회원 탈퇴" <c:if test="${user.mem_social==1}">onclick="javascript:quitKakao()"</c:if><c:if test="${user.mem_social==2}">onclick="javascript:naverQuit()"</c:if>>
							<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
							<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/kakao.js"></script>
							<button class="btn mem-btn" id="cancel-btn">취소하기</button>
						</div>
					</div>
					<div class="modal_bg"></div>
				</div>
				<script src="https://developers.kakao.com/sdk/js/kakao.js?appkey=${kakao_apikey}"></script>
				<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/kakao.js"></script>
				<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/quitSocialMember.js"></script><!-- 네이버 탈퇴 -->
			</c:if>
		</div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/mypageProfileImage.js"></script>
<script>
	$('#main_btn').toggleClass("mem-btn");
	$('#main_btn').toggleClass("mem-btn-green");
</script>