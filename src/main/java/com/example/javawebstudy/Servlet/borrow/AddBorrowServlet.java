package com.example.javawebstudy.Servlet.borrow;

import com.example.javawebstudy.Utils.MybatisUtil;
import com.example.javawebstudy.Utils.ThymeLeafUtil;
import com.example.javawebstudy.entity.Book;
import com.example.javawebstudy.entity.Student;
import com.example.javawebstudy.mapper.BookMapper;
import com.example.javawebstudy.mapper.BorrowMapper;
import com.example.javawebstudy.mapper.StudentMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/add_borrow")
public class AddBorrowServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = studentMapper.selectAllStudents();
        List<Book> books = bookMapper.selectAllBooks();
        Context context = new Context();
        context.setVariable("allStudents", students);
        context.setVariable("allBooks", books);
        sqlSession.close();

        ThymeLeafUtil.process("add-borrow-info.html", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        HttpSession httpSession = req.getSession();
        if(parameterMap.containsKey("borrow_student") && parameterMap.containsKey("borrow_book")) {
            System.out.println(req.getParameter("borrow_student") + " " + req.getParameter("borrow_book"));
            int sid = Integer.parseInt(req.getParameter("borrow_student").split(" - ")[0]);
            int bid = Integer.parseInt(req.getParameter("borrow_book").split(" - ")[0]);
            SqlSession sqlSession = MybatisUtil.getSqlSession();
            BorrowMapper borrowMapper = sqlSession.getMapper(BorrowMapper.class);
            try{
                int affectedRows = borrowMapper.addABorrowInfo(sid, bid);
                httpSession.setAttribute("operationSuccess", affectedRows > 0);
                httpSession.setAttribute("notificationMessage", affectedRows > 0 ? "成功添加借阅信息！" : "错误：添加借阅信息失败！");
            }catch (Exception e) {
                httpSession.setAttribute("operationSuccess", false);
                httpSession.setAttribute("notificationMessage", "错误：该书籍已全部借出！");
            }

            sqlSession.close();

        }else{
            httpSession.setAttribute("operationSuccess", false);
            httpSession.setAttribute("notificationMessage", "错误：表单信息不完整！");
        }
        resp.sendRedirect("index");
    }
}
