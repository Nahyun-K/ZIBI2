<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="justify-content-center">
	<div class="mypage-follow" style="width: 60% !important; margin: 0 auto">
		<c:if test="${count==0}">
			<div class="no-data">
				팔로우한 회원이 없습니다 😟
			</div>
		</c:if>
		<c:if test="${count>0}">
			<c:forEach var="follow" items="${list}">
				<div class="row">
					<div class="col-5">
						<c:if test="${!empty follow.photo}">
							<img src="${pageContext.request.contextPath}/upload/${follow.photo}">
						</c:if>
						<c:if test="${empty follow.photo}">
							<img src="${pageContext.request.contextPath}/images/na/no-image.png">
						</c:if>
					</div>
					<div class="col-7">
						<span>
							<c:if test="${follow.category==1}">👥 소모임 예약</c:if>
							<c:if test="${follow.category==2}">🙋 재능 기부</c:if>
							<c:if test="${follow.category==3}">✍️ 커뮤니티</c:if>
							<c:if test="${follow.category==4}">🤝 중고 거래</c:if>
							(${follow.reg_date})
						</span>
						<div>
							<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${follow.mem_num}">${follow.mem_nickname}</a>
						</div>
						<h5>
							<c:if test="${follow.category==1}">
								<a href="${pageContext.request.contextPath}/book/detail?book_num=${follow.num}">${follow.title}</a>
							</c:if>
							<c:if test="${follow.category==2}">
								<a href="${pageContext.request.contextPath}/helper/detail?helper_num=${follow.num}">${follow.title}</a>
							</c:if>
							<c:if test="${follow.category==3}">
								<a href="${pageContext.request.contextPath}/community/detail?community_num=${follow.num}">${follow.title}</a>
							</c:if>
							<c:if test="${follow.category==4}">
								<a href="${pageContext.request.contextPath}/secondhand/detail?sc_num=${follow.num}">${follow.title}</a>
							</c:if>
						</h5>
					</div>
				</div>
			</c:forEach>
			<div class="text-center">${page}</div>
		</c:if>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/mypageProfileImage.js"></script><!-- 프로필 사진 변경 -->
<script>
	$('#follow_btn').toggleClass("mem-btn");
	$('#follow_btn').toggleClass("mem-btn-green");
</script>