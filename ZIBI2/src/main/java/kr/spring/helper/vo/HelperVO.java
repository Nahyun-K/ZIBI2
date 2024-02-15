package kr.spring.helper.vo;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class HelperVO {

	private int helper_num;
	@Range(min=1,max=5)
	private int helper_category;
	@Range(min=1,max=2)
	private int helper_select;//헬프미&헬프유 선택
	private int helper_hit;
	@NotBlank
	private String helper_title;
	@NotBlank
	private String helper_content;
	private MultipartFile upload;
	private String helper_filename;
	@NotBlank
	private String helper_address1;
	private String helper_address2;
	private String helper_ip;
	private int helper_solution;
	private Date helper_reg_date;
	private Date helper_modify_date;
	
	private int mem_num;
	private String mem_nickname;
	
	//스크랩 & 댓글
	private int helper_scrap;//좋아요 개수
	private int helper_reply;//댓글 개수
	
}
