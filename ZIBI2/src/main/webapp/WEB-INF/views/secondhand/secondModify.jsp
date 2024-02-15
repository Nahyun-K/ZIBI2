<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        // Disable the "카테고리 선택" option
        $("#categorm_form option[value='']").prop("disabled", true);
    });
</script>


<div class="container">
	<div class="d-flex justify-content-center">
		<div class="rounded col-md-4 col-lg-6">
			<form:form action="update" id="sc_updateform" modelAttribute="secondVO" enctype="multipart/form-data">
				<form:hidden path="sc_num"/>
				<div>
					<form:label path="sc_title">제목</form:label>
					<form:input path="sc_title" class="w-100 form-control p-3" placeholder="제목"/>
					<form:errors path="sc_title" cssClass="error-color"/>
				</div>
				
				<div>
                    <form:label path="sc_category">카테고리</form:label>
                    <form:select path="sc_category" class="w-100 form-select p-3" id="categorm_form">
                    	<form:option value="0" disabled="disabled" label="카테고리 선택"/>
       					<form:option value="1" label="의류/액세서리" />
        				<form:option value="2" label="도서/티켓/문구" />
        				<form:option value="3" label="뷰티" />
   	 					<form:option value="4" label="전자기기" />
        				<form:option value="5" label="식품" />
        				<form:option value="6" label="기타" />
                    </form:select>
                </div>
				
				<div>
					<form:label path="sc_price">가격</form:label>
					<!-- <input type="number" id="sc_price" class="w-100 form-control p-3" placeholder="판매가격"> -->
					<form:input path="sc_price" class="w-100 form-control p-3" placeholder="판매가격"/> 
					<form:errors path="sc_price" cssClass="error-color"/>
				</div>
				<div>
					<form:label path="upload">썸네일 이미지</form:label>
					<br>
					<input type="file" name="upload" id="upload">
					<c:if test="${!empty secondVO.sc_filename}">
					<div id="sc_file_detail">(${secondVO.sc_filename})파일이 등록되어 있습니다.
						<input type="button" value="파일삭제" id="sc_file_del"> 
					</div>
					<script type="text/javascript">
						$(function(){
							$('#sc_file_del').click(function(){
								let choice = confirm('삭제하시겠습니까?');
								if(choice){
									$.ajax({
										url:'deleteFile',
										data:{sc_num:${secondVO.sc_num}},
										type:'post',
										dataType:'json',
										success:function(param){
											if(param.result == 'logout'){
												alert('로그인 후 사용하세요');
											}else if(param.result == 'success'){
												$('#sc_file_detail').hide();
											}else{
												alert('파일 삭제 오류 발생');
											}
										},
										error:function(){
											alert('네트워크 오류 발생');
										}
									});
								}
							});
						});
					</script>
					</c:if>
				</div>
				<div>
					<form:label path="sc_content">내용을 입력하세요</form:label>
					<form:textarea path="sc_content"/>
					<form:errors path="sc_content" cssClass="error-color"/>
					<script>
						function MyCustomUploadAdapterPlugin(editor){
							editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
								return new UploadAdapter(loader);
							}
						}
					
						ClassicEditor
							.create(document.querySelector('#sc_content'),{
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
			
				<div class="sc_status">
					<form:label path="sc_status">상품 상태</form:label>
					<br>
					<form:radiobutton path="sc_status" id="sc_status1" value="1"/><label for="sc_status1">중고</label>
					<form:radiobutton path="sc_status" id="sc_status2" value="2"/><label for="sc_status2">새상품</label>
				</div>
				<div class="sc_way">
					<form:label path="sc_way">거래 방법</form:label>
					<br>
					<form:radiobutton path="sc_way" id="sc_way1" value="1"/><label for="sc_way1">직거래</label>
					<form:radiobutton path="sc_way" id="sc_way2" value="2"/><label for="sc_way2">택배</label>
				</div>
				
				<!-- 거래 희망 장소 -->
				<form:label path="sc_address">거래 희망 장소</form:label>
				<input type="button" onclick="second_execDaumPostcode()" class="default-btn" value="주소 검색"><br>
				<form:input path="sc_address" placeholder="주소" class="w-100 form-control p-3 mb-1"/>
				<form:input path="sc_place" placeholder="거래 장소를 입력하세요" class="w-100 form-control p-3"/>
				
				<div id="clickLatlng"></div> 
				<div id="clickLatlng1"></div> 
				
				<div id="map" style="width:700px;height:300px;margin-top:10px;display:none"></div>
				<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
				<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2b2c8d108f8ba3676d57626832ac387e&libraries=services"></script>
				<script>
				    var mapContainer = document.getElementById('map'),
				        mapOption = {
				            center: new daum.maps.LatLng(37.537187, 127.005476),
				            level: 5
				        };
				
				    var map = new daum.maps.Map(mapContainer, mapOption);
				    var geocoder = new daum.maps.services.Geocoder();
				    var marker = new daum.maps.Marker({
				        position: new daum.maps.LatLng(37.537187, 127.005476),
				        map: map
				    });
				
				    var clickedCoords = null; //클릭된 좌표를 저장할 변수
				
				    daum.maps.event.addListener(map, 'click', function(mouseEvent) {
				        var coords = mouseEvent.latLng;
				
				        mapContainer.style.display = "block";
				        map.relayout();
				        map.setCenter(coords);
				        marker.setPosition(coords);
				
				        // 클릭된 좌표로 변수 update
				        clickedCoords = {
				            lat: coords.getLat(),
				            lng: coords.getLng()
				        };
				
				        // 좌표 업데이트
				        updateClickLatlng(clickedCoords);
				    });
				
				    function second_execDaumPostcode() {
				        new daum.Postcode({
				            oncomplete: function(data) {
				                var addr = data.address;
				
				                document.getElementById("sc_address").value = addr;
				
				                geocoder.addressSearch(data.address, function(results, status) {
				                    if (status === daum.maps.services.Status.OK) {
				                        var result = results[0];
				                        var coords = new daum.maps.LatLng(result.y, result.x);
				
				                        mapContainer.style.display = "block";
				                        map.relayout();
				                        map.setCenter(coords);
				                        marker.setPosition(coords);
				
				                        // Update the variable with address coordinates
				                        clickedCoords = {
				                            lat: coords.getLat(),
				                            lng: coords.getLng()
				                        };
				
				                        // Update the display with the coordinates
				                        updateClickLatlng(clickedCoords);
				                    }
				                });
				            }
				        }).open();
				    }
				    function updateClickLatlng(coords) {
				        // 좌표에서 주소 가져오기
				        geocoder.coord2Address(coords.lng, coords.lat, function(results, status) {
				            if (status === daum.maps.services.Status.OK) {
				                var address = results[0].address.address_name;
	
				                // id값에 주소 update
				                document.getElementById("sc_address").value = address;
				            }
				            
				            //sc_latitude 및 sc_longitude에 대한 hidden update
				            document.getElementById("sc_latitude").value = coords.lat;
				            document.getElementById("sc_longitude").value = coords.lng;
				            
				          	//좌표 출력   나중에 주석처리
				            var message = '현재 좌표의 위도는 ' + coords.lat + ' 이고, 경도는 ' + coords.lng + ' 입니다';
				            var resultDiv = document.getElementById('clickLatlng');
				            resultDiv.innerHTML = message;
				        });
				    }
				</script>
				<!-- 입력한 주소 지도 표시 끝 -->
				<form:hidden path="sc_latitude"/>    
				<form:hidden path="sc_longitude"/>
				<form:button class="w-100 btn btn-light form-control p-3 rounded-pill">상품 수정</form:button>
			</form:form>

		</div>
	</div>
</div>