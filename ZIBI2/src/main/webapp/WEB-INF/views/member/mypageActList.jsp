<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="member-page">
	<div class="text-end mypage-category">
		<select id="category" class="selectbox" name="category">
			<option value="1" <c:if test="${param.category==1}">selected</c:if>>전체</option>
			<option value="2" <c:if test="${param.category==2}">selected</c:if>>커뮤니티</option>
			<option value="3" <c:if test="${param.category==3}">selected</c:if>>댓글</option>
			<option value="4" <c:if test="${param.category==4}">selected</c:if>>좋아요/스크랩</option>
			<option value="5" <c:if test="${param.category==5}">selected</c:if>>소모임 리뷰</option>
			<option value="6" <c:if test="${param.category==6}">selected</c:if>>중고거래 찜</option>
			<option value="7" <c:if test="${param.category==7}">selected</c:if>>중고거래 채팅</option>
		</select>
	</div>
	<c:if test="${count==0}">
		<div class="justify-content-center no-data">
			ZIBI에서 활동한 내역이 없습니다 😟
		</div>
	</c:if>
	<c:if test="${count>0}">
		<div class="mypage-list text-center">
			<div class="row">
				<div class="col-4">카테고리</div>
				<div class="col-8">제목</div>
			</div>
			<c:forEach var="act" items="${list}">
				<div class="row">
					<div class="col-4 text-center">
						<c:if test="${act.category==2}">커뮤니티</c:if>
						<c:if test="${act.category==3 && act.subCategory==1}">커뮤니티 댓글</c:if>
						<c:if test="${act.category==3 && act.subCategory==2}">소모임 댓글</c:if>
						<c:if test="${act.category==3 && act.subCategory==3}">재능기부 댓글</c:if>
						<c:if test="${act.category==4 && act.subCategory==1}">커뮤니티 좋아요</c:if>
						<c:if test="${act.category==4 && act.subCategory==2}">재능기부 스크랩</c:if>
						<c:if test="${act.category==4 && act.subCategory==3}">소모임 스크랩</c:if>
						<c:if test="${act.category==5}">소모임 리뷰</c:if>
					</div>
					<div class="col-8">
						<c:if test="${act.category==2}"><!-- 커뮤니티 -->
							<a href="${pageContext.request.contextPath}/community/detail?community_num=${act.num}">${act.title}</a>
						</c:if>
						<c:if test="${act.category==3 && act.subCategory==1}"><!-- 커뮤니티 댓글 -->
							<a href="${pageContext.request.contextPath}/community/detail?community_num=${act.num}">${act.title}</a>
						</c:if>
						<c:if test="${act.category==3 && act.subCategory==2}"><!-- 소모임 댓글 -->
							<a href="${pageContext.request.contextPath}/book/detail?book_num=${act.num}">${act.title}</a>
						</c:if>
						<c:if test="${act.category==3 && act.subCategory==3}"><!-- 재능기부 댓글 -->
							<a href="${pageContext.request.contextPath}/helper/detail?helper_num=${act.num}">${act.title}</a>
						</c:if>
						<c:if test="${act.category==4 && act.subCategory==1}"><!-- 커뮤니티 좋아요 -->
							<a href="${pageContext.request.contextPath}/community/detail?community_num=${act.num}">${act.title}</a>
						</c:if>
						<c:if test="${act.category==4 && act.subCategory==2}"><!-- 재능기부 스크랩 -->
							<a href="${pageContext.request.contextPath}/helper/detail?helper_num=${act.num}">${act.title}</a>
						</c:if>
						<c:if test="${act.category==4 && act.subCategory==3}"><!-- 소모임 스크랩 -->
							<a href="${pageContext.request.contextPath}/book/detail?book_num=${act.num}">${act.title}</a>
						</c:if>
						<c:if test="${act.category==5}"><!-- 소모임 리뷰 -->
							<a href="${pageContext.request.contextPath}/book/detail?book_num=${act.num}">${act.title}</a>
						</c:if>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="text-center">${page}</div>
	</c:if>
</div>
<script>
	$('#act_btn').toggleClass("mem-btn");
	$('#act_btn').toggleClass("mem-btn-green");
	
	//카테고리 선택 이벤트
	$('#category').change(function(){
		if($(this).val()==6){
			location.href = '/secondhand/secondfavList';
		} else if($(this).val()==7){
			location.href = '/secondhand/list';
		}else{
			location.href = 'mypageAct?category='+$('#category').val();
		}
	});//end of change
</script>