<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="wrap" id="emailAuthModal" style="display: none">
	<div class="modal_box">
		<p>
			<img src="${pageContext.request.contextPath}/images/logo_mini.png">
			입력하신 이메일로 전송된 인증코드 여섯자리를 입력해주세요
		</p>
		<input type="number" placeholder="인증코드 여섯자리 입력" id="inputEmail">
		<p id="emailAuthError"></p>
		<div>
			<button class="btn mem-btn-green" id="email_check_btn">인증코드 확인</button>
			<button class="btn mem-btn" onclick="javascript:closeModalAction()">닫기</button>
		</div>
	</div>
	<div class="modal_bg"></div>
</div>
<div class="container form-width">
	<div class="member-form">					
		<form:form action="register" id="register_member" modelAttribute="memberVO">
			<div class="row" style="height: 300px;">
				<div class="col-6">
					<form:label path="mem_email">이메일</form:label>
					<form:input path="mem_email" class="form-control" placeholder="test@test.com" autocomplete="off"/>
					<input type="button" class="btn mem-btn" value="인증" id="email_check">
					<img id="spiner" src="${pageContext.request.contextPath}/images/na/spinner.gif" width="35px" style="display:none;">
					<span id="email_area"></span>
					<form:errors path="mem_email"/>
				</div>
				<div class="col-6">
					<form:label path="mem_nickname">닉네임</form:label>
					<form:input path="mem_nickname" class="form-control" placeholder="한글만 가능" autocomplete="off"/>
					<input type="button" class="btn mem-btn" value="중복체크" id="nickname_check">
					<span id="nickname_area"></span>
					<form:errors path="mem_nickname"/>
				</div>
				<div class="col-6">
					<form:label path="mem_password">비밀번호</form:label>
					<form:password path="mem_password" class="w-100 form-control" placeholder="영문 대소문자, 숫자 4자리에서 12자리 가능"/>
					<span id="password_area"></span>
				</div>
				<div class="col-6">
					<label for="c_password">비밀번호 확인</label>
					<input type="password" id="c_password" class="w-100 form-control" placeholder="비밀번호와 동일하게 입력" >
					<span id="cpassword_area"></span>
				</div>
			</div>
			<div class="row">
				<form:button class="btn mem-btn-green">회원가입하기</form:button>
			</div>
		</form:form>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/mypageZipcode.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/register.js"></script>