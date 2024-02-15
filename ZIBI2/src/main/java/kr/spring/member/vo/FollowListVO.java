package kr.spring.member.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FollowListVO {
	
	private int mem_num;
	private String num;
	private String photo;
	private String mem_nickname;
	private String title;
	private String reg_date;
	private String category;
	
}