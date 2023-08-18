package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    public void update(String sql, Object... parameters) {
        update(sql, createPSS(parameters));
    }
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
    public <T> T queryForObject(String sql, RowMapper<T> rm, Object... parameters) throws DataAccessException {
        return queryForObject(sql, rm, createPSS(parameters));
    }

    public <T> T queryForObject(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws DataAccessException {
        List<T> query = query(sql, rm, pss);
        if (query.isEmpty()) {
            return null;
        }
        return query.get(0);
    }

    public <T> List<T> query(String sql, RowMapper<T> rm, Object... parameters) throws DataAccessException {
        return query(sql, rm, createPSS(parameters));
    }

    public <T> List<T> query(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws DataAccessException {
        // TODO 구현 필요함.
        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            if (pss != null){
                pss.setValues(pstmt);
            }
            rs = pstmt.executeQuery();

            List<T> objs = new ArrayList<>();
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

    private PreparedStatementSetter createPSS(Object... parameters) {
        return pstmt -> {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
        };
    }
}
