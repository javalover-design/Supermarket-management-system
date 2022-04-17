package com.market.servlet.user;

import com.market.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lambda
 */
public class LogoutServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //移除用户的session即完成注销功能
        req.getSession().removeAttribute(Constants.USER_SESSION);
        //之后返回登录页面
        resp.sendRedirect(req.getContextPath()+"/login.jsp");
    }
}
