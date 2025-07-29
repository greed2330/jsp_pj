<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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