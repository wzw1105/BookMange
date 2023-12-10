package com.example.javawebstudy.Servlet.book;

import com.example.javawebstudy.Utils.MybatisUtil;
import com.example.javawebstudy.mapper.BookMapper;
import com.example.javawebstudy.mapper.StudentMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.Map;

@WebServlet("/delete_book")
@Log
public class DeleteBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        HttpSession httpSession = req.getSession();
        if(parameterMap.containsKey("delete_book_id") && !req.getParameter("delete_book_id").isEmpty()) {
            SqlSession sqlSession = MybatisUtil.getSqlSession();
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            int delete_book_id = Integer.parseInt(req.getParameter("delete_book_id"));
            try{
                int affected_rows = bookMapper.deleteABook(delete_book_id);
                if(affected_rows == 0) {
                    httpSession.setAttribute("operationSuccess", false);
                    httpSession.setAttribute("notificationMessage", "错误：删除失败!");
                }else{
                    httpSession.setAttribute("operationSuccess", true);
                    httpSession.setAttribute("notificationMessage", "删除书籍成功!");
                }
            }catch (Exception e) {
                log.warning(e.toString());
                httpSession.setAttribute("operationSuccess", false);
                httpSession.setAttribute("notificationMessage", "错误：删除失败!");
            }

            sqlSession.close();
        }else{
            httpSession.setAttribute("operationSuccess", false);
            httpSession.setAttribute("notificationMessage", "错误：书籍ID为空!");
        }
        resp.sendRedirect("book_manage");
    }
}
