package kr.spring.policy.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.policy.service.PolicyService;
import kr.spring.policy.vo.PolicyVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class StatsController {
	
	@Autowired
	PolicyService policyService;
	
	//공공데이터 키
	@Value("${NA-API-KEY.statsConsumerkey}")
	private String consumer_key;
	@Value("${NA-API-KEY.statsConsumerSecret}")
	private String consumer_secret;
	
	private String accessToken;
	
	/*------------------url 문자열 생성, json 데이터 생성-------------------*/
	@RequestMapping("/stats/getData")
	@ResponseBody
	public Map<String,String> getStats(@RequestParam String selectYear,@RequestParam String inputYear) throws Exception {
		
		//서비스 ID, 보안 키로 액세스 토큰 받아오기
		String apiUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json"; //액세스 토큰을 받아올 주소
		
		StringBuilder urlBuilder = new StringBuilder(apiUrl);
		urlBuilder.append("?" + URLEncoder.encode("consumer_key","UTF-8") + "="+consumer_key); //공공데이터에서 요청한 파라미터값 저장
		urlBuilder.append("&" + URLEncoder.encode("consumer_secret","UTF-8") + "=" + URLEncoder.encode(consumer_secret, "UTF-8"));
		
		String jsonData = statsUrl(urlBuilder);
		
		JSONParser jsonParser = new JSONParser(); //문자열을 Json 형식에 맞게 Object로 파싱할 수 있는 Parser
		Object obj = jsonParser.parse(jsonData); //JsonParser로 Json 문자열을 Obejct 형식으로 파싱
		JSONObject jsonObj = (JSONObject)obj; //Object 형식의 데이터를 JSONObject형식으로 형변환
		JSONObject jsonToken = (JSONObject)jsonObj.get("result"); //json 데이터 내 액세스 토큰이 든 json 키 result 추출
		accessToken = (String)jsonToken.get("accessToken"); //액세스 토큰 추출
		
		
		//총 가구수
		apiUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/stats/population.json";
		
		String year = null; 
		
		if(selectYear!=null && !selectYear.equals("0")) {
			year = selectYear;
			log.debug("<<데이터 1>>" + selectYear);
		} else {
			year = inputYear;
			log.debug("<<데이터 2>>" + inputYear);
		}
		String low_search = "1";
		
		Map<String, String> map = new HashMap<String, String>();

		urlBuilder = new StringBuilder(apiUrl);
		urlBuilder.append("?" + URLEncoder.encode("accessToken","UTF-8") + "="+accessToken);
		urlBuilder.append("&" + URLEncoder.encode("year","UTF-8") + "=" + URLEncoder.encode(year, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("low_search","UTF-8") + "=" + URLEncoder.encode(low_search, "UTF-8"));
		
		jsonData = statsUrl(urlBuilder);
		
		obj = jsonParser.parse(jsonData); //JsonParser로 Json 문자열을 Obejct 형식으로 파싱
		jsonObj = (JSONObject)obj; //Object 형식의 데이터를 JSONObject형식으로 형변환
		JSONArray array1 = (JSONArray)jsonObj.get("result"); //json 데이터 내 총 가구수가 든 json 추출 (배열 형태로 존재하므로 array 사용)
		
		if(array1==null) {
			map.put("result","failed");
		} else {
			//1인 가구 수
			apiUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/stats/household.json";
			String household_type = "A0"; //조회 종료년월

			urlBuilder = new StringBuilder(apiUrl);
			urlBuilder.append("?" + URLEncoder.encode("accessToken","UTF-8") + "="+accessToken);
			urlBuilder.append("&" + URLEncoder.encode("year","UTF-8") + "=" + URLEncoder.encode(year, "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("household_type","UTF-8") + "=" + URLEncoder.encode(household_type, "UTF-8"));
			
			jsonData = statsUrl(urlBuilder);
			
			obj = jsonParser.parse(jsonData); //JsonParser로 Json 문자열을 Obejct 형식으로 파싱
			jsonObj = (JSONObject)obj; //Object 형식의 데이터를 JSONObject형식으로 형변환
			
			JSONArray array2 = (JSONArray)jsonObj.get("result"); //json 데이터 내 1인 가구수가 든 json 추출 (배열 형태로 존재하므로 array 사용)
					
			updateDB(year, array1, array2); //DB 업데이트
			
			map.put("result","success");
		}
		
		return map;
	}
	
	/*------------------json 데이터 DB 업데이트-------------------*/
	private void updateDB(String year, JSONArray array1, JSONArray array2) {
		
		for(int i=0; i<array1.size(); i++){
			JSONObject jsonFinalData1 = (JSONObject)array1.get(i); //배열에 들어있는 json 데이터를 꺼냄
			JSONObject jsonFinalData2 = (JSONObject)array2.get(i); //배열에 들어있는 json 데이터를 꺼냄
			
			PolicyVO policyVO = new PolicyVO();
			
			int district_num = Integer.parseInt((String)jsonFinalData1.get("adm_cd"));//행정구역 번호
			String district_name = (String)jsonFinalData1.get("adm_nm");//행정구역명
			int tot_family = Integer.parseInt((String)jsonFinalData1.get("tot_family"));//전체 가구수
			int househod_cnt = Integer.parseInt((String)jsonFinalData2.get("household_cnt"));//1인 가구수
			
			policyVO.setDistrict_num(district_num); //행정구역 번호 
			policyVO.setDistrict_name(district_name); //행정구역명
			policyVO.setTot_family(tot_family); //전체 가구수
			policyVO.setYear(year); //년도
			policyVO.setHousehold_cnt(househod_cnt); //1인 가구수
			
			PolicyVO db_policy = policyService.selectStats(district_num);
			
			if(db_policy==null) { //만약 데이터가 없으면 인서트
				policyService.insertDistrict(policyVO); //행정구역 번호, 행정구역명
				policyService.insertPolicy(policyVO); //행정구역 번호
				policyService.insertStats(policyVO); //행정구역 번호, 년도, 총가구원수, 1인 가구수
			} else { //만약 데이터가 있으면 업데이트
				policyService.updateStats(policyVO);
			}
		}
	}
	
	/*------------------url 생성, json 응답 받아옴-------------------*/
	private String statsUrl(StringBuilder urlBuilder) throws Exception {
		
		URL url = new URL(urlBuilder.toString()); //url 생성
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("GET"); //get 방식으로 전송
		conn.setRequestProperty("Content-type", "application/json"); //json으로 전송
		conn.setRequestProperty("Accept", "application/json"); //json으로 응답받음

		log.debug("<<응답 코드>>" + conn.getResponseCode()); //응답 코드 확인

		BufferedReader rd;

		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}

		rd.close();
		conn.disconnect(); //url 연결 끊음
		
		return sb.toString(); //json 데이터를 문자열로 변환
	}
}