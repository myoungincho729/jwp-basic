package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Question;

import java.util.List;

public class QuestionDao {

    public List<Question> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS " +
                "ORDER BY questionId DESC";
        List<Question> questions = jdbcTemplate.query(
                sql,
                rs -> new Question(
                        rs.getLong("questionId"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        null,
                        rs.getTimestamp("createdDate"),
                        rs.getLong("countOfAnswer"))
        );
        return questions;
    }

    public Question findById(Long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS " +
                "WHERE questionId = ?";
        Question question = jdbcTemplate.queryForObject(
                sql,
                rs -> new Question(rs.getLong("questionId"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getLong("countOfAnswer")),
                questionId
        );
        return question;
    }
}
