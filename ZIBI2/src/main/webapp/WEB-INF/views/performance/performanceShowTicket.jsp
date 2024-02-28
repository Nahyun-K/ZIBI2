<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<div class="container page-width">
		<!--  -->
		<h2>티켓</h2><br><br>
		<div class="row">
			<div class="col">
				<input type="button" class="btn mem-btn-green w-100" value="예매 내역" onclick="" id="">
			</div>
		</div>
		<!--  -->
		
		
		
		<!--  -->
		<div class="ticketPage">
			<div>
			<c:forEach var="total" items="${total}" varStatus="status">
				<div class="showTicket">
					<img class="showTicketImg" src="https://image.tmdb.org/t/p/w500/${total.performanceVO.performance_poster}" alt="">
				</div>
				
					<div class="showTicket">
						<div class="ticketBody">
							${total.performanceVO.performance_title}
						</div>
						<div class="ticketInfo">
							<span class="ticketLabel">상영관</span>
							<span class="ticketContent">
								${total.cinemaVO.cinema_location1} ${total.cinemaVO.cinema_location2} ${total.cinemaVO.cinema_theater}
							</span>
						</div>
						<div class="ticketInfo">
							<span class="ticketLabel">상영일</span>
							<span class="ticketContent">
								${total.ticketingVO.ticketing_date} ${total.ticketingVO.ticketing_start_time}
							</span>					
						</div>
						<div class="ticketInfo">
							<span class="ticketLabel">결제</span>
							<span class="ticketContent">
								${total.paymentVO.payment_type} ${total.paymentVO.payment_price} ${total.paymentVO.payment_state} ${total.paymentVO.payment_date}
							</span>
						</div>
						<div class="ticketInfo">
							<span class="ticketLabel">좌석</span>
							<span class="ticketContent">
							<%-- <c:forEach var="all" items="${all}" varStatus="status">
							<c:if test="${total.paymentVO.payment_uid==all.paymentVO.payment_uid}">
								${all.choiceVO.choice_row+1}행 ${all.choiceVO.choice_col+1}열  
							</c:if>
							</c:forEach> --%>
							</span>
						</div>
					</div>
	
			</c:forEach>
			
			<br><br><br><br>
			<div>
				<input type="button" class="mem-btn-green mem-btn py-2 px-4 btn-position d-none d-xl-inline-block rounded-pill" value="예매내역 더보기" onclick='location.href="${pageContext.request.contextPath}/performance/history"'>
			</div>
			<br><br>
			</div>
		</div>
		
		<!--  -->


	</div>
	
	
		
		
