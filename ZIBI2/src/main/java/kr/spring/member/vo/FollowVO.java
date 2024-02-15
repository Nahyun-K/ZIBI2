package kr.spring.member.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FollowVO {
	
	private int mem_num; //나
	private int fmem_num; //내가 팔로우한 사람
	
	private String fmem_nickname; //내가 팔로우한 사람 닉네임
	
}