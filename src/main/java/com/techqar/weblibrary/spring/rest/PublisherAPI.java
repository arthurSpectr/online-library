package com.techqar.weblibrary.spring.rest;

import com.techqar.weblibrary.dao.impl.PublisherService;
import com.techqar.weblibrary.domain.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/publisher")
public class PublisherAPI {

    @Autowired
    private PublisherService publisherService;

    @RequestMapping("/get/{id}")
    public Publisher get(@PathVariable("id") long id) {
        return publisherService.get(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Publisher publisher) {
        publisherService.save(publisher);
        return true;
    }

    @RequestMapping("/delete/{id}")
    public boolean deletePublisher(@PathVariable("id") long id) {
        publisherService.delete(id);
        return true;
    }

    @RequestMapping("/all")
    public List<Publisher> getPublishers() {
        return publisherService.getAll();
    }

    @RequestMapping("/allPage")
    public List<Publisher> allPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
        return publisherService.getAll(pageNumber, pageSize, "fio", Sort.Direction.ASC).getContent();
    }

    @RequestMapping("/search")
    public List<Publisher> search(@RequestParam("name") String name) {
        return publisherService.search(name);
    }

    @RequestMapping("/searchPage")
    public List<Publisher> searchPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("name") String name) {
        return publisherService.search(pageNumber, pageSize, "fio", Sort.Direction.ASC, name).getContent();
    }

}
