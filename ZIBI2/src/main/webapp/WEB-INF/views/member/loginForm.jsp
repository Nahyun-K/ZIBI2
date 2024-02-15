<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <!-- Contact Start -->
<input type="hidden" id="kakao_apikey" value="${kakao_apikey}"/>
<input type="hidden" id="naver_apikey" value="${naver_apikey}"/>
<div class="container page-width">
	<div class="member-form row justify-content-center">
		<div class="col-7 align-self-center register-info">
			<h3>1인 가구를 위한 플랫폼, ZIBI</h3>
			<div>ZIBI는 1인 가구를 위한 각종 혜택과 정보를 모아 둔 플랫폼입니다.<br>간단한 정보로 회원가입 후 다양한 컨텐츠를 즐겨보세요 🤗</div>
			<a href="${pageContext.request.contextPath}/member/register">회원가입하기 > </a>
		</div>
		<div class="col-5 align-self-center">
			<form:form action="login" id="login" modelAttribute="memberVO">
				<div>
					<form:label path="mem_email">이메일</form:label>
					<form:input path="mem_email" class="w-100 form-control" />
					<form:label path="mem_password">비밀번호</form:label>
					<form:password path="mem_password" class="w-100 form-control" />
				</div>
				<div id="register-check" style="margin-top: 30px;"> <!-- 높이 고정 후 수직 가운데 정렬 -->
					<form:errors element="span"/>
				</div>
				<form:button class="btn mem-btn-green w-100" style="margin:10px 0 10px 0;">로그인</form:button>
				<div class="text-center">
					<a id="kakao-login-btn" href="javascript:loginWithKakao()">
						<img src="${pageContext.request.contextPath}/images/na/login_kakao.png" height="39" alt="카카오 로그인 버튼" />
					</a>
					<span id="naver_id_login"></span>
				</div>
				<input type="button" class="btn mem-btn w-100" value="비밀번호 분실" onclick="location.href='${pageContext.request.contextPath}/member/findPassword'">
			</form:form>
		</div>
	</div>
</div>
<script src="https://developers.kakao.com/sdk/js/kakao.js?appkey=${kakao_apikey}"></script>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/kakao.js"></script><!-- 소셜 로그인/회원가입 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/naver.js"></script><!-- 소셜 로그인/회원가입 -->