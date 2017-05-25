package com.qk.seed.dao.repository;

import com.qk.seed.model.po.BookStore;
import com.qk.seed.model.po.BookStoreWithBooks;

import java.util.List;


public interface BookStoreRepository {

    BookStore selectBookStoreById(Long id);

    List<BookStore> selectAllBookStores();

    BookStoreWithBooks selectBookStoreWithBooksById(Long id);

}
