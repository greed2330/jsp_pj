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
	<div class="wrap">
		<!-- header 시작 -->
		<%@ include file="/common/header.jsp" %>
		<!-- header 끝 -->
		
		<!-- 컨텐츠 시작 -->
		<div id="container">
			<div id="contents">
				<!-- 상단 중앙1 시작 -->
				<div id="section1">
					<h1 align="center">게시판 목록</h1>
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
							<form name="boardList">
								<table>
									<tr>
										<th>글번호</th>
										<th>작성자</th>
										<th>글제목</th>
										<th>작성일</th>
										<th>조회수</th>
									</tr>
									<!-- 게시글이 있으면 -->
									<c:forEach var="dto" items="${list}">
									<tr>
										<td>${dto.b_num}</td>
										<td>${dto.b_writer}</td>
										<td>${dto.b_title}</td>
										<td>${dto.b_regdate}</td>
										<td>${dto.b_readcnt}</td>
									</tr>
									</c:forEach>
									<tr>
										<td colspan="5" align="center">
											<ul class="pagination">
												<!-- 페이징처리 -->
												<!-- 이전버튼 활성화 -->
												<c:if test="${paging.startPage > 10}">
													<li>
														<a href="${path}/board_list.bc?pageNum=${paging.prev}" class="prevPage"> [이전] </a>
													</li>
												</c:if>
												
												<c:forEach var="num" begin="${paging.startPage}" end="${paging.endPage}">
													<li>
														<a href="${path}/board_list.bc?pageNum=${num}" class="<c:if test='${num == paging.currentPage}'>active</c:if>">${num}</a>
													</li>
												</c:forEach>
												
												<!-- 페이지 번호 처리 -->
												<!-- 다음버튼 활성화 -->
												<c:if test="${paging.startPage < paging.pageCount}">
													<li>
														<a href="${path}/board_list.bc?pageNum=${paging.next}" class="nextPage"> [다음] </a>
													</li>
												</c:if>
											</ul>
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