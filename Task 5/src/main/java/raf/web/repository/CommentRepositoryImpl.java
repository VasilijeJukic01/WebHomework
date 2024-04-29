package raf.web.repository;

import raf.web.domain.Comment;
import raf.web.logger.LogHelper;
import raf.web.repository.abstraction.CommentRepository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class CommentRepositoryImpl implements CommentRepository {

    private static final List<Comment> comments = new CopyOnWriteArrayList<>();

    @Override
    public List<Comment> findByPost(int id) {
        LogHelper.getInstance().logInfo(CommentRepositoryImpl.class, "Finding comments for post with ID: " + id);
        return comments.stream()
                .filter(comment -> comment.getPostId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public void insert(Comment comment) {
        LogHelper.getInstance().logInfo(CommentRepositoryImpl.class, "Inserting new comment");
        comments.add(comment);
    }

}
