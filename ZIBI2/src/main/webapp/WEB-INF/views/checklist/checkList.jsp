<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<link href="${pageContext.request.contextPath}/css/yeeun.css" rel="stylesheet">   
<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
<!-- 내용 시작 -->
<form>
<div class="align-center">
<h3>목록</h3>
</div>
<HR width="50%" align="center">
<div class="check_btn">
<input type="button" value="전체" class="check-btn-green w-25" onclick="location.href='checkList.jsp'">&nbsp;<input type="button" value="내 리스트" class="check-btn w-25" onclick="location.href='checkMyList'">
</div>
<HR width="50%" align="center">
<c:if test="${count == 0}">
<div class="result-display1">표시할 게시물이 없습니다.</div></c:if>
		<c:if test="${count > 0}">
			<c:forEach var="checklist" items="${list}">
				<div class="check_result">
					<c:if test="${!empty checklist.room_filename}">
						<img
							src="${pageContext.request.contextPath}/upload/${checklist.room_filename}"
							style="width: 200px; height: 200px; margin-right:20px; "
							class="radius">
					</c:if>
				</div>
				<div class="float-left2 align-center">
				<div class="align-center">${checklist.check_date}</div>
					<div class="align-center font-size1">
						<a href="detail?check_id=${checklist.check_id}">${checklist.room_name}</a>
					</div>
					<br>
					<div class="align-center">${checklist.room_deposit}</div>
					<br>
					<div class="align-center">${checklist.room_description}</div>
				</div>
				<br>
				<HR width="50%" align="center">
			</c:forEach>
			<div class="align-center">${page}</div>
		</c:if>
	<div class="align-center">
	<input type="button" value="새로운 매물 체크 시작하기" class="btn  che-btn-green_" onclick="location.href='checkwrite'"></div>
	<br><br><br><br>
</form>
