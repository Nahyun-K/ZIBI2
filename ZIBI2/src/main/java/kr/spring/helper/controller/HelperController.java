package kr.spring.helper.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.helper.service.HelperService;
import kr.spring.helper.vo.HelperReplyVO;
import kr.spring.helper.vo.HelperVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PageUtil_hp;
import kr.spring.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HelperController {
	@Autowired
	private HelperService helperService;

	/* 게시판 글 등록 */
	// 자바빈(VO)초기화
	@ModelAttribute
	public HelperVO initCommand() {
		return new HelperVO();
	}

	// 헬프미 글 등록 폼 호출
	@GetMapping("/helper/write")
	public String form() {
		return "helperWrite";// 타일스 설정
	}

	// 글 등록
	@PostMapping("/helper/write")
	public String submit(@Valid HelperVO helperVO, BindingResult result, HttpServletRequest request,
			HttpSession session, Model model) throws IllegalStateException, IOException {
		log.debug("<재능기부 헬프미 글 등록> : " + helperVO);

		// 유효성 체크 결과 오류가 있을 시 폼 호출
		if (result.hasErrors()) {
			return form();
		}

		// 회원번호 셋팅
		MemberVO vo = (MemberVO) session.getAttribute("user");
		helperVO.setMem_num(vo.getMem_num());
		// ip 셋팅
		helperVO.setHelper_ip(request.getRemoteAddr());
		// 파일 업로드 ?
		helperVO.setHelper_filename(FileUtil.createFile(request, helperVO.getUpload()));
		// 글작성
		helperService.insertHelper(helperVO);
		log.debug("<재능기부 헬프미 글 등록> : " + helperVO);

		// View에 표시할 메세지 지정
		model.addAttribute("message", "글이 작성되었습니다.");
		model.addAttribute("url", request.getContextPath() + "/helper/list");

		return "common/resultAlert";
	}

	/* 게시판 글 목록 */
	@RequestMapping("/helper/list")
	public ModelAndView process(@RequestParam(value = "pageNum", defaultValue = "1") int currentPage,
			@RequestParam(value = "order", defaultValue = "1") int order, 
			@RequestParam(value = "helper_category", defaultValue = "") String helper_category,
			@RequestParam(value = "helper_select", defaultValue = "") String helper_select,
			String keyfield, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		map.put("helper_category", helper_category);
		map.put("helper_select", helper_select);

		// 전체&검색 레코드 수
		int count = helperService.selectRowCount(map);
		log.debug("<count> : " + count);

		PageUtil_hp page = new PageUtil_hp(keyfield, keyword, currentPage, count, 3, 10, "list", 
									"&order=" + order + "&helper_category=" + helper_category + "&helper_select=" + helper_select);

		List<HelperVO> list = null;
		if (count > 0) {
			map.put("order", order);
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());

			list = helperService.selectList(map);
		}
		ModelAndView mav = new ModelAndView();

		mav.setViewName("helperList");// 타일스 설정
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("page", page.getPage());

		return mav;
	}

	/* 게시판 글 상세 */
	@RequestMapping("/helper/detail")
	public ModelAndView process(@RequestParam int helper_num) {
		log.debug("<재능기부 글 상세> : " + helper_num);

		// 해당 글의 조회수 증가
		helperService.updateHit(helper_num);

		HelperVO helper = helperService.selectHelper(helper_num);

		// 제목에 태그 허용하지 않음
		helper.setHelper_title(StringUtil.useNoHtml(helper.getHelper_title()));
		// 타일스설정명
		return new ModelAndView("helperView", "helper", helper);
	}
	
	/* 게시판 글 수정 */
	//수정폼 호출
	@GetMapping("/helper/update")
	public String formUpdate(@RequestParam int helper_num, Model model) {
		// 한 건의 데이터 받기
		HelperVO helperVO = helperService.selectHelper(helper_num);

		model.addAttribute("helperVO", helperVO);

		return "helperModify";// 타일스 설정
	}

	/* 부모글 업로드 파일 삭제 */
	@RequestMapping("/helper/deleteFile")
	@ResponseBody
	public Map<String, String> processFile(int helper_num, HttpSession session, HttpServletRequest request) {
		Map<String, String> mapJson = new HashMap<String, String>();

		// 로그인 체크
		MemberVO user = (MemberVO) session.getAttribute("user");
		if (user == null) {
			mapJson.put("result", "logout");
		} else {
			// 파일명 추출
			HelperVO vo = helperService.selectHelper(helper_num);

			// DB에서 먼저 지우기
			helperService.deleteFile(helper_num);

			// 업로드 경로의 파일 지우기
			FileUtil.removeFile(request, vo.getHelper_filename());

			mapJson.put("result", "success");
		}
		return mapJson;
	}

	// 수정 폼에서 전송된 데이터 처리
	@PostMapping("/helper/update")
	public String submitUpdate(@Valid HelperVO helperVO, BindingResult result, HttpServletRequest request, Model model)
			throws IllegalStateException, IOException {
		log.debug("<글 수정> : " + helperVO);

		// 유효성 체크 결과 오류가 있을 시 폼 호출
		if (result.hasErrors()) {
			
			// 한 건의 데이터 읽어옴
			HelperVO vo = helperService.selectHelper(helperVO.getHelper_num());
			helperVO.setHelper_filename(vo.getHelper_filename());
			return "helperModify";// 타일스 설정
		}
		// DB에 저장된 파일 정보 구하기
		HelperVO db_helper = helperService.selectHelper(helperVO.getHelper_num());
		// 파일명 셋팅
		helperVO.setHelper_filename(FileUtil.createFile(request, helperVO.getUpload()));
		// IP 셋팅
		helperVO.setHelper_ip(request.getRemoteAddr());

		// 글 수정
		helperService.updateHelper(helperVO);

		// 전송된 파일이 있을 경우 이전 파일 삭제
		if(helperVO.getUpload() != null && !helperVO.getUpload().isEmpty()) {
			// 수정전 파일 삭제 처리
			FileUtil.removeFile(request, db_helper.getHelper_filename());
		}

		model.addAttribute("message", "글이 수정되었습니다.");
		model.addAttribute("url", request.getContextPath() + "/helper/detail?helper_num=" + helperVO.getHelper_num());

		return "common/resultAlert";
	}

	/* 글 삭제 */
	@RequestMapping("/helper/delete")
	public String submitDelete(@RequestParam int helper_num, HttpServletRequest request) {
		log.debug("<글 삭제> : " + helper_num);

		// DB에 저장된 파일 정보 구하기
		HelperVO db_helper = helperService.selectHelper(helper_num);

		// 글 삭제
		helperService.deleteHelper(helper_num);

		if (db_helper.getHelper_filename() != null) {
			// 파일 삭제
			FileUtil.removeFile(request, db_helper.getHelper_filename());
		}
		return "redirect:/helper/list";
	}
}
