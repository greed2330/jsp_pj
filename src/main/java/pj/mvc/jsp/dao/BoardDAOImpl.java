package pj.mvc.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import pj.mvc.jsp.dto.BoardCommentDTO;
import pj.mvc.jsp.dto.BoardDTO;

public class BoardDAOImpl implements BoardDAO{
	private static BoardDAOImpl instance;
	
	//커넥션 풀 객체를 보관
	DataSource dataSource = null;
	
	//싱글톤 객체 생성
	public static BoardDAOImpl getInstance() {
		if(instance == null) {
			instance = new BoardDAOImpl();
		}
		return instance;
	}
	
	//디폴트 생성자
	private BoardDAOImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/jsp_pj_ict05");
		}catch(NamingException e) {
			e.printStackTrace();
		}
	}
	//커넥션풀(DBCP : DataVase Connection Pool 방식) - context.xml에 설정
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	//게시글 목록
	@Override
	public List<BoardDTO> boardList(int start, int end) {
		System.out.println("BoardDAOImpl - boardList()");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql =
				"SELECT *"
				+"FROM" 
				+"	(SELECT A.* "
				+"		 , rownum AS rn"
				+"	   FROM (SELECT * FROM mvc_board_tbl"
				+"	ORDER BY B_NUM DESC) A"
				+"	)"
				+"WHERE rn BETWEEN ? AND ?";
		
		//1. list 생성
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			//데이터가 존재하면
			while(rs.next()) {
				//2. dto 생성
				BoardDTO dto = new BoardDTO();
				//3. dto에 1건의 rs 게시글 정보를 담는다.
				dto.setB_num(rs.getInt("b_num"));
				dto.setB_title(rs.getString("b_title"));
				dto.setB_content(rs.getString("b_content"));
				dto.setB_readcnt(rs.getInt("b_readcnt"));
				dto.setB_writer(rs.getString("b_writer"));
				dto.setB_password(rs.getString("b_password"));
				dto.setB_regdate(rs.getDate("b_regdate"));
				dto.setB_comment_count(rs.getInt("b_comment_count"));
				
				//4.list에 dto 추가
				list.add(dto);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)conn.close();
				if(pstmt != null)pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	//게시글 갯수 구하기
	@Override
	public int boardCnt() {
		System.out.println("BoardDAOImpl - boardCnt()");
		String sql = "SELECT COUNT(*) AS cnt FROM MVC_BOARD_TBL";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int total = 0;
		
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				total = rs.getInt("cnt");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)conn.close();
				if(pstmt != null)pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return total;
	}

	//조회수 증가
	@Override
	public void plusReadCnt(int board_num) {
		System.out.println("BoardDAOImpl - plusReadCnt()"); 
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE mvc_board_tbl "
				+ "SET b_readcnt = b_readcnt + 1 "
				+ "WHERE b_num = ?";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)conn.close();
				if(pstmt != null)pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//게시글 상세 처리
	@Override
	public BoardDTO getBoardDetail(int board_num) {
		BoardDTO dto = new BoardDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM MVC_BOARD_TBL "
				+ "WHERE b_num = ?";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setB_num(rs.getInt("b_num"));
				dto.setB_title(rs.getString("b_title"));
				dto.setB_content(rs.getString("b_content"));
				dto.setB_writer(rs.getString("b_writer"));
				dto.setB_readcnt(rs.getInt("b_readcnt"));
				dto.setB_password(rs.getString("b_password"));
				dto.setB_regdate(rs.getDate("b_regdate"));
				dto.setB_comment_count(rs.getInt("b_comment_count"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)conn.close();
				if(pstmt != null)pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	//게시글 수정삭제 버튼 클릭 시 - 비밀번호 인증처리
	@Override
	public int password_chk(int board_num, String password) {
		return 0;
	}

	//게시글 수정 처리
	@Override
	public int updateBoard(BoardDTO dto) {
		return 0;
	}

	//게시글 삭제 처리
	@Override
	public int deleteBoard(int board_num) {
		return 0;
	}

	//게시글 작성 처리
	@Override
	public int insertBoard(BoardDTO dto) {
		return 0;
	}

	//댓글 작성 처리
	@Override
	public int insertComment(BoardCommentDTO dto) {
		return 0;
	}

	//댓글 목록
	@Override
	public List<BoardCommentDTO> commentList(int board_num) {
		return null;
	}
	
}
