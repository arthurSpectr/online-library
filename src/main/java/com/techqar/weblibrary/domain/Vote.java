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
@Table(catalog = "spring_learning")
public class Vote {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String value;

    @Column(name = "book_id")
    private Date bookId;

    private String username;

}
