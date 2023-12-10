package com.example.javawebstudy.Servlet.borrow;

import com.example.javawebstudy.Utils.MybatisUtil;
import com.example.javawebstudy.mapper.BorrowMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

@WebServlet("/delete_borrow")
public class DeleteBorrowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int deleteBorrowId = Integer.parseInt(req.getParameter("borrow_id"));
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        BorrowMapper borrowMapper = sqlSession.getMapper(BorrowMapper.class);
        int affectedRows = borrowMapper.deleteABorrowInfo(deleteBorrowId);
        //System.out.println("affectedRows: " + affectedRows);

        //Context context = new Context();
        //context.setVariable("deletedRows", affectedRows);
        //ThymeLeafUtil.process("index.html", context, resp);
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("operationSuccess", affectedRows > 0);
        httpSession.setAttribute("notificationMessage", affectedRows > 0 ? "成功删除借阅信息！" : "错误：删除借阅信息失败！");
        sqlSession.close();
        resp.sendRedirect("index");

    }
}
