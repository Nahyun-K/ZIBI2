<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hyun/selectLocation.js"></script>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!-- 부트스트랩 시작 원래 5.3.2버전 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<!-- 부트스트랩 끝 -->


        <!-- Blog Start -->
        <div class="container-fluid blog py-6" style="padding:0 200px"> 
                <div class="text-center wow" data-wow-delay="0.1s">
                    <h5 class="mb-5" style="color:#32a77b;"> ※지비 회원은 할인가로 만나볼 수 있습니다 <span style="font-size:12pt;color:#32a77b;"> (1인 2000원 할인)</span></h5>
                    <h1 class="display-5 mb-5">영화 선택하기</h1>
                </div>
                <div class="row gx-4 justify-content-center">
                	<!-- -------------- 첫번째 : 영화관 위치 찾기 -------------- -->
                    <div class="col-md-6 col-lg-3 wow bounceInUp" data-wow-delay="0.1s">
                        <div class="blog-item">
                            <div class="blog-content mx-4 d-flex rounded bg-light">
                                <div class="text-dark green-background rounded-start">
                                    <div class="h-100 p-3 d-flex flex-column justify-content-center text-center">
                                        <p class="fw-bold mb-0 white-color">1</p>
                                        <p class="fw-bold mb-0 white-color">Step</p>
                                    </div>
                                </div>
                                <div class="h5 lh-base my-auto h-100 p-3" id="selectCinemaTitle">
	                                영화관 선택
                                </div>
                            </div>
                            
                            <div class="overflow-hidden rounded justify-content-center" style="margin-top:5px;padding:20px;">
                            <!-- table > tbody > tr > td / > thead > tr > th -->

								<!-- 지역1 -->
                            	<table id="location1" style="float:left;">
                            	<thead>
                            		<tr>
                            			<th></th>
                            		</tr>
                            	</thead>
                            	<tbody>
	                            	<c:forEach var="cinema" items="${cinemaList}">
	                            	<tr class="searchCinema1">
										<td class="searchCinema1 searchCinemaTable">${cinema.cinema_location1}</td>
									</tr>
									</c:forEach>
									
								</tbody>
                            	</table>
                            	
                            	
                            	<!-- 지역2 -->
                            	<table id="location2" style="float:left;">
	                            	<thead>
	                            		<tr>
	                            			<th></th>
	                            		</tr>
	                            	</thead>
	                            	<tbody>
	                            		<!-- ajax 값! -->
	                            	</tbody>
                            	</table>
                            	
                            </div>
                        </div>
                    </div>
                    <!-- -------------- 첫번째 : 영화관 위치 찾기 -------------- -->
                    
                    
                	<!-- -------------- 두번째 : 영화 선택 -------------- -->
                    <div class="col-md-6 col-lg-4 wow bounceInUp" data-wow-delay="0.3s">
                        <div class="blog-item">
                            
                            <div class="blog-content mx-4 d-flex rounded bg-light">
                                <div class="text-dark green-background rounded-start">
                                    <div class="h-100 p-3 d-flex flex-column justify-content-center text-center">
                                        <p class="fw-bold mb-0 white-color">2</p>
                                        <p class="fw-bold mb-0 white-color">Step</p>
                                    </div>
                                </div>
                                <div class="h5 lh-base my-auto h-100 p-3" id="selectPerformanceTitle">
	                                영화
                                </div>
                            </div>
                            
                            <div class="overflow-hidden rounded">
                                <table id="movieList" style="float:left;">
                                <tbody id="ticketing_Ent">
                                	<!-- ajax -->
		                           	<c:forEach var="performance" items="${list}">
		                           	<tr class="ticketing-ent-row" id="${performance.performance_num}">
										<td class="ticketing-poster"><img id="ticketing-poster-img" src="https://image.tmdb.org/t/p/w500/${performance.performance_poster}" style="height:230px; margin:10px;"></td>
										<td class="ticketing-info">
											${performance.performance_title}
											<br>
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
										</td>
									</tr>
									</c:forEach>
								</tbody>
                            	</table>
                            	
                            </div>
                        </div>
                    </div>
                    <!-- -------------- 두번째 : 영화 선택 -------------- -->
                    
                    
                    
                	<!-- -------------- 세번째 : 날짜 선택 -------------- -->
                    <div class="col-md-6 col-lg-1 wow bounceInUp" data-wow-delay="0.5s">
                        <div class="blog-item">
                            
                             <div class="blog-content mx-4 d-flex rounded bg-light">
                                <div class="text-dark green-background rounded-start">
                                    <div class="h-100 p-3 d-flex flex-column justify-content-center text-center">
                                        <p class="fw-bold mb-0 white-color">3</p>
                                        <p class="fw-bold mb-0 white-color">Step</p>
                                    </div>
                                </div>
                            </div>
                            <div class="overflow-hidden rounded">
								<div id="date_list" style="height:300px;overflow:scroll;width:110px;">
									<c:forEach var="day" items="${dayList}">
										<input type="button" value="${day.ticketing_date}" style="border:1px solid #d3d3d3; padding:2px;">
									</c:forEach>
								</div>
								
                            </div>
                            
                        </div>
                    </div>
                    <!-- -------------- 세번째 : 날짜 선택 -------------- -->
                    
                    <!-- -------------- 네번째 : 최종 선택 -------------- -->
                    <div class="col-md-6 col-lg-4 wow bounceInUp" data-wow-delay="0.5s">
                        <div class="blog-item">
                            
                            <div class="blog-content mx-4 d-flex rounded bg-light">
                                <div class="text-dark green-background rounded-start">
                                    <div class="h-100 p-3 d-flex flex-column justify-content-center text-center">
                                        <p class="fw-bold mb-0 white-color">4</p>
                                        <p class="fw-bold mb-0 white-color">Step</p>
                                    </div>
                                </div>
                                <div class="h5 lh-base my-auto h-100 p-3" id="resultTitle">
	                                상영관 + 날짜 + 시간 선택
                                </div>
                            </div>
                            <!-- 최종 선택할 수 있는 영화 나옴 -->
                            <div class="overflow-hidden rounded" id="resultSelect">
								<!-- ajax -->
                            </div>
                            
                        </div>
                    </div>
                    <!-- -------------- 네번째 : 최종 선택 -------------- -->
                    
                    
                    <div>
	                    <!-- ----------------- form 시작 --------------------- -->
	                    <form action="updateTicketing" id="update_ticketing" method="post">
							<!-- 상영관 -->
							<input type="hidden" id="cinema_hidden" value=""/>
							<!-- 영화 -->
							<input type="hidden" id="performance_hidden" value=""/>
							<!-- 날짜 -->
							<input type="hidden" id="day_hidden" value="${today}"/><!-- 기본값 항상 있어야 함 -->
							<!-- 최종 선택 -->
							<input type="hidden" id="ent_hidden" value=""/>
							<!-- <input type="submit" value="예매하기"> -->
							<input type="button" class="mem-btn-green mem-btn py-2 px-4 d-none btn-position d-xl-inline-block rounded-pill" value="예매하기" onclick='submitEnt()'>
						</form>
	                    <!-- ----------------- form 끝 --------------------- -->
                    </div>
                </div>
        </div>
        <!-- Blog End -->
        
