package com.market.filter;

import com.market.pojo.User;
import com.market.utils.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lambda
 */
public class SysFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        //登录拦截，我们需要获取session来判断，
       HttpServletRequest request= (HttpServletRequest)req;
       HttpServletResponse response=(HttpServletResponse)resp;
        User user = (User)request.getSession().getAttribute(Constants.USER_SESSION);
        if (user==null){
            //证明该用户注销
            response.sendRedirect("/smbms/error.jsp");
        }
        filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
