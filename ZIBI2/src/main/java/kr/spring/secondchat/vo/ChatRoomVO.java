package kr.spring.secondchat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatRoomVO {
	private int chatroom_num;  	//채팅방 식별 번호	//pk  
	private int sc_num;      	//판매글 번호    //fk
	private String sc_title;
	private int seller_num;		//판매자 회원 번호//fk
	private String seller; //seller의 mem_nickname
	private int buyer_num;		//구매자 회원번호 //fk
	private String buyer; //buyer의 mem_nickname
	
	//읽어올 정보
	private int read_count;			//읽지 않은 메시지수 
	private String sc_filename;		//판매글 썸네일 사진
	private int sc_sellstatus;  //판매 상태 (0:판매중, 1: 예약대기,2:예약중,3:거래완료)
	private int sc_price; 		//판매 가격
	private String chat_reg_date;//마지막 채팅 일자
	private String mem_nickname;//판매자 닉네임
	//매핑
	private ChatVO chatVO; 
}
