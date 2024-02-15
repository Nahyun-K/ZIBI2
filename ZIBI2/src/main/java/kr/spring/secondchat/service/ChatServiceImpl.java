package kr.spring.secondchat.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.second.vo.SecondOrderVO;
import kr.spring.secondchat.dao.ChatMapper;
import kr.spring.secondchat.vo.ChatRoomVO;
import kr.spring.secondchat.vo.ChatVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
public class ChatServiceImpl implements ChatService{
	@Autowired
	private ChatMapper chatMapper;
	
	//채팅방 체크 
	@Override
	public ChatRoomVO selectroomcheck(Map<String, Object> map) {
		return chatMapper.selectroomcheck(map);
	}
	//판매자 구하기
	@Override
	public int selectSellerNum(int sc_num) {
		return chatMapper.selectSellerNum(sc_num);
	}
	
	
	@Override				//현재 chatRoomVO에 buyer_num, sc_num, seller_num 있음
	public void insertChatRoom(ChatRoomVO chatRoomVO) {
		//chatRoomVO에 chatroom_num,sc_num, seller_num, buyer_num이 있어야 insert가능. 
		
		//기본키 생성
		chatRoomVO.setChatroom_num(chatMapper.selectChatRoomNum());
		//채팅방 생성
		chatMapper.insertChatRoom(chatRoomVO);
	}
	
	//채팅방 관련 정보 구하기
	@Override
	public ChatRoomVO selectChatroomDetail(int chatroom_num) {
		return chatMapper.selectChatroomDetail(chatroom_num);
	}
	//채팅 메시지 등록
	@Override
	public void insertChat(ChatVO chatVO) {
		//채팅 메시지 번호 생성
		chatVO.setChat_num(chatMapper.selectChatNum());
		//채팅 메시지 등록
		chatMapper.insertChat(chatVO);
	}
	//채팅 메시지 읽기
	@Override
	public List<ChatVO> selectChatDetail(Map<String, Integer> map) {
		//읽은 채팅 기록 1에서 0으로 update
		chatMapper.updateReadCheck(map);
		//채팅 메시지 읽기
		return chatMapper.selectChatDetail(map);
	}
	//구매자 목록 구하기
	@Override
	public List<ChatRoomVO> selectChatBuyerList(Map<String, Object> map) {
		return chatMapper.selectChatBuyerList(map);
	}
	@Override
	public int selectChatBuyerListRowCount(Map<String, Object> map) {
		return chatMapper.selectChatBuyerListRowCount(map);
	}
}