<div id="newspan"></div>
<br><br><br><br><br><br><br><br>




<!-- ----------------------- << 캐러셀 시작 >> ------------------------ -->
 <!-- 캐러셀 시작 -->
<div class="container">
	<div class="main-content" style="width:600px;">
		
		<!-- ----------------------- << 캐러셀 시작 >> ------------------------ -->
		<div class="owl-carousel">
		
		
		<c:forEach var="day" items="${dayList}">
										
									
			<div class="bg-light rounded service-item" id="select_day" data-value="${day.ticketing_date}">
				<div class="service-content d-flex justify-content-center p-4">
					<div class="service-content-icon text-center">
						<h4 class="mb-3">
							<a href="javascript:;" onclick="date();">${fn:substring(day.ticketing_date,5,7)}/${fn:substring(day.ticketing_date,8,10)}</a>
						</h4>
					</div>
				</div>
			</div>
			
			
		</c:forEach>
			
			
		</div>
		<!-- ----------------------- << 캐러셀 끝 >> ------------------------ -->
	</div>
</div>
<!-- ----------------------- << 캐러셀 끝 >> ------------------------ -->
<script src="${pageContext.request.contextPath}/sample/lib/owlcarousel/owl.carousel.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/owlCarousel.js"></script>

<script>
function submitEnt(){
	location.href="${pageContext.request.contextPath}/performance/updateTicketing?ticketing_num="+$('#ent_hidden').val();
}
</script>




