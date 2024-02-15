package kr.spring.helper.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.helper.dao.HelperMapper;
import kr.spring.helper.vo.HelperReplyVO;
import kr.spring.helper.vo.HelperScrapVO;
import kr.spring.helper.vo.HelperVO;
@Service
@Transactional
public class HelperServiceImpl implements HelperService{
	@Autowired
	private HelperMapper helperMapper;
	
	@Override
	public List<HelperVO> selectList(Map<String, Object> map) {
		return helperMapper.selectList(map);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return helperMapper.selectRowCount(map);
	}

	@Override
	public void insertHelper(HelperVO helper) {
		helperMapper.insertHelper(helper);
	}

	@Override
	public HelperVO selectHelper(int helper_num) {
		return helperMapper.selectHelper(helper_num);
	}

	@Override
	public void updateHit(int helper_num) {
		helperMapper.updateHit(helper_num);
	}

	@Override
	public void updateHelper(HelperVO helper) {
		helperMapper.updateHelper(helper);
	}

	@Override
	public void deleteHelper(int helper_num) {
		//스크랩&댓글이 존재하면 스크랩&댓글 우선 삭제하고 부모글 삭제
		helperMapper.deleteScrapByHelperNum(helper_num);
		helperMapper.deleteReplyByHelperNum(helper_num);
		//부모글 삭제
		helperMapper.deleteHelper(helper_num);
	}

	@Override
	public void deleteFile(int helper_num) {
		helperMapper.deleteFile(helper_num);
	}

	@Override
	public HelperScrapVO selectscrap(HelperScrapVO scrap) {
		return helperMapper.selectscrap(scrap);
	}

	@Override
	public int selectScrapCount(int helper_num) {
		return helperMapper.selectScrapCount(helper_num);
	}

	@Override
	public void insertScrap(HelperScrapVO scrap) {
		helperMapper.insertScrap(scrap);
		
	}

	@Override
	public void deleteScrap(HelperScrapVO helperScrap) {
		helperMapper.deleteScrap(helperScrap);
		
	}
	
	@Override
	public void updateSolution0(HelperVO sol) {
		helperMapper.updateSolution0(sol);
	}
	
	@Override
	public void updateSolution1(HelperVO sol) {
		helperMapper.updateSolution1(sol);
	}

	@Override
	public HelperVO selectSolution(HelperVO solution) {
		return helperMapper.selectSolution(solution);
	}

	@Override
	public List<HelperReplyVO> selectListReply(Map<String, Object> map) {
		return helperMapper.selectListReply(map);
	}

	@Override
	public int selectRowCountReply(Map<String, Object> map) {
		return helperMapper.selectRowCountReply(map);
	}

	@Override
	public HelperReplyVO selectReply(int re_num) {
		return helperMapper.selectReply(re_num);
	}

	@Override
	public void insertReply(HelperReplyVO helperReply) {
		helperMapper.insertReply(helperReply);
		
	}

	@Override
	public void updateReply(HelperReplyVO helperReply) {
		helperMapper.updateReply(helperReply);
	}

	@Override
	public void deleteReply(int re_num) {
		helperMapper.deleteReply(re_num);
	}
}

