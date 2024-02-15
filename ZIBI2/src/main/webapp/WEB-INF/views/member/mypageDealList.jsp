<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="member-page">
	<div class="text-end mypage-category">
		<select id="category" class="selectbox" name="category">
			<option value="1" <c:if test="${param.category==1}">selected</c:if>>전체</option>
			<option value="2" <c:if test="${param.category==2}">selected</c:if>>소모임</option>
			<option value="3" <c:if test="${param.category==3}">selected</c:if>>소모임 참여</option>
			<option value="4" <c:if test="${param.category==4}">selected</c:if>>재능 기부</option>
			<option value="5" <c:if test="${param.category==5}">selected</c:if>>영화 예매</option>
			<option value="6">중고 거래</option>
		</select>
	</div>
	<c:if test="${count==0}">
		<div class="justify-content-center no-data">
			등록한 거래가 없습니다 😟
		</div>
	</c:if>
	<c:if test="${count>0}">
		<div class="mypage-list text-center">
			<div class="row text-center">
				<div class="col-3">카테고리</div>
				<div class="col-5">제목</div>
				<div class="col-4">일시</div>
			</div>
			<c:forEach var="deal" items="${list}">
				<div class="row">
					<c:if test="${deal.category==2}">
						<div class="col-3 text-center">소모임</div>
					</c:if>
					<c:if test="${deal.category==3}">
						<div class="col-3 text-center">소모임 참여</div>
					</c:if>
					<c:if test="${deal.category==4}">
						<div class="col-3 text-center">재능 기부</div>
					</c:if>
					<c:if test="${deal.category==5}">
						<div class="col-3 text-center">영화 예매</div>
					</c:if>
					<div class="col-5">
						<c:if test="${deal.category==2}">
							<a href="${pageContext.request.contextPath}/book/detail?book_num=${deal.num}">${deal.title}</a>
						</c:if>
						<c:if test="${deal.category==3}">
							<a href="${pageContext.request.contextPath}/book/detail?book_num=${deal.num}">${deal.title}</a>
						</c:if>
						<c:if test="${deal.category==4}">
							<a href="${pageContext.request.contextPath}/helper/detail?helper_num=${deal.num}">${deal.title}</a>
						</c:if>
						<c:if test="${deal.category==5}">
							<a href="${pageContext.request.contextPath}detail?performance_num=${deal.num}">${deal.title}</a>
						</c:if>
					</div>
					<div class="col-4 text-center">${deal.reg_date}</div>
				</div>
			</c:forEach>
		</div>
		<div class="text-center">${page}</div>
	</c:if>
</div>
<script>
	$('#deal_btn').toggleClass("mem-btn");
	$('#deal_btn').toggleClass("mem-btn-green");

	//카테고리 선택 이벤트
	$('#category').change(function(){
		if($(this).val()==5){
			location.href = '/performance/history';
		} else if($(this).val()==6){
			location.href = '/secondhand/secondsellList';
		} else{
			location.href = 'mypageDeal?category='+$('#category').val();
		}
	});//end of change
	
</script>