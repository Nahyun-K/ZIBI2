package kr.spring.second.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.second.dao.SecondMapper;
import kr.spring.second.vo.SecondFavVO;
import kr.spring.second.vo.SecondOrderVO;
import kr.spring.second.vo.SecondReviewVO;
import kr.spring.second.vo.SecondVO;
import kr.spring.secondchat.vo.ChatRoomVO;

@Service
@Transactional
public class SecondServiceImpl implements SecondService{
	@Autowired
	private SecondMapper secondMapper;
	
	//중고거래 전체 목록/검색 목록
	@Override
	public List<SecondVO> selectList(Map<String, Object> map) {
		return secondMapper.selectList(map);
	}
	//중고거래 글의 총개수/검색 개수
	@Override
	public int selectRowCount(Map<String, Object> map) {
		return secondMapper.selectRowCount(map);
	}

	@Override
	public void insertSecond(SecondVO second) {
		secondMapper.insertSecond(second);
	}
	//상세글 정보 - 한건 
	@Override
	public SecondVO selectSecond(int sc_num) {
		return secondMapper.selectSecond(sc_num);
	}
	//상세페이지 조회수 증가
	@Override
	public void updateHit(int sc_num) {
		secondMapper.updateHit(sc_num);
	}

	@Override
	public void updateSecond(SecondVO second) {
		secondMapper.updateSecond(second);
	}
	//글 삭제
	@Override
	public void deleteSecond(int sc_num) {
		//부모글 찜 삭제
		secondMapper.deleteFavByScNum(sc_num);
		//second_order 거래가 존재하면 먼저 삭제
		secondMapper.deleteOrderByScNum(sc_num);
		//second_review 후기가 존재하면 먼저 삭제 
		secondMapper.deleteReviewByScNum(sc_num);
		//부모글 삭제
		secondMapper.deleteSecond(sc_num);
	}
	//파일 삭제 처리
	@Override
	public void deleteFile(int sc_num) {
		secondMapper.deleteFile(sc_num);
	}
	
	//================판매 상태 변경===============
	//판매중으로 변경
	@Override
	public void updateForSale(int sc_num) {
		secondMapper.updateForSale(sc_num);
	}
	//예약대기로 변경
	@Override
	public void updateWaitReserve(int sc_num) {
		secondMapper.updateWaitReserve(sc_num);
	}
	//예약중으로 변경
	@Override
	public void updateReserve(int sc_num) {
		secondMapper.updateReserve(sc_num);
	}
	//거래완료로 변경
	@Override
	public void updateSellFin(int sc_num) {
		secondMapper.updateSellFin(sc_num);
	}
	
	//=========   거래   ==================
	@Override
	public void insertSecondOrder(SecondOrderVO secondOrderVO) {
		secondMapper.insertSecondOrder(secondOrderVO);
	}
	//거래 상태 변경
	@Override
	public void updateOrderReserve(int sc_num) {
		secondMapper.updateOrderReserve(sc_num);
	}
	//판매완료로 변경
	@Override
	public void updateOrderSellFin(int sc_num) {
		secondMapper.updateOrderSellFin(sc_num);
	}
	
	//구매자 선택시 sc_num 존재 여부 (select)
	@Override
	public SecondOrderVO selectOrderCheck(Map<String, Object> map) {
		return secondMapper.selectOrderCheck(map);
	}
	@Override
	public void insertOrderSellFin(SecondOrderVO secondOrderVO) {
		secondMapper.insertOrderSellFin(secondOrderVO);
		
	}
	//숨김 게시글 sc_show=1 숨김처리
	@Override
	public void updateScHide(int sc_num) {
		secondMapper.updateScHide(sc_num);
	}
	//숨김 게시글 sc_show=2 공개처리
	@Override
	public void updateScShow(int sc_num) {
		secondMapper.updateScShow(sc_num);
	}
	
	
	//=========   찜   ==================
	
	@Override
	public SecondFavVO selectFav(SecondFavVO fav) {
		return secondMapper.selectFav(fav);
	}

	@Override
	public int selectFavCount(int sc_num) {
		return secondMapper.selectFavCount(sc_num);
	}

