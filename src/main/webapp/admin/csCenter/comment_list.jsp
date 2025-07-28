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

</head>
<body>
					<!-- 우측화면 시작 -->
					<div id="right">
						<div class="table_div">
						
							<form name="boardList">
								<table border="1" width="1000px">
									<tr>
										<th>글번호</th>
										<th>작성자</th>
										<th>글제목</th>
										<th>작성일</th>
									</tr>
									<!-- 게시글이 있으면 -->
									<c:forEach var="dto" items="${list}">
									<tr>
										<td>${dto.c_board_num}</td>
										<td>${dto.c_writer}</td>
										<td>${dto.c_content}</td>
										<td>${dto.c_regDate}</td>
									</tr>
									</c:forEach>
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
	</div>
</body>
</html>