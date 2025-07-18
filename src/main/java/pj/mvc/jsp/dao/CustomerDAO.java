package pj.mvc.jsp.dao;

import pj.mvc.jsp.dto.CustomerDTO;

public interface CustomerDAO {
	// ID 중복확인 처리	(1은 성공, 0은 실패)
	public int useridCheck(String strId);
	
	// 회원가입 처리	(1은 성공, 0은 실패)
	public int insertCustomer(CustomerDTO dto);
	
	// 로그인 처리 / 회원정보 인증(수정, 탈퇴)	(1은 인증, 0은 재인증.)
	public int idPasswordChk(String strId, String strPassword);
	
	//회원 정보 인증처리 및 탈퇴처리
	public int deleteCustomer(String strId);
	
	// 회원 정보 인증 처리 및 상세페이지 조회
	public CustomerDTO getCustomerDetail(String strId);
	
	// 회원정보 수정 처리
	public int updateCustomer(CustomerDTO dto);
}
