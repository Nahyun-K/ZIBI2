<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<!-- 거래자 목록 -->
<div class="container-fluid menu py-6 my-6">
	<div class="container" style="max-width: 500px; ">
		<div class="text-center">
			<h2 class="mb-5">구매자 목록</h2>
		</div>
		<div class="tab-class text-center">
			<div class="tab-content">
				<div id="tab-6" class="tab-pane fade show p-0 active">
				<c:if test="${count == 0}">
					<div class="result-display">표시할 채팅방이 없습니다.</div>
				</c:if>
				<c:if test="${count > 0}">
				<table class="basic-table">
					<c:forEach var="chat" items="${list}">
					<tr>
						<td>
							<div class=" row g-4">
								<div class="col-sm-12 mx auto">
									<div class="menu-item d-flex align-items-center">
										<img class="flex-shrink-0 img-fluid rounded-circle"
											src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${chat.buyer_num}"
											style="width: 90px;" alt="">
										<div class="w-100 d-flex flex-column text-start ps-4">
											<div class="d-flex justify-content-between border-bottom">
												<h4>${chat.mem_nickname}</h4>
											</div>
											<p class="mb-0">${chat.chatVO.chat_message}</p>
											${chat.chatVO.chat_reg_date}
										</div>
										<span class="seller-read">${chat.read_count}</span>
										<input type="button" value="채팅" class="chat-btn3"
											onclick="location.href='${pageContext.request.contextPath}/secondchat/chatDetail?chatroom_num=${chat.chatroom_num}'">
									</div>
								</div>
							</div>
						</td>
					</tr>
					</c:forEach>
					</table>
				</c:if>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 거래자 목록 끝 -->