package kr.spring.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.book.vo.BookVO;
import kr.spring.member.dao.MemberMapper;
import kr.spring.member.vo.ActListVO;
import kr.spring.member.vo.DealListVO;
import kr.spring.member.vo.FollowListVO;
import kr.spring.member.vo.FollowVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.second.vo.SecondVO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberMapper memberMapper;
	
	/*---------회원 가입----------*/
	@Override
	public int createMemNum() {
		return memberMapper.createMemNum();
	}
	
	@Override
	public void registerMember(MemberVO memberVO) {
		memberMapper.insertMember(memberVO);
		memberMapper.insertMemberDetail(memberVO);
	}
	
	/*---------회원 정보----------*/
	@Override
	public MemberVO selectMember(int mem_num) {
		return memberMapper.selectMember(mem_num);
	}
	
	
	/*---------회원 정보 유효성 체크----------*/
	@Override
	public MemberVO checkEmail(String mem_email) {
		return memberMapper.checkEmail(mem_email);
	}

	@Override
	public MemberVO checkNickname(String mem_nickname) {
		return memberMapper.checkNickname(mem_nickname);
	}
	
	@Override
	public MemberVO checkPhone(String mem_phone) {
		return memberMapper.checkPhone(mem_phone);
	}

	
	/*---------회원 정보 수정----------*/
	@Override
	public void updateMember(MemberVO memberVO) {
		memberMapper.updateMember(memberVO);
	}
	@Override
	public void updateMemberDetail(MemberVO memberVO) {
		memberMapper.updateMemberDetail(memberVO);
	}

	@Override
	public void updateProfileImages(MemberVO memberVO) {
		memberMapper.updateProfileImages(memberVO);
	}
	
	@Override
	public void updatePassword(MemberVO memberVO) {
		memberMapper.updatePassword(memberVO);		
	}
	
	/*---------회원 탈퇴----------*/
	@Override
	public List<BookVO> selectBookList(int mem_num) {
		return memberMapper.selectBookList(mem_num);
	}

	@Override
	public List<SecondVO> selectSecond(int mem_num) {
		return memberMapper.selectSecond(mem_num);
	}
	
	@Override
	public int selectMovie(int mem_num) {
		return memberMapper.selectMovie(mem_num);
	}
	
	@Override
	public void quitMember(int mem_num) {
		
		//커뮤니티 좋아요, 댓글, 부모글 삭제
		memberMapper.deleteCommunityFav(mem_num);
		memberMapper.deleteCommunityReply(mem_num);
		//부모글 하위 데이터 삭제
		memberMapper.deleteCommunityFavByCNumm(mem_num);
		memberMapper.deleteCommunityReByCNumm(mem_num);
		//부모글 삭제
		memberMapper.deleteCommunity(mem_num);
		
		//재능기부 글, 댓글, 스크랩 전부 삭제
		memberMapper.deleteHelperReply(mem_num);
		memberMapper.deleteHelperScrap(mem_num);
		//부모글 하위 데이터 삭제
		memberMapper.deleeHelperScrapByHnum(mem_num);
		memberMapper.deleteHelperReplyByHnum(mem_num);
		//부모글 삭제
		memberMapper.deleteHelper(mem_num);
		
		//소모임 예약, 매칭, 댓글, 리뷰, 스크랩 삭제
		memberMapper.deleteBookScrap(mem_num);
		memberMapper.deleteBookReply(mem_num);
		memberMapper.deleteBookReview(mem_num);
		memberMapper.deleteBookMatch(mem_num);
		//부모글 하위 데이터 삭제
		memberMapper.deleteBookReplyByBookNum(mem_num);
		memberMapper.deleteBookMatchByBookNum(mem_num);
		memberMapper.deleteBookScrapByBookNum(mem_num);
		memberMapper.deleteBookReviewByBookNum(mem_num);
		//부모글 삭제
		memberMapper.deleteBook(mem_num);
		
		//팔로우, 팔로잉 삭제
		memberMapper.deleteFollowByFmem_num(mem_num);
		memberMapper.deleteFollowByMem_num(mem_num);
		
		//회원 상세 삭제
		memberMapper.quitMemberDetail(mem_num);
		//회원 필수 정보는 업데이트
		memberMapper.quitMember(mem_num);
	}
	
	/*---------회원 팔로우----------*/
	@Override
	public FollowVO selectFollow(FollowVO followVO) {
		return memberMapper.selectFollow(followVO);
	}
	
	@Override
	public void followMember(FollowVO followVO){
		memberMapper.followMember(followVO);
	}

	@Override
	public void unfollowMember(FollowVO followVO) {
		memberMapper.unfollowMember(followVO);
	}

	@Override
	public int followCount(int mem_num) {
		return memberMapper.followCount(mem_num);
	}
	
	@Override
	public int selectFollowByFmem_numCount(int fmem_num) {
		return memberMapper.selectFollowByFmem_numCount(fmem_num);
	}
	
	@Override
	public List<FollowVO> selectFollowByFmem_num(int fmem_num) {
		return memberMapper.selectFollowByFmem_num(fmem_num);
	}
	
	/*---------회원 글 내역----------*/

	@Override
	public List<DealListVO> selectDealList(Map<String, Object> map) {
		return memberMapper.selectDealList(map);
	}

	@Override
	public int selectDealCount(Map<String,Object> map) {
		return memberMapper.selectDealCount(map);
	}

	@Override
	public List<ActListVO> selectActList(Map<String, Object> map) {
		return memberMapper.selectActList(map);
	}

	@Override
	public int selectActCount(Map<String, Object> map) {
		return memberMapper.selectActCount(map);
	}

	@Override
	public List<FollowListVO> selectFollowList(Map<String,Integer> map) {
		return memberMapper.selectFollowList(map);
	}

	@Override
	public int selectFollowCount(int fmem_num) {
		return memberMapper.selectFollowCount(fmem_num);
	}

	@Override
	public int selectOpenCount(int mem_num) {
		return memberMapper.selectOpenCount(mem_num);
	}

	@Override
	public List<FollowListVO> selectOpenList(Map<String, Integer> map) {
		return memberMapper.selectOpenList(map);
	}

	
}