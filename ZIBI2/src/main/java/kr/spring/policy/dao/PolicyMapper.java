package kr.spring.policy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.policy.vo.PolicyVO;

@Mapper
public interface PolicyMapper {
	
	//공공데이터 목록
	public List<PolicyVO> selectStatsList();
	
	//공공데이터 개별 데이터 select
	@Select("SELECT * FROM district WHERE district_num=#{district_num}")
	public PolicyVO selectStats(int district_num);
	
	
	//공공데이터 행정구역 정보 insert (district 테이블, policy 테이블)
	@Insert("INSERT INTO district (district_num,district_name) VALUES (#{district_num},#{district_name})")
	public void insertDistrict(PolicyVO policyVO);
	
	//공공데이터 - 정책 url insert
	@Insert("INSERT INTO Policy (district_num) VALUES (#{district_num})")
	public void insertPolicy(PolicyVO policyVO);
	
	//공공데이터 insert
	@Insert("INSERT INTO stats (district_num,year,tot_family,household_cnt) VALUES (#{district_num},#{year},#{tot_family},#{household_cnt})")
	public void insertStats(PolicyVO policyVO);
	
	
	//공공데이터 update
	public void updateStats(PolicyVO policyVO);
	
	
	//관리자 - 행정구역 위도, 경도 정보 update (district 테이블)
	@Update("UPDATE district SET district_latitude=#{district_latitude},district_lonitude=#{district_lonitude} WHERE district_num=#{district_num}")
	public void updateDistrict(PolicyVO policyVO);
	
	//관리자 - 행정구역 url 정보 update (policy 테이블)
	@Update("UPDATE policy SET policy_url=#{policy_url} WHERE district_num=#{district_num}")
	public void updatePolicy(PolicyVO policyVO);
	
	//관리자 - 행정구역 정보 삭제 (policy 테이블)
	@Delete("UPDATE policy SET policy_url='-' WHERE district_num=#{district_num}")
	public void deletePolicy(int distirct_num);
	
	//행정구역 정보 select 갯수 (district 테이블, policy 테이블)
	@Select("Select COUNT(*) FROM Policy JOIN district USING (district_num)")
	public int selectPolicyCount();
	
	//행정구역 정보 select (district 테이블, policy 테이블)
	public List<PolicyVO> selectPolicyList(Map<String,Object> map);
	
	//개별 행정구역 정보 select
	@Select("Select * FROM Policy JOIN district USING (district_num) WHERE district_num=#{district_num}")
	public PolicyVO selectPolicy(int district_num);
	
}