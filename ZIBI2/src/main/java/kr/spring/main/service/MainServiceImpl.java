package kr.spring.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.main.dao.MainMapper;
import kr.spring.main.vo.LastestContentVO;
import kr.spring.performance.vo.PerformanceVO;

@Service
public class MainServiceImpl implements MainService {
	
	@Autowired
	MainMapper mainMapper;
	
	@Override
	public List<LastestContentVO> selectLastestContent() {
		return mainMapper.selectLastestContent();
	}

	@Override
	public int selectLastestContentCount() {
		return mainMapper.selectLastestContentCount();
	}

	@Override
	public PerformanceVO selectLastestPerformance() {
		return mainMapper.selectLastestPerformance();
	}

	@Override
	public List<LastestContentVO> selectLastestSecond() {
		return mainMapper.selectLastestSecond();
	}

	@Override
	public List<LastestContentVO> selectLastestHelper() {
		return mainMapper.selectLastestHelper();
	}

	@Override
	public List<LastestContentVO> selectLastestMovie() {
		return mainMapper.selectLastestMovie();
	}

	@Override
	public List<LastestContentVO> selectLastestBook() {
		return mainMapper.selectLastestBook();
	}

	@Override
	public List<LastestContentVO> selectLastestCommunity() {
		return mainMapper.selectLastestCommunity();
	}

	@Override
	public List<LastestContentVO> selectLastestHouse() {
		return mainMapper.selectLastestHouse();
	}

	@Override
	public LastestContentVO selectMostFollowMember() {
		return mainMapper.selectMostFollowMember();
	}

	@Override
	public LastestContentVO selectMostFollowerMember() {
		return mainMapper.selectMostFollowerMember();
	}

	@Override
	public LastestContentVO selectMostContentMember() {
		return mainMapper.selectMostContentMember();
	}

	@Override
	public LastestContentVO selectMostHelpMember() {
		return mainMapper.selectMostHelpMember();
	}

	@Override
	public LastestContentVO selectMostSecondMember() {
		return mainMapper.selectMostSecondMember();
	}

	@Override
	public LastestContentVO selectMostMovieMember() {
		return mainMapper.selectMostMovieMember();
	}
}
