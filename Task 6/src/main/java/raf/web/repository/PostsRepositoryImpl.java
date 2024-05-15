package raf.web.repository;

import raf.web.domain.Post;
import raf.web.logger.LogHelper;
import raf.web.repository.abstraction.PostsRepository;

import java.util.List;

public class PostsRepositoryImpl extends SQLRepository implements PostsRepository {

    public PostsRepositoryImpl() {}

    @Override
    public List<Post> all() {
        LogHelper.getInstance().logInfo(getClass(), "Getting all posts");
        String query = "SELECT * FROM Posts";

        return executeQuery(query, resultSet -> new Post(
                resultSet.getInt("id"),
                resultSet.getString("author"),
                resultSet.getString("title"),
                resultSet.getString("body"),
                resultSet.getDate("post_date")
        ));
    }

    @Override
    public Post find(int id) {
        LogHelper.getInstance().logInfo(getClass(), "Finding post with ID: " + id);
        String query = "SELECT * FROM Posts WHERE id=?";
        List<Post> posts = executeQuery(query, resultSet -> new Post(
                resultSet.getInt("id"),
                resultSet.getString("author"),
                resultSet.getString("title"),
                resultSet.getString("body"),
                resultSet.getDate("post_date")
        ), id);

        return posts.isEmpty() ? null : posts.get(0);
    }

    @Override
    public void insert(Post p) {
        LogHelper.getInstance().logInfo(getClass(), "Inserting new post");
        String query = "INSERT INTO Posts (author, title, body, post_date) VALUES (?, ?, ?, CURRENT_DATE)";
        executeUpdate(query, p.getAuthor(), p.getTitle(), p.getBody());
    }

    @Override
    public void update(Post p) {
        LogHelper.getInstance().logInfo(getClass(), "Updating post with ID: " + p.getId());
        String query = "UPDATE Posts SET author=?, title=?, body=? WHERE id=?";
        executeUpdate(query, p.getAuthor(), p.getTitle(), p.getBody(), p.getId());
    }

    @Override
    public void delete(int id) {
        LogHelper.getInstance().logInfo(getClass(), "Deleting post with ID: " + id);
        String query = "DELETE FROM Posts WHERE id=?";
        executeUpdate(query, id);
    }
}
