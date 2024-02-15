<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 내용 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<c:if test="${book.book_onoff == 2}">
	<script type="text/javascript">
		location.replace('list');
	</script>
</c:if>
<div class="container" id="book_detail">
	<div class="d-flex justify-content-center">
		<div class="rounded col-lg-10">
			<div class="text-center" style="margin-left:20px;position:relative;">
				<c:if test="${book.book_category == 0}"><div style="background:#0f4b43;" class="book-first">취미 소모임</div></c:if>
                <c:if test="${book.book_category == 1}"><div style="background:#5eaf08;" class="book-first">원데이 클래스</div></c:if>
                <c:if test="${book.book_category == 2}"><div style="background:#486627;" class="book-first">스터디 모임</div></c:if>
				<div class="scrap-area">
             		<img id="output_scrap" data-num="${book.book_num}"
             			src="${pageContext.request.contextPath}/images/jy/noScrap.png" width="40px">
             		<br>	
             		<span id="output_scount"></span>	
             	</div>
			</div>
			<div style="float:left;">
				<c:if test="${empty book.book_thumbnailName}">
					<img class="book-thumb" src="${pageContext.request.contextPath}/images/jy/thumbnail_basic.png">
				</c:if>
				<c:if test="${!empty book.book_thumbnailName}">
					<img class="book-thumb" src="${pageContext.request.contextPath}/upload/${book.book_thumbnailName}">
				</c:if>
			</div>
			<table>
				<tr>
					<td class="match-guide">
						<c:if test="${book.book_match == 1}">
							<span>✅ 승인이 필요 없는 모임이에요!</span>
						</c:if>
						<c:if test="${book.book_match == 2}">
							<span>✅ 주최자의 승인이 필요한 모임이에요!</span>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<h4>${book.book_title} (${book.book_headcount}/${book.book_maxcount}명)</h4>
					</td>
				</tr>
				<tr>
					<td class="book-expense">
						<c:if test="${empty book.book_expense}">
							무료
						</c:if>
						<c:if test="${!empty book.book_expense}">
							<fmt:formatNumber value="${book.book_expense}"/>원
						</c:if>
					</td>
				</tr>	
				<tr class="book-profileArea">
					<td>
						<%-- 참여자 --%>
						<c:if test="${user.mem_num != book.mem_num}">
							<img class="book-profile" src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${book.mem_num}">
							<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${book.mem_num}">
								<span class="book-nick">${book.mem_nickname}</span>
							</a>
						</c:if>
						<%-- 주최자 --%>
						<c:if test="${user.mem_num == book.mem_num}">
							<c:if test="${book.compareNow == 2}">
								<c:if test="${book.book_onoff == 0 && book.book_headcount < book.book_maxcount}">
									<input type="button" value="모집 마감하기" class="bookd-btn-green w-75"
										id="complete_btn" data-num="${book.book_num}" data-head="${book.book_headcount}">
								</c:if>
								<c:if test="${book.book_onoff == 3 || book.book_headcount == book.book_maxcount}">
									<input type="button" value="모집 마감" class="btn btn-light w-100" disabled>
								</c:if>
							</c:if>
							<c:if test="${book.compareNow == 1}">
								<c:if test="${book.book_onoff == 0 || book.book_onoff == 3}">
									<input type="button" value="모임 완료하기" class="bookd-btn-green w-100 book-complete"
										data-num="${book.book_num}">
								</c:if>
								<c:if test="${book.book_onoff == 1}">
									<input type="button" value="새로 모집하기" class="bookd-btn w-75"
										id="reset_btn" data-num="${book.book_num}">
								</c:if>
							</c:if>
						</c:if>
					</td>
				</tr>
			</table>
			<hr size="3" width="100%">
			<div>
				<span class="book-span">모임 후기 (${rcount})</span>
				<br><br>
				<%-- 모임 후기 --%>
				<c:if test="${rcount == 0}">
					<div class="owl-none">
						<img src="${pageContext.request.contextPath}/images/logo_mini.png" width="40px"> 
						이 모임의 첫 번째 후기 작성자가 되어주세요!
					</div>
				</c:if>
				<c:if test="${rcount > 0}">	
				<div class="owl-carousel">
					<c:forEach var="rev" items="${rlist}">
						<div class="owl-items">
							<div class="d-flex justify-content-center p-4">
								<div class="text-center">
									<p class="mb-1">${rev.book_rev}</p>
									<hr size="3" width="100%">
									<p class="mb-1">
										✒️ ${rev.mem_nickname} (<span class="owl-grade">${rev.book_grade}점</span>)
									</p>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
				</c:if>	
				<script>
					$(document).ready(function(){
						var owl = $('.owl-carousel');
						owl.owlCarousel({
							items:1,
						    loop:true,
						    nav:true,
						    navText:['◀','▶'],
						    margin:10,
						    autoplay:true,
						    autoplayHoverPause:true
						});
						owl.on('mousewheel', '.owl-stage', function (e) {
						    if (e.originalEvent.deltaY>0) {
						        owl.trigger('next.owl');
						    } else {
						        owl.trigger('prev.owl');
						    }
						    e.preventDefault();
						});
					});
				</script>
			</div>	
			<hr size="3" width="100%">
			<div class="book-listDiv">
				<span class="book-span">모임 소개</span>
				<br><br>
				${book.book_content}
			</div>
			<hr size="3" width="100%">
			<div class="book-listDiv">
				<span class="book-span">모임 장소</span>
				<br><br>
				${book.book_address1} ${book.book_address2}
				<div id="map" style="width:500px;height:300px;margin-top:13px;"></div>
			</div>
			<div class="book-listDiv">
				<span class="book-span">모임 일정</span>
				<br><br>
				${book.book_gatheringDate}
			</div>
			<div class="book-listDiv">
				<span class="book-span">준비물</span>
				<br><br>
				<c:if test="${empty book.book_kit}">
					준비물 없음
				</c:if>
				<c:if test="${!empty book.book_kit}">
					${book.book_kit}
				</c:if>
			</div>
			<div class="sns-area">
				<input type="hidden" value="${ngrokkey}" id="ngrok">
				<input type="hidden" value="${kakao_apikey}" id="apikey">
				<a id="btnTwitter" class="link-icon" href="javascript:shareTwitter();">
					<img src="../images/jy/icon-twitter.png" width="40px">
				</a>
				<a id="btnBlog" class="link-icon">
					<img src="../images/jy/Blog_48.png" width="40px">
				</a>
				<a id="kakao-link-btn" href="javascript:kakaoShare()">
					<img src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png" width="40px">
				</a>
			</div>
			<hr size="3" width="13%" id="sns_hr">
			<div class="align-center">
				<%-- 주최자 --%>
				<c:if test="${!empty user && user.mem_num == book.mem_num && book.book_onoff == 0}">
					<input type="button" value="수정하기" class="bookd-btn w-25" id="update_btn"
						onclick="location.href='update?book_num=${book.book_num}'">
					<input type="hidden" value="${book.compareNow}" id="compare">
					<input type="hidden" value="${book.book_headcount}" id="head">	
					<script type="text/javascript">
						$('#update_btn').click(function(){
							if($('#compare').val() == 1 && $('#head').val() >= 1){
								alert('모임 완료하기 버튼을 클릭하여 모임 완료를 확정해 주세요.');
								history.go(0);
							}
						});
					</script>
					<c:if test="${book.compareNow == 2}">	
						<input type="button" value="모임 취소하기" class="bookd-btn-green w-25"
							onclick="location.href='cancel?book_num=${book.book_num}'">	
					</c:if>	
				</c:if>
				<%-- 참여자 --%>
				<c:if test="${!empty user && user.mem_num != book.mem_num}">
					<c:if test="${book.book_onoff == 0 && book.compareNow == 2 && book.book_headcount < book.book_maxcount}">
						<input type="button" value="참여 신청하기" id="book_apply" class="bookd-btn-green w-25">
					</c:if>
					<c:if test="${book.book_onoff == 3 || book.compareNow == 1 || book.book_headcount == book.book_maxcount}">
						<input type="button" value="모집 마감" class="btn btn-light w-25" disabled>
					</c:if>
				</c:if>
					<input type="button" value="목록으로" 
						onclick="location.href='list'" class="bookd-btn w-25">
				<%-- 댓글 시작 --%>
				<hr size="3" noshade="noshade" width="100%">
				<div id="reply_count"></div>
				<div id="reply_area">
					<div id="reply_div">
						<form id="re_form">
							<div class="rep-mem">
								<c:if test="${!empty user}">
									<img class="rep-profile" src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${user.mem_num}">
									<span>${user.mem_nickname}</span>
								</c:if>
							</div>
							<input type="hidden" name="book_num" value="${book.book_num}" id="book_num">
							<textarea rows="3" cols="50" name="book_rep" id="book_rep" class="rep-content" 
								placeholder="한 번 작성한 댓글은 수정이 불가합니다. 서로를 배려하는 댓글 문화에 동참해 주세요." 
								<c:if test="${empty user}">disabled="disabled"</c:if>
								><c:if test="${empty user}">로그인 후 이용하세요!</c:if></textarea>
							<c:if test="${!empty user}">
								<div id="re_first">
									<span class="letter-rpcount">300/300</span>
										<input type="submit" value="등록" id="re_btn">
								</div>
							</c:if>
						</form>
					</div>	
				</div>
				<div class="paging-button" style="display:none;">
					<input type="button" value="[댓글 더보기]" class="add-btn">
				</div>
				<div id="output"></div>
				<div id="loading" style="display:none;">
					<img src="${pageContext.request.contextPath}/images/jy/loading.gif" width="100" height="100">
				</div>
				<%-- 댓글 끝 --%>
			</div>
		</div>
	</div>
