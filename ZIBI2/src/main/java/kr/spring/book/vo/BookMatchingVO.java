package kr.spring.book.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookMatchingVO {
	private int book_num;
	private int apply_num;
	private int book_state;//0 : 대기, 1 : 확정, 2 : 거절
	private Date book_matchDate;//SYSDATE(default)
	private String apply_gatheringDate;
	private String apply_title;
	private String apply_address1;
	
	/*-- 읽어올 정보 --*/
	private String mem_name;
	private String mem_email;
	private String mem_phone;
	private String mem_nickname;
}