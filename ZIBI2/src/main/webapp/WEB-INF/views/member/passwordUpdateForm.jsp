<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container form-width">
	<div class="member-form">
		<form:form action="passwordUpdate" id="update_password" modelAttribute="memberVO">
			<input type="hidden" name="mem_num" value="${user.mem_num}">
			<div class="row align-self-center">
				<div class="col-12 text-center">
					<h6>* 비밀번호는 영문 대소문자, 숫자 4자리에서 12자리 가능합니다</h6>
				</div>
				<div class="col-6">
					<form:label path="mem_password">비밀번호</form:label>
					<form:password path="mem_password" class="w-100 form-control" placeholder="영문 대소문자, 숫자 4자리에서 12자리 가능"/>
					<span id="password_area"></span>
				</div>
				<div class="col-6">
					<label for="c_password">비밀번호 확인</label>
					<input type="password" id="c_password" class="w-100 form-control"  placeholder="비밀번호와 동일하게 입력" >
				</div>
			</div>
			<form:errors element="span"/>
			<span id="cpassword_area"></span>
			<div class="row" style="margin-top: 30px;">
				<form:button class="btn mem-btn-green">비밀번호 변경</form:button>
			</div>
		</form:form>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {

		let password_checked = 0; //비밀번호 일치 체크 
		
		//비밀번호 일치 여부
		$('#c_password').keyup(function(){
			
			$('#memberVO\\.errors').text('');
			$('#cpassword_area').text('');
			password_checked = 0;
			
			if($('#mem_password').val()==$('#c_password').val()){
				$('#cpassword_area').text('비밀번호가 일치합니다');
				password_checked = 1;
			}
		});
		
		//submit 이벤트 발생 시 비밀번호 체크
		$('#update_password').submit(function(){
			if(password_checked==0){
				$('#password_area').text('비밀번호가 일치하지 않습니다');
				return false;
			}	
		});//end of submit
		
	});//end of function
</script>