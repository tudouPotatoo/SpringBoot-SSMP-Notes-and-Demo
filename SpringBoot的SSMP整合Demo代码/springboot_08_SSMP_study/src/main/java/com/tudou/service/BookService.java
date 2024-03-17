package com.tudou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tudou.pojo.Book;
import java.util.List;

/**
 * 使用Mybatis-plus写好的Service层代码需要配置
 * 1. Service接口继承IService<Entity.Class>类
 * 形如：public interface BookService extends IService<Book>
 * 2. ServiceImpl类添加@Service注解
 * 3. ServiceImpl实现类实现Service接口
 * 4. ServiceImpl实现类继承ServiceImpl<XxxMapper.class, Entity.Class>类
 *    这里的XxxMapper.class代表这个接口对应的mapper层的mapper接口
 * 形如：@Service
 *      public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService
 */
public interface BookService extends IService<Book> {
    /**
     * 分页查询
     * @param currPage 当前的页码
     * @param pageSize 每页的大小
     * @return 分页信息 包含总数据条数、查询到的分页数据、总页数等信息
     */
    Page<Book> getByPage(int currPage, int pageSize);

    /**
     *
     * @param type 图书类型
     * @param name 图书名字
     * @param description 图书描述
     * @return 查询到的书籍
     */
    List<Book> getByCondition(String type, String name, String description);
}
