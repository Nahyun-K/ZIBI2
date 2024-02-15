package kr.spring.community.controller;

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

import kr.spring.community.service.CommunityService;
import kr.spring.community.vo.CommunityFavVO;
import kr.spring.community.vo.CommunityFollowVO;
import kr.spring.community.vo.CommunityReplyVO;
import kr.spring.community.vo.CommunityVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PageUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CommunityAjaxController {
	@Autowired
	private CommunityService communityService;
	
	/*=======================
	 * 부모글 업로드 파일 삭제
	 *=======================*/
	@RequestMapping("/community/deleteFile")
	@ResponseBody
	public Map<String,String> processFile(int community_num,
			                     HttpSession session,
			                     HttpServletRequest request){
		Map<String,String> mapJson = new HashMap<String,String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			CommunityVO vo = communityService.selectCommunity(community_num);
			
			communityService.deleteFile(community_num);
			FileUtil.removeFile(request, vo.getCommunity_filename());
			
			mapJson.put("result", "success");
		}
		
		return mapJson;
	}
	
	/*=======================
	 * 부모글 좋아요 읽기
	 *=======================*/
	@RequestMapping("/community/getFav")
	@ResponseBody
	public Map<String,Object> getFav(CommunityFavVO fav, HttpSession session){
		log.debug("<<게시판 좋아요 CommunityFavVO>> : " + fav);
		
		Map<String,Object> mapJson = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("status", "noFav");
		}else {
			//로그인된 회원번호 셋팅
			fav.setMem_num(user.getMem_num());
			
			CommunityFavVO communityFav = communityService.selectCommuFav(fav);
			if(communityFav!=null) {
				mapJson.put("status", "yesFav");
			}else {
				mapJson.put("status", "noFav");
			}
		}
		//좋아요수
		mapJson.put("count", communityService.selectFavCount(
				                          fav.getCommunity_num()));
		return mapJson;
	}
	/*=======================
	 * 부모글 좋아요 등록/삭제
	 *=======================*/
	@RequestMapping("/community/writeFav")
	@ResponseBody
	public Map<String,Object> writeFav(CommunityFavVO fav, HttpSession session){
		log.debug("<<부모글 좋아요 등록/삭제 CommunityFavVO>> : " + fav);
		
		Map<String,Object> mapJson = new HashMap<String,Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//로그인된 회원번호 셋팅
			fav.setMem_num(user.getMem_num());
			
			//이전에 좋아요를 등록했는지 여부 확인
			CommunityFavVO communityFavVO = communityService.selectCommuFav(fav);
			if(communityFavVO!=null) {//좋아요를 이미 등록
				communityService.deleteFav(fav);//좋아요 삭제
				mapJson.put("status", "noFav");
			}else {//좋아요 미등록
				communityService.insertFav(fav);//좋아요 등록
				mapJson.put("status", "yesFav");
			}
			mapJson.put("result", "success");
			mapJson.put("count", communityService.selectFavCount(
					                          fav.getCommunity_num()));
		}
		return mapJson;
	}
	
	/*=======================
	 * 댓글 등록
	 *=======================*/
	@RequestMapping("/community/writeReply")
	@ResponseBody
	public Map<String,String> writeReply(CommunityReplyVO communityReplyVO,
			                             HttpSession session,
			                             HttpServletRequest request){
		log.debug("<<댓글 등록 CommunityReplyVO>> : " + communityReplyVO);
		
		Map<String,String> mapJson = new HashMap<String,String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			//로그인 안 됨
			mapJson.put("result", "logout");
		}else {
			//회원번호 등록
			communityReplyVO.setMem_num(user.getMem_num());
			//댓글 등록
			communityService.insertReply(communityReplyVO);
			mapJson.put("result", "success");
		}
		return mapJson;
	}
	/*=======================
	 * 댓글 목록
	 *=======================*/
	@RequestMapping("/community/listReply")
	@ResponseBody
	public Map<String,Object> getList(
		@RequestParam(value="pageNum",defaultValue="1") int currentPage,
		@RequestParam(value="rowCount",defaultValue="10") int rowCount,
		@RequestParam int community_num,HttpSession session){
		log.debug("<<댓글 목록 community_num>> : " + community_num);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("community_num", community_num);
		
		//전체 레코드수
		int count = communityService.selectRowCountReply(map);
		//페이지 처리
		PageUtil page = new PageUtil(currentPage,count,rowCount);
		
		List<CommunityReplyVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			list = communityService.selectListReply(map);
		}else {
			list = Collections.emptyList();
		}
		
		Map<String,Object> mapJson = new HashMap<String,Object>();
		mapJson.put("count", count);
		mapJson.put("list", list);
		
		log.debug("<<댓글 목록>> : " + list);
		
		//로그인한 회원정보 셋팅
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user!=null) {
			mapJson.put("user_num", user.getMem_num());
		}		
		return mapJson;
	}
	
	/*=======================
	 * 댓글 수정
	 *=======================*/
	@RequestMapping("/community/updateReply")
	@ResponseBody
	public Map<String,String> modifyReply(CommunityReplyVO communityReplyVO, HttpSession session,HttpServletRequest request){
		
		log.debug("<<댓글 수정 CommunityReplyVO>>");
		
		Map<String,String> mapJson = new HashMap<String,String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		CommunityReplyVO db_reply = communityService.selectReply(communityReplyVO.getRe_num());
		if(user==null) {
			//로그인이 되지 않은 경우
			mapJson.put("result", "logout");
		}else if(user!=null && user.getMem_num()==db_reply.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호 일치
	
			//댓글 수정
			communityService.updateReply(communityReplyVO);
			mapJson.put("result", "success");
		}else {
			//로그인한 회원번호와 작성자 회원번호 불일치
			mapJson.put("result", "wroungAccres");
		}
		
		return mapJson;
	}
	
	/*=======================
	 * 댓글 삭제
	 *=======================*/
	@RequestMapping("/community/deleteReply")
	@ResponseBody
	public Map<String,String> deleteReply(@RequestParam int re_num, HttpSession session){
		log.debug("<<댓글 삭제 re_num>> : " + re_num);
		
		Map<String,String> mapJson = new HashMap<String,String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		CommunityReplyVO db_reply = communityService.selectReply(re_num);
		if(user==null) {
			//로그인이 되지 않은 경우
			mapJson.put("result", "logout");
		}else if(user!=null && user.getMem_num()==db_reply.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 일치
			communityService.deleteReply(re_num);
			mapJson.put("result", "success");
		}else {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			mapJson.put("result", "wrongAccess");
		}
		return mapJson;
	}
	
	/*=======================
	 * 부모글 팔로우 읽기
	 *=======================*/
	@RequestMapping("/community/getFollow")
	@ResponseBody
	public Map<String,Object> getFollow(CommunityFollowVO follow, HttpSession session){
		log.debug("<<커뮤니티 팔로우 CommunityFollowVO>> : " + follow);
		
		Map<String,Object> mapJson = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("status", "noFollow");
		}else {
			//로그인된 회원번호 셋팅
			follow.setMem_num(user.getMem_num());
			
			CommunityFollowVO communityFollow = communityService.selectFollow(follow);
			if(communityFollow!=null) {
				mapJson.put("status", "yesFollow");
			}else {
				mapJson.put("status", "noFollow");
			}
		}
		//좋아요수
		mapJson.put("count", communityService.selectFollowCount(
				follow.getMem_num()));
		return mapJson;
	}
	/*=======================
	 * 부모글 좋아요 등록/삭제
	 *=======================*/
	@RequestMapping("/community/writeFollow")
	@ResponseBody
	public Map<String,Object> writeFollow(CommunityFollowVO follow, HttpSession session){
		log.debug("<<부모글 팔로우 등록/삭제 CommunityFollowVO>> : " + follow);
		
		Map<String,Object> mapJson = new HashMap<String,Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//로그인된 회원번호 셋팅
			follow.setMem_num(user.getMem_num());
			
			//이전에 좋아요를 등록했는지 여부 확인
			CommunityFollowVO communityFollowVO = communityService.selectFollow(follow);
			if(communityFollowVO!=null) {//좋아요를 이미 등록
				communityService.deleteFollow(follow);//좋아요 삭제
				mapJson.put("status", "noFav");
			}else {//좋아요 미등록
				communityService.insertFollow(follow);//좋아요 등록
				mapJson.put("status", "yesFollow");
			}
			mapJson.put("result", "success");
			mapJson.put("count", communityService.selectFollowCount(
					follow.getMem_num()));
		}
		return mapJson;
	}
}