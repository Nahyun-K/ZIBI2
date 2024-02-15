package kr.spring.book.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
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

import kr.spring.book.service.BookService;
import kr.spring.book.vo.BookMatchingVO;
import kr.spring.book.vo.BookReplyVO;
import kr.spring.book.vo.BookScrapVO;
import kr.spring.book.vo.BookVO;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PageUtil_book;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BookAjaxController {
	@Autowired
	private BookService bookService;
	@Autowired
	private MemberService memberService;
	@Autowired
	JavaMailSenderImpl mailSender;
	
	//발송 이메일 호출
	@Value("${spring.mail.username")
	private String fromemail;

	/*-- 부모글 수정 시 썸네일 삭제 --*/
	@RequestMapping("/book/deleteFile")
	@ResponseBody
	public Map<String,String> deleteFile(int book_num,
										HttpSession session,
										HttpServletRequest request){
		Map<String,String> mapJson = new HashMap<String, String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			BookVO vo = bookService.selectBook(book_num);
			
			bookService.deleteFile(book_num);
			FileUtil.removeFile(request, vo.getBook_thumbnailName());
			
			mapJson.put("result", "success");
		}
		return mapJson;
	}
	
	/*-- 모임 참여하기 --*/
	@PostMapping("/book/bookApply")
	@ResponseBody
	public Map<String,String> applySubmit(@RequestParam String email,
										@RequestParam int book_num,
										@RequestParam int apply_num,
										@RequestParam int book_state,
										@RequestParam String apply_gatheringDate,
										@RequestParam String apply_title,
										@RequestParam String apply_address1,
										HttpSession session){
		
		Map<String,String> mapJson = new HashMap<String,String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//일정 중복 여부 체크
			BookMatchingVO db_match = bookService.selectMatch(apply_gatheringDate,apply_num);
			if(db_match!=null) {
				mapJson.put("result", "duplicated");
			}else {
				//예약 신청 정보 세팅
				BookMatchingVO bookMatchingVO = new BookMatchingVO();
				bookMatchingVO.setBook_num(book_num);
				bookMatchingVO.setApply_num(apply_num);
				bookMatchingVO.setBook_state(book_state);
				bookMatchingVO.setApply_gatheringDate(apply_gatheringDate);
				bookMatchingVO.setApply_title(apply_title);
				bookMatchingVO.setApply_address1(apply_address1);
				
				//예약 신청 완료
				bookService.insertMatch(bookMatchingVO);
				
				//이메일 내용 세팅
				MemberVO member = memberService.selectMember(apply_num);
				String nickname = member.getMem_nickname();
				
				//예약 완료 이메일 양식
				String toMail = email;
				String title = "[ZIBI] 소모임 참여 신청 안내";
				String content = "<div><h2>"+ nickname + "님의 소모임 예약이 완료되었습니다.</h2>";
					   content += "변경 사항이 발생할 수 있으니 ZIBI [나의 모임]에서 꼭 확인하세요!";
					   content += "<h3 style=\'color:#32a77b;margin:10px 0;\'>신청한 모임</h3>";
					   content += "✅ <b>참여 모임</b> : " + apply_title + "<br>";
					   content += "✅ <b>모임 장소</b> : " + apply_address1 + "<br>";
					   content += "✅ <b>참여 일자</b> : " + apply_gatheringDate + "<br></div>";
				
				try {
					MimeMessage message = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
					helper.setFrom(fromemail);
					helper.setTo(toMail);
					helper.setSubject(title);
					helper.setText(content, true);
					mailSender.send(message);
					
					mapJson.put("result", "apply");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return mapJson;
	}
	
	/*-- 모임 참여 취소하기 --*/
	@PostMapping("/book/applyCancel")
	@ResponseBody
	public Map<String,String> applyCancel(@RequestParam int book_num,
										@RequestParam int apply_num,
										HttpSession session){
		
		Map<String,String> mapJson = new HashMap<String,String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//참여 취소 처리
			bookService.deleteMatch(book_num, apply_num);
			mapJson.put("result", "cancel");
		}
		return mapJson;
	}

	/*-- 모임 참여 승인하기 --*/
	@PostMapping("/book/applyApprove")
	@ResponseBody
	public Map<String,String> applyApprove(@RequestParam int book_num,
										@RequestParam int apply_num,
										HttpSession session){
		
		Map<String,String> mapJson = new HashMap<String, String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//참여 승인 처리
			bookService.approveMatch(book_num, apply_num);
			mapJson.put("result", "approve");
		}
		return mapJson;
	}
	
	/*-- 모임 참여 거절하기 --*/
	@PostMapping("/book/applyDeny")
	@ResponseBody
	public Map<String,String> denyApprove(@RequestParam int book_num,
										@RequestParam int apply_num,
										HttpSession session){
		
		Map<String,String> mapJson = new HashMap<String, String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//참여 거절 처리
			bookService.denyMatch(book_num, apply_num);
			mapJson.put("result", "deny");
		}
		return mapJson;
	}
	
	/*-- 모집 마감하기(참여 일괄 거절) --*/
	@PostMapping("/book/applyClose")
	@ResponseBody
	public Map<String,String> applyClose(@RequestParam int book_num, HttpSession session){
		Map<String,String> mapJson = new HashMap<String, String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//모집 완료 처리
			bookService.denyAllMatch(book_num);
			mapJson.put("result", "close");
		}
		return mapJson;
	}
	
	/*-- 모임 완료하기 --*/
	@PostMapping("/book/bookComplete")
	@ResponseBody
	public Map<String,String> bookComplete(@RequestParam int book_num, HttpSession session){
		Map<String,String> mapJson = new HashMap<String, String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//모임 완료 처리
			bookService.updateOnoff1(book_num);
			mapJson.put("result", "complete");
		}
		return mapJson;
	}
	
	/*-- 새로 모집하기 --*/
	@PostMapping("/book/bookReset")
	@ResponseBody
	public Map<String,String> bookReset(@RequestParam int book_num, HttpSession session){
		Map<String,String> mapJson = new HashMap<String, String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//새로 모집 처리
			bookService.resetOnoff(book_num);
			mapJson.put("result", "reset");
		}
		
		return mapJson;
	}
	
	/*-- 스크랩 읽기 --*/
	@RequestMapping("/book/getScrap")
	@ResponseBody
	public Map<String,Object> getScrap(BookScrapVO scrap, HttpSession session){
		Map<String,Object> mapJson = new HashMap<String, Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("status", "noScrap");
		}else if(user != null && bookService.selectBook(scrap.getBook_num()).getMem_num() == user.getMem_num()){
			mapJson.put("status", "noScrap");
		}else {
			//로그인한 회원의 회원번호 세팅
			scrap.setMem_num(user.getMem_num());
			
			//스크랩 존재 여부 확인
			BookScrapVO db_scrap = bookService.selectScrap(scrap);
			if(db_scrap!=null) {
				mapJson.put("status", "yesScrap");
			}else {
				mapJson.put("status", "noScrap");
			}
		}
		//스크랩 개수
		mapJson.put("count", bookService.selectScrapCount(scrap.getBook_num()));
		return mapJson;
	}
	
	/*-- 스크랩 등록/삭제 --*/
	@RequestMapping("/book/clickScrap")
	@ResponseBody
	public Map<String,Object> clickScrap(BookScrapVO scrap, HttpSession session){
		Map<String,Object> mapJson = new HashMap<String, Object>();

		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
			mapJson.put("result", "logout");
		}else if(user != null && bookService.selectBook(scrap.getBook_num()).getMem_num() == user.getMem_num()){
			mapJson.put("result", "bookWriter");
		}else {
			//로그인된 회원 번호 세팅
			scrap.setMem_num(user.getMem_num());
			
			//스크랩 존재 여부 확인
			BookScrapVO db_scrap = bookService.selectScrap(scrap);
			
			if(db_scrap!=null) {
				bookService.deleteScrap(scrap);
				mapJson.put("status", "noScrap");
			}else {
				bookService.insertScrap(scrap);
				mapJson.put("status", "yesScrap");
			}
			mapJson.put("result", "success");
			mapJson.put("count", bookService.selectScrapCount(scrap.getBook_num()));
		}
		return mapJson;
	}
	
	/*-- 댓글 등록 --*/
	@RequestMapping("/book/insertReply")
	@ResponseBody
	public Map<String,String> insertReply(BookReplyVO bookReplyVO,
										HttpSession session,
										HttpServletRequest request){
		
		Map<String,String> mapJson = new HashMap<String, String>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			bookReplyVO.setMem_num(user.getMem_num());
			bookReplyVO.setBook_repIp(request.getRemoteAddr());
			//댓글 등록
			bookService.insertReply(bookReplyVO);
			
			mapJson.put("result", "success");
		}
		return mapJson;
	}
	
	/*-- 댓글 목록 --*/
	@RequestMapping("/book/listReply")
	@ResponseBody
	public Map<String,Object> getRep(
			@RequestParam(value="pageNum",defaultValue = "1") int currentPage,
			@RequestParam(value="rowCount",defaultValue = "50") int rowCount,
			@RequestParam int book_num, HttpSession session){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("book_num", book_num);
		
		//전체 레코드 수
		int rpcount = bookService.selectRepCount(map);
		//페이지 처리
		PageUtil_book rppage = new PageUtil_book(currentPage, rpcount, rowCount);
		
		List<BookReplyVO> rplist = null;
		if(rpcount > 0) {
			map.put("rpstart", rppage.getStartRow());
			map.put("rpend", rppage.getEndRow());
			rplist = bookService.selectListReply(map);
		}else {
			rplist = Collections.emptyList();
		}
		
		Map<String,Object> mapJson = new HashMap<String, Object>();
		mapJson.put("rpcount", rpcount);
		mapJson.put("rplist", rplist);
		
		//로그인한 회원정보 세팅
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user!=null) {
			mapJson.put("user_num", user.getMem_num());
			mapJson.put("mem_nick", user.getMem_nickname());
		}
		
		return mapJson;
	}
	
	/*-- 대댓글 등록 --*/
	@RequestMapping("/book/insertReReply")
	@ResponseBody
	public Map<String,String> insertReReply(@RequestParam int book_num,
										@RequestParam String book_rerep,
										@RequestParam int ref_rep_num,
										HttpSession session,
										HttpServletRequest request){
		
		Map<String,String> mapJson = new HashMap<String, String>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			BookReplyVO bookReplyVO = new BookReplyVO();
			bookReplyVO.setMem_num(user.getMem_num());
			bookReplyVO.setBook_repIp(request.getRemoteAddr());
			bookReplyVO.setRef_rep_num(ref_rep_num);
			bookReplyVO.setBook_num(book_num);
			bookReplyVO.setBook_rep(book_rerep);
			
			//대댓글 등록
			bookService.insertReplies(bookReplyVO);
			
			mapJson.put("result", "success");
		}
		
		return mapJson;
	}
	
	/*-- 댓글 삭제 --*/
	@RequestMapping("/book/deleteReply")
	@ResponseBody
	public Map<String,String> deleteReply(@RequestParam int rep_num,
										HttpSession session){
		
		Map<String,String> mapJson = new HashMap<String, String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		BookReplyVO db_reply = bookService.selectReply(rep_num);
		if(user==null) {
			mapJson.put("result", "logout");
		}else if(user!=null && user.getMem_num()==db_reply.getMem_num()) {
			bookService.deleteReply(rep_num);
			mapJson.put("result", "success");
		}else {
			mapJson.put("result", "wrongAccess");
		}
		
		return mapJson;
	}
	
}