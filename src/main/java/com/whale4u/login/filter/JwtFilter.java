package com.whale4u.login.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtFilter extends GenericFilterBean {

    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

            chain.doFilter(req, res);
        } else {
            //当HTTP请求头不包含authorization或者authorization的值不是以Bearer 开头时
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                String data = "Missing or invalid Authorization header";
                PrintWriter writer = null;
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html; charset=utf-8");
                try {
                    writer = response.getWriter();
                    // 通过 PrintWriter 将 data 数据直接 print 回去
                    writer.print(data);
                } catch (IOException e) {
                } finally {
                    if (writer != null)
                        writer.close();
                }
                //抛出异常
//                throw new ServletException("Missing or invalid Authorization header");
            }

            final String token = authHeader.substring(7);

            try {
                final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
//                System.out.println(claims); //{sub=admin, roles=user, iat=1593685938}
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                throw new ServletException("Invalid token");
            }

            chain.doFilter(req, res);
        }
    }
}