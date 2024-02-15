package kr.spring.community.service;

import java.util.List;
import java.util.Map;

import kr.spring.community.vo.CommunityFavVO;
import kr.spring.community.vo.CommunityFollowVO;
import kr.spring.community.vo.CommunityReplyVO;
import kr.spring.community.vo.CommunityVO;

public interface CommunityService {
	//부모글
	public List<CommunityVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	public void insertCommunity(CommunityVO community);
	public CommunityVO selectCommunity(int community_num);
	public void updateHit(int community_num);
	public void updateCommunity(CommunityVO community);
	public void deleteCommunity(int community_num);
	public void deleteFile(int community_num);
	//좋아요
	public CommunityFavVO selectCommuFav(CommunityFavVO fav);
	public int selectFavCount(int community_num);
	public void insertFav(CommunityFavVO fav);
	public void deleteFav(CommunityFavVO communityFav);
	//댓글
	public List<CommunityReplyVO> selectListReply(Map<String,Object> map);
	public int selectRowCountReply(Map<String,Object> map);
	public CommunityReplyVO selectReply(int re_num);
	public void insertReply(CommunityReplyVO communityReply);
	public void updateReply(CommunityReplyVO communityReply);
	public void deleteReply(int re_num);
	//팔로우
	public CommunityFollowVO selectFollow(CommunityFollowVO follow);
	public int selectFollowCount(int mem_num);
	public void insertFollow(CommunityFollowVO follow);
	public void deleteFollow(CommunityFollowVO communityFollow);

}



