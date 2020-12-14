package com.techqar.weblibrary.dao.impl;

import com.techqar.weblibrary.dao.AuthorDao;
import com.techqar.weblibrary.domain.Author;
import com.techqar.weblibrary.spring.repo.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// сервисный уровень для работы с авторами
// API для реализованных бизнес процессов
// код должен обращаться только через Service (не к Repository напрямую)
@Service // сервисный Spring бин
@Transactional // методы помечаются как транзакционные (для запросов применяются настройки транзакций по-умолчанию, уровень изоляции и пр.)
public class AuthorService implements AuthorDao {

    @Autowired
    private AuthorRepo authorRepo;

    @Override
    public List<Author> getAll() {
        return authorRepo.findAll();
    }

    public List<Author> getAll(Sort sort) {
        return authorRepo.findAll(sort);
    }



    @Override
    public Page<Author> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return authorRepo.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }

    @Override
    public List<Author> search(String... searchString) {
        return authorRepo.findByFioContainingIgnoreCaseOrderByFio(searchString[0]);
    }

    @Override
    public Page<Author> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString) {
        return authorRepo.findByFioContainingIgnoreCaseOrderByFio(searchString[0], PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }


    @Override
    public Author save(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public void delete(Author author) {
        authorRepo.delete(author);
    }

    public void delete(Long id) {
        authorRepo.deleteById(id);
    }

    @Override
    public Author get(long id) {
        Optional<Author> bookmark = authorRepo.findById(id); // Optional - обертка, в котором может быть значение или пусто (используется для исключение ошибки NullPointerException
        // если значение представлено - вернуть его
        return bookmark.orElse(null);
    }





}