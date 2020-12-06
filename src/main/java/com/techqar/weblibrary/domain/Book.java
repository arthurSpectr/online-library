package com.techqar.weblibrary.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Table(catalog = "weblibrary")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
public class Book {
    public Book() {
    }

    public Book(Long id, String name, Integer pageCount, String isbn, Genre genre, Author author, Publisher publisher, Integer publishYear, byte[] image, String descr, Long viewCount, Long totalRating, Long totalVoteCount, Integer avgRating) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.image = image;
        this.descr = descr;
        this.viewCount = viewCount;
        this.totalRating = totalRating;
        this.totalVoteCount=totalVoteCount;
        this.avgRating = avgRating;
    }

    public Book(Long id, byte[] image) {
        this.id = id;
        this.image = image;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name="book_generator", sequenceName = "book_id_seq", initialValue = 0, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String name;

    @Lob
    @Column(updatable = false)
    private byte[] content;

    @Column(name = "page_count")
    private Integer pageCount;

    private String isbn;

    @ManyToOne
    @JoinColumn
    private Genre genre;

    @ManyToOne
    @JoinColumn
    private Author author;

    @ManyToOne
    @JoinColumn
    private Publisher publisher;

    @Column(name = "publish_year")
    private Integer publishYear;

    private byte[] image;

    private String descr;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "total_rating")
    private Long totalRating;

    @Column(name = "total_vote_count")
    private Long totalVoteCount;

    @Column(name = "avg_rating")
    private Integer avgRating;

    @Override
    public String toString() {
        return name;
    }
}
