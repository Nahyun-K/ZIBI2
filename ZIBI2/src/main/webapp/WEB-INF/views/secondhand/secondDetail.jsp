<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2b2c8d108f8ba3676d57626832ac387e&libraries=services"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jiwon/second.fav.js"></script>
<script type="text/javascript">
$(function(){
	//바로 예약 버튼 클릭
	$('#sc_order').click(function(){
		$('#secondOrderModal').show();
	});
	//바로 예약 모달 창 닫기 버튼 클릭 시 hide
	$('#order_closeModalBtn').click(function () {
		$('#secondOrderModal').hide();
	});
	//글 삭제 버튼 클릭
	$('#sc_delete').click(function(){
		$('#secondDeleteModal').show();
		
	});
	//상태변경 버튼 클릭
	$('#sc_modify_status').click(function(){
		$('#secondModifyStatusModal').show();
	});
	//상태변경 모달 창 닫기 버튼 클릭 시 hide
	$('#ms_closeModalBtn').click(function () {
		$('#secondModifyStatusModal').hide();
	});
	//모달창에서 판매중 클릭시 
	$('#scForsale_btn').click(function(){
		$.ajax({
			url: 'updateForSale',
            type: 'post',
            data:{sc_num:${second.sc_num}},
            dataType: 'json',
            success: function(param) {
            	$('#secondModifyStatusModal').hide();
            	$('#status_png').empty();
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
		});
	});
	//모달창에서 예약대기 클릭시 
	$('#scWaitReserve_btn').click(function(){
		$.ajax({
			url: 'updateWaitReserve',
            type: 'post',
            data:{sc_num:${second.sc_num}},
            dataType: 'json',
            success: function(param) {
            	$('#secondModifyStatusModal').hide();
            	$('#status_png').empty();
            	output = '<img src="${pageContext.request.contextPath}/images/jiwon/sc_waitReserve.png" class="position-absolute top-50 start-50 translate-middle" style="width:300px;height:300px; z-index: 1;">';  
            	$('#status_png').append(output);
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
		});
	});
	//모달창에서 예약중 클릭시 
	$('#scReserve_btn').click(function(){
		$.ajax({
			url: 'updateReserve',
            type: 'post',
            data:{sc_num:${second.sc_num}},
            dataType: 'json',
            success: function(param) {
            	$('#secondModifyStatusModal').hide();
            	$('#status_png').empty();
            	output = '<img src="${pageContext.request.contextPath}/images/jiwon/sc_reserve1.png" class="position-absolute top-50 start-50 translate-middle" style="width:300px;height:300px; z-index: 1;">';       
            	$('#status_png').append(output);
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
		});
	});
	
	//바로예약 모달창에서 채팅없이 거래 클릭 시 - 예약대기로 변경되고, second_order 테이블에 insert
	$('#sc_orderinsert').click(function(){
		$.ajax({
			url: 'insertScOrder',
			type: 'post',
            data:{sc_num:${second.sc_num}},
            dataType: 'json',
            success: function(param) {
            	if(param.result == 'logout'){
            		alert('로그인 후 사용하세요');
            		//모달창 닫기
            		$('#secondOrderModal').hide();
            	}else if(param.result == 'success'){
            		//모달창 닫기
            		$('#secondOrderModal').hide();
            		$('#status_png').empty();
                	output = '<img src="${pageContext.request.contextPath}/images/jiwon/sc_waitReserve.png" class="position-absolute top-50 start-50 translate-middle" style="width:300px;height:300px; z-index: 1;">';  
                	$('#status_png').append(output);	
            	}else{
            		alert('바로 예약 오류 발생');
            	}
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
		});
	});
	
});
</script>
<div class="container-fluid py-6">
    <div class="container">
        <div class="row g-5 align-items-start">
        	<!-- 썸네일 -->
			<div class="col-lg-5 position-relative">
				<c:if test="${empty second.sc_filename}">
				<img src="${pageContext.request.contextPath}/images/jiwon/sc_noimg.png"
					style="width: 500px; height: 450px;" class="img-fluid rounded"
					alt="">
				</c:if>
				<c:if test="${!empty second.sc_filename}">
				<img
					src="${pageContext.request.contextPath}/upload/${second.sc_filename}"
					style="width: 500px; height: 450px;" class="img-fluid rounded"
					alt="">
				</c:if>
				
				<div id="status_png">
				<c:if test="${second.sc_sellstatus==1}">
					<img src="${pageContext.request.contextPath}/images/jiwon/sc_waitReserve.png" class="position-absolute top-50 start-50 translate-middle" style="width:300px;height:300px; z-index: 1;">
				</c:if>
				<c:if test="${second.sc_sellstatus==2}">
					<img src="${pageContext.request.contextPath}/images/jiwon/sc_reserve1.png" class="position-absolute top-50 start-50 translate-middle" style="width:300px;height:300px; z-index: 1;">
				</c:if>
				<c:if test="${second.sc_sellstatus==3}">
					<img src="${pageContext.request.contextPath}/images/jiwon/sc_sellFin.png" class="position-absolute top-50 start-50 translate-middle" style="width:300px;height:300px; z-index: 1;">
				</c:if>
				</div>
				
			</div>
			<div class="col-lg-7">
                <small class="d-inline-block fw-bold text-uppercase bg-light border rounded-pill px-4 py-1 mb-3">
                	<a>홈</a>
                	>
                	<c:if test="${second.sc_category==1}"><span>의류/액세서리</span></c:if>
                	<c:if test="${second.sc_category==2}"><span>도서/티켓/문구</span></c:if>
                	<c:if test="${second.sc_category==3}"><span>뷰티</span></c:if>
                	<c:if test="${second.sc_category==4}"><span>전자기기</span></c:if>
                	<c:if test="${second.sc_category==5}"><span>식품</span></c:if>
                	<c:if test="${second.sc_category==6}"><span>기타</span></c:if>
                </small>
                <p style="font-size:35px;">${second.sc_title}</p>
                <p class="mb-4" style="font-size:40px;"><fmt:formatNumber value="${second.sc_price}"/>원</p>
                
                <div class="dinfo-box1 row g-4 text-dark mb-5">
               			<!-- 칸 나누는 구역 -->
                	<div class="container-fluid">
				        <div class="row" style="text-align:center;">
				            <div class="box1-first col-4">
				                <p>상품 상태</p>
				                <p><c:if test="${second.sc_status == 1}">중고</c:if>
				                	<c:if test="${second.sc_status == 2}">새 상품</c:if>
				                </p>
				            </div>
				            
				            <div class="box1-second col-4">
				                <p>거래 방법</p>
				                <p><c:if test="${second.sc_way == 1}">직거래</c:if>
				                   <c:if test="${second.sc_way == 2}">택배</c:if>
				                </p>
				            </div>
				            <div class="col-4">
				                <p>거래 지역</p>
				                <p>${second.sc_address}</p>
				            </div>
				         </div>
				    </div>
                </div>
                	<!-- 사용자가 없거나, 게시글 작성자가 아닐때   -->
                <c:choose>
                <c:when test="${user.mem_num != second.mem_num}">
                <!-- 판매중인 경우 채팅 ,예약 버튼 있음 -->
                <c:if test="${second.sc_sellstatus==0}">
	                <!-- 찜 -->
	                <img id="output_fav" data-num="${second.sc_num}"
	                	src="${pageContext.request.contextPath}/images/jiwon/sc_fav.png" style="width:40px;height:40px;">
	                <span id="output_fcount"></span>
	                <input type="button" value="채팅하기" class="sc-order-btn-green w-25"
	                	onclick="location.href='${pageContext.request.contextPath}/secondchat/chatListForBuyer?sc_num=${second.sc_num}'">
	                <input type="button" value="바로 예약" id="sc_order" class="sc-order-btn w-25">
                </c:if>
                <!-- 예약 대기&예약중인 경우 채팅만 가능, 바로 예약 disabled 처리 -->
                <c:if test="${second.sc_sellstatus==1 || second.sc_sellstatus==2}">
	                <!-- 찜 -->
	                <img id="output_fav" data-num="${second.sc_num}"
	                	src="${pageContext.request.contextPath}/images/jiwon/sc_fav.png" style="width:40px;height:40px;">
	                <span id="output_fcount"></span>
	                <input type="button" value="채팅하기" class="sc-order-btn-green w-25"
	                	onclick="location.href='${pageContext.request.contextPath}/secondchat/chatListForBuyer?sc_num=${second.sc_num}'">
	                <input type="button" value="바로 예약" id="sc_order" class="sc-disabled-btn w-25" disabled>
                </c:if>
                
                <!-- 판패 완료 시 채팅, 바로예약 버튼 diabled처리 -->
               	<c:if test="${second.sc_sellstatus==3}">
	                <!-- 찜 -->
	                <img id="output_fav" data-num="${second.sc_num}"
	                	src="${pageContext.request.contextPath}/images/jiwon/sc_fav.png" style="width:40px;height:40px;">
	                <span id="output_fcount"></span>
	                <input type="button" value="채팅하기" class="sc-disabled-btn w-25" disabled
	                	onclick="location.href='${pageContext.request.contextPath}/secondchat/chatListForBuyer?sc_num=${second.sc_num}'">
	                <input type="button" value="바로 예약" id="sc_order" class="sc-disabled-btn w-25" disabled>
                </c:if>
                </c:when>
                </c:choose>
                <!-- 로그인한 사람이 게시글 작성자일때 -->
                <c:if test="${!empty user && user.mem_num == second.mem_num}">
                <div class="row g-4 text-dark mb-5">
                	<div class="container-fluid" >
				        <div class="row" style="text-align:center;">
				            <div class="col-4">
				            	<a href="update?sc_num=${second.sc_num}">
					                <img src="${pageContext.request.contextPath}/images/jiwon/sc_modifylogo.png" 
					                	class="sc_modifylogo" width="30">
					                	<br>
					                <span>상품 수정</span>
				                </a>
				            </div>
				            <div class="col-4" id="" >
				            	<a href="#" id="sc_modify_status">	
					            	<img src="${pageContext.request.contextPath}/images/jiwon/sc_status_modifylogo.png" 
					            		class="sc_status_modifylogo" width="30">
					            		<br>
					                <span>상태 변경</span>
				                </a>
				            </div>
				            <div class="col-4">
				            	<a href="#" id="sc_delete">	
					            	<img src="${pageContext.request.contextPath}/images/jiwon/sc_deletelogo.png" 
					            		class="sc_deletelogo" width="30">
					            		<br>
					                <span>상품 삭제</span>
				                </a>
				            </div>
				    	</div>
				    </div>
                </div>
                <img id="output_fav" data-num="${second.sc_num}" style="width:40px;height:40px;"
	                	src="${pageContext.request.contextPath}/images/jiwon/sc_fav.png">
	            <span id="output_fcount"></span>
	            <input type="button" value="채팅하기" class="sc-order-btn-green w-25" onclick="location.href='${pageContext.request.contextPath}/secondchat/chatListForSeller?sc_num=${second.sc_num}'">
                </c:if> 
            </div>
        </div>
    </div>
</div>
<!-- About End -->

<br><br>

<!-- About Satrt -->
<div class="container-fluid py-6">
    <div class="container" style="border-top:3px solid #eeeeee;">
        <div class="row g-5 align-items-start">
        	
            <div class="col-lg-7">
           		<small class="d-inline-block fw-bold text-dark text-uppercase bg-light border rounded-pill px-4 py-1 mb-3">상품정보</small>
       			<p class="display-5 mb-4">${second.sc_content}</p>         	
       			<p class="fw-bold">거래 희망 장소</p>
       			<p>${second.sc_place}</p>
            	<!-- 위도 경도가 0이 아닌 경우에 지도 표시  -->
            	<c:if test="${second.sc_latitude!=0 && second.sc_longitude!=0}">
            	<!-- 지도 시작 -->
            	<div id="map" style="width:500px;height:300px;"></div>
            	
                <script>
				    // 카카오 맵 초기화
				    const container = document.getElementById('map');
				    const options = {
				        center: new kakao.maps.LatLng(${second.sc_latitude}, ${second.sc_longitude}),
				        level: 3
				    };
				
				    const map = new kakao.maps.Map(container, options);
				
				    // 마커 생성
				    const markerPosition = new kakao.maps.LatLng(${second.sc_latitude}, ${second.sc_longitude});
				    const marker = new kakao.maps.Marker({
				        position: markerPosition
				    });
				
				    // 마커 지도에 표시
				    marker.setMap(map);
				</script>
				</c:if>
            </div>
            <!-- 지도 끝 -->
            
            <div class="sc-storeinfo col-lg-5">
                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border rounded-pill px-4 py-1 mb-3">상점정보</small>
				<div class="menu-item d-flex align-items-center">
				
					<img class="flex-shrink-0 img-fluid rounded-circle"
						src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${second.mem_num}" style="width:50px" alt="">
					<div class="w-100 d-flex flex-column text-start ps-4">
						<div class="d-flex justify-content-between border-bottom pb-2 mb-2">
							<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${second.mem_num}" style="color:black;">${second.mem_nickname}</a>
							<%-- <h4>${second.sc_reg_date}</h4> --%>
						</div>
						<!--수정하기!!!  -->
						<p class="mb-0">상품 개수</p>
					</div>
				</div>
				<br>
				<div><h2>상점 후기</h2></div>
				<table>
				<c:forEach var="screv" items="${list1}">
					<tr>
						<td>
							<div class="menu-item d-flex align-items-center">
							<img class="flex-shrink-0 img-fluid rounded-circle"  style="width:50px"
							src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${screv.reviewer_num}" alt="">
						<div class="w-100 d-flex flex-column text-start ps-4">
						<div class="d-flex justify-content-between border-bottom mb-1">
							<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${screv.reviewer_num}" style="color:black;">${screv.mem_nickname}</a>
						</div>
						
						<!-- 별점 -->
						<div class="testimonial-content">
							<c:if test="${screv.sc_rev_star==5}">
								<div class="rate2 d-flex">
									<span style="width: 100%"></span>
								</div>
							</c:if>
							<c:if test="${screv.sc_rev_star==4.5}">
								<div class="rate2 d-flex">
									<span style="width: 92%"></span>
								</div>
							</c:if>
							<c:if test="${screv.sc_rev_star==4}">
								<div class="rate2 d-flex">
									<span style="width: 81%"></span>
								</div>
							</c:if>
							<c:if test="${screv.sc_rev_star==3.5}">
								<div class="rate2 d-flex">
									<span style="width: 71%"></span>
								</div>
							</c:if>
							<c:if test="${screv.sc_rev_star==3}">
								<div class="rate2 d-flex">
									<span style="width: 60%"></span>
								</div>
							</c:if>
							<c:if test="${screv.sc_rev_star==2.5}">
								<div class="rate2 d-flex">
									<span style="width: 50%"></span>
								</div>
							</c:if>
							<c:if test="${screv.sc_rev_star==2}">
								<div class="rate2 d-flex">
									<span style="width: 39%"></span>
								</div>
							</c:if>
							<c:if test="${screv.sc_rev_star==1.5}">
								<div class="rate2 d-flex">
									<span style="width: 30%;"></span>
								</div>
							</c:if>
							<c:if test="${screv.sc_rev_star==1}">
								<div class="rate2 d-flex">
									<span style="width: 20%"></span>
								</div>
							</c:if>
							<c:if test="${screv.sc_rev_star==0.5}">
								<div class="rate2 d-flex">
									<span style="width: 9%"></span>
								</div>
							</c:if>
						<p class="mt-1">${screv.sc_rev_content}</p>
						</div>
						</div>
					</div>
						
						
						</td>
					</tr>
				</c:forEach>
				</table>
				
            </div>
        </div>
    </div>
</div>
<!-- About End -->
<%--바로 예약 버튼 모달 --%>
<div id="secondOrderModal" style="display: none">
	<div class="order-modal-box">
		<div id="order_closeModalBtn">
			<span class="close">&times;</span>
		</div>
		<div>
			<h3>잠깐! 판매자와 채팅하셨나요?</h3>
			<br>		
		</div>
		<div id="detail-span1">
			<span>채팅을 하지 않고 예약하면 취소될 확률이 높아요.<br>채팅을 통해 판매자와 소통해 보세요.</span>
		</div>
		<div class="order-btn1">
			<input type="button" value="채팅하고 거래할래요." class="sc-order-btn-green"
				onclick="location.href='${pageContext.request.contextPath}/secondchat/chatListForBuyer?sc_num=${second.sc_num}'">
			<input type="button" value="채팅없이 바로 예약할래요." id="sc_orderinsert" class="sc-order-btn">
		</div>
	</div>
	<div class="modal-bg"></div>
</div>

<%-- 상태 변경 모달 --%>
<div id="secondModifyStatusModal" style="display: none">
	<div class="ms-modal-box">
		<div id="ms_closeModalBtn">
			<span class="close">&times;</span>
		</div>
		<div class="mModal-status">
			<h3 class="mModal-h3">상태 변경</h3>
			<ul>
				<li><input type="button" value="판매중" id="scForsale_btn" class="sc-sdefault-btn btn-light"></li>
				<li><input type="button" value="예약대기" id="scWaitReserve_btn" class="sc-sdefault-btn btn-light"></li>
				<li><input type="button" value="예약중" id="scReserve_btn" class="sc-sdefault-btn btn-light"></li>
				<li><input type="button" value="거래완료" id="scSellFin_btn" class="sc-sdefault-btn btn-light" 
					onclick="location.href='${pageContext.request.contextPath}/secondchat/chatSelectBuyerList?sc_num=${second.sc_num}'">
				</li>
			</ul>
		</div>
	</div>
	<div class="modal-bg"></div>
</div>

<%-- 글 삭제 모달 --%>
<div id="secondDeleteModal" style="display: none">
	<div class="dmodal-box">
		<div class="delete-span1">
			<p class="delete-span2">상품을 삭제하시겠습니까?</p>		
			<p id="delete_span3">삭제된 상품은 복구되지 않습니다.</p>
		</div>
		<div>
			<input type="button" value="취소" onclick="location.href='detail?sc_num=${second.sc_num}'" class="scdelete-btn">
			<input type="button" value="삭제" onclick="location.href='delete?sc_num=${second.sc_num}'" class="scdelete-btn-green">
		</div>
		
	</div>
	<div class="modal-bg"></div>
</div>

