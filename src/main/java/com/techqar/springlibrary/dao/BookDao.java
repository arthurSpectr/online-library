package com.techqar.springlibrary.dao;

import com.techqar.springlibrary.domain.Book;

import java.util.List;

public interface BookDao extends Crud<Book> {
    List<Book> findTopBooks(int limit);
}
