package com.example.javawebstudy.filter;

import com.example.javawebstudy.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;

import java.io.IOException;

@WebFilter("/*")
@Log
public class SessionFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute("user");
        String url = req.getRequestURL().toString();
        if(user == null && !url.endsWith("/login") && !url.contains("/static/")) {
            log.info("WebFilter prevents request to " + req.getRequestURL().toString());
            res.sendRedirect("/JavaWeb/login");
            return;
        }
        chain.doFilter(req, res);

    }
}
