package com.jetchiu.supercanteen.config;

import com.alibaba.fastjson2.JSON;
import com.jetchiu.supercanteen.DTO.Res;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        // 直接提示前端认证错误
        out.write(JSON.toJSONString(Res.Error(null,"认证失败")));
        out.flush();
        out.close();
    }
}
