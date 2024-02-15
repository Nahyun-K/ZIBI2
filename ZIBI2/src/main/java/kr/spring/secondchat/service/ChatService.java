package kr.spring.secondchat.service;

import java.util.List;
import java.util.Map;

import kr.spring.secondchat.vo.ChatRoomVO;
import kr.spring.secondchat.vo.ChatVO;

public interface ChatService {
	//채팅방 체크
	public ChatRoomVO selectroomcheck(Map<String,Object> map);
	
	//판매자 구하기 
	public int selectSellerNum(int sc_num);
	//채팅방 생성
	public void insertChatRoom(ChatRoomVO chatRoomVO); //selectChatRoomNum있음.
	//채팅방 관련 정보 구하기 - 
	public ChatRoomVO selectChatroomDetail(int chatroom_num);
	
	//채팅 메시지 등록
	public void insertChat(ChatVO chatVO);
	//채팅 메시지 읽기
	public List<ChatVO> selectChatDetail(Map<String,Integer> map);
	
	
	//구매자 목록 구하기
	public List<ChatRoomVO> selectChatBuyerList(Map<String,Object> map);
	//구매자 목록 행 개수 구하기
	public int selectChatBuyerListRowCount(Map<String,Object> map);
	
	
}
