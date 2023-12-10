package com.example.javawebstudy.Utils;

import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;

public class ThymeLeafUtil {
    private static final TemplateEngine templateEngine;

    static {
        templateEngine = new TemplateEngine();
        //设定模板解析器决定了从哪里获取模板文件，这里直接使用ClassLoaderTemplateResolver表示加载内部资源文件
        ClassLoaderTemplateResolver r = new ClassLoaderTemplateResolver();
        templateEngine.setTemplateResolver(r);
    }

    public static void process(String template, Context context, HttpServletResponse resp) throws IOException {
        templateEngine.process(template, context, resp.getWriter());

    }
}
