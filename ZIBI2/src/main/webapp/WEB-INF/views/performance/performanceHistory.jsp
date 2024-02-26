<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container-fluid contact py-6">
	<div class="container">
	<h2>결제 내역</h2>
		<div>
			<table class="historyTable">
				<tr>
					<th></th>
					<th>영화</th>
					<th>상영관</th>
					<th>상영일</th>
					<th>결제</th>
					<th>결제 상태</th>
					<th>결제일</th>
					<th>좌석</th>
				</tr>
				
				<c:forEach var="total" items="${total}" varStatus="status">
				<tr>
					<td>
						<img class="" style="width:150px;" src="https://image.tmdb.org/t/p/w500/${total.performanceVO.performance_poster}" alt="">
					</td>
					<td>
						<div>
						<a href="${pageContext.request.contextPath}/performance/choiceSeat?uid=${total.paymentVO.payment_uid}">${total.performanceVO.performance_title} </a>&nbsp;
						<%-- <c:if test="${total.performanceVO.performance_age == 0}">
						<img class="ratingAge" src="${pageContext.request.contextPath}/images/hyun/rating1.png">
						</c:if>
						<c:if test="${total.performanceVO.performance_age == 12}">
						<img class="ratingAge" src="${pageContext.request.contextPath}/images/hyun/rating2.png">
						</c:if>
						<c:if test="${total.performanceVO.performance_age == 15}">
						<img class="ratingAge" src="${pageContext.request.contextPath}/images/hyun/rating3.png">
						</c:if>
						<c:if test="${total.performanceVO.performance_age == 19}">
						<img class="ratingAge" src="${pageContext.request.contextPath}/images/hyun/rating4.png">
						</c:if> --%>
						
							
						</div>
					</td>
					<td>
						<div>
							${total.cinemaVO.cinema_location1} ${total.cinemaVO.cinema_location2} ${total.cinemaVO.cinema_theater}
						</div>
					</td>
					<td>${total.ticketingVO.ticketing_date} ${total.ticketingVO.ticketing_start_time}</td>
					<td>
						<c:if test="${total.paymentVO.payment_type eq 'card'}">
							카드 결제
						</c:if>
						<c:if test="${total.paymentVO.payment_type eq 'kakaopay'}">
							카카오 페이
						</c:if>
						<c:if test="${total.paymentVO.payment_type eq 'tosspay'}">
							토스 페이
						</c:if>
						<br>
						<fmt:formatNumber value="${total.paymentVO.payment_price}" pattern="#,###원" />
						
						
					</td>
					<td>
						<c:if test="${total.paymentVO.payment_state eq 1}">
							결제완료
						</c:if>
						<c:if test="${total.paymentVO.payment_state eq 2}">
							환불 진행
						</c:if>
						<c:if test="${total.paymentVO.payment_state eq 3}">
							환불 완료
						</c:if>
					</td>
					<td>
						${total.paymentVO.payment_date}
					</td>
		
					<td>
					<c:forEach var="all" items="${all}" varStatus="status">
					<c:if test="${total.paymentVO.payment_uid==all.paymentVO.payment_uid}">
						${all.choiceVO.choice_row+1}행${all.choiceVO.choice_col+1}열  &nbsp;
					</c:if>
					</c:forEach>
					</td>
		
				</tr>
				</c:forEach>
			</table>
			<div class="align-center">${page}</div>
				
		</div>
	</div>
</div>

