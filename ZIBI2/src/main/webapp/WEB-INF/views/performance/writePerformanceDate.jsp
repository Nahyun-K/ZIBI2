<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid event py-6">
	<div class="container">
		<!-- form태그를 사용하려면 먼저 Controller에서 modelAttribute부터 -->
		<form:form action="registerDate" modelAttribute="ticketingVO" enctype="multipart/form-data">
			<form:errors element="div" cssClass="error-color"/>
				<ul>
					<li class="form-style ticketInfo">
						<form:label path="performance_num">영화 선택</form:label>
						<form:select path="performance_num">
							<option value="">선택	</option>
							<c:forEach var="performance" items="${listPerformance}">
							<form:option value="${performance.performance_num}" label ="${performance.performance_title} ${performance.performance_start_date}" />
							</c:forEach>
						</form:select>
					</li>
					<li class="form-style ticketInfo">
						<form:label path="">지역 선택</form:label>
						<form:select path="" id="selectLoc1">
							<form:option value="" label ="선택" />
							<c:forEach var="cinema" items="${listCinemaLoc1}">
							<form:option value="${cinema.cinema_location1}" label ="${cinema.cinema_location1} ${performance.performance_start_date}" />
							</c:forEach>
						</form:select>
					</li>
					<li class="form-style ticketInfo">
						<form:label path="cinema_num">상영관 지역 선택</form:label>
						<form:select path="cinema_num"  id="selectLoc2">
							<option value="">선택	</option>
						</form:select>
					</li>
					<li class="form-style ticketInfo">
						<form:label path="ticketing_date">날짜</form:label>
						<form:input class="form-control" path="ticketing_date"/>
						<form:errors path="ticketing_date" cssClass="error-color"/>
					</li>
					<li class="form-style ticketInfo">
						<form:label path="ticketing_start_time">시간</form:label>
						<form:input class="form-control" path="ticketing_start_time"/>
						<form:errors path="ticketing_start_time" cssClass="error-color"/>
					</li>
				</ul>
				<div class="align-center">
					<form:button class="mem-btn-green py-2 px-4 d-none d-xl-inline-block rounded-pill submitBtn">전송</form:button>
					<input class="mem-btn-green py-2 px-4 d-none d-xl-inline-block rounded-pill submitBtn" type="button" value="목록" onclick="location.href='list'">
				</div>	
		</form:form>
		<br><br>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
// 장소 선택 -> 서울 -> 월드타워

$(function(){
	$('#selectLoc1').change(function(){
		var loc1  = document.getElementById("selectLoc1");
		var location1 = loc1.options[loc1.selectedIndex].value;
		//alert("value = "+ location1);
		
		$.ajax({
			url:'adminSelectLocation',
			type:'post',
			data:{location1:location1},
			dataType:'json',
			success:function(param){
				if(param.result=='success'){
					//alert("성공");
					//console.log(param.listLoc2);
					$('#selectLoc2').empty();
					let output = '<option value="">선택</option>';
					for(var i=0; i<param.listLoc2.length; i++){
						console.log(param.listLoc2[i].cinema_num);
						output += '<option value="'+param.listLoc2[i].cinema_num+'">'+param.listLoc2[i].cinema_location2 + ' ' + param.listLoc2[i].cinema_theater+'</option>';
					}
					$('#selectLoc2').append(output);
				}
			},
			error:function(){
				alert('네트워크 오류 발생');//
			}
		});
	})
});
</script>