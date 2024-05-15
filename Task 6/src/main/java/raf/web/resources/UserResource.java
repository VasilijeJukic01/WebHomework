package raf.web.resources;

import raf.web.dto.UserDTO;
import raf.web.logger.LogHelper;
import raf.web.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/register")
    @Produces({MediaType.APPLICATION_JSON})
    public Response register(UserDTO userDTO) {
        LogHelper.getInstance().logInfo(UserResource.class, "Registering user: " + userDTO.getUsername());
        return this.userService.createUser(userDTO) != null
                ? Response.ok().build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(UserDTO userDTO) {
        String jwt = this.userService.login(userDTO.getUsername(), userDTO.getPassword());
        return jwt != null
                ? Response.ok(jwt).build()
                : Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
