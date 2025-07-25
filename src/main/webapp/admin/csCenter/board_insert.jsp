<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 반응형 웹 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>main</title>

<!-- css -->
<link rel="stylesheet" href="${path}/resources/css/common/header.css">
<link rel="stylesheet" href="${path}/resources/css/common/footer.css">
<link rel="stylesheet" href="${path}/resources/css/admin/ad_boardList.css">

<!-- js -->
<script src="https://kit.fontawesome.com/657537baae.js" crossorigin="anonymous"></script>

<!-- (3-3-2). 자바스크립트 소스 연결 -->
<!-- defer : html을 다 읽은 후에 자바스크립트를 실행한다. 페이지가 모두 로드된 후에 실행된다. -->
<script src="${path}/resources/js/common/main.js" defer></script>
<script>
	
	$(function(){	//상세 페이지가 로딩되면
		//btnSave 버튼을 눌렀을 때, 비밀번호, 글제목, 글내용 체크 -> board_insertAction.bc, submit
		$('#btnSave').click(function(){
			//비밀번호 입력 확인
			if(!$("#b_password").val()){
				alert("비밀번호 미기입!!");
				$("#b_password").focus();
				return false;
			}
			
			if(!$("#b_title").val()){
				alert("글 제목을 입력하세요!!");
				$("#b_title").focus();
				return false;
			}
			
			if(!$("#b_content").val()){
				alert("내용을 입력하세요!!");
				$("#b_content").focus();
				return false;
			}
			
			document.insertForm.action="${path}/board_insertAction.bc";
			document.insertForm.submit();
		});
		// [게시글 수정 버튼] 클릭 시 [게시글 수정 처리]로 이동
	});
</script>

</head>
<body>
	<div class="wrap">
		<!-- header 시작 -->
		<%@ include file="/common/header.jsp" %>
		<!-- header 끝 -->
		
		<!-- 컨텐츠 시작 -->
		<div id="container">
			<div id="contents">
				<!-- 상단 중앙1 시작 -->
				<div id="section1">
					<h1 align="center">게시판 작성 페이지</h1>
				</div>
				<!-- 상단 중앙1 종료 -->
				
				<!-- 상단 중앙2 시작 -->
				<div id="section2">
					<!-- 좌측메뉴 시작 -->
					<%@ include file="/admin/common/leftMenu.jsp" %>
					<!-- 좌측메뉴 종료 -->
					
					<!-- 우측화면 시작 -->
					<div id="right">
						<div class="table_div">
							<form name="insertForm" method="post">
								<table>
									<tr>
										<th style="width: 200px">작성자</th>
										<td style="width: 200px; text-align:center">${sessionScope.sessionID}</td>
										
										<th style="width: 200px">비밀번호</th>
										<td style="width: 200px; text-align:center">
											<input style="width: 200px" type="password" class="input" name="b_password" id="b_password" 
											size="30" placeholder="비밀번호 입력" autofocus>
										</td>
									</tr>
									
									
									<tr>
										<th style="width: 100%">글제목</th>
										<td colspan="3" style="text-align:center">
										<input style="width: 200px" type="text" class="input" name="b_title" id="b_title" 
											size="100px" placeholder="글제목 입력">
										</td>
									</tr>
									<tr>
										<th style="width: 200px">글내용</th>
										<td colspan="3" style="width: 200px; text-align:center">
										<textarea rows="5" cols="93" name="b_content" id="b_content"></textarea>
										</td>
									</tr>
									<tr>
										<td colspan="4" style="text-align:center">
											<br>
											<!-- 게시글번호 hidden 추가 : input이 없으므로(게시글 번호는 입력받지 않으므로 input이 없음) -->
											<input type="button" class="inputButton" value="작성" id="btnSave">
											<input type="button" class="inputButton" value="초기화" id="btnDelete">
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
					<!-- 우측화면 종료 -->
				</div>
				<!-- 상단 중앙2 종료 -->
			</div>
		</div>
		
		<!-- 컨텐츠 끝 -->
		
		<!-- footer 시작 -->
		<%@ include file="/common/footer.jsp" %>
		<!-- footer 끝 -->
	</div>
</body>
</html>