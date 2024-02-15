package kr.spring.checklist.service;

import java.util.List;
import java.util.Map;

import kr.spring.checklist.vo.CheckListVO;

public interface CheckListService {
	//부모글
	public List<CheckListVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	public void insertCheckList(CheckListVO CheckList);
	public CheckListVO selectCheckList(int Check_id);
}
