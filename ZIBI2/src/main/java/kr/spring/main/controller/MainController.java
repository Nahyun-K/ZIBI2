package kr.spring.main.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.spring.main.service.MainService;
import kr.spring.main.vo.LastestContentVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.performance.vo.PerformanceVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	
	@Autowired
	private MainService mainService;
	
	/* ---------- 루트 경로 진입 ----------*/
	@RequestMapping("/")
	public String init(HttpSession session) {
		return "redirect:/main/home";
	}
	
	/* ---------- 메인 ----------*/
	@RequestMapping("/main/home")
	public String main(Model model) {
		
		//최신 영화 1개
		PerformanceVO perf = mainService.selectLastestPerformance();
		
		
		//중고 최신글
		List<LastestContentVO> list_second = mainService.selectLastestSecond();
		
		//재능기부 최신글
		List<LastestContentVO> list_helper = mainService.selectLastestHelper();
		
		//영화 최신
		List<LastestContentVO> list_movie = mainService.selectLastestMovie();
		
		//소모임 최신
		List<LastestContentVO> list_book = mainService.selectLastestBook();
		
		//커뮤니티 최신
		//List<LastestContentVO> list_community = mainService.selectLastestCommunity();
		
		//집 체크리스트 최신 - 작성 필요
		
		
		//가장 핫한 회원
		LastestContentVO member_follower = mainService.selectMostFollowerMember(); //가장 팔로우가 많은
		LastestContentVO member_book = mainService.selectMostFollowMember(); //가장 모임 참여를 많이 한
		LastestContentVO member_content = mainService.selectMostContentMember(); //가장 커뮤니티 글을 많이 쓴
		LastestContentVO member_movie = mainService.selectMostMovieMember(); //가장 영화 예매를 많이 한
		LastestContentVO member_second = mainService.selectMostSecondMember(); //가장 중고 거래를 많이 한
		LastestContentVO member_helper = mainService.selectMostHelpMember(); //가장 헬프유 많이 한
		
		model.addAttribute("perf",perf);
		
		model.addAttribute("list_second", list_second);
		model.addAttribute("list_helper", list_helper);
		model.addAttribute("list_movie", list_movie);
		model.addAttribute("list_book", list_book);
		//model.addAttribute("list_community", list_community);
		
		model.addAttribute("member_content",member_content);
		model.addAttribute("member_follower",member_follower);
		model.addAttribute("member_book",member_book);
		model.addAttribute("member_movie",member_movie);
		model.addAttribute("member_second",member_second);
		model.addAttribute("member_helper",member_helper);
		
		return "home"; //타일스 설정명
	}
	
	/* ---------- 템플릿 샘플 ----------*/
	@RequestMapping("/sample")
	public String sample() {
		return "sample";
	}
}