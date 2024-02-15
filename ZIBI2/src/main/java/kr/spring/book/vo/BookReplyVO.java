package kr.spring.book.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookReplyVO {
	private int rep_num;
	private String book_rep;
	private String book_repDate;
	private String book_repIp;
	private int ref_rep_num;
	private int ref_level;
	private int book_num;
	private int mem_num;
	private int book_deleted;
	
	private String mem_nickname;
}
