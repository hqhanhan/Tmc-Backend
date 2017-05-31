package com.qk.seed.service;

import com.qk.seed.model.entity.BookStoreWithBooks;
import com.qk.seed.model.entity.BookStore;

import java.util.List;
import java.util.Optional;


public interface BookStoreService {

    Optional<BookStore> getBookStoreById(Long id);

    List<String> getAllBookStoreNames();

    Optional<BookStoreWithBooks> getBookStoreWithBooksById(Long id);

}
