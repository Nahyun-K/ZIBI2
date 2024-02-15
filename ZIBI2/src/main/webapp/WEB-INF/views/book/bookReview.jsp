<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 내용 시작 -->
<div class="container">
	<div class="d-flex justify-content-center">
		<div class="rounded col-md-4 col-lg-6">
			<form action="review" id="book_review" method="post">
			<input type="hidden" value="${match.book_num}" name="book_num">
			<input type="hidden" value="${match.apply_gatheringDate}" name="apply_gatheringDate">
				<div class="title-phrase2">
					${match.mem_nickname}님은 이 모임 어떠셨나요?
				</div>
				<div class="applySub">
					참여 모임
				</div>
				<hr size="3" noshade="noshade" width="100%">
				<ul>
					<li class="applyTitle">
						${match.apply_title}
					</li>
					<li>
						${match.apply_gatheringDate}
					</li>
				</ul>
				<p>
				<div class="applySub" style="margin-bottom:11px !important">
					참여 후기
				</div>
				<div id="gradeValid" class="error-phrase" style="margin-left:5px;"></div>
				<fieldset class="rate">
	                <input type="radio" id="rating10" name="book_grade" value="5"><label for="rating10"></label>
	                <input type="radio" id="rating9" name="book_grade" value="4.5"><label class="half" for="rating9"></label>
	                <input type="radio" id="rating8" name="book_grade" value="4"><label for="rating8"></label>
	                <input type="radio" id="rating7" name="book_grade" value="3.5"><label class="half" for="rating7"></label>
	                <input type="radio" id="rating6" name="book_grade" value="3"><label for="rating6"></label>
	                <input type="radio" id="rating5" name="book_grade" value="2.5"><label class="half" for="rating5"></label>
	                <input type="radio" id="rating4" name="book_grade" value="2"><label for="rating4"></label>
	                <input type="radio" id="rating3" name="book_grade" value="1.5"><label class="half" for="rating3"></label>
	                <input type="radio" id="rating2" name="book_grade" value="1"><label for="rating2"></label>
	                <input type="radio" id="rating1" name="book_grade" value="0.5"><label class="half" for="rating1"></label>
                </fieldset>
				<textarea rows="5" id="book_rev" name="book_rev" placeholder="후기는 한 번 작성하면 수정, 삭제할 수 없으니 신중하게 작성해 주세요."></textarea>
				<div class="letter-count">100/100</div>
				<div id="revValid" class="error-phrase"></div>
				<div class="align-center">
					<input type="submit" value="후기 남기기" class="w-25 btn btn-light form-control p-3 rounded-pill active">
					<input type="button" class="w-25 btn btn-light form-control p-3 rounded-pill active" 
						onclick="location.href='list'" value="목록으로">
				</div>	
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jy/book.review.js"></script>
<!-- 내용 끝 -->