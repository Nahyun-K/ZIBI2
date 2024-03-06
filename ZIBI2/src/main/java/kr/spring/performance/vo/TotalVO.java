package kr.spring.performance.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TotalVO { 
	private CinemaVO cinemaVO;
	private PerformanceVO performanceVO;
	private TicketingVO ticketingVO;
	private ChoiceVO choiceVO;
	private SeatVO seatVO;
	private PaymentVO paymentVO;
}
