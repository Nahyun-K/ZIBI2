package kr.spring.checklist.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.checklist.dao.CheckListMapper;
import kr.spring.checklist.vo.CheckListVO;
import kr.spring.helper.dao.HelperMapper;

@Service
@Transactional
public class CheckListServiceImpl implements CheckListService{
	@Autowired
	private CheckListMapper checkListMapper;
	
	@Override
	public List<CheckListVO> selectList(Map<String, Object> map) {
		return checkListMapper.selectList(map);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return checkListMapper.selectRowCount(map);
	}

	@Override
	public void insertCheckList(CheckListVO CheckList) {
		checkListMapper.insertCheckList(CheckList);
	}

	@Override
	public CheckListVO selectCheckList(int Check_id) {
		return checkListMapper.selectCheckList(Check_id);
	}

}
