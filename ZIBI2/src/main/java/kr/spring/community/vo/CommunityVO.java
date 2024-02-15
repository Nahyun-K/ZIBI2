package kr.spring.community.vo;

import java.sql.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommunityVO {
	private int community_num;
	@NotBlank
	private String community_title;
	@Range(min=1,max=5)
	private int community_category;
	@NotBlank
	private String community_content;
	private int community_hit;
	private Date community_reg_date;
	private Date community_modify_date;
	private MultipartFile upload;
	private String community_filename;
	private int mem_num;
	private String mem_nickname;
	
	private int re_cnt;//댓글 개수
	private int fav_cnt;//좋아요 개수
}
