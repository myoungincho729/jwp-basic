package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcTemplate {
    public void update(PreparedStatementSetter pstmtSetter) throws SQLException {
        // TODO 구현 필요함.
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = createQuery();
            pstmt = con.prepareStatement(sql);
            if (pstmtSetter != null){
                pstmtSetter.setValues(pstmt);
            }

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public Object queryForObject(PreparedStatementSetter pstmtSetter, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = createQuery();
            pstmt = con.prepareStatement(sql);
            if (pstmtSetter != null){
                pstmtSetter.setValues(pstmt);
            }
            rs = pstmt.executeQuery();
            Object obj = null;
            if (rs.next()) {
                obj = rowMapper.mapRow(rs);
            }

            return obj;
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
    public List<Object> query(PreparedStatementSetter pstmtSetter, RowMapper rowMapper) throws SQLException {
        // TODO 구현 필요함.
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = createQuery();
            pstmt = con.prepareStatement(sql);
            if (pstmtSetter != null){
                pstmtSetter.setValues(pstmt);
            }
            rs = pstmt.executeQuery();

            List<Object> objs = new ArrayList<>();
            while (rs.next()) {
                Object obj = rowMapper.mapRow(rs);
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
    public abstract String createQuery();
}
