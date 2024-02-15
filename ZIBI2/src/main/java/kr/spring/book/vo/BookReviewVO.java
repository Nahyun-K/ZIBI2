package kr.spring.book.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookReviewVO {
	private int rev_num;
	private int book_num;
	private int mem_num;
	private String book_rev;
	private String book_grade;
	private String book_revIp;
	
	/*-- 읽어올 정보 --*/
	private String apply_gatheringDate;
	private String mem_nickname;
}
