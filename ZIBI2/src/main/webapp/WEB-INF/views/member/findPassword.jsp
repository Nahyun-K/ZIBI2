<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="wrap" id="passwordModal" style="display: none">
	<div class="modal_box">
		<p>
			<img src="${pageContext.request.contextPath}/images/logo_mini.png" >
			입력하신 이메일로 임시 비밀번호가 전송되었습니다.<br>임시 비밀번호로 로그인해주세요
		</p>
		<div>
			<button class="btn mem-btn-green" onclick="javascript:loginDirect()">로그인</button><br>
		</div>
		<p>혹시 이메일을 받지 못하셨나요? <a onclick="javascript:replaceEmailForm()">이메일을 다시 입력해주세요</a></p>
	</div>
	<div class="modal_bg"></div>
</div>
<div class="container form-width">
	<div class="member-form">					
		<form action="findPassword" id="find_email">
			<div class="row">
				<label for="mem_email">이메일</label>
				<input id="mem_email" name="mem_email" class="form-control" placeholder="사이트에 등록된 이메일 입력" autocomplete="off">
				<span id="email_area"></span>
				<div class="text-center">
					<img id="spiner" src="${pageContext.request.contextPath}/images/na/spinner.gif" style="display: none" width="35px">
					<input type="submit" class="btn mem-btn-green" value="임시 비밀번호 전송" id="email_check">
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/findPassword.js"></script>