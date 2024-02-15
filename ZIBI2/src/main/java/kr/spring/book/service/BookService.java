package kr.spring.book.service;

import java.util.List;
import java.util.Map;

import kr.spring.book.vo.BookMatchingVO;
import kr.spring.book.vo.BookReplyVO;
import kr.spring.book.vo.BookReviewVO;
import kr.spring.book.vo.BookScrapVO;
import kr.spring.book.vo.BookVO;

public interface BookService {
	/*------- 부모글 -------*/
	public List<BookVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	public void insertBook(BookVO book);
	public BookVO selectBook(int book_num);
	public void updateBook(BookVO book);
	public void cancelBook(int book_num);
	public void deleteFile(int book_num);
	
	/*------- 예약 내역(나의 모임) -------*/
	public List<BookVO> selectMatchList(Map<String,Object> map);
	public int selectMatchCount(Map<String,Object> map);
	
	/*------- 매칭 -------*/
	public void insertMatch(BookMatchingVO bookMatchingVO);
	public BookMatchingVO selectMatch(String book_gatheringDate, int apply_num);
	public void deleteMatch(int book_num, int apply_num);
	public void approveMatch(int book_num, int apply_num);
	public void denyMatch(int book_num, int apply_num);
	public void denyAllMatch(int book_num);
	public void updateOnoff1(int book_num);
	public void resetOnoff(int book_num);
	
	/*------- 후기 -------*/
	public int selectRevByrev_num(int book_num, int apply_num, String apply_gatheringDate);
	public List<BookReviewVO> selectListRev(int book_num);
	public int selectRevCount(int book_num);
	public void insertRev(BookReviewVO rev);
	public BookMatchingVO selectMatchForRev(int book_num, int apply_num, String apply_gatheringDate);
	
	/*------- 스크랩 -------*/
	public BookScrapVO selectScrap(BookScrapVO scrap);
	public int selectScrapCount(int book_num);
	public void insertScrap(BookScrapVO scrap);
	public void deleteScrap(BookScrapVO scrapVO);
	
	/*------- 댓글 -------*/
	public List<BookReplyVO> selectListReply(Map<String,Object> map);
	public int selectRepCount(Map<String,Object> map);
	public BookReplyVO selectReply(int rep_num);
	public void insertReply(BookReplyVO bookReply);
	public void insertReplies(BookReplyVO bookReply);
	public void deleteReply(int rep_num);
}
