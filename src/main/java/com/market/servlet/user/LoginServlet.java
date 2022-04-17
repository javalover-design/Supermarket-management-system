package com.market.servlet.user;

import com.market.pojo.User;
import com.market.service.user.UserService;
import com.market.service.user.UserServiceImpl;
import com.market.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lambda
 */
public class LoginServlet extends HttpServlet {
    /**Servlet控制层要去调用业务层代码Service*/
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入了servlet");
        //获取用户名与密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        //和数据库层中的内容对比，调用业务层的内容
        UserService userService=new UserServiceImpl();
        //此处已经将用户查出来了
        User login = userService.login(userCode, userPassword);
        if (login!=null && login.getUserPassword().equals(userPassword)){
            //证明找到了，是存在这个人的。将用户信息放入session中管理,名为～，值为当前的用户，
            // 将其存入session中
            req.getSession().setAttribute(Constants.USER_SESSION,login);
            //跳转到内部的主页
            resp.sendRedirect( "jsp/frame.jsp");

        }else {
            //如果没有此人，则将其转发会登录页面，顺带提示错误信息
            req.setAttribute("error","用户名或者密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }
    }


