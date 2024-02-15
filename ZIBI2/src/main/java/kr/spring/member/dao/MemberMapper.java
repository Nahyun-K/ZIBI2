package kr.spring.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.book.vo.BookVO;
import kr.spring.member.vo.ActListVO;
import kr.spring.member.vo.DealListVO;
import kr.spring.member.vo.FollowListVO;
import kr.spring.member.vo.FollowVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.second.vo.SecondVO;

@Mapper
public interface MemberMapper {
	
	/*---------회원 가입----------*/
	//시퀀스 생성
	@Select("SELECT member_seq.nextval FROM dual")
	public int createMemNum();
	//필수 정보 insert
	public void insertMember(MemberVO memberVO);
	//부가 정보 insert
	public void insertMemberDetail(MemberVO memberVO);
	
	/*---------회원 정보----------*/
	//회원 전체 정보 select
	@Select("SELECT * FROM member JOIN member_detail USING (mem_num) WHERE mem_num=#{mem_num}")
	public MemberVO selectMember(int mem_num);
	
	/*---------회원 정보 유효성 체크----------*/
	//이메일 체크 - 이메일 인증 시 사용
	@Select("SELECT * FROM member JOIN member_detail USING (mem_num) WHERE mem_email=#{mem_email}")
	public MemberVO checkEmail(String mem_email);
	//닉네임 체크
	@Select("SELECT * FROM member WHERE mem_nickname=#{mem_nickname}")
	public MemberVO checkNickname(String mem_nickname);
	//핸드폰 번호 체크
	@Select("SELECT * FROM member_detail WHERE mem_phone=#{mem_phone}")
	public MemberVO checkPhone(String mem_phone);
	
	/*---------회원 정보 수정----------*/
	//닉네임 업데이트
	@Update("UPDATE member SET mem_nickname=#{mem_nickname} WHERE mem_num=#{mem_num}")
	public void updateMember(MemberVO memberVO);
	//회원 부가정보 수정
	public void updateMemberDetail(MemberVO memberVO);
	//프로필 사진 수정
	@Update("UPDATE member_detail SET mem_photoname=#{mem_photoname}, mem_photo=#{mem_photo} WHERE mem_num=#{mem_num}")
	public void updateProfileImages(MemberVO memberVO);
	//비밀번호 수정
	@Update("UPDATE member_detail SET mem_password=#{mem_password} WHERE mem_num=#{mem_num}")
	public void updatePassword(MemberVO memberVO);
	
	/*---------회원 탈퇴----------*/
	//조건 - 소모임 예약 compareNow==2, book_state=<1인 경우 탈퇴 불가, 그 탈퇴 가능
	public List<BookVO> selectBookList(int mem_num);
	//조건 - 중고 거래
	public List<SecondVO> selectSecond(int mem_num);
	//조건 - 영화 예매
	public int selectMovie(int mem_num);
	
	//소모임 - 스크랩 삭제
	@Delete("DELETE book_scrap WHERE mem_num=#{mem_num}")
	public void deleteBookScrap(int mem_num);
	//소모임 - 리뷰 삭제
	@Delete("DELETE book_review WHERE mem_num=#{mem_num}")
	public void deleteBookReview(int mem_num);
	//소모임 - 댓글 삭제
	@Delete("DELETE book_reply WHERE mem_num=#{mem_num}")
	public void deleteBookReply(int mem_num);
	//소모임 - 예약 매칭 삭제
	@Delete("DELETE book_matching WHERE apply_num=#{mem_num}")
	public void deleteBookMatch(int mem_num);
	//소모임 - 부모글에 딸린 외래키 조건 삭제
	@Delete("DELETE FROM book_review WHERE book_num IN (SELECT book_num FROM book WHERE mem_num=#{mem_num})")
	public void deleteBookReviewByBookNum(int mem_num);
	@Delete("DELETE FROM book_reply WHERE book_num IN (SELECT book_num FROM book WHERE mem_num=#{mem_num})")
	public void deleteBookReplyByBookNum(int mem_num);
	@Delete("DELETE FROM book_scrap WHERE book_num IN (SELECT book_num FROM book WHERE mem_num=#{mem_num})")
	public void deleteBookScrapByBookNum(int mem_num);
	@Delete("DELETE FROM book_scrap WHERE book_num IN (SELECT book_num FROM book WHERE mem_num=#{mem_num})")
	public void deleteBookMatchByBookNum(int mem_num);
	//소모임 - 예약 삭제
	@Delete("DELETE book WHERE mem_num=#{mem_num}")
	public void deleteBook(int mem_num);
	
	//커뮤니티 - 댓글 삭제
	@Delete("DELETE community_reply WHERE mem_num=#{mem_num}")
	public void deleteCommunityReply(int mem_num);
	//커뮤니티 - 좋아요 삭제
	@Delete("DELETE community_fav WHERE mem_num=#{mem_num}")
	public void deleteCommunityFav(int mem_num);
	//커뮤니티 - 부모글에 딸린 외래키 조건 삭제
	@Delete("DELETE FROM community_fav WHERE community_num IN (SELECT community_num FROM community WHERE mem_num=#{mem_num})")
	public void deleteCommunityFavByCNumm(int mem_num);
	@Delete("DELETE FROM community_reply WHERE community_num IN (SELECT community_num FROM community WHERE mem_num=#{mem_num})")
	public void deleteCommunityReByCNumm(int mem_num);
	//커뮤니티 - 부모글 삭제
	@Delete("DELETE community WHERE mem_num=#{mem_num}")
	public void deleteCommunity(int mem_num);
	
	//재능기부 - 스크랩 삭제
	@Delete("DELETE helper_scrap WHERE mem_num=#{mem_num}")
	public void deleteHelperScrap(int mem_num);
	//재능기부 - 댓글 삭제
	@Delete("DELETE helper_reply WHERE mem_num=#{mem_num}")
	public void deleteHelperReply(int mem_num);
	//재능기부 - 부모글에 딸린 외래키 조건 삭제
	@Delete("DELETE FROM helper_scrap WHERE helper_num IN (SELECT helper_num FROM helper WHERE mem_num=#{mem_num})")
	public void deleeHelperScrapByHnum(int mem_num);
	@Delete("DELETE FROM helper_reply WHERE helper_num IN (SELECT helper_num FROM helper WHERE mem_num=#{mem_num})")
	public void deleteHelperReplyByHnum(int mem_num);
	//재능기부 - 부모글 삭제
	@Delete("DELETE helper WHERE mem_num=#{mem_num}")
	public void deleteHelper(int mem_num);
	
	//내가 팔로우한 사람 삭제 - 회원 탈퇴 시 이용
	@Delete("DELETE FROM follow WHERE fmem_num=#{fmem_num}") 
	public void deleteFollowByFmem_num(int fmem_num);
	//나를 팔로우한 사람 삭제 - 회원 탈퇴 시 이용
	@Delete("DELETE FROM follow WHERE mem_num=#{mem_num}") 
	public void deleteFollowByMem_num(int mem_num);
	
	//member 테이블 업데이트
	@Update("UPDATE member SET mem_email='-',mem_auth=0,mem_nickname='탈퇴한 회원' where mem_num=#{mem_num}")
	public void quitMember(int mem_num);
	//회원 부가 정보 삭제
	@Delete("DELETE FROM member_detail where mem_num=#{mem_num}")
	public void quitMemberDetail(int mem_num);
	
	/*---------회원 팔로우/언팔로우----------*/
	//팔로우 유무
	@Select("SELECT * FROM follow JOIN member USING(mem_num) WHERE mem_num=#{mem_num} AND fmem_num=#{fmem_num}") 
	public FollowVO selectFollow(FollowVO followVO);
	//팔로우 수
	@Select("SELECT COUNT(*) FROM follow WHERE mem_num=#{mem_num}") 
	public int followCount(int mem_num);
	//내가 팔로우한 사람 수
	@Select("SELECT COUNT(*) FROM follow WHERE fmem_num=#{fmem_num}")
	public int selectFollowByFmem_numCount(int fmem_num);
	//내가 팔로우한 사람
	@Select("SELECT * FROM follow WHERE fmem_num=#{fmem_num}")
	public List<FollowVO> selectFollowByFmem_num(int fmem_num);
	//팔로잉
	@Insert("INSERT INTO follow (mem_num,fmem_num) VALUES (#{mem_num},#{fmem_num})") 
	public void followMember(FollowVO followVO);
	//언팔로우
	@Delete("DELETE FROM follow WHERE mem_num=#{mem_num} AND fmem_num=#{fmem_num}") 
	public void unfollowMember(FollowVO followVO);
	
	/*---------마이페이지 목록----------*/
	//거래 내역
	public List<DealListVO> selectDealList(Map<String,Object> map);
	//거래 내역 수
	public int selectDealCount(Map<String,Object> map);
	
	//활동 내역
	public List<ActListVO> selectActList(Map<String,Object> map);
	//활동 내역 수
	public int selectActCount(Map<String,Object> map);
	
	//팔로우한 사람의 글 목록
	public List<FollowListVO> selectFollowList(Map<String,Integer> map);
	//팔로우한 사람의 글 갯수
	public int selectFollowCount(int fmem_num);
	
	/*---------오픈 프로필----------*/
	//오픈 프로필 글 개수
	public int selectOpenCount(int mem_num);
	//오픈 프로필 글
	public List<FollowListVO> selectOpenList(Map<String,Integer> map);
}
