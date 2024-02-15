<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 내용 시작 -->
<div class="container">
	<div class="d-flex justify-content-center">
		<div class="rounded col-md-4 col-lg-6">
			<form action="cancel" id="book_cancel" method="post">
			<input type="hidden" value="${book.book_num}" name="book_num">
				<div class="title-phrase2">
					모임을 취소할래요!
				</div>
				<div class="applySub">
					취소 대상 모임
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
				<div style="margin-top:50px;" class="applySub">
					이메일 확인
				</div>
				<hr size="3" noshade="noshade" width="100%">
				<div>
					<label for="mem_email">이메일</label>
					<input type="email" id="mem_email" name="mem_email"
						class="w-100 form-control p-3" autocomplete="off">
					<span class="guide-phrase">*가입 시 기입한 이메일을 입력해 주세요.</span>	
					<div id="emailValid" class="error-phrase"></div>	
				</div>
				<div>
					<label for="cancelAgree">확인 사항</label>
					<input type="checkbox" id="cancelAgree" name="cancelAgree">
					<br>
					<span>
						모임 취소로 인한 불이익이 발생할 수 있다는 점을 인지하고 있으며<br>
						이와 관련한 책임은 'ZIBI'가 아닌 주최자 개인이 부담하는 것에 동의합니다.
					</span>
					<div id="cancelValid" class="error-phrase"></div>
				</div>
				<div class="align-center" style="margin-top:20px;">
				<input type="submit" value="모임 취소하기" class="w-25 btn btn-light form-control p-3 rounded-pill active">
				<input type="button" class="w-25 btn btn-light form-control p-3 rounded-pill active" 
					onclick="location.href='list'" value="목록으로">
				</div>	
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
window.onload = function(){
	//입력 유효성 체크
	$('#book_cancel').submit(function(){
		if($('#mem_email').val().trim()==''){
			$('#emailValid').text('@이 포함된 올바른 이메일 주소를 입력해 주세요.');
			return false;
		}else{
			$('#emailValid').text('');
		}
		
		if(!$('input[id="cancelAgree"]').is(":checked")){
			$('#cancelValid').text('확인 사항에 동의해 주세요.');
			return false;
		}else{
			$('#cancelValid').text('');
		}
	});
};
</script>
<!-- 내용 끝 -->