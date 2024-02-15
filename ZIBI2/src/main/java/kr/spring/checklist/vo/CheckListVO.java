package kr.spring.checklist.vo;

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
public class CheckListVO {
	private int check_id;
	private int mem_num;
	@NotBlank
	private String room_name;
	@NotBlank
	private String room_address1;
	private String room_address2;
	@Range(min=1,max=999999999)
	private int room_deposit;
	private int room_expense;
	private int room_size;
	private int room_star;
	private String room_description;
	private MultipartFile upload;
	private String room_filename;
	
	private int room_check1;
	private int room_check2;
	private int room_check3;
	private int room_check4;
	private int room_check5;
	private int room_check6;
	private Date check_date;
}
