package com.techqar.weblibrary.spring.controller;

import com.techqar.weblibrary.domain.Author;
import com.techqar.weblibrary.spring.repo.AuthorRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Log
public class RedirectController {

    @Autowired
    private AuthorRepo authorRepo;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse response) {
        List<Author> authors = authorRepo.findAll();
        return "ok";
    }
}
