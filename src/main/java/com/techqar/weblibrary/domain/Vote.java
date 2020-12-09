package com.techqar.weblibrary.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
@EqualsAndHashCode(of = "id")
@Table(catalog = "weblibrary")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_generator")
    @SequenceGenerator(name="vote_generator", sequenceName = "vote_id_seq", initialValue = 0, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String value;

    @Column(name = "book_id")
    private Date bookId;

    private String username;

}
