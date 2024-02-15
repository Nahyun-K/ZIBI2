<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 내용 시작 -->
<div class="container">
<c:if test="${!empty user}">
<h4 id="match_title">🔔 나의 모임</h4>
<table id="match_table">
	<tr class="match-tr">
		<th>분류</th>
		<th>진행 상황</th>
		<th>일정</th>
		<th>장소</th>
		<th>모임명</th>
		<th>신청자</th>
		<th>내 활동</th>
	</tr>
	<c:if test="${mcount > 0}">
	<c:forEach var="mbook" items="${mlist}">
	<tr class="match-content">
		<td>
			<c:if test="${user.mem_num == mbook.mem_num}"><span style="color:#dd5c5e;font-weight:bold;">주최</span></c:if>
			<c:if test="${user.mem_num == mbook.apply_num}"><span style="color:#32a77b;font-weight:bold;">참여</span></c:if>
		</td>
		<td>
			<%-- 참여자 --%>
			<c:if test="${user.mem_num == mbook.apply_num && mbook.book_onoff != 2}">
				<c:if test="${mbook.book_state == 0}">
					<input type="button" value="참여 대기" disabled="disabled" class="btn-guide2">
				</c:if>
				<c:if test="${mbook.book_state == 1}">
					<input type="button" value="참여 확정" disabled="disabled" class="btn-guide2">
				</c:if>
				<c:if test="${mbook.book_state == 2}">
					<input type="button" value="참여 거절" disabled="disabled" class="btn-guide3">
				</c:if>
			</c:if>
			<%-- 주최자 --%>
			<c:if test="${user.mem_num != mbook.apply_num && mbook.book_onoff != 2}">
				<c:if test="${mbook.book_state == 0}">
					<input type="button" value="승인 필요" disabled="disabled" class="btn-guide2">
				</c:if>
				<c:if test="${mbook.book_state == 1}">
					<input type="button" value="승인 완료" disabled="disabled" class="btn-guide2">
				</c:if>
				<c:if test="${mbook.book_state == 2}">
					<input type="button" value="거절 완료" disabled="disabled" class="btn-guide3">
				</c:if>
			</c:if>
			<%-- 공통 --%>
			<c:if test="${mbook.book_onoff == 0 && mbook.compareNow == 2 && mbook.book_state != 2 && mbook.book_headcount < mbook.book_maxcount}">
				<input type="button" value="모집 중" disabled="disabled" class="btn-guide">
			</c:if>
			<c:if test="${mbook.book_onoff != 2 && mbook.compareNow == 1 && mbook.book_state != 2}">
				<input type="button" value="모임 확정" disabled="disabled" class="btn-guide">
			</c:if>
			<c:if test="${mbook.book_onoff == 2}">
				<input type="button" value="모임 취소" disabled="disabled" class="btn-guide4">
			</c:if>
			<c:if test="${(mbook.book_onoff == 3 && mbook.book_state != 2 && mbook.compareNow == 2)|| (mbook.book_headcount == mbook.book_maxcount && mbook.compareNow == 2 && mbook.book_state != 2)}">
				<input type="button" value="모집 완료" disabled="disabled" class="btn-guide">
			</c:if>
		</td>
		<td>
			${mbook.apply_gatheringDate}
		</td>
		<td>
			<c:set var="maddr" value="${mbook.apply_address1}"/>
			<c:if test="${maddr.startsWith('서울') || maddr.startsWith('경기')}">
				${fn:substring(maddr,0,6)}
            </c:if>
            <c:if test="${!maddr.startsWith('서울') && !maddr.startsWith('경기')}">
				${fn:substring(maddr,0,2)}
			</c:if>	
		</td>
		<td>
			<c:if test="${mbook.book_onoff != 2}">
				<a href="detail?book_num=${mbook.book_num}">
					<c:set var="mtitle" value="${mbook.apply_title}"/>
						${fn:substring(mtitle,0,13)}
					<c:if test="${fn:length(mtitle) > 13}">
						...
					</c:if>
				</a>
			</c:if>
			<c:if test="${mbook.book_onoff == 2}">
				<c:set var="mtitle" value="${mbook.apply_title}"/>
					${fn:substring(mtitle,0,13)}
				<c:if test="${fn:length(mtitle) > 13}">
					...
				</c:if>
			</c:if>
		</td>
		<td class="align-center">
			<c:if test="${user.mem_num == mbook.apply_num}">
				-
			</c:if>
			<c:if test="${user.mem_num != mbook.apply_num}">
				<a href="${pageContext.request.contextPath}/member/mypageOpen?mem_num=${mbook.apply_num}">
					${mbook.mem_nickname}
				</a>
			</c:if>
		</td>
		<td>
			<%-- 참여자 --%>
			<c:if test="${user.mem_num == mbook.apply_num && mbook.book_state != 2}">
				<c:if test="${mbook.compareNow == 2 && mbook.book_onoff == 0}">
					<input type="button" value="참여 취소하기" class="default-btn4 apply-cancel" 
						data-num="${mbook.book_num}" data-apply="${mbook.apply_num}">
				</c:if>
				<c:if test="${mbook.compareNow == 1 && mbook.book_state == 1 && mbook.rev_status == 1}">
					<input type="button" value="후기 작성하기" class="default-btn4" 
						onclick="location.href='review?book_num=${mbook.book_num}&apply_gatheringDate=${mbook.apply_gatheringDate}'">
				</c:if>
				<c:if test="${mbook.compareNow == 1 && mbook.book_state == 1 && mbook.rev_status == 2}">
					<span class="rev-complete">👏후기 작성 완료👏</span>
				</c:if>
			</c:if>
			<%-- 주최자 --%>
			<c:if test="${user.mem_num == mbook.mem_num && mbook.book_onoff == 0 && mbook.compareNow == 2}">
				<c:if test="${mbook.book_match == 2 && mbook.book_state == 0}">
					<input type="button" value="승인하기" class="default-btn3 apply-approve"
						data-num="${mbook.book_num}" data-apply="${mbook.apply_num}">
					<input type="button" value="거절하기" class="default-btn3 apply-deny"
						data-num="${mbook.book_num}" data-apply="${mbook.apply_num}">
				</c:if>
				<c:if test="${mbook.book_state != 2}">
					<input type="button" value="모임 취소하기" class="default-btn3 book-cancel"
						onclick="location.href='cancel?book_num=${mbook.book_num}'">
				</c:if>
			</c:if>
		</td>
	</tr>
	</c:forEach>
	</c:if>
	<c:if test="${mcount == 0}">
		<td colspan="7" class="align-center" style="padding:40px 0;">모임 예약 내역이 존재하지 않습니다.</td>
	</c:if>
