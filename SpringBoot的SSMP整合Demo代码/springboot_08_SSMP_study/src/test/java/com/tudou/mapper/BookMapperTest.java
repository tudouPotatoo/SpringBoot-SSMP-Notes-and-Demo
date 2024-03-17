package com.tudou.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tudou.pojo.Book;
import com.tudou.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    /**
     * 增加一条数据
     */
    @Test
    public void testInsert() {
        Book book = new Book();
        book.setName("马斯克传");
        book.setType("人物传记");
        book.setDescription("《馬斯克》是美國商業巨頭、SpaceX/特斯拉執行長馬斯克的授權傳記。");
        int count = bookMapper.insert(book);
        System.out.println(count > 0);
    }

    /**
     * 删除一条数据
     */
    @Test
    public void testDelete() {
        int count = bookMapper.deleteById(14);
        System.out.print(count > 0);
    }

    /**
     * 根据id更新数据
     */
    @Test
    public void testUpdate() {
        Book book = new Book();
        book.setId(2);
        book.setName("乔布斯传");
        book.setType("人物传记");
        book.setDescription("《賈伯斯傳》是史蒂夫·賈伯斯授權CNN和《時代雜誌》前高管沃爾特·艾薩克森撰寫的傳記。");
        int count = bookMapper.updateById(book);
        System.out.println(count);
    }

    /**
     * 根据id查找数据
     */
    @Test
    public void testSelectOne() {
        Book book = bookMapper.selectById(2);
        System.out.println(book);
    }

    /**
     * 查找所有数据
     */
    @Test
    public void testSelectAll() {
        List<Book> books = bookMapper.selectList(null);
        System.out.println(books);
    }

    /**
     * 分页查找数据
     * mybatis-plus使用分页要配置拦截器
     */
    @Test
    public void testSelectByPage() {
        int currPage = 1;
        int pageSize = 5;
        IPage<Book> page = new Page<>(currPage, pageSize);
        page = bookMapper.selectPage(page, null);
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
    public void testSelectByCondition() {
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<>();
        lqw.like(Book::getType, "计算机");
        lqw.like(Book::getName, "Spring");
        List<Book> books = bookMapper.selectList(lqw);
        System.out.println(books);
    }
}
