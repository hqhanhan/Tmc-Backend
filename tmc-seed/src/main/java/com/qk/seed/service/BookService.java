package com.qk.seed.service;

import com.qk.seed.model.po.Book;
import com.qk.seed.model.po.BookWithBookStore;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getBookById(Long id);

    List<Book> getBooksByAuthor(String author);

    List<Book> getBooksByPage(Integer page, Integer perPage);

    List<String> getAllBookNames();

    Optional<BookWithBookStore> getBookWithBookStoreById(Long id);

    Integer getTotalPage(Integer perPage);

    boolean saveBook(Book book);

    boolean modifyBookOnNameById(Book book);

    boolean deleteBookById(Long id);

    boolean save(Book book);


}
