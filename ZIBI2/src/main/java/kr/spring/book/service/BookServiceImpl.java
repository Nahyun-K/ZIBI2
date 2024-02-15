package kr.spring.book.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.book.dao.BookMapper;
import kr.spring.book.vo.BookMatchingVO;
import kr.spring.book.vo.BookReplyVO;
import kr.spring.book.vo.BookReviewVO;
import kr.spring.book.vo.BookScrapVO;
import kr.spring.book.vo.BookVO;

@Service
@Transactional
public class BookServiceImpl implements BookService{
	@Autowired
	private BookMapper bookMapper;

	@Override
	public List<BookVO> selectList(Map<String, Object> map) {
		return bookMapper.selectList(map);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return bookMapper.selectRowCount(map);
	}

	@Override
	public void insertBook(BookVO book) {
		bookMapper.insertBook(book);
	}

	@Override
	public BookVO selectBook(int book_num) {
		return bookMapper.selectBook(book_num);
	}

	@Override
	public void updateBook(BookVO book) {
		bookMapper.updateBook(book);
	}

	@Override
	public void deleteFile(int book_num) {
		bookMapper.deleteFile(book_num);
	}

	@Override
	public List<BookVO> selectMatchList(Map<String, Object> map) {
		return bookMapper.selectMatchList(map);
	}

	@Override
	public int selectMatchCount(Map<String, Object> map) {
		return bookMapper.selectMatchCount(map);
	}

	@Override
	public void insertMatch(BookMatchingVO bookMatchingVO) {
		//신청 인원수 반영
		bookMapper.addMatch(bookMatchingVO.getBook_num());
		bookMapper.insertMatch(bookMatchingVO);
	}

	@Override
	public void deleteMatch(int book_num, int apply_num) {
		//취소 인원수 반영
		bookMapper.cancelMatch(book_num);
		bookMapper.deleteMatch(book_num, apply_num);
	}

	@Override
	public void cancelBook(int book_num) {
		bookMapper.cancelBook(book_num);
	}

	@Override
	public void approveMatch(int book_num, int apply_num) {
		bookMapper.approveMatch(book_num, apply_num);
	}

	@Override
	public void denyMatch(int book_num, int apply_num) {
		//거절 인원수 반영
		bookMapper.cancelMatch(book_num);
		bookMapper.denyMatch(book_num, apply_num);
	}
	
	@Override
	public void denyAllMatch(int book_num) {
		//게시글 모집 마감 처리
		bookMapper.updateOnoff3(book_num);
		//거절 인원수 게시글에 반영
		bookMapper.denyHeadcount(book_num);
		//대기 중인 나머지 참여 신청 일괄 거절
		bookMapper.denyAllMatch(book_num);
	}

	@Override
	public BookMatchingVO selectMatch(String book_gatheringDate, int apply_num) {
		return bookMapper.selectMatch(book_gatheringDate, apply_num);
	}

	@Override
	public void updateOnoff1(int book_num) {
		//대기 중인 나머지 참여 신청 일괄 거절
		bookMapper.denyAllMatch(book_num);
		//모임 완료 처리
		bookMapper.updateOnoff1(book_num);
	}

	@Override
	public void resetOnoff(int book_num) {
		//참여 인원수 초기화
		bookMapper.resetHeadcount(book_num);
		//새로 모집 처리
		bookMapper.resetOnoff(book_num);
	}

	@Override
	public BookScrapVO selectScrap(BookScrapVO scrap) {
		return bookMapper.selectScrap(scrap);
	}

	@Override
	public int selectScrapCount(int book_num) {
		return bookMapper.selectScrapCount(book_num);
	}

	@Override
	public void insertScrap(BookScrapVO scrap) {
		bookMapper.insertScrap(scrap);
	}

	@Override
	public void deleteScrap(BookScrapVO scrapVO) {
		bookMapper.deleteScrap(scrapVO);
	}

	@Override
	public List<BookReviewVO> selectListRev(int book_num) {
		return bookMapper.selectListRev(book_num);
	}

	@Override
	public int selectRevCount(int book_num) {
		return bookMapper.selectRevCount(book_num);
	}

	@Override
	public void insertRev(BookReviewVO rev) {
		bookMapper.insertRev(rev);
	}

	@Override
	public BookMatchingVO selectMatchForRev(int book_num, int apply_num, String apply_gatheringDate) {
		return bookMapper.selectMatchForRev(book_num, apply_num, apply_gatheringDate);
	}

	@Override
	public int selectRevByrev_num(int book_num, int apply_num, String apply_gatheringDate) {
		return bookMapper.selectRevByrev_num(book_num, apply_num, apply_gatheringDate);
	}

	@Override
	public List<BookReplyVO> selectListReply(Map<String, Object> map) {
		return bookMapper.selectListReply(map);
	}

	@Override
	public int selectRepCount(Map<String, Object> map) {
		return bookMapper.selectRepCount(map);
	}

	@Override
	public BookReplyVO selectReply(int rep_num) {
		return bookMapper.selectReply(rep_num);
	}

	@Override
	public void insertReply(BookReplyVO bookReply) {
		bookMapper.insertReply(bookReply);
	}

	@Override
	public void insertReplies(BookReplyVO bookReply) {
		bookMapper.insertReplies(bookReply);
	}

	@Override
	public void deleteReply(int rep_num) {
		bookMapper.deleteReply(rep_num);
	}

}
