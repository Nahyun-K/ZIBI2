<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container form-width">
	<div class="member-form">					
		<form:form action="quitMember" id="check_password" modelAttribute="memberVO">
			<div>
				<form:label path="mem_password">비밀번호</form:label>
				<form:password path="mem_password" class="w-100 form-control"/>
				<form:errors element="span"/>
			</div>
			<div id="quit-check">
				<span id="password_area"></span>
			</div>
			<button class="btn mem-btn-green" id="quit_btn" style="margin-top: 10px;">회원 탈퇴</button>
			<div class="wrap" id="passwordModal" style="display: none">
				<div class="modal_box">
					<p>
						<img src="${pageContext.request.contextPath}/images/logo_mini.png" >
						정말로 탈퇴하시겠습니까?
					</p>
					<div>
						<form:button class="btn mem-btn-green">회원 탈퇴</form:button>
						<button class="btn mem-btn" id="cancel-btn">취소하기</button>
					</div>
				</div>
				<div class="modal_bg"></div>
			</div>
		</form:form>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/checkPassword.js"></script>