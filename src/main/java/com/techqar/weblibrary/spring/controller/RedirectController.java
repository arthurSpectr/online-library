package com.techqar.weblibrary.spring.controller;

import com.techqar.weblibrary.domain.Book;
import com.techqar.weblibrary.spring.repo.AuthorRepo;
import com.techqar.weblibrary.spring.repo.BookRepo;
import com.techqar.weblibrary.spring.repo.GenreRepo;
import com.techqar.weblibrary.spring.repo.PublisherRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Controller
@Log
public class RedirectController {

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private GenreRepo genreRepo;

    @Autowired
    private PublisherRepo publisherRepo;

    @Transactional
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Page<Book> books = bookRepo.findByGenre(1L, PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")));


        return "ok";
    }
}
