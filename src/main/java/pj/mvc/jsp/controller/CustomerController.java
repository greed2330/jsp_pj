package pj.mvc.jsp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pj.mvc.jsp.service.CustomerServiceImpl;

// http://localhost/jsp_pj_ict05/*.do
@WebServlet("*.do")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CustomerServiceImpl csi;
	
    public CustomerController() {
        super();
    }
    
    //1단계. 웹브라우저가 전송한 HTTP 전송을 받음
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		action(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void action(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//2단계. 클라이언트 요청 분석
		
		//한글 안깨지게 처리
		request.setCharacterEncoding("UTF-8");
		
		// url ===> http://localhost/jsp_pj_ict05/*.do
		// uri ===> 				/jsp_pj_ict05/*.do
		// contextPath ===>			/jsp_pj_ict05
		// url ===> 							 /*.do
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();	//플젝명
		String url = uri.substring(contextPath.length());	// uri.substring(시작위치)
		String viewPage = "";
		
		//첫페이지
		if(url.equals("/main.do") || url.equals("/*.do")) {
			System.out.println("<<< url ==> /main.do >>>");
			
			viewPage = "/common/main.jsp";
		}
		//[회원가입]
		else if(url.equals("/join.do")) {
			System.out.println("<<< url ==> /join.do >>>");
			csi = new CustomerServiceImpl();
			viewPage = "/customer/join/join.jsp";
		}
		//ID중복확인 버튼 클릭 시
		else if(url.equals("/idConfirmAction.do")) {
			System.out.println("url ==> /idConfirmAction.do");
			csi = new CustomerServiceImpl();
			csi.idConfirmAction(request, response);
			viewPage = "/customer/join/idConfirmAction.jsp";
		}
		
		//회원가입 버튼 클릭시
		else if(url.equals("/joinAction.do")) {
			System.out.println("<<< url ==> /joinAction.do >>>");
			csi.signInAction(request, response);
			viewPage = "/customer/join/joinAction.jsp";
		}
		
		//[로그인]
		else if(url.equals("/login.do")) {
			viewPage = "";
		}
		
		//RequestDispatcher : 서블릿 또는 JSP 요청을 받은 후, 다른 컴포넌트로 요청을 위임하는 클래스이다.
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		
		
		
	}
}
