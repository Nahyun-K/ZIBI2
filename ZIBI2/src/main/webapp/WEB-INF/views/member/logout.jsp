<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<input type="hidden" id="kakao_apikey" value="${kakao_apikey}"/>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/kakao.js"></script>
<script>
	logoutKakao();
	location.replace('/main/home');	
</script>