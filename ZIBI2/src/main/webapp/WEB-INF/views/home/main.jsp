<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 메인 바디 시작 -->
<!-- 캐러셀 시작 -->
<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
	<div class="carousel-indicators">
		<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
		<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 0"></button>
	</div>
	<div class="carousel-inner">
		<div class="carousel-item active " style="background: #f4faff;">
			<div class="cmain-content text-end">
				<h5 style="font-weight: 600;">낯설기만 한 ‘방’이 포근한 ‘집’이 되는 기적을 만드는</h5>
				<h1>ZIBI</h1>
				<p>1인 가구에게 필요한 정보를 한 데 모은 플랫폼🔥</p>
				<small id="img_site">Image by jcomp on Freepik</small>
			</div>
			<div class="cmain-photo">
				<img src="${pageContext.request.contextPath}/images/na/main-photo2.jpg" height="500">
			</div>
		</div>
		<div class="carousel-item" style="background: #fafafa;">
			<div class="cmovie-photo">
				<img src="${pageContext.request.contextPath}/upload/${perf.performance_poster}" style="height: 500px !important;">
			</div>
			<div class="cmovie-content">
				<small>매일 만나는 최신 영화 혜택 📽️</small>
				<h1>
					<a href="${pageContext.request.contextPath}/performance/detail?performance_num=${perf.performance_num}">${perf.performance_title}</a>
				</h1>
				<small>${perf.performance_start_date}~</small>
			</div>
		</div>
	</div>
	<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
		<span class="carousel-control-prev-icon" aria-hidden="true"></span>
		<span class="visually-hidden">Previous</span>
	</button>
	<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
		<span class="carousel-control-next-icon" aria-hidden="true"></span>
		<span class="visually-hidden">Next</span>
	</button>
</div>
<!-- 캐러셀 끝 -->
<div class="container">
	<!-- 메뉴 설명 시작 -->
	<div class="main-content">
		<h5 style="margin-bottom: 20px;">ZIBI의 다양한 메뉴를 만나보세요</h5>
		<div class="owl-carousel">
			<div class="bg-light rounded service-item" onclick="location.href='${pageContext.request.contextPath}/book/list'">
				<div class="service-content d-flex justify-content-center p-4">
					<div class="service-content-icon text-center">
						<h5 class="mb-3">
							<a href="${pageContext.request.contextPath}/book/list">👥 모임 참여</a>
						</h5>
						<p class="mb-1">무료한 하루, ZIBI 소모임에서 지비러들과 만나요!</p>
					</div>
				</div>
			</div>
			<div class="bg-light rounded service-item" onclick="location.href='${pageContext.request.contextPath}/performance/list'">
				<div class="service-content d-flex justify-content-center p-4">
					<div class="service-content-icon text-center">
						<h5 class="mb-3">
							<a href="${pageContext.request.contextPath}/performance/list">🎬 영화 감상</a>
						</h5>
						<p class="mb-1">지비러를 위한 영화를<br>특가로 감상하세요!</p>
					</div>
				</div>
			</div>
			<div class="bg-light rounded service-item" onclick="location.href='${pageContext.request.contextPath}/secondhand/list'">
				<div class="service-content d-flex justify-content-center p-4">
					<div class="service-content-icon text-center">
						<h5 class="mb-3">
							<a href="${pageContext.request.contextPath}/secondhand/list">🤝 중고거래</a>
						</h5>
						<p class="mb-1">더이상 사용하지 않는<br>물품을 거래하세요!</p>
					</div>
				</div>
			</div>
			<div class="bg-light rounded service-item" onclick="location.href='${pageContext.request.contextPath}/helper/list'">
				<div class="service-content d-flex  justify-content-center p-4">
					<div class="service-content-icon text-center">
						<h5 class="mb-3">
							<a href="${pageContext.request.contextPath}/helper/list">🙋 재능 기부</a>
						</h5>
						<p class="mb-1">나의 사소한 재능을<br>기부하세요!</p>
					</div>
				</div>
			</div>
			<div class="bg-light rounded service-item" onclick="location.href='${pageContext.request.contextPath}/policy/main'">
				<div class="service-content d-flex justify-content-center p-4">
					<div class="service-content-icon text-center">
						<h5 class="mb-3">
							<a href="${pageContext.request.contextPath}/policy/main">ℹ️ 1인 가구 정보</a>
						</h5>
						<p class="mb-1">1인 가구 정보를<br>열람하세요!</p>
					</div>
				</div>
			</div>
			<div class="bg-light rounded service-item" onclick="location.href='${pageContext.request.contextPath}/community/list'">
				<div class="service-content d-flex justify-content-center p-4">
					<div class="service-content-icon text-center">
						<h5 class="mb-3">
							<a href="${pageContext.request.contextPath}/community/list">✍️ 커뮤니티</a>
						</h5>
						<p class="mb-1">지비러들끼리 간편하게<br>소통하세요!</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 메뉴 설명 끝 -->
	<div class="main-content">
		<div class="row main-hot">
			<h5 style="margin-bottom: 20px;">ZIBI의 명단이 궁금하신가요?</h5>
			<div class="col-4">
				<div class="photo-area">
					<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${member_content.mem_num}">
						<img class="profile-img" src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${member_content.mem_num}">
					</a>
				</div>
				<div>
					<h6>가장 말 많은 지비러 ‍😁</h6>
					<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${member_content.mem_num}">${member_content.mem_nickname}</a>님이 작성한 커뮤니티 글 ${member_content.count}건
				</div>
			</div>
			<div class="col-4 ">
				<div class="photo-area">
					<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${member_follower.mem_num}" class="my-auto">
						<img class="profile-img" src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${member_follower.mem_num}">
					</a>
				</div>
				<div>
					<h6>가장 인기 많은 지비러 😎</h6>
					<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${member_follower.mem_num}" class="my-auto">${member_follower.mem_nickname}</a>님을 팔로우한 지비러 ${member_follower.count}명
				</div>
			</div>
			<div class="col-4">
				<div class="photo-area">
					<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${member_book.mem_num}" class="my-auto">
						<img class="profile-img" src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${member_book.mem_num}">
					</a>
				</div>
				<div>
					<h6>가장 사람을 좋아하는 지비러 🥰</h6>
					<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${member_book.mem_num}" class="my-auto">${member_book.mem_nickname}</a>님이 참여한 모임 ${member_book.count}건
				</div>
			</div>
			<div class="col-4">
				<div class="photo-area">
					<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${member_movie.mem_num}" class="my-auto">
						<img class="profile-img" src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${member_movie.mem_num}">
					</a>
				</div>
				<div>
					<h6>가장 문화 생활을 즐기는 지비러 🧐</h6>
					<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${member_movie.mem_num}" class="my-auto">${member_movie.mem_nickname}</a>님이 예매한 영화 ${member_movie.count}건
				</div>
			</div>
			<div class="col-4">
				<div class="photo-area">
					<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${member_helper.mem_num}" class="my-auto">
						<img class="profile-img" src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${member_helper.mem_num}">
					</a>
				</div>
				<div>
					<h6>가장 천사같은 지비러 🤗</h6>
					<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${member_helper.mem_num}" class="my-auto">${member_helper.mem_nickname}</a>님이 헬프유한 재능 기부 ${member_helper.count}건
				</div>
			</div>
			<div class="col-4">
				<div class="photo-area">
					<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${member_second.mem_num}" class="my-auto">
						<img class="profile-img" src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${member_second.mem_num}">
					</a>
				</div>
				<div>
					<h6>가장 알뜰한 지비러 🤑</h6>
					<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${member_second.mem_num}" class="my-auto">${member_second.mem_nickname}</a>님이 중고 거래한 횟수 ${member_second.count}건
				</div>
			</div>
		</div>
	</div>	
	<!-- 최신글 시작 -->
	<div class="main-content">
		<div class="row">
			<h5>최신글을 구경하세요</h5>
			<div class="col-6">
				<h6>🤝 중고 거래</h6>
				<div class="container-fluid team py-6" >
					<div class="row g-1">
						<c:forEach var="content" items="${list_second}">
							<div class="col-lg-4 col-md-2">
								<div class="team-item rounded">
									<div class="team-img">
										<c:if test="${!empty content.photo}">
											<img class="img-fluid rounded" src="${pageContext.request.contextPath}/upload/${content.photo}" >
										</c:if>
										<c:if test="${empty content.photo}">
											<img class="img-fluid rounded" src="${pageContext.request.contextPath}/images/na/no-image.png">
										</c:if>
									</div>
									<div class="team-content text-center py-1 rounded-bottom text-center">
										<h6 class="text-primary">
											<a href="${pageContext.request.contextPath}/secondhand/detail?sc_num=${content.num}" class="my-auto">
												${content.title}
											</a>
										</h6>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="col-6">
				<h6>🙋 재능 기부</h6>
				<div class="container-fluid team py-6">
					<div class="row g-1">
						<c:forEach var="content" items="${list_helper}">
							<div class="col-lg-4 col-md-2">
								<div class="team-item rounded">
									<div class="team-img">
										<c:if test="${!empty content.photo}">
											<img class="img-fluid rounded" src="${pageContext.request.contextPath}/upload/${content.photo}" >
										</c:if>
										<c:if test="${empty content.photo}">
											<img class="img-fluid rounded" src="${pageContext.request.contextPath}/images/na/no-image.png">
										</c:if>
									</div>
									<div class="team-content text-center py-1 rounded-bottom text-center">
										<h6 class="text-primary">
											<a href="${pageContext.request.contextPath}/helper/detail?helper_num=${content.num}" class="my-auto text-center">
												${content.title}
											</a>
										</h6>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-6">
				<h6>✍️ 커뮤니티</h6>
				<div class="container-fluid team py-6">
					<div class="row g-1">
						<c:forEach var="content" items="${list_community}">
							<div class="col-lg-4 col-md-2">
								<div class="team-item rounded">
									<div class="team-img">
										<c:if test="${!empty content.photo}">
											<img class="img-fluid rounded" src="${pageContext.request.contextPath}/upload/${content.photo}" >
										</c:if>
										<c:if test="${empty content.photo}">
											<img class="img-fluid rounded" src="${pageContext.request.contextPath}/images/na/no-image.png">
										</c:if>
									</div>
									<div class="team-content text-center py-1 rounded-bottom text-center">
										<h6 class="text-primary">
											<a href="${pageContext.request.contextPath}/community/detail?community_num=${content.num}" class="my-auto text-center">
												${content.title}
											</a>
										</h6>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="col-6">
				<h6>👥 소모임 예약</h6>
				<div class="container-fluid team py-6">
					<div class="row g-1">
						<c:forEach var="content" items="${list_book}">
							<div class="col-lg-4 col-md-2">
								<div class="team-item rounded">
									<div class="team-img">
										<c:if test="${!empty content.photo}">
											<img class="img-fluid rounded" src="${pageContext.request.contextPath}/upload/${content.photo}" >
										</c:if>
										<c:if test="${empty content.photo}">
											<img class="img-fluid rounded" src="${pageContext.request.contextPath}/images/na/no-image.png">
										</c:if>
									</div>
									<div class="team-content text-center py-1 rounded-bottom text-center">
										<h6 class="text-primary">
											<a href="${pageContext.request.contextPath}/book/detail?book_num=${content.num}" class="my-auto text-center">
												${content.title}
											</a>
										</h6>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="row">

		</div>
	</div>
	<!-- 최신글 끝 -->

</div>
<c:if test="${!empty message}">
	<div class="wrap" id="mainModal">
		<div class="modal_box">
			<p>
				<img src="${pageContext.request.contextPath}/images/logo_mini.png">
				ZIBI와 함께 해주셔서 감사합니다<br>
				<c:if test="${message=='quitNaver'}">(네이버 연동은 사용자가 직접 해지하셔야 합니다)</c:if>
			</p>
			<div>
				<button class="btn mem-btn" id="cancel-btn" onclick="$('#mainModal').hide();">홈으로</button>
				<c:if test="${message=='quitNaver'}"><button class="btn mem-btn-green" onclick="location.href='https://nid.naver.com/'">네이버 연동 해지</button></c:if>
			</div>
		</div>
	</div>
</c:if>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/sample/lib/owlcarousel/owl.carousel.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/owlCarousel.js"></script>
<!-- 메인 바디 끝 -->