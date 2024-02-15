<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- 내용 시작 -->
<link href="${pageContext.request.contextPath}/common/css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/yeeun.css" rel="stylesheet">
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>	
<script type="text/javascript">
	window.onload = function() {
		let community_maxcount = document.getElementById('community_maxcount');
		if (community_maxcount.value == 0) {
			community_maxcount.value = '';
		}
	};  
</script>
<div class="container">
	<div class="d-flex justify-content-center">
		<div class="rounded col-md-4 col-lg-6" style="width:800px;">
			<form:form action="write" id="community_write"
				modelAttribute="communityVO" enctype="multipart/form-data">
				<div>
					<h2>글 등록</h2>
					<div style="border-bottom:1px solid black;height=5px;"></div>
					<br>
					<form:label path="community_category">카테고리</form:label>
					<br>
					<form:select path="community_category">
						<form:option value="0" disabled="disabled" label="카테고리 선택"/>
						<form:option value="1">핫플레이스</form:option>
						<form:option value="2">부동산</form:option>
						<form:option value="3">취미</form:option>
						<form:option value="4">건강</form:option>
						<form:option value="5">육아</form:option>
					</form:select>
					<form:errors path="community_category" cssClass="error-phrase" />
				</div>
				<div>
					<form:label path="community_title">제목</form:label>
					<form:input path="community_title" class="w-100 form-control p-3" placeholder="제목을 입력하세요."/>
					<form:errors path="community_title" cssClass="error-phrase" />
				</div>
				<div>
					<form:label path="community_content">내용</form:label>
					<form:textarea path="community_content" />
					<form:errors path="community_content" cssClass="error-phrase" />
					<script>
						function MyCustomUploadAdapterPlugin(editor){
							editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
								return new UploadAdapter(loader);
							}
						}
					
						ClassicEditor
							.create(document.querySelector('#community_content'),{
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
				<div class="filebox">
					<form:label path="community_filename">파일</form:label>
					<br>
					<input type="file" id="community_filename" name="upload"
						accept="image/gif,image/png,image/jpeg">
					<form:errors path="community_filename" cssClass="error-phrase"/>	
					
					<br><br>
				</div>
				<div class="align-center" style="margin-top:20px;">
				<input type="submit" value="등록" class="comm-btn-green w-25"
					onclick="location.href='list'">
					<input type="button" value="목록"  class="check-btn w-25" onclick="location.href='list'">
				</div>
			</form:form>
		</div>
	</div>
</div>
<!-- 내용 끝 -->
