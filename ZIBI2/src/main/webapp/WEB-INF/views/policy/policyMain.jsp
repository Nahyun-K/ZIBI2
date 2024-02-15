<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container page-width text-center">
	<c:if test="${empty list}">
		<div class="policy-map text-center">
			데이터가 존재하지 않습니다
		</div>
	</c:if>
	<c:if test="${!empty list}">
		<div class="policy-map text-center">
			<h3>지자체에서 시행 중인 1인 가구 정책을 찾아보세요 🔍</h3>
			<span>지도의 지자체를 클릭하시면 해당 지자체의 1인 가구 정책 사이트로 연결됩니다</span>
			<span>(없는 지자체는 표시되지 않습니다)</span>
		</div>
		<div id="map" style="width:100%; height:600px;"></div>
		<p>연결된 사이트가 유효하지 않은 URL일 경우 관리자에게 문의해주세요</p>
	</c:if>
</div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakao_apikey}"></script>
<script>
	$('#policy_btn').toggleClass("mem-btn");
	$('#policy_btn').toggleClass("mem-btn-green");
	
	var MARKER_WIDTH = 33, // 기본, 클릭 마커의 너비
	MARKER_HEIGHT = 36, // 기본, 클릭 마커의 높이
	OFFSET_X = 12, // 기본, 클릭 마커의 기준 X좌표
	OFFSET_Y = MARKER_HEIGHT, // 기본, 클릭 마커의 기준 Y좌표
	OVER_MARKER_WIDTH = 40, // 오버 마커의 너비
	OVER_MARKER_HEIGHT = 42, // 오버 마커의 높이
	OVER_OFFSET_X = 13, // 오버 마커의 기준 X좌표
	OVER_OFFSET_Y = OVER_MARKER_HEIGHT, // 오버 마커의 기준 Y좌표
	SPRITE_MARKER_URL = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markers_sprites2.png', // 스프라이트 마커 이미지 URL
	SPRITE_WIDTH = 126, // 스프라이트 이미지 너비
	SPRITE_HEIGHT = 146, // 스프라이트 이미지 높이
	SPRITE_GAP = 10; // 스프라이트 이미지에서 마커간 간격

	var markerSize = new kakao.maps.Size(MARKER_WIDTH, MARKER_HEIGHT), // 기본, 클릭 마커의 크기
		markerOffset = new kakao.maps.Point(OFFSET_X, OFFSET_Y), // 기본, 클릭 마커의 기준좌표
		overMarkerSize = new kakao.maps.Size(OVER_MARKER_WIDTH, OVER_MARKER_HEIGHT), // 오버 마커의 크기
		overMarkerOffset = new kakao.maps.Point(OVER_OFFSET_X, OVER_OFFSET_Y), // 오버 마커의 기준 좌표
		spriteImageSize = new kakao.maps.Size(SPRITE_WIDTH, SPRITE_HEIGHT); // 스프라이트 이미지의 크기
	
		var positions = [] , selectedMarker = null;
		
	//루프를 돌면서 관리자가 저장한 행정구역의 정보를 읽어 옴
	<c:forEach var="policyVO" items="${list}" varStatus="status">
		if( '${policyVO.policy_url}'.substr(0,4) == 'http' ){
			var district_num = ${policyVO.district_num};
			var district_name = '${policyVO.district_name}';
			var district_latitude = ${policyVO.district_latitude};
			var district_lonitude = ${policyVO.district_lonitude};
			var policy_url = '${policyVO.policy_url}';
			positions.push( { url:policy_url, place:new kakao.maps.LatLng(district_latitude,district_lonitude), content:district_name } );
		}
	</c:forEach>
	
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
		mapOption = {
			center : new kakao.maps.LatLng(35.724906989225346,128.0894168414805), // 지도의 중심좌표
			level : 13 // 지도의 확대 레벨
		};
	
	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	
	//지도 위에 마커를 표시합니다
	for (var i = 0, len = positions.length; i < len; i++) {
		var gapX = (MARKER_WIDTH + SPRITE_GAP), // 스프라이트 이미지에서 마커로 사용할 이미지 X좌표 간격 값
			originY = (MARKER_HEIGHT + SPRITE_GAP) * i, // 스프라이트 이미지에서 기본, 클릭 마커로 사용할 Y좌표 값
			overOriginY = (OVER_MARKER_HEIGHT + SPRITE_GAP) * i, // 스프라이트 이미지에서 오버 마커로 사용할 Y좌표 값
			normalOrigin = new kakao.maps.Point(0, originY), // 스프라이트 이미지에서 기본 마커로 사용할 영역의 좌상단 좌표
			clickOrigin = new kakao.maps.Point(gapX, originY), // 스프라이트 이미지에서 마우스오버 마커로 사용할 영역의 좌상단 좌표
			overOrigin = new kakao.maps.Point(gapX * 2, overOriginY); // 스프라이트 이미지에서 클릭 마커로 사용할 영역의 좌상단 좌표
	
		// 마커를 생성하고 지도위에 표시합니다
		addMarker(positions[i].place, positions[i].url, positions[i].content, overOrigin, clickOrigin);
	}
	
	//마커를 생성하고 지도 위에 표시하고, 마커에 mouseover, mouseout, click 이벤트를 등록하는 함수입니다
	function addMarker(position, url, overOrigin, clickOrigin) {
	
		// 마커를 생성함
		var marker = new kakao.maps.Marker({
			map : map, position : position
		});
		
		
		// 마커에 표시할 인포윈도우를 생성합니다 
	    var infowindow = new kakao.maps.InfoWindow({
	        content: '<div style="padding:5px;">🚩'+positions[i].content+'</div>' // 인포윈도우에 표시할 내용
	    });
		
		// 마커에 click 이벤트를 등록합니다
		kakao.maps.event.addListener(marker, 'click', function() {
	
			// 클릭된 마커가 없고, click 마커가 클릭된 마커가 아니면 클릭 이벤트 발생
			if (!selectedMarker || selectedMarker !== marker) {
				window.open(url);
			}
			// 클릭된 마커를 현재 클릭된 마커 객체로 설정합니다
			selectedMarker = marker;
		});
		
		// 마커에 mouseover 이벤트를 등록합니다
		kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow) );
	
		// 마커에 mouseout 이벤트를 등록합니다
		kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow) );
	}
	
	// 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
	function makeOverListener(map, marker, infowindow) {
	    return function() {
	        infowindow.open(map, marker);
	    };
	}
	
	// 인포윈도우를 닫는 클로저를 만드는 함수입니다 
	function makeOutListener(infowindow) {
	    return function() {
	        infowindow.close();
	    };
	}
</script>