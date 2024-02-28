<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>

        <!-- 결제창 start -->
        <div class="container-fluid blog py-6">
            <div class="container">
                <div class="text-center wow" data-wow-delay="0.1s">
                    <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Payment</small>
                    <h1 class="display-5">결제하기</h1>
                    <h5 class="mb-5" style="color:#32a77b;"> ※지비 회원은 할인가로 만나볼 수 있습니다</h5>
                </div>
                <div class="row gx-4 justify-content-center">
                
                
                	<!-- 예매 정보 시작 -->
                    <div class="col-md-6 col-lg-4 wow" >
                        <div class="blog-item">
                            <div class="blog-content mx-4 d-flex rounded bg-light">
                                <div class="text-dark mem-btn-green rounded-start">
                                    <div class="h-100 p-3 d-flex flex-column justify-content-center text-center">
                                        <p class="fw-bold mb-0 white-color">1</p>
                                        <p class="fw-bold mb-0 white-color">Step</p>
                                    </div>
                                </div>
                                <p class="h5 lh-base my-auto h-100 p-3">예매정보</p>
                            </div>
                            <div class="overflow-hidden rounded payment-body">
                            	<p>${payPerformance.performance_title}</p>
                            	<p><img class="img-fluid rounded" src="https://image.tmdb.org/t/p/w500/${payPerformance.performance_poster}" alt="" style="width:150px;"></p>
                            	<%-- <p>연령 : 
                            	<c:if test="${payPerformance.performance_age == 0}">
								전체 관람가
								<img class="ratingAge" src="${pageContext.request.contextPath}/images/hyun/rating1.png">
								</c:if>
								<c:if test="${payPerformance.performance_age == 12}">
								12세 이상 관람
								<img class="ratingAge" src="${pageContext.request.contextPath}/images/hyun/rating2.png">
								</c:if>
								<c:if test="${payPerformance.performance_age == 15}">
								15세 이상 관람
								<img class="ratingAge" src="${pageContext.request.contextPath}/images/hyun/rating3.png">
								</c:if>
								<c:if test="${payPerformance.performance_age == 19}">
								청소년 관람 불가
								<img class="ratingAge" src="${pageContext.request.contextPath}/images/hyun/rating4.png">
								</c:if>
                            	</p> --%>
                            	<p>영화관 : ${payCinema.cinema_theater}</p>
                            	<p>일시 : ${payTicketing.ticketing_date} ${payTicketing.ticketing_start_time} </p>
                            	<p></p>
                            	<div>
									좌석 : 
									<c:forEach var="seatList" items="${seatList}" varStatus="status">
										<c:set var="text" value="${fn:split(seatList,'_')}" />
										<c:forEach var="textValue" items="${text}" varStatus="varStatus">
											<c:if test="${varStatus.count eq 1}">
									         ${textValue+1}행
									         </c:if>
									         <c:if test="${varStatus.count eq 2}">
									         ${textValue+1}열
									         </c:if>
									    </c:forEach>
									</c:forEach>
								</div>
                            </div>
                        </div>
                    </div>
                    <!-- 예매 정보 끝 -->
                    
                    
                    <!-- 결제 수단 시작 -->
                    <div class="col-md-6 col-lg-4 wow">
                        <div class="blog-item">
                            <div class="blog-content mx-4 d-flex rounded bg-light">
                                <div class="text-dark mem-btn-green rounded-start">
                                    <div class="h-100 p-3 d-flex flex-column justify-content-center text-center">
                                        <p class="fw-bold mb-0 white-color">2</p>
                                        <p class="fw-bold mb-0 white-color">Step</p>
                                    </div>
                                </div>
                                <p class="h5 lh-base my-auto h-100 p-3">결제수단</p>
                            </div>
                            <div class="overflow-hidden rounded payment-body">
                            	<input type="radio" name="options" value="kakaopay"> 
								<img style="margin:5px; height:32px;" src="${pageContext.request.contextPath}/sample/img/payment_icon_yellow_medium.png" alt="">
								카카오페이                            	
                            	<br>
								<input type="radio" name="options" value="card"> 
								<img style="margin:5px; height:40px;" src="${pageContext.request.contextPath}/sample/img/logo_kg.png" alt="">
								카드결제
                            </div>
                        </div>
                    </div>
                    <!-- 결제 수단 끝 -->
                    
                    <!-- 결제하기 시작 -->
                    <div class="col-md-6 col-lg-4 wow">
                        <div class="blog-item">
                        	<div class="blog-content mx-4 d-flex rounded bg-light">
                                <div class="text-dark mem-btn-green rounded-start">
                                    <div class="h-100 p-3 d-flex flex-column justify-content-center text-center">
                                        <p class="fw-bold mb-0 white-color">3</p>
                                        <p class="fw-bold mb-0 white-color">Step</p>
                                    </div>
                                </div>
                                <p class="h5 lh-base my-auto h-100 p-3">결제하기</p>
                            </div>
                            <div class="overflow-hidden rounded payment-body">
                    			<h5 class="mb-5" style="color:#32a77b;"> ※지비 회원은 할인가로 만나볼 수 있습니다 <span style="font-size:12pt;color:#32a77b;"> (1인 2000원 할인)</span></h5>
                            	<c:if test="${adult_money>0}">
                            	<div class="ticketInfo">
                            		일반 : <fmt:formatNumber value="${payCinema.cinema_adult}" pattern="#,###" /><br>
                            		-> 할인가 <fmt:formatNumber value="${payCinema.cinema_adult-2000}" pattern="#,###" /> X ${adult_money}
                           		</div>
						        </c:if>
						        <c:if test="${teenage_money>0}">
								<div class="ticketInfo">
									청소년 : <fmt:formatNumber value="${payCinema.cinema_teenage}" pattern="#,###" /><br>
									-> 할인가 <fmt:formatNumber value="${payCinema.cinema_teenage-2000}" pattern="#,###" /> X ${teenage_money}
								</div>
								</c:if>
								<c:if test="${treatement_money>0}">
								<div class="ticketInfo">
									우대 : <fmt:formatNumber value="${payCinema.cinema_treatment}" pattern="#,###" /><br>
									-> 할인가 <fmt:formatNumber value="${payCinema.cinema_treatment-2000}" pattern="#,###" /> X ${treatement_money}
								</div><br>
								</c:if>
								<div>결제 금액 : 
								<fmt:formatNumber value="${(payCinema.cinema_adult-2000)*adult_money + (payCinema.cinema_teenage-2000)*teenage_money + (payCinema.cinema_treatment-2000)*treatement_money}" pattern="#,###" /> 
									<span style="display:none;" id="total_price">${(payCinema.cinema_adult-2000)*adult_money + (payCinema.cinema_teenage-2000)*teenage_money + (payCinema.cinema_treatment-2000)*treatement_money}</span>
								</div>
                            	
                            </div>
                        </div>
                    </div>
                    <!-- 결제하기 시작 -->
                    <div>
						<button class="mem-btn-green py-2 px-4 d-none d-xl-inline-block rounded-pill submitBtn" onclick="pay()">결제</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 결제창 End -->


