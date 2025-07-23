<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/setting.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link rel="stylesheet" href="${path}/resources/css/customer/join.css">

<!-- js -->
<script src="https://kit.fontawesome.com/657537baae.js" crossorigin="anonymous"></script>

<!-- (3-3-2). 자바스크립트 소스 연결 -->
<!-- defer : html을 다 읽은 후에 자바스크립트를 실행한다. 페이지가 모두 로드된 후에 실행된다. -->
<script src="${path}/resources/js/common/main.js" defer></script>

<script src="${path}/resources/js/customer/modify.js" defer></script>
</head>
<body>
	<div class="wrap">
		<!-- header 시작 -->
		<%@ include file="../../../common/header.jsp" %><!-- customer/webapp/common/header.jsp -->
		<!-- header 끝 -->
		
		<!-- 컨텐츠 시작 -->
		<c:if test="${updateCnt == 1}">
			<script type="text/javascript">
				alert("회원수정 성공!!");
				window.location="${path}/main.do";
			</script>
		</c:if>
		<c:if test="${updateCnt != 1}">
			<script type="text/javascript">
				alert("회원수정 실패!!");
				window.location="${path}/modifyDetailAction.do"
			</script>
		</c:if>
		<!-- 컨텐츠 끝 -->
		
		<!-- footer 시작 -->
		<%@ include file="../../../common/footer.jsp" %>
		<!-- footer 끝 -->
	</div>
</body>
</html>