</div>
<%-- 참여 신청 폼(모달) --%>
<div id="bookApplyModal" style="display: none">
	<div class="modal-box">
		<div class="title-phrase2" style="margin-bottom:-20px;">
			이 모임에 참여할래요!
			<img src="${pageContext.request.contextPath}/images/jy/close.png" id="close_btn">	
		</div>
		<div class="applySub">
			참여 신청 모임
		</div>
		<hr size="3" noshade="noshade" width="100%">
		<ul>
			<li style="float:left;">
				<c:if test="${empty book.book_thumbnailName}">
					<img src="${pageContext.request.contextPath}/images/jy/thumbnail_basic.png">
				</c:if>
				<c:if test="${!empty book.book_thumbnailName}">
					<img src="${pageContext.request.contextPath}/upload/${book.book_thumbnailName}">
				</c:if>
			</li>
			<li class="applyTitle">
				${book.book_title}
			</li>
			<li>
				<span>✅ ${book.book_address1} ${book.book_address2}</span>
			</li>
			<li>
				<c:if test="${book.book_match == 1}">
					✅ 바로 승인/ 
				</c:if>
				<c:if test="${book.book_match == 2}">
					✅ 승인 필요/ 
				</c:if>
				<c:if test="${empty book.book_expense}">
					무료/ 
				</c:if>
				<c:if test="${!empty book.book_expense}">
					<fmt:formatNumber value="${book.book_expense}"/>원/  
				</c:if>
				<c:if test="${empty book.book_kit}">
					준비물 없음
				</c:if>
				<c:if test="${!empty book.book_kit}">
					${book.book_kit}
				</c:if>
			</li>
		</ul>
		<div class="applySub">
			신청자 정보
		</div>
		<hr size="3" noshade="noshade" width="100%">
			<div class="applyDiv">
				<label for="mem_name">이름</label>
				<input type="text" name="mem_name" autocomplete="off"
					id="mem_name" class="form-control" required/>
				<span id="nameValid" class="error-phrase"></span>
			</div>
			<div class="applyDiv2">	
				<label for="mem_email">이메일</label>
				<input type="email" name="mem_email" autocomplete="off"
					id="mem_email" class="form-control" required/>
				<span class="guide-phrase">*입력하신 이메일 주소로 신청 완료 메일이 전송됩니다. 실제 사용하고 계신 이메일을 기입해 주세요.</span>
				<br>
				<span id="emailValid" class="error-phrase"></span>
			</div>
			<div class="applyDiv">
				<label for="mem_phone">연락처</label>
				<input type="text" name="mem_phone" autocomplete="off"
					id="mem_phone" class="form-control" required/>
				<span id="phoneValid" class="error-phrase"></span>
			</div>
			<div>
				<label for="agree">개인 정보 수집에 동의합니다.</label>
				<input type="checkbox" id="agree" required>
				<br>
				<span id="agreeValid" class="error-phrase"></span>
			</div>
			<div style="margin-bottom:12px;">
				<label for="notify">참여를 원하는 모임의 안내사항을 전부 확인하였습니다.</label>
				<input type="checkbox" id="notify" required>
				<br>
				<span id="notifyValid" class="error-phrase"></span>
			</div>
			<button id="apply_btn" class="default-btn" data-num="${book.book_num}" 
				data-apply="${user.mem_num}" data-state="${book.book_state}" 
				data-onoff="${book.book_onoff}" data-g="${book.book_gatheringDate}"
				data-title="${book.book_title}" data-addr="${book.book_address1}">참여하기</button>
			<img src="${pageContext.request.contextPath}/images/jy/loading.gif" id="apply_loading">		
	</div>
	<div class="modal-bg"></div>
