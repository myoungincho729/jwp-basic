package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    public void update(String sql, PreparedStatementSetter pss) throws DataAccessException {
        // TODO 구현 필요함.
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            if (pss != null){
                pss.setValues(pstmt);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Object queryForObject(String sql, RowMapper rm, PreparedStatementSetter pss) throws DataAccessException {
        List<Object> query = query(sql, rm, pss);
        if (query.isEmpty()) {
            return null;
        }
        return query.get(0);
    }
    public List<Object> query(String sql, RowMapper rm, PreparedStatementSetter pss) throws DataAccessException {
        // TODO 구현 필요함.
        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            if (pss != null){
                pss.setValues(pstmt);
            }
            rs = pstmt.executeQuery();

            List<Object> objs = new ArrayList<>();
            while (rs.next()) {
                objs.add(rm.mapRow(rs));
            }
            return objs;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
        }
    }
}
