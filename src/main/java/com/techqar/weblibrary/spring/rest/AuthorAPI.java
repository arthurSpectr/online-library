package com.techqar.weblibrary.spring.rest;

import com.techqar.weblibrary.dao.impl.AuthorService;
import com.techqar.weblibrary.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/author")
public class AuthorAPI {

    @Autowired
    private AuthorService authorService;

    @RequestMapping("/get/{id}")
    public Author get(@PathVariable("id") String id) {
        return authorService.get(Long.parseLong(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Author author) {
        authorService.save(author);
        return true;
    }

    @RequestMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        authorService.delete(id);
        return true;
    }

    @RequestMapping("/all")
    public List<Author> getAuthors() {
        return authorService.getAll();
    }

    @RequestMapping("/allPage")
    public List<Author> allPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
        return authorService.getAll(pageNumber, pageSize, "fio", Sort.Direction.ASC).getContent();
    }

    @RequestMapping("/search")
    public List<Author> search(@RequestParam("fio") String fio) {
        return authorService.search(fio);
    }

    @RequestMapping("/searchPage")
    public List<Author> searchPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("fio") String fio) {
        return authorService.search(pageNumber, pageSize, "fio", Sort.Direction.ASC, fio).getContent();
    }

}
