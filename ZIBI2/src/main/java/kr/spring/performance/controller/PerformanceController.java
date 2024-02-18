package kr.spring.performance.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.performance.service.PerformanceService;
import kr.spring.performance.vo.CinemaVO;
import kr.spring.performance.vo.PerformanceVO;
import kr.spring.performance.vo.TicketingVO;
import kr.spring.performance.vo.TotalVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PageUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PerformanceController {
	
	//tmdb api 키 호출
	@Value("${HYUN-API-KEY.tmdbKey}")
	private String tmdbKey;
	
	
	// 의존성 주입
	@Autowired
	private PerformanceService performanceService;
	
	@Autowired
	private MemberService memberService;
	
	/*=================================
	 * 기본 레이아웃 (타일즈 설정을 위한 페이지) 
	 *=================================*/
	@RequestMapping("/performance/layout")
	public ModelAndView main() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("performancePage"); //타일스
		return mav; //타일스 설정명
	}
	
	// now playing 영화만 뽑기
	public List<PerformanceVO> getMovie(String[] args) throws IOException, InterruptedException, ParseException {
		log.debug("이전");
		//영화 now-playing 리스트 호출 api
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.themoviedb.org/3/movie/now_playing?language=ko-KR&page=1&region=KR"))
				.header("accept", "application/json")
				.header("Authorization", "Bearer "+tmdbKey)
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		// json형태의 string일 경우
		String jsonData = response.body();
		// reader를 Object로 parse
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(jsonData); 

		// obj를 우선 JSONObject에 담음
		JSONObject jsonMain = (JSONObject)obj;

		// jsonObject에서 jsonArray를 get함
		JSONArray jsonArr = (JSONArray)jsonMain.get("results");
		
		log.debug("" + jsonArr);
		List<PerformanceVO> list = new ArrayList<PerformanceVO>();;
		
		// jsonArr에서 하나씩 JSONObject로 cast해서 사용
		if (jsonArr.size() > 0){
			//List<MovieVO> list = new ArrayList<MovieVO>();
			for(int i=0; i<jsonArr.size(); i++){
				JSONObject jsonObj = (JSONObject)jsonArr.get(i);
				
				PerformanceVO performance = new PerformanceVO();
				
				performance.setPerformance_id(Math.toIntExact((Long) jsonObj.get("id")));
				performance.setPerformance_title((String) jsonObj.get("title"));
				performance.setPerformance_poster((String) jsonObj.get("poster_path"));
				performance.setPerformance_content((String)jsonObj.get("overview"));
				String releaseDateStr = (String) jsonObj.get("release_date");
				Date releaseDate = Date.valueOf(releaseDateStr);
				performance.setPerformance_start_date(releaseDate);
				
				int movie_num = Math.toIntExact((Long) jsonObj.get("id")); // 영화 id 구하기
				
				
				int count = performanceService.countPerformance(movie_num);
				log.debug("<<id의 performance COUNT>> : " + count);
				if(count <= 0) {
					performanceService.insertPerformance(performance);
				}
				
				log.debug("json 값 : " + performance);
				list.add(performance);
				
			}
		}
		log.debug("끝");
		
		return list;
		
	}
	
	/*=================================
	 * [메인] 공연 리스트
	 *=================================*/
	@RequestMapping("/performance/list")
	public ModelAndView getMovieInfo(String[] args) throws IOException, InterruptedException, ParseException {

		
		// ----------------------- 리스트 페이지 실행 시 데이터 저장 시작 --------------------------
		//영화 now-playing 리스트 호출 api
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.themoviedb.org/3/movie/now_playing?language=ko-KR&page=1&region=KR"))
				.header("accept", "application/json")
				.header("Authorization", "Bearer "+tmdbKey)
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		// json형태의 string일 경우
		String jsonData = response.body();
		// reader를 Object로 parse
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(jsonData); 

		// obj를 우선 JSONObject에 담음
		JSONObject jsonMain = (JSONObject)obj;

		// jsonObject에서 jsonArray를 get함
		JSONArray jsonArr = (JSONArray)jsonMain.get("results");
		
		log.debug("" + jsonArr);

		
		// jsonArr에서 하나씩 JSONObject로 cast해서 사용
		if (jsonArr.size() > 0){
			//List<MovieVO> list = new ArrayList<MovieVO>();
			for(int i=0; i<jsonArr.size(); i++){
				JSONObject jsonObj = (JSONObject)jsonArr.get(i);
				
				PerformanceVO performance = new PerformanceVO();
				
				performance.setPerformance_id(Math.toIntExact((Long) jsonObj.get("id")));
				performance.setPerformance_title((String) jsonObj.get("title"));
				performance.setPerformance_poster((String) jsonObj.get("poster_path"));
				performance.setPerformance_content((String)jsonObj.get("overview"));
				String releaseDateStr = (String) jsonObj.get("release_date");
				Date releaseDate = Date.valueOf(releaseDateStr);
				performance.setPerformance_start_date(releaseDate);
				
				int movie_num = Math.toIntExact((Long) jsonObj.get("id")); // 영화 id 구하기
				
				
				int count = performanceService.countPerformance(movie_num);
				log.debug("<<id의 performance COUNT>> : " + count);
				if(count <= 0) {
					performanceService.insertPerformance(performance);
				}
				
				
				//=========2번쨰 api호출=================// 영화 1개 당 상세
				  HttpRequest request2 = HttpRequest.newBuilder() //영화 id
				  .uri(URI.create("https://api.themoviedb.org/3/movie/" + movie_num + "?language=ko-KR")) .header("accept", "application/json")
				  .header("Authorization",
				  "Bearer " + tmdbKey
				  ) .method("GET", HttpRequest.BodyPublishers.noBody()) .build();
				  HttpResponse<String> response2 = HttpClient.newHttpClient().send(request2,HttpResponse.BodyHandlers.ofString());
				  
				  
				  String jsonData2 = response2.body(); 
				  // reader를 Object로 parse 
				  JSONParser parser2 = new JSONParser();
				  Object obj2 = parser2.parse(jsonData2);
				  
				  JSONObject jsonObj2 = (JSONObject)obj2;
				  log.debug("---------------------------------------------------------------------------");
				  log.debug("" + jsonObj2);
				  log.debug("---------------------------------------------------------------------------");
				  
			}
		}
		// ----------------------- 리스트 페이지 실행 시 데이터 저장 끝 --------------------------
		
		
		log.debug("<<목록 메서드>>");
		
		List<PerformanceVO> list = getMovie(args); // 현재 상영되는 영화만 출력
		
		log.debug("<<LIST>> : " + list);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("performanceList"); // tiles 설정 name과 동일해야 함
		mav.addObject("list", list);

		return mav; 
	}
	
	/*=================================
	 * 영화 Detail
	 *=================================*/
	@RequestMapping("/performance/detail") // detail?performance_num=${performance.performance_num}
	public ModelAndView performanceDetail(@RequestParam int performance_num) {
		log.debug("<< 디테일 >>");
		Map<String, Object> map = new HashMap<String, Object>();
		log.debug("<<영화 번호>> : " + performance_num);
		
		PerformanceVO performance = performanceService.selectWithPerformance(performance_num);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("performanceDetail"); // tiles 설정 name과 동일해야 함
		mav.addObject("performance", performance);
		return mav; 
	}
	
	
	/*=================================
	 * 게시판 글 등록
	 *=================================*/
	// 자바빈(VO) 초기화
	@ModelAttribute
	public PerformanceVO initCommand() {
		return new PerformanceVO();
	}
	// 등록 폼 호출