	@Override
	public void insertFav(SecondFavVO fav) {
		secondMapper.insertFav(fav);
	}

	@Override
	public void deleteFav(SecondFavVO secondFav) {
		secondMapper.deleteFav(secondFav);
	}
	
	
	//=========  중고거래 마이페이지   ==================
	//판매내역 - 전체
	@Override
	public List<SecondVO> selectMyscList(Map<String, Object> map) {
		return secondMapper.selectMyscList(map);
	}
	@Override
	public int selectMyscCount(Map<String, Object> map) {
		return secondMapper.selectMyscCount(map);
	}
	
	//판매내역 - 판매중
	@Override
	public List<SecondVO> selectForSaleList(Map<String, Object> map) {
		return secondMapper.selectForSaleList(map);
	}
	//로그인한 사람의 판매중 글 전체 레코드 수
	@Override
	public int selectForSaleCount(Map<String, Object> map) {
		return secondMapper.selectForSaleCount(map);
	}
	//판매내역 - 예약대기
	@Override
	public List<SecondVO> selectWaitReserveList(Map<String, Object> map) {
		return secondMapper.selectWaitReserveList(map);
	}
	@Override
	public int selectWaitReserveCount(Map<String, Object> map) {
		return secondMapper.selectWaitReserveCount(map);
	}
	//판매내역 - 예약중
	@Override
	public List<SecondVO> selectReserveList(Map<String, Object> map) {
		return secondMapper.selectReserveList(map);
	}
	@Override
	public int selectReserveCount(Map<String, Object> map) {
		return secondMapper.selectReserveCount(map);
	}
	//판매내역 - 거래완료
	@Override
	public List<SecondVO> selectSellFinList(Map<String, Object> map) {
		return secondMapper.selectSellFinList(map);
	}
	@Override
	public int selectSellFinCount(Map<String, Object> map) {
		return secondMapper.selectSellFinCount(map);
	}
	//판매내역 - 숨김
	@Override
	public List<SecondVO> selectHideList(Map<String, Object> map) {
		return secondMapper.selectHideList(map);
	}
	@Override
	public int selectHideCount(Map<String, Object> map) {
		return secondMapper.selectHideCount(map);
	}
	//판매내역 - 끌어올리기
	@Override
	public void updateSysdate(int sc_num) {
		secondMapper.updateSysdate(sc_num);
	}
	
	//구매내역
	@Override
	public List<SecondOrderVO> selectBuyList(Map<String, Object> map) {
		return secondMapper.selectBuyList(map);
	}
	@Override
	public int selectBuyCount(Map<String,Object> map) {
		return secondMapper.selectBuyCount(map);
	}
	
	//찜 상품
	@Override
	public List<SecondVO> selectScFavList(Map<String, Object> map) {
		return secondMapper.selectScFavList(map);
	}
	@Override
	public int selectScFavCount(int mem_num) {
		return secondMapper.selectScFavCount(mem_num);
	}
	//=============== 후기 =================
	@Override
	public void insertScReview(SecondReviewVO review) {
		secondMapper.insertScReview(review);
	}
	@Override
	public List<SecondReviewVO> selectReviewList(Map<String, Object> map) {
		return secondMapper.selectReviewList(map);
	}
	@Override
	public int selectReviewCount(int mem_num) {
		return secondMapper.selectReviewCount(mem_num);
	}
	@Override
	public void deleteOrderByScNum(int sc_num) {
		secondMapper.deleteOrderByScNum(sc_num);
	}
	//=============== 내 상점- 채팅 내역 ================
	@Override
	public List<ChatRoomVO> selectBuyChatList(Map<String, Object> map) {
		return secondMapper.selectBuyChatList(map);
	}
	@Override
	public int selectBuyChatCount(Map<String, Object> map) {
		return secondMapper.selectBuyChatCount(map);
	}
	//글 상세 후기 목록
	@Override
	public int selectSellRevCount(int sc_num) {
		return secondMapper.selectSellRevCount(sc_num);
	}
	@Override
	public List<SecondVO> selectSellRevList(Map<String, Object> map) {
		return secondMapper.selectSellRevList(map);
	}
}
