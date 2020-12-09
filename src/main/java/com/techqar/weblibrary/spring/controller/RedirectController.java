package com.techqar.weblibrary.spring.controller;

import com.techqar.weblibrary.dao.impl.AuthorService;
import com.techqar.weblibrary.dao.impl.BookService;
import com.techqar.weblibrary.dao.impl.GenreService;
import com.techqar.weblibrary.dao.impl.PublisherService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Log
public class RedirectController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private PublisherService publisherService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:" + request.getRequestURL().append("index.xhtml").toString();
    }
}
