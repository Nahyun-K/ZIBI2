<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function() {
	//전체
    function SecondsellAll() {
        $.ajax({
            url: 'sc_sellAll',
            type: 'post',
            data: { mem_num: ${memberVO.mem_num} },
            dataType: 'json',
            success: function(param) {
                // 해당 탭의 tbody에 동적으로 생성한 내용 추가
                $('#tab-6 tbody').empty();

                $(param.sellAllList).each(function(index, item) {
                    let output = '<tr>';
                    output += '<td>';
                    if(item.sc_filename == null){
                    	output += '<a href="detail?sc_num='+item.sc_num +'"><img width="60" src="${pageContext.request.contextPath}/images/jiwon/sc_mynoimg.png"></a>';
                    }else{
                    	output += '<a href="detail?sc_num='+item.sc_num +'"><img width="60" src="${pageContext.request.contextPath}/upload/' + item.sc_filename + '"></a>';	
                    }
                    output += '</td>';
                    output += '<td>';
                    if (item.sc_sellstatus == 0) {
                        output += '판매중';
                    } else if (item.sc_sellstatus == 1) {
                        output += '예약대기';
                    } else if (item.sc_sellstatus == 2) {
                        output += '예약중';
                    } else if (item.sc_sellstatus == 3) {
                        output += '판매완료';
                    }
                    output += '</td>';
                    output += '<td><a href="detail?sc_num='+item.sc_num +'" class="sc-title-fav">' + item.sc_title + '</a></td>';
                    output += '<td>' + item.sc_price + '</td>';
                    output += '<td>';
                    output += item.sc_address != null ? item.sc_address : '지역 설정 안함';
                    output += '</td>';
                    output += '<td>';
                    if(item.sc_modify_date == null){
                    	output += item.sc_reg_date;
                    }else{
                    	output += item.sc_modify_date;
                    }
                    output += '</td>';
                    output += '<td><input type="button" value="채팅하기" class="scstore-btn2"';
                    output += 'onclick="location.href=\'${pageContext.request.contextPath}/secondchat/chatListForSeller?sc_num=' + item.sc_num + '\'"></td>';
                    output += '<td>';
                    if (item.sc_sellstatus != 0) {
                        output += '<input type="button" value="끌어올리기" class="sc_up" id="sc-up-disabled" disabled data-num="' + item.sc_num + '">';
                    } else {
                        buttonClass = "sc_up";
                        output += '<input type="button" value="끌어올리기" class="sc_up" id="sc_up3" data-num="' + item.sc_num + '">';
                    }
                    output += '</td>';
                    output += '<td>';
                    output += '<input type="button" value="수정" class="store-md" onclick="location.href=\'update?sc_num=' + item.sc_num + '\'"><br>';
                    output += '<input type="button" value="삭제" class="store-md" onclick="location.href=\'delete?sc_num=' + item.sc_num + '\'"><br>';
                    output += '<input type="button" value="숨기기" class="sc_hidein" data-num="' + item.sc_num + '" id="sc_hidein2">';
                    output += '</td>';
                    output += '</tr>';
		
                    // 해당 탭의 tbody에 동적으로 생성한 내용 추가
                    $('#tab-6 tbody').append(output);
                });
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
        });//end of ajax
    }//end of function
    
    //끌어올리기 - 등록일 sysdate로 update   (판매중인 게시글만 끌어올리기 가능)
    $(document).on('click', '.sc_up', function(){
    	let sc_num = $(this).attr('data-num');
    	 $.ajax({
 	        url: 'updateSysdate',
 	        type: 'post',
 	        data: {sc_num: sc_num},
 	        dataType: 'json',
 	        success: function(param) {
 	        	alert('최상단 UP하기 사용!')
 	        },
 	        error: function() {
 	            alert('네트워크 오류 발생');
 	        }
 	    });
    });
    
  	//숨기기 -  (전체, 판매중 부분만)
    $(document).on('click', '.sc_hidein', function(){
    	let sc_num = $(this).attr('data-num');
    	 $.ajax({
 	        url: 'updateScHide',
 	        type: 'post',
 	        data: {sc_num: sc_num},
 	        dataType: 'json',
 	        success: function(param) {
 	        	alert('게시글 숨김 처리되었습니다.')
 	        },
 	        error: function() {
 	            alert('네트워크 오류 발생');
 	        }
 	    });
    });
    
    //판매중
    $('#sc_forSale').click(function(){
    	$.ajax({
            url: 'sc_forSale',
            type: 'post',
            data: { mem_num: ${memberVO.mem_num}},
            dataType: 'json',
            success: function(param) {
            	// 해당 탭의 tbody에 동적으로 생성한 내용 추가
                $('#tab-7 tbody').empty();
            	
                $(param.forSaleList).each(function(index, item) {
                    let output = '<tr>';
                    output += '<td>';
                    if(item.sc_filename == null){
                    	output += '<a href="detail?sc_num='+item.sc_num +'"><img width="60" src="${pageContext.request.contextPath}/images/jiwon/sc_mynoimg.png"></a>';
                    }else{
                    	output += '<a href="detail?sc_num='+item.sc_num +'"><img width="60" src="${pageContext.request.contextPath}/upload/' + item.sc_filename + '"></a>';	
                    }
                    
                    output += '</td>';
                    output += '<td>';
                    if (item.sc_sellstatus == 0) {
                        output += '판매중';
                    } else if (item.sc_sellstatus == 1) {
                        output += '예약대기';
                    } else if (item.sc_sellstatus == 2) {
                        output += '예약중';
                    } else if (item.sc_sellstatus == 3) {
                        output += '판매완료';
                    }
                    output += '</td>';
                    output += '<td><a href="detail?sc_num='+item.sc_num +'" class="sc-title-fav">' + item.sc_title + '</a></td>';
                    output += '<td>' + item.sc_price + '</td>';
                    output += '<td>';
                    output += item.sc_address != null ? item.sc_address : '지역 설정 안함';
                    output += '</td>';
                    output += '<td>';
                    if(item.sc_modify_date == null){
                    	output += item.sc_reg_date;
                    }else{
                    	output += item.sc_modify_date;
                    }
                    output += '</td>';
                    output += '<td><input type="button" value="채팅하기" class="scstore-btn2"';
                    output += 'onclick="location.href=\'${pageContext.request.contextPath}/secondchat/chatListForSeller?sc_num=' + item.sc_num + '\'"></td>';
                    output += '<td><input type="button" value="끌어올리기" class="sc_up" data-num="'+item.sc_num+'" id="sc_up4"></td>';
                    output += '<td>';
                    output += '<input type="button" value="수정" onclick="location.href=\'update?sc_num=' + item.sc_num + '\'" class="store-md"><br>';
                    output += '<input type="button" value="삭제" onclick="location.href=\'delete?sc_num=' + item.sc_num + '\'" class="store-md"><br>';
                    output += '<input type="button" value="숨기기" class="sc_hidein" data-num="' + item.sc_num + '" id="sc_hidein4">';
                    output += '</td>';
                    output += '</tr>';
		
                    // 해당 탭의 tbody에 동적으로 생성한 내용 추가
                    $('#tab-7 tbody').append(output);
                });
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
    	});
    });
    
    
    //예약 대기
    $('#sc_waitReserve').click(function(){
    	$.ajax({
            url: 'sc_waitReserve',
            type: 'post',
            data: { mem_num: ${memberVO.mem_num} },
            dataType: 'json',
            success: function(param) {
            	// 해당 탭의 tbody에 동적으로 생성한 내용 추가
                $('#tab-8 tbody').empty();
            	
                $(param.waitReserveList).each(function(index, item) {
                    let output = '<tr>';
                    output += '<td>';
                    if(item.sc_filename == null){
                    	output += '<a href="detail?sc_num='+item.sc_num +'"><img width="60" src="${pageContext.request.contextPath}/images/jiwon/sc_mynoimg.png"></a>';
                    }else{
                    	output += '<a href="detail?sc_num='+item.sc_num +'"><img width="60" src="${pageContext.request.contextPath}/upload/' + item.sc_filename + '"></a>';	
                    }
                    output += '</td>';
                    output += '<td>';
                    if (item.sc_sellstatus == 0) {
                        output += '판매중';
                    } else if (item.sc_sellstatus == 1) {
                        output += '예약대기';
                    } else if (item.sc_sellstatus == 2) {
                        output += '예약중';
                    } else if (item.sc_sellstatus == 3) {
                        output += '판매완료';
                    }
                    output += '</td>';
                    output += '<td><a href="detail?sc_num='+item.sc_num +'" class="sc-title-fav">' + item.sc_title + '</a></td>';
                    output += '<td>' + item.sc_price + '</td>';
                    output += '<td>';
                    output += item.sc_address != null ? item.sc_address : '지역 설정 안함';
                    output += '</td>';
                    output += '<td>';
                    output += item.sc_order_reg_date != null ? item.sc_order_reg_date : 'X';
                    output += '</td>';
                    output += '<td>';
                    output += item.sc_buyer_nickname != null ? item.sc_buyer_nickname : 'X';
                    output += '</td>';
                    output += '<td><input type="button" value="채팅하기" class="scstore-btn2"';
                    output += 'onclick="location.href=\'${pageContext.request.contextPath}/secondchat/chatListForSeller?sc_num=' + item.sc_num + '\'"></td>';
                    output += '<td>';
                    if(item.sc_buyer_nickname!=null){ // 구매자가 있다면 버튼 클릭 가능 
                    	output += '<input type="button" id="updateOrderReserve" value="예약 확정" data-num="'+item.sc_num+'" class="reserve-okno"><br>';
                        output += '<input type="button" value="예약 거절" data-num="'+item.sc_num+'" id="updateOrderReject" class="reserve-okno"><br>';	
                    }else{ // 구매자가 없다면 버튼 클릭 불가능
                    	output += '<input type="button" id="updateOrderReserve" value="예약 확정" data-num="'+item.sc_num+'" class="screserve-disabled" disabled><br>';
                        output += '<input type="button" value="예약 거절" data-num="'+item.sc_num+'" id="updateOrderReject" class="screserve-disabled" disabled><br>';
                    }
                    output += '</td>';
                    output += '</tr>';
		
                    // 해당 탭의 tbody에 동적으로 생성한 내용 추가
                    $('#tab-8 tbody').append(output);
                });
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
    	});
    });
    //예약 대기 페이지에서 예약 확정 클릭 시 ajax   - 동적
    $(document).on('click', '#updateOrderReserve', function(){
	    let sc_num = $(this).attr('data-num');
	    $.ajax({
	        url: 'updateOrderReserve',
	        type: 'post',
	        data: {sc_num: sc_num},
	        dataType: 'json',
	        success: function(param) {
	            alert('예약이 확정되었습니다.');
	        },
	        error: function() {
	            alert('네트워크 오류 발생');
	        }
	    });
	});
    
    //예약 대기 페이지에서 예약 거절 클릭시 ajax - 동적
    $(document).on('click', '#updateOrderReject', function(){
	    let sc_num = $(this).attr('data-num');
	    $.ajax({
	        url: 'updateOrderReject',
	        type: 'post',
	        data: {sc_num: sc_num},
	        dataType: 'json',
	        success: function(param) {
	            alert('예약이 거절되었습니다.');
	        },
	        error: function() {
	            alert('네트워크 오류 발생');
	        }
	    });
	});
    
    
    //예약 중
    $('#sc_reserve').click(function(){
    	$.ajax({
            url: 'sc_reserve',
            type: 'post',
            data: { mem_num: ${memberVO.mem_num} },
            dataType: 'json',
            success: function(param) {
            	// 해당 탭의 tbody에 동적으로 생성한 내용 추가
                $('#tab-9 tbody').empty();
            	
                $(param.reserveList).each(function(index, item) {
                    let output = '<tr>';
                    output += '<td>';
                    if(item.sc_filename == null){
                    	output += '<a href="detail?sc_num='+item.sc_num +'"><img width="60" src="${pageContext.request.contextPath}/images/jiwon/sc_mynoimg.png"></a>';
                    }else{
                    	output += '<a href="detail?sc_num='+item.sc_num +'"><img width="60" src="${pageContext.request.contextPath}/upload/' + item.sc_filename + '"></a>';	
                    }
                    output += '</td>';
                    output += '<td>';
                    if (item.sc_sellstatus == 0) {
                        output += '판매중';
                    } else if (item.sc_sellstatus == 1) {
                        output += '예약대기';
                    } else if (item.sc_sellstatus == 2) {
                        output += '예약중';
                    } else if (item.sc_sellstatus == 3) {
                        output += '판매완료';
                    }
                    output += '</td>';
                    output += '<td><a href="detail?sc_num='+item.sc_num +'" class="sc-title-fav">' + item.sc_title + '</a></td>';
                    output += '<td>' + item.sc_price + '</td>';
                    output += '<td>';
                    output += item.sc_address != null ? item.sc_address : '지역 설정 안함';
                    output += '</td>';
                    output += '<td>';
                    if(item.sc_modify_date == null){
                    	output += item.sc_reg_date;
                    }else{
                    	output += item.sc_modify_date;
                    }
                    output += '</td>';
                    output += '<td>';
                    output += item.sc_buyer_nickname != null ? item.sc_buyer_nickname : 'X';
                    output += '</td>';
                    output += '<td><input type="button" value="채팅하기" class="scstore-btn2"';
                    output += 'onclick="location.href=\'${pageContext.request.contextPath}/secondchat/chatListForSeller?sc_num=' + item.sc_num + '\'"></td>';
                    output += '<td>';
                    output += '<input type="button" value="판매완료하기" id="updateOrderFini" class="sellfini-btn" data-num="'+item.sc_num+'"><br>';
                    output += '<input type="button" value="수정" onclick="location.href=\'update?sc_num=' + item.sc_num + '\'" class="store-md">';
                    output += '</td>';
                    output += '</tr>';
		
                    // 해당 탭의 tbody에 동적으로 생성한 내용 추가
                    $('#tab-9 tbody').append(output);
                });
            	
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
    	});
    });
    
  	//예약중 페이지에서 판매완료하기 클릭 시 ajax   - 동적
    $(document).on('click', '#updateOrderFini', function(){
	    let sc_num = $(this).attr('data-num');
	    $.ajax({
	        url: 'updateOrderFini',
	        type: 'post',
	        data: {sc_num: sc_num},
	        dataType: 'json',
	        success: function(param) {
	            alert('판매 완료 처리되었습니다.');
	        },
	        error: function() {
	            alert('네트워크 오류 발생');
	        }
	    });
	});
    
    //판매완료
    $('#sc_sellFin').click(function(){
    	$.ajax({
            url: 'sc_sellFin',
            type: 'post',
            data: { mem_num: ${memberVO.mem_num} },
            dataType: 'json',
            success: function(param) {
            	// 해당 탭의 tbody에 동적으로 생성한 내용 추가
                $('#tab-10 tbody').empty();
            	
                $(param.sellFinList).each(function(index, item) {
                    let output = '<tr>';
                    output += '<td>';
                    if(item.sc_filename == null){
                    	output += '<a href="detail?sc_num='+item.sc_num +'"><img width="60" src="${pageContext.request.contextPath}/images/jiwon/sc_mynoimg.png"></a>';
                    }else{
                    	output += '<a href="detail?sc_num='+item.sc_num +'"><img width="60" src="${pageContext.request.contextPath}/upload/' + item.sc_filename + '"></a>';	
                    }
                    output += '</td>';
                    output += '<td>';
                    if (item.sc_sellstatus == 0) {
                        output += '판매중';
                    } else if (item.sc_sellstatus == 1) {
                        output += '예약대기';
                    } else if (item.sc_sellstatus == 2) {
                        output += '예약중';
                    } else if (item.sc_sellstatus == 3) {
                        output += '판매완료';
                    }
                    output += '</td>';
                    output += '<td><a href="detail?sc_num='+item.sc_num +'" class="sc-title-fav">' + item.sc_title + '</a></td>';
                    output += '<td>' + item.sc_price + '</td>';
                    output += '<td>';
                    output += item.sc_address != null ? item.sc_address : '지역 설정 안함';
                    output += '</td>';
                    output += '<td>';
                    if(item.sc_modify_date == null){
                    	output += item.sc_reg_date;
                    }else{
                    	output += item.sc_modify_date;
                    }
                    output += '</td>';
                    output += '<td>';
                    output += item.sc_buyer_nickname != null ? item.sc_buyer_nickname : 'X';
                    output += '</td>';
                    output += '<td><input type="button" value="채팅하기" class="scstore-btn2"';
                    output += 'onclick="location.href=\'${pageContext.request.contextPath}/secondchat/chatListForSeller?sc_num=' + item.sc_num + '\'"></td>';
                    output += '<td>';
                    output += '<input type="button" value="수정" onclick="location.href=\'update?sc_num=' + item.sc_num + '\'" class="store-md"><br>';
                    output += '<input type="button" value="삭제" onclick="location.href=\'delete?sc_num=' + item.sc_num + '\'" class="store-md"><br>';
                    output += '<input type="button" value="숨기기" class="sc_hidein" data-num="' + item.sc_num + '" id="sc_hidein3">';
                    output += '</td>';
                    output += '</tr>';
		
                    // 해당 탭의 tbody에 동적으로 생성한 내용 추가
                    $('#tab-10 tbody').append(output);
                });
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
    	});
    });
    
  	//숨김
    $('#sc_hide').click(function(){
    	$.ajax({
            url: 'sc_hide',
            type: 'post',
            data: { mem_num: ${memberVO.mem_num} },
            dataType: 'json',
            success: function(param) {
            	// 해당 탭의 tbody에 동적으로 생성한 내용 추가
                $('#tab-11 tbody').empty();
            	
                $(param.sellFinList).each(function(index, item) {
                    let output = '<tr>';
                    output += '<td>';
                    if(item.sc_filename == null){
                    	output += '<a href="detail?sc_num='+item.sc_num +'"><img width="60" src="${pageContext.request.contextPath}/images/jiwon/sc_mynoimg.png"></a>';
                    }else{
                    	output += '<a href="detail?sc_num='+item.sc_num +'"><img width="60" src="${pageContext.request.contextPath}/upload/' + item.sc_filename + '"></a>';	
                    }
                    output += '</td>';
                    output += '<td>';
                    if (item.sc_sellstatus == 0) {
                        output += '판매중';
                    } else if (item.sc_sellstatus == 1) {
                        output += '예약대기';
                    } else if (item.sc_sellstatus == 2) {
                        output += '예약중';
                    } else if (item.sc_sellstatus == 3) {
                        output += '판매완료';
                    }
                    output += '</td>';
                    output += '<td><a href="detail?sc_num='+item.sc_num +'" class="sc-title-fav">' + item.sc_title + '</a></td>';
                    output += '<td>' + item.sc_price + '</td>';
                    output += '<td>';
                    output += item.sc_address != null ? item.sc_address : '지역 설정 안함';
                    output += '</td>';
                    output += '<td>';
                    if(item.sc_modify_date == null){
                    	output += item.sc_reg_date;
                    }else{
                    	output += item.sc_modify_date;
                    }
                    output += '</td>';
                    output += '<td>';
                    output += '<input type="button" value="숨기기 해제" class="sc_hideout" data-num="'+item.sc_num+'" id="sc_hidein1">';
                    output += '</td>';
                    output += '</tr>';
		
                    // 해당 탭의 tbody에 동적으로 생성한 내용 추가
                    $('#tab-11 tbody').append(output);
                });
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
    	});
    });
    
  	//숨기기 해제 클릭 시 - sc_num에 해당하는 게시글 sc_show=2로 update
    $(document).on('click', '.sc_hideout', function(){
    	let sc_num = $(this).attr('data-num');
    	 $.ajax({
 	        url: 'updateScShow',
 	        type: 'post',
 	        data: {sc_num: sc_num},
 	        dataType: 'json',
 	        success: function(param) {
 	        	alert('게시글이 공개되었습니다.')
 	        },
 	        error: function() {
 	            alert('네트워크 오류 발생');
 	        }
 	    });
    });
  	
  	
    //페이지 들어올 때 자동으로 ajax 실행된다("전체"가 먼저 실행되서 목록에 보여진다)
    SecondsellAll();

    //"전체"클릭 시 ajax 실행된다
    $('#sc_sellAll').click(function() {
        SecondsellAll();
    });
});
</script>
    
<!-- Menu Start -->
<div class="container-fluid menu py-6">
    <div class="container">
        <div class="tab-class text-center">
            <ul class="nav nav-pills d-inline-flex justify-content-center mb-5">
            	<!-- 전체 -->
                <li class="nav-item p-2">
                    <a id="sc_sellAll" class="d-flex py-2 mx-2  rounded-pill active" 
                     	data-bs-toggle="pill" href="#tab-6">
                        <span class="text-dark" style="width: 150px;">전체</span>
                    </a>
                </li>
                <!-- 판매중 -->
                <li class="nav-item p-2">
                    <a id="sc_forSale" class="d-flex py-2 mx-2 rounded-pill" 
                    	 data-bs-toggle="pill" href="#tab-7">
                        <span class="text-dark" style="width: 150px;">판매중</span>
                    </a>
                </li>
                <!-- 예약대기 -->
                <li class="nav-item p-2">
                    <a id="sc_waitReserve" class="d-flex py-2 mx-2 rounded-pill" 
                    	data-bs-toggle="pill" href="#tab-8">
                        <span class="text-dark" style="width: 150px;">예약대기</span>
                    </a>
                </li>
                <!-- 예약중 -->
                <li class="nav-item p-2">
                    <a id="sc_reserve" class="d-flex py-2 mx-2 rounded-pill" data-bs-toggle="pill" href="#tab-9">
                        <span class="text-dark" style="width: 150px;">예약중</span>
                    </a>
                </li>
                <!-- 거래완료 -->
                <li class="nav-item p-2">
                    <a id="sc_sellFin" class="d-flex py-2 mx-2  rounded-pill" data-bs-toggle="pill" href="#tab-10">
                        <span class="text-dark" style="width: 150px;">판매완료</span>
                    </a>
                </li>
                <!-- 숨김 -->
                <li class="nav-item p-2">
                    <a id="sc_hide" class="d-flex py-2 mx-2  rounded-pill" data-bs-toggle="pill" href="#tab-11">
                        <span class="text-dark" style="width: 150px;">숨김</span>
                    </a>
                </li>
            </ul>
            <div class="tab-content">
            	<!-- 전체 -->
                <div id="tab-6" class="tab-pane fade show p-0 active">
                    <div class="row g-4">
                        <div class="col-lg-12">
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
										    <th scope="col">최근작성일</th>
										    <th scope="col">채팅하기</th>
										    <th scope="col">끌어올리기</th>
										    <th scope="col">기능</th>
										  </tr>
										</thead>
										<tbody>
										  
										</tbody>
									</table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <!-- 판매중 -->
                <div id="tab-7" class="tab-pane fade show p-0">
                    <div class="row g-4">
                        <div class="col-lg-12">
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
										    <th scope="col">최근작성일</th>
										    <th scope="col">채팅하기</th>
										    <th scope="col">끌어올리기</th>
										    <th scope="col">기능</th>
										  </tr>
										</thead>
										<tbody>
										  
										</tbody>
									</table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>    
                
                <!-- 예약대기 -->
                <div id="tab-8" class="tab-pane fade show p-0">
                    <div class="row g-4">
                    
                        <div class="col-lg-12">
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
										    <th scope="col">예약요청일</th>
										    <th scope="col">구매자</th>
										    <th scope="col">채팅하기</th>
										    <th scope="col">예약</th>
										  </tr>
										</thead>
										<tbody>
										  
										</tbody>
									</table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 예약중 -->
                <div id="tab-9" class="tab-pane fade show p-0">
                    <div class="row g-4">
                        <div class="col-lg-12">
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
										    <th scope="col">최근작성일</th>
											<th scope="col">구매자</th>
										    <th scope="col">채팅하기</th>
										    <th scope="col">예약</th>
										  </tr>
										</thead>
										<tbody>
										  
										</tbody>
									</table>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                </div>
                <!-- 판매완료 -->
                <div id="tab-10" class="tab-pane fade show p-0">
                    <div class="row g-4">
                        <div class="col-lg-12">
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
										    <th scope="col">최근작성일</th>
										    <th scope="col">구매자</th>
										    <th scope="col">채팅하기</th>
										    <th scope="col">기능</th>
										  </tr>
										</thead>
										<tbody>
										  
										</tbody>
									</table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="tab-11" class="tab-pane fade show p-0">
                    <div class="row g-4">
                        <div class="col-lg-12">
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
										    <th scope="col">최근작성일</th>
										    <th scope="col">기능</th>
										  </tr>
										</thead>
										<tbody>
										  
										</tbody>
									</table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Menu End -->