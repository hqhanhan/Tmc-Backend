package com.qk.seed.authenticator.filter;

import com.qk.seed.authenticator.exception.NotAuthorizedException;
import com.qk.seed.authenticator.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;



@WebFilter
public class AuthenticationFilter implements Filter {

    @Autowired
    private TokenManager tokenManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader!=null || !authorizationHeader.startsWith("tmcqk ") ){
            throw new NotAuthorizedException();
        }
        String token = authorizationHeader.substring("tmcqk".length()).trim();
        //TODO 解析 authorizationHeader ，确认token是否合法
        if(true){

        }

        filterChain.doFilter(request,response);


    }

    @Override
    public void destroy() {

    }
}
