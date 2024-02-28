package kr.spring.performance.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.performance.service.PerformanceService;
import kr.spring.performance.vo.CinemaVO;
import kr.spring.performance.vo.PerformanceVO;
import kr.spring.performance.vo.SeatVO;
import kr.spring.performance.vo.TicketingVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PerformanceAjaxController {
	// 의존성 주입
	@Autowired
	private PerformanceService performanceService;
	
	@Autowired
	private MemberService memberService; 
	
	/*=================================
	 * [관리자] 상영관 - 상영관 + 2관 
	 *=================================*/
	@RequestMapping("/admin/adminSelectLocation")
	@ResponseBody
	public Map<String, Object> adminSelectLocation(@RequestParam(value="location1") String loc1, HttpSession session, HttpServletRequest request){
		Map<String, Object> mapJson = new HashMap<String, Object>();
		List<CinemaVO> listLoc2 = null;
		listLoc2 = performanceService.adminSelectLocation(loc1);
		mapJson.put("result", "success");
		
		mapJson.put("listLoc2", listLoc2);
		
		log.debug("<<admin loc1>> : " + loc1);
		log.debug("<<admin listLoc2>> : " + listLoc2);
		
		return mapJson;
	}
	
	/*=================================
	 * [사용자]
	 *=================================*/
	@RequestMapping("/performance/selectLocation")
	@ResponseBody
	public Map<String, Object> selectLocation(@RequestParam(value="location1") String loc1, HttpSession session, HttpServletRequest request){
		Map<String, Object> mapJson = new HashMap<String, Object>();
		List<CinemaVO> listLoc2 = null;
		listLoc2 = performanceService.selectLocation2(loc1);
		mapJson.put("result", "success");
		
		mapJson.put("listLoc2", listLoc2);
		
		log.debug("<<loc1>> : " + loc1);
		log.debug("<<listLoc2>> : " + listLoc2);
		
		return mapJson;
	}
	
	@RequestMapping("/performance/selectLocList")
	@ResponseBody
	public Map<String, Object> selectLocList(HttpSession session, HttpServletRequest request){
		Map<String, Object> mapJson = new HashMap<String, Object>();
		List<CinemaVO> listColor = null;
		listColor = performanceService.selectCinemaLoc1();
		mapJson.put("result", "success");
		mapJson.put("listColor", listColor);
		
		log.debug("<<listColor>> : " + listColor);
		
		return mapJson;
	}
	
	@RequestMapping("/performance/locationNum")
	@ResponseBody // 지역2 str으로 해당 상영관의 번호 알아내기
	public Map<String, Object> locationNum(@RequestParam(value="location2") String location2, HttpSession session, HttpServletRequest request){
		Map<String, Object> mapJson = new HashMap<String, Object>();
		List<CinemaVO> locNum = null;
		locNum = performanceService.selectCinemaNum(location2);
		mapJson.put("result", "success");
		mapJson.put("locNum", locNum);
		
		log.debug("<<locNum>> : " + locNum);
		
		return mapJson;
	}
	
	@RequestMapping("/performance/resultPerformance")
	@ResponseBody // 상영관 + 날짜로 영화 list와 예매할 수 있는 상영관 찾기
	public Map<String, Object> resultPerformance(@RequestParam(value="cinema") String cinema, 
			                              @RequestParam(value="day") String day, 
			                              @RequestParam(value="performance_num") String performance_num,
			                              HttpSession session, HttpServletRequest request){
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<시작>>>>>>>>>>>>>>>>>>>>>>");

		mapJson.put("result", "success");
		List<CinemaVO> locationNum = null;
		locationNum = performanceService.selectCinemaNum(cinema); // cinema : 지역2 -> 지역 번호 찾기
		log.debug("<<지역번호 길이>> : "  + locationNum.size());
		log.debug("<<지역>> : " + cinema);
		
		log.debug("<<날짜 출력>> : " + day);
		log.debug("<<영화 번호 출력>> : " + performance_num);

		
		// sql에 전송할 Map - 상영관 번호,선택 날짜, 오늘 날짜, 현재 시각 + if 영화 정보
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("day", day);
		
		// 지역번호 여러 개
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<locationNum.size(); i++) {
			log.debug("<<지역 번호 출력>> : "+ locationNum.get(i).getCinema_num());
			list.add(locationNum.get(i).getCinema_num());
		}
		if(list!=null) {
			log.debug("<<list 값>> : " + list);
			map.put("list", list);
		}

		// 영화가 있을 경우
		if(performance_num != "" || performance_num != null) { // 영화가 있을 때 // ""가 맞음 null은 정확성을 위해 명시
			map.put("performance_num",performance_num);
		}
		
		// 상영관
		List<CinemaVO> resultCinema = null;
		resultCinema = performanceService.selectCinemaWithTicketing(map);
		log.debug("<<resultCinema>> : " + resultCinema);
		
		// 영화
		List<PerformanceVO> resultPerformance = null;
		resultPerformance = performanceService.selectPerformanceWithTicketing(map);
		log.debug("<<resultPerformance>> : " + resultPerformance);
		
		// 상영관+영화+날짜
		List<TicketingVO> resultTicketing = null;
		resultTicketing = performanceService.selectWithTicketing(map);
		log.debug("<<resultTicketing>> : " + resultTicketing);
			
		log.debug("<<<<<<<<<<<<<<<<<<<<<<끝>>>>>>>>>>>>>>>>>>>>>>");
		
		mapJson.put("resultCinema", resultCinema);
		mapJson.put("resultPerformance", resultPerformance);
		mapJson.put("resultTicketing", resultTicketing);
		
		return mapJson;
		
	}
	
	
	/*=================================
	 * 좌석 선택 페이지 - 좌석 선택
	 *=================================*/
	@RequestMapping("/performance/drawSeat")
	@ResponseBody
	public Map<String, Object> drawSeat(@RequestParam(value="ticketing_num",defaultValue="0") Integer ticketing_num, HttpSession session, HttpServletRequest request){
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		Map<String, Object> map = new HashMap<String, Object>();
		log.debug("<<Mem_num>> : " + user.getMem_num());
		log.debug("<<ticketing_num 값 알아내기>>: " + ticketing_num);
//		map.put("mem_num", user.getMem_num());
		map.put("ticketing_num", ticketing_num);
		
		Map<String, Object> mapJson = new HashMap<String, Object>();

		List<CinemaVO> pickCinema = null;
		List<PerformanceVO> pickPerformance = null;
		List<TicketingVO> pickTicketing = null;
		List<SeatVO> choosenSeat = null;
		
		// sql
		pickCinema = performanceService.pickCinema(map);
		pickPerformance = performanceService.pickPerformance(map);
		pickTicketing = performanceService.pickTicketing(map);
		
		// 이미 예약된 좌석 선택
		choosenSeat = performanceService.selectChoice(map);

		
		log.debug("===================<<Ajax>>======================");
		log.debug("<<pickCinema>> : " + pickCinema);
		log.debug("<<pickPerformance>> : " + pickPerformance);
		log.debug("<<pickTicketing>> : " + pickTicketing);
		log.debug("<<choosenSeat>> : " + choosenSeat);

		log.debug("===================<<Ajax>>======================");
		
		// ajax
		mapJson.put("pickCinema", pickCinema);
		mapJson.put("pickPerformance", pickPerformance);
		mapJson.put("pickTicketing", pickTicketing);
		mapJson.put("choosenSeat", choosenSeat);
		return mapJson;
	}
	
	
	
	
	/*=================================
	 * 결제 - 성공
	 *=================================*/
	@RequestMapping("/performance/initPayment")
	@ResponseBody // 지역2 str으로 해당 상영관의 번호 알아내기
	public Map<String, Object> initPayment(@RequestParam(value="imp_uid") String imp_uid, 
			                               @RequestParam(value="merchant_uid") String merchant_uid, 
			                               @RequestParam(value="pay_method") String pay_method, 
			                               @RequestParam(value="total_price") int total_price, 
			                               @RequestParam(value="ticketing_num") int ticketing_num, 
			                               @RequestParam(value="cinema_num") String cinema_num, 
			                               @RequestParam(value="choice_seat") String choice_seat, 
			                               @RequestParam(value="choice_adult") int choice_adult, 
			                               @RequestParam(value="choice_teenage") int choice_teenage, 
			                               @RequestParam(value="choice_treatment") int choice_treatment, 
			                               HttpSession session, HttpServletRequest request){
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		log.debug("<<Mem_num>> : " + user.getMem_num());
		Map<String, Object> mapJson = new HashMap<String, Object>();
		log.debug("====== << 결제 >> =======");
		
		choice_seat = choice_seat.substring(1, choice_seat.length()-1);
		choice_seat = choice_seat.replaceAll("[,]", "");
		String[] seatNum = choice_seat.split(" ");
		List<String> seatList = new ArrayList<>();

		Map<String, Object> mapChoice = new HashMap<String, Object>();
		mapChoice.put("choice_adult", choice_adult);
		mapChoice.put("choice_teenage", choice_teenage);
		mapChoice.put("choice_treatment", choice_treatment);
		mapChoice.put("mem_num", user.getMem_num());
		mapChoice.put("ticketing_num", ticketing_num);

		performanceService.insertChoice(mapChoice); // max 할 때 mem_num도 같이
		
		// 좌석 정보 insert - ChoiceVO <<<<<<<<<<<INSERT 여기서 하는거 없애기>>>>>>>>>>>
		for(int i=0; i<seatNum.length; i++) {
			Map<String, Object> mapSeat = new HashMap<String, Object>();
			log.debug(i + "번째 : " + seatNum[i]);
			String[] rowAndCol = seatNum[i].split("_"); // 행열 나누기 
			log.debug("<<행>> "  + Integer.parseInt(rowAndCol[0]));
			log.debug("<<열>> "  + Integer.parseInt(rowAndCol[1]));
			
//			mapChoice.put("choice_row", Integer.parseInt(rowAndCol[0]));
//			mapChoice.put("choice_col", Integer.parseInt(rowAndCol[1]));
			mapSeat.put("mem_num", user.getMem_num());
			mapSeat.put("choice_row", Integer.parseInt(rowAndCol[0]));
			mapSeat.put("choice_col", Integer.parseInt(rowAndCol[1]));
			
			// for문 밖으로 빼기
			performanceService.insertSeat(mapSeat);
			
			seatList.add(seatNum[i]);
		}
		
		
		// 포트원 결제모듈에서 결제건별로 고유하게 채번하는 ID
//		log.debug("<< imp_uid >> : " + imp_uid); // 결제창을 띄우는 순간 imp_uid 자동 생성됨
		log.debug("<< merchant_uid >> : " + merchant_uid); // uid
		log.debug("<< pay_method >> : " + pay_method); // type
		log.debug("<< total_price >> : " + total_price); // price
		log.debug("<< ticketing_num >> : " + ticketing_num); // choice_num
		log.debug("<< cinema_num >> : " + cinema_num);
		log.debug("<< choice_seat >> : " + choice_seat); // choice_num
		log.debug("<< choice_adult >> : " + choice_adult); // 명 // choice_num
		log.debug("<< choice_teenage >> : " + choice_teenage); // 명 // choice_num
		log.debug("<< choice_treatment >> : " + choice_treatment); // 명 // choice_num
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("payment_uid", merchant_uid);
		map.put("payment_type", pay_method);
		map.put("payment_price", total_price);
		map.put("payment_state", 1);
		map.put("mem_num", user.getMem_num());
		map.put("choice_adult", choice_adult);
		map.put("choice_teenage", choice_teenage);
		map.put("choice_treatment", choice_treatment);
		map.put("ticketing_num", ticketing_num);
		
//		payment : uid, type, price, state, date, modify_date, mem_num, choice_num
		performanceService.insertPayment(map);
		
//		String[] str = choice_seat.split(" ");
//		String match = "[^0-9_]";
//		for(int i=0; i<str.length; i++) {
//			String seat = str[i].replaceAll(match, "");
//			String[] seats = seat.split("_");
//			// seats[0] : 행 seats[1] : 열
//			log.debug("<<Seat>> : " + seats[0] + " " + seats[1]);
//
//			Map<String, Object> map = new HashMap<String, Object>();
//			
//			map.put("payment_uid", merchant_uid);
//			map.put("payment_type", pay_method);
//			map.put("payment_price", total_price);
//			map.put("payment_state", 1);
//			map.put("mem_num", user.getMem_num());
//			map.put("choice_row", Integer.parseInt(seats[0])); // 행
//			map.put("choice_col", Integer.parseInt(seats[1])); // 열
//			map.put("choice_adult", choice_adult);
//			map.put("choice_teenage", choice_teenage);
//			map.put("choice_treatment", choice_treatment);
//			map.put("ticketing_num", ticketing_num);
//			
////			payment : uid, type, price, state, date, modify_date, mem_num, choice_num
//			performanceService.insertPayment(map);
//			
//		}
//		

		mapJson.put("result", "success");
		log.debug("====== << 결제창 이동 >> =======");
		return mapJson;
	}
	
	
	/*=================================
	 * 결제 - 실패
	 *=================================*/
	@RequestMapping("/performance/errorPayment")
	@ResponseBody // 지역2 str으로 해당 상영관의 번호 알아내기
	public Map<String, Object> errorPayment(
            @RequestParam(value="ticketing_num") int ticketing_num, 
            @RequestParam(value="choice_seat") String choice_seat, 
            @RequestParam(value="choice_adult") int choice_adult, 
            @RequestParam(value="choice_teenage") int choice_teenage, 
            @RequestParam(value="choice_treatment") int choice_treatment, 
            HttpSession session, HttpServletRequest request){

		MemberVO user = (MemberVO)session.getAttribute("user");
		
		log.debug("<<Mem_num>> : " + user.getMem_num());
		
		log.debug("<< ticketing_num >> : " + ticketing_num); // choice_num
		log.debug("<< choice_seat >> : " + choice_seat); // choice_num
		log.debug("<< choice_adult >> : " + choice_adult); // 명 // choice_num
		log.debug("<< choice_teenage >> : " + choice_teenage); // 명 // choice_num
		log.debug("<< choice_treatment >> : " + choice_treatment); // 명 // choice_num
		
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		log.debug("====== << ERROR >> =======");
		// 롤백
		String[] str = choice_seat.split(" ");
		String match = "[^0-9_]";
		for(int i=0; i<str.length; i++) {
			String seat = str[i].replaceAll(match, "");
			String[] seats = seat.split("_");
			// seats[0] : 행 seats[1] : 열
			log.debug("<<Seat>> : " + seats[0] + " " + seats[1]);

			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("mem_num", user.getMem_num());
			map.put("choice_row", Integer.parseInt(seats[0])); // 행
			map.put("choice_col", Integer.parseInt(seats[1])); // 열
			map.put("choice_adult", choice_adult);
			map.put("choice_teenage", choice_teenage);
			map.put("choice_treatment", choice_treatment);
			map.put("ticketing_num", ticketing_num);
			
			
			// 지워야 함
			//performanceService.deleteChoice(map);
			
		}
		
		log.debug("====== << ERROR >> =======");
		
		return mapJson;
	}

	
	
	
	
	
}
