package kr.spring.helper.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.helper.service.HelperService;
import kr.spring.helper.vo.HelperReplyVO;
import kr.spring.helper.vo.HelperScrapVO;
import kr.spring.helper.vo.HelperVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PageUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HelperAjaxController {
	@Autowired HelperService helperService;

	/* 부모글 스크랩 읽기 */
	@RequestMapping("/helper/getScrap")
	@ResponseBody
	public Map<String, Object> getScrap(HelperScrapVO scrap, HttpSession session){
		log.debug("<재능기부 스크랩> : " + scrap);

		Map<String, Object> mapJson = new HashMap<String, Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("status", "noScrap");
		}else {
			//로그인된 회원번호 셋팅
			scrap.setMem_num(user.getMem_num());

			HelperScrapVO helperScrap = helperService.selectscrap(scrap);
			//스크랩 표시
			if(helperScrap!=null) {
				mapJson.put("status", "yesScrap");
			}else {
				mapJson.put("status", "noScrap");
			}
		}
		//스크랩수
		mapJson.put("count", helperService.selectScrapCount(scrap.getHelper_num()));

		return mapJson;
	}

	/* 부모글 스크랩 등록&삭제 */
	@RequestMapping("/helper/writeScrap")
	@ResponseBody
	public Map<String, Object> writeScrap(HelperScrapVO scrap, HttpSession session){
		log.debug("<부모글 스크랩 등록&삭제> : " + scrap);

		Map<String, Object> mapJson = new HashMap<String, Object>();

		//로그인 여부 확인 
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//로그인 된 회원번호 셋팅
			scrap.setMem_num(user.getMem_num());

			//이전에 스크랩 등록했는지 여부
			HelperScrapVO helperScrapVO = helperService.selectscrap(scrap);
			if(helperScrapVO!=null) {
				helperService.deleteScrap(scrap);//스크랩 삭제
				mapJson.put("status", "noScrap");
			}else {//스크랩 미등록
				helperService.insertScrap(scrap);
				mapJson.put("status", "yesScrap");
			}
			mapJson.put("result", "success");
			mapJson.put("count", helperService.selectScrapCount(scrap.getHelper_num()));
		}
		return mapJson;
	}

	/*------------------해결중&해결완료 토글-------------------*/

	/* 해결중&해결완료 읽기*/
	@RequestMapping("/helper/getSol")
	@ResponseBody
	public Map<String, Object> getSolution(HelperVO solution){
		log.debug("<<helper_num>> :" + solution.getHelper_num());

		Map<String, Object> mapJson = new HashMap<String, Object>();

		//해결여부 읽어오기
		HelperVO helpersol = helperService.selectSolution(solution);
		log.debug("<<HelperVO>> : " + helpersol);
		log.debug("<해결완료 또 안 됨> : " + helpersol);
		mapJson.put("status", "noSol");//기본값을 해결중으로 띄움
		//해결여부 표시
		if(helpersol!=null) {
			if(helpersol.getHelper_solution()==1) {
				mapJson.put("status", "yesSol");
			}else {
				mapJson.put("status", "noSol");
			}
		}
		return mapJson;
	}

	/* 해결중&해결완료 수정*/
	@RequestMapping("/helper/updateSol")
	@ResponseBody
	public Map<String, Object> getSol(HelperVO sol, HttpSession session){
		log.debug("<해결토글> : " + sol);

		Map<String, Object> mapJson = new HashMap<String, Object>();

		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("status", "noSol");
		}else {
			//로그인된 회원번호 셋팅
			sol.setMem_num(user.getMem_num());

			//이전에 버튼을 클릭했는지 여부 - 토글형태로 하기 위해
			HelperVO helperSol = helperService.selectSolution(sol);

			//변경 결과에 따라 다른 결과 전송
			if(helperSol.getHelper_solution() == 0) {//해결완료로 수정
				helperService.updateSolution1(sol);
				mapJson.put("status", "yesSol");
			}else {
				helperService.updateSolution0(sol);
				mapJson.put("status", "noSol");
			}
		}
		return mapJson;
	}


	/*------------------ 댓글 -------------------*/

	/*댓글 등록*/
	@RequestMapping("/helper/writeReply")
	@ResponseBody
	public Map<String, Object> writeReply(HelperReplyVO helperReplyVO,
			HttpSession session,
			HttpServletRequest request){
		log.debug("<댓글 등록 HelperReplyVO> : " + helperReplyVO);

		Map<String, Object> mapJson = new HashMap<String, Object>();

		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//회원번호 등록
			helperReplyVO.setMem_num(user.getMem_num());
			//ip 등록
			helperReplyVO.setRe_ip(request.getRemoteAddr());
			//댓글 등록
			helperService.insertReply(helperReplyVO);
			mapJson.put("result", "success");
		}
		return mapJson;
	}

	/*댓글 목록*/
	@RequestMapping("/helper/listReply")
	@ResponseBody				
	public Map<String, Object> getList(//전달받는 데이터
			@RequestParam(value="pageNum",defaultValue="1") int currentPage,
			@RequestParam(value="rowCount",defaultValue="10") int rowCount,
			@RequestParam int helper_num, HttpSession session){
		log.debug("<<댓글 목록 board_num>> : " + helper_num);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("helper_num", helper_num);

		//전체 레코드 수
		int count = helperService.selectRowCountReply(map);
		//페이지 처리 - 더보기 형식을 구하기 위한 start와 end를 구하는 형식
		PageUtil page = new PageUtil(currentPage,count,rowCount);

		List<HelperReplyVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			list = helperService.selectListReply(map);
		}else {//페이지 없으면 비어있게 표시
			list = Collections.emptyList();
		}

		Map<String,Object> mapJson = new HashMap<String, Object>();
		mapJson.put("count", count);
		mapJson.put("list", list);

		//로그인한 회원정보 셋팅
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user!=null) {
			mapJson.put("user_num", user.getMem_num());
		}

		return mapJson;
	}

	/*댓글 수정*/
	@RequestMapping("/helper/updateReply")
	@ResponseBody
	public Map<String, String> modifyReply(HelperReplyVO helperReplyVO,
			HttpSession session,
			HttpServletRequest request){
		log.debug("<댓글 수정 HelperReplyVO> : " + helperReplyVO);

		Map<String, String> mapJson = new HashMap<String, String>();

		MemberVO user = (MemberVO)session.getAttribute("user");

		HelperReplyVO db_reply = helperService.selectReply(helperReplyVO.getRe_num());
		if(user==null) {
			mapJson.put("result", "logout");
		}else if(user != null && user.getMem_num() == db_reply.getMem_num()) {//로그인한 회원번호와 작성자 회원번호 일치할 경우
			helperReplyVO.setRe_ip(request.getRemoteAddr());
			//댓글 수정
			helperService.updateReply(helperReplyVO);
			mapJson.put("result", "success");
		}else {
			mapJson.put("result", "wrongAccess");
		}
		return mapJson;
	}

	/*댓글 삭제*/
	@RequestMapping("helper/deleteReply")
	@ResponseBody
	public Map<String, String> deleteReply(@RequestParam int re_num,
			HttpSession session){
		log.debug("<댓글 삭제 re_num> : " + re_num);

		Map<String, String> mapJson = new HashMap<String, String>();

		MemberVO user = (MemberVO)session.getAttribute("user");

		HelperReplyVO db_reply = helperService.selectReply(re_num);
		if(user==null) {
			mapJson.put("result", "logout");
		}else if(user !=null && user.getMem_num()==db_reply.getMem_num()) {//로그인한 회원번호와 작성자 회원번호 일치할 경우
			helperService.deleteReply(re_num);
			mapJson.put("result", "success");
		}else {
			mapJson.put("result", "wrongAccess");
		}
		return mapJson;
	}
}






















