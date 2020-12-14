package com.techqar.weblibrary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@DynamicUpdate
@DynamicInsert
@Getter @Setter
@SelectBeforeUpdate
@EqualsAndHashCode(of = "id")
@Table(name = "author", catalog = "weblibrary")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    @SequenceGenerator(name="author_generator", sequenceName = "author_id_seq", initialValue = 0, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String fio;

    private Date birthday;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;

    @Override
    public String toString() {
        return fio;
    }
}
