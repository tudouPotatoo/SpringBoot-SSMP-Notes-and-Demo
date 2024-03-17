package com.tudou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tudou.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    /**
     * 增加一条数据
     */
    @Test
    public void testSave() {
        Book book = new Book();
        book.setName("《燦爛千陽》");
        book.setType("长篇小说");
        book.setDescription("《燦爛千陽》，故事背景設定在阿富汗，描寫的是兩代主角因無情戰火摧殘家園而相遇相知的故事，時空跨越整整30年，涵括了阿富汗近代動盪不安的歷史。");
        System.out.println(bookService.save(book));
    }

    /**
     * 删除一条数据
     */
    @Test
    public void testRemove() {
        bookService.removeById(23);
    }

    /**
     * 根据id更新数据
     */
    @Test
    public void testUpdate() {
        Book book = new Book();
        book.setId(12);
        book.setName("追风筝的人");
        book.setType("长篇小说");
        book.setDescription("以第一人稱講述了來自阿富汗首都喀布爾富人區的普什圖少年阿米爾和他的童年好友，父親的哈扎拉僕人哈山之間的故事。");
        System.out.println(bookService.updateById(book));
    }

    /**
     * 根据id查找数据
     */
    @Test
    public void testGetById() {
        Book book = bookService.getById(2);
        System.out.println(book);
    }

    /**
     * 查找所有数据
     */
    @Test
    public void testList() {
        List<Book> books = bookService.list();
        System.out.println(books);
    }

    /**
     * 分页查找数据
     */
    @Test
    public void testListByPage() {
        int currPage = 3;
        int pageSize = 5;
        IPage<Book> page = new Page<>(currPage, pageSize);
        page = bookService.page(page, null);
        System.out.println("current =====>" + page.getCurrent());
        System.out.println("size =====>" + page.getSize());
        System.out.println("total =====>" + page.getTotal());
        System.out.println("pages =====>" + page.getPages());
        System.out.println("records =====>" + page.getRecords());
    }

    /**
     * 根据条件查询数据
     */
    @Test
    public void testListByCondition() {
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<>();
        lqw.like(Book::getType, "计算机");
        lqw.like(Book::getName, "Spring");
        List<Book> books = bookService.list(lqw);
        System.out.println(books);
    }
}