<!-- <input type="radio" name="options" value="tosspay"> 토스페이 -->




<!-- ----------------------------<< 결제 >>------------------------------------ -->
<form id="choiceValue" action="choiceSeat" method="get">
	<input type="hidden" id="uid" name="uid" value=""/>
</form>
<!-- ----------------------------<< 결체 >>------------------------------------ -->

<div id="payment-data" data-ticketing-num="${payTicketing.ticketing_num}" data-cinema-num="${payCinema.cinema_num}" data-choice-seat="${seatList}" data-choice-adult="${adult_money}" data-choice-teenage="${teenage_money}" data-choice-treatment="${treatement_money}"></div>

<!-- 결제하기 버튼 생성 -->
<script type="text/javascript">
function total(){ 
	var total_price = $('#total_price').text();
	console.log(total_price);
}
var IMP = window.IMP;
IMP.init("imp22154723"); // 가맹점 식별코드

function pay(){
	
	console.log($("#payment-data").data("ticketing-num"));
	console.log($("#payment-data").data("cinema-num"));
	
	var listVar = $('input[name=options]:checked').val();
	console.log(listVar);
	if(listVar == 'kakaopay') {
		let method = 'kakaopay';
		let payMethod = 'card';
		payment(method, payMethod);
	} else if(listVar == 'card'){
		let method = 'html5_inicis';
		let payMethod = 'card';
		payment(method, payMethod);
	}
	
}
 
