package kr.spring.performance.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.performance.dao.PerformanceMapper;
import kr.spring.performance.vo.ChoiceVO;
import kr.spring.performance.vo.CinemaVO;
import kr.spring.performance.vo.PaymentVO;
import kr.spring.performance.vo.PerformanceVO;
import kr.spring.performance.vo.SeatVO;
import kr.spring.performance.vo.TicketingVO;
import kr.spring.performance.vo.TotalVO;

@Service
@Transactional
public class PerformanceServiceImpl implements PerformanceService {
	@Autowired
	private PerformanceMapper performanceMapper;

	@Override
	public void insertPerformance(PerformanceVO performance) { 
		performanceMapper.insertPerformance(performance);
	}

	@Override
	public List<PerformanceVO> selectList(Map<String, Object> map) {
		return performanceMapper.selectList(map);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return performanceMapper.selectRowCount(map);
	}

	@Override
	public void insertCinema(CinemaVO cinema) {
		performanceMapper.insertCinema(cinema);
	}

	@Override
	public List<CinemaVO> selectCinemaLoc1() {
		return performanceMapper.selectCinemaLoc1();
	}

	@Override
	public List<CinemaVO> selectCinemaLoc2(Map<String, Object> map) {
		return null;
		
	}

	@Override
	public List<CinemaVO> selectLocation2(String loc1) {
		return performanceMapper.selectLocation2(loc1);
	}

	@Override
	public void insertDate(TicketingVO ticketing) {
		performanceMapper.insertDate(ticketing);
		
	}

	@Override
	public List<CinemaVO> selectCinemaNum(String cinema_location2) {
		return performanceMapper.selectCinemaNum(cinema_location2);
	}

	@Override
	public List<TicketingVO> selectPerformance(int cinema_num) {
		return performanceMapper.selectPerformance(cinema_num);
	}

	@Override
	public List<TicketingVO> selectDate() {
		return performanceMapper.selectDate();
	}

	@Override
	public List<CinemaVO> selectCinemaWithTicketing(Map<String, Object> map) {
		return performanceMapper.selectCinemaWithTicketing(map);
	}

	@Override
	public List<PerformanceVO> selectPerformanceWithTicketing(Map<String, Object> map) {
		return performanceMapper.selectPerformanceWithTicketing(map);
	}

	@Override
	public List<TicketingVO> selectWithTicketing(Map<String, Object> map) {
		return performanceMapper.selectWithTicketing(map);
	}

	@Override
	public List<CinemaVO> adminSelectLocation(String loc1) {
		return performanceMapper.adminSelectLocation(loc1);
	}

	@Override
	public List<CinemaVO> pickCinema(Map<String, Object> map) {
		return performanceMapper.pickCinema(map);
	}

	@Override
	public List<PerformanceVO> pickPerformance(Map<String, Object> map) {
		return performanceMapper.pickPerformance(map);
	}

	@Override
	public List<TicketingVO> pickTicketing(Map<String, Object> map) {
		return performanceMapper.pickTicketing(map);
	}

	@Override
	public List<SeatVO> selectChoice(Map<String, Object> map) {
		return performanceMapper.selectChoice(map);
	}

	@Override
	public CinemaVO choosingCinema(Map<String, Object> map) {
		return performanceMapper.choosingCinema(map);
	}

	@Override
	public PerformanceVO choosingPerformance(Map<String, Object> map) {
		return performanceMapper.choosingPerformance(map);
	}
	
	@Override
	public TicketingVO choosingTicketing(Map<String, Object> map) {
		return performanceMapper.choosingTicketing(map);
	}

	@Override
	public void insertChoice(Map<String, Object> map) {
		performanceMapper.insertChoice(map);
	}

	@Override
	public void updateChoice(int cinema_num, int num) {
		performanceMapper.updateChoice(cinema_num, num);
	}

	@Override
	public void insertPayment(Map<String, Object> map) {
		performanceMapper.insertPayment(map);
	}

	// 결제 내역
	@Override
	public List<CinemaVO> selectPayCinema(Map<String, Object> map) {
		return performanceMapper.selectPayCinema(map);
	}

	@Override
	public List<PerformanceVO> selectPayPerformance(Map<String, Object> map) {
		return performanceMapper.selectPayPerformance(map);
	}

	@Override
	public List<TicketingVO> selectPayTicketing(Map<String, Object> map) {
		return performanceMapper.selectPayTicketing(map);
	}

	@Override
	public List<ChoiceVO> selectPayChoice(Map<String, Object> map) {
		return performanceMapper.selectPayChoice(map);
	}

	@Override
	public List<PaymentVO> selectPayPayment(Map<String, Object> map) {
		return performanceMapper.selectPayPayment(map);
	}

	@Override
	public List<TotalVO> selectPayTotal(Map<String, Object> map) {
		return performanceMapper.selectPayTotal(map);
	}

	@Override
	public List<TotalVO> selectPayAll(Map<String, Object> map) {
		return performanceMapper.selectPayAll(map);
	}

	@Override
	public PerformanceVO selectWithPerformance(int performance_id) {
		return performanceMapper.selectWithPerformance(performance_id);
	}

	@Override
	public int selectPayCount(Map<String, Object> map) {
		return performanceMapper.selectPayCount(map);
	}

//	@Override
//	public void deleteChoice(Map<String, Object> map) {
//		performanceMapper.deleteChoice(map);
//	}

	@Override
	public int countPerformance(int performance_id) {
		return performanceMapper.countPerformance(performance_id);
	}

	@Override
	public void insertSeat(Map<String, Object> map) {
		performanceMapper.insertSeat(map);
	}

	


	
	
	
}
