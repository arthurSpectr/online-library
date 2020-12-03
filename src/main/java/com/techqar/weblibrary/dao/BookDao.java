package com.techqar.weblibrary.dao;

import com.techqar.weblibrary.domain.Book;

import java.util.List;

public interface BookDao extends GeneralDao<Book> {
    List<Book> findTopBooks(int limit);
}
