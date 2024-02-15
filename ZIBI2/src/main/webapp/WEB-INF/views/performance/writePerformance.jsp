<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<div class="container-fluid event py-6">
	<div class="container">
		<!-- form태그를 사용하려면 먼저 Controller에서 modelAttribute부터 -->
		<form:form action="register" modelAttribute="performanceVO" enctype="multipart/form-data">
			<form:errors element="div" cssClass="error-color"/>
				<ul>
					<li class="form-style ticketInfo">
						<form:label path="performance_title">제목</form:label>
						<form:input class="form-control" path="performance_title"/>
						<form:errors path="performance_title" cssClass="error-color"/>
					</li>
					<li class="ticketInfo" style="list-style-type:none; width:50%;">
						<form:textarea path="performance_content"/>
						<form:errors path="performance_content" cssClass="error-color"/>
						<script>
						function MyCustomUploadAdapterPlugin(editor){
							editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
								return new UploadAdapter(loader);
							}
						}
						
						ClassicEditor
						    .create(document.querySelector('#performance_content'),{
						    	extraPlugins:[MyCustomUploadAdapterPlugin]
						    })
						    .then(editor => {
						    	window.editor = editor;
						    })
						    .catch(error => {
						    	console.error(error);
						    });					
					</script>
					</li>
					<li class="form-style ticketInfo"><!-- upload를 하려면 vo에 MultipartFile 변수를 만들어줘야 한다 (sql X) -->
						<form:label class="mem-btn-green" style="padding:5px 10px; border-radius: 10px;" path="upload">포스터</form:label>
						<input type="file" name="upload" id="upload">
					</li>
					<li class="form-style ticketInfo">
						<form:label path="performance_start_date">개봉일</form:label>
						<form:input class="form-control" path="performance_start_date"/>
						<form:errors path="performance_start_date" cssClass="error-color"/>
					</li>
					<li class="form-style ticketInfo">
						<form:label path="performance_age">상영등급</form:label>
						<form:input class="form-control" path="performance_age"/>
						<form:errors path="performance_age" cssClass="error-color"/>
					</li>
					<li class="form-style ticketInfo">
						<form:label path="performance_category">카테고리</form:label>
						<form:input class="form-control" path="performance_category"/>
						<form:errors path="performance_category" cssClass="error-color"/>
					</li>
				</ul>
				<div class="align-center">
					<form:button class="mem-btn-green py-2 px-4 d-none d-xl-inline-block rounded-pill submitBtn">전송</form:button> 
					<input type="button" value="목록" onclick="location.href='list'" class="mem-btn-green py-2 px-4 d-none d-xl-inline-block rounded-pill submitBtn">
				</div>	
		</form:form>
		<br><br>
	</div>

</div>
<!-- 
레이아웃
<div class="container-fluid contact py-6 wow bounceInUp" data-wow-delay="0.1s">
	<div class="container">
		<h2>좌석 선택</h2>
	
	</div>
</div>

 -->