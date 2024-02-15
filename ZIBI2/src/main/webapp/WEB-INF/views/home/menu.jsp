<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div class="container-fluid nav-bar menu-title">
	<div class="container">
		<nav class="navbar navbar-expand-lg py-4">
			<div class="collapse navbar-collapse" id="navbarCollapse">
				<tiles:getAsString name="title"/>
			</div>
		</nav>
	</div>
</div>