package kr.spring.community.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommunityReplyVO {
	private int re_num;
	private String re_content;
	private String re_date;
	private String re_modifydate;
	private int community_num;
	private int mem_num;
	
	private String mem_nickname;
}
