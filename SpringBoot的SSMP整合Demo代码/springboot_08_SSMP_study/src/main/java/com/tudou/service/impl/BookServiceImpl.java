package com.tudou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tudou.mapper.BookMapper;
import com.tudou.pojo.Book;
import com.tudou.service.BookService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 直接使用Mybatis-plus写好的service层方法
 * 如果内置的方法还不足够，可以在接口定义新方法，在实现类进行实现
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;

    /**
     * 分页查询
     * @param currPage 当前的页码
     * @param pageSize 每页的大小
     * @return 分页信息 包含总数据条数、查询到的分页数据、总页数等信息
     */
    @Override
    public Page<Book> getByPage(int currPage, int pageSize) {
        Page<Book> page = new Page<>(currPage, pageSize);
         page = bookMapper.selectPage(page, null);
        return page;
    }

    /**
     *
     * @param type 图书类型
     * @param name 图书名字
     * @param description 图书描述
     * @return 查询到的书籍
     */
    @Override
    public List<Book> getByCondition(String type, String name, String description) {
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<>();
        if (Strings.isNotBlank(type)) {
            lqw.like(Book::getType, type);
        }
        if (Strings.isNotBlank(name)) {
            lqw.like(Book::getName, name);
        }
        if (Strings.isNotBlank(description)) {
            lqw.like(Book::getDescription, description);
        }
        return bookMapper.selectList(lqw);
    }
}
