/**
 * 
 */



//이메일 확인
//일전에 했었던 예제 참고하여 직업 하기.
// 회원가입, 수정시의 이메일 체크
function seletEmailChk(){
	
}

//ID 중복확인(버튼 클릭 시)
function confirmId(){
	//alert("중복확인");
	if(!document.inputform.user_id.value){
		alert("아이디를 입력하세요!!");
		document.inputform.user_id.focus();
		return false;
	}else{//중복확인 버튼 클릭하였을 시 컨트롤러로 url전달, 컨트롤러에 소스 추가
		let url = "/jsp_pj_ict05/idConfirmAction.do?user_id="+document.inputform.user_id.value;
		window.open(url, "confirm", "menubar=no, width=500, height=400");
	}
}

//2. join.jsp - onSubmit시 - 회원가입페이지 필수 체크 

//onSubmit
function signInCheck(){
	/* 2-1. 중복확인 체크 안했을 시 0으로 설정*/
	/*hiddenUserid : 중복확인 버튼 안눌렀을때 체크(0: 클릭안함, 1: 클릭함)*/
	
	
	/* 2-2. 중복확인 버튼 클릭하지 않는 경우 "중복체크 해주세요!"*/
	if(document.inputform.hiddenUserid.value){
		alert("중복확인 해주세요!!");
		document.inputform.dubChk.focus();
		return false;
	}
	
	//비밀번호 불일치 => 과제
}

//3. 사용가능한 ID를 찾은 경우 => 자식찾에서 부모창으로 userid전달

/*
opener : window 객체의 open() 메서드로 열린 자식창(=중복확인창)에서 부모창에 접근할 때 사용.
hidden : 중복확인 버튼 안눌렀을때 체크(0: 클릭안함, 1: 클릭함)
self.close(); //자식창 닫기
*/

/*function setUserid(userid){	//작동이 opener.self.close();에서 멈춤.
	//alert(userid);
	opener.document.inputform.user_id.value = userid;
	opener.document.inputform.hiddenUserid.value = "1";	//중복확인창 클릭 인증
	opener.self.close();
}*/

function setUserid(userId){
	alert(userId);
	opener.document.inputform.user_id.value= userId;
	opener.document.inputform.hiddenUserid.value = "1";
	self.close();
}