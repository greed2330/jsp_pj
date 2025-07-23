package pj.mvc.jsp.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardServiceImpl implements BoardService{

	//[게시글 목록]
	@Override
	public void boardListAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	//[게시글 상세 처리]
	@Override
	public void boardDetailAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	//[게시글 수정삭제 버튼 클릭 시 - 비밀번호 인증처리]
	@Override
	public int password_chkAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	//[게시글 수정 처리]
	@Override
	public void boardUpdateAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	//[게시글 삭제 처리]
	@Override
	public void boardDeleteAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	//[게시글 작성 처리]
	@Override
	public void boardInsertAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	//[댓글 작성 처리]
	@Override
	public void commentAddaction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	//[댓글 목록]
	@Override
	public void commentListAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
