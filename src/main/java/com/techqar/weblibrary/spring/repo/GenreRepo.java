package com.techqar.weblibrary.spring.repo;

import com.techqar.weblibrary.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepo extends JpaRepository <Genre, Long> {
}
