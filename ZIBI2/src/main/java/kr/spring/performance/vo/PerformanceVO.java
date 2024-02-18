package kr.spring.performance.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PerformanceVO { 
	private int performance_num;
	private String performance_title;
	private String performance_poster; // 포스터명
	private String performance_content;
	private Date performance_start_date; // '2000-05-03' 년-월-일
	private int performance_age;
	private int performance_id;
	private MultipartFile upload;
}
