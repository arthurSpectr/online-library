package com.techqar.weblibrary.jsfui.controller;

import com.google.common.base.Strings;
import com.techqar.weblibrary.dao.GenreDao;
import com.techqar.weblibrary.domain.Genre;
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

@Getter
@Setter
@ManagedBean
@SessionScoped
@Component
public class GenreController extends AbstractController<Genre> {

    private int rowsCount = 20;
    private int first;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private SprController sprController;

    private Genre selectedGenre;

    private LazyDataTable<Genre> lazyModel;

    private Page<Genre> genrePages;

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable<>(this);
    }

    public List<Genre> find(String name) {
        return genreDao.search(name);
    }

    @Override
    public Page<Genre> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {

        if(null == sortField) {
            sortField = "name";
        }

        if(Strings.isNullOrEmpty(sprController.getSearchText())) {
            genrePages = genreDao.getAll(pageNumber, pageSize, sortField, sortDirection);
        } else {
            genrePages = genreDao.search(pageNumber, pageSize, sortField, sortDirection, sprController.getSearchText());
        }

        return genrePages;
    }

    @Override
    public void addAction() {
        selectedGenre = new Genre();
        showEditDialog();
    }

    @Override
    public void editAction() {
        showEditDialog();
    }

    private void showEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogGenre').show()");
    }

    @Override
    public void deleteAction() {
        genreDao.delete(selectedGenre);
    }

    public List<Genre> getAll() {
        return genreDao.getAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}
