package com.example.javawebstudy.Servlet.borrow;

import com.example.javawebstudy.Utils.MybatisUtil;
import com.example.javawebstudy.Utils.ThymeLeafUtil;
import com.example.javawebstudy.entity.Borrow;
import com.example.javawebstudy.mapper.BorrowMapper;
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

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        BorrowMapper borrowMapper = sqlSession.getMapper(BorrowMapper.class);
        List<Borrow> allBorrowInfo = borrowMapper.SelectAllBorrowInfo();
        int borrowQuantity = borrowMapper.getBorrowQuantity();
        int bookQuantity = borrowMapper.getBookQuantity();
        int studentQuantity = borrowMapper.getStudentQuantity();


        Context context = new Context();
        context.setVariable("allBorrowInfo", allBorrowInfo);
        context.setVariable("borrowQuantity",borrowQuantity);
        context.setVariable("bookQuantity", bookQuantity);
        context.setVariable("studentQuantity", studentQuantity);

        // 获取Session中的内容
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
        //System.out.println(allBorrowInfo.toString() + " " + Integer.toString(borrowQuantity));

        ThymeLeafUtil.process("index.html", context, resp);
    }
}
