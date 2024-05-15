package raf.web.repository;

import raf.web.domain.Comment;
import raf.web.logger.LogHelper;
import raf.web.repository.abstraction.CommentRepository;

import java.util.List;

public class CommentRepositoryImpl extends SQLRepository implements CommentRepository {

    @Override
    public List<Comment> findByPost(int id) {
        LogHelper.getInstance().logInfo(CommentRepositoryImpl.class, "Finding comments for post with ID: " + id);
        String query = "SELECT * FROM Comments WHERE post_id = ?";

        return executeQuery(query, resultSet -> new Comment(
                resultSet.getInt("id"),
                resultSet.getString("author"),
                resultSet.getString("content"),
                resultSet.getInt("post_id")
        ), id);
    }

    @Override
    public void insert(Comment comment) {
        LogHelper.getInstance().logInfo(CommentRepositoryImpl.class, "Inserting new comment");
        String query = "INSERT INTO comments (author, content, post_id) VALUES (?,?,?)";

        executeUpdate(query, comment.getAuthor(), comment.getContent(), comment.getPostId());
    }

}
