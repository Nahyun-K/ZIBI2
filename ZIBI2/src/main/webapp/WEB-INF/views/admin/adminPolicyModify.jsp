<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="page-width member-page policy-page">
	<h2>${district_name}</h2>
	<div class="row">
		<div class="col-12 text-center">
			<h4>[${policyVO.district_name}]</h4>
		</div>
	</div>
	<div class="row">
		<div class="col" id="map" style="width:100%;height:400px;"></div>
	</div>
	<div class="row">
		<div class="col-8">
			<input class="form-control" type="search" placeholder="지도에 표시할 행정구역을 입력해주세요" id="keyword">
		</div>
		<div class="col-4">
			<input class="btn mem-btn w-100" type="button" id="search_btn" value="검색">
		</div>
	</div>
	<form:form action="policyModifySubmit" modelAttribute="policyVO">
		<input type="hidden" value="${policyVO.district_name}" name="district_name" id="district_name">
		<input type="hidden" value="${policyVO.district_num}" name="district_num" id="district_num">
		<div class="row">
			<div class="col-6">
				<p>위도</p>
				<form:input path="district_latitude" class="form-control"/>
				<form:errors path="district_latitude"/>
			</div>
			<div class="col-6">
				<p>경도</p>
				<form:input path="district_lonitude" class="form-control"/>
				<form:errors path="district_lonitude"/>
			</div>
		</div>
		<div class="row">
			<div class="col-12">
				<p>정책 사이트 URL<small> (url 미등록 시 1인 가구 정책 사이트 페이지에 노출되지 않습니다)</small></p>
				<form:input path="policy_url" class="form-control"/>
				<form:errors path="policy_url"/>
			</div>
		</div>
		<div class="row">
			<form:button class="btn mem-btn-green">수정하기</form:button>
		</div>
	</form:form>
</div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakao_apikey}&libraries=services"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/adminKakaoMap.js"></script>
<script type="text/javascript">
	$('#policy_btn').toggleClass("mem-btn");
	$('#policy_btn').toggleClass("mem-btn-green");
</script>