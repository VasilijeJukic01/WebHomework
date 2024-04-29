package raf.web.repository;

import raf.web.domain.Post;
import raf.web.logger.LogHelper;
import raf.web.repository.abstraction.PostsRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostsRepositoryImpl implements PostsRepository {

    private static final List<Post> posts = new CopyOnWriteArrayList<>();

    public PostsRepositoryImpl() {
        createPostsSample();
    }

    private void createPostsSample() {
        posts.add(new Post(1, "John", "How to create a simple web application", "In this post, we will create a simple web application using Java.", new Date()));
        posts.add(new Post(2, "Jane", "Life with a Cactus", "This morning, I woke up, looked at my cactus, and wondered, " +
                "\"Who's the crazy one here? Me for having it, or it for having to live with me?" +
                "\" That look it gives me seems like it's judging, or maybe it's just my imagination running wild.", new Date()));
    }

    @Override
    public List<Post> all() {
        LogHelper.getInstance().logInfo(getClass(), "Getting all posts");
        return posts;
    }

    @Override
    public Post find(int id) {
        LogHelper.getInstance().logInfo(getClass(), "Finding post with ID: " + id);
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void insert(Post p) {
        LogHelper.getInstance().logInfo(getClass(), "Inserting new post");
        posts.add(p);
    }

    @Override
    public void update(Post p) {
        posts.stream()
                .filter(post -> post.getId() == p.getId())
                .forEach(post -> {
                    post.setTitle(p.getTitle());
                    post.setBody(p.getBody());
                });
    }

    @Override
    public void delete(int id) {
        posts.removeIf(post -> post.getId() == id);
    }
}
