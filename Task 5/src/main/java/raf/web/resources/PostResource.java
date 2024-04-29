package raf.web.resources;


import raf.web.domain.Post;
import raf.web.logger.LogHelper;
import raf.web.service.PostsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/posts")
public class PostResource {

    @Inject
    private PostsService postsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> allPosts(){
        LogHelper.getInstance().logInfo(PostResource.class, "Getting all posts");
        return this.postsService.allPosts();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void insert(@Valid Post post) {
        this.postsService.insert(post);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post find(@PathParam("id") Integer id) {
        LogHelper.getInstance().logInfo(PostResource.class, "Finding post with ID: " + id);
        return this.postsService.find(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void update(@PathParam("id") Integer id, @Valid Post post) {
        post.setId(id);
        this.postsService.update(post);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Integer id) {
        this.postsService.delete(id);
    }

}
