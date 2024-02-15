<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid menu py-6">
	<div class="container">
		<div id="tab-6" class="tab-pane fade show p-0 active">
			<div class="row g-4">
				<div class="col-lg-12 wow" data-wow-delay="0.1s">
					<div class="menu-item d-flex align-items-center">
						<div class="w-100 d-flex flex-column text-start ps-4">
							<table class="table">
								<thead>
									<tr>
										<th scope="col">사진</th>
										<th scope="col">글제목</th>
										<th scope="col">판매자</th>
										<th scope="col">최근 채팅일</th>
										<th scope="col">채팅하기</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="second" items="${list}">
									<tr>
										<td>
											<c:if test="${second.sc_filename ==null}">
												<a href="detail?sc_num=${second.sc_num}"><img width="60" src="${pageContext.request.contextPath}/images/jiwon/sc_mynoimg.png"></a>
											</c:if>
											<c:if test="${second.sc_filename !=null}">
												<a href="detail?sc_num=${second.sc_num}"><img width="60" src="${pageContext.request.contextPath}/upload/${second.sc_filename}"></a>	
											</c:if>
										</td>
										<td><a href="detail?sc_num=${second.sc_num}" class="sc-title-fav">${second.sc_title}</a></td>
										<td>${second.mem_nickname}</td>
										<td>${second.chat_reg_date}</td>
										<td><input type="button" value="채팅하기" class="scstore-btn2"
											onclick="location.href='${pageContext.request.contextPath}/secondchat/chatListForBuyer?sc_num=${second.sc_num}'">
										</td>
									</tr>								
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>