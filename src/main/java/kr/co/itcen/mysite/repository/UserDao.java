package kr.co.itcen.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.exception.UserDaoException;
import kr.co.itcen.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSource dataSource;
	
	public void insert(UserVo vo) throws UserDaoException {
		sqlSession.insert("user.insert", vo);
	}
	
	
	
	
	
	public void update(UserVo vo) {
		Connection connection = null;		
		PreparedStatement pstmt = null;
		try {
			connection = dataSource.getConnection();

			String sql = "update user set name=?, gender=?, password=? where no=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender());
			pstmt.setString(3, vo.getPassword());
			pstmt.setLong(4, vo.getNo());
						
			pstmt.executeUpdate();		
								
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
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
		UserVo result = null;
		Connection connection = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();

			String sql = "select name, email, gender, no from user where no=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
						
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {				
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);
				Long userNo = rs.getLong(4);
				
				result = new UserVo();				
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);	
				result.setNo(userNo);
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return result;
	}
}
