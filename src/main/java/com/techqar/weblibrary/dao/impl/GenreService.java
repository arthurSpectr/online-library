package com.techqar.weblibrary.dao.impl;

import com.techqar.weblibrary.dao.GenreDao;
import com.techqar.weblibrary.domain.Genre;
import com.techqar.weblibrary.spring.repo.GenreRepo;
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
public class GenreService implements GenreDao {


    @Autowired
    private GenreRepo genreRepo;


    @Override
    public List<Genre> getAll() {
        return genreRepo.findAll();
    }

    public List<Genre> getAll(Sort sort) {
        return genreRepo.findAll(sort);
    }


    @Override
    public Page<Genre> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return genreRepo.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }


    @Override
    public List<Genre> search(String... searchString) {
        return genreRepo.findByNameContainingIgnoreCaseOrderByName(searchString[0]);
    }

    @Override
    public Page<Genre> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString) {
        return genreRepo.findByNameContainingIgnoreCaseOrderByName(searchString[0], PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }


    @Override
    public Genre save(Genre genre) {
        return genreRepo.save(genre);
    }

    @Override
    public void delete(Genre genre) {
        genreRepo.delete(genre);

    }

    @Override
    public Genre get(long id) {
        Optional<Genre> bookmark = genreRepo.findById(id); // Optional - обертка, в котором может быть значение или пусто (используется для исключение ошибки NullPointerException
        // если значение представлено - вернуть его
        return bookmark.orElse(null);
    }






}
