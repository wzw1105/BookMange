package com.example.javawebstudy.Servlet.student;

import com.example.javawebstudy.Utils.MybatisUtil;
import com.example.javawebstudy.mapper.StudentMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.Map;

@WebServlet("/delete_student")
public class DeleteStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        HttpSession httpSession = req.getSession();
        if(parameterMap.containsKey("delete_student_id") && !req.getParameter("delete_student_id").isEmpty()) {
            SqlSession sqlSession = MybatisUtil.getSqlSession();
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            int delete_student_id = Integer.parseInt(req.getParameter("delete_student_id"));
            try{
                int affected_rows = studentMapper.deleteAStudent(delete_student_id);
                if(affected_rows == 0) {
                    httpSession.setAttribute("operationSuccess", false);
                    httpSession.setAttribute("notificationMessage", "错误：删除失败!");
                }else{
                    httpSession.setAttribute("operationSuccess", true);
                    httpSession.setAttribute("notificationMessage", "删除学生成功!");
                }
            }catch (Exception e) {
                httpSession.setAttribute("operationSuccess", false);
                httpSession.setAttribute("notificationMessage", "错误：删除失败!");
            }

            sqlSession.close();
        }else{
            httpSession.setAttribute("operationSuccess", false);
            httpSession.setAttribute("notificationMessage", "错误：学生ID为空!");
        }
        resp.sendRedirect("student_manage");
    }
}
