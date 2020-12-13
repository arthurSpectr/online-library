package com.techqar.weblibrary.jsfui.controller;

import com.google.common.base.Strings;
import com.techqar.weblibrary.dao.AuthorDao;
import com.techqar.weblibrary.domain.Author;
import com.techqar.weblibrary.jsfui.model.LazyDataTable;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
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
public class AuthorController extends AbstractController<Author> {

    private int rowsCount = 20;
    private int first;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private SprController sprController;

    private Author selectedAuthor;

    private LazyDataTable<Author> lazyModel;

    private Page<Author> authorPages;

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable<>(this);
    }

    public void save() {
        authorDao.save(selectedAuthor);
        RequestContext.getCurrentInstance().execute("PF('dialogAuthor').hide()");
    }

    @Override
    public Page<Author> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        if (sortField == null) {
            sortField = "fio";
        }

        // для удобной проверки строк - используем библиотеку Google Guava и метод isNullOrEmpty
        if (Strings.isNullOrEmpty(sprController.getSearchText())) {
            authorPages = authorDao.getAll(pageNumber, pageSize, sortField, sortDirection);
        } else {
            authorPages = authorDao.search(pageNumber, pageSize, sortField, sortDirection, sprController.getSearchText());
        }

        return authorPages;
    }

    @Override
    public void addAction() {
        selectedAuthor = new Author();
        showEditDialog();
    }

    @Override
    public void editAction() {
        showEditDialog();
    }

    @Override
    public void deleteAction() {
        authorDao.delete(selectedAuthor);
    }

    public void showEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogAuthor').show()");
    }

    public List<Author> find(String fio) {
        return authorDao.search(fio);
    }
}
