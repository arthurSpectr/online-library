package com.techqar.weblibrary.dao.impl;

import com.techqar.weblibrary.dao.PublisherDao;
import com.techqar.weblibrary.domain.Publisher;
import com.techqar.weblibrary.spring.repo.PublisherRepo;
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
public class PublisherService implements PublisherDao {

    @Autowired
    private PublisherRepo publisherRepo;


    @Override
    public List<Publisher> getAll() {
        return publisherRepo.findAll();
    }

    public List<Publisher> getAll(Sort sort) {
        return publisherRepo.findAll(sort);
    }



    @Override
    public Page<Publisher> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return publisherRepo.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }


    @Override
    public List<Publisher> search(String... searchString) {
        return publisherRepo.findByNameContainingIgnoreCaseOrderByName(searchString[0]);
    }

    @Override
    public Page<Publisher> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString) {
        return publisherRepo.findByNameContainingIgnoreCaseOrderByName(searchString[0], PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }


    @Override
    public Publisher save(Publisher publisher) {
        return publisherRepo.save(publisher);
    }

    @Override
    public void delete(Publisher publisher){
        publisherRepo.delete(publisher);
    }


    @Override
    public Publisher get(long id) {
        Optional<Publisher> bookmark = publisherRepo.findById(id); // Optional - обертка, в котором может быть значение или пусто (используется для исключение ошибки NullPointerException
        // если значение представлено - вернуть его
        return bookmark.orElse(null);
    }




}
