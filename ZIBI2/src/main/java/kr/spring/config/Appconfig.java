package kr.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import kr.spring.interceptor.AdminCheckInterceptor;
import kr.spring.interceptor.LoginCheckInterceptor;
import kr.spring.websocket.SocketHandler;

@Configuration
@EnableWebSocket
public class Appconfig implements WebMvcConfigurer, WebSocketConfigurer {
	
	private AdminCheckInterceptor adminCheck;
	private LoginCheckInterceptor loginCheck; //로그인 인터셉터
	
	@Bean
	public AdminCheckInterceptor interceptor1() { //인터셉터 객체 생성
		adminCheck = new AdminCheckInterceptor();
		return adminCheck; 
	}
	
	@Bean
	public LoginCheckInterceptor interceptor2() { //인터셉터 객체 생성
		loginCheck = new LoginCheckInterceptor();
		return loginCheck; 
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) { //인터셉터 등록
		
		//관리자 체크 인터셉터
		registry.addInterceptor(adminCheck)
			.addPathPatterns("/stats/getData")
			.addPathPatterns("/admin/policy")
			.addPathPatterns("/admin/policyModify")
			.addPathPatterns("/admin/performanceMain")
			.addPathPatterns("/admin/write")
			.addPathPatterns("/admin/writeCinema")
			.addPathPatterns("/admin/writePerformanceDate");
		
		//로그인 체크 인터셉터
		registry.addInterceptor(loginCheck)
			.addPathPatterns("/member/mypageMain")
			.addPathPatterns("/member/mypageUpdate")
			.addPathPatterns("/member/mypageDeal")
			.addPathPatterns("/member/mypageAct")
			.addPathPatterns("/member/mypageFollow")
			.addPathPatterns("/member/passwordUpdate")
			.addPathPatterns("/member/checkPassword")
			.addPathPatterns("/member/passwordUpdate")
			.addPathPatterns("/admin/policy")
			.addPathPatterns("/admin/policyModify")
			.addPathPatterns("/admin/performanceMain")
			.addPathPatterns("/admin/write")
			.addPathPatterns("/admin/writeCinema")
			.addPathPatterns("/admin/writePerformanceDate")
			.addPathPatterns("/book/write")
			.addPathPatterns("/secondhand/write")
			.addPathPatterns("/secondhand/update")
			.addPathPatterns("/secondhand/secondsellList")
			.addPathPatterns("/secondchat/chatDetail")
			.addPathPatterns("/secondchat/chatListForSeller")
			.addPathPatterns("/secondchat/chatListForBuyer")
			.addPathPatterns("/book/update")
			.addPathPatterns("/performance/updateTicketing")
			.addPathPatterns("/performance/submitSeat")
			.addPathPatterns("/performance/choiceSeat")
			.addPathPatterns("/performance/history")
			.addPathPatterns("/book/cancel")
			.addPathPatterns("/book/review")
			.addPathPatterns("/helper/write")
			.addPathPatterns("/helper/update")
			.addPathPatterns("/helper/delete")
			;
			
	}
	
	
	/*---------------------------Tiles 사용 설정----------------------------*/
	@Bean
	public TilesConfigurer tiesConfigurer() {
		final TilesConfigurer configurer = new TilesConfigurer();
		
		//tilesdef.xml의 경로와 파일명 지정
		configurer.setDefinitions(new String[] { //여러 개 넘겨주기 위해 배열 이용
					"/WEB-INF/tiles-def/de.xml",
					"/WEB-INF/tiles-def/hyun.xml",
					"/WEB-INF/tiles-def/jiwon.xml",
					"/WEB-INF/tiles-def/jy.xml",
					"/WEB-INF/tiles-def/na.xml",
					"/WEB-INF/tiles-def/yeeun.xml"
					}); 
		
		configurer.setCheckRefresh(true);
		
		return configurer;
	} 
	
	@Bean
	public TilesViewResolver tilesViewResolver() {
		
		final TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		
		return tilesViewResolver;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new SocketHandler(), "message-ws").setAllowedOrigins("*");
	}
	
	
}