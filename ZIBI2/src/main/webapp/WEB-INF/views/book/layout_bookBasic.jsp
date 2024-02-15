<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta property="og:title" content="ZIBI">
	<meta property="og:url" content="https://11b8-58-76-175-14.ngrok-free.app/book/list">
	<meta property="og:image" content="https://firebasestorage.googleapis.com/v0/b/zibi-jy.appspot.com/o/logo(typo)_1x1_240112.png?alt=media&token=8afc4e06-80df-4f7d-8f15-f8548e603a72">
	<meta property="og:description" content="ZIBI 가입하고 소모임 함께 해요!">
	
	<title><tiles:getAsString name="tabtitle"/></title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/jy/owl.carousel.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/jy/owl.theme.default.min.css" rel="stylesheet">
	
	<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/jy/jy.css" rel="stylesheet">
	<style>@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap');</style>
	
	<%-- title 아이콘 변경 --%>
	<link rel="icon" href="${pageContext.request.contextPath}/images/logo_tab.png"/>
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/images/logo_tab.png"/>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<body style="overflow-x:hidden;">
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
	<a href="#" class="btn btn-light btn-md-square rounded-circle back-to-top">
		<img src="${pageContext.request.contextPath}/images/na/up_menu.png" width="15px">
	</a> 
</body>
</html>