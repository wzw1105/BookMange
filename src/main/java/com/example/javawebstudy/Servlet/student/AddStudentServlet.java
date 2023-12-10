package com.example.javawebstudy.Servlet.student;

import com.example.javawebstudy.Utils.MybatisUtil;
import com.example.javawebstudy.Utils.ThymeLeafUtil;
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
import java.util.Map;

@WebServlet("/add_student")
public class AddStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ThymeLeafUtil.process("add-student.html", new Context(), resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        HttpSession httpSession = req.getSession();

        if(parameterMap.containsKey("student_id") && req.getParameter("student_id") != null && !req.getParameter("student_id").isEmpty()
                && parameterMap.containsKey("student_name") && req.getParameter("student_name") != null && !req.getParameter("student_name").isEmpty()
                && parameterMap.containsKey("student_grade") && req.getParameter("student_grade") != null && !req.getParameter("student_grade").isEmpty()) {
            int sid = Integer.parseInt(req.getParameter("student_id"));
            String name = req.getParameter("student_name");
            String grade = req.getParameter("student_grade");

            SqlSession sqlSession = MybatisUtil.getSqlSession();
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

            try{
                int affectedRows = studentMapper.insertAStudent(sid, name, grade);
                if(affectedRows > 0) {
                    httpSession.setAttribute("operationSuccess", true);
                    httpSession.setAttribute("notificationMessage", "添加学生成功!");
                }else {
                    httpSession.setAttribute("operationSuccess", false);
                    httpSession.setAttribute("notificationMessage", "错误：添加学生失败!");
                }
            }catch(Exception e) {
                httpSession.setAttribute("operationSuccess", false);
                httpSession.setAttribute("notificationMessage", "错误：添加学生失败!");
            }

            sqlSession.close();
        }else{
            httpSession.setAttribute("operationSuccess", false);
            httpSession.setAttribute("notificationMessage", "错误：表单信息不完整!");
        }

        resp.sendRedirect("student_manage");
    }
}
