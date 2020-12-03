package com.techqar.weblibrary.spring.repo;

import com.techqar.weblibrary.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {
    List<Author> findByFioContainingIgnoreCaseOrderByFio(String fio);
}