//	@RequestMapping("/performance/write")
	@RequestMapping("/admin/write") // -> /performance/writePerformance로 변경하기
	public String form() {
		log.debug("<<영화 등록 폼>>");
		return "writePerformance"; // write.jsp명과 동일 tiles
	}
	//전송된 데이터 처리
//	@PostMapping("/performance/register")
	@PostMapping("/admin/register")
	public String submit(@Valid PerformanceVO performanceVO, BindingResult result, 
			             HttpServletRequest request, HttpSession session, Model model) throws IllegalStateException, IOException {
		log.debug("<<영화 저장>>" + performanceVO);
		
		// 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		
		// 파일 업로드
		performanceVO.setPerformance_poster(FileUtil.createFile(request, performanceVO.getUpload()));
		
		// sql - 영화 등록
		performanceService.insertPerformance(performanceVO);
		
		//View에 표시할 메시지
		model.addAttribute("message", "영화가 등록되었습니다");
		model.addAttribute("url", request.getContextPath()+"/performance/list");
		
		return "common/resultAlert";
	}
	
	/*=================================
	 * 상영관 정보 등록
	 *=================================*/
	// 자바빈(VO) 초기화
	@ModelAttribute
	public CinemaVO initCinema() {
		return new CinemaVO();
	}
	// 상영관 등록 폼 호출
