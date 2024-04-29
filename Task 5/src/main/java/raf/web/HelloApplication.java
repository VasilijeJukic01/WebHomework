package raf.web;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import raf.web.service.CommentService;
import raf.web.service.PostsService;
import raf.web.repository.CommentRepositoryImpl;
import raf.web.repository.abstraction.CommentRepository;
import raf.web.repository.abstraction.PostsRepository;
import raf.web.repository.PostsRepositoryImpl;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication(){
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(PostsRepositoryImpl.class).to(PostsRepository.class).in(Singleton.class);
                this.bind(CommentRepositoryImpl.class).to(CommentRepository.class).in(Singleton.class);

                this.bindAsContract(PostsService.class);
                this.bindAsContract(CommentService.class);
            }
        };

        register(binder);

        packages("raf.web.resources");
    }

}