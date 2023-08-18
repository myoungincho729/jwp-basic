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
    public void insert(User user) {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.update(
                sql,
                pstmt -> {
                    pstmt.setString(1, user.getUserId());
                    pstmt.setString(2, user.getPassword());
                    pstmt.setString(3, user.getName());
                    pstmt.setString(4, user.getEmail());
                });
    }

    public void update(User user) {
        // TODO 구현 필요함.
        String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userId=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.update(
                sql,
                pstmt -> {
                    pstmt.setString(1, user.getPassword());
                    pstmt.setString(2, user.getName());
                    pstmt.setString(3, user.getEmail());
                    pstmt.setString(4, user.getUserId());
                });
    }

    public List<User> findAll() {
        String sql = "SELECT userId, password, name, email FROM USERS";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        List<Object> query = jdbcTemplate.query(
                sql,
                rs -> new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                ),
            null
        );
        return query.stream()
                .map(obj -> (User) obj)
                .collect(Collectors.toList());
    }

    public User findByUserId(String userId) {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        return (User) jdbcTemplate.queryForObject(
                sql,
                rs -> new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                ),
                pstmt -> pstmt.setString(1, userId)
        );
    }
}