</div>
<%-- Daum 지도 API 시작 --%>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=397d490d4a8bb2a2dc0a8a1612615084&libraries=services"></script>
	<script>
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표(초기 값으로 주소를 좌표로 바꿔서 제공하면 중심 좌표가 바뀜)
        level: 3 // 지도의 확대 레벨
    };  

	// 지도를 생성합니다    
	var map = new daum.maps.Map(mapContainer, mapOption); 
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new daum.maps.services.Geocoder();
	// 주소로 좌표를 검색합니다
	geocoder.addressSearch('${book.book_address1}', function(result, status) {
	    // 정상적으로 검색이 완료됐으면 
	     if (status === daum.maps.services.Status.OK) {
	    	 console.log('lat : ' + result[0].y);
	    	 console.log('lng : ' + result[0].x);
	        var coords = new daum.maps.LatLng(result[0].y, result[0].x);
	        // 결과값으로 받은 위치를 마커로 표시합니다
	        var marker = new daum.maps.Marker({
	            map: map,
	            position: coords
	        });
	        
	        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	        map.setCenter(coords);
	    } 
	});    
</script>
<%-- Daum 지도 API 끝 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jy/book.apply.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jy/book.scrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jy/book.reply.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jy/owl.carousel.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script type="text/javascript">
let ngrok = $('#ngrok').val();

