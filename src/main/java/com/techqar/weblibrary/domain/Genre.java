package com.techqar.weblibrary.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
@EqualsAndHashCode(of = "id")
@Table(catalog = "weblibrary")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_generator")
    @SequenceGenerator(name="genre_generator", sequenceName = "genre_id_seq", initialValue = 0, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String name;

//    @Basic(fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    private List<Book> books;

    @Override
    public String toString() {
        return name;
    }
}
