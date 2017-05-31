package com.qk.seed.service.impl;

//import com.qk.oauth.dao.mapper.ClientDetailsMapper;
//import com.qk.oauth.model.po.ClientDetails;
import com.qk.seed.dao.mapper.BookMapper;

import com.qk.seed.model.entity.Book;
import com.qk.seed.model.entity.BookWithBookStore;
import com.qk.seed.service.BookService;
import com.qk.seed.util.PageUtil;

import com.qk.seed.dao.repository.BookRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@CommonsLog
@Service
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

//    @Autowired
//    private ClientDetailsMapper clientDetailsMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public Optional<Book> getBookById(Long id) {
        Book book = bookMapper.selectBookById(id);
        return Optional.ofNullable(book);
    }

    @Transactional
    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.selectBooksByAuthor(author);
    }

    @Transactional
    @Override
    public List<Book> getBooksByPage(Integer page, Integer perPage) {
        Integer offset = PageUtil.calculateOffset(page, perPage);
        return bookRepository.selectBooksByPage(offset, perPage);
    }

    @Transactional
    @Override
    public List<String> getAllBookNames() {
        return bookRepository
                .selectAllBooks()
                .stream()
                .map(Book::getName)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public Optional<BookWithBookStore> getBookWithBookStoreById(Long id) {
        return Optional.ofNullable(bookRepository.selectBookWithBookStoreById(id));
    }

    @Transactional
    @Override
    public Integer getTotalPage(Integer perPage) {
        return PageUtil.calculateTotalPage(bookRepository.selectCount(), perPage);
    }

    @Override
    @Transactional
    public boolean saveBook(Book book) {
        return bookRepository.insertBook(book) > 0;
    }

    @Override
    @Transactional
    public boolean modifyBookOnNameById(Book book) {
        return bookRepository.updateBookOnNameById(book) > 0;
    }

    @Override
    @Transactional
    public boolean deleteBookById(Long id) {
        return bookRepository.deleteBookById(id) > 0;
    }


    @Override
    @Transactional
    public boolean save(Book book)  {
        boolean b = bookRepository.insertBook(book) > 0;
        log.debug(b);

//        ClientDetails record = new ClientDetails();
//        record.setAppid(UUID.randomUUID().toString());
//        record.setAppsecret(book.getName());
//        boolean b1 = clientDetailsMapper.insert(record) > 0;
//        log.debug(b1);

        return false;
    }
}