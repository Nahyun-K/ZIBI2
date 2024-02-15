package kr.spring.community.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.community.vo.CommunityFavVO;
import kr.spring.community.vo.CommunityFollowVO;
import kr.spring.community.vo.CommunityReplyVO;
import kr.spring.community.vo.CommunityVO;
import kr.spring.community.vo.CommunityFavVO;
import kr.spring.community.vo.CommunityReplyVO;
import kr.spring.community.vo.CommunityVO;

@Mapper
public interface CommunityMapper {
	public List<CommunityVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	public void insertCommunity(CommunityVO community);
	public CommunityVO selectCommunity(int Community_num);
	@Update("UPDATE community SET community_hit=community_hit+1 WHERE community_num=#{community_num}")
	 public void updateHit(int community_num);
	 public void updateCommunity(CommunityVO community);
	 @Delete("DELETE FROM community WHERE community_num=#{community_num}")
	 public void deleteCommunity(int community_num);
	 @Update("UPDATE community SET community_filename='' WHERE community_num=#{community_num}")
	 public void deleteFile(int community_num);
	 
	 //좋아요
	 @Select("SELECT * FROM community_fav WHERE community_num=#{community_num} AND mem_num=#{mem_num}")
	 public CommunityFavVO selectCommuFav(CommunityFavVO fav);
	 @Select("SELECT COUNT(*) from community_fav WHERE community_num=#{community_num}")
	 public int selectFavCount(int board_num);
	 @Insert("INSERT INTO community_fav (community_num,mem_num) VALUES (#{community_num},#{mem_num})")
	 public void insertFav(CommunityFavVO fav);
	 @Delete("DELETE FROM community_fav WHERE community_num=#{community_num} AND mem_num=#{mem_num}")
	 public void deleteFav(CommunityFavVO communityFav);
	 @Delete("DELETE FROM community_fav WHERE community_num=#{community_num}")
	 public void deleteFavByCommunityNum(int community_num);
	 
	 //댓글
	 public List<CommunityReplyVO> selectListReply(Map<String,Object> map);
	 @Select("SELECT COUNT(*) FROM community_reply WHERE community_num=#{community_num}")
	 public int selectRowCountReply(Map<String,Object> map);
	 //댓글 수정,삭제시 작성자 회원번호 구할 때 사용
	 @Select("SELECT * FROM community_reply WHERE re_num=#{re_num}")
	 public CommunityReplyVO selectReply(int re_num);
	 public void insertReply(CommunityReplyVO communityReply);
	 @Update("UPDATE community_reply SET re_content=#{re_content},re_modifydate=SYSDATE WHERE re_num=#{re_num}")
	 public void updateReply(CommunityReplyVO communityReply);
	 @Delete("DELETE FROM community_reply WHERE re_num=#{re_num}")
	 public void deleteReply(int re_num);
	 //부모글 삭제시 댓글이 존재하면 부모글 삭제전 댓글 삭제
	 @Delete("DELETE FROM community_reply WHERE community_num=#{community_num")
	 public void deleteReplyByCommunityNum(int community_num);
	 
	 //팔로우
	 @Select("SELECT * FROM follow WHERE community_num=#{community_num} AND mem_num=#{mem_num}")
	 public CommunityFollowVO selectFav(CommunityFollowVO follow);
	 @Select("SELECT COUNT(*) from follow WHERE community_num=#{community_num}")
	 public int selectFollowCount(int community_num);
	 @Insert("INSERT INTO follow (mem_num,fmem_num) VALUES (#{mem_num},#{fmem_num})")
	 public void insertFollow(CommunityFollowVO follow);
	 @Delete("DELETE FROM follow WHERE community_num=#{community_num} AND mem_num=#{mem_num}")
	 public void deleteFollow(CommunityFollowVO communityFollow);
	 @Delete("DELETE FROM follow WHERE community_num=#{community_num}")
	 public void deleteFollowByMemNum(int mem_num);
}
