package pj.mvc.jsp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pj.mvc.jsp.dao.BoardDAO;
import pj.mvc.jsp.service.BoardServiceImpl;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("*.bc")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		action(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
		
	}
	
	public void action(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//한글 안깨지게 처리
		request.setCharacterEncoding("UTF-8");
		
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = uri.substring(contextPath.length());
		String viewPage="";
		BoardServiceImpl service = new BoardServiceImpl();
		
		//[게시글 목록]
		if(url.equals("/board_list.bc")||url.equals("/*.bc")) {
			System.out.println("<<< url ==> /board_list.bc >>>");
			service.boardListAction(request, response);
			viewPage = "admin/csCenter/board_list.jsp";
		}
		
		//[게시글 상세 화면]
		if(url.equals("/board_detailAction.bc")) {
			System.out.println("<<<url ==> /board_detailAction.bc>>>");
			service.boardDetailAction(request, response);
			viewPage = "admin/csCenter/board_detailAction.jsp";
		}
		
		
		//[게시글 수정삭제 버튼] 클릭 시 - 비밀번호 인증처리
		if(url.equals("/password_chkAction.bc")) {
			System.out.println("<<<url ==> /password_chkAction.bc>>>");
			
			int result = service.password_chkAction(request, response);
			
			if(result != 0) {
				viewPage = "admin/csCenter/board_edit.jsp";
			}
			else {
				System.out.println("<<<비밀번호 불일치>>>");
				int b_num = Integer.parseInt(request.getParameter("hidden_b_num")); 
				
				viewPage = request.getContextPath() +"/board_detailAction.bc?b_num="+b_num+"&message=error";
				response.sendRedirect(viewPage);
				return;	//sendRedirect후 forward 못타게 하기위함
			}
		}
		
		//[게시글 수정 처리]
		if(url.equals("/board_updateAction.bc")) {
			System.out.println("<<< url ==> /board_updateAction.bc >>>");
			
			viewPage = request.getContextPath() + "/board_list.bc"; 
			response.sendRedirect(viewPage);
			service.boardUpdateAction(request, response);
			
			return;
		}
		
		//[게시글 삭제 처리]
		if(url.equals("/board_deleteAction.bc")) {
			System.out.println("<<< url ==> /board_deleteAction.bc >>>");
			
			service.boardDeleteAction(request, response);
			viewPage = request.getContextPath() + "/board_list.bc"; 
			response.sendRedirect(viewPage);
			return;
		}
		//[게시글 작성 화면]
		if(url.equals("/board_insert.bc")) {
			System.out.println("<<< url ==> /board_insert.bc >>>");
			
			viewPage = "admin/csCenter/board_insert.jsp";
		}
		//[게시글 작성 처리]
		if(url.equals("/board_insertAction.bc")) {
			System.out.println("<<< url ==> /board_insertAction.bc >>>");
			service.boardInsertAction(request, response);
			
			viewPage = request.getContextPath() + "/board_list.bc";
			response.sendRedirect(viewPage);
			return;
		}
		
		//[댓글 작성 처리]
		if(url.equals("/comment_insert.bc")) {
			System.out.println("<<< url ==> /comment_insert.bc >>>");
			
			service.commentAddaction(request, response);	// (5)
			
			//viewPage = "admin/csCenter/board_detail.jsp";
			//board_detailAction.jsp의 comment_add()로 복귀 => $.ajax의 콜백함수로 리턴
		}
		
		//[댓글 목록]
		if(url.equals("/comment_list.bc")) {
			System.out.println("<<< url ==> /comment_insert.bc >>>");
			
			service.commentListAction(request, response);	// (5)
			
			viewPage = "admin/csCenter/comment_list.jsp";
			//board_detailAction.jsp의 comment_list()로 복귀 => $.ajax의 콜백함수로 리턴
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}

}