//모달창
$(document).on('click','#close_btn',function(){
	if(confirm('작성 및 변경한 내용은 유지되지 않습니다. 나가시겠습니까?')==false){
		return;
	}
	
	$('body').css('overflow-y','');
	$('#bookApplyModal').hide();
});

//트위터 공유
function shareTwitter(){
	let sendText = "ZIBI 가입하고 소모임 함께 해요!";
	let sendUrl = ngrok + "/book/detail?book_num=${book.book_num}";
	window.open("https://twitter.com/intent/tweet?text="+sendText+"&url="+sendUrl);
}

//네이버 공유
function format(){
	var args = Array.prototype.slice.call(arguments,1);
	return arguments[0].replace(/\{(\d+)\}/g,function(match,index){
		return args[index];
	});
}

function shareBlog(url,title){
	let encodeUrl = encodeURIComponent(url);
	let encodeTitle = encodeURIComponent(title);
	
	let link = format('https://share.naver.com/web/shareView.nhn?url={0}&title={1}',encodeUrl, encodeTitle);
	window.open(link, 'share', 'width=500, height=500');
}

$('#btnBlog').click(function(){
	shareBlog(ngrok + '/book/list','ZIBI 가입하고 소모임 함께 해요!');
});

//카카오 공유
let apikey = $('#apikey').val();
Kakao.init(apikey);

function kakaoShare(){
	Kakao.Link.sendDefault({
		objectType:'feed',
		content:{
			title: 'ZIBI 가입하고 소모임 함께 해요!',
			description:'국내 유일 자취 플랫폼 ZIBI',
			imageUrl:ngrok + '/upload/${book.book_thumbnailName}',
			link:{
				mobileWebUrl:ngrok + '/book/detail?book_num=${book.book_num}',
				webUrl:ngrok + '/book/detail?book_num=${book.book_num}'
			},
		},
		buttons:[
			{
				title:'웹으로 보기',
				link:{
					mobileWebUrl:ngrok + '/book/detail?book_num=${book.book_num}',
					webUrl:ngrok + '/book/detail?book_num=${book.book_num}'
				},
			},
		],
		//카카오톡 미설치 시 카카오톡 설치 경로 이동
		installTalk:true,
	})
}
</script>
<!-- 내용 끝 -->