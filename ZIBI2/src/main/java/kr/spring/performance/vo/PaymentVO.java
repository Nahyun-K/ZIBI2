package kr.spring.performance.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentVO { 

	private int payment_num;
	private String payment_uid;
	private String payment_type;
	private int payment_price;
	private int payment_state;
	private Date payment_date;
	private Date payment_modify_date;
	
	private int mem_num;
	private int choice_num;
	
}
