package kr.spring.second.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SecondOrderVO {
	private int sc_order_num;
	private int sc_buyer_num;
	private int sc_order_status;//1:예약대기,2:예약중(확정),3:거래완료,4:거절
	private Date sc_order_reg_date;
	private int sc_num;
	
	private String mem_nickname;//판매자 닉네임
	private SecondVO secondVO;
}
