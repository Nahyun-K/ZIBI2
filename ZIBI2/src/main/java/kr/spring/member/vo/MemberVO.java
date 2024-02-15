package kr.spring.member.vo;

import java.io.IOException;
import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"mem_photo"})
public class MemberVO {
	
	/*-----필수 입력 데이터-----*/
	private int mem_num;
	@Email
	@NotBlank
	private String mem_email;
	@NotBlank
	private String mem_nickname;
	private int mem_social; //소셜 회원 구분 (0-일반,1-카카오)
	private int mem_auth;

	/*-----선택 입력 데이터-----*/
	@NotBlank
	private String mem_name;
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String mem_password;
	@Pattern(regexp="^010-([0-9]{3,4})-([0-9]{4})$")
	private String mem_phone;
	@Size(min=5,max=5)
	private String mem_zipcode;
	@NotEmpty
	private String mem_address1;
	@NotEmpty
	private String mem_address2;
	private Date mem_regdate;
	private Date mem_modidate;
	private byte[] mem_photo;
	private String mem_photoname;
	
	private int follow_count; //팔로우 수
	
	
	/*---비밀번호 체크---*/
	public boolean checkPassword(String input_password) {
		if(input_password.equals(mem_password)) return true;
		return false;
	}
	
	/*---이미지 처리 (mulitpartFile > byte[])---*/
	public void setUpload(MultipartFile upload) throws IOException { //프로퍼티가 존재하는 것처럼 setter 작성
		setMem_photo(upload.getBytes());
		setMem_photoname(upload.getOriginalFilename());
	}
}
