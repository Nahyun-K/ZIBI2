<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="admin-page policy-main text-center">
	<div class="text-end">
		<select id="year">
			<option value="0">연도 선택/직접 입력</option>
			<option value="2000">2000</option>
			<option value="2005">2005</option>
			<option value="2010">2010</option>
			<option value="2015">2015</option>
			<option value="2016">2016</option>
			<option value="2017">2017</option>
			<option value="2018">2018</option>
			<option value="2019">2019</option>
			<option value="2020">2020</option>
			<option value="2021">2021</option>
			<option value="2022">2022</option>
		</select>
		<input type="text" id="inputYear" placeholder="이외 년도">
		<input id="getData" class="btn mem-btn" type="button" value="1인 가구 통계 가져오기">
	</div>
	<c:if test="${count==0}">
		<div class="justify-content-center no-data">
			등록한 데이터가 없습니다
		</div>
	</c:if>
	<c:if test="${count>0}">
		<div class="row text-center">
			<div class="col-2">행정구역명</div>
			<div class="col-2">위도</div>
			<div class="col-2">경도</div>
			<div class="col-5">정책 사이트</div>
		</div>
		<c:forEach var="policyVO" items="${list}">
			<div class="row text-center">
				<div class="col-2">
					<a href="${pageContext.request.contextPath}/admin/policyModify?district_num=${policyVO.district_num}">[수정]</a>
					${policyVO.district_name}
				</div>
				<div class="col-2">${policyVO.district_latitude}</div>
				<div class="col-2">${policyVO.district_lonitude}</div>
				<div class="col-5">
					<a href="${policyVO.policy_url}">${policyVO.policy_url}</a>
				</div>
			</div>
		</c:forEach>
		<div class="text-center">${page}</div>
	</c:if>
</div>
<script type="text/javascript">
	$('#policy_btn').toggleClass("mem-btn");
	$('#policy_btn').toggleClass("mem-btn-green");	
	
	$('#getData').click(function(){ //년도 선택 이벤트
		
		let selectedYear = $('#year option:selected').val();
		
		if( !(($('#inputYear').val() && selectedYear==0) || selectedYear!=0) ){
			alert('연도를 선택하거나 입력해주세요.');
			return;
		}
		
		if( ($('#inputYear').val() && selectedYear==0) || selectedYear!=0 ){ //년도를 입력한 경우 select는 1이어야 함
			$.ajax({
				url: '/stats/getData', 
				type: 'post',
				data: {selectYear:selectedYear, inputYear:$('#inputYear').val() },
				dataType: 'json',
				success:function(param){
					if(param.result=="success"){
						alert('공공데이터 업데이트 성공');
					} else if(param.result=="failed"){
						alert('해당 연도의 공공데이터는 존재하지 않습니다');
					} else{
						alert('공공데이터 업데이트 오류');
					}
				},
				error:function(){
					alert('공공데이터 업데이트 네트워크 오류');
				}
			});
		} else{
			alert('년도를 입력한 경우 직접입력을 선택해주세요');
			return;
		}
	});
</script>