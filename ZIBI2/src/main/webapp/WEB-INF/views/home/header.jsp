<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 헤더 시작 -->
<header class="main-header" style="padding-top: 20px;">
	<div class="container">
		<div class="col-md-12 my-auto text-end" id="member_menu">
			<c:if test="${!empty user}">
				<a class="nickname-area" href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${user.mem_num}">
					<img src="${pageContext.request.contextPath}/images/na/header_member.png" width="15px"> ${user.mem_nickname}님
				</a>
				<c:if test="${user.mem_social!=1}">
					<a href="${pageContext.request.contextPath}/member/logout">로그아웃</a>
				</c:if>
				<c:if test="${user.mem_social==1}">
					<a href="${pageContext.request.contextPath}/member/logoutKakao">로그아웃</a><!-- 카카오에서 제공하는 로그아웃 기능 이용 -->
				</c:if>
			</c:if>
			<c:if test="${empty user}">
				<a href="${pageContext.request.contextPath}/member/login">로그인</a>
			</c:if>
			<c:if test="${empty user}">
				<a href="${pageContext.request.contextPath}/member/register">회원가입</a>
			</c:if>
			<c:if test="${!empty user && user.mem_auth!=9}">
				<a href="${pageContext.request.contextPath}/member/mypageMain">마이페이지</a>
			</c:if>
			<c:if test="${!empty user && user.mem_auth==9}">
				<a href="${pageContext.request.contextPath}/admin/policy">관리자 페이지</a>
			</c:if>
			<a id="questionPopup_btn">문의하기</a>
		</div>
		<div class="text-end" id="question_popup" style="display: none;">
			229rkawk@google.com으로 문의해 주세요!😉 (운영 시간 월~금, 9시~18시)
			<input type="button" class="btn mem-btn" value="닫기" id="close_questionPopup_btn">
		</div>
		<nav class="navbar navbar-expand-lg">
			<a href="${pageContext.request.contextPath}/main/home" class="navbar-brand">
				<img id="main_logo" src="${pageContext.request.contextPath}/images/logo_rec.png" width="100px">
			</a>
			<div class="collapse navbar-collapse align-self-end" id="navbarCollapse">
				<div class="navbar-nav">
					<div class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" data-bs-toggle="dropdown">득템찬스</a>
						<div class="dropdown-menu">
							<a href="${pageContext.request.contextPath}/secondhand/list" class="dropdown-item">중고거래</a> 
							<a href="${pageContext.request.contextPath}/helper/list" class="dropdown-item">재능기부</a>
						</div>
					</div>
					<div class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" data-bs-toggle="dropdown">문화꿀팁</a>
						<div class="dropdown-menu">
							<a href="${pageContext.request.contextPath}/performance/list" class="dropdown-item">영화 예매</a> 
							<a href="${pageContext.request.contextPath}/book/list" class="dropdown-item">소모임 예약</a> 
							<a href="${pageContext.request.contextPath}/community/list" class="dropdown-item">커뮤니티</a>
						</div>
					</div>
					<div class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" data-bs-toggle="dropdown">척척박사</a>
						<div class="dropdown-menu">
							<a href="${pageContext.request.contextPath}/policy/policyCount" class="dropdown-item">1인 가구 정보</a> 
						</div>
					</div>
				</div>
			</div>
			<div class="collapse navbar-collapse justify-content-end"></div>
		</nav>
	</div>
</header>
<!-- 헤더 끝 -->
<script>
	$('#questionPopup_btn').click(function(){
		$('#question_popup').show();
	});
	$('#close_questionPopup_btn').click(function(){
		$('#question_popup').hide();
	});
</script>