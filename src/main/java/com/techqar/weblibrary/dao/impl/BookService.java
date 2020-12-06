package com.techqar.weblibrary.dao.impl;


import com.techqar.weblibrary.dao.BookDao;
import com.techqar.weblibrary.domain.Book;
import com.techqar.weblibrary.spring.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService implements BookDao {

    @Autowired
    private BookRepo bookRepo;

    @Override
    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    @Override
    public List<Book> getAll(Sort sort) {
        return bookRepo.findAll(sort);
    }

    @Override
    public Page<Book> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return bookRepo.findAllWithoutContent(PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }

    @Override
    public List<Book> search(String... searchString) {
        return null;
    }


    @Override
    public Page<Book> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString) {
        // now does not working because of containing 
//        return bookRepo.findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(searchString[0], searchString[0], PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
        return null;
    }


    @Override
    public Book save(Book book) {

        // отдельно сохраняем данные книги
        bookRepo.save(book);

        if (book.getContent() != null) {
            // отдельно сохраняем контент
            bookRepo.updateContent(book.getContent(), book.getId());
        }

        return book;

    }

    @Override
    public void delete(Book book) {
        bookRepo.delete(book);
    }

    @Override
    public Book get(long id) {
        Optional<Book> bookmark = bookRepo.findById(id); // Optional - обертка, в котором может быть значение или пусто (используется для исключение ошибки NullPointerException
        if (bookmark.isPresent()) { // если значение представлено - вернуть его
            return bookmark.get();
        } else {
            return null;
        }
    }


    @Override
    public byte[] getContent(long id) {
        return bookRepo.getContent(id);
    }

    public List<Book> findTopBooks(int limit) {
        return bookRepo.findTopBooks(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "viewCount")));
    }

    @Override
    public Page<Book> findByGenre(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, long genreId) {
        return bookRepo.findByGenre(genreId, PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }

    @Override
    public void updateViewCount(long viewCount, long id) {
        bookRepo.updateViewCount(viewCount, id);
    }

    @Override
    public void updateRating(long totalRating, long totalVoteCount, int avgRating, long id) {
        bookRepo.updateRating(totalRating, totalVoteCount, avgRating, id);
    }


}
