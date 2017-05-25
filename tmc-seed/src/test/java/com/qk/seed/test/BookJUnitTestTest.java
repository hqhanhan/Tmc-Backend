package com.qk.seed.test;

import com.qk.seed.Start;
import com.qk.seed.model.po.Book;
import com.qk.seed.service.BookService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

/**
 * Description：
 * Created by hqhan on 2017/5/25 0025.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE,classes = Start.class)




///由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。

@WebAppConfiguration
@SpringApplicationConfiguration(classes = Start.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BookJUnitTestTest {


    private final Supplier<String> uuid = () -> UUID.randomUUID().toString().replace("-", "");

    private static final long SUCCESS = 200;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Autowired
    private BookService bookService;


    @Transactional
    @Test
    public void index()throws Exception{

        Optional<ResponseEntity<Book>> bookResponseEntity = bookService.getBookById(1L).map(ResponseEntity::ok);
        ResponseEntity<Book> bookResponseEntity1 = bookResponseEntity.get();
        int status = bookResponseEntity1.getStatusCode().value();
        Book book = bookResponseEntity1.getBody();
        Long id = book.getId();
        Double price = book.getPrice();
        assertEquals(Double.valueOf(1000), price);
        assertEquals(SUCCESS, status);
        Long oldId = Long.valueOf(1);
        assertEquals(id, oldId);


    }

}
