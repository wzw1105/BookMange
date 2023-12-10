package com.example.javawebstudy.Servlet.auth;

import java.io.*;
import java.util.Map;

import com.example.javawebstudy.Utils.MybatisUtil;
import com.example.javawebstudy.Utils.ThymeLeafUtil;
import com.example.javawebstudy.entity.User;
import com.example.javawebstudy.mapper.UsersMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.thymeleaf.context.Context;

@WebServlet(name = "loginServlet", value = "/login")
@Log
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //request.getRequestDispatcher("login.html").forward(request, response); // 重定向到Tomcat创建的默认Servlet：即index.html页面
        Context context = new Context();
        ThymeLeafUtil.process("login.html", context, response);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        Map<String, String[]> parameterMap = req.getParameterMap();

        if(parameterMap.containsKey("username") && parameterMap.containsKey("password")) {
            try(SqlSession session = MybatisUtil.getSqlSession()) {
                UsersMapper usersMapper = session.getMapper(UsersMapper.class);
                String name = req.getParameter("username");
                String password = req.getParameter("password");
                User user = usersMapper.selectUserByNamePassword(name, password);
                log.info("Submit: " + name + " " + password);
                if(user != null) {
                   log.info("Find User: " + user.getUsername() + " " + user.getPassword());
                    // 在session中添加属性user，
                    HttpSession httpSession = req.getSession();
                    httpSession.setAttribute("user", user);
                    // resp.getWriter().write("用户 "+ name +" 登录成功!");

                    // 重定向到首页
                    resp.setStatus(302);
                    resp.setHeader("Location", "index");
                }else {
                    resp.getWriter().write("用户名或密码错误！");
                }
            }
        }else{
            resp.getWriter().write("您提交的表单不完整！");
        }

    }

    @Override
    public void destroy() {
    }
}