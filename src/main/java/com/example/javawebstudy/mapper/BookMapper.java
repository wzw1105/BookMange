package com.example.javawebstudy.mapper;

import com.example.javawebstudy.entity.Book;
import com.example.javawebstudy.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface BookMapper {

    @Results({
            @Result(column = "bid", property = "bid"),
            @Result(column = "name", property = "name"),
            @Result(column = "year", property = "date"),
            @Result(column = "remain_quantity", property = "remainQuantity"),
            @Result(column = "total_quantity", property = "totalQuantity")
    })
    @Select("select * from book")
    List<Book> selectAllBooks();

    @Insert("insert into book(name, year, total_quantity) values('${name}', '${date}', ${totalQuantity})")
    int insertABook(@Param("name") String name, @Param("date") String date, @Param("totalQuantity") int totalQuantity);

    @Delete("delete from book where bid = ${bid}")
    int deleteABook(int bid);

    @Results({
            @Result(column = "bid", property = "bid"),
            @Result(column = "name", property = "name"),
            @Result(column = "year", property = "date"),
            @Result(column = "remain_quantity", property = "remainQuantity"),
            @Result(column = "total_quantity", property = "totalQuantity")
    })
    @Select("select * from book where bid like '%${str}%' or name like '%${str}%'")
    List<Book> searchBooksByStr(String str);
}
