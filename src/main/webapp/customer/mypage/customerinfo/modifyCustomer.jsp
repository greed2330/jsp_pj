<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 반응형 웹 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>main</title>

<!-- css -->
<link rel="stylesheet" href="/jsp_pj_ict05/resources/css/common/header.css">
<link rel="stylesheet" href="/jsp_pj_ict05/resources/css/common/footer.css">
<link rel="stylesheet" href="/jsp_pj_ict05/resources/css/customer/login.css">

<!-- js -->
<script src="https://kit.fontawesome.com/657537baae.js" crossorigin="anonymous"></script>

<!-- (3-3-2). 자바스크립트 소스 연결 -->
<!-- defer : html을 다 읽은 후에 자바스크립트를 실행한다. 페이지가 모두 로드된 후에 실행된다. -->
<script src="/jsp_pj_ict05/resources/js/common/main.js" defer></script>

</head>
<body>
	<div class="wrap">
		<!-- header 시작 -->
		<%@ include file="../../../common/header.jsp" %><!-- mypage/customer/webapp/common/header.jsp -->
		<!-- header 끝 -->
		
		<!-- 컨텐츠 시작 -->
		<div id="container">
			<div id="contents">
				<!-- 상단 중앙1 시작 -->
				<div id="section1">
					<h1 align="center">회원수정 - 인증화면</h1>
				</div>
				
				<!-- 상단 중앙2 시작 -->
				<div id="section2">
					<div id="s2_inner">
						<div class="join">
							<form name="passwordform" action="modifyDetailAction.do" method="post"><!-- 상세페이지 요청 -->
							
								<%
									String sessionID = (String)request.getSession().getAttribute("sessionID");
								%>
								<input type="hidden" name="hiddenUserid" value="0">
								<table>
									<tr>
										<th colspan="2" align="center"> 
											<span style="color: #FF82AA"><b><%= sessionID %></span>님 비밀번호를 입력하세요!!
										</th>
									</tr>
									
									<tr>
										<th> 비밀번호 * </th>
										<td>
											<input type="password" class="input" name="user_password" 
											size="20" placeholder="공백없이 20자 이내로 작성" required autofocus>
										</td>
									</tr>
									
									<tr>
										<td colspan="2" style="border-bottom: none">
											<br> 
											<div align="right">
												<input class="inputButton" type="submit" value="회원정보">
												<input class="inputButton" type="button" value="취소" onclick="window.location='main.do'">
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>	<!-- join -->
					</div>
				</div>
			</div>
		</div>
		<!-- 컨텐츠 끝 -->
		
		<!-- footer 시작 -->
		<%@ include file="../../../common/footer.jsp" %>
		<!-- footer 끝 -->
	</div>
</body>
</html>