package kr.spring.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.FollowListVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PageUtil_naMyPageOpenProfile;
import kr.spring.util.PasswordCheckException;
import kr.spring.util.socialMemberCheckException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	
	//의존 관계 주입
	@Autowired
	private MemberService memberService;
	
	//카카오 앱키 호출
	@Value("${NA-API-KEY.kakaoAppKey}")
	private String kakao_apikey;
	
	//네이버 앱키 호출
	@Value("${NA-API-KEY.naverAppKey}")
	private String naver_apikey;
	
	//VO 초기화
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	
	/*-----------------------오픈 프로필-----------------------------*/
	@RequestMapping("/member/mypageOpen")
	public String mypageOpen(@RequestParam int mem_num, Model model, @RequestParam(value="pageNum",defaultValue="1") int currentPage) {
		
		log.debug("<<오픈 프로필 회원 번호, mem_num>> : " + mem_num);
		
		int count = memberService.selectOpenCount(mem_num);
		String nickname = memberService.selectMember(mem_num).getMem_nickname();
		
		PageUtil_naMyPageOpenProfile page = new PageUtil_naMyPageOpenProfile(Integer.toString(mem_num),null, currentPage, count, 8,10,"mypageOpen"); //총 글 갯수?

		List<FollowListVO> list = null;
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		if(count>0) { //회원이 글을 쓴 경우
			map.put("mem_num", mem_num);
			map.put("start",page.getStartRow());
			map.put("end",page.getEndRow());
			
			list = memberService.selectOpenList(map);
		}
		
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());
		model.addAttribute("nickname",nickname);
		model.addAttribute("mem_num", mem_num);
		
		return "mypageOpen"; //타일즈
	}
	
	/*-----------------------임시 비밀번호 발급 폼-----------------------------*/
	@RequestMapping("/member/findPassword")
	public String findPasswordForm() {
		return "findPassword"; //타일즈
	}
	
	
	/*------------------------------회원가입----------------------------------*/
	//회원가입 폼 호출
	@GetMapping("/member/register")
	public String memberRegister() {
		return "registerForm"; //타일즈
	}
	
	//회원가입 처리
	@PostMapping("/member/register")
	public String registerSubmit(@Valid MemberVO memberVO, BindingResult result) {
		
		if(result.hasFieldErrors("mem_email") || result.hasFieldErrors("mem_password") || result.hasFieldErrors("mem_nickname")) {//유효성 체크
			return "registerForm";
		}
		
		memberVO.setMem_num(memberService.createMemNum());
		memberService.registerMember(memberVO);//회원가입
		
		return "redirect:/main/home"; //타일즈
	}
	
	/*------------------------------로그인/로그아웃----------------------------------*/
	//로그인 폼 호출
	@GetMapping("/member/login")
	public String loginForm(Model model) {
		
		model.addAttribute("kakao_apikey",kakao_apikey);
		model.addAttribute("naver_apikey",naver_apikey);
		
		return "loginForm"; //타일즈
	}
	
	//로그인 처리
	@RequestMapping("/member/login")
	public String loginSubmit(@Valid MemberVO memberVO, BindingResult result, HttpSession session, Model model) {
		
		MemberVO db_member = null;
		
		model.addAttribute("kakao_apikey",kakao_apikey);
		model.addAttribute("naver_apikey",naver_apikey);
		
		try {
			db_member = memberService.checkEmail(memberVO.getMem_email());
			boolean check = false;
			
			log.debug("<<로그인 check>>" + db_member);
			
			if(db_member!=null && db_member.getMem_social()!=memberVO.getMem_social()) { //소셜로 회원가입된 회원인 경우, 소셜 체크 진행
				throw new socialMemberCheckException();
			} else if(db_member != null) {
				check = db_member.checkPassword(memberVO.getMem_password()); //비밀번호 일치여부 확인
			}
			
			if(check) { //비밀번호 일치할 경우
				session.setAttribute("user",db_member); //로그인 처리
				return "redirect:/main/home";
			}
			
			throw new PasswordCheckException(); //비밀번호 미일치할 경우 예외 던지기
			
		} catch (PasswordCheckException e) {
			result.reject("invalidIdOrPassword");
			return "loginForm";
		} catch (socialMemberCheckException e) {
			result.reject("socialMember");
			return "loginForm";
		}
	}

	//로그아웃 처리
	@RequestMapping("/member/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/main/home";
	}
	
	//네이버 로그인
	@RequestMapping("/member/loginNaver")
	public String loginNaver(Model model) {
		
		model.addAttribute("naver_apikey",naver_apikey);
		log.debug("<<네이버 로그인 진잉ㅂ>>");
		
		return "member/naverLogin"; //jsp 호출
	}
	
	//카카오 로그아웃 처리
	@RequestMapping("/member/logoutKakao")
	public String logoutKakao(Model model, HttpSession session) {
		
		model.addAttribute("kakao_apikey",kakao_apikey);
		session.invalidate();
		
		return "member/logout"; //jsp 호출
	}
	
	//로그인 전용 프로필 사진 출력 (마이페이지 사용)
	@RequestMapping("/member/photoView")
	public String photoView(Model model, HttpServletRequest request, HttpSession session) {
		
		MemberVO user = (MemberVO)session.getAttribute("user"); //로그인 정보
		
		if(user==null) { //미로그인 시
			getBasicProfileImage(request, model);
		} else { //로그인 시
			MemberVO memberVO = memberService.selectMember(user.getMem_num());
			viewProfile(memberVO, request, model);
		}
		return "imageView"; //view 클래스로
	}
	
	//회원번호 지정 프로필 사진 출력 (게시판 사용)
	@RequestMapping("/member/viewProfile")
	public String getProfileByMem_num(@RequestParam int mem_num, HttpServletRequest request, Model model) {
		
		MemberVO memberVO = memberService.selectMember(mem_num);
		viewProfile(memberVO, request, model);
		
		return "imageView";
	}
	
	//프로필 사진 처리
	public void viewProfile(MemberVO memberVO, HttpServletRequest request, Model model) {
		if(memberVO==null||memberVO.getMem_photoname()==null) {
			getBasicProfileImage(request, model);
		} else {
			model.addAttribute("imageFile",memberVO.getMem_photo());
			model.addAttribute("filename",memberVO.getMem_photoname());
		}
	}
	
	//기본 프로필 사진 처리
	public void getBasicProfileImage(HttpServletRequest request, Model model) {
		byte[] readbyte = FileUtil.getBytes(request.getServletContext().getRealPath("/image_bundle/face.webp"));
		model.addAttribute("imageFile",readbyte);
		model.addAttribute("filename","face.png");
	}
	
}