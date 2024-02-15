package kr.spring.second.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.second.service.SecondService;
import kr.spring.second.vo.SecondOrderVO;
import kr.spring.second.vo.SecondReviewVO;
import kr.spring.second.vo.SecondVO;
import kr.spring.secondchat.vo.ChatRoomVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PageUtil_second;
import kr.spring.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SecondController {
	@Autowired
	private SecondService secondService;
	
	@Autowired
	private MemberService memberService;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public SecondVO initCommand() {
		return new SecondVO();
	}
	
	/*================================
	 * 중고거래 글 등록
	 *================================*/
	//등록 폼 호출
	@GetMapping("/secondhand/write") 
	public String scwriteform() {
		return "secondWrite"; //타일스
	}
	
	//등록폼 전송된 데이터 처리 
	@PostMapping("/secondhand/write")
	public String scwritesubmit(@Valid SecondVO secondVO, BindingResult result,
						HttpServletRequest request , HttpSession session, Model model) throws IllegalStateException, IOException {
		log.debug("<<중고거래 글 저장>> : " + secondVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "secondWrite"; //타일스
		}
		
		//회원 번호 셋팅
		MemberVO vo = (MemberVO)session.getAttribute("user");
		
		secondVO.setMem_num(vo.getMem_num());
		//ip 셋팅
		secondVO.setSc_ip(request.getRemoteAddr());
		//파일 업로드
		secondVO.setSc_filename(FileUtil.createFile(request, secondVO.getUpload()));
		
		//글쓰기   
		secondService.insertSecond(secondVO);
		
		//View에 표시할 메시지 
		//model.addAttribute("message", "상품 등록이 완료되었습니다.");
		//model.addAttribute("url", request.getContextPath()+"/secondhand/secondList");
		
		return "redirect:/secondhand/list";
	}
	/*================================
	 * 중고거래 글 목록
	 *================================*/
	@RequestMapping("/secondhand/list")
	public ModelAndView process(
			@RequestParam(value="pageNum",defaultValue="1") int currentPage,
			@RequestParam(value="order",defaultValue="1") int order,//정렬 기능
			String keyfield, String keyword) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		
		//전체/검색 레코드 수
		int count = secondService.selectRowCount(map);
		log.debug("<<count>> : " + count);
		
		//페이지 처리		//keyfield는 null
		PageUtil_second page = new PageUtil_second(null,keyword,currentPage,count,4,10,"list");
		
		//목록 읽어오기
		List<SecondVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = secondService.selectList(map);
			
			// 각 요소에 대해 주소 추출
		    for (SecondVO second : list) {
		        if (second.getSc_address() != null) {
		            String extractedAddress = extractAddress(second.getSc_address());
		            second.setSc_address(extractedAddress);
		        }
		    }
			log.debug("<<중고거래 글 목록 list>> : " + list);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("secondMain"); //타일스
		mav.addObject("count",count);
		mav.addObject("list",list);
		mav.addObject("page",page.getPage());
		
		return mav;
	}
	/*================================
	 * 중고거래 글 상세
	 *================================*/
	@RequestMapping("/secondhand/detail")
	public ModelAndView process(@RequestParam int sc_num) {
		log.debug("<<중고거래 글 상세 sc_num>> : " + sc_num);
		Map<String,Object> map = new HashMap<String,Object>();
		//해당 글의 조회수 증가
		secondService.updateHit(sc_num);
		
		SecondVO second = secondService.selectSecond(sc_num);
		
		log.debug("<<글 상세 second>> : " + second);
		//제목에 태그를 허용하지 않음
		second.setSc_title(StringUtil.useNoHtml(second.getSc_title()));
		if(second.getSc_address()!=null) {
			String extractedAddress = extractAddress(second.getSc_address());
	        second.setSc_address(extractedAddress);
		}
		map.put("sc_num", sc_num);
		
		List<SecondVO> list1 = null;
		int count1 = secondService.selectSellRevCount(sc_num);
		log.debug("<<글 상세 판매자 후기 count1>> :" + count1);
		if(count1 > 0) {
			list1 = secondService.selectSellRevList(map);
			log.debug("<<글 상세 판매자 후기 list1>> : " + list1);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("secondDetail"); //타일스
		mav.addObject("second",second);
		mav.addObject("list1",list1);
		
		return mav;
	}//second에 mem_num, sc_num, sc_title, sc_content, sc_category,sc_price,sc_status,sc_way,sc_place,위도,경도,조회수,등록일 등 정보 있음
	
	
	//읍면동 ~리까지 정규표현식을 이용해서 추출함 ex) 서울시 송파구 장지동 901이 저장되어있다면 서울 송파구 장지동이라고 출력
	private String extractAddress(String originalAddress) {
        // 정규표현식 패턴
        Pattern pattern = Pattern.compile("([가-힣\\s]+[읍면동가-힣]+).*");

        // 정규표현식에 맞는 부분 추출
        Matcher matcher = pattern.matcher(originalAddress);

        // 주소 반환
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return originalAddress; // 매칭되는 부분이 없을 경우 원래 주소를 반환
        }
    }
    
	/*================================
	 * 중고거래 글 수정
	 *================================*/
	@GetMapping("/secondhand/update")		//데이터 전달받기 위해 Model 필요
	public String scformUpdate(@RequestParam int sc_num,Model model) {
		SecondVO secondVO = secondService.selectSecond(sc_num);
		log.debug("글 수정 폼 호출 secondVO>> : " +secondVO);
		model.addAttribute("secondVO",secondVO);
		
		return "secondModify";
	}
	
	//수정 폼에서 전송된 데이터 처리
	@PostMapping("/secondhand/update")
	public String scsubmitUpdate(@Valid SecondVO secondVO, BindingResult result,
								HttpServletRequest request, Model model) throws IllegalStateException, IOException{
		log.debug("<<중고거래 글 수정>> : " + secondVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			//title 또는 content가 입력되지 않아 유효성 체크에 걸리면
			//파일 정보를 잃어버리기 때문에 폼을 호출할 때 다시 셋팅해주어야 함
			
			//세팅 작업
			SecondVO vo = secondService.selectSecond(secondVO.getSc_num());
			secondVO.setSc_filename(vo.getSc_filename());
			return "secondModify";
		}
		//DB에 저장된 파일 정보 구하기
		SecondVO db_second = secondService.selectSecond(secondVO.getSc_num());
		log.debug("<<DB에 저장된 파일 정보 db_second>> : " + db_second);
		//파일명 셋팅		파일이 업로드되면 가공해서 세팅해줌	
		secondVO.setSc_filename(FileUtil.createFile(request, secondVO.getUpload()));
		//ip 셋팅
		secondVO.setSc_ip(request.getRemoteAddr());
		
		//글 수정
		secondService.updateSecond(secondVO);
		log.debug("<<중고거래 글 수정 완료 secondVO>> : " + secondVO);
		//전송된 파일이 있을 경우 이전 파일 삭제
		if(secondVO.getUpload() != null && !secondVO.getUpload().isEmpty()) {
			//수정전 파일 삭제 처리
			FileUtil.removeFile(request, db_second.getSc_filename());//이전 파일 삭제처리
		}

		return "redirect:/secondhand/detail?sc_num="+secondVO.getSc_num();
	}
	
	/*================================
	 * 중고거래 글 삭제
	 *================================*/
	@GetMapping("/secondhand/delete")
	public String scsubmitDelete(@RequestParam int sc_num, HttpServletRequest request) {
		log.debug("<<중고거래 글 삭제 sc_num>> : " + sc_num);
		//파일 삭제 처리
		//DB에 저장된 파일 정보 구하기
		SecondVO db_second = secondService.selectSecond(sc_num);
		
		//글 삭제 된 이후에 파일 삭제하기
		secondService.deleteSecond(sc_num);
		if(db_second.getSc_filename() != null) {
			//파일 삭제 
			FileUtil.removeFile(request, db_second.getSc_filename());
		}
		return "redirect:/secondhand/list";
	}
	
	/*================================
	 * 중고거래 내 상점
	 *================================*/
	//default - 판매내역 페이지
	@RequestMapping("/secondhand/secondsellList")
	public String managesellList(HttpSession session,Model model) {
		
		//회원번호 셋팅
		MemberVO user = (MemberVO)session.getAttribute("user");
		MemberVO memberVO = memberService.selectMember(user.getMem_num());//로그인한 mem_num회원정보 가져오기
		
		model.addAttribute("memberVO",memberVO);
		log.debug("<<판매내역 페이지 - memberVO >> : " + memberVO);
		return "secondsellList"; //타일즈
	}
	
	//구매내역 페이지
	@RequestMapping("/secondhand/secondbuyList")
	public String managebuyList(
			@RequestParam(value="pageNum",defaultValue="1") int currentPage,
			HttpSession session,Model model) {
		//회원번호 셋팅
		MemberVO user = (MemberVO)session.getAttribute("user");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mem_num",user.getMem_num());
		int count = secondService.selectBuyCount(map);
		log.debug("<<구매내역 count : " + count);
		PageUtil_second page = new PageUtil_second(null,null, currentPage, count, 2,10,"secondbuyList");
		
		List<SecondOrderVO> list = null;
		if(count > 0) {
			list = secondService.selectBuyList(map);
			log.debug("<<구매내역 list >> : " + list); 
		}
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());
		
		return "secondbuyList"; //타일즈
	}
	
	//찜 상품 페이지
	@RequestMapping("/secondhand/secondfavList")
	public String managefavList(
			@RequestParam(value="pageNum",defaultValue="1") int currentPage,
			HttpSession session,Model model) {
		//회원번호 셋팅
		MemberVO user = (MemberVO)session.getAttribute("user");
		int count = secondService.selectScFavCount(user.getMem_num());
		log.debug("<<찜 상품 count : " + count);
		Map<String,Object> map = new HashMap<String,Object>();
		PageUtil_second page = new PageUtil_second(null,null, currentPage, count, 2,10,"secondfavList");
		
		List<SecondVO> list = null;
		if(count > 0) {
			map.put("mem_num",user.getMem_num());
			map.put("start",page.getStartRow());
			map.put("end",page.getEndRow());
			list = secondService.selectScFavList(map);
			log.debug("<<찜 상품 list >> : " + list); 
		}
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());
		
		return "secondfavList"; //타일즈
	}
	/*================================
	 * 거래 후기 페이지
	 *================================*/
	@RequestMapping("/secondhand/secondreviewList")
	public String managereviewList(HttpSession session,Model model) {
		//회원번호 셋팅
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		int count = secondService.selectReviewCount(user.getMem_num());
		log.debug("<<거래 후기 count >> : " + count);
		
		List<SecondReviewVO> list = null;
		String seller_nickname = null;
		if(count > 0) {
			//로그인한 회원번호(판매자)
			map.put("mem_num",user.getMem_num());
			//로그인한 닉네임(판매자 닉네임)
			MemberVO member = memberService.selectMember(user.getMem_num());
			seller_nickname = member.getMem_nickname();
			//별점, 닉네임, 거래후기 등록일, 거래내용 가져와야함.
			list = secondService.selectReviewList(map);
			log.debug("<<거래 후기 페이지 list>> : " + list);
			
		}
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("seller_nickname",seller_nickname);
		return "secondreviewList"; //타일즈
	}

	/*================================
	 * 거래 후기 등록
	 *================================*/
	//거래 후기 작성 폼 호출
	@GetMapping("/secondhand/secondReviewWrite")
	public ModelAndView reviewForm(@RequestParam int sc_num,HttpSession session) {
		SecondVO second = secondService.selectSecond(sc_num);//secondVO에 sc_num, mem_nickname(판매자 닉네임) 들어있음
		log.debug("<<거래 후기 위한 판매자 닉네임 second>> : " + second);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("secondReviewWrite");
		mav.addObject("second",second);
		return mav;
	}
	//전송된 데이터 처리 
	@PostMapping("/secondhand/secondReviewWrite")
	public String submitScReview(@Valid SecondReviewVO secondReviewVO,HttpSession session,
					HttpServletRequest request, Model model) {
		log.debug("<<중고거래 후기 작성 폼 전송 후 데이터 secondReviewVO>> : " + secondReviewVO);
		//회원 번호 세팅
		MemberVO user = (MemberVO)session.getAttribute("user");
		secondReviewVO.setReviewer_num(user.getMem_num());
		//후기 작성자 ip 세팅
		secondReviewVO.setSc_rev_ip(request.getRemoteAddr());
		//후기 남기기
		secondService.insertScReview(secondReviewVO);
		
		model.addAttribute("message", "후기 작성이 완료되었습니다.");
		model.addAttribute("url", "list");
		
		return "common/resultAlert";
	}
	/*================================
	 * 내 상점 - 채팅 내역 페이지 
	 *================================*/
	@RequestMapping("/secondhand/secondBuyChatList")
	public String secondBuyChatList(HttpSession session,Model model) {
		//회원번호 셋팅
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mem_num", user.getMem_num());
		int count = secondService.selectBuyChatCount(map);
		log.debug("<<채팅 내역 count >> : " + count);
		
		List<ChatRoomVO> list = null;
		if(count > 0) {
			list = secondService.selectBuyChatList(map);
			log.debug("<<채팅 내역 페이지 list>> : " + list);
		}
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		return "secondBuyChatList"; //타일즈
	}
}

