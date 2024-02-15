package kr.spring.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.book.vo.BookVO;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.ActListVO;
import kr.spring.member.vo.DealListVO;
import kr.spring.member.vo.FollowListVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.second.vo.SecondVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PageUtil_na;
import kr.spring.util.PageUtil_naCategory;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MypageController {
	
	@Autowired
	private MemberService memberService;
	
	//카카오 앱키 호출
	@Value("${NA-API-KEY.kakaoAppKey}")
	private String kakao_apikey;
	
	//VO 초기화
	@ModelAttribute
	public MemberVO initCommand(HttpSession session) {
		MemberVO user = (MemberVO)session.getAttribute("user"); //현재 로그인한 유저의 정보로 초기화
		return memberService.selectMember(user.getMem_num());
	}
	
	/*---------------------------페이지 호출-----------------------------*/
	//메인
	@RequestMapping("/member/mypageMain")
	public String mypageMain(Model model) {
		
		model.addAttribute("kakao_apikey",kakao_apikey);
		
		return "mypageMain"; //타일즈
	}
	
	//거래내역 목록
	@RequestMapping("/member/mypageDeal")
	public String mypageDeal(@RequestParam(value="pageNum",defaultValue="1") int currentPage, @RequestParam(value="category",defaultValue="1") String category, HttpSession session, Model model) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		map.put("category", category);
		map.put("mem_num", user.getMem_num());
		
		log.debug("<<카운트 읽어오기 시작>>");
		int count = memberService.selectDealCount(map);
		
		PageUtil_naCategory page = new PageUtil_naCategory(category, null, currentPage, count, 10, 10,"mypageDeal");
		
		List<DealListVO> list = null;
		
		if(count>0) {
			map.put("category", category);
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			list = memberService.selectDealList(map);
			log.debug("<<리스 트>>" + list);
		}
		
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());
		
		return "mypageDeal"; //타일즈
	}
	
	//활동내역 목록
	@RequestMapping("/member/mypageAct")
	public String mypageAct(@RequestParam(value="pageNum",defaultValue="1") int currentPage, @RequestParam(value="category",defaultValue="1") String category, HttpSession session, Model model) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		map.put("category", category);
		map.put("mem_num", user.getMem_num());
		
		log.debug("<<카운트 읽어오기 시작>> : " + map);
		int count = memberService.selectActCount(map);
		
		PageUtil_naCategory page = new PageUtil_naCategory(category, null, currentPage, count, 10,10,"mypageAct");
		
		List<ActListVO> list = null;
		
		if(count>0) {
			map.put("category", category);
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			list = memberService.selectActList(map);
		}
		
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());
		
		return "mypageAct"; //타일즈
	}
	
	//팔로우내역 목록
	@RequestMapping("/member/mypageFollow")
	public String mypageFollow(@RequestParam(value="pageNum",defaultValue="1") int currentPage, HttpSession session, Model model) {
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		int count = memberService.selectFollowCount(user.getMem_num());
		
		PageUtil_na page = new PageUtil_na(null,null, currentPage, count, 10,10,"mypageFollow"); //총 글 갯수?

		List<FollowListVO> list = null;
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		if(count>0) {
			
			map.put("fmem_num", user.getMem_num());
			map.put("start",page.getStartRow());
			map.put("end",page.getEndRow());
			
			list = memberService.selectFollowList(map);
		}
		
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());
		
		return "mypageFollow"; //타일즈
	}
	
	/*---------------------------회원 정보 수정-----------------------------*/
	

	//회원 정보 수정폼
	@GetMapping("/member/mypageUpdate")
	public String mypageUpdateForm() {
		return "mypageUpdate";
	}
	
	//회원 정보 수정 submit
	@PostMapping("/member/mypageUpdate")
	public String mypageUpdateSubmit(@Valid MemberVO memberVO, BindingResult result,HttpSession session) {
		
		log.debug("<<회원 정보 수정 1>> : " + memberVO);
		
		if(result.hasErrors()) { //유효성 체크
			return"mypageUpdate";
		}
		
		memberService.updateMember(memberVO); //닉네임 업데이트
		memberService.updateMemberDetail(memberVO);//update 진행
		session.setAttribute("user",memberVO);
		
		return "redirect:/member/mypageMain"; //url 리다이렉트
	}
	
	/*---------------------------비밀번호 변경-----------------------------*/
	//비밀번호 변경 폼
	@GetMapping("/member/passwordUpdate")
	public String updatePasswordForm() {
		return "passwordUpdateForm";
	}
	
	//비밀번호 변경 submit
	@PostMapping("/member/passwordUpdate")
	public String updatePassword(@Valid MemberVO memberVO, BindingResult result, HttpSession session, Model model) {
		
		//양식에 맞지 않게 입력 시
		if(!Pattern.matches("^[A-Za-z0-9]{4,12}$", memberVO.getMem_password())) {
			result.reject("passwordNotMatchUpdate");
			return "passwordUpdateForm";
		}
		
		//공란 입력 시
		if(result.hasFieldErrors("mem_password")) {
			result.reject("passwordBlank");
			return "passwordUpdateForm";
		}
		
		MemberVO db_member = memberService.selectMember(memberVO.getMem_num());
		
		//현재와 똑같은 비밀번호 입력 시
		if(memberVO.getMem_password().equals(db_member.getMem_password())) {
			result.reject("passwordDuplicated");
			return "passwordUpdateForm";
		}
		
		memberService.updatePassword(memberVO);
				
		return "redirect:/member/mypageMain"; //url 리다이렉트
	}
	
	/*---------------------------회원 탈퇴-----------------------------*/
	//회원 탈퇴 비밀번호 확인폼
	@RequestMapping("/member/checkPassword")
	public String passwordForm() {
		return "checkPassword";
	}
	
	//회원 탈퇴
	@RequestMapping("/member/quitMember")
	public String quitMember(@Valid MemberVO memberVO, BindingResult result, HttpSession session, RedirectAttributes attr) {
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		//소모임 조건 체크		
		List<BookVO> mList = memberService.selectBookList(user.getMem_num());
		
		if(mList.size()>0) {
			result.reject("bookError");
			return "checkPassword"; //타일즈
		}
		
		//중고 거래 조건 체크
		List<SecondVO> sList  = memberService.selectSecond(user.getMem_num());
		if(sList.size() > 0) {
			result.reject("secondError");
			return "checkPassword"; //타일즈
		}
		
		//영화 예매 조건 체크
		int movie_count = memberService.selectMovie(user.getMem_num());
		
		if(movie_count>0) {
			result.reject("movieError");
			return "checkPassword"; //타일즈
		}
		
		session.invalidate(); //로그아웃
		memberService.quitMember(user.getMem_num()); //탈퇴
		
		if(user.getMem_social()==2) //네이버 회원인 경우
			attr.addFlashAttribute("message","quitNaver"); //모달창을 띄워 네이버 서비스로 이동
		else  //그 외 회원인 경우
			attr.addFlashAttribute("message","quit"); //모달창으로 감사 인사
		
		return "redirect:/main/home"; //url 리다이렉트
	}
}