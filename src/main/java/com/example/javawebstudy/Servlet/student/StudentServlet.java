package com.example.javawebstudy.Servlet.student;

import com.example.javawebstudy.Utils.MybatisUtil;
import com.example.javawebstudy.Utils.ThymeLeafUtil;
import com.example.javawebstudy.entity.Book;
import com.example.javawebstudy.entity.Student;
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
import java.util.Objects;

@WebServlet("/student_manage")
public class StudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Map<String, String[]> parameterMap = req.getParameterMap();
        List<Student> students = studentMapper.selectAllStudents();

        Context context = new Context();
        context.setVariable("allStudents", students);

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

        ThymeLeafUtil.process("student-manage.html", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();

        if(parameterMap.containsKey("search_str") && req.getParameter("search_str") != null && !req.getParameter("search_str").isEmpty()) {
            SqlSession sqlSession = MybatisUtil.getSqlSession();
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            List<Student> students = studentMapper.searchStudentsByStr(req.getParameter("search_str"));
            Context context = new Context();
            context.setVariable("allStudents", students);
            sqlSession.close();
            ThymeLeafUtil.process("student-manage.html", context, resp);
        }else{
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("operationSuccess", false);
            httpSession.setAttribute("notificationMessage", "错误：搜索字段为空!");
            resp.sendRedirect("student_manage");
        }
    }
}
