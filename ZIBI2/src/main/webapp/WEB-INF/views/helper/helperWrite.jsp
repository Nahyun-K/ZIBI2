<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="${pageContext.request.contextPath}/css/de.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<!-- 내용 시작 -->
<div style="border:500px;">
	<div class="d-flex justify-content-center">
	<form:form action="write" modelAttribute="helperVO" id="register_form"
		enctype="multipart/form-data">
		<form:errors element="div"/>
		<h2>재능기부 글쓰기</h2>
		<hr size="3" noshade="noshade" width="100%">
		<div class="helper_select">
			<form:label path="helper_select" class="margin-bottom"><b>게시판</b></form:label><br>
			<form:radiobutton path="helper_select" id="helper_select1" value="1"/><label for="helper_select1">헬프미</label> 
			<form:radiobutton path="helper_select" id="helper_select2" value="2"/><label for="helper_select2">헬프유</label> 
			<br>
			<form:errors path="helper_select" cssClass="error-color"/>
		</div>
		<br>
		<div>
            <form:label path="helper_category" class="margin-bottom"><b>카테고리</b></form:label><br>
            <form:select path="helper_category" id="helper_category"><!-- class 넣어야함 -->
            	<form:option value="0" disabled="disabled" label="카테고리 선택"/>
       			<form:option value="1" label="벌레" />
				<form:option value="2" label="조립" />
				<form:option value="3" label="수리" />
				<form:option value="4" label="소분" />
				<form:option value="5" label="기타" />
			</form:select>
			<br>
			<form:errors path="helper_category" cssClass="error-color"/>
        </div>
        <br>
        
        <div>
       		<form:label path="helper_title"><b>제목</b></form:label> 
			<form:input path="helper_title" class="w-100 form-control p-3 margin-top" placeholder="제목을 입력하세요."/> 
			<form:errors path="helper_title" cssClass="error-color"/>
        </div>
        <br>
        <div>
        	<div class="margin-bottom"><b>내용</b></div>
        	<form:textarea path="helper_content"/> 
			<form:errors path="helper_content" cssClass="error-color"/> 
			<script type="text/javascript">
				function MyCustomUploadAdapterPlugin(editor){
					editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
						return new UploadAdapter(loader);
					}
				}
				
					ClassicEditor
						.create(document.querySelector('#helper_content'),{
							extraPlugins:[MyCustomUploadAdapterPlugin]
						})
						.then(editor => {
							window.editor = editor;
						})
						.catch(error => {
							console.error(error);
						})
				</script>
        </div>
        <br>
        <div> 
        	<form:label path="upload" value="첨부파일"><b>썸네일</b></form:label>
			<input type="file" name="upload" id="upload" 
					accept="image/gif,image/png,image/jpeg,image/jpg"><br>
			<span class="thum">※ 썸네일 사진 미첨부 시 기본 이미지가 표시됩니다.</span>
        </div>
        <br>
        <div>
        	<form:label path="helper_address1"><b>주소</b></form:label>
        	<input type="button" onclick="sample5_execDaumPostcode()" value="주소 검색" class="default-btn margin-address">
			<form:input path="helper_address1" class="w-80 form-control p-3"
				placeholder="도움장소를 입력하세요." id="sample5_address"/>
			<form:errors path="helper_address1" cssClass="error-color"/>
        </div>
        <br>
		<div>
			<form:label path="helper_address2"><b>상세주소</b></form:label>
			<form:input path="helper_address2" class="w-100 form-control p-3 margin-top"
				placeholder="상세주소는 선택사항입니다."/>
				<span class="thum">※ 개인정보 보호를 위해 지나치게 상세한 주소 입력은 권하지 않습니다.</span><br>
				<span class="thum">예시) 지비아파트 101동 801호(X) / 지비아파트 1단지(O)</span><br>
		</div>
		<br>
        <div>
        	<div id="map"></div>
        </div>
		<br>
	<div>
	</div>
		<div class="align-center">
			<form:button class="btn mem-btn-green w-25">글 등록</form:button>
			<input type="button" value="목록" class="btn mem-btn w-25 margin-left"
				onclick="location.href='list'">
		</div>
	</form:form>
</div>
</div>
<!-- 내용 끝 -->
<!-- Daum 지도 API -->
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
                document.getElementById("sample5_address").value = addr;
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