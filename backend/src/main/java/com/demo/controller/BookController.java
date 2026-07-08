package com.demo.controller;

import com.demo.context.UserContext;
import com.demo.dto.ApiResponse;
import com.demo.entity.Book;
import com.demo.service.IBookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    // ==================== 管理员 / 公共接口 ====================

    @GetMapping
    public ApiResponse<List<Book>> getAll() {
        return ApiResponse.success(bookService.findAll());
    }

    @GetMapping("/{bno}")
    public ApiResponse<Book> getById(@PathVariable String bno) {
        Book book = bookService.findByBno(bno);
        if (book != null) {
            return ApiResponse.success(book);
        }
        return ApiResponse.error(404, "教材不存在");
    }

    /** 管理员查某位教师的教材 */
    @GetMapping("/by-teacher/{tno}")
    public ApiResponse<List<Book>> getByTeacher(@PathVariable String tno) {
        return ApiResponse.success(bookService.findByTno(tno));
    }

    @PutMapping("/{bno}")
    public ApiResponse<String> update(@PathVariable String bno, @RequestBody Book book) {
        book.setBno(bno);
        if (bookService.update(book)) {
            return ApiResponse.success("修改成功");
        }
        return ApiResponse.error(400, "修改失败");
    }

    @PutMapping("/{bno}/stock")
    public ApiResponse<String> updateStock(@PathVariable String bno, @RequestBody Map<String, String> params) {
        if (bookService.updateStock(bno, params.get("bnum"))) {
            return ApiResponse.success("库存更新成功");
        }
        return ApiResponse.error(400, "库存更新失败");
    }

    @DeleteMapping("/{bno}")
    public ApiResponse<String> delete(@PathVariable String bno) {
        if (bookService.delete(bno)) {
            return ApiResponse.success("删除成功");
        }
        return ApiResponse.error(400, "删除失败");
    }

    @GetMapping("/search")
    public ApiResponse<List<Book>> search(
            @RequestParam(required = false) String bno,
            @RequestParam(required = false) String bname,
            @RequestParam(required = false) String bauthor,
            @RequestParam(required = false) String bsource,
            @RequestParam(required = false) String bedition,
            @RequestParam(required = false) String ccno,
            @RequestParam(required = false) String tno) {
        Book book = new Book();
        book.setBno(bno);
        book.setBname(bname);
        book.setBauthor(bauthor);
        book.setBsource(bsource);
        book.setBedition(bedition);
        book.setCcno(ccno);
        book.setTno(tno);
        return ApiResponse.success(bookService.dynamicSearch(book));
    }

    // ==================== 教师自助接口 ====================

    /** 教师查自己的教材 */
    @GetMapping("/my")
    public ApiResponse<List<Book>> getMyBooks() {
        return ApiResponse.success(bookService.findByTno(UserContext.getUserId()));
    }

    /** 教师添加自己的教材，tno 自动从上下文获取 */
    @PostMapping
    public ApiResponse<Book> add(@RequestBody Book book) {
        book.setTno(UserContext.getUserId());
        return ApiResponse.success("添加成功", bookService.add(book));
    }
}
