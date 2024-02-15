<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="${pageContext.request.contextPath}/css/de.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/helper.scrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/helper.solution.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/helper.reply.js"></script>
<!-- 내용 시작 -->
<div class="container">
<div class="row">
<div class="detail-border col-10">
		<c:if test="${helper.helper_select ==  1}"><div style="background:#04B486;" class="border_cate">[ 헬프미  ]</div></c:if>
		<c:if test="${helper.helper_select ==  2}"><div style="background:#04B486;" class="border_cate">[ 헬프유  ]</div></c:if>
		<!-- 해결중&해결완료 토글 -->
		<div class="align-right">
		<div>
		<div id="output_solution" data-num="${helper.helper_num}"></div>
		<span id="output_text" data-num="${helper.helper_num}" style="font-size:20px; font-weight:bold; margin-left:10px;"></span>
		<c:if test="${user.mem_num == helper.mem_num}">
		<img id="output_solution" class="toggle solution"
			src="${pageContext.request.contextPath}/images/de/toggle1.png" width="60">
		</c:if>
		<br>
		</div>
		</div>
		<br>
		<div class="title">
		ㅣ${helper.helper_title}ㅣ
		</div><br>
		<div class="align-center">
		<c:if test="${empty helper.helper_filename}">
		<img src="${pageContext.request.contextPath}/images/de/noimg.png"
			style="width:300px; height:300px;">
		</c:if>
		<c:if test="${!empty helper.helper_filename}">
		<img src="${pageContext.request.contextPath}/upload/${helper.helper_filename}"
			style="width:300px; height:300px;" class="radius">
		</c:if>
		</div>
		<br>
		<div>
			<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${helper.mem_num}">
			<img src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${helper.mem_num}"
						width="50" height="50" class="my-photo radius margin-photo"></a>
			${helper.mem_nickname}
			<div class="align-right margin-date">
			<c:if test="${!empty helper.helper_modify_date}">
			최근 수정일 : ${helper.helper_modify_date}
			</c:if>
			<c:if test="${empty helper.helper_modify_date}">
			작성일 : ${helper.helper_reg_date}
			</c:if>
			</div>
		</div>
	<hr size="3" width="99%" noshade="noshade">	
	<c:if test="${helper.helper_category ==  1}"><div class="font-color">🐛 벌레 🐛</div></c:if>
	<c:if test="${helper.helper_category ==  2}"><div class="font-color">⚙️ 조립 ⚙️</div></c:if>
	<c:if test="${helper.helper_category ==  3}"><div class="font-color">🛠️ 수리 🛠️</div></c:if>
	<c:if test="${helper.helper_category ==  4}"><div class="font-color">🛒 소분 🛒</div></c:if>
	<c:if test="${helper.helper_category ==  5}"><div class="font-color">🏃‍♀️ 기타 🏃‍♀️</div></c:if>
	<hr size="3" width="12%" noshade="noshade" style="text-align: left; margin-left:5px;"><br>	
	<div style="margin-left:30px; margin-right:20px;">
	<h3>상세설명</h3>
	<div>
	${helper.helper_content}
	</div><br>
	<h3>도움장소</h3>
	<div><img src="${pageContext.request.contextPath}/images/de/map.png" width="30">
	${helper.helper_address1} ${helper.helper_address2}
	<div id="map"></div><br>
	</div>
	</div>
	<div class="align-right">
		<c:if test="${!empty user}">
		<!-- 스크랩 -->		
		<img id="output_scrap" data-num="${helper.helper_num}" 
			src="${pageContext.request.contextPath}/images/de/heart1.png" width="30">
		스크랩 <span id="output_scount"></span><span>·</span>
		</c:if>
		<!-- 조회수 -->		
		<img src="${pageContext.request.contextPath}/images/de/hit.png" width="30">
		조회수  ${helper.helper_hit}<span>·</span>
		<!-- 댓글수 -->	
		<img src="${pageContext.request.contextPath}/images/de/reply.png" width="30">
		<span id="output_rcount"></span>
	</div>
	<hr size="3" width="99%" noshade="noshade">	
	<!-- 댓글 시작 -->
	<div id="reply_div" class="align-center">
		<span class="re_title"></span>
		<form id="re_form">
			<input type="hidden" name="helper_num" value="${helper.helper_num}" id="helper_num">
			<div class="float-left">
			<textarea rows="3" cols="60" name="re_content" id="re_content" class="rep-content" placeholder="댓글을 입력해주세요."
			<c:if test="${empty user}">disabled="disabled"</c:if>
			><c:if test="${empty user}">로그인 후 작성할 수 있습니다.</c:if></textarea>	
			</div>
		  <c:if test="${!empty user}">
		  <div class="float-left">
		  	<input type="submit" value="작성하기" class="btn-reply">
		   </div>	
		  <div id="re_first">
		  	<span class="letter-count">300/300</span>
		  </div>
		  </c:if>
		</form>
	</div>
	<!-- 댓글 목록 출력 시작 -->
	<div id="output"></div>
	<div class="paging-button" style="display:none;">
		<input type="button" value="더보기" class="d-inline-block text-dark text-uppercase bg-light border border-color rounded-pill px-2 py-1 mb-1">
	</div>
	<div id="loading" style="display:none;">
	<img src="${pageContext.request.contextPath}/images/de/loading.gif" width="100" height="100">
	</div>
	<!-- 댓글 목록 출력 끝 -->
	<!-- 댓글 끝 -->
	<br><br>
</div>
<div class="col-1 detail-border2">
	<div class="align-center">
	<br><br><br>
	<c:if test="${!empty user}">
	<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${user.mem_num}">
	<img src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${user.mem_num}" width="120" height="120" class="my-photo radius2">
	</a>
	</c:if>	
	<c:if test="${empty user}">
	<img src="${pageContext.request.contextPath}/images/de/basic.png" width="120" height="120" class="my-photo radius2">
	</c:if>								
	<br><br><br>
			<c:if test="${user.mem_num == helper.mem_num}">
			<input type="button" value="글수정" onclick="location.href='update?helper_num=${helper.helper_num}'"
				class="btn mem-btn w-100"><br><br>
			<input type="button" value="글삭제" onclick="location.href='delete?helper_num=${helper.helper_num}'"
					id="delete_btn" class="btn mem-btn w-100"><br><br>
						<script type="text/javascript">
						let delete_btn = document.getElementById('delete_btn');
						//이벤트 연결
						delete_btn.onclick=function(){
							let choice = confirm('삭제하시겠습니까?');
							if(choice){
								location.href='delete?helper_num=${helper.helper_num}';
							}
						};
						</script>
			</c:if>
			<!-- 채팅 따로 구현하기 - 시연 때문에 홈으로라고 명칭만 변경해둠 -->
	<input type="button" value="홈으로" onclick="location.href='talkList?helper_num=${helper.helper_num}'" 
			class="btn mem-btn-green w-100"><br><br>	
	<input type="button" value="목록으로" onclick="location.href='list?helper_num=${helper.helper_num}'" 
			class="btn mem-btn-green w-100">	
	</div>
</div>
</div>
</div>
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
	geocoder.addressSearch('${helper.helper_address1}', function(result, status) {
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
<!-- 내용 끝 -->