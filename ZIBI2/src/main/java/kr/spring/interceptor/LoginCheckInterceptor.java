package kr.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user")==null) { //로그인하지 않은 경우
			response.sendRedirect(request.getContextPath()+"/member/login"); 
				//로그인 페이지로 리다이렉트(IOException 처리 필수)
			return false;
		}
		return true;
	}
}
