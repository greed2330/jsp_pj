package pj.mvc.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import pj.mvc.jsp.dto.CustomerDTO;

public class CustomerDAOImpl implements CustomerDAO{
	private static CustomerDAOImpl instance;
	
	//디폴트 생성자
	//커넥션풀(DBCP : DataVase Connection Pool 방식) - context.xml에 설정
	DataSource dataSource = null;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	//싱글톤
	private CustomerDAOImpl(){
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/jsp_pj_ict05");
			 
		}catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static CustomerDAOImpl getInstance() {
		if(instance == null) {
			instance = new CustomerDAOImpl();
		}
		return instance;
	}
	
	
	// ID 중복확인 처리
	@Override
	public int useridCheck(String strId) {
		System.out.println("CustomerDAOImpl - useridCheck()");
		int selectCnt = 0;
		String query = """
				SELECT user_id FROM mvc_customer_tbl 
				WHERE user_id = ?
				""";
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, strId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				selectCnt = 1;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return selectCnt;
	}

	// 회원가입 처리
	@Override
	public int insertCustomer(CustomerDTO dto) {
		System.out.println("CustomerDAOImpl - insertCustomer()");
		
		
		int insertCnt = 0;
		
		try {
			
			String query = """
					INSERT INTO mvc_customer_tbl(user_id, user_password, user_name, user_birthday, user_address, user_hp, user_email, user_regdate)
					VALUES (?, ?, ?, ?, ?, ?, ?, ?)
					""";
			
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getUser_id());
			pstmt.setString(2, dto.getUser_password());
			pstmt.setString(3, dto.getUser_name());
			pstmt.setDate(4, dto.getUser_birthday());
			pstmt.setString(5, dto.getUser_address());
			pstmt.setString(6, dto.getUser_hp());
			pstmt.setString(7, dto.getUser_email());
			pstmt.setTimestamp(8, dto.getUser_regdate());
			
			//실행
			insertCnt = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return insertCnt;
	}

	// 로그인 처리 / 회원정보 인증(수정, 탈퇴)
	@Override
	public int idPasswordChk(String strId, String strPassword) {
		System.out.println("CustomerDAOImpl - idPasswordChk()");
		int selectCnt = 0;
		String query = """
				SELECT * FROM mvc_customer_tbl 
				WHERE user_id = ? AND user_password = ?
				""";
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, strId);
			pstmt.setString(2, strPassword);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				selectCnt = 1;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return selectCnt;
	}

	// 회원 정보 인증처리 및 탈퇴처리
	@Override
	public int deleteCustomer(String strId) {
		int deleteCnt = 0;
		String query = """
				DELETE FROM mvc_customer_tbl 
				WHERE user_id = ?
				""";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, strId);
			deleteCnt = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return deleteCnt;
	}

	// 회원 정보 인증 처리 및 상세페이지 조회
	@Override
	public CustomerDTO getCustomerDetail(String strId) {
		System.out.println("CustomerDAOImpl - getCustomerDetail()");
		
		//1 DTO 생성
		CustomerDTO dto = new CustomerDTO();
		ResultSet rs = null;
		try {
			// 2. DB 연결 => 데이터베이스 커넥션 생성
			conn = dataSource.getConnection();
			
			// 3. SQL 작성 => strId(sessionID)와 일치하는 데이터가 존재하는지 확인
			String query = "SELECT * FROM mvc_customer_tbl "
					  +"WHERE user_id = ?";
			//4. 실행
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, strId);
			
			rs = pstmt.executeQuery();
			// 5-1. ResultSet에 존재하면
			if(rs.next()) {
				dto.setUser_id(rs.getString("user_id"));
				dto.setUser_password(rs.getString("user_password"));
				dto.setUser_name(rs.getString("user_name"));
				dto.setUser_birthday(rs.getDate("user_birthday"));
				dto.setUser_address(rs.getString("user_address"));
				dto.setUser_hp(rs.getString("user_hp"));
				dto.setUser_email(rs.getString("user_email"));
				dto.setUser_regdate(rs.getTimestamp("user_regdate"));
			}
			
			// 502. ResultSet를 읽어서 CustomerDTO에 setter로 담는다.
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	// 회원정보 수정 처리
	@Override
	public int updateCustomer(CustomerDTO dto) {
		int updateCnt = 0;
		
		try {
					
			String query = """
					UPDATE mvc_customer_tbl
					SET user_password = ?, 
					user_name = ?, 
					user_birthday = ?,
					user_address = ?,
					user_hp = ?, 
					user_email = ?, 
					user_regdate = ?
					WHERE user_id = ?
					""";
			
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, dto.getUser_password());
			pstmt.setString(2, dto.getUser_name());
			pstmt.setDate(3, dto.getUser_birthday());
			pstmt.setString(4, dto.getUser_address());
			pstmt.setString(5, dto.getUser_hp());
			pstmt.setString(6, dto.getUser_email());
			pstmt.setTimestamp(7, dto.getUser_regdate());
			pstmt.setString(8, dto.getUser_id());
			System.out.println(dto);
			
			//실행
			updateCnt = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return updateCnt;
	}
}
