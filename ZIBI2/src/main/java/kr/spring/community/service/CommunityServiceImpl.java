package kr.spring.community.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.community.dao.CommunityMapper;
import kr.spring.community.vo.CommunityFavVO;
import kr.spring.community.vo.CommunityFollowVO;
import kr.spring.community.vo.CommunityReplyVO;
import kr.spring.community.vo.CommunityVO;

@Service
@Transactional
public class CommunityServiceImpl implements CommunityService{
	@Autowired
	private CommunityMapper communityMapper;
	
	@Override
	public List<CommunityVO> selectList(Map<String, Object> map) {
		return communityMapper.selectList(map);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return communityMapper.selectRowCount(map);
	}

	@Override
	public void insertCommunity(CommunityVO community) {
		communityMapper.insertCommunity(community);
	}

	@Override
	public CommunityVO selectCommunity(int community_num) {
		return communityMapper.selectCommunity(community_num);
	}

	@Override
	public void updateHit(int community_num) {
		communityMapper.updateHit(community_num);
	}

	@Override
	public void updateCommunity(CommunityVO community) {
		communityMapper.updateCommunity(community);
	}

	@Override
	public void deleteCommunity(int community_num) {
		//부모글 좋아요 삭제
		communityMapper.deleteFavByCommunityNum(community_num);
		//댓글이 존재하면 댓글을 우선 삭제하고 부모글 삭제
		communityMapper.deleteCommunity(community_num);
		//부모글 삭제
		communityMapper.deleteCommunity(community_num);
	}

	@Override
	public void deleteFile(int community_num) {
		communityMapper.deleteFile(community_num);
	}

	@Override
	public CommunityFavVO selectCommuFav(CommunityFavVO fav) {
		return communityMapper.selectCommuFav(fav);
	}

	@Override
	public int selectFavCount(int community_num) {
		return communityMapper.selectFavCount(community_num);
	}

	@Override
	public void insertFav(CommunityFavVO fav) {
		communityMapper.insertFav(fav);
	}

	@Override
	public void deleteFav(CommunityFavVO communityFav) {
		communityMapper.deleteFav(communityFav);
	}

	@Override
	public List<CommunityReplyVO> selectListReply(Map<String, Object> map) {
		return communityMapper.selectListReply(map);
	}

	@Override
	public int selectRowCountReply(Map<String, Object> map) {
		return communityMapper.selectRowCountReply(map);
	}

	@Override
	public CommunityReplyVO selectReply(int re_num) {
		return communityMapper.selectReply(re_num);
	}

	@Override
	public void insertReply(CommunityReplyVO communityReply) {
		communityMapper.insertReply(communityReply);
	}

	@Override
	public void updateReply(CommunityReplyVO communityReply) {
		communityMapper.updateReply(communityReply);
	}

	@Override
	public void deleteReply(int re_num) {
		communityMapper.deleteReply(re_num);
		
	}

	@Override
	public CommunityFollowVO selectFollow(CommunityFollowVO follow) {
		
		return null;
	}

	@Override
	public int selectFollowCount(int mem_num) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertFollow(CommunityFollowVO follow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFollow(CommunityFollowVO communityFollow) {
		// TODO Auto-generated method stub
		
	}
	

	}
