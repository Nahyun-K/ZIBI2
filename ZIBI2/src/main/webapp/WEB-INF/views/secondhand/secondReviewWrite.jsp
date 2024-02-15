<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 거래 후기 작성 폼 -->
<div class="container">
	<div class="d-flex justify-content-center">
		<div class="rounded col-md-4 col-lg-6">
			<form action="secondReviewWrite" id="second_review" method="post">
			<input type="hidden" value="${second.sc_num}" name="sc_num">
				<div class="review-title">
					${second.mem_nickname}님과 거래가 어떠셨나요?
				</div>
				<div class="review-txt1">매너를 평가해주세요!</div>
				<div class="review-txt2">1개 이상 선택해주세요.</div>
				
				<fieldset class="rate">
                    <input type="radio" id="rating10" name="sc_rev_star" value="5"><label for="rating10" title="5점"></label>
                    <input type="radio" id="rating9" name="sc_rev_star" value="4.5"><label class="half" for="rating9" title="4.5점"></label>
                    <input type="radio" id="rating8" name="sc_rev_star" value="4"><label for="rating8" title="4점"></label>
                    <input type="radio" id="rating7" name="sc_rev_star" value="3.5"><label class="half" for="rating7" title="3.5점"></label>
                    <input type="radio" id="rating6" name="sc_rev_star" value="3"><label for="rating6" title="3점"></label>
                    <input type="radio" id="rating5" name="sc_rev_star" value="2.5"><label class="half" for="rating5" title="2.5점"></label>
                    <input type="radio" id="rating4" name="sc_rev_star" value="2"><label for="rating4" title="2점"></label>
                    <input type="radio" id="rating3" name="sc_rev_star" value="1.5"><label class="half" for="rating3" title="1.5점"></label>
                    <input type="radio" id="rating2" name="sc_rev_star" value="1"><label for="rating2" title="1점"></label>
                    <input type="radio" id="rating1" name="sc_rev_star" value="0.5"><label class="half" for="rating1" title="0.5점"></label>
                </fieldset>
				<div class="review-txt1">거래 경험을 알려주세요!</div>
				<div class="review-txt2">남겨주신 거래 후기는 상대방의 프로필에 공개돼요.</div>
					<textarea rows="5" name="sc_rev_content" id="sc_rev_content" placeholder="여기에 적어주세요.(선택사항)"></textarea>
					<div class="rev-notice1">
						<span class="rev-notice-icon">※</span>
						후기는 <span class="rev-redspan">수정 및 삭제가 불가능</span>하므로 신중히 작성해주세요.
					</div>
					<div class="rev-notice1">
						<span class="rev-notice-icon">※</span>
						허위 및 성의없는 내용을 작성할 경우, 서비스 이용이 제한될 수 있습니다.</div>
						<br>
					<div class="review-buttons">
						<input type="submit" value="후기 보내기" class="revwrite-btn btn btn-light w-25">
						<input type="button" value="목록으로" onclick="location.href='list'" class="revwrite-btn btn btn-light w-25">
					</div>
			</form>
		</div>
	</div>
</div>

<!-- Contact End -->