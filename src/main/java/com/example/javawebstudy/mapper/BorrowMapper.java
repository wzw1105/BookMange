package com.example.javawebstudy.mapper;

import com.example.javawebstudy.entity.Borrow;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BorrowMapper {

    @Results({
            @Result(column = "id", property = "bid"),
            @Result(column = "student_sid", property = "student.sid"),
            @Result(column = "student_name", property = "student.name"),
            @Result(column = "student_grade", property = "student.grade"),
            @Result(column = "book_bid", property = "book.bid"),
            @Result(column = "book_name", property = "book.name"),
            @Result(column = "book_year", property = "book.date"),
            @Result(column = "remain_quantity", property = "book.remainQuantity"),
            @Result(column = "total_quantity", property = "book.totalQuantity"),
            @Result(column = "borrow_time", property = "borrowTime"),
            @Result(column = "due_time", property = "dueTime")
    })
    @Select("select *, student.sid as student_sid, student.name as student_name, student.grade as student_grade, book.bid as book_bid, book.name as book_name, book.year as book_year " +
            "from borrow inner join book on borrow.bid = book.bid inner join student on borrow.sid = student.sid order by borrow_time desc")
    public List<Borrow> SelectAllBorrowInfo();

    @Delete("delete from borrow where id = ${borrowID}")
    public int deleteABorrowInfo(@Param("borrowID") int borrowID);

    @Insert("insert into borrow(sid, bid) values (${sid}, ${bid})")
    public int addABorrowInfo(@Param("sid") int sid, @Param("bid") int bid);

    @Select("select count(*) from student")
    public int getStudentQuantity();

    @Select("select count(*) from book")
    public int getBookQuantity();

    @Select("select count(*) from borrow")
    public int getBorrowQuantity();
}
