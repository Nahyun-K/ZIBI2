package kr.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.spring.member.vo.MemberVO;

public class AdminCheckInterceptor implements HandlerInterceptor {
	
	public boolean preHandel(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO db_member = (MemberVO)session.getAttribute("user");
		
		if(db_member.getMem_auth()!=9) { //관리자가 아닌 경우
			response.sendRedirect(request.getContextPath()+"/member/login"); //로그인 페이지로 리다이렉트
			return false;
		}
		return true;
	}
}