package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
	
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
    public void insert(User user) throws SQLException {
        try {
            con = ConnectionManager.getConnection();
            String sql = createQueryForInsert();
            setValuesForInsert(user, pstmt, sql);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }
    
    private String createQueryForInsert() {
    	return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
    }
    
    private void setValuesForInsert(User user, PreparedStatement pstmt, String sql) {
    	try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
	        pstmt.setString(3, user.getName());
	        pstmt.setString(4, user.getEmail());
	        pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    //------------------------------------------------------------------------------------
    
    public void update(User user) throws SQLException {
        try {
        	con = ConnectionManager.getConnection();
            String sql = createQueryForUpdate();
            setValuesForUpdate(user, pstmt, sql);
        } finally {
        	if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }
    
    private String createQueryForUpdate() {
    	return "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userid=?";
    }

    private void setValuesForUpdate(User user, PreparedStatement pstmt, String sql) {
    	try {
			pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
            pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public List<User> findAll() throws SQLException {
    	try {
    		con = ConnectionManager.getConnection();
    		String sql = "SELECT userId, password, name, email FROM USERS";
    		pstmt = con.prepareStatement(sql);
    		
    		rs = pstmt.executeQuery();
    		
    		User user = null;
    		List<User> list = new ArrayList<User>();
            while (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
                list.add(user);
            }
            
            return list;
		} finally {
			if (rs != null) {
                rs.close();
            }
			if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
		}
    }

    public User findByUserId(String userId) throws SQLException {
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
