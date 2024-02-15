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
										<th scope="col">판매상태</th>
										<th scope="col">글제목</th>
										<th scope="col">가격</th>
										<th scope="col">동네</th>
										<th scope="col">최근수정일</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="second" items="${list}">
									<tr>
										<td><a href="detail?sc_num=${second.sc_num}"><img width="60" src="${pageContext.request.contextPath}/upload/${second.sc_filename}"></a></td>
										<td>
											<c:if test="${second.sc_sellstatus ==0}">판매중</c:if>
											<c:if test="${second.sc_sellstatus ==1}">예약대기</c:if>
											<c:if test="${second.sc_sellstatus ==2}">예약중</c:if>
											<c:if test="${second.sc_sellstatus ==3}">거래완료</c:if>
										</td>
										<td><a href="detail?sc_num=${second.sc_num}" class="sc-title-fav">${second.sc_title}</a></td>
										<td>${second.sc_price}</td>
										<td>${second.sc_address}</td>
										<td>
											<c:if test="${!empty second.sc_modify_date}">
												${second.sc_modify_date}
											</c:if>
											<c:if test="${empty second.sc_modify_date}">
												${second.sc_reg_date}
											</c:if>
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