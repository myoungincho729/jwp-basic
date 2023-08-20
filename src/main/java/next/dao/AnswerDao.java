package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Answer;

import java.util.List;

public class AnswerDao {
    public List<Answer> findAllByQuestionId(Long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE questionId = ? " +
                "ORDER BY createdDate";
        return jdbcTemplate.query(
                sql,
                rs -> new Answer(
                        rs.getLong("answerId"),
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getLong("questionId")
                ),
                questionId
        );
    }
}
