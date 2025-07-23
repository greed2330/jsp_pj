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
<link rel="stylesheet" href="${path}/resources/css/customer/login.css">

<!-- js -->
<script src="https://kit.fontawesome.com/657537baae.js" crossorigin="anonymous"></script>

<!-- (3-3-2). 자바스크립트 소스 연결 -->
<!-- defer : html을 다 읽은 후에 자바스크립트를 실행한다. 페이지가 모두 로드된 후에 실행된다. -->
<script src="${path}/resources/js/common/main.js" defer></script>

</head>
<body>
	<div class="wrap">
		<!-- header 시작 -->
		<%@ include file="../../common/header.jsp" %><!-- customer/webapp/common/header.jsp -->
		<!-- header 끝 -->
		
		<!-- 컨텐츠 시작 -->
		<div id="container">
			<div id="contents">
				<!-- 상단 중앙1 시작 -->
				<div id="section1">
					<h1 align="center">로그인</h1>
				</div>
				
				<!-- 상단 중앙2 시작 -->
				<div id="section2">
					<div id="s2_inner">
						<div class="join">
							<form name="loginform" action="loginAction.do" method="post"
							onsubmit="return loginCheck()">
							
								<!-- 2-1. 중복체크 안했을 시 value = 0 체크하면 value =1 -->
								<!-- 세션이 없는 경우 : 로그인 실패 -->
								<c:if test="${sessionScope.sessionID == null}">
										<script type="text/javascript">
											alert("아이디와 비밀번호가 일치하지 않습니다.");
										</script>
										<table>
											<tr>
												<th> 아이디 * </th>
												<td>
													<input type="text" class="input" name="user_id" 
													size="20" placeholder="공백없이 20자 이내로 작성" required autofocus>
												</td>
											</tr>
											
											<tr>
												<th> 비밀번호 * </th>
												<td>
													<input type="password" class="input" name="user_password" 
													size="20" placeholder="공백없이 20자 이내로 작성" required>
												</td>
											</tr>
											
											<tr>
												<td colspan="2" style="border-bottom: none">
													<br> 
													<div align="right">
														<input class="inputButton" type="submit" value="로그인">
														<input class="inputButton" type="reset" value="취소">
														<input class="inputButton" type="button" value="가입취소" onclick="window.location='${path}/main.do'"><!-- controller의 .do로 감. -->
													</div>
												</td>
											</tr>
										</table>
									</c:if>
								<c:if test="${sessionScope.sessionID != null}">
										<script type="text/javascript">
											alert("로그인 성공");
										</script>
								
										<table>
											
											<tr>
												<th colspan="2">
												<span style=""><b>${sessionScope.sessionID}님 반갑습니다.</b></span>
												 </th>
											</tr>
											
											<tr>
												<td colspan="2" style="border-bottom: none">
													<br> 
													<div align="right">
														<input class="inputButton" type="button" value="회원수정" onclick="window.location='${path}/modifyCustomer.do'">
														<input class="inputButton" type="button" value="회원탈퇴" onclick="window.location='${path}/deleteCustomer.do'">
														<input class="inputButton" type="button" value="로그아웃" onclick="window.location='${path}/logout.do'"><!-- controller의 .do로 감. -->
													</div>
												</td>
											</tr>
										</table>
									</c:if>
								
							</form>
						</div>	<!-- join -->
					</div>
				</div>
			</div>
		</div>
		<!-- 컨텐츠 끝 -->
		
		<!-- footer 시작 -->
		<%@ include file="../../common/footer.jsp" %>
		<!-- footer 끝 -->
	</div>
</body>
</html>