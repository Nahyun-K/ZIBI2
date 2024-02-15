<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">
<title><tiles:getAsString name="tabtitle"/></title>
<style>@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap');</style>
<%-- title 아이콘 변경 --%>
<link rel="icon" href="${pageContext.request.contextPath}/images/logo_tab.png"/>
<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/images/logo_tab.png"/>

<!-- Icon Font Stylesheet -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
<!-- Libraries Stylesheet -->
<link href="${pageContext.request.contextPath}/sample/lib/animate/animate.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/sample/lib/owlcarousel/owl.carousel.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/sample/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<!-- Customized Bootstrap Stylesheet -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/jiwon.css" rel="stylesheet">

<tiles:insertAttribute name="css" ignore="true"/>
	

</head>
<body>
	<tiles:insertAttribute name="header"/>
	<div class="container-fluid nav-bar menu-title">
		<div class="container">
			<nav class="navbar navbar-expand-lg py-4">
				<div class="collapse navbar-collapse" id="navbarCollapse">
					<tiles:getAsString name="title"/>
				</div>
			</nav>
		</div>
	</div>
	<tiles:insertAttribute name="body"/>
	<tiles:insertAttribute name="footer"/> 
</body>
<!-- Template Javascript -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/sample/lib/wow/wow.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/sample/lib/easing/easing.min.js"></script>
	<script src="${pageContext.request.contextPath}/sample/lib/waypoints/waypoints.min.js"></script>
	<script src="${pageContext.request.contextPath}/sample/lib/counterup/counterup.min.js"></script>
	<script src="${pageContext.request.contextPath}/sample/lib/owlcarousel/owl.carousel.min.js"></script>
	<script src="${pageContext.request.contextPath}/sample/js/main.js"></script>
</html>