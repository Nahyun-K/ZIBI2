package kr.spring.performance.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TicketingVO { 
	private int ticketing_num;
	private int performance_num;
	private int cinema_num;
	private Date ticketing_date;
	private String ticketing_start_time;
}
