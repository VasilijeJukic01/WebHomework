package raf.web.repository.abstraction;

import raf.web.domain.Post;

import java.util.List;

public interface PostsRepository {

    List<Post> all();

    Post find(int id);

    void insert(Post t);

    void update(Post t);

    void delete(int id);

}
