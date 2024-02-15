<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<div class="container-fluid event py-6">
		<div class="container">
			<input type="button" value="영화 form" class="btn btn-primary py-2 px-4 d-none d-xl-inline-block rounded-pill"  onclick="location.href='write'"><br><br>
			<input type="button" value="상영관 form" class="btn btn-primary py-2 px-4 d-none d-xl-inline-block rounded-pill"  onclick="location.href='writeCinema'"><br><br>
			<input type="button" value="날짜 form" class="btn btn-primary py-2 px-4 d-none d-xl-inline-block rounded-pill"  onclick="location.href='writePerformanceDate'">
		</div>
	</div>
	</div>
<script type="text/javascript">
	$('#perf_btn').toggleClass("mem-btn");
	$('#perf_btn').toggleClass("mem-btn-green");
</script>