package raf.web.service;

import raf.web.domain.Post;
import raf.web.repository.abstraction.PostsRepository;

import javax.inject.Inject;
import java.util.List;

public class PostsService {

    @Inject
    private PostsRepository postRepository;

    public void insert(Post post){
        this.postRepository.insert(post);
    }

    public List<Post> allPosts(){
        return this.postRepository.all();
    }

    public Post find(Integer id){
        return this.postRepository.find(id);
    }

    public void update(Post post){
        this.postRepository.update(post);
    }

    public void delete(Integer id){
        this.postRepository.delete(id);
    }

}
