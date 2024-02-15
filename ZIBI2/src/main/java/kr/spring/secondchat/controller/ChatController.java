package kr.spring.secondchat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.vo.MemberVO;
import kr.spring.second.service.SecondService;
import kr.spring.second.vo.SecondOrderVO;
import kr.spring.secondchat.service.ChatService;
import kr.spring.secondchat.vo.ChatRoomVO;
import kr.spring.secondchat.vo.ChatVO;
import lombok.extern.slf4j.Slf4j;
//중고거래 채팅 controller
@Controller
@Slf4j
public class ChatController {
	@Autowired
	private ChatService chatService;
	@Autowired
	private SecondService secondService;
	
	/*================================
	 * 중고거래 채팅
	 *================================*/
	//구매자가 상세페이지에서 채팅하기 버튼 클릭할 때   chatListForBuyer에서 채팅방 생성 여부 처리 한 후 
	@GetMapping("/secondchat/chatListForBuyer")
	public String chatListForBuyer(@RequestParam int sc_num,HttpSession session) {
		log.debug("<<채팅- 채팅방 여부 진입 sc_num >> : " + sc_num);
		
		
		//세션에 로그인된 회원 정보 user에 저장
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("sc_num", sc_num);//판매글 번호
		map.put("buyer_num", user.getMem_num());//로그인한 회원번호를 buyer_num으로 전달(구매자 회원번호)
		
		ChatRoomVO chatroom = chatService.selectroomcheck(map);//db에 레코드가 있다면 한건의 레코드 읽어오고, 없다면 null
		log.debug("<<채팅- 채팅방 여부 chatroom>> : " + chatroom);
		//판매자 구하기 
		int seller_num = chatService.selectSellerNum(sc_num);
		log.debug("<<채팅- 판매자 구하기 seller_num>> : " + seller_num);
		
		//insert에 데이터를 전달해주기 위해 자바빈 생성 
		ChatRoomVO chatRoomVO = new ChatRoomVO();
		chatRoomVO.setSc_num(sc_num);
		chatRoomVO.setBuyer_num(user.getMem_num());
		chatRoomVO.setSeller_num(seller_num);
		log.debug("<<채팅- chatRoomVO에 데이터 세팅 후 > : " + chatRoomVO);
		//채팅방 체크
		if(chatroom!=null) {//이미 채팅방이 생성되어있는 경우 				 	
			return "redirect:/secondchat/chatDetail?chatroom_num=" + chatroom.getChatroom_num();
		}
		//채팅방이 생성되어 있지 않아 채팅방 생성한다
		chatService.insertChatRoom(chatRoomVO);
		return "redirect:/secondchat/chatDetail?chatroom_num=" + chatService.selectroomcheck(map).getChatroom_num();
	}
	
	//판매자가 상세페이지에서 채팅하기 버튼 클릭할 때  chatListForSeller.jsp로
	@GetMapping("/secondchat/chatListForSeller")
	public String chatListForSeller(@RequestParam int sc_num,Model model, HttpSession session) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//판매자 구하기
		int seller_num = chatService.selectSellerNum(sc_num);
		map.put("seller_num", seller_num);//판매자
		map.put("sc_num", sc_num);
		
		//행 개수 읽어오기 
		int count = chatService.selectChatBuyerListRowCount(map);
		log.debug("<<구매자 count >> : " + count);
		List<ChatRoomVO> list = null;
		//구매자 목록 읽어오기
		if(count>0) {
			list = chatService.selectChatBuyerList(map);
		}
		log.debug("<<구매자 목록 정보 list > : " + list);
		model.addAttribute("count",count);
		model.addAttribute("list",list);
		
