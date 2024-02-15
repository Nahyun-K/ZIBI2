<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container"> 
	<div class="scmain-btn">
	<button type="button" class="sell-btn" onclick="location.href='write'">
		<img src="${pageContext.request.contextPath}/images/jiwon/sc_writelogo.png" width="22px">
			<span class="sell-txt separator1">판매하기</span>
	</button>
	<button type="button" class="sc-mypage-btn" onclick="location.href='secondsellList'">  <!-- 내상점의 판매 내역으로 감 -->
		<img src="${pageContext.request.contextPath}/images/jiwon/second_mypage.png" width="22px">
			<span class="scmypage-txt">내 상점</span>
	</button>
	</div>
	<div>
		<form action="list" id="search_form" method="get">
			<ul class="search">
				<li><input type="search" name="keyword" id="keyword" placeholder="상품명, 지역명, 내용 입력"
					value="${param.keyword}" class="p-2" autocomplete="off"></li>
				<li>
 					<input type="image" src="../images/jiwon/sc_searchicon.png" class="sc-btn-search">	
				</li>
			</ul>
			<div>
				<input type="button" value="목록" onclick="location.href='list'" class="sclist-btn">
			</div>
		</form>
		<c:if test="${count == 0}">
		<div class="result-display">표시할 게시물이 없습니다.</div>
		</c:if>
		<c:if test="${count > 0}">
    	    <div class="container">
            	<div class="text-left">
                	<h4 class="mainlist-title">중고거래 인기상품</h4>
            	</div>
            	<div class="row">
            		<c:forEach var="second" items="${list}">
                	<div class="col-lg-3 col-md-6 col-sm-12">
                    	<div class=" rounded">
                        	<div class="service-content d-flex align-items-center justify-content-center p-1">
                            	<div class="second-box text-left">
                            		<c:if test="${second.sc_sellstatus == 0}"><p style="background:#32a77b;" class="second-span4">판매중</p></c:if>
                            		<c:if test="${second.sc_sellstatus == 1}"><p style="background:#93C571;" class="second-span4">예약대기</p></c:if>
		                			<c:if test="${second.sc_sellstatus == 2}"><p style="background:#75B44A;" class="second-span4">예약중</p></c:if>
		                			<c:if test="${second.sc_sellstatus == 3}"><p style="background:#638E35;" class="second-span4">판매 완료</p></c:if>
                            		<a href="detail?sc_num=${second.sc_num}">
                            			<c:if test="${empty second.sc_filename}">
                            			<img src="${pageContext.request.contextPath}/images/jiwon/sc_noimg.png" id="thumb_noimg">
                            			</c:if>
                            			<c:if test="${!empty second.sc_filename}">
                            			<img src="${pageContext.request.contextPath}/upload/${second.sc_filename}" id="thumb_img">
                            			</c:if>
                                		<span id="main_title">${second.sc_title}</span>
                                		<br>
                               			<span id="main_price" class="mb-4"><b><fmt:formatNumber value="${second.sc_price}"/>원</b></span>
                               			<span id="main_regdate" class="mb-3 pb-3">${second.sc_reg_date}</span><br>
                                		
                                		<span id="main_address">
                                			<img src="${pageContext.request.contextPath}/images/logo_tab.png" width="30px;">
                                			${second.sc_address}
                                		</span>
                            		</a>
                            	</div>
                            	
                        	</div>
                    	</div>
                	</div>
                	</c:forEach>
                	<div class="second-page" style="margin-top:10px;">${page}</div>
            	</div>
        	</div>
    	</c:if>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
