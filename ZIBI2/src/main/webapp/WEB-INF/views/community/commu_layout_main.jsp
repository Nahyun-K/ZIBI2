<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ZIBI</title>
<style>@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap');</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/yeeun.css" rel="stylesheet">
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
</html>