		return "chatListForSeller";//해당 판매글에 대한 구매자들 목록 페이지 
	}
	
	
	//채팅 메시지 페이지 호출 - 화면 호출만
	@GetMapping("/secondchat/chatDetail")
	public String chatDetail(@RequestParam int chatroom_num, Model model, HttpSession session) {
		//화면 호출만 - chatroomvo에 데이터 담아서 가져오기 조인햇 seller buyer num 가져옥
		
		ChatRoomVO chatRoomVO = chatService.selectChatroomDetail(chatroom_num);
		log.debug("<<채팅 메시지 페이지 호출 - chatRoomVO >> : " + chatRoomVO);
		//chatRoomVO에 chatroom_num,sc_num,sc_title,seller_num,seller,buyer_num,buyer(닉네임) 들어있음
		model.addAttribute("chatRoomVO",chatRoomVO);
		
		return "chatDetail";
	}
	
	//채팅 메시지 읽기     - ajax 통신 
	@RequestMapping("/secondchat/chatDetailAjax")
	@ResponseBody	
	public Map<String,Object> chatDetailAjax(@RequestParam int chatroom_num, HttpSession session) { 
		//메시지를 읽어오기 chatroom_num,user.mem_num->mem_num =>mem_num은 읽었는지 체크하기 위해서
		log.debug("<<채팅 메시지 읽기 - chatroom_num >> : " + chatroom_num);
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			Map<String,Integer> map = new HashMap<String,Integer>();

			map.put("chatroom_num", chatroom_num);//수신그룹
			map.put("mem_num", user.getMem_num());//로그인된 사람. 로그인 된 사람이 포함된 정보를 가져오게끔

			//list에 가져와야 할 것들 : reg_date필요   //selectChatDetail 서비스에는 updateReadCheck와 selectChatDetail mapper가 있다
			List<ChatVO> list = chatService.selectChatDetail(map);
			log.debug("<<채팅 메시지 읽기 - list >> : " + list);
			mapAjax.put("result", "success");
			mapAjax.put("list", list);
			mapAjax.put("user_num", user.getMem_num());
		}
		return mapAjax; 
	}

	//채팅 메시지 전송    - ajax 통신
	@RequestMapping("/secondchat/writeChat")
	@ResponseBody	
	public Map<String,Object> wrtieTalkAjax(ChatVO vo, HttpSession session){
		log.debug("<<채팅 메시지 전송  - ChatVO vo >> : " + vo);
		//vo에 chatroom_num, chat_message 담겨 있음
		Map<String,Object> mapAjax = new HashMap<String,Object>();

		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			//ChatVO에 mem_num 셋팅
			vo.setMem_num(user.getMem_num());

			log.debug("<<채팅 메시지 등록 TalkVO>> : " + vo);
			//채팅 메시지 등록
			chatService.insertChat(vo);

			mapAjax.put("result", "success");
		}
		return mapAjax;
	}
	
	/*================================
	 * 중고거래 거래자 선택 목록 - 채팅한 사람 목록 띄우기 (chatListForSeller에서 거래 확
	 *================================*/
	//판매자가 상세페이지에서 채팅하기 버튼 클릭할 때  chatListForSeller.jsp로
	@GetMapping("/secondchat/chatSelectBuyerList")
	public String chatSelectBuyerList(@RequestParam int sc_num,Model model, HttpSession session) {
		log.debug("<<거래자 선택 sc_num>> : " + sc_num);
		Map<String,Object> map = new HashMap<String,Object>();
		
		//판매자 구하기
		int seller_num = chatService.selectSellerNum(sc_num);
		map.put("seller_num", seller_num);//판매자
		map.put("sc_num", sc_num);
		
		//행 개수 읽어오기 
		int count = chatService.selectChatBuyerListRowCount(map);
		log.debug("<<거래자 선택 count>> : " + count);
		
		//거래완료로 update  (sc_sellstatus=3) 이때, 거래자가 없으므로 second_order테이블에는 update되지 않는다
		secondService.updateSellFin(sc_num);
		map.put("result", "success");
		
		List<ChatRoomVO> list = null;
		
		//구매자 목록 읽어오기
		if(count==0) {	//거래자가 없으면 거래완료처리만 해주고 글 상세로 redirect
			return "redirect:/secondhand/detail?sc_num="+sc_num;
		}
		//count가 0 이상일때  거래자 list 구하고 넘김
		list = chatService.selectChatBuyerList(map);
		log.debug("<<구매자 목록 정보 list > : " + list);
		model.addAttribute("count",count);
		model.addAttribute("list",list);
		model.addAttribute("sc_num" ,sc_num);
		
		return "chatSelectBuyerList";//해당 판매글에 대한 구매자들 목록 페이지 
	}
	/*================================
	 * 중고거래 chatSelectBuyerList.jsp에서 구매자 선택 시 - Ajax
	 *================================*/
	@RequestMapping("/secondchat/sc_selectBuyerAjax")
	@ResponseBody	
	public Map<String,Object> sc_selectBuyerAjax(@RequestParam int sc_num, 
									@RequestParam int buyer_num, HttpSession session) {
		log.debug("<<구매자 선택시 넘기는 sc_num>> : " + sc_num);
		log.debug("<<구매자 선택시 넘기는 buyer_num>> : " + buyer_num);
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		map.put("sc_num", sc_num);//판매글 번호
		SecondOrderVO secondOrder = secondService.selectOrderCheck(map);
		log.debug("<<구매자 선택시 second_order테이블에 sc_num 존재 여부 secondOrder>> : " + secondOrder);
		
		//insert에 전달해주기 위해 자바빈 생성
		SecondOrderVO secondOrderVO = new SecondOrderVO();
		secondOrderVO.setSc_num(sc_num);
		secondOrderVO.setSc_buyer_num(buyer_num);
		log.debug("<<구매자 선택시 secondOrderVO>> : " + secondOrderVO);
		if(user==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			if(secondOrder!=null) {
				//만약 sc_num에 대한 행이 있을 경우 update sc_order_status = 3
				secondService.updateOrderSellFin(sc_num);
			}else {//만약 sc_num에 대한 행이 없을 경우 second_order테이블 insert 해주기
				secondService.insertOrderSellFin(secondOrderVO);
			}
			mapAjax.put("result", "success");
		}
		return mapAjax;
	}
}
