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
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

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
    private BookDao bookDao;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private GenreController genreController;

    private Book selectedBook;
    private LazyDataTable<Book> lazyModel; // класс-утилита, которая помогает выводить данные постранично (работает в паре с компонентами на странице JSF)

    private byte[] uploadedImage;
    private byte[] uploadedContent;

    private Page<Book> bookPages; //хранит список найденных книг
    private List<Book> topBooks;

    private String searchText;

    private long selectedGenreId;

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable<>(this);
    }

    public void save() {

        if(null != uploadedImage) {
            selectedBook.setImage(uploadedImage);
        }

        if(null != uploadedContent) {
            selectedBook.setContent(uploadedContent);
        }

        bookDao.save(selectedBook);
        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').hide()");
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
                    bookPages = bookDao.findByGenre(pageNumber, pageSize, sortField, sortDirection, selectedGenreId);
                    break;
                case SEARCH_TEXT:
                    bookPages = bookDao.search(pageNumber, pageSize, sortField, sortDirection, searchText);
                    break;
                case ALL:
                    bookPages = bookDao.getAll(pageNumber, pageSize, sortField, sortDirection);
                    break;

            }
        }


        return bookPages;
    }


    @Override
    public void addAction() {

    }

    @Override
    public void editAction() {
        uploadedImage = selectedBook.getImage();

        // выбранный book уже будет записан в переменную selectedBook (как только пользователь кликнет на редактирование)
        // книга отобразится в диалоговом окне
        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').show()");
    }

    @Override
    public void deleteAction() {

    }

    public List<Book> getTopBooks() {
        topBooks = bookDao.findTopBooks(TOP_BOOKS_LIMIT);
        return topBooks;
    }

    public void showBookByGenre(Long selectedGenreId) {
        searchType = SearchType.SEARCH_GENRE;
        this.selectedGenreId = selectedGenreId;
    }

    public void showAll() {
        searchType = SearchType.ALL;
    }

    public String getSearchMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("weblibrary", FacesContext.getCurrentInstance().getViewRoot().getLocale());

        String message = null;

        if(null == searchType) {
            return null;
        }

        switch (searchType) {
            case SEARCH_GENRE:
                message = bundle.getString("genre") + ": '" + genreDao.get(selectedGenreId) + "'";
                break;
            case SEARCH_TEXT:

                if(null == searchText || searchText.trim().length()==0) {
                    return null;
                }

                message = bundle.getString("search") + ": '" + searchText + "'";
                break;
        }

        return message;
    }

    public byte[] getContent(long id) {

        byte[] content;

        if(uploadedContent != null) {
            content = uploadedContent;
        } else {
            content = bookDao.getContent(id);
        }

        return content;
    }

    public void uploadImage(FileUploadEvent event) {
        if(event.getFile() != null) {
            uploadedImage = event.getFile().getContents();
        }
    }

    public void uploadContent(FileUploadEvent event) {
        if(event.getFile() != null) {
            uploadedContent = event.getFile().getContents();
        }
    }

    public void searchAction() {
        searchType = SearchType.SEARCH_TEXT;
    }

    public void updateViewCount(long viewCount, long id) {
        bookDao.updateViewCount(viewCount+1, id);
    }

    public Page<Book> getBookPages(){
        return bookPages;
    }

    public void onCloseDialog(CloseEvent event) {
        uploadedContent = null;
    }

}
