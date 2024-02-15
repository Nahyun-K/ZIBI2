package kr.spring.performance.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CinemaVO { 
	private int cinema_num;
	private String cinema_location1;
	private String cinema_location2;
	private String cinema_theater;
	private int cinema_theater_num;
	private int cinema_total;
	private int cinema_row;
	private int cinema_col;
	private int cinema_adult;
	private int cinema_teenage;
	private int cinema_treatment;
}
