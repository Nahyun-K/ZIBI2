package kr.spring.second.controller;

import java.util.Collections;
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

import kr.spring.member.vo.MemberVO;
import kr.spring.second.service.SecondService;
import kr.spring.second.vo.SecondFavVO;
import kr.spring.second.vo.SecondOrderVO;
import kr.spring.second.vo.SecondVO;
import kr.spring.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SecondAjaxController {
	@Autowired
	private SecondService secondService;
	/*============================
	 * 부모글 업로드 파일 삭제
	 *============================*/
	@RequestMapping("/secondhand/deleteFile")
	@ResponseBody
	public Map<String,String> processFile(int sc_num,
						HttpSession session,HttpServletRequest request){
		Map<String,String> mapJson = new HashMap<String,String>();
		
		//로그인이 되어 있어야함
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {//로그인이 안된 경우
			mapJson.put("result", "logout");
		}else {//로그인 된 경우
			//삭제 전에 파일명 추출해야함
			SecondVO vo = secondService.selectSecond(sc_num);
			//먼저 DB에서 지워줌 
			secondService.deleteFile(sc_num);
			//업로드 경로에서 파일 지워줌
			FileUtil.removeFile(request, vo.getSc_filename());
			//그럼 이제 정상적으로 삭제 된 거임
			
			mapJson.put("result", "success");
		}
		return mapJson;
	}
	
	/*============================
	 * 부모글 찜 읽기
	 *============================*/
	@RequestMapping("/secondhand/sc_getFav")
	@ResponseBody	
	public Map<String,Object> getFav(SecondFavVO scfav, HttpSession session){
		log.debug("<<중고거래 찜 SecondFavVO>> : " + scfav);
		
		Map<String,Object> mapJson = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("status", "noFav");//count만 표시하고 상태는 선택하지 않은 
		}else {
			//로그인된 회원번호 셋팅
			scfav.setMem_num(user.getMem_num());
			
			SecondFavVO secondFav = secondService.selectFav(scfav);
			if(secondFav!=null) {//이미 찜 등록한 경우
				mapJson.put("status", "yesFav");
			}else {
				mapJson.put("status", "noFav");
			}
		}
		//좋아요수
		mapJson.put("count", secondService.selectFavCount(scfav.getSc_num()));
		
		return mapJson;
	}
	/*============================
	 * 부모글 찜 등록/삭제  (토글 형태)
	 *============================*/
	@RequestMapping("/secondhand/sc_writeFav")
	@ResponseBody					//scfav에는 전달된 sc_num만 값이 들어있음
	public Map<String,Object> sc_writeFav(SecondFavVO scfav, HttpSession session){
		log.debug("<<중고거래 상세글 찜 등록/삭제 SecondFavVO>> " + scfav);
		
		Map<String,Object> mapJson = new HashMap<String,Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//로그인된 회원번호 셋팅
			scfav.setMem_num(user.getMem_num());
			
			//이전에 찜을 등록했는지 여부 확인
			SecondFavVO secondFav = secondService.selectFav(scfav);//한건의 데이터 읽어옴
			if(secondFav!=null) {//이미 찜 등록한 경우
				secondService.deleteFav(scfav);//찜 삭제
				mapJson.put("status", "noFav");
			}else {//아직 찜 안 한 경우
				secondService.insertFav(scfav);//좋아요 등록
				mapJson.put("status", "yesFav");
			}
			mapJson.put("result", "success");
			//찜 수 						
			mapJson.put("count",secondService.selectFavCount(scfav.getSc_num()));
		}
		return mapJson;
	}
	/*============================
	 * 중고거래 판매내역 - 전체
	 *============================*/
	@RequestMapping("/secondhand/sc_sellAll")
	@ResponseBody							
	public Map<String,Object> sc_sellAll(@RequestParam int mem_num, HttpSession session){
		log.debug("<<중고거래 판매내역 - 전체 mem_num>> : " + mem_num);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		Map<String,Object> mapJson = new HashMap<String,Object>();
		
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			//로그인된 회원번호 셋팅
			//secondvo.setMem_num(user.getMem_num());   //secondvo에는 mem_num과 sc_num이 있음. 
			//log.debug("<<중고거래 판매내역 - 전체- mem_num 셋팅 SecondVO>> : " + secondvo);
			//map.put("mem_num", secondvo.getMem_num());//map에 mem_num넣기(로그인한)
			map.put("mem_num", mem_num); 
			//전체 레코드 수    로그인한 사람의 판매 게시물만 
			int count = secondService.selectMyscCount(map);
			log.debug("<<판매내역 - 전체 레코드 수 count>> : " + count);
			
			List<SecondVO> sellAllList = null;
			if(count > 0) {
				//판매내역 정보
				sellAllList = secondService.selectMyscList(map);
				mapJson.put("result", "success");
			}else {
				sellAllList = Collections.emptyList();//비어있는 배열로 인식하게 하기 위해서
			}
			mapJson.put("count", count);
			mapJson.put("sellAllList", sellAllList);
		}
		
		return mapJson;
	}
	/*============================
	 * 중고거래 판매내역 - 판매중
	 *============================*/
	@RequestMapping("/secondhand/sc_forSale")
	@ResponseBody		
	public Map<String,Object> sc_forSale(@RequestParam int mem_num, HttpSession session){
		log.debug("<<중고거래 판매내역 - 판매중 mem_num>> : " + mem_num);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		Map<String,Object> mapJson = new HashMap<String,Object>();
		
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			map.put("mem_num", mem_num); 
			//판매중인 전체 레코드 수    - 로그인 한 사람의 게시물만
			int count = secondService.selectForSaleCount(map);
			log.debug("<<판매내역 - 판매중 레코드 수 count>> : " + count);
			List<SecondVO> forSaleList = null;
			if(count > 0) {
				forSaleList = secondService.selectForSaleList(map);
				mapJson.put("result", "success");
			}else {
				forSaleList = Collections.emptyList();
			}
			mapJson.put("count", count);
			mapJson.put("forSaleList", forSaleList);
		}
		return mapJson;
	}
	/*============================
	 * 중고거래 판매내역 - 예약대기
	 *============================*/
	@RequestMapping("/secondhand/sc_waitReserve")
	@ResponseBody
	public Map<String,Object> sc_waitReserve(@RequestParam int mem_num, HttpSession session){
		log.debug("<<중고거래 판매내역 - 예약대기 mem_num>> : " + mem_num);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		Map<String,Object> mapJson = new HashMap<String,Object>();
		
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			map.put("mem_num", mem_num); 
			//예약대기 전체 레코드 수    - 로그인 한 사람의 게시물만
			int count = secondService.selectWaitReserveCount(map);
			log.debug("<<판매내역 - 예약대기 레코드 수 count>> : " + count);
			List<SecondVO> waitReserveList = null;
			if(count > 0) {
				waitReserveList = secondService.selectWaitReserveList(map);
				log.debug("<<판매내역 - 예약대기 waitReserveList>> : " + waitReserveList);
				mapJson.put("result", "success");
			}else {
				waitReserveList = Collections.emptyList();
			}
			mapJson.put("count", count);
			mapJson.put("waitReserveList", waitReserveList);
		}
		return mapJson;
	}
	/*============================
	 * 중고거래 판매내역 - 예약대기 페이지 - 예약 확정 버튼 클릭 시 - 예약중으로 상태 변경됨
	 *============================*/
	@RequestMapping("/secondhand/updateOrderReserve")
	@ResponseBody
	public Map<String,Object> updateOrderReserve(@RequestParam int sc_num, HttpSession session){
		log.debug("<<예약대기 페이지 - 예약 확정 sc_num>> : " + sc_num);
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			map.put("result", "logout");
		}else {
			//second테이블 sc_sellstatus=2로 update
			secondService.updateReserve(sc_num);
			//second_order테이블 sc_order_statis=2로 update
			secondService.updateOrderReserve(sc_num);
			map.put("result", "success");
		}
		return map;
	}
	/*============================
	 * 중고거래 판매내역 - 예약대기 페이지 - 예약 거절 버튼 클릭 시 - 판매중으로 상태 변경됨
	 *============================*/
	@RequestMapping("/secondhand/updateOrderReject")
	@ResponseBody
	public Map<String,Object> updateOrderReject(@RequestParam int sc_num, HttpSession session){
		log.debug("<<예약대기 페이지 - 예약 거절 sc_num>> : " + sc_num);
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			map.put("result", "logout");
		}else {
			//second테이블 sc_sellstatus=0로 update
			secondService.updateForSale(sc_num);
			//second_order테이블에서 삭제
			secondService.deleteOrderByScNum(sc_num);
			map.put("result", "success");
		}
		return map;
	}
	
	/*============================
	 * 중고거래 판매내역 - 예약중
	 *============================*/
	@RequestMapping("/secondhand/sc_reserve")
	@ResponseBody
	public Map<String,Object> sc_reserve(@RequestParam int mem_num, HttpSession session){
		log.debug("<<중고거래 판매내역 - 예약중 mem_num>> : " + mem_num);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		Map<String,Object> mapJson = new HashMap<String,Object>();
		
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			map.put("mem_num", mem_num); 
			//예약중인 전체 레코드 수    - 로그인 한 사람의 게시물만
			int count = secondService.selectReserveCount(map);
			log.debug("<<판매내역 - 예약중 레코드 수 count>> : " + count);
			List<SecondVO> reserveList = null;
			if(count > 0) {
				reserveList = secondService.selectReserveList(map);
				log.debug("<<예약중 reserveList>> : " + reserveList);
				mapJson.put("result", "success");
			}else {
				reserveList = Collections.emptyList();
			}
			mapJson.put("count", count);
			mapJson.put("reserveList", reserveList);
		}
		return mapJson;
	}
	/*============================
	 * 중고거래 판매내역 - 판매완료
	 *============================*/
	@RequestMapping("/secondhand/sc_sellFin")
	@ResponseBody
	public Map<String,Object> sc_sellFin(@RequestParam int mem_num, HttpSession session){
		log.debug("<<중고거래 판매내역 - 거래완료 mem_num>> : " + mem_num);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		Map<String,Object> mapJson = new HashMap<String,Object>();
		
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			map.put("mem_num", mem_num); 
			//거래완료인 전체 레코드 수    - 로그인 한 사람의 게시물만
			int count = secondService.selectSellFinCount(map);
			log.debug("<<판매내역 - 거래완료 레코드 수 count>> : " + count);
			List<SecondVO> sellFinList = null;
			if(count > 0) {
				sellFinList = secondService.selectSellFinList(map);
				mapJson.put("result", "success");
			}else {
				sellFinList = Collections.emptyList();
			}
			mapJson.put("count", count);
			mapJson.put("sellFinList", sellFinList);
		}
		return mapJson;
	}
	/*============================
	 * 중고거래 판매내역 - 예약중 페이지 - 판매완료하기 버튼 클릭 시 - 판매완료로 상태 변경됨
	 *============================*/
	@RequestMapping("/secondhand/updateOrderFini")
	@ResponseBody
	public Map<String,Object> updateOrderFini(@RequestParam int sc_num, HttpSession session){
		log.debug("<<예약중 페이지 - 판매완료처리 sc_num>> : " + sc_num);
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			map.put("result", "logout");
		}else {
			//second테이블 sc_sellstatus=3로 update
			secondService.updateSellFin(sc_num);
			//second_order테이블 sc_order_status=3로 update
			secondService.updateOrderSellFin(sc_num);
			map.put("result", "success");
		}
		return map;
	}
	
	/*============================
	 * 중고거래 판매내역 - 숨김처리 글 목록
	 *============================*/
	@RequestMapping("/secondhand/sc_hide")
	@ResponseBody
	public Map<String,Object> sc_hide(@RequestParam int mem_num, HttpSession session){
		log.debug("<<중고거래 판매내역 - 숨김처리 mem_num>> : " + mem_num);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		Map<String,Object> mapJson = new HashMap<String,Object>();
		
		if(user==null) {
			mapJson.put("result", "logout");
		}else {
			map.put("mem_num", mem_num); 
			//숨김처리 sc_show=1(숨김)  전체 레코드 수 - 로그인 한 사람의 게시물만
			int count = secondService.selectHideCount(map);
			log.debug("<<판매내역 - 숨김 레코드 수 count>> : " + count);
			List<SecondVO> sellFinList = null;
			if(count > 0) {
				sellFinList = secondService.selectHideList(map);
				mapJson.put("result", "success");
			}else {
				sellFinList = Collections.emptyList();
			}
			mapJson.put("count", count);
			mapJson.put("sellFinList", sellFinList);
		}
		return mapJson;
	}
	
	/*================================
	 * 중고거래 상태변경(detail) 모달창- 판매중
	 *================================*/
	@RequestMapping("/secondhand/updateForSale")
	@ResponseBody
	public Map<String,Object> updateForSale(@RequestParam int sc_num, HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			map.put("result", "logout");
		}else{
			//판매중으로 update  (sc_sellstatus=0)
			secondService.updateForSale(sc_num);
			map.put("result", "success");
		}
		return map;
	}
	/*================================
	 * 중고거래 상태변경(detail) 모달창- 예약대기
	 *================================*/
	@RequestMapping("/secondhand/updateWaitReserve")
	@ResponseBody
	public Map<String,Object> updateWaitReserve(@RequestParam int sc_num, HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			map.put("result", "logout");
		}else{
			//예약대기로 update  (sc_sellstatus=1)
			secondService.updateWaitReserve(sc_num);
			map.put("result", "success");
		}
		return map;
	}
	/*================================
	 * 중고거래 상태변경(detail) 모달창- 예약중
	 *================================*/
	@RequestMapping("/secondhand/updateReserve")
	@ResponseBody
	public Map<String,Object> updateReserve(@RequestParam int sc_num, HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			map.put("result", "logout");
		}else{
			//예약중으로 update  (sc_sellstatus=2)
			secondService.updateReserve(sc_num);
			map.put("result", "success");
		}
		return map;
	}
	/*================================
	 * 중고거래 상태변경(detail) 모달창- 거래완료
	 *================================*/
	/*삭제하기!!!!!!!!!!!!!!!!!!!
	@RequestMapping("/secondhand/updateSellFin")
	@ResponseBody
	public Map<String,Object> updateSellFin(@RequestParam int sc_num, HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			map.put("result", "logout");
		}else{
			//거래완료로 update  (sc_sellstatus=3)
			secondService.updateSellFin(sc_num);
			map.put("result", "success");
		}
		return map;
	}
	*/
	/*================================
	 * 중고거래 바로 예약(detail) 클릭 시 - 예약 대기로 변경, second_order 테이블에 insert
	 *================================*/
	@RequestMapping("/secondhand/insertScOrder")
	@ResponseBody
	public Map<String,Object> insertScOrder(@RequestParam int sc_num, HttpSession session){
		log.debug("<<바로 예약 sc_num>> : " + sc_num);
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			map.put("result", "logout");
		}else{
			//second 테이블 sc_sellstatus 1(예약대기)로 변경
			secondService.updateWaitReserve(sc_num);
			SecondOrderVO secondOrderVO = new SecondOrderVO();
			secondOrderVO.setSc_buyer_num(user.getMem_num());
			secondOrderVO.setSc_num(sc_num);
			log.debug("<<바로 예약 secondOrderVO>> : " + secondOrderVO);
			secondService.insertSecondOrder(secondOrderVO);
			
			map.put("result", "success");
			map.put("secondOrderVO", secondOrderVO);
		}
		return map;
	}
	/*============================
	 * 중고거래 판매내역 - 끌어올리기
	 *============================*/
	@RequestMapping("/secondhand/updateSysdate")
	@ResponseBody
	public Map<String,Object> updateSysdate(@RequestParam int sc_num, HttpSession session){
		log.debug("<<끌어올리기 sc_num>> : " + sc_num);
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			map.put("result", "logout");
		}else{
			//sc_modify_date SYSDATE로 update
			secondService.updateSysdate(sc_num);
			map.put("result", "success");
		}
		return map;
	}
	
	/*============================
	 * 중고거래 판매내역 - 숨김 처리
	 *============================*/
	@RequestMapping("/secondhand/updateScHide")
	@ResponseBody
	public Map<String,Object> updateScHide(@RequestParam int sc_num, HttpSession session){
		log.debug("<<숨김 처리 sc_num>> : " + sc_num);
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			map.put("result", "logout");
		}else{
			//sc_show 1로 숨김 처리
			secondService.updateScHide(sc_num);
			map.put("result", "success");
		}
		return map;
	}
	/*============================
	 * 중고거래 판매내역 - 숨김 해제
	 *============================*/
	@RequestMapping("/secondhand/updateScShow")
	@ResponseBody
	public Map<String,Object> updateScShow(@RequestParam int sc_num, HttpSession session){
		log.debug("<<숨김 해제 sc_num>> : " + sc_num);
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			map.put("result", "logout");
		}else{
			//sc_show 2로 update -> 글 공개처리
			secondService.updateScShow(sc_num);
			map.put("result", "success");
		}
		return map;
	}
	
	
	
}






















