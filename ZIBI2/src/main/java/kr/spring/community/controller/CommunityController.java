package kr.spring.community.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.community.service.CommunityService;
import kr.spring.community.vo.CommunityVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PageUtil;
import kr.spring.util.PageUtil_ye;
import kr.spring.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CommunityController {
	@Autowired
	private CommunityService communityService;
	
	/*=================================
	 * 게시판 글 등록
	 *=================================*/
	//자바빈(VO) 초기화
	@ModelAttribute
	public CommunityVO initCommand() {
		return new CommunityVO();
	}
	
	//등록 폼 호출
	@GetMapping("/community/communitywrite")
	public String form() {
		return "communityWrite";
	}
	
	//전송된 데이터 처리
	@PostMapping("/community/write")
	public String submit(@Valid CommunityVO communityVO, BindingResult result,
			             HttpServletRequest request,HttpSession session,
			             Model model) 
			            		 throws IllegalStateException, IOException {
		log.debug("<<게시판 글 저장>> : " + communityVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		
		//회원 번호 셋팅
		MemberVO vo = (MemberVO)session.getAttribute("user");
		communityVO.setMem_num(vo.getMem_num());
		//파일 업로드
		communityVO.setCommunity_filename(FileUtil.createFile(request, communityVO.getUpload()));
		//글쓰기
		communityService.insertCommunity(communityVO);
		
		//View에 표시할 메시지
		model.addAttribute("message", "글쓰기가 완료되었습니다.");
		model.addAttribute("url", request.getContextPath()+"/community/list");
		
		return "common/resultAlert";
	}
	
	/*=================================
	 * 게시판 글 목록
	 *=================================*/
	@RequestMapping("/community/list")
	public ModelAndView process(
			       @RequestParam(value="pageNum",defaultValue="1") int currentPage,
			       @RequestParam(value="order",defaultValue="") String order,
			       @RequestParam(value="community_category",defaultValue="") Integer community_category,
			       String keyfield, String keyword) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		map.put("community_category", community_category);
		
		//전체/검색 레코드수
		int count = communityService.selectRowCount(map);
		log.debug("<<count>> : " + count);
		
		PageUtil_ye page = new PageUtil_ye (keyfield,keyword,currentPage,
				                     count,20,10,"list","&community_category="+community_category+"&order="+order);
		
		List<CommunityVO> list = null;
		if(count > 0) {
			map.put("order",order);
			map.put("start",page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = communityService.selectList(map);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("communityList");
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("page", page.getPage());
		
		return mav;
	}
	/*=================================
	 * 게시판 글 상세
	 *=================================*/
	@RequestMapping("/community/detail")
	public ModelAndView process(@RequestParam int community_num) {
		log.debug("<<게시판 글 상세 community_num>> : " + community_num);
		
		//해당 글의 조회수 증가
		communityService.updateHit(community_num);
		
		CommunityVO community = communityService.selectCommunity(community_num);
		//제목에 태그를 허용하지 않음
		community.setCommunity_title(StringUtil.useNoHtml(community.getCommunity_title()));
		                        //타일스 설정명,속성명,속성값
		return new ModelAndView("communityView","community",community);
	}
	
	/*=================================
	 * 파일 다운로드
	 *=================================*/
	@RequestMapping("/community/file")
	public ModelAndView download(@RequestParam int community_num,
			                     HttpServletRequest request) {
		CommunityVO community = communityService.selectCommunity(community_num);
		
		//파일을 절대경로에서 읽어들여 byte[]로 변환
		byte[] downloadFile = FileUtil.getBytes(
				 request.getServletContext().getRealPath("/upload")
				                            +"/"+community.getCommunity_filename());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("downloadView");
		mav.addObject("downloadFile", downloadFile);
		mav.addObject("filename", community.getCommunity_filename());
	
		return mav;
	}
	/*=================================
	 * 게시판 글 수정
	 *=================================*/
	//수정 폼 호출
	@GetMapping("/community/update")
	public String formUpdate(@RequestParam int community_num,Model model) {
		CommunityVO communityVO = communityService.selectCommunity(community_num);
		
		model.addAttribute("communityVO", communityVO);
		
		return "communityModify";
	}
	//수정 폼에서 전송된 데이터 처리
	@PostMapping("/community/update")
	public String submitUpdate(@Valid CommunityVO communityVO,
			                   BindingResult result,
			                   HttpServletRequest request,
			                   Model model) throws IllegalStateException, IOException {
		log.debug("<<글 수정>> : " + communityVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			//title 또는 content가 입력되지 않아 유효성 체크에 걸리면
			//파일 정보를 잃어버리기 때문에 폼을 호출할 때 다시 셋팅해주어야 함
			CommunityVO vo = communityService.selectCommunity(communityVO.getCommunity_num());
			communityVO.setCommunity_filename(vo.getCommunity_filename());
			return "communityModify";
		}
		
		//DB에 저장된 파일 정보 구하기
		CommunityVO db_community = communityService.selectCommunity(
				communityVO.getCommunity_num());
		
		//파일명 셋팅
		communityVO.setCommunity_filename(FileUtil.createFile(
				                 request, communityVO.getUpload()));
		
		//글 수정
		communityService.updateCommunity(communityVO);
		
		//전송된 파일이 있을 경우 이전 파일 삭제
		if(communityVO.getUpload() != null && !communityVO.getUpload().isEmpty()) {
			//수정전 파일 삭제 처리
			FileUtil.removeFile(request, db_community.getCommunity_filename());
		}
		
		//View에 표시할 메시지
		model.addAttribute("message", "글 수정 완료!!");
		model.addAttribute("url", 
		           request.getContextPath()+"/community/detail?community_num="
		                                         +communityVO.getCommunity_num());
		
		return "common/resultAlert";
	}
	/*=================================
	 * 게시판 글 삭제
	 *=================================*/
	@RequestMapping("/community/delete")
	public String submitDelete(@RequestParam int community_num,
			                   HttpServletRequest request) {
		log.debug("<<게시판 글 삭제 community_num>> : " + community_num);
		
		//DB에 저장된 파일 정보 구하기
		CommunityVO db_community = communityService.selectCommunity(community_num);
		
		//글 삭제
		communityService.deleteCommunity(community_num);
		
		if(db_community.getCommunity_filename() != null) {
			//파일 삭제
			FileUtil.removeFile(request, db_community.getCommunity_filename());
		}
		
		return "redirect:/community/list";
	}
}