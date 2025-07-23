package pj.mvc.jsp.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		//게시글 목록
		
		//게시글 상세페이지
		
		//[게시글 수정삭제 버튼] 클릭 시 - 비밀번호 인증처리
		
		//[게시글 수정 처리]
		
		//[게시글 삭제 처리]
		
		//[게시글 작성 화면]
		
		//[게시글 작성 처리]
	}

}
