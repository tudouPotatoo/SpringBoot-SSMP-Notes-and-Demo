package com.tudou.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tudou.controller.model.Result;
import com.tudou.pojo.Book;
import com.tudou.service.BookService;
import com.tudou.utils.enumeration.StatusMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
//@ResponseBody
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 增加书籍
     */
    @PostMapping
    public Result addBook(@RequestBody Book book) {
        bookService.save(book);
        return new Result(HttpStatus.OK.value(), StatusMsg.SUCCESS);
    }

    /**
     * 删除书籍
     */
    @DeleteMapping("/{id}")
    public Result deleteBookById(@PathVariable Integer id) {
        bookService.removeById(id);
        return new Result(HttpStatus.OK.value(), StatusMsg.SUCCESS);
    }

    /**
     * 修改书籍信息
     */
    @PutMapping
    public Result updateBook(@RequestBody Book book) {
        bookService.updateById(book);
        return new Result(HttpStatus.OK.value(), StatusMsg.SUCCESS);
    }

    /**
     * 查询书籍信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable int id) {
        Book book = bookService.getById(id);
        return new Result(HttpStatus.OK.value(), book);
    }

    /**
     * 查询所有书籍信息
     */
    @GetMapping
    public Result getAll() {
        List<Book> books = bookService.list();
        return new Result(HttpStatus.OK.value(), books);
    }

    /**
     * 分页查询书籍信息
     */
    @GetMapping("/{currPage}/{pageSize}")
    public Result getByPage(@PathVariable int currPage, @PathVariable int pageSize) {
        Page<Book> page = bookService.getByPage(currPage, pageSize);
        return new Result(HttpStatus.OK.value(), page);
    }

    /**
     * 根据条件查询书籍信息
     * 传入形式?type=xx&name=xxx&description=xx
     */
    @GetMapping("/query")
    public Result getByCondition(@RequestParam(required = false) String type, @RequestParam(required = false) String name, @RequestParam(required = false) String description) {
        List<Book> books = bookService.getByCondition(type, name, description);
        return new Result(HttpStatus.OK.value(), books);
    }
}
