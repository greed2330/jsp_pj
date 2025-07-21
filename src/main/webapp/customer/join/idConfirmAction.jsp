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
<link rel="stylesheet" href="/jsp_pj_ict05/resources/css/customer/join.css">

<!-- js -->
<script src="https://kit.fontawesome.com/657537baae.js" crossorigin="anonymous"></script>

<!-- (3-3-2). 자바스크립트 소스 연결 -->
<!-- defer : html을 다 읽은 후에 자바스크립트를 실행한다. 페이지가 모두 로드된 후에 실행된다. -->
<script src="/jsp_pj_ict05/resources/js/customer/join.js" defer></script>

</head>
<body>
	<div class="wrap">
		
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
								<%
									int selectCnt = (Integer)request.getAttribute("selectCnt");
									String strId = (String)request.getAttribute("strId");
									
									//id 중복일 때, id를 입력받아 다시 중복체크
									if(selectCnt == 1){
								%>
										<table align="center" width="500px">
											<tr>
												<th colspan="2">
													<span><%= strId %>는 사용할 수 없습니다.</span>
												</th>
											</tr>
										<tr>
											<th> 아이디 * </th>
											<td>
												<input type="text" class="input" name="user_id" 
												size="20" placeholder="공백없이 20자 이내로 작성" required autofocus>
												<input type="button" name="dubChk" value="중복확인" style="margin-left:10px"
												onclick="confirmId()">
											</td>
										</tr>
										<tr>
											<td colspan="2" style="border-bottom: none">
												<br> 
												<div align="right">
													<input class="inputButton" type="submit" value="회원가입">
													<input class="inputButton" type="reset" value="초기화">
													<input class="inputButton" type="button" value="가입취소" onclick="window.location='main.do'"><!-- controller의 .do로 감. -->
												</div>
											</td>
										</tr>
									</table>
								<%
									}
									else{
								%>
									<table align="center" width="500px">
										<tr>
											<th colspan="2">
												<span><%= strId %>는 사용할 수 있습니다.</span>
											</th>
										</tr>
										<tr>
											<td colspan="2" style="border-bottom: none">
												<br> 
												<div align="right">
													<input class="inputButton" type="button" value="확인" onclick="setUserid('<%=strId%>')">
												</div>
											</td>
										</tr>
									</table>
								<%
									}
								%>
								<!-- id 중복일 때, id를 입력받아 다시 중복체크 -->
								
								<!-- id 중복아닐 때, 확인 후 창닫기 -->
							</form>
						</div>	<!-- join -->
					</div>
				</div>
			</div>
		</div>
		<!-- 컨텐츠 끝 -->
		
	</div>
</body>
</html>