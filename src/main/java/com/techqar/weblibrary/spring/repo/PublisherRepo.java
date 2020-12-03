package com.techqar.weblibrary.spring.repo;

import com.techqar.weblibrary.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepo extends JpaRepository<Publisher, Long> {
}