</table>
<div class="book-page">${mpage}</div>
</c:if>
<form action="list" id="search_form" method="get">
	<ul class="search">
		<li>
 		<select name="keyfield" id="keyfield">
 			<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>제목
 			<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>작성자
 		</select>
		</li>
		<li>
			<input type="search" name="keyword" id="keyword" 
				value="${param.keyword}" class="w-50 p-2"
				autocomplete="off">
		</li>
		<li>
 			<input type="image" src="../images/jy/search-icon.png" class="btn-search">
 		</li>
	</ul>
	<div class="order">
		<select id="order" name="order">
			<option value="1" <c:if test="${param.order == 1}">selected</c:if>>최신순
			<option value="2" <c:if test="${param.order == 2}">selected</c:if>>리뷰순
			<option value="3" <c:if test="${param.order == 3}">selected</c:if>>스크랩순
			<option value="4" <c:if test="${param.order == 4}">selected</c:if>>마감임박순
		</select>
			<input type="button" value="목록으로" onclick="location.href='list'" class="default-btn2">
		<c:if test="${!empty user}">
			<input type="button" value="모임 만들기" onclick="location.href='write'" class="default-btn">
		</c:if>	
	</div>
</form>
<c:if test="${count == 0}">
<div class="result-display">표시할 게시물이 없습니다.</div>
</c:if>
<c:if test="${count > 0}">
<c:forEach var="book" items="${list}">
<ul class="book-ul">
	<li class="book-li">
		<div class="col-lg-3 col-md-6 col-sm-12">
		    <div class="book-section">
		        <div class="d-flex align-items-center justify-content-center p-7">
		            <div class="text-center">
		            	<div>
		                	<c:if test="${book.book_category == 0}"><div style="background:#0f4b43;" class="book-first">취미 소모임</div></c:if>
		                	<c:if test="${book.book_category == 1}"><div style="background:#5eaf08;" class="book-first">원데이 클래스</div></c:if>
		                	<c:if test="${book.book_category == 2}"><div style="background:#486627;" class="book-first">스터디 모임</div></c:if>
		                </div>
		                <div class="thumb-wrap">
		                 <div class="thumb-img" <c:if test="${book.book_onoff == 2}">style="opacity:20%;"</c:if>>
		             	<c:if test="${!empty book.book_thumbnailName}">
		             		<img src="${pageContext.request.contextPath}/upload/${book.book_thumbnailName}" class="book-img">
		             	</c:if>
		             	<c:if test="${empty book.book_thumbnailName}">
		             		<img src="${pageContext.request.contextPath}/images/jy/thumbnail_basic.png" class="book-img">
		             	</c:if>
		             	</div>
		             	<div class="thumb-cancel">
		             		<c:if test="${book.book_onoff == 2}">취소된<br>모임이에요🥲</c:if>
		             	</div>
		            	</div>                    
		                <div>
		                	<c:if test="${book.book_onoff != 2}">
		                		<a href="detail?book_num=${book.book_num}" class="a-style"><b>${book.book_title}</b></a>
		                	</c:if>
		                	<c:if test="${book.book_onoff == 2}">
		                		<b>${book.book_title}</b>
		                	</c:if>
		                </div>
		                <span class="book-second">후기 ${book.rev_cnt}</span>
		                <hr size="3" width="270px" class="align-center">
		                <div class="book-third">
		                	<c:if test="${!empty book.book_expense}">
		                		<fmt:formatNumber value="${book.book_expense}"/>원
		                	</c:if>
		                	<c:if test="${empty book.book_expense}">
		                		무료
		                	</c:if>
		                </div>
		                <div class="book-third">${book.book_headcount}/${book.book_maxcount}명</div>
		                <p>
		                <div class="align-right">
			                <c:set var="addr" value="${book.book_address1}"/>
			                <c:if test="${addr.startsWith('서울') || addr.startsWith('경기')}">
								${fn:substring(addr,0,6)}
							</c:if>
							<c:if test="${!addr.startsWith('서울') && !addr.startsWith('경기')}">
								${fn:substring(addr,0,2)}
							</c:if>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
	</li>	
</ul>	
</c:forEach>
<div class="book-page">${page}</div>	
</c:if>
<div style="clear:both;"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jy/book.apply.js"></script>
<script type="text/javascript">
$(function(){
	//검색 유효성 체크
	$('#search_form').submit(function(){
		if($('#keyword').val().trim()==''){
			$('.btn-search').attr('disabled');
			return false;
		}
	});
	
	$('#order').change(function(){
		location.href='list?keyfield='+$('#keyfield').val()+'&keyword='+$('#keyword').val()+'&order='+$('#order').val();
	});

});	
</script>
<!-- 내용 끝 -->