package kr.spring.policy.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.policy.dao.PolicyMapper;
import kr.spring.policy.vo.PolicyVO;

@Service
public class PolicyServiceImpl implements PolicyService {
	
	@Autowired
	PolicyMapper policyMapper;

	@Override
	public void insertPolicy(PolicyVO policyVO) {
		policyMapper.insertPolicy(policyVO);
	}
	
	@Override
	public int selectPolicyCount() {
		return policyMapper.selectPolicyCount();
	}

	@Override
	public List<PolicyVO> selectPolicyList(Map<String,Object> map) {
		return policyMapper.selectPolicyList(map);
	}

	@Override
	public PolicyVO selectPolicy(int district_num) {
		return policyMapper.selectPolicy(district_num);
	}

	@Override
	public void updatePolicy(PolicyVO policyVO) {
		policyMapper.updatePolicy(policyVO);
	}

	@Override
	public void updateDistrict(PolicyVO policyVO) {
		policyMapper.updateDistrict(policyVO);		
	}

	@Override
	public void deletePolicy(int distirct_num) {
		policyMapper.deletePolicy(distirct_num);
	}

	@Override
	public PolicyVO selectStats(int district_num) {
		return policyMapper.selectStats(district_num);
	}

	@Override
	public void insertStats(PolicyVO policyVO) {
		policyMapper.insertStats(policyVO);
	}

	@Override
	public void updateStats(PolicyVO policyVO) {
		policyMapper.updateStats(policyVO);
	}

	@Override
	public void insertDistrict(PolicyVO policyVO) {
		policyMapper.insertDistrict(policyVO);
	}

	@Override
	public List<PolicyVO> selectStatsList() {
		return policyMapper.selectStatsList();
	}
}