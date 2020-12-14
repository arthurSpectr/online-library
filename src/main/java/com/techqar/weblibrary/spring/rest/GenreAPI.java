package com.techqar.weblibrary.spring.rest;

import com.techqar.weblibrary.dao.impl.GenreService;
import com.techqar.weblibrary.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/genre")
public class GenreAPI {

    @Autowired
    private GenreService genreService;

    @RequestMapping("/get/{id}")
    public Genre get(@PathVariable("id") long id) {
        return genreService.get(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Genre genre) {
        genreService.save(genre);
        return true;
    }

    @RequestMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        genreService.delete(id);
        return true;
    }

    @RequestMapping("/all")
    public List<Genre> getGenres() {
        return genreService.getAll();
    }

    @RequestMapping("/allPage")
    public List<Genre> allPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
        return genreService.getAll(pageNumber, pageSize, "fio", Sort.Direction.ASC).getContent();
    }

    @RequestMapping("/search")
    public List<Genre> search(@RequestParam("name") String name) {
        return genreService.search(name);
    }

    @RequestMapping("/searchPage")
    public List<Genre> searchPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("name") String name) {
        return genreService.search(pageNumber, pageSize, "fio", Sort.Direction.ASC, name).getContent();
    }

}
