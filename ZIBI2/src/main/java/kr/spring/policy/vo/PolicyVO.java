package kr.spring.policy.vo;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PolicyVO {
	
	private int district_num;
	
	private String district_name;
	
	@NotBlank
	private String district_latitude;
	
	@NotBlank
	private String district_lonitude;
	
	private String policy_url;
	
	private String year; //통계년월
	
	private int tot_family; //전체 세대수
	
	private int household_cnt; // 1인 세대수
	
}
