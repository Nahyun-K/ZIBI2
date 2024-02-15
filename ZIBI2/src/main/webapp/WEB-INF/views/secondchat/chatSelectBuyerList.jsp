<!-- 거래자 선택 페이지 - 거래 완료 시 채팅한 사람 중 선택할 수 있게 페이지 이동됨 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	//구매자 선택 버튼 클릭 시
	$('#sc_selectBuyer').click(function(){
		let sc_num = $(this).attr('data-num');
		let buyer_num = $(this).attr('data-buyer_num');
		$.ajax({
			url: 'sc_selectBuyerAjax',
            type: 'post',
            data:{sc_num:sc_num,buyer_num:buyer_num},
            dataType: 'json',
            success: function(param) {
            	alert('거래 완료');
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
		});
	});
	
	
});
</script>
<!--  거래자 선택 목록 시작 -->
<div class="container-fluid menu py-6 my-6">
	<div class="container"
		style="max-width: 500px;">
		<div class="text-center">
			<h1 class="mb-5">거래자 선택</h1>
		</div>
		<div class="tab-class text-center">
			<div class="tab-content">
				<div id="tab-6" class="tab-pane fade show p-0 active">
					<c:if test="${count == 0}">
						<div class="result-display">표시할 채팅방이 없습니다.</div>
					</c:if>
					<c:if test="${count > 0}">
					<table class="basic-table2">
						<c:forEach var="chat" items="${list}">
						<tr>
							<td>
								<div class="row g-4">
									<div class="col-sm-12 mx auto">
										<div class="menu-item d-flex align-items-center">
											<img class="flex-shrink-0 img-fluid rounded-circle"
												src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${chat.buyer_num}"
												style="width: 90px;" alt="">
											<div class="w-100 d-flex flex-column text-start ps-4">
												<div class="d-flex justify-content-between border-bottom border-primary pb-2 mb-2">
													<h4>${chat.mem_nickname}</h4>
												</div>
												<p class="mb-0">${chat.chatVO.chat_message}</p>
												${chat.chatVO.chat_reg_date}
											</div>
											<span class="seller-read">${chat.read_count}</span>
											<input type="button" value="구매자 선택" class="selbuyer-btn1" id="sc_selectBuyer" data-num="${chat.sc_num}" data-buyer_num="${chat.buyer_num}"
												onclick="location.href='${pageContext.request.contextPath}/secondhand/detail?sc_num=${chat.sc_num}'">
										</div>
									</div>
								</div>
							</td>
							</tr>
						</c:forEach>
						</table>
						<!-- 거래자 선택하지 않는 버튼 클릭시 이미 이 페이지로 올때 second테이블에 update해줬으므로 바로 list로 가면 된다.  -->
						<input type="button" class="selbuyer-btn2" value="거래자 선택하지 않을래요" 
						onclick="location.href='${pageContext.request.contextPath}/secondhand/detail?sc_num=${sc_num}'">
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 거래자 선택 목록 끝 -->