//	@RequestMapping("/performance/writeCinema")
	@RequestMapping("/admin/writeCinema")
	public String formCinema() {
		log.debug("<<상영관 등록 폼>>");
		return "writeCinema"; // write.jsp명과 동일 tiles
	}
	//전송된 데이터 처리
//	@PostMapping("/performance/registerCinema")
	@PostMapping("/admin/registerCinema")
	public String submitCinema(@Valid CinemaVO CinemaVO, BindingResult result, 
			             HttpServletRequest request, HttpSession session, Model model) throws IllegalStateException, IOException {
		log.debug("<<상영관 저장>>" + CinemaVO);
		
		// 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		
		// sql - 상영관 등록
		performanceService.insertCinema(CinemaVO);
		
		//View에 표시할 메시지
		model.addAttribute("message", "상영관이 등록되었습니다");
		model.addAttribute("url", request.getContextPath()+"/performance/list");
		
		return "common/resultAlert";
	}
	
	/*=================================
	 * 상영관 선택
	 *=================================*/
	// 상영관+영화+날짜 중 영화 출력 페이지 호출
	@GetMapping("/performance/ticketing")
	public ModelAndView ticketPage(@RequestParam(value="performance_num", defaultValue="0") int performance_num, String[] args) throws IOException, InterruptedException, ParseException{
		// 그냥 예매하기 버튼으로 간건지
		// 영화를 클릭하고 예매하기 버튼으로 갔는지 구분하기
		log.debug("<<티켓 페이지>>");
		log.debug("<<선택한 영화 번호-performance_num>> : " + performance_num);
		log.debug("<<오늘 날짜>> : "); // 2024:01:25:17:38:33
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 상영관 리스트 출력
		List<CinemaVO> cinemaList = null;
		cinemaList = performanceService.selectCinemaLoc1();
		
		log.debug(""+cinemaList);
		
		// 전체/검색 레코드 수
		int count = performanceService.selectRowCount(map);
		log.debug("<<영화 개수>> : " + count);
		
		// 영화 리스트 출력
//		List<PerformanceVO> list = null;
//		if(count > 0) {
//			list = performanceService.selectList(map);
//		}
		
		
		// 수정
		List<PerformanceVO> list = getMovie(args); // 현재 상영되는 영화만 출력
		
		
		// 날짜
		List<TicketingVO> dayList = null;
		dayList = performanceService.selectDate();
		log.debug("<<날짜 출력>> : " + dayList);
		String time = PerformanceController.getCurrentDateTime();
		String today = time.substring(0,10); // YYYY:MM:DD:hh:mm // 시간 24시 기준
				
		ModelAndView mav = new ModelAndView();
		mav.setViewName("ticketing"); // tiles 설정
		
		mav.addObject("cinemaList", cinemaList); // 상영관
		
		mav.addObject("list", list); // 영화
		
		// 날짜
		mav.addObject("dayList", dayList);
		mav.addObject("today", today);
		
		return mav; 
	}
	
	
	// 오늘 날짜, 현재 시간
	public static String getCurrentDateTime() {
		java.util.Date today = new java.util.Date(); // java.util.Date VS java.sql.Date
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyy:MM:dd:HH:mm:ss"; // 년 월 일 시 분 초
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
		return formatter.format(today);
	}
	
	
	/*=================================
	 * 영화관,상영관,상영 날짜,상영 시간 선택 폼
	 *=================================*/
	// 자바빈(VO) 초기화
	@ModelAttribute
	public TicketingVO initPerformanceDate() {
		return new TicketingVO();
	}
//	@RequestMapping("/performance/writePerformanceDate")
	@RequestMapping("/admin/writePerformanceDate")
	public ModelAndView formPerformanceDate() {
		log.debug("<<영화관,상영관,상영 날짜,상영 시간 선택 폼>>");
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 영화 select
		List<PerformanceVO> listPerformance = null;
		listPerformance = performanceService.selectList(map);
		
		// 상영관 지역1 select
		List<CinemaVO> listCinemaLoc1 = null;
		listCinemaLoc1 = performanceService.selectCinemaLoc1();
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("writePerformanceDate"); // tiles 설정
		mav.addObject("listPerformance", listPerformance);
		mav.addObject("listCinemaLoc1", listCinemaLoc1);

		return mav; 
	}
	//전송된 데이터 처리
//	@PostMapping("/performance/registerDate")
	@PostMapping("/admin/registerDate")
	public String submitDate(@Valid TicketingVO  ticketingVO, BindingResult result, 
			             HttpServletRequest request, HttpSession session, Model model) throws IllegalStateException, IOException {
		log.debug("<<상영 정보 저장>> : " + ticketingVO);
		
		// 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		performanceService.insertDate(ticketingVO);
		
		//View에 표시할 메시지
		model.addAttribute("message", "날짜 정보가 등록되었습니다");
		model.addAttribute("url", request.getContextPath()+"/admin/policy");
		
		return "common/resultAlert";
	}
	
	/*=================================
	 * 좌석 선택
	 *=================================*/
	// 좌석 선택 페이지
	// [상영관+영화+날짜] 선택 (폼) 페이지 제출 시 -> performanceSeat 페이지로 전송하려면 아래 method와 @RequestMapping이 동시에 있어야 함
	// [상영관+영화+날짜] 선택 (폼) : 전송된 데이터 처리
	@RequestMapping("/performance/updateTicketing")
	public ModelAndView submitDate(@RequestParam(value="ticketing_num",defaultValue="0") Integer ticketing_num,
			HttpServletRequest request, HttpSession session) {
		log.debug("<<좌석 선택>>");
		log.debug("<<ticketing_num>> : " + ticketing_num); // 명량 104
		Map<String, Object> map = new HashMap<String, Object>();
		ModelAndView mav = new ModelAndView();
		MemberVO memberVO = (MemberVO)session.getAttribute("user");
		
		log.debug("<<mem_num>> : " + memberVO.getMem_num());
		log.debug("<<mem_num>> : " + memberVO.getMem_email());
		log.debug("<<mem_num>> : " + memberVO.getMem_nickname());
		
		
		map.put("ticketing_num", ticketing_num);
		
		List<CinemaVO> pickCinema = null;
		List<PerformanceVO> pickPerformance = null;
		List<TicketingVO> pickTicketing = null;
		
		pickCinema = performanceService.pickCinema(map);
		pickPerformance = performanceService.pickPerformance(map);
		pickTicketing = performanceService.pickTicketing(map);

		log.debug("===================<<Controller>>======================");
		TicketingVO tmpTicket = performanceService.choosingTicketing(map);
		CinemaVO tmpCinema = performanceService.choosingCinema(map);
		log.debug("<<pickCinema>> : " + pickCinema);
		log.debug("<<pickPerformance>> : " + pickPerformance);
		log.debug("<<pickTicketing>> : " + pickTicketing);
		
		CinemaVO choiceCinema = null;
		PerformanceVO choicePerformance = null;
		TicketingVO choiceTicketing = null;
		
		// ticketing_num에 대한 값 1행
		choiceCinema = performanceService.choosingCinema(map);
		choicePerformance = performanceService.choosingPerformance(map);
		choiceTicketing = performanceService.choosingTicketing(map);
		log.debug("<<choiceCinema>> : " + choiceCinema);
		log.debug("<<choicePerformance>> : " + choicePerformance);
		log.debug("<<choiceTicketing>> : " + choiceTicketing);
		log.debug("===================<<Controller>>======================");
		
		
		mav.setViewName("performanceSeat"); // tiles 설정 name과 동일해야 함
		mav.addObject("pickCinema", pickCinema);
		mav.addObject("pickPerformance", pickPerformance);
		mav.addObject("pickTicketing", pickTicketing);
		mav.addObject("tmpTicket", tmpTicket);
		mav.addObject("tmpCinema", tmpCinema);

		mav.addObject("choiceCinema", choiceCinema);
		mav.addObject("choicePerformance", choicePerformance);
		mav.addObject("choiceTicketing", choiceTicketing);
		
		return mav; 
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	// 좌석 정보 저장 -> 결제창으로 이동
//	@GetMapping("/performance/submitSeat2")
//	public ModelAndView submitSeat2(String seat_info, int ticketing_num,
//			@RequestParam(value="adult_money",defaultValue="0") int adult_money, 
//			@RequestParam(value="teenage_money",defaultValue="0") int teenage_money, 
//			@RequestParam(value="treatement_money",defaultValue="0") int treatement_money) {
//		log.debug("<<결제창으로 이동>>");
//		ModelAndView mav = new ModelAndView();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("ticketing_num", ticketing_num);
//		
//		log.debug("<<좌석 정보>>" + seat_info); // 공백으로 split하기
//		log.debug("<<일반>> : " + adult_money); // 명
//		log.debug("<<청소년>> : " + teenage_money); // 명
//		log.debug("<<우대>> : " + treatement_money); // 명
//		
//		String[] seatNum = seat_info.split(" ");
//		List<String> seatList = new ArrayList<>();
//		
//		for(int i=0; i<seatNum.length; i++) {
//			log.debug(i + "번째 : " + seatNum[i]);
//			seatList.add(seatNum[i]);
//		}
//		
//		
//		// -------------------------------------------------------------------
//		CinemaVO payCinema = null;
//		PerformanceVO payPerformance = null;
//		TicketingVO payTicketing = null;
//		
//		payCinema = performanceService.choosingCinema(map);
//		payPerformance = performanceService.choosingPerformance(map);
//		payTicketing = performanceService.choosingTicketing(map);
//		
//		
//		log.debug("<<seatList>> : " + seatList);
//		log.debug("================================================");
//		log.debug("<<payCinema>> : " + payCinema);
//		log.debug("<<payPerformance>> : " + payPerformance);
//		log.debug("<<payTicketing>> : " + payTicketing);
//		log.debug("================================================");
//		// -------------------------------------------------------------------
//		
//		
//		
//		
//		mav.setViewName("performancePayment"); // tiles 설정 name과 동일해야 함
//		// ticketing_num에 대한 값 넣어주기
//		mav.addObject("payCinema", payCinema);
//		mav.addObject("payPerformance", payPerformance);
//		mav.addObject("payTicketing", payTicketing);
//		
//		
//		//가격 -- 나중에 넣어주기
//		mav.addObject("adult_money", adult_money); // 명
//		mav.addObject("teenage_money", teenage_money); // 명
//		mav.addObject("treatement_money", treatement_money); // 명
//		// 좌석 정보
//		mav.addObject("seatList", seatList);
//		
//		return mav; 
//	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	// 좌석 정보 insert ChoiceVO - 행/열/인원/회원번호/ticketing_num -> 결제창으로 이동
	@GetMapping("/performance/submitSeat")
	public ModelAndView submitSeat(String seat_info, int ticketing_num, int cinema_num,
			@RequestParam(value="adult_money",defaultValue="0") int adult_money, 
			@RequestParam(value="teenage_money",defaultValue="0") int teenage_money, 
			@RequestParam(value="treatement_money",defaultValue="0") int treatement_money,
			HttpServletRequest request, HttpSession session) {
		log.debug("<<결제창으로 이동>>");
		MemberVO memberVO = (MemberVO)session.getAttribute("user");
		
		ModelAndView mav = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ticketing_num", ticketing_num);
		
		log.debug("<<좌석 정보>>" + seat_info); // 공백으로 split하기
		log.debug("<<일반>> : " + adult_money); // 명
		log.debug("<<청소년>> : " + teenage_money); // 명
		log.debug("<<우대>> : " + treatement_money); // 명
		
		String[] seatNum = seat_info.split(" ");
		List<String> seatList = new ArrayList<>();
		
		
		// 좌석 정보 insert - ChoiceVO
		for(int i=0; i<seatNum.length; i++) {
			Map<String, Object> mapChoice = new HashMap<String, Object>();
			log.debug(i + "번째 : " + seatNum[i]);
			String[] rowAndCol = seatNum[i].split("_"); // 행열 나누기 
			log.debug("<<행>> "  + Integer.parseInt(rowAndCol[0]));
			log.debug("<<열>> "  + Integer.parseInt(rowAndCol[1]));
			mapChoice.put("choice_row", Integer.parseInt(rowAndCol[0]));
			mapChoice.put("choice_col", Integer.parseInt(rowAndCol[1]));
			mapChoice.put("choice_adult", adult_money);
			mapChoice.put("choice_teenage", teenage_money);
			mapChoice.put("choice_treatment", treatement_money);
			mapChoice.put("mem_num", memberVO.getMem_num());
			mapChoice.put("ticketing_num", ticketing_num);
			
			
			performanceService.insertChoice(mapChoice);
			
			seatList.add(seatNum[i]);
		}
		
		// 여석 UPDATE - CinemaVO
		log.debug("<<결제 인원 수>> : "  + (adult_money+teenage_money+treatement_money));
		performanceService.updateChoice(cinema_num, (adult_money+teenage_money+treatement_money));
		
		
		// -------------------------------------------------------------------
		CinemaVO payCinema = null;
		PerformanceVO payPerformance = null;
		TicketingVO payTicketing = null;
		
		payCinema = performanceService.choosingCinema(map);
		payPerformance = performanceService.choosingPerformance(map);
		payTicketing = performanceService.choosingTicketing(map);
		
		
		log.debug("<<seatList>> : " + seatList);
		log.debug("================================================");
		log.debug("<<payCinema>> : " + payCinema);
		log.debug("<<payPerformance>> : " + payPerformance);
		log.debug("<<payTicketing>> : " + payTicketing);
		log.debug("================================================");
		// -------------------------------------------------------------------
		
		
		mav.setViewName("performancePayment"); // tiles 설정 name과 동일해야 함
		// ticketing_num에 대한 값 넣어주기
		mav.addObject("payCinema", payCinema);
		mav.addObject("payPerformance", payPerformance);
		mav.addObject("payTicketing", payTicketing);
		
		
		//가격 -- 나중에 넣어주기
		mav.addObject("adult_money", adult_money); // 명
		mav.addObject("teenage_money", teenage_money); // 명
		mav.addObject("treatement_money", treatement_money); // 명
		// 좌석 정보
		mav.addObject("seatList", seatList);
				
		return mav; 
	}
		
	
	
	
	
	/*=================================`
	 * 결제
	 *=================================*/
	@RequestMapping("/performance/choiceSeat")
	public ModelAndView choiceSeat(@RequestParam String uid,
//            						 @RequestParam String choice_seat,
//			                         @RequestParam int choice_adult,
//			                         @RequestParam int choice_teenage,
//			                         @RequestParam int choice_treatment,
//			                         @RequestParam int ticketing_num,
//			                         @RequestParam int cinema_num,
			                         HttpServletRequest request, HttpSession session) {
		log.debug("<<결제끝>>");

		MemberVO memberVO = (MemberVO)session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		log.debug("<<uid>> "  + uid);

		Integer user = memberVO.getMem_num();
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("mem_num", user);
		map.put("payment_uid", uid);
		List<TotalVO> total = performanceService.selectPayTotal(map);
		List<TotalVO> all = performanceService.selectPayAll(map);
		

//		log.debug("<<ticketing_num>> "  + ticketing_num);
//		log.debug("<<cinema_num>> "  + cinema_num);
//		log.debug("<<choice_seat>> "  + choice_seat);
//		log.debug("<<choice_adult>> "  + choice_adult);
//		log.debug("<<choice_teenage>> "  + choice_teenage);
//		log.debug("<<choice_treatment>> "  + choice_treatment);
//		log.debug("<<ticketing_num>> "  + ticketing_num);
		
		mav.setViewName("performanceShowTicket"); // tiles 설정 name과 동일해야 함
		mav.addObject("total", total);
		mav.addObject("all", all);
		return mav; 
	}
	
	
	/*=================================`
	 * 결제 완료 = 결제 정보 + 티켓
	 *=================================*/
	@RequestMapping("/performance/showTicket")
	public ModelAndView ticket(HttpServletRequest request, HttpSession session) {
		
		MemberVO memberVO = (MemberVO)session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		
		
		
		mav.setViewName("performanceShowTicket"); // tiles 설정 name과 동일해야 함
		
		return mav; 
		
	}
	
	/*=================================`
	 * 결제 내역
	 *=================================*/
	@RequestMapping("/performance/history")
	public ModelAndView history(@RequestParam(value="pageNum",defaultValue="1") int currentPage,
								HttpServletRequest request, HttpSession session) {
		
		MemberVO memberVO = (MemberVO)session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		
		Integer user = memberVO.getMem_num();
		log.debug("<<회원번호 user>> : " + user);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("mem_num", user);

		List<TotalVO> total = performanceService.selectPayTotal(map);
		List<TotalVO> all = performanceService.selectPayAll(map);
		log.debug("" + total);
		log.debug("<<pageNum>> : " + currentPage);
		int count = performanceService.selectPayCount(map);
		log.debug("<<count>> : " + count);
		PageUtil page = new PageUtil(currentPage, count, 20, 10 ,"total");
		
		
		mav.setViewName("performanceHistory"); // tiles 설정 name과 동일해야 함
		mav.addObject("total", total);
		mav.addObject("all", all);
		mav.addObject("page", page.getPage());
		
		return mav; 
		
	}
	
	
	
	

}
