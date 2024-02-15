package kr.spring.performance.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChoiceVO { 
	private int choice_num;
	private int choice_row;
	private int choice_col;
	private int choice_adult;
	private int choice_teenage;
	private int choice_treatement;
	
	private int mem_num;
	private int ticketing_num;
}
