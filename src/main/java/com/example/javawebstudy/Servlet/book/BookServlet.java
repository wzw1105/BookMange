package com.example.javawebstudy.Servlet.book;

import com.example.javawebstudy.Utils.MybatisUtil;
import com.example.javawebstudy.Utils.ThymeLeafUtil;
import com.example.javawebstudy.entity.Book;
import com.example.javawebstudy.mapper.BookMapper;
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
import java.util.Objects;

@WebServlet("/book_manage")
public class BookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);


        List<Book> books = bookMapper.selectAllBooks();

        Context context = new Context();
        context.setVariable("allBooks", books);

        HttpSession httpSession = req.getSession();
        Object operationSuccess = httpSession.getAttribute("operationSuccess");
        Object notificationMessage = httpSession.getAttribute("notificationMessage");
        if(operationSuccess != null) {
            context.setVariable("operationSuccess", operationSuccess);
            context.setVariable("notificationMessage", notificationMessage);

            httpSession.removeAttribute("operationSuccess");
            httpSession.removeAttribute("notificationMessage");
        }
        sqlSession.close();
        ThymeLeafUtil.process("book-manage.html", context, resp);
    }
    /*
    处理搜索请求
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        if(parameterMap.containsKey("search_str") && req.getParameter("search_str") != null && !req.getParameter("search_str").isEmpty()) {
            SqlSession sqlSession = MybatisUtil.getSqlSession();
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            //System.out.println("book_manage SEARCH: " + req.getParameter("search_str"));
            List<Book> books = bookMapper.searchBooksByStr(req.getParameter("search_str"));
            //System.out.println("book_manage SEARCH PARAMETER: " + req.getParameter("search_str"));

            Context context = new Context();
            context.setVariable("allBooks", books);
            sqlSession.close();
            ThymeLeafUtil.process("book-manage.html", context, resp);
        }else{
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("operationSuccess", false);
            httpSession.setAttribute("notificationMessage", "错误：搜索字段为空!");
            resp.sendRedirect("book_manage");
        }
    }
}
