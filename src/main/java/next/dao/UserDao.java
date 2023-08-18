package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            public String createQuery() {
                return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
            }

            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };
        jdbcTemplate.update();
    }

    public void update(User user) throws SQLException {
        // TODO 구현 필요함.
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            public String createQuery() {
                return "UPDATE USERS SET password=?, name=?, email=? WHERE userId=?";
            }

            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };
        jdbcTemplate.update();
    }

    public List<User> findAll() throws SQLException {
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            @Override
            public String createQuery() {
                return "SELECT userId, password, name, email FROM USERS";
            }

            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
            }
        };
        List<Object> query = selectJdbcTemplate.query();
        return query.stream()
                .map(obj -> (User) obj)
                .collect(Collectors.toList());
    }

    public User findByUserId(String userId) throws SQLException {
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }

            @Override
            public String createQuery() {
                return  "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            }

            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };
        return (User) selectJdbcTemplate.queryForObject();
    }
}
