package kr.spring.member.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ActListVO {

	private int num;
	private String title;
	private String reg_date;
	private String category;
	private String subCategory;
}
