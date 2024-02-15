package kr.spring.performance.service;

import java.util.List;
import java.util.Map;

import kr.spring.performance.vo.ChoiceVO;
import kr.spring.performance.vo.CinemaVO;
import kr.spring.performance.vo.PaymentVO;
import kr.spring.performance.vo.PerformanceVO;
import kr.spring.performance.vo.TicketingVO;
import kr.spring.performance.vo.TotalVO;

public interface PerformanceService {
	// 사용자
	public List<PerformanceVO> selectList(Map<String, Object> map);
	public int selectRowCount(Map<String, Object> map);
	
	public List<CinemaVO> selectCinemaLoc1();
	public List<CinemaVO> selectCinemaLoc2(Map<String, Object> map);
	public List<CinemaVO> selectCinemaNum(String cinema_location2);
	public List<TicketingVO> selectPerformance(int cinema_num);
	public List<TicketingVO> selectDate(); 
	
	
	public PerformanceVO selectWithPerformance(int performance_num);
	
	public List<CinemaVO> selectCinemaWithTicketing(Map<String, Object> map);
	public List<PerformanceVO> selectPerformanceWithTicketing(Map<String, Object> map);
	public List<TicketingVO> selectWithTicketing(Map<String, Object> map);
	

	public List<CinemaVO> pickCinema(Map<String, Object> map);
	public List<PerformanceVO> pickPerformance(Map<String, Object> map);
	public List<TicketingVO> pickTicketing(Map<String, Object> map);
	
	
	public CinemaVO choosingCinema(Map<String, Object> map);
	public PerformanceVO choosingPerformance(Map<String, Object> map);
	public TicketingVO choosingTicketing(Map<String, Object> map);
	
	
	// 결제 페이지
	public void insertChoice(Map<String, Object> map);
	public void updateChoice(int cinema_num, int num); // 여석 수 업데이트
	public void insertPayment(Map<String, Object> map);
	
	// 결제 내역
	public List<CinemaVO> selectPayCinema(Map<String, Object> map);
	public List<PerformanceVO> selectPayPerformance(Map<String, Object> map);
	public List<TicketingVO> selectPayTicketing(Map<String, Object> map);
	public List<ChoiceVO> selectPayChoice(Map<String, Object> map);
	public List<PaymentVO> selectPayPayment(Map<String, Object> map);
	public List<TotalVO> selectPayTotal(Map<String, Object> map);
	public List<TotalVO> selectPayAll(Map<String, Object> map);
	public int selectPayCount(Map<String, Object> map);
	
	
	
	public List<ChoiceVO> selectChoice(Map<String, Object> map);
	
	public void deleteChoice(Map<String, Object> map);
	
	
	
	// 관리자
	public void insertPerformance(PerformanceVO performance);
	public void insertCinema(CinemaVO cinema);
	public List<CinemaVO> adminSelectLocation(String loc1);
	public List<CinemaVO> selectLocation2(String loc1);
	public void insertDate(TicketingVO ticketing);
	
}
