<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${!empty user && empty user.mem_name && user.mem_auth!=9}">
	<div id="userInfoModal">
		<span>
			<img src="${pageContext.request.contextPath}/images/logo_tab.png" width="20px">
			<a href="${pageContext.request.contextPath}/member/mypageUpdate">추가 정보를 입력하고 더 편하게 ZIBI를 즐기세요! </a>
			<b onclick="$('#userInfoModal').hide();"><small> [닫기]</small></b>
		</span>
	</div>
	
</c:if>