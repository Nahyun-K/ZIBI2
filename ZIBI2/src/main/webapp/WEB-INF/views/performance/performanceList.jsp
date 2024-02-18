<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 관리자 button -->
<!-- <input type="button" value="영화 form" onclick="location.href='write'">/performance/formPage
<input type="button" value="상영관 form" onclick="location.href='writeCinema'">
<input type="button" value="날짜 form" onclick="location.href='writePerformanceDate'"> -->

<%-- <form action="performanceList" method="get">
	<input type="search" name="keyword" id="keyword" value="${param.keyword}">
	<input type="submit" value="검색">
	<input type="button" value="목록" onclick="location.href='performanceList'">
</form> --%> 

	<!-- -----------------------------부트스트랩------------------------------- -->
	<div class="container-fluid event py-6">
		<div class="container">
			<!-- 빠른 예매 버튼 시작 -->
			<input type="button" value="빠른 예매하기" onclick="location.href='ticketing'" class="btn mem-btn-green py-2 px-4 d-none d-xl-inline-block rounded-pill" style="width:180px;height:60px;">
			<!-- 빠른 예매 버튼 끝 -->
			
			<div class="text-center wow" data-wow-delay="0.1s">
				<!-- <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Latest Events</small> -->
				<h5 class="mb-5" style="color:#32a77b;"> ※지비 회원은 할인가로 만나볼 수 있습니다 <span style="font-size:12pt;color:#32a77b;"> (1인 2000원 할인)</span></h5>
				<h1 class="display-5 mb-5">현재 상영작</h1>
			</div>
			<!-- <div class="tab-class text-center">
			=================== 메뉴 시작 ===================
				<ul class="nav nav-pills d-inline-flex justify-content-center mb-5 wow" data-wow-delay="0.1s">
					<li class	="nav-item p-2">
						<a class="d-flex mx-2 py-2 border border-primary bg-light rounded-pill active" data-bs-toggle="pill" href="#tab-1">
							<span class="text-dark" style="width: 150px;">All Events</span>
						</a>
					</li>
					<li class="nav-item p-2">
						<a class="d-flex py-2 mx-2 border border-primary bg-light rounded-pill" data-bs-toggle="pill" href="#tab-2">
							<span class="text-dark" style="width: 150px;">Wedding</span>
						</a>
					</li>
					<li class="nav-item p-2">
						<a class="d-flex mx-2 py-2 border border-primary bg-light rounded-pill" data-bs-toggle="pill" href="#tab-3">
							<span class="text-dark" style="width: 150px;">Corporate</span>
						</a>
					</li>
					<li class="nav-item p-2">
						<a class="d-flex mx-2 py-2 border border-primary bg-light rounded-pill" data-bs-toggle="pill" href="#tab-4">
							<span class="text-dark" style="width: 150px;">Cocktail</span>
						</a>
					</li>
					<li class="nav-item p-2">
						<a class="d-flex mx-2 py-2 border border-primary bg-light rounded-pill" data-bs-toggle="pill" href="#tab-5">
							<span class="text-dark" style="width: 150px;">Buffet</span>
						</a>
					</li>
				</ul>
			=================== 메뉴 끝 ===================
			</div> -->
		</div>
	</div>
	
	

		<!-- Events Start -->
        <div class="container-fluid event py-6">
            <div class="container">
            	<!-- ------------------------------ -->
                    <!-- =================== 영화 -- 시작 =================== -->
                    <div class="tab-content">
                        <div id="tab-1" class="tab-pane fade show p-0 active">
                            <div class="row g-4">
                                <div class="col-lg-12">
                                    <div class="row g-4">
                                    <!-- ====================================== 영화 반복문 돌리기 시작 ====================================== -->
                                    	<c:forEach var="performance" items="${list}" varStatus="status">
									
	                                	<!-- ============== <<영화 시작>> ============== -->
                                        <div class="col-md-6 col-lg-3 wow bounceInUp" data-wow-delay="0.3s">
                                            <div class="event-img position-relative">
	                                            <!-- tmdb api 이미지 path :  https://developer.themoviedb.org/docs/image-basics -->
                                                <img class="img-fluid rounded w-100" src="https://image.tmdb.org/t/p/w500/${performance.performance_poster}" alt="">
                                                <div class="event-overlay d-flex flex-column p-4 mainMouseover">
                                                    <!-- <h4 class="me-auto">Wedding</h4> -->
                                                    <%-- <input type="button" value="예매하기" onclick="location.href='ticketing?performance_num=${performance.performance_num}'"> --%>
                                                    <input type="button" value="영화 상세" class="mainMouseoverBtn" onclick="location.href='detail?performance_num=${performance.performance_num}'">
                                                </div>
                                            </div>
                                            <div>${performance.performance_title}</div>
                                            <div>개봉일 ${performance.performance_start_date}</div>
                                        </div>
                                        <!-- ============== <<영화 끝>> ============== -->
                                        
                                        </c:forEach>
                                    <!-- ====================================== 영화 반복문 돌리기 끝 ====================================== -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- =================== 영화 -- 끝 =================== -->
                </div>
            </div>
        <!-- Events End -->
        
        
        
        
        
        
