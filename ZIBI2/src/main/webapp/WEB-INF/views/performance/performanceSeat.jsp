<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hyun/choose.seat.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- ticketing_num 저장 -->
<c:forEach var="ticketing" items="${pickTicketing}" varStatus="status">
	<div id="ticketing-num" style="display:none;">${ticketing.ticketing_num}</div>
</c:forEach>
<!-- ticketing_num -->


<!-- 영화 정보 / 인원 선택 Start -->
<div class="container-fluid contact py-6">
	<div class="container">
		<h3>영화 정보</h3>
		<h5 style="color:#32a77b;"> ※지비 회원은 할인가로 만나볼 수 있습니다</h5>
		<div class="seat-movieInfo-sort">
			<c:forEach var="performance" items="${pickPerformance}" varStatus="status">
			<div class="div_inline">
					<img class="poster" src="https://image.tmdb.org/t/p/w500/${performance.performance_poster}" alt="">
				</div>
			</c:forEach>
		
			<div class="div_inline">
				<c:forEach var="cinema" items="${pickCinema}" varStatus="status">
					<div class="movieInfo-sort" style="margin-top:30px;">${cinema.cinema_location1} ${cinema.cinema_location2} ${cinema.cinema_theater}</div>
				</c:forEach>
				<c:forEach var="ticketing" items="${pickTicketing}" varStatus="status">
					<div class="movieInfo-sort">예매 정보: ${ticketing.ticketing_date} ${ticketing.ticketing_start_time}</div>
				</c:forEach>
				<c:forEach var="performance" items="${pickPerformance}" varStatus="status">
					<div class="movieInfo-sort">${performance.performance_title}</div>
					<div class="movieInfo-sort">
					<%-- <c:if test="${performance.performance_age == 0}">
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
					</c:if> --%>
					</div>
				</c:forEach>
			</div>
			
			
		</div>
		
		<br>
		<h3>인원 선택</h3>
		<div class="select-people">
			<div class="select-people-inner">
				<span class="people people-category">일반&nbsp;&nbsp;&nbsp;</span>
				<span class="people adult-minus point">-</span>
				<span class="people adult-num">0</span>
				<span class="people adult-plus point">+</span>
			</div>
			<div class="select-people-inner">
				<span class="people people-category">청소년</span>
				<span class="people teenage-minus point">-</span>
				<span class="people teenage-num">0</span>
				<span class="people teenage-plus point">+</span>
			</div>
			<div class="select-people-inner">
				<span class="people people-category">우대&nbsp;&nbsp;&nbsp;</span>
				<span class="people treatement-minus point">-</span>
				<span class="people treatement-num">0</span>
				<span class="people treatement-plus point">+</span>
			</div>
		</div>
	</div>
</div>
<br>
<!-- 영화 정보/ 인원 선택 End -->


<!-- 좌석 선택 Start -->
<div class="container-fluid contact py-6 wow" data-wow-delay="0.1s">
	<div class="container">
		<h2>좌석 선택</h2>
		<div id="seat">
			<!-- ajax 좌석 그리기 -->
			
		</div>
	</div>
</div>
<!-- 좌석 선택 End -->


<br><br><br>


<!-- 총 금액 Start -->
<div class="container-fluid contact py-6 wow" data-wow-delay="0.1s">
	<div class="container">
	
		<h3>결제 정보</h3>

		<div style="margin: 40px;">
		<c:forEach var="cinema" items="${pickCinema}" varStatus="status">
			<div class="people">일반 : <fmt:formatNumber value="${cinema.cinema_adult}" pattern="#,###" /> <span class="adult_money"></span></div>
			<div class="people">청소년 : <fmt:formatNumber value="${cinema.cinema_teenage}" pattern="#,###" /> <span class="teenage_money"></span></div>
			<div class="people">우대 : <fmt:formatNumber value="${cinema.cinema_treatment}" pattern="#,###" /> <span class="treatement_money"></span></div>
		</c:forEach>
		<div><span class="total-money"></span></div>
		</div>

	</div>
</div>
<!-- 총 금액 End -->
<br>
<div class="container-fluid contact py-6 wow" data-wow-delay="0.1s">
	<div class="container">
		<!-- ----------------------------<<ChoiceVO>>------------------------------------ -->
		<form action="submitSeat" method="get">
			<!-- mem_num -->
			<input type="hidden" id="cinema_num" name="cinema_num" value="${tmpCinema.cinema_num}"/>
			<!-- ticketing_num -->
			<input type="hidden" id="ticketing_num" name="ticketing_num" value="${tmpTicket.ticketing_num}"/>
			<!-- 선택한 좌석 정보 -->
			<input type="hidden" id="seat_info" name="seat_info" value=""/>
			<!-- 일반 명 수 -->
			<input type="hidden" id="adult_money" name="adult_money" value=""/>
			<!-- 청소년 명 수 -->
			<input type="hidden" id="teenage_money" name="teenage_money" value=""/>	
			<!-- 우대 명 수 -->
			<input type="hidden" id="treatement_money" name="treatement_money" value=""/>
			
			<input type="submit" style="float:right;" class="mem-btn-green mem-btn py-2 px-4 d-none d-xl-inline-block rounded-pill" value="결제하기">
		</form>
		<!-- ----------------------------<<ChoiceVO>>------------------------------------ -->
	</div>
</div>
<br><br><br>







