package next.dao;

import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import next.model.User;

public class InsertJdbcTemplate {
	void insert(User user, UserDao userDao) throws SQLException {
		try {
            userDao.con = ConnectionManager.getConnection();
            String sql = userDao.createQueryForInsert();
            userDao.setValuesForInsert(user, userDao.pstmt, sql);
        } finally {
            if (userDao.pstmt != null) {
            	userDao.pstmt.close();
            }

            if (userDao.con != null) {
            	userDao.con.close();
            }
        } 
    }
}
