package next.dao;

import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UpdateJdbcTemplate {
	public void update(User user, UserDao userDao) throws SQLException {
        try {
        	userDao.con = ConnectionManager.getConnection();
            String sql = userDao.createQueryForUpdate();
            userDao.setValuesForUpdate(user, userDao.pstmt, sql);
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
