package raf.web.repository.abstraction;

import raf.web.domain.Comment;

import java.util.List;

public interface CommentRepository {

    List<Comment> findByPost(int id);

    void insert(Comment comment);

}
