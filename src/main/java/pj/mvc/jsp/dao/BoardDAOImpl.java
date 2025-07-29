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
	ResultSet rs = null;
	//게시글 목록
	@Override
	public List<BoardDTO> boardList(int start, int end) {
		System.out.println("BoardDAOImpl - boardList()");
		
		String sql =
				"SELECT * "
				+"FROM " 
				+"	(SELECT A.* "
				+"		 , rownum AS rn"
				+"	   FROM (SELECT * FROM mvc_board_tbl"
				+ "			   WHERE b_show = 'Y' "
				+"				ORDER BY B_NUM DESC) A "
				+"	) "
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
		System.out.println("BoardDAOImpl - getBoardDetail()");
		BoardDTO dto = new BoardDTO();
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
		System.out.println("BoardDAOImpl - password_chk()");
		int selectCnt = 0;
		//1.
		String sql = "SELECT count(*) AS cnt FROM MVC_BOARD_TBL "
				+ "WHERE b_num = ? "
				+ "AND b_password = ?";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				selectCnt = rs.getInt("cnt");
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
		System.out.println("selectCnt : "+selectCnt);
		return selectCnt;
	}

	//게시글 수정 처리
	@Override
	public void updateBoard(BoardDTO dto) {
		System.out.println("BoardDAOImpl - updateBoard()");
		int updateCnt = 0;
		String sql = "UPDATE MVC_BOARD_TBL "
				+ "SET b_password = ?, "
				+ "b_title = ?, "
				+ "b_content = ? "
				+ "WHERE b_num = ?";		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getB_password());
			pstmt.setString(2, dto.getB_title());
			pstmt.setString(3, dto.getB_content());
			pstmt.setInt(4, dto.getB_num());
			updateCnt = pstmt.executeUpdate(); 
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
		System.out.println("updateCnt : "+updateCnt);
	}

	//게시글 삭제 처리
	@Override
	public void deleteBoard(int board_num) {
		System.out.println("BoardDAOImpl - deleteBoard()");
		int deleteCnt = 0;
//		String sql = "DELETE FROM MVC_BOARD_TBL "
//				+ "WHERE b_num = ?";		
		String sql = "UPDATE mvc_board_tbl "
				+ "		SET b_show = 'N' "
				+ "		WHERE b_num = ?";
		
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			deleteCnt = pstmt.executeUpdate();
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
		System.out.println("deleteCnt : "+ deleteCnt);
	}

	//게시글 작성 처리
	@Override
	public void insertBoard(BoardDTO dto) {
		System.out.println("BoardDAOImpl - insertBoard()");
		String sql = "INSERT INTO mvc_board_tbl(b_num, b_title, b_content, b_writer, b_password, b_regdate, b_comment_count) "
				+"VALUES((SELECT NVL(MAX(b_num)+1, 1) FROM MVC_BOARD_TBL), ?, ?, ?, ?, sysdate, 0)";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getB_title());
			pstmt.setString(2, dto.getB_content());
			pstmt.setString(3, dto.getB_writer());
			pstmt.setString(4, dto.getB_password());
			pstmt.executeUpdate();
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
	}

	//댓글 작성 처리
	@Override
	public void insertComment(BoardCommentDTO dto) {
		System.out.println("BoardDAOImpl - insertComment()");
		String sql = "INSERT INTO mvc_comment_tbl(c_comment_num, c_board_num, c_writer, c_content, c_regDate) "
				+ "VALUES((SELECT NVL(MAX(c_comment_num)+1, 1) FROM mvc_comment_tbl), ?, ?, ?, sysdate)";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getC_board_num());
			pstmt.setString(2, dto.getC_writer());
			pstmt.setString(3, dto.getC_content());
			pstmt.executeUpdate();
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
	}

	//댓글 목록
	@Override
	public List<BoardCommentDTO> commentList(int board_num) {
		System.out.println("BoardDAOImpl - boardList()");
		
		String sql =
				"SELECT *"
				+"FROM" 
				+"	(SELECT A.* "
				+"		 , rownum AS rn"
				+"	   FROM (SELECT * FROM mvc_comment_tbl c, mvc_board_tbl b "
				+"			WHERE b.B_NUM = c.c_board_num "
				+"			AND b_num = ? "
				+"		ORDER BY C_COMMENT_NUM DESC) A "
				+"	) "
				+"ORDER BY rn DESC";
		
		//1. list 생성
		List<BoardCommentDTO> list = new ArrayList<BoardCommentDTO>();
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			
			rs = pstmt.executeQuery();
			
			//데이터가 존재하면
			while(rs.next()) {
				//2. dto 생성
				BoardCommentDTO dto = new BoardCommentDTO();
				//3. dto에 1건의 rs 게시글 정보를 담는다.
				dto.setC_comment_num(rs.getInt("rn"));
				dto.setC_board_num(rs.getInt("c_board_num"));
				dto.setC_writer(rs.getString("c_writer"));
				dto.setC_content(rs.getString("c_content"));
				dto.setC_regDate(rs.getDate("c_regDate"));
				
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
	
}
