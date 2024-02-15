package kr.spring.book.vo;

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
public class BookVO {
	private int book_num;
	private int mem_num;
	private MultipartFile upload;
	private String book_thumbnailName;
	@Range(min=0,max=2)
	private int book_category;//0 : 취미 소모임, 1: 원데이 클래스, 2 : 스터디 모임
	private int book_onoff;//0 : 모집 중(default), 1 : 모임 완료, 2 : 모임 취소, 3 : 모집 완료
	@NotBlank
	private String book_title;
	@NotBlank
	private String book_content;
	@NotBlank
	private String book_gatheringDate;
	@Range(min=1,max=2)
	private int book_match;//0 : 예약 바로 확정, 1 : 주최자 승인 필요
	private Date book_regDate;//SYSDATE(default)
	private Date modifyDate;
	@NotBlank
	private String book_address1;
	@NotEmpty
	private String book_address2;
	private String book_kit;
	@Range(min=2,max=99)
	private int book_maxcount;
	private int book_headcount;//0(default)
	private String book_ip;
	private Integer book_expense;
	
	/*-- 읽어올 정보 --*/
	private String mem_nickname;
	private int apply_num;
	private int book_state;//0 : 대기, 1 : 확정, 2 : 거절
	private String apply_gatheringDate;
	private String apply_title;
	private String apply_address1;
	private int compareNow;//1 : 모임 일정 지남, 2 : 모임 일정 이전
	private int rev_status;//1 : 리뷰 작성 전, 2 : 리뷰 작성 후
	
	private int rev_cnt;
	private int scrap_cnt;
	private int rep_cnt;
}
