package kr.spring.performance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.performance.vo.ChoiceVO;
import kr.spring.performance.vo.CinemaVO;
import kr.spring.performance.vo.PaymentVO;
import kr.spring.performance.vo.PerformanceVO;
import kr.spring.performance.vo.SeatVO;
import kr.spring.performance.vo.TicketingVO;
import kr.spring.performance.vo.TotalVO;

@Mapper
public interface PerformanceMapper {
	// 사용자
	public List<PerformanceVO> selectList(Map<String, Object> map);
	public int selectRowCount(Map<String, Object> map);
	@Select("SELECT cinema_location1 FROM cinema GROUP BY cinema_location1")
	public List<CinemaVO> selectCinemaLoc1();
	public List<CinemaVO> selectCinemaLoc2(Map<String, Object> map);
	@Select("SELECT cinema_num FROM cinema WHERE cinema_location2=#{cinema_location2}")
	public List<CinemaVO> selectCinemaNum(String cinema_location2); // 지역이름으로 지역번호 찾기
	@Select("SELECT * FROM ticketing WHERE cinema_num=#{cinema_num}")
	public List<TicketingVO> selectPerformance(int cinema_num);
	@Select("SELECT DISTINCT ticketing_date FROM ticketing WHERE ticketing_date >= TRUNC(SYSDATE) ORDER BY ticketing_date ASC")
	public List<TicketingVO> selectDate();
	
	// 영화 디테일
	@Select("SELECT * FROM performance WHERE performance_id=#{performance_id}")
	public PerformanceVO selectWithPerformance(int performance_id);
	
	// 상영관 + 날짜 + 영화로 예매할 수 있는 정보
	public List<CinemaVO> selectCinemaWithTicketing(Map<String, Object> map);
	public List<PerformanceVO> selectPerformanceWithTicketing(Map<String, Object> map); // 
	public List<TicketingVO> selectWithTicketing(Map<String, Object> map);
	
	
	// 좌석 선택 페이지
	public List<CinemaVO> pickCinema(Map<String, Object> map);
	public List<PerformanceVO> pickPerformance(Map<String, Object> map);
	public List<TicketingVO> pickTicketing(Map<String, Object> map);
	
	// ticketing_num에 대한 값 (1행)
	public CinemaVO choosingCinema(Map<String, Object> map);
	public PerformanceVO choosingPerformance(Map<String, Object> map);
	public TicketingVO choosingTicketing(Map<String, Object> map);

	
	// 결제 페이지
	public void insertChoice(Map<String, Object> map); // 좌석+영화+상영관+회원에 대한 정보
	public void insertSeat(Map<String, Object> map);
	@Update("UPDATE cinema SET cinema_total=cinema_total-#{num} WHERE cinema_num=#{cinema_num}")
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
	
	
	//좌석 출력
//	@Select("SELECT * FROM perform_choice WHERE ticketing_num=#{ticketing_num}")
	@Select("SELECT s.seat_num,s.seat_row,s.seat_col FROM perform_choice c JOIN perform_seat s ON c.choice_num=s.seat_num WHERE ticketing_num=#{ticketing_num}")
	public List<SeatVO> selectChoice(Map<String, Object> map);
	
	
	//좌석 rollback // => 지워도 됨
//	@Delete("DELETE FROM perform_choice WHERE choice_row=#{choice_row} AND choice_col=#{choice_col} AND choice_adult=#{choice_adult} AND choice_teenage=#{choice_teenage} AND choice_treatment=#{choice_treatment} AND mem_num=#{mem_num} AND ticketing_num=#{ticketing_num}")
//	public void deleteChoice(Map<String, Object> map);
	
	
	// 관리자
	@Select("SELECT count(*) FROM performance WHERE performance_id=#{performance_id}")
	public int countPerformance(int performance_id);
	public void insertPerformance(PerformanceVO performance); // 영화 정보 저장
	public void insertCinema(CinemaVO cinema); // 상영관 정보 저장
	@Select("SELECT DISTINCT cinema_location1, cinema_location2 FROM cinema WHERE cinema_location1=#{loc1}")
	public List<CinemaVO> selectLocation2(String loc1); // 지역1에 따른 지역2 출력

	@Select("SELECT * FROM cinema WHERE cinema_location1=#{loc1}")
	public List<CinemaVO> adminSelectLocation(String loc1);
	public void insertDate(TicketingVO ticketing);
	
}
