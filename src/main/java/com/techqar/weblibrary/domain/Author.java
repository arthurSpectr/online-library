package com.techqar.weblibrary.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "author", catalog = "weblibrary")
@EqualsAndHashCode(of = "id")
@Getter @Setter
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
public class Author {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String fio;

    private Date birthday;

    @Basic(fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "author")
    private List<Book> books;

    @Override
    public String toString() {
        return fio;
    }
}
