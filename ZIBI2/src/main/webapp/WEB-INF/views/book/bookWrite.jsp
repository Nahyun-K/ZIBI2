<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!-- 내용 시작 -->
<%-- daterangepicker --%>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<%-- CKEditor --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<div class="container">
	<div class="d-flex justify-content-center">
		<div class="rounded col-md-4 col-lg-6">
			<form:form action="write" id="book_write" 
					modelAttribute="bookVO" enctype="multipart/form-data">
				<div class="title-phrase">
					${mem_nickname}님의 소모임을<br>
					소개해 주세요!
				</div>	
				<div class="align-right">
					<h4>필수 입력사항</h4>
				</div>
				<hr size="3" noshade="noshade" width="100%">
				<div class="book_match">
					<form:label path="book_match">승인 여부</form:label>
					<br>
					<form:radiobutton path="book_match" id="book_match1"  value="1"/><label for="book_match1">바로 승인</label>
					<form:radiobutton path="book_match" id="book_match2" value="2"/><label for="book_match2">승인 필요</label>
					<br>
					<span class="guide-phrase">*승인 필요 체크 시 나의 예약 내역에서 신청자의 참여를 직접 승인해야 예약이 확정됩니다.</span>
					<br>
					<form:errors path="book_match" cssClass="error-phrase"/>
				</div>				
				<div>
					<form:label path="book_category">카테고리</form:label>
					<br>
					<form:select path="book_category">
						<form:option value="0">취미 소모임</form:option>
						<form:option value="1">원데이 클래스</form:option>
						<form:option value="2">스터디 모임</form:option>
					</form:select>
					<form:errors path="book_category" cssClass="error-phrase"/>
				</div>
				<div>
					<form:label path="book_title">제목</form:label>
					<form:input path="book_title" class="w-100 form-control p-3" autocomplete="off"/>
					<form:errors path="book_title" cssClass="error-phrase"/>
				</div>
				<div>
					<form:label path="book_gatheringDate">모임 일정</form:label>
					<form:input path="book_gatheringDate" class="w-100 form-control p-3" 
						placeholder="모임 날짜와 시간을 선택해 주세요!" autocomplete="off"/>
					<span class="guide-phrase">*입력창을 클릭해 모임 날짜와 시간을 선택하세요!</span>
					<br>	
					<form:errors path="book_gatheringDate" cssClass="error-phrase"/>
					<script>
					$("#book_gatheringDate").daterangepicker({
					    locale: {
					    "format": 'YYYY-MM-DD HH:mm',
					    "applyLabel": "확인",
					    "cancelLabel": "취소",
					    "daysOfWeek": ["일", "월", "화", "수", "목", "금", "토"],
					    "monthNames": ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
					    },
					    timePicker: true,
					    showDropdowns: true,
						minDate:moment().startOf('day'),
					    autoApply: true,
					    timePicker24Hour: true,
					    singleDatePicker: true
					}).on('cancel.daterangepicker',function(ev,picker){
						$(ev.currentTarget).val('');
					});
						 
					$("#book_gateringDate").on('show.daterangepicker', function (ev, picker) {
					    $(".yearselect").css("float", "left");
					    $(".monthselect").css("float", "right");
					    $(".cancelBtn").css("float", "right");
					});
					</script>
				</div>
				<div>
					<form:label path="book_address1">모임 장소</form:label>
					<input type="button" onclick="sample5_execDaumPostcode()" value="주소 찾기" class="default-btn">
					<form:input path="book_address1" class="w-100 form-control p-3" autocomplete="off"/>
					<form:errors path="book_address1" cssClass="error-phrase"/>
				</div>
				<div>
					<form:label path="book_address2">상세 주소</form:label>
					<form:input path="book_address2" class="w-100 form-control p-3" autocomplete="off"/>
					<form:errors path="book_address2" cssClass="error-phrase"/>
				</div>
				<div>
			       	<div id="map" style="width:500px;height:300px;margin-top:13px;display:none"></div>
			    </div>
				<div>
					<form:label path="book_maxcount">참여 인원</form:label>
					<form:input path="book_maxcount" class="w-100 form-control p-3"
						placeholder="최대 참여 인원을 알려주세요!" autocomplete="off"/>
					<form:errors path="book_maxcount" cssClass="error-phrase"/>	
				</div>
				<div>
					<form:label path="book_content">소개 글</form:label>
					<form:textarea path="book_content"/>
					<form:errors path="book_content" cssClass="error-phrase"/>
					<script>
						function MyCustomUploadAdapterPlugin(editor){
							editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
								return new UploadAdapter(loader);
							}
						}
					
						ClassicEditor
							.create(document.querySelector('#book_content'),{
								extraPlugins:[MyCustomUploadAdapterPlugin]
							})
							.then(editor => {
								window.editor = editor;
							})
							.catch(error => {
								console.error(error);
							});
					</script>
				</div>
				<div class="align-right" style="margin-top:50px;">
					<h4>선택 입력사항</h4>
				</div>
				<hr size="3" noshade="noshade" width="100%">
				<div class="filebox">
					<form:label path="book_thumbnailName">썸네일</form:label>
					<br>
					<input type="file" id="book_thumbnailName" name="upload"
						accept="image/gif,image/png,image/jpeg">
					<br>
					<span class="guide-phrase">
						*500 x 500px 또는 1:1 비율의 고화질 이미지를 권장하며 미선택 시 기본 이미지가 제공됩니다.
					</span>
				</div>
				<div>
					<form:label path="book_expense">참여 비용</form:label>
					<input type="number" id="book_expense" name="book_expense"
						class="w-100 form-control p-3" placeholder="비용이 발생하지 않는다면 넘어가도 좋아요!"
						autocomplete="off">
				</div>
				<div>
					<form:label path="book_kit">준비물</form:label>
					<form:input path="book_kit" class="w-100 form-control p-3" 
						placeholder="지참해야 하는 준비물이 있다면 알려주세요!  ex. 붓, 수채화 물감"
						autocomplete="off"/>
				</div>
				<div class="align-center" style="margin-top:20px;">
				<input type="submit" value="모임 만들기" class="w-25 btn btn-light form-control p-3 rounded-pill active">
				<input type="button" class="w-25 btn btn-light form-control p-3 rounded-pill active" 
					onclick="location.href='list'" value="목록으로">
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
                document.getElementById("book_address1").value = addr;
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
<script type="text/javascript">
window.onload = function(){
	//참여 인원 기본값 0 초기화
	let book_maxcount = document.getElementById('book_maxcount');
	if(book_maxcount.value == 0){
		book_maxcount.value = '';
	}
};
</script>