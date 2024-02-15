package kr.spring.secondchat.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.second.vo.SecondOrderVO;
import kr.spring.secondchat.vo.ChatRoomVO;
import kr.spring.secondchat.vo.ChatVO;

@Mapper
public interface ChatMapper {
	//채팅방 체크								//판매글 번호			구매자 회원번호
	@Select("SELECT * FROM chatroom WHERE sc_num=#{sc_num} AND buyer_num=#{buyer_num}")
	public ChatRoomVO selectroomcheck(Map<String,Object> map);
	//채팅방 번호 생성
	@Select("SELECT chatroom_seq.nextval FROM dual")
	public int selectChatRoomNum();
	//판매자 구하기
	@Select("SELECT mem_num FROM second WHERE sc_num=#{sc_num}")
	public int selectSellerNum(int sc_num);
	//채팅방 생성
	@Insert("INSERT INTO chatroom (chatroom_num,sc_num,seller_num,buyer_num) VALUES (#{chatroom_num},#{sc_num},#{seller_num},#{buyer_num})")
	public void insertChatRoom(ChatRoomVO chatRoomVO); 
	//채팅방 관련 정보 구하기 - 
	public ChatRoomVO selectChatroomDetail(int chatroom_num);
	
	//채팅 메시지 번호 생성
	@Select("SELECT chat_seq.nextval FROM dual")
	public int selectChatNum();
	//채팅 메시지 등록
	@Insert("INSERT INTO chat (chat_num,chat_message,chatroom_num,mem_num) VALUES (#{chat_num},#{chat_message},#{chatroom_num},#{mem_num})")
	public void insertChat(ChatVO chatVO);
	//채팅 메시지 읽기
	public List<ChatVO> selectChatDetail(Map<String,Integer> map);
	//읽은 채팅 기록 1에서 0으로 update													//발신자와 로그인한 사람이 다를때
	@Update("UPDATE chat SET chat_read_check=0 WHERE chatroom_num=${chatroom_num} AND mem_num!=${mem_num}")
	public void updateReadCheck(Map<String,Integer> map);
	
	//구매자 목록 구하기
	public List<ChatRoomVO> selectChatBuyerList(Map<String,Object> map);
	//구매자 목록 행 개수 구하기
	public int selectChatBuyerListRowCount(Map<String,Object> map);
	
	
	
}
