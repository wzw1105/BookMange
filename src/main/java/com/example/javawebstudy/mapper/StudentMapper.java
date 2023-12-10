package com.example.javawebstudy.mapper;

import com.example.javawebstudy.entity.Book;
import com.example.javawebstudy.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StudentMapper {
    @Results({
            @Result(column = "student_id", property = "sid"),
            @Result(column = "student_name", property = "name"),
            @Result(column = "student_grade", property = "grade"),
            @Result(column = "borrow_quantity", property = "borrowQuantity"),
            @Result(column = "need_return_quantity", property = "needReturnQuantity")
    })
    @Select("select student.sid as student_id, student.name as student_name, student.grade as student_grade, COUNT(borrow.id) as borrow_quantity, COUNT(if(NOW() > borrow.due_time, 1, null)) as need_return_quantity " +
            "from student left join borrow on student.sid = borrow.sid " +
            "group by student.sid")
    List<Student> selectAllStudents();

    @Insert("insert into student(sid, name, grade) values (${sid}, '${name}', '${grade}')")
    int insertAStudent(@Param("sid") int sid, @Param("name") String name, @Param("grade") String grade);

    @Delete("delete from student where sid=${sid}")
    int deleteAStudent(int sid);

    @Results({
            @Result(column = "student_id", property = "sid"),
            @Result(column = "student_name", property = "name"),
            @Result(column = "student_grade", property = "grade"),
            @Result(column = "borrow_quantity", property = "borrowQuantity"),
            @Result(column = "need_return_quantity", property = "needReturnQuantity")
    })
    @Select("select student.sid as student_id, student.name as student_name, student.grade as student_grade, COUNT(borrow.id) as borrow_quantity, COUNT(if(NOW() > borrow.due_time, 1, null)) as need_return_quantity " +
            "from student left join borrow on student.sid = borrow.sid " +
            "where student.sid like '%${str}%' or student.name like '%${str}%' or student.grade like '%${str}%'" +
            "group by student.sid")
    List<Student> searchStudentsByStr(String str);
}
