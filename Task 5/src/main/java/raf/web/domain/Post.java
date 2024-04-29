package raf.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private int id;
    private String author;
    private String title;
    private String body;
    private Date date;
    private final List<Comment> comments = new CopyOnWriteArrayList<>();

}
