package org.slam.publicshare.book.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slam.publicshare.common.entity.Auditable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Auditable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String owner;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BookImage> images = new ArrayList<>();

    @Builder
    public Book(String title, String author, String publisher, String description, String owner) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.owner = owner;
    }

    public void addImages(List<BookImage> images) {
        this.images.addAll(images);
    }

    public void addImage(BookImage image) {
        this.images.add(image);
    }

}