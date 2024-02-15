<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="openProfile">
	<div class="row justify-content-center">
		<div class="col-5 user">
			<img src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${mem_num}">
		</div>
		<div class="col-2 align-self-center">
			<h5>${nickname}</h5>
			<input type="button" value="팔로우" class="btn mem-btn" id="follow_btn">
			<p>팔로워 <span id="follower"></span>명</p>
		</div>
	</div>
	<c:if test="${count==0}">
		<div class="text-center">
			<h6 style="margin: 40px !important;">회원이 작성한 글이 없습니다 😅</h6>
		</div>
	</c:if>
	<c:if test="${count>0}">
		<div class="row row-cols-1 row-cols-md-4">
			<c:forEach var="content" items="${list}">
				<div class="col">
					<div class="card">
						<div class="card-img">
							<c:if test="${!empty content.photo}">
								<img class="card-image" src="${pageContext.request.contextPath}/upload/${content.photo}" >
							</c:if>
							<c:if test="${empty content.photo}">
								<img class="card-img-top" src="${pageContext.request.contextPath}/images/na/no-image.png">
							</c:if>
						</div>	
						<div class="card-body">
							<h5 class="card-title">
								<c:if test="${content.category==1}">
									<a href="${pageContext.request.contextPath}/book/detail?book_num=${content.num}" class="my-auto text-center">
										${content.title}
									</a>
								</c:if>
								<c:if test="${content.category==2}">
									<a href="${pageContext.request.contextPath}/helper/detail?helper_num=${content.num}" class="my-auto text-center">
										${content.title}
									</a>
								</c:if>
								<c:if test="${content.category==3}">
									<a href="${pageContext.request.contextPath}/community/detail?community_num=${content.num}" class="my-auto text-center">
										${content.title}
									</a>
								</c:if>
								<c:if test="${content.category==4}">
									<a href="${pageContext.request.contextPath}/secondhand/detail?sc_num=${content.num}" class="my-auto text-center">
										${content.title}
									</a>
								</c:if>
							</h5>
							<p class="card-text">
								<c:if test="${content.category==1}">👥 소모임 예약</c:if>
								<c:if test="${content.category==2}">🙋 재능 기부</c:if>
								<c:if test="${content.category==3}">✍️ 커뮤니티</c:if>
								<c:if test="${content.category==4}">🤝 중고 거래</c:if>
							</p>
						</div>
						<div class="card-footer">
							<small class="text-muted">${content.reg_date}</small>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="text-center">${page}</div>
	</c:if>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/follow.js"></script>