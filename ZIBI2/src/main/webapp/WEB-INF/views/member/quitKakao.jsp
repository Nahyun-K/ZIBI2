<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="wrap" id="passwordModal" style="display: none">
	<div class="modal_box">
		<p>
			<img src="${pageContext.request.contextPath}/images/logo_mini.png" >
			정말로 탈퇴하시겠습니까?
		</p>
		<div>
			<input type="button" class="btn mem-btn-green" value="회원 탈퇴" onclick="javascript:kakaoQuit()">
			<button class="btn mem-btn" id="cancel-btn">취소하기</button>
		</div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/quitKakao.js"></script><!-- 카카오 탈퇴 -->