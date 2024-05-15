package raf.web.service;

import raf.web.domain.Comment;
import raf.web.repository.abstraction.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;

    public void insert(Comment comment) {
        this.commentRepository.insert(comment);
    }

    public List<Comment> findByPost(int id) {
        return this.commentRepository.findByPost(id);
    }

}
