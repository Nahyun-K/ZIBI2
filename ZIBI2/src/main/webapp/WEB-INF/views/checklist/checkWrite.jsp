<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<link href="${pageContext.request.contextPath}/css/yeeun.css" rel="stylesheet">   
<!-- 내용 시작 -->
<%-- daterangepicker --%>
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<%-- CKEditor --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
window.onload = function(){
	let checklist_maxcount = document.getElementById('checklist_maxcount');
	if(checklist_maxcount.value == 0){
		checklist_maxcount.value = '';
	}
};
</script>
<div class="container">
	<div class="d-flex justify-content-center">
		<div class="rounded col-md-4 col-lg-6">
			<form:form action="checkwrite" id="checklist_write" 
					modelAttribute="checkListVO" enctype="multipart/form-data">
				<h2>등록하기</h2>
				<div class="align-right">
					<h4>필수 입력사항</h4>
				</div>
				<hr size="3" noshade="noshade" width="100%">
				<br>
				<div>
					<form:label path="room_name">매물 이름</form:label>
					<form:input path="room_name" class="w-100 form-control p-3" autocomplete="off"/>
					<form:errors path="room_name" cssClass="error-phrase"/>
				</div>
				<br>
				<div>
					<form:label path="room_address1">매물 주소</form:label>
					<input type="button" onclick="sample5_execDaumPostcode()" value="주소 찾기" class="default-btn">
					<form:input path="room_address1" class="w-100 form-control p-3" autocomplete="off"/>
					<form:errors path="room_address1" cssClass="error-phrase"/>
				</div>
				<br>
				<div>
					<form:label path="room_address2">상세 주소</form:label>
					<form:input path="room_address2" class="w-100 form-control p-3" autocomplete="off"/>
					<form:errors path="room_address2" cssClass="error-phrase"/>
				</div>
				<br>
				<div>
			       	<div id="map" style="width:500px;height:300px;margin-top:13px;display:none"></div>
			    </div>
				<div>
					<form:label path="room_deposit">전세금</form:label>
					<form:input path="room_deposit" class="w-50 form-control p-3" autocomplete="off"/>
					<form:errors path="room_deposit" cssClass="error-phrase"/>
				</div>
				<br>
				<div>
					<form:label path="room_expense">관리비</form:label>
					<form:input path="room_expense" class="w-50 form-control p-3" autocomplete="off"/>
					<form:errors path="room_expense" cssClass="error-phrase"/>
				</div>
				<br>
				<div>
					<form:label path="room_size">평형</form:label>
					<form:input path="room_size" class="w-50 form-control p-3" autocomplete="off"/>
					<form:errors path="room_size" cssClass="error-phrase"/>
				</div>
				<br>
					<fieldset>
						<span class="text-bold">평가</span><br>
					</fieldset>
					<div>
						<form:textarea class="col-auto form-control"
							path="room_description"
							placeholder="집의 상태, 주변환경, 가격 등을 고려해서 전반적인 평가를 입력해주세요."/>
					</div>			
				<br>
				<div class="filebox">
					<form:label path="room_filename">파일</form:label>
					<br>
					<input type="file" id="room_filename" name="upload"
						accept="image/gif,image/png,image/jpeg">
					<br>
				</div>
				<div class="align-right" style="margin-top:50px;">
					<h4>선택 입력사항</h4>
					<hr size="3" noshade="noshade" width="100%">
				</div>
				<h4>[ 체크리스트 ]</h4>
				<br>
				<div>
				집의 전반적인 채광량은 어떤가요? &nbsp; 
				<form:radiobutton path="room_check1" value="1"/> 별로에요
				<form:radiobutton path="room_check1" value="2"/> 좋아요 
				<br><br>
				집의 방음 상태는 어떤가요? &nbsp;
				<input type="radio" name="room_check2" value="1"/> 별로에요
				<form:radiobutton path="room_check2" value="2"/> 좋아요
				<br><br>
				싱크대 배수 상태는 어떤가요? &nbsp; 
				<form:radiobutton path="room_check3" value="1"/> 별로에요
				<form:radiobutton path="room_check3" value="2"/> 좋아요
				<br><br>
				지하철역 접근성은 어떤가요? &nbsp; 
				<form:radiobutton path="room_check4" value="1"/> 별로에요
				<form:radiobutton path="room_check4" value="2"/> 좋아요
				<br><br>
				대형마트 접근성은 어떤가요? &nbsp; 
				<form:radiobutton path="room_check5" value="1"/> 별로에요
				<form:radiobutton path="room_check5" value="2"/> 좋아요
				<br><br>
				발코니 상태는 어떤가요? &nbsp; 
				<form:radiobutton path="room_check6" value="1"/> 별로에요
				<form:radiobutton path="room_check6" value="2"/> 좋아요
				</div>
				<br><br> 
				<div class="align-center" style="margin-top:20px;">
				<input type="submit" value="저장" class="btn  che-btn-green_">
				</div>
			</form:form>
		</div>
	</div>
</div>
<!-- 내용 끝 -->
<!-- Daum 지도 API 시작 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=397d490d4a8bb2a2dc0a8a1612615084&libraries=services"></script>
<script>
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
            level: 5 // 지도의 확대 레벨
        };

    //지도를 미리 생성
    var map = new daum.maps.Map(mapContainer, mapOption);
    //주소-좌표 변환 객체를 생성
    var geocoder = new daum.maps.services.Geocoder();
    //마커를 미리 생성
    var marker = new daum.maps.Marker({
        position: new daum.maps.LatLng(37.537187, 127.005476),
        map: map
    });

    function sample5_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = data.address; // 최종 주소 변수

                // 주소 정보를 해당 필드에 넣는다.
                document.getElementById("room_address1").value = addr;
                // 주소로 상세 정보를 검색
                geocoder.addressSearch(data.address, function(results, status) {
                    // 정상적으로 검색이 완료됐으면
                    if (status === daum.maps.services.Status.OK) {

                        var result = results[0]; //첫번째 결과의 값을 활용

                        // 해당 주소에 대한 좌표를 받아서
                        var coords = new daum.maps.LatLng(result.y, result.x);
                        // 지도를 보여준다.
                        mapContainer.style.display = "block";
                        map.relayout();
                        // 지도 중심을 변경한다.
                        map.setCenter(coords);
                        // 마커를 결과값으로 받은 위치로 옮긴다.
                        marker.setPosition(coords)
                    }
                });
            }
        }).open();
    }
</script>
<!-- Daum 지도 API 끝 -->