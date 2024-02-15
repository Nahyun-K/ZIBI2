package kr.spring.second.service;

import java.util.List;
import java.util.Map;

import kr.spring.second.vo.SecondFavVO;
import kr.spring.second.vo.SecondOrderVO;
import kr.spring.second.vo.SecondReviewVO;
import kr.spring.second.vo.SecondVO;
import kr.spring.secondchat.vo.ChatRoomVO;

public interface SecondService {
	//부모글
	public List<SecondVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	public void insertSecond(SecondVO second);
	public SecondVO selectSecond(int sc_num);
	public void updateHit(int sc_num);
	public void updateSecond(SecondVO second);		//글 수정
	public void deleteSecond(int sc_num);
	public void deleteFile(int sc_num);
	
	//판매 상태 변경
	public void updateForSale(int sc_num);//판매중으로 변경
	public void updateWaitReserve(int sc_num);//예약대기로 변경
	public void updateReserve(int sc_num);//예약중으로 변경
	public void updateSellFin(int sc_num);//거래완료로 변경
	
	//글 상세 후기 목록
	public List<SecondVO> selectSellRevList(Map<String,Object> map);
	public int selectSellRevCount(int sc_num);
	
	//거래
	public void insertSecondOrder(SecondOrderVO secondOrderVO);
	//거래 상태 변경
	public void updateOrderReserve(int sc_num);//예약중으로 변경
	
	//거래 상태 변경 second_order테이블 판매완료로 변경
	public void updateOrderSellFin(int sc_num);//판매 완료로 변경
	
	//구매자 선택시 sc_num 존재 여부 (select)
	public SecondOrderVO selectOrderCheck(Map<String,Object> map);
	//구매자 선택시 sc_num 관련 행이 없다면 판매완료 행 insert  sc_order_status를 3으로 insert
	public void insertOrderSellFin(SecondOrderVO secondOrderVO);
	
	
	//숨김 게시글 sc_show=1 숨김처리
	public void updateScHide(int sc_num);
	//숨김 게시글 sc_show=2 공개처리
	public void updateScShow(int sc_num);//숨김 -> 공개로 변경
	
	
	//찜
	public SecondFavVO selectFav(SecondFavVO fav);//한건의 데이터 읽어옴
	public int selectFavCount(int sc_num);
	public void insertFav(SecondFavVO fav);
	public void deleteFav(SecondFavVO secondFav);
	
	//=========  중고거래 마이페이지   ==================
		//판매내역 - 전체 
	public List<SecondVO> selectMyscList(Map<String,Object> map);
	public int selectMyscCount(Map<String,Object> map);//로그인한 사람의 판매글 전체 레코드 수 
	
		//판매내역 - 판매중
	public List<SecondVO> selectForSaleList(Map<String,Object> map);
	public int selectForSaleCount(Map<String,Object> map);//로그인한 사람의 판매중 글 전체 레코드 수 
	
		//판매내역 - 예약대기
	public List<SecondVO> selectWaitReserveList(Map<String,Object> map);
	public int selectWaitReserveCount(Map<String,Object> map);
		//판매내역 - 예약중
	public List<SecondVO> selectReserveList(Map<String,Object> map);
	public int selectReserveCount(Map<String,Object> map);
		//판매내역 - 거래완료
	public List<SecondVO> selectSellFinList(Map<String,Object> map);
	public int selectSellFinCount(Map<String,Object> map);
		//판매내역 - 숨김
	public List<SecondVO> selectHideList(Map<String,Object> map);
	public int selectHideCount(Map<String,Object> map);
	
	//판매내역 - 끌어올리기
	public void updateSysdate(int sc_num);
	
	//구매내역
	public List<SecondOrderVO> selectBuyList(Map<String,Object> map);
	public int selectBuyCount(Map<String,Object> map);
	
	//찜 상품
	public List<SecondVO> selectScFavList(Map<String,Object> map);
	public int selectScFavCount(int mem_num);
	
	//후기
	public void insertScReview(SecondReviewVO review);
	//후기 목록 가져오기  - 후기 작성자 닉네임, 로그인 한 사람(판매자) mem_num 가져오기
	public List<SecondReviewVO> selectReviewList(Map<String,Object> map);
	//후기 목록 행 개수 가져오기
	public int selectReviewCount(int mem_num);
	
	public void deleteOrderByScNum(int sc_num);
	
	//=============== 내 상점- 채팅 내역 ================
	//채팅 내역 목록 구하기
	public List<ChatRoomVO> selectBuyChatList(Map<String,Object> map);
	//채팅 내역 행 개수 구하기
	public int selectBuyChatCount(Map<String,Object> map);
}
