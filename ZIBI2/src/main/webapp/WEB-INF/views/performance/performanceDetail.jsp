<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid event py-6">
	<div class="container">
	
	
		<div style="margin:20px 0;">
			<span style="font-size:32pt;">${performance.performance_title}</span>
		</div>
		
		<div class="div_inline" style="margin:30px 0;">
			<img class="img-fluid rounded" style="width:400px;" src="${pageContext.request.contextPath}/upload/${performance.performance_poster}" alt="">
		</div>
		<div class="div_inline" style="margin-left:30px;">
			<span class="detailLabel">개봉일 </span>
			<span class="detailContent"> ${performance.performance_start_date}</span><br>
			<span class="detailLabel">관람연령등급 </span>
			<span class="detailContent">
			<c:if test="${performance.performance_age == 0}">
				 전체 관람가
				<img class="ratingAge" src="${pageContext.request.contextPath}/images/hyun/rating1.png">
			</c:if>
			<c:if test="${performance.performance_age == 12}">
				 12세 이상 관람
				<img class="ratingAge" src="${pageContext.request.contextPath}/images/hyun/rating2.png">
			</c:if>
			<c:if test="${performance.performance_age == 15}">
				 15세 이상 관람
				<img class="ratingAge" src="${pageContext.request.contextPath}/images/hyun/rating3.png">
			</c:if>
			<c:if test="${performance.performance_age == 19}">
				 청소년 관람 불가
				<img class="ratingAge" src="${pageContext.request.contextPath}/images/hyun/rating4.png">
			</c:if>
			</span>
		</div>
		<div>
			${performance.performance_content}
		</div>
		
		
		
		
	</div>
</div>