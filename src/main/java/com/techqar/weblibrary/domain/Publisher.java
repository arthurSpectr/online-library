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
@Table(catalog = "spring_learning")
public class Publisher {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String name;

    @Basic(fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

    @Override
    public String toString() {
        return name;
    }

}
