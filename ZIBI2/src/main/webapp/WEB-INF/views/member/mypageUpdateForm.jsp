<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container form-width">
	<div class="member-form">
		<form:form action="mypageUpdate" id="update_member" modelAttribute="memberVO">	
			<input type="hidden" name="mem_num" id="mem_num" value="${memberVO.mem_num}">
			<div class="row">
				<div class="col-12 text-center">
					<h6>* 이메일을 제외한 정보만 수정 가능합니다</h6>
				</div>
				<div class="col-6">
					<form:label path="mem_name">이름</form:label>
					<form:input path="mem_name" class=" form-control"/>
					<form:errors path="mem_name"/>
				</div>
				<div class="col-6">
					<form:label path="mem_nickname">닉네임</form:label>
					<form:input path="mem_nickname" class="form-control" placeholder="한글만 가능" autocomplete="off"/>
					<input type="button" class="btn mem-btn" value="중복체크" id="nickname_check" style="display: none;">
					<span id="nickname_area"></span>
					<form:errors path="mem_nickname"/>
				</div>
				<div class="col-6">
					<form:label path="mem_phone">연락처</form:label>
					<form:input path="mem_phone" class="form-control"/>
					<input type="button" class="btn mem-btn" value="중복체크" id="phone_check" style="display: none;">
					<span id="phone_area"></span>
					<form:errors path="mem_phone"/>
				</div>
				<div class="col-6">
					<form:label path="mem_zipcode">우편번호</form:label>
					<form:input path="mem_zipcode" class="input-check w-100 form-control" maxlength="5" autocomplete="off"/>
					<input type="button" class="btn mem-btn" value="찾기" id="zipcode_check" onclick="execDaumPostcode()">
					<span id="zipcode_area"></span>
					<form:errors path="mem_zipcode"/>
				</div>
				<div class="col-6">
					<form:label path="mem_address1">주소</form:label>
					<form:input path="mem_address1" class="form-control"/>
					<form:errors path="mem_address1"/>
				</div>
				<div class="col-6">
					<form:label path="mem_address2">상세 주소</form:label>
					<form:input path="mem_address2" class="form-control"/>
					<form:errors path="mem_address2"/>
				</div>
			</div>
			<div class="row" style="margin-top: 30px;">
				<form:button class="btn  mem-btn-green">회원 정보 수정</form:button>
			</div>
		</form:form>
	</div>
</div>
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
	<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/mypageUpdate.js"></script><!-- 유효성 체크 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/na/mypageZipcode.js"></script><!-- 우편번호 -->