package kr.spring.main.service;

import java.util.List;

import kr.spring.main.vo.LastestContentVO;
import kr.spring.performance.vo.PerformanceVO;

public interface MainService {
	
	//최신 글 갯수
	public int selectLastestContentCount();
	
	//최신 글 읽기
	public List<LastestContentVO> selectLastestContent();
	
	//영화 최신 글
	public PerformanceVO selectLastestPerformance();
	
	//중고 최신글
	public List<LastestContentVO> selectLastestSecond();
	
	//재능기부 최신글
	public List<LastestContentVO> selectLastestHelper();
	
	//영화 최신
	public List<LastestContentVO> selectLastestMovie();
	
	//소모임 최신
	public List<LastestContentVO> selectLastestBook();
	
	//커뮤니티 최신
	public List<LastestContentVO> selectLastestCommunity();
	
	//집 체크리스트 최신
	public List<LastestContentVO> selectLastestHouse();
	
	
	//모임 참여 많이 한 회원
	public LastestContentVO selectMostFollowMember();
	
	//팔로워 많은 회원
	public LastestContentVO selectMostFollowerMember();
	
	//커뮤니티 작성글 많은 회원
	public LastestContentVO selectMostContentMember();
	
	//헬프유 많은 회원
	public LastestContentVO selectMostHelpMember();
	
	//중고거래 많은 회원
	public LastestContentVO selectMostSecondMember();
	
	//영화예매 많은 회원
	public LastestContentVO selectMostMovieMember();
	
}
