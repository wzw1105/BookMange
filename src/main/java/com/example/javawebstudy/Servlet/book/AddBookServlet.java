package com.example.javawebstudy.Servlet.book;

import com.example.javawebstudy.Utils.MybatisUtil;
import com.example.javawebstudy.Utils.ThymeLeafUtil;
import com.example.javawebstudy.mapper.BookMapper;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet("/add_book")
public class AddBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ThymeLeafUtil.process("add-book.html", new Context(), resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        HttpSession httpSession = req.getSession();
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );

        if(parameterMap.containsKey("name") && req.getParameter("name") != null && !req.getParameter("name").isEmpty()
                && parameterMap.containsKey("date") && req.getParameter("date") != null && !req.getParameter("date").isEmpty()
                && parameterMap.containsKey("total_quantity") && req.getParameter("total_quantity") != null && !req.getParameter("total_quantity").isEmpty()) {
            String name = req.getParameter("name");
            String year = req.getParameter("date");
            int totalQuantity = Integer.parseInt(req.getParameter("total_quantity"));

            SqlSession sqlSession = MybatisUtil.getSqlSession();
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);

            try{

                int affectedRows = bookMapper.insertABook(name, year, totalQuantity);
                //System.out.println("add_book POST: " + name + " " + year + Integer.toString(affectedRows) + " " + Integer.toString(totalQuantity));
                if(affectedRows > 0) {
                    httpSession.setAttribute("operationSuccess", true);
                    httpSession.setAttribute("notificationMessage", "添加学生成功!");
                }else {
                    httpSession.setAttribute("operationSuccess", false);
                    httpSession.setAttribute("notificationMessage", "错误：添加学生失败!");
                }
            }catch(Exception e) {
                System.out.println(e.toString());
                httpSession.setAttribute("operationSuccess", false);
                httpSession.setAttribute("notificationMessage", "错误：添加学生失败!");
            }

            sqlSession.close();
        }else{
            httpSession.setAttribute("operationSuccess", false);
            httpSession.setAttribute("notificationMessage", "错误：表单信息不完整!");
        }

        resp.sendRedirect("book_manage");


    }
}