function payment(method, payMethod) {
	IMP.request_pay({
	    pg : method, // html5_inicis : 일반 카드 // kakaopay.TC0ONETIME : 카카오페이 // pg사 코드 https://portone.gitbook.io/docs/tip/pg-2
	    pay_method : payMethod, // kakaopay에서 생략 가능
	    merchant_uid: "order_no_" + new Date().getTime(), // 상점에서 관리하는 고유 주문 번호 - String
	    name : '주문명:결제테스트', // 상품명
	    amount : $('#total_price').text(), // 가격 - integer
	    buyer_email : '',
	    buyer_name : '구매자이름', // 구매자 이름
	    buyer_tel : '', // 휴대폰 번호
	}, function(rsp) {
	    if ( rsp.success ) {
	    	var total_price = document.getElementById('total_price');
	    	console.log('가격 : ' + total_price);
	    	console.log('uid : ' + rsp.merchant_uid);
	    	$('#uid').attr('value', rsp.merchant_uid);
	    	//[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
	    	$.ajax({
	    		url: "/performance/initPayment", //cross-domain error가 발생하지 않도록 주의해주세요
	    		type: 'POST',
	    		data: {
	    			// uid, type, price, choice_num

		    		imp_uid : rsp.imp_uid,
		    		merchant_uid : rsp.merchant_uid,
		    		pay_method : payMethod,
		    		total_price : $('#total_price').text(),
		    		
		    		ticketing_num : $('#payment-data').data("ticketing-num"),
		    		cinema_num : $('#payment-data').data("cinema-num"),
		    		choice_seat : $('#payment-data').data("choice-seat"),
		    		choice_adult : $('#payment-data').data("choice-adult"),
		    		choice_teenage : $('#payment-data').data("choice-teenage"),
		    		choice_treatment : $('#payment-data').data("choice-treatment")
		    		
	    		},
	    		dataType: 'json',
	    		success:function(param){
	    			if(param.result=='success'){
						var msg = '결제가 완료되었습니다.';
		    			msg += '\n상점 거래ID : ' + rsp.merchant_uid;
		    			//msg += '\n 결제 금액 : ' +rsp.merchant_uid;
		    			//msg += ' 카드 승인번호 : ' + rsp.apply_num; // 카카오 페이지에서는 안 보임
		    			
		    			alert(msg);
		    			
		    			// 결제가 성공했을 때 submit
		    			submit();
					}
				},
				error:function(){
					alert('결제 네트워크 오류 발생');
					console.log('error1 : ' + ticketing_num);
					ifError();
				}
	    	})
	    } else {
	    	ticketing_num=$('#payment-data').data("ticketing-num");
	    	console.log('error2 : ' + ticketing_num);
	        var msg = '결제에 실패하였습니다. ';
	        msg += '에러내용 : ' + rsp.error_msg;
	        alert(msg);
	    	ifError();
	    }
	});
}


function ifError() {
	//alert('error 진입');
	$.ajax({
		url:'errorPayment',
		type:'post',
		data:{
			ticketing_num : $('#payment-data').data("ticketing-num"),
    		cinema_num : $('#payment-data').data("cinema-num"),
    		choice_seat : $('#payment-data').data("choice-seat"),
    		choice_adult : $('#payment-data').data("choice-adult"),
    		choice_teenage : $('#payment-data').data("choice-teenage"),
    		choice_treatment : $('#payment-data').data("choice-treatment")
		},
		dataType:'json',
		success:function(param){
				//alert('에러 성공ㄹ');
			location.href='/main/home';
		},
		error:function(){
			alert('네트워크 오류 발생');
		}
	});
}
function submit() {
	document.getElementById('choiceValue').submit(); // submit
}

</script>
