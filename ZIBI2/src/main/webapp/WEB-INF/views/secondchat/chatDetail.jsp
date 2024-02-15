<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jiwon/message.chat.js"></script>
<div class="container" id="chatDetail">
	<div class="d-flex justify-content-center">
		<div class="rounded col-md-4 col-lg-6 contact-form" style="height:550px;">
			<div class="seller-title">
				<h3>${chatRoomVO.seller}</h3>
			</div>
			
			<div class="chat-iteminfo d-flex align-items-center">
					<img class="sc-photo rounded"
						src="${pageContext.request.contextPath}/upload/${chatRoomVO.sc_filename}" style="width:50px" alt="">
					<div class="w-100 d-flex flex-column text-start ps-4 mb-2">
						<div class="d-flex mt-2">
							<c:if test="${chatRoomVO.sc_sellstatus ==0}">판매중</c:if>
							<c:if test="${chatRoomVO.sc_sellstatus ==1}">예약대기</c:if>
							<c:if test="${chatRoomVO.sc_sellstatus ==2}">예약중</c:if>
							<c:if test="${chatRoomVO.sc_sellstatus ==3}">거래완료</c:if>
							<span class="separator3">${chatRoomVO.sc_title}</span>
							
						</div>
						<p class="mb-0"><fmt:formatNumber value="${chatRoomVO.sc_price}"/>원</p>
					</div>
				</div>
			
			<div>
				<div id="chatting_message"></div>
				<div class="send-box">
					<form method="post" id="chatdetail_form">
						<input type="hidden" name="chatroom_num" id="chatroom_num" value="${chatRoomVO.chatroom_num}">
						<textarea class="chat-message"
							rows="1" name="chat_message" id="chat_message"></textarea>
						<div id="message_btn">
							<button class="chat-send-btn" type="submit">
								<img src="${pageContext.request.contextPath}/images/jiwon/chat_send.png">
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>


