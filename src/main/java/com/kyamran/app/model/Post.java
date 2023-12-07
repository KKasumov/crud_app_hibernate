package com.kyamran.app.model;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "updated")
    private LocalDate updated;

    @Column(name = "postStatus")
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_labels",
            joinColumns = {@JoinColumn(name = "PostID")},
            inverseJoinColumns = {@JoinColumn(name = "LabelID")}
    )
    private List<Label> labels;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WriterID", nullable = false)
    private Writer writer;

    public Post(List<Label> labels) {
        this.labels = labels;
    }

    public Post(String content, List<Label> labels, Writer writer) {
        this.content = content;
        this.labels = labels;
        this.writer = writer;
    }

    public Post() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(String created) {
        this.created = LocalDate.parse(created);
    }

    public void setUpdated(String updated) {
        this.updated = LocalDate.parse(updated);
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", postStatus=" + postStatus +
                ", labels=" + labels +
                ", writer=" + writer.getId() +
                '}';
    }
}
