package raf.web.resources;

import raf.web.domain.Comment;
import raf.web.service.CommentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void insert(Comment comment) {
        this.commentService.insert(comment);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> find(@PathParam("id") int id) {
        return this.commentService.findByPost(id);
    }

}
