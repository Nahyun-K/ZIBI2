<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
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
										<th scope="col">판매상태</th>
										<th scope="col">글제목</th>
										<th scope="col">가격</th>
										<th scope="col">동네</th>
										<th scope="col">판매자</th>
										<th scope="col">구매날짜</th>
										<th scope="col">채팅하기</th>
										<th scope="col">후기</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="second" items="${list}">
									<tr>
										<td>
											<c:if test="${second.secondVO.sc_filename ==null}">
												<a href="detail?sc_num=${second.sc_num}"><img width="60" src="${pageContext.request.contextPath}/images/jiwon/sc_mynoimg.png"></a>
											</c:if>
											<c:if test="${second.secondVO.sc_filename !=null}">
												<a href="detail?sc_num=${second.sc_num}"><img width="60" src="${pageContext.request.contextPath}/upload/${second.secondVO.sc_filename}"></a>	
											</c:if>
										</td>
										<td>
											<c:if test="${second.secondVO.sc_sellstatus ==0}">판매중</c:if>
											<c:if test="${second.secondVO.sc_sellstatus ==1}">예약대기</c:if>
											<c:if test="${second.secondVO.sc_sellstatus ==2}">예약중</c:if>
											<c:if test="${second.secondVO.sc_sellstatus ==3}">거래완료</c:if>
										</td>
										<td><a href="detail?sc_num=${second.sc_num}" class="sc-title-fav">${second.secondVO.sc_title}</a></td>
										<td>${second.secondVO.sc_price}</td>
										<td>${second.secondVO.sc_address}</td>
										<td>${second.mem_nickname}</td>
										<td>${second.sc_order_reg_date}</td>
										<td><input type="button" value="채팅하기" class="scstore-btn2"></td>
										<td><input type="button" value="후기" onclick="location.href='${pageContext.request.contextPath}/secondhand/secondReviewWrite?sc_num=${second.sc_num}'" class="store-md"></td>
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