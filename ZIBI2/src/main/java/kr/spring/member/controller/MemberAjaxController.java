package kr.spring.member.controller;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.FollowVO;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MemberAjaxController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Value("${spring.mail.username}")
	private String email;
	
	/*---------------------팔로우/언팔로우-----------------------*/
	//팔로우/언팔로우 읽기
	@RequestMapping("/member/getFollow")
	@ResponseBody
	public Map<String,Object> followProcess(HttpSession session, FollowVO followVO) {
		
		Map<String,Object> mapJson = new HashMap<String, Object>();
		MemberVO user = (MemberVO)session.getAttribute("user"); //로그인한 회원
		
		if(user==null) { //로그아웃
			mapJson.put("status","logout");
		} else if(user.getMem_num()==followVO.getMem_num()){ //나 자신인 경우
			mapJson.put("status","disabledFollow");
		} else {
			followVO.setFmem_num(user.getMem_num());
			FollowVO db_follow = memberService.selectFollow(followVO);
			
			if(db_follow!=null) { //이미 팔로우 되어 있음
				mapJson.put("status","yesFollow");
			} else { //팔로우 되어있지 않음
				mapJson.put("status","noFollow");
			}
		}
		
		mapJson.put("count",memberService.followCount(followVO.getMem_num()));
		
		return mapJson;
	}
	
	//팔로우,언팔로우
	@RequestMapping("/member/writeFollow")
	@ResponseBody
	public Map<String,Object> writeFollow(FollowVO followVO, HttpSession session){
		
		Map<String,Object> mapJson = new HashMap<String, Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		
		if(user==null) {
			mapJson.put("result", "logout");
		} else if(user.getMem_num()==followVO.getMem_num()){ //나 자신인 경우
			mapJson.put("status","disabledFollow");
		} else {
			followVO.setFmem_num(user.getMem_num()); //로그인된 사용자 번호
			FollowVO db_follow = memberService.selectFollow(followVO); //해당 회원의 팔로우 정보
			
			if(db_follow!=null) { //이미 팔로우 되어 있음 
				memberService.unfollowMember(followVO); //언팔로우
				mapJson.put("status","noFollow");
			} else {
				memberService.followMember(followVO); //팔로우
				mapJson.put("status","yesFollow");
			}
			
			mapJson.put("result","success");
		}
		
		mapJson.put("count",memberService.followCount(followVO.getMem_num()));
		
		return mapJson;
	}
	
	/*---------------------회원 탈퇴-----------------------*/
	//비밀번호 확인
	@RequestMapping("/member/quitPassword")
	@ResponseBody
	public Map<String,String> checkPassword(@RequestParam String input_password, HttpSession session) {
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		Map<String,String> mapJson = new HashMap<String, String>();
		
		if(user==null) {
			mapJson.put("result","logout");
		} else if( !user.checkPassword(input_password) ) { //비밀번호 불일치
			mapJson.put("result","passwordNotMatch");
		} else { //비밀번호 일치
			mapJson.put("result","passwordMatch");
		}
		return mapJson;
	}
	
	/*--------------------이메일 인증 html 생성-----------------------*/
	@PostMapping("/member/emailAuth")
	@ResponseBody
	public Map<String, Integer> emailAuth(@RequestParam String mem_email) {
		
		log.info("<<이메일 인증 - 사용자 입력 이메일>> : " + mem_email);
		
		Map<String, Integer> mapJson = new HashMap<String, Integer>();
		Random random = new Random();
		int checkNum = random.nextInt(888888)+111111; //랜덤 코드 생성
		
		String title = "ZIBI 회원가입 인증 이메일 입니다."; //이메일 제목
		String content = "<div style=\'text-align: border: 1px solid black; margin: 10px;\'>" //이메일 내용
						+ "<h2 style=\'text-align: center; padding: 5px\'>ZIBI 회원가입 인증 코드입니다</h2>"
						+ "<div style=\'text-align: center; padding: 5px\'>아래의 인증 번호 여섯 자리를<br>ZIBI의 인증코드란에 입력해주세요 😊</div>"
						+ "<h5 style=\'text-align: center; padding: 5px; color: #32a77b;\'>"
						+ checkNum
						+ "</h5>";
		
		sendEmail(mem_email, title, content); //이메일 전송
		mapJson.put("code", checkNum); //코드 확인을 위해 클라이언트단으로 전송
		
		return mapJson;
	}
	
	/*---------------------비밀번호 찾기-----------------------*/
	//비밀번호 찾기 폼
	@PostMapping("/member/findPassword")
	@ResponseBody
	public Map<String,String> findPassword(@RequestParam String mem_email){
		
		log.info("<<비밀번호 찾기 - 사용자 입력 이메일>> : " + mem_email);
		
		Map<String,String> mapJson = new HashMap<String, String>();
		
		//이메일 존재 유무 체크
		MemberVO db_member = memberService.checkEmail(mem_email);
		
		if(db_member!=null) {
			
			String password = randomPassword();
			log.info("<<비밀번호 찾기 - 임시 비밀번호>> : " + password);
			
			String title = "ZIBI 임시 비밀번호입니다."; //이메일 제목
			String content = "<div style=\'text-align: border: 1px solid black; margin: 10px;\'>" //이메일 내용
							+ "<h2 style=\'text-align: center; padding: 5px\'>ZIBI 임시 비밀번호입니다.</h2>"
							+ "<div style=\'text-align: center; padding: 5px\'>아래의 임시 비밀번호 여섯자리를<br>ZIBI 로그인 시 사용할 수 있습니다 🤗</div>"
							+ "<h5 style=\'text-align: center; padding: 5px; color: #32a77b;\'>"
							+ password
							+ "</h5>";
			
			sendEmail(mem_email, title, content); //이메일 전송
			
			db_member.setMem_password(password); //임시 비밀번호 세팅
			memberService.updatePassword(db_member); //비밀번호를 임시 비밀번호로 변경
			
			mapJson.put("result", "success");
		} else {
			mapJson.put("result", "fail");
		}
		
		return mapJson;
	}
	
	//임시 비밀번호 생성
	public String randomPassword() {
		
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; //임시 비밀번호 내용물로 사용할 문자
		
		SecureRandom rm = new SecureRandom(); //난수 생성기
		/* Random은 암호학적으로 안전하지 않기에 SecureRandom 클래스를 사용, Random 클래스는 난수에 패턴이 있으므로 사용 주의 */
		StringBuffer sb = new StringBuffer(); //임시 비밀번호 저장용 가변 길이 문자열 객체 생성
		
		for(int i=0 ; i<6 ; i++) { //6자리의 임시 비밀번호 생성
			int index = rm.nextInt(chars.length()); //문자열의 인덱스 무작위 반환
			sb.append(chars.charAt(index)); //설정한 문자열의 무작위로 발생된 인덱스 부분의 문자를 저장
		}
		
		return sb.toString();
	}
	
	/*---------------------이메일 전송-----------------------*/
	private void sendEmail(String mem_email, String title , String content) {
		
		String toMail = mem_email; //수신자
			
		try {
			MimeMessage message = mailSender.createMimeMessage(); //Spring에서 제공하는 mail API
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8"); //true - multpart message(사진 사용 가능)를 사용함
            
            helper.setFrom(email); //발신자
            helper.setTo(toMail); //수신자
            helper.setSubject(title); //이메일 제목
            helper.setText(content, true); //이메일 내용, true - html을 사용함
            
            mailSender.send(message); //이메일 전송
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*---------------------소셜 로그인/회원 가입-----------------------*/
	@RequestMapping("/member/loginSocial")
	@ResponseBody
	public Map<String,String> loginKakao(@RequestParam String mem_email,@RequestParam int mem_social, HttpSession session) {
		
		Map<String, String> mapJson = new HashMap<String, String>();
		MemberVO db_member = memberService.checkEmail(mem_email);
		
		if(db_member==null) { //회원가입 이력 없음 > 회원가입 + 로그인
			int mem_num = memberService.createMemNum();
			MemberVO memberVO = new MemberVO();
			
			memberVO.setMem_num(mem_num);
			memberVO.setMem_email(mem_email);
			
			if(mem_social==1) { //카카오
				memberVO.setMem_social(1); //소셜 회원 번호 생성
				memberVO.setMem_nickname("카카오"+mem_num); //닉네임 생성
			} else if(mem_social==2) { //네이버
				memberVO.setMem_social(2); //소셜 회원 번호 생성
				memberVO.setMem_nickname("네이버"+mem_num); //닉네임 생성
			}
			
			memberService.registerMember(memberVO); //회원가입
			session.setAttribute("user", memberVO); //로그인 처리
			
			mapJson.put("result","success");//로그인 성공 전달
			
			log.debug("<<소셜 회원가입>>"+memberVO);
		} else { //회원가입 되어있음 > 로그인
			
			if( db_member.getMem_social() != mem_social ) { //다른 방법으로 가입된 이메일인 경우
				mapJson.put("result","socialNotMatch"); //로그인 거절 전달
			} else {
				session.setAttribute("user",db_member); //로그인
				mapJson.put("result","success");//로그인 성공 전달
			}
		}
		return mapJson;
	}
	
	/*---------------------프로필 사진 업로드-----------------------*/
	@RequestMapping("/member/updateMyPhoto")
	@ResponseBody
	public Map<String,String> updateMyPhoto(HttpSession session, MemberVO memberVO){
		
		Map<String, String> mapJson = new HashMap<String, String>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		if(user==null) {
			mapJson.put("result","logout");
		} else {
			memberVO.setMem_num(user.getMem_num());
			memberService.updateProfileImages(memberVO);
			mapJson.put("result","success");
		}
		return mapJson;
	}
	
	/*-----------------------중복 체크-----------------------------*/
	//이메일 중복 체크 : 회원가입
	@RequestMapping("/member/checkEmail")
	@ResponseBody
	public Map<String,String> checkEmail(@RequestParam String mem_email, HttpSession session){
		
		Map<String, String> map = new HashMap<String, String>();
		MemberVO memberVO = memberService.checkEmail(mem_email); //이메일 중복 여부를 변수에 저장
		
		if(memberVO!=null) { //이메일이 중복인 경우
			map.put("result", "emailDuplicated");
		} else {
			if(!Pattern.matches("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",mem_email)) { //이메일 미중복, 패턴 불일치
				map.put("result", "notMatchPattern");
			} else { //이메일 미중복, 패턴 일치
				map.put("result", "emailNotFound");
			}
		}
		return map;
	}
	
	//닉네임 중복 체크 : 회원 가입
	@RequestMapping("/member/registerNickname")
	@ResponseBody
	public Map<String,String> registerNickname(@RequestParam String mem_nickname){
		
		Map<String, String> map = new HashMap<String, String>();
		MemberVO memberVO = memberService.checkNickname(mem_nickname); //닉네임 유무 체크
		
		if(memberVO!=null) { //닉네임 중복
			map.put("result", "nicknameDuplicated");
		} else { //닉네임 미중복
			if(!Pattern.matches("^[가-힣]*$",mem_nickname)) { //형식 불일치
				map.put("result", "notMatchPattern");
			} else { //패턴 일치
				map.put("result", "nicknameNotFound");
			}
		}
		return map;
	}
	
	//닉네임 중복 체크 : 마이페이지
	@RequestMapping("/member/checkNickname")
	@ResponseBody
	public Map<String,String> checkNickname(@RequestParam String mem_nickname, HttpSession session){
		
		Map<String, String> map = new HashMap<String, String>();
		MemberVO user = (MemberVO)session.getAttribute("user"); //로그인된 사용자 정보를 읽어 옴
		MemberVO memberVO = memberService.checkNickname(mem_nickname); //닉네임 유무 체크
		
		if(user==null) { //세션 만료 시
			map.put("result", "logout");
		} else if(mem_nickname.equals(user.getMem_nickname())) { //원래 닉네임 사용 가능
			map.put("result", "originalNickName");
		} else if(memberVO!=null) { //닉네임 중복
			map.put("result", "nicknameDuplicated");
		} else { //닉네임 미중복
			if(!Pattern.matches("^[가-힣]*$",mem_nickname)) { //형식 불일치
				map.put("result", "notMatchPattern");
			} else { //패턴 일치
				map.put("result", "nicknameNotFound");
			}
		}
		return map;
	}
	
	//연락처 중복 체크 : 마이페이지
	@RequestMapping("/member/checkPhone")
	@ResponseBody
	public Map<String,String> checkPhone(@RequestParam String mem_phone, HttpSession session){
		
		Map<String, String> map = new HashMap<String, String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user"); //로그인된 사용자 정보를 읽어 옴
		MemberVO memberVO = memberService.checkPhone(mem_phone); //파라미터로 받은 연락처 체크
		
		if(user==null) { //로그아웃
			map.put("result", "logout");
		} else if(mem_phone.equals(user.getMem_phone())) { //원래 연락처는 사용 가능
			map.put("result", "originalPhone");
		} else if(memberVO!=null) { //연락처 중복
			map.put("result", "phoneDuplicated");
		} else { //연락처 미중복
			if(!Pattern.matches("^010-([0-9]{3,4})-([0-9]{4})$",mem_phone)) { //형식 불일치
				map.put("result", "notMatchPattern");
			} else { //연락처 미중복
				map.put("result", "phoneNotFound");
			}
		}
		return map;
	}
}
