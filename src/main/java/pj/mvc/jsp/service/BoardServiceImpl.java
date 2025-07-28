package pj.mvc.jsp.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pj.mvc.jsp.dao.BoardDAO;
import pj.mvc.jsp.dao.BoardDAOImpl;
import pj.mvc.jsp.dto.BoardCommentDTO;
import pj.mvc.jsp.dto.BoardDTO;
import pj.mvc.jsp.page.Paging;

public class BoardServiceImpl implements BoardService{

	//[게시글 목록]
	@Override
	public void boardListAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BoardServiceImpl - boardListAction()");
		
		//3단계. 화면에서 입력받은 값을 가져오기.
		String pageNum = request.getParameter("pageNum");
		
		//4단계. 싱글톤 방식으로 DAO 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		//5-1단계. 전체 게시글 갯수 카운트
		Paging paging = new Paging(pageNum);
		int total = dao.boardCnt();
		System.out.println("total : "+total);
		
		paging.setTotalCount(total);
		
		//5-2단계. 게시글 목록조회
		int start = paging.getStartRow();
		int end = paging.getEndRow();
		
		
		List<BoardDTO> list = dao.boardList(start, end);
		System.out.println("list : "+list);
		
		//6단계. jsp로 처리결과 전달
		request.setAttribute("list", list);
		request.setAttribute("paging", paging);
	}
	
	//[게시글 상세 처리]
	@Override
	public void boardDetailAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BoardServiceImpl - boardDetailAction()");
		
		//3단계. get 방식으로 넘긴 값을 가져온다.(hidden)
		int b_num = Integer.parseInt(request.getParameter("b_num"));
		
		//4단계. 싱글톤 방식으로 DAO 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		//5-1단계. 조회수 증가
		dao.plusReadCnt(b_num);
		BoardDTO dto = dao.getBoardDetail(b_num);
		
		//5-2단계. 게시글 상세페이지
		
		//6단계. jsp로 처리결과 전달
		request.setAttribute("dao", dao);
		request.setAttribute("dto", dto);
		
	}

	//[게시글 수정삭제 버튼 클릭 시 - 비밀번호 인증처리]
	@Override
	public int password_chkAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BoardServiceImpl - password_chkAction()");
		//3단계. get 방식으로 넘긴 값을 가져온다.(hidden)
		int num = Integer.parseInt(request.getParameter("hidden_b_num")); 
		String password = request.getParameter("b_password");
		
		//4단계. 싱글톤 방식으로 DAO 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		//5-1단계. 게시글 수정 삭제 시 비밀번호 인증
		int result = dao.password_chk(num, password);
		//5-2단계. 게시글 상세 페이지 => 수정하기 위해 수정상세페이지에 뿌릴 값 받아오기
		BoardDTO dto = null;
		if(result != 0) {
			dto = dao.getBoardDetail(num);
		}
		
		//6단계. jsp로 처리결과 전달
		request.setAttribute("dto", dto);
		
		System.out.println(result);
		return result;
	}

	//[게시글 수정 처리]
	@Override
	public void boardUpdateAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BoardServiceImpl - boardUpdateAction()");
		//3단계. 화면에서 입력된 값을 가져온다.(hidden 포함)을 가져와서 DTO에 담기
		BoardDTO dto = new BoardDTO();
		dto.setB_num(Integer.parseInt(request.getParameter("hidden_b_num")));
		dto.setB_password(request.getParameter("b_password"));
		dto.setB_title(request.getParameter("b_title"));
		dto.setB_content(request.getParameter("b_content"));
		
		//4단계. 싱글톤 방식으로 DAO 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		dao.updateBoard(dto);
		
		//5단계. 게시글 수정처리 후 컨트롤러에서 board_list로 이동
		
		//6단계. jsp로 처리결과 전달
	}
	
	//[게시글 삭제 처리]
	@Override
	public void boardDeleteAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BoardServiceImpl - boardDeleteAction()");
		//3단계. get 방식으로 넘긴 값을 가져온다.(hidden)
		
		
		//4단계. 싱글톤 방식으로 DAO 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		dao.deleteBoard(Integer.parseInt(request.getParameter("hidden_b_num")));
		
		//5단계. 게시글 수정처리 후 컨트롤러에서 board_list로 이동
	}

	//[게시글 작성 처리]
	@Override
	public void boardInsertAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BoardServiceImpl - boardInsertAction()");
		BoardDAO dao = BoardDAOImpl.getInstance();
		BoardDTO dto = new BoardDTO();
		dto.setB_writer((String)request.getSession().getAttribute("sessionID"));
		dto.setB_password(request.getParameter("b_password"));
		dto.setB_content(request.getParameter("b_content"));
		dto.setB_title(request.getParameter("b_title"));
		dao.insertBoard(dto);
	}

	//[댓글 작성 처리]
	@Override
	public void commentAddaction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BoardServiceImpl - commentAddaction()");
		
		// 3단계. 화면에서 입력받은 값(jQuery에서 넘긴값)응 가져와서 dto에 담는다.
		//CommentDTO 생성
		BoardCommentDTO dto = new BoardCommentDTO();
		dto.setC_board_num(Integer.parseInt(request.getParameter("board_num")));
		dto.setC_writer(request.getParameter("writer"));
		dto.setC_content(request.getParameter("content"));
		
		//4단계. 싱글톤 방식ㅇ로 DAO객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		//5단계. 댓글 작성처리 후 컨트롤러에서 list로 이동
		dao.insertComment(dto);
	}

	//[댓글 목록]
	@Override
	public void commentListAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BoardServiceImpl - commentAddaction()");
		
		// 3단계. 화면에서 입력받은 값(jQuery에서 넘긴값)응 가져와서 dto에 담는다.
		//CommentDTO 생성
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		//4단계. 싱글톤 방식ㅇ로 DAO객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		//5단계. 댓글 작성처리 후 컨트롤러에서 list로 이동
		List<BoardCommentDTO> list = dao.commentList(board_num);
		
		//6단계. jsp로 처리결과 전달
		request.setAttribute("list", list);
	}

}
