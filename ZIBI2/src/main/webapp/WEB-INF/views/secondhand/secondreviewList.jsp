<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!-- 거래후기 -->
<div class="container-fluid py-6">
	<div class="container">
		<div class="text-center wow" data-wow-delay="0.1s">
			<h4 class="sc_reviewtitle mb-5">👍 ${seller_nickname}님이 받은 매너 평가</h4>
		</div>
		
		<div class="owl-carousel testimonial-carousel testimonial-carousel-2 wow" data-wow-delay="0.3s">
			
			<c:forEach var="second" items="${list}">
			<div class="testimonial-item rounded review-bg">
				<div class="d-flex mb-3">
					<!-- 프로필 사진 -->
					<img class="flex-shrink-0 img-fluid rounded-circle"
						src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${second.reviewer_num}"
						style="width: 90px;" alt="">
					<div class="position-absolute" style="top: 15px; right: 20px;">
						<img src="${pageContext.request.contextPath}/images/jiwon/sc_quota.png" style="width:30px;height:30px;">
					</div>
					<div class="ps-3 my-auto">
						<!-- 후기 작성자 닉네임 -->
						<h4 class="mb-0">${second.reviewer_nickname}</h4>
						<span class="m-0">구매자</span>
						<span>${second.sc_rev_regdate}</span>
					</div>
				</div>
				<!-- 별점 -->
				<div class="testimonial-content">
					<c:if test="${second.sc_rev_star==5}">
						<div class="rate2 d-flex">
							<span style="width: 100%"></span>
						</div>
					</c:if>
					<c:if test="${second.sc_rev_star==4.5}">
						<div class="rate2 d-flex">
							<span style="width: 92%"></span>
						</div>
					</c:if>
					<c:if test="${second.sc_rev_star==4}">
						<div class="rate2 d-flex">
							<span style="width: 81%"></span>
						</div>
					</c:if>
					<c:if test="${second.sc_rev_star==3.5}">
						<div class="rate2 d-flex">
							<span style="width: 71%"></span>
						</div>
					</c:if>
					<c:if test="${second.sc_rev_star==3}">
						<div class="rate2 d-flex">
							<span style="width: 60%"></span>
						</div>
					</c:if>
					<c:if test="${second.sc_rev_star==2.5}">
						<div class="rate2 d-flex">
							<span style="width: 50%"></span>
						</div>
					</c:if>
					<c:if test="${second.sc_rev_star==2}">
						<div class="rate2 d-flex">
							<span style="width: 39%"></span>
						</div>
					</c:if>
					<c:if test="${second.sc_rev_star==1.5}">
						<div class="rate2 d-flex">
							<span style="width: 30%;"></span>
						</div>
					</c:if>
					<c:if test="${second.sc_rev_star==1}">
						<div class="rate2 d-flex">
							<span style="width: 20%"></span>
						</div>
					</c:if>
					<c:if test="${second.sc_rev_star==0.5}">
						<div class="rate2 d-flex">
							<span style="width: 9%"></span>
						</div>
					</c:if>
					<p class="fs-5 m-0 pt-3">${second.sc_rev_content}</p>
				</div>
			</div>
			</c:forEach>
		</div>
	</div>
</div>
