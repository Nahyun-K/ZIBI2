package kr.spring.performance.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SeatVO {
	private int seat_num;
	private int seat_row;
	private int seat_col;
}
