package kr.co.itcen.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.exception.UserDaoException;
import kr.co.itcen.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;

	
	public Boolean insert(UserVo vo) throws UserDaoException {
		int count = sqlSession.insert("user.insert", vo);
		System.out.println(vo);
		return count == 1;
	}
	
	public Boolean update(UserVo vo) {
		int count = sqlSession.update("user.update", vo);
		return count == 1;
	}	
	
	public UserVo get(UserVo vo) {
		UserVo result = sqlSession.selectOne("user.getByEmailAndPassword", vo);
		return result;
	}		
	
	public UserVo get(String email, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		sqlSession.selectOne("user.getByEmailAndPassword2", map);
		return null;
	}	
	
	public UserVo get(Long no) {
		return sqlSession.selectOne("user.getByNo", no);
	}
		
}
