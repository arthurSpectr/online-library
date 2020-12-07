package com.techqar.weblibrary.jsfui.controller;

import com.techqar.weblibrary.dao.BookDao;
import com.techqar.weblibrary.dao.GenreDao;
import com.techqar.weblibrary.domain.Book;
import com.techqar.weblibrary.domain.Genre;
import com.techqar.weblibrary.jsfui.enums.SearchType;
import com.techqar.weblibrary.jsfui.model.LazyDataTable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
@Getter
@Setter
@Log
public class BookController extends AbstractController<Book> {

    public static final int DEFAULT_PAGE_SIZE = 20;// по-умолчанию сколько книг отображать на странице

    // из JSF таблицы обязательно должна быть ссылки на переменные, иначе при использовании постраничности dataGrid работает некорректно (не отрабатывает bean)
    // также - выбранное пользователем значение (кол-во записей на странице) будет сохраняться
    private int rowsCount = DEFAULT_PAGE_SIZE;
    public static final int TOP_BOOKS_LIMIT = 5;

    private SearchType searchType; // запоминает последний выбранный вариант поиска

    @Autowired
    private BookDao bookDao;// будет автоматически подставлен BookService, т.к. Spring контейнер по-умолчанию ищет бин-реализацию по типу

    private LazyDataTable<Book> lazyModel; // класс-утилита, которая помогает выводить данные постранично (работает в паре с компонентами на странице JSF)

    private Page<Book> bookPages; //хранит список найденных книг
    private List<Book> topBooks;


    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable<>(this);
    }


    // метод автоматически вызывается из LazyDataTable
    @Override
    public Page<Book> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {


        if (sortField == null) {
            sortField = "name";
        }

        if (searchType == null) {
            bookPages = bookDao.getAll(pageNumber, pageSize, sortField, sortDirection);
        } else {

            switch (searchType) {
                case SEARCH_GENRE:

                    break;
                case SEARCH_TEXT:
                    break;
                case ALL:
                    bookPages = bookDao.getAll(pageNumber, pageSize, sortField, sortDirection);
                    break;

            }
        }


        return bookPages;
    }

    public List<Book> getTopBooks() {
        topBooks = bookDao.findTopBooks(TOP_BOOKS_LIMIT);
        return topBooks;
    }

}
