package kr.spring.helper.vo;

import java.sql.Date;

import kr.spring.util.DurationFromNow;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class HelperReplyVO {
	private int re_num;
	private String re_content;
	private String re_date;
	private String re_mdate;
	private String re_ip;
	private int helper_num;
	private int mem_num;
	
	private String mem_nickname;
	
	private void setRe_date(String re_date) {
		this.re_date = DurationFromNow.getTimeDiffLabel(re_date);
	}
	private void setRe_mdate(String re_mdate) {
		this.re_mdate = DurationFromNow.getTimeDiffLabel(re_mdate);
	}
}
