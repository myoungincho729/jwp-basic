package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    public void update(String sql, PreparedStatementSetter pstmtSetter) throws SQLException {
        // TODO 구현 필요함.
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmtSetter.setValues(pstmt);

            pstmt.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public Object queryForObject(String sql, PreparedStatementSetter pss, RowMapper rm) throws SQLException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pss.setValues(pstmt);

            try (ResultSet rs = pstmt.executeQuery()) {
                Object obj = null;
                if (rs.next()) {
                    obj = rm.mapRow(rs);
                }
                return obj;
            } catch (SQLException e) {

            }
        } catch (SQLException e) {

        }
        return null;
    }
    public List<Object> query(String sql, PreparedStatementSetter pss, RowMapper rm) throws SQLException {

        // TODO 구현 필요함.
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            if (pss != null){
                pss.setValues(pstmt);
            }
            rs = pstmt.executeQuery();

            List<Object> objs = new ArrayList<>();
            while (rs.next()) {
                Object obj = rm.mapRow(rs);
                objs.add(obj);
            }

            return objs;
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
