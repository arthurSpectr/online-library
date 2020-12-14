package com.techqar.weblibrary.spring.rest;

import com.techqar.weblibrary.dao.impl.BookService;
import com.techqar.weblibrary.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/book")
public class BookAPI {

    @Autowired
    private BookService bookService;

    @RequestMapping("/all")
    public List<Book> getBooks(){
        return bookService.getAll();
    }

    @RequestMapping("/allPage")
    public List<Book> allPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize){
        return bookService.getAll(pageNumber, pageSize, "fio", Sort.Direction.ASC).getContent();
    }

    @RequestMapping("/search")
    public List<Book> search(@RequestParam("fio") String fio){
        return bookService.search(fio);
    }

    @RequestMapping("/searchPage")
    public List<Book> searchPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("fio") String fio){
        return bookService.search(pageNumber, pageSize, "fio", Sort.Direction.ASC, fio).getContent(); // т.к. возвращается объект Page, надо у него вызвать getContent, чтобы получить коллекцию
    }

    @RequestMapping("/get")
    public Book get(@RequestParam("id") long id){
        return bookService.get(id);
    }

    @RequestMapping("/delete")
    public boolean delete(@RequestParam("id") long id){
        bookService.delete(id);
        return true;
    }

    @RequestMapping(value = "/addContent", method = RequestMethod.POST)
    public boolean addContent(@RequestBody byte[] content, @RequestParam("bookId") long bookId){
        Book book = bookService.get(bookId); // сначала получаем саму книгу
        book.setContent(content); // обновляем контент
        bookService.save(book); // сохраняем обратно в БД
        return true;
    }


    // поиск списка книг по жанру
    @RequestMapping(value = "/searchByGenre")
    public List<Book> getByGenre(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("genreId") long genreId){
        return bookService.findByGenre(pageNumber, pageSize, "name", Sort.Direction.ASC, genreId).getContent();
    }

    // получение PDF контента по id книги
    @RequestMapping(value = "/getContent")
    public byte[] getContent(@RequestParam("bookId") long bookId){
        return bookService.getContent(bookId);
    }

}
