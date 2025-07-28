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
   $(function() {  // 상세페이지가 로딩되면
      // 자동으로 댓글 목록 호출
      comment_list();
      
      // [댓글쓰기 버튼 클릭(1)]
      $('#btnCommentSave').click(function() {
         comment_add();         
      });
      
      // [게시글 목록 버튼] 클릭시 컨트롤러의 목록으로 이동
      $('#btnList').click(function() {
         location.href="${path}/board_list.bc";
      });
      
      // [게시글 수정삭제 버튼] 클릭시 [게시글 수정삭제 화면]으로 이동(컨트롤러에서 패스워드 체크 후)
      $('#btnEdit').click(function() {
         document.detailForm.action="${path}/password_chkAction.bc";
         document.detailForm.submit();
      });
   });
   
   // [댓글쓰기 버튼 클릭(2)]
   function comment_add() {
      alert('comment_add()');
      
      // 게시글번호, 댓글작성자, 댓글 내용을 파라미터로 넘김
      let param = {
         "board_num" : ${dto.b_num},// key: value => 댓글번호: 게시글번호
         "writer" : $('#c_writer').val(),
         "content" : $('#c_content').val()
      }
      $.ajax({
         url: '${path}/comment_insert.bc',  // 컨트롤러 이동(3)
         type: 'POST',
         data: param,
         success: function() {  // 콜백함수(6) => 댓글쓰기가 완료되면 서버에서 콜백함수 호출
            $('#c_writer').val("");
            $('#c_content').val("")
            comment_list();   // 댓글목록 새로고침(7)
         },
         error: function() {
            alert('comment_add() 오류');
         }
      });
   }
   
   // 댓글목록
   function comment_list() {  // (8)
      alert("comment_list()");
      $.ajax({
         url: '${path}/comment_list.bc',  // 컨트롤러 이동(9)
         type: 'POST',
         data: 'board_num=${dto.b_num}',
         success: function(result) {
            $('#comment_list').html(result);   // 콜백함수
         },
         error: function() {
            alert('comment_list() 오류');
         }
      });
   }
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
					<h1 align="center">상세 페이지</h1>
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
							<form name="detailForm" method="post">
								<table>
									<tr>
										<th style="width: 200px">글번호</th>
										<td style="width: 200px; text-align:center">${dto.b_num}</td>
										
										<th style="width: 200px">조회수</th>
										<td style="width: 200px; text-align:center">${dto.b_readcnt}</td>
									</tr>
									
									<tr>
										<th style="width: 200px">작성자</th>
										<td style="width: 200px; text-align:center">${dto.b_writer}</td>
										
										<th style="width: 200px">비밀번호</th>
										<td style="width: 200px; text-align:center">
											<input style="width: 200px" type="password" class="input" name="b_password" id="b_password" 
											size="30" placeholder="비밀번호 입력" autofocus required>
											
											<c:if test="${param.message == 'error'}">
												<br>
												<span style="color:red">
													<script>
														alert("비밀번호 불일치!!");
													</script>
													</span>
											</c:if>
										</td>
									</tr>
									
									<tr>
										<th style="width: 200px">글제목</th>
										<td colspan="3" style="text-align:center">${dto.b_title}</td>
									</tr>
									<tr>
										<th style="width: 200px">글내용</th>
										<td colspan="3" style="width: 200px; text-align:center">${dto.b_content}</td>
									</tr>
									
									<tr>
										<th style="width: 200px">작성일</th>
										<td colspan="3" style="text-align:center">${dto.b_regdate}</td>
									</tr>
									
									<tr>
										<td colspan="4" style="text-align:center">
											<br>
											<!-- 게시글번호 hidden 추가 : input이 없으므로(게시글 번호는 입력받지 않으므로 input이 없음) -->
											<input type="hidden" value="${dto.b_num}" name="hidden_b_num">
											<input type="button" class="inputButton" value="수정/삭제" id="btnEdit">
											<input type="button" class="inputButton" value="목록" id="btnList">
										</td>
									</tr>
								</table>
								<br><br>
								
								<!-- 댓글 목록 코드 -->
								<div id="comment_list" align="center">
									<!-- 댓글목록 -->
								</div>
								<table>
									<tr>
										<th style="width: 150px">댓글작성자</th>
										<td style="width: 200px; text-align:left">
											<input style="width: 250px" type="text" class="input" name="c_writer" 
											id="c_writer" size="30" placeholder="작성자 입력">
										</td>
										
										
										<th style="width: 200px" rowspan="2" align="right">
											<center><input type="button" class="inputButton" value="작성" id="btnCommentSave"></center>
										</th>
									</tr>
									
									<tr>
										<th style="width: 150px">글내용</th>
										<td style="width: 600px; text-align:center">
											<textarea style="width: 600px" rows="5" cols="193" name="c_content" id="c_content" placeholder="댓글입력"></textarea>
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