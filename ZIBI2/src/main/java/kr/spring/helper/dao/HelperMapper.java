package kr.spring.helper.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.helper.vo.HelperReplyVO;
import kr.spring.helper.vo.HelperScrapVO;
import kr.spring.helper.vo.HelperVO;

@Mapper
public interface HelperMapper {
	public List<HelperVO> selectList(Map<String,Object> map);//글 목록
	public int selectRowCount(Map<String,Object> map);
	public void insertHelper(HelperVO helper);
	public HelperVO selectHelper(int helper_num);
	@Update("UPDATE helper SET helper_hit=helper_hit+1 WHERE helper_num=#{helper_num}")
	public void updateHit(int helper_num);
	public void updateHelper(HelperVO helper);
	@Delete("DELETE FROM helper WHERE helper_num=#{helper_num}")
	public void deleteHelper(int helper_num);
	@Update("UPDATE helper SET helper_filename='' WHERE helper_num=#{helper_num}")
	public void deleteFile(int helper_num);
	
	//스크랩
	@Select("SELECT * FROM helper_scrap WHERE helper_num=#{helper_num} AND mem_num=#{mem_num}")
	public HelperScrapVO selectscrap(HelperScrapVO scrap);
	@Select("SELECT COUNT(*) FROM helper_scrap WHERE helper_num=#{helper_num}")
	public int selectScrapCount(int helper_num);
	@Insert("INSERT INTO helper_scrap (helper_num,mem_num) VALUES (#{helper_num},#{mem_num})")
	public void insertScrap(HelperScrapVO scrap);
	@Delete("DELETE FROM helper_scrap WHERE helper_num=#{helper_num} AND mem_num=#{mem_num}")
	public void deleteScrap(HelperScrapVO helperScrap);
	//부모글 삭제시 스크랩이 존재하면 부모글 삭제 전 좋아요 삭제
	@Delete("DELETE FROM helper_scrap WHERE helper_num=#{helper_num}")
	public void deleteScrapByHelperNum(int helper_num);
	
	//해결중&해결완료
	@Select("SELECT helper_solution FROM helper WHERE helper_num=#{helper_num}")
	public HelperVO selectSolution(HelperVO solution);
	@Update("UPDATE helper SET helper_solution=0 WHERE helper_num=#{helper_num}")
	public void updateSolution0(HelperVO sol);
	@Update("UPDATE helper SET helper_solution=1 WHERE helper_num=#{helper_num}")
	public void updateSolution1(HelperVO sol);
	
	//댓글
	public List<HelperReplyVO> selectListReply(Map<String, Object> map); //댓글 목록
	@Select("SELECT COUNT(*) FROM helper_reply WHERE helper_num=#{helper_num}")
	public int selectRowCountReply(Map<String,Object> map); //댓글 수
	@Select("SELECT * FROM helper_reply WHERE re_num=#{re_num}")
	public HelperReplyVO selectReply(int re_num); 
	public void insertReply(HelperReplyVO helperReply); //댓글 등록
	@Update("UPDATE helper_reply SET re_content=#{re_content},re_ip=#{re_ip},re_mdate=SYSDATE WHERE re_num=#{re_num}")
	public void updateReply(HelperReplyVO helperReply); //댓글 수정
	@Delete("DELETE FROM helper_reply WHERE re_num=#{re_num}")
	public void deleteReply(int re_num); // 댓글 삭제
	//부모글 삭제시 댓글이 존재하면 부모글 삭제 전 댓글 삭제
	@Delete("DELETE FROM helper_reply WHERE helper_num=#{helper_num}")
	public void deleteReplyByHelperNum(int helper_num);
}

















