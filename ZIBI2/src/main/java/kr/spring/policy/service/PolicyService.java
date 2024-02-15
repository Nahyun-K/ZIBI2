package kr.spring.policy.service;

import java.util.List;
import java.util.Map;

import kr.spring.policy.vo.PolicyVO;

public interface PolicyService {
	
	//공공데이터 개별 데이터 select
	public PolicyVO selectStats(int district_num);
	
	//공공데이터 목록
	public List<PolicyVO> selectStatsList();
	
	//공공데이터 insert
	public void insertStats(PolicyVO policyVO);
	
	//공공데이터 update
	public void updateStats(PolicyVO policyVO);
	
	//공공데이터 행정구역 정보 insert (district 테이블, policy 테이블)
	public void insertDistrict(PolicyVO policyVO);
	
	//공공데이터 - 정책 url insert
	public void insertPolicy(PolicyVO policyVO);
	
	//관리자 - 행정구역 위도, 경도 정보 update
	public void updateDistrict(PolicyVO policyVO);
		
	//관리자 - 행정구역 url 정보 update (policy 테이블)
	public void updatePolicy(PolicyVO policyVO);
	
	//관리자 - 행정구역 정보 삭제 (policy 테이블)
	public void deletePolicy(int distirct_num);
	
	//행정구역 정보 select 갯수 (district 테이블, policy 테이블)
	public int selectPolicyCount();
	
	//행정구역 정보 select (district 테이블, policy 테이블)
	public List<PolicyVO> selectPolicyList(Map<String,Object> map);
	
	//개별 행정구역 정보 select
	public PolicyVO selectPolicy(int district_num);
}
