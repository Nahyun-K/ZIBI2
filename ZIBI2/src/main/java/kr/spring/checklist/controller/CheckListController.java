package kr.spring.checklist.controller;

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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.checklist.service.CheckListService;
import kr.spring.checklist.vo.CheckListVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PageUtil;
import kr.spring.util.PageUtil_ye;
import kr.spring.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CheckListController {
	@Autowired
	private CheckListService checkListService;
	/*=================================
	 * 체크리스트 글 등록
	 *=================================*/
	//자바빈(VO) 초기화
	@ModelAttribute
	public CheckListVO initCommand() {
		return new CheckListVO();
	}
	
	//등록 폼 호출
	@GetMapping("/checklist/checkwrite")
	public String form() {
		return "checkWrite";
	}
	
	//전송된 데이터 처리
	@PostMapping("/checklist/checkwrite")
	public String submit(@Valid CheckListVO checkListVO, BindingResult result,
			             HttpServletRequest request,HttpSession session,
			             Model model) 
			            		 throws IllegalStateException, IOException {
		log.debug("<<체크리스트 글 저장>> : " + checkListVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			for(FieldError error : result.getFieldErrors()) {
				log.debug("<<에러 필드>> : " + error.getField());
			}
			return form();
		}
		
		//회원 번호 셋팅
		MemberVO vo = (MemberVO)session.getAttribute("user");
		checkListVO.setMem_num(vo.getMem_num());
		//파일 업로드
		checkListVO.setRoom_filename(FileUtil.createFile(request, checkListVO.getUpload()));
		//글 등록
		checkListService.insertCheckList(checkListVO);
		
		//View에 표시할 메시지
		model.addAttribute("message", "글 등록이 완료되었습니다.");
		model.addAttribute("url", request.getContextPath()+"/checklist/checkList");
		
		return "common/resultAlert";
	}
	
	/*=================================
	 * 체크리스트 글 목록
	 *=================================*/
	@RequestMapping("/checklist/checkList")
	public ModelAndView process(@RequestParam(value="pageNum",defaultValue="1") int currentPage,
			                   String keyfield,String keyword) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//전체/검색 레코드수
		int count = checkListService.selectRowCount(map);
		log.debug("<<count>> : " + count);
		
		PageUtil_ye page = new PageUtil_ye (keyfield,keyword,currentPage,count,20,10,"list");
		
		List<CheckListVO> list = null;
		if(count > 0) {
			map.put("start",page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = checkListService.selectList(map);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("checkList");
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("page", page.getPage());
		
		return mav;
	}
	/*=================================
	 * 체크리스트 글 상세
	 *=================================*/
	@RequestMapping("/checklist/checkDetail")
	public ModelAndView process(@RequestParam int check_id) {
		log.debug("<<체크리스트 글 상세 check_id>> : " + check_id);
		
		CheckListVO checkList = checkListService.selectCheckList(check_id);
		//제목에 태그를 허용하지 않음
		checkList.setRoom_name(StringUtil.useNoHtml(checkList.getRoom_name()));
		                        //타일스 설정명,속성명,속성값
		return new ModelAndView("checkListView","checkList",checkList);
	}
	
	/*=================================
	 * 파일 다운로드
	 *=================================*/
	@RequestMapping("/checkList/file")
	public ModelAndView download(@RequestParam int check_id,
			                     HttpServletRequest request) {
		CheckListVO checklist = checkListService.selectCheckList(check_id);
		
		//파일을 절대경로에서 읽어들여 byte[]로 변환
		byte[] downloadFile = FileUtil.getBytes(
				 request.getServletContext().getRealPath("/upload")
				                            +"/"+checklist.getRoom_filename());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("downloadView");
		mav.addObject("downloadFile", downloadFile);
		mav.addObject("filename", checklist.getRoom_filename());
	
		return mav;
	}
}
