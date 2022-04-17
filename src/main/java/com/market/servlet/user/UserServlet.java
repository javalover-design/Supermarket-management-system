package com.market.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.market.pojo.Role;
import com.market.pojo.User;
import com.market.service.role.RoleServiceImpl;
import com.market.service.user.UserServiceImpl;
import com.market.utils.Constants;
import com.market.utils.PageSupport;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lambda
 *
 * 实现Servlet复用
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //获取前端传递的方法名
        String method = req.getParameter("method");
        if (method.equals("savepwd") && method!=null){
            this.updatePwd(req,resp);

            //如果前端传递的是修改密码的方法名，那么就会执行修改密码操作
        }else if (method.equals("pwdmodify")&& method!=null){
                this.pwdModify(req,resp);
                //如果前端传递的是查询的值，那么调用响应查询的方法
        }else if (method.equals("query") && method!=null){
            this.query(req,resp);

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
/**编写一个修改密码的方法*/
    public void updatePwd(HttpServletRequest req,HttpServletResponse resp){
        //从session中拿id
        Object attribute = req.getSession().getAttribute(Constants.USER_SESSION);
        //定义一个布尔变量
        boolean flag=false;

        //获取用户输入的新密码
        String newpassword = req.getParameter("newpassword");
        if (attribute!=null && !StringUtils.isNullOrEmpty(newpassword)){
            //如果对应的session的id user不为空，并且设置了新的密码
            UserServiceImpl userService = new UserServiceImpl();
            User user=(User)attribute;
            flag= userService.upDate(user.getId(), newpassword);
            if (flag){
                //证明修改成功了，需要给用户相应的提示
                req.setAttribute("message","修改密码成功，请退出，使用新密码登录！");
                //密码修改成功之后需要移除session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            }else {
                //密码修改失败
                req.setAttribute("message","密码修改失败");
            }

        }else {
            //此处表示没有登录成功或者新密码设置的有问题
            req.setAttribute("message","新密码有问题");
        }

        //无论修改成功或者失败都需要向修改密码的页面跳转
        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**验证旧密码
     * 由于session中存在旧密码，所以我们的密码可以直接在session中获取，不需要在数据库中查询*/
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp){
        //从session中获取对象
        Object attribute = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");

        //使用万能的map，存储ajax传递的信息
        Map<String, String> resultMap = new HashMap<>();
        if (attribute==null){
            //如果session失效了,传递给ajax参数为result，值为sessionerror
            resultMap.put("result","sessionerror");

        }else if (StringUtils.isNullOrEmpty(oldpassword)){
            //此处表示输入的原来的密码为空，需要执行相应的操作
            resultMap.put("result","error");
        }else{
            //此时表示用户存在，并且旧密码正确,获取用户的旧密码（即用户登录时的密码）
            String userPassword = ((User) attribute).getUserPassword();
            if (userPassword.equals(oldpassword)){
                //此时表示输入的密码与数据库中的密码一致
                resultMap.put("result","true");
            }else {
                //此处表示不为空，用户存在，但是输入的内容与数据库中的密码不符合
                resultMap.put("result","false");
            }
        }

        try {
            //由于此时需要json的形式返回验证结果，所以我们需要设置响应的类型
            resp.setContentType("application/json");
            //以json形式返回需要流
            PrintWriter writer = resp.getWriter();
            /*
            * 使用alibaba的jsonArray的工具类，将转换格式，将map对象转换成json对象{key，value}*/
            writer.write(JSONArray.toJSONString(resultMap));
            //流处理完需要刷新并关闭
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**查询用户列表的方法*/
    public void query(HttpServletRequest req, HttpServletResponse resp){
        //查询用户列表
        List<User> userList=null;
        //1.首先查询用户的名字（从前端获取数据）
        String queryUserName = req.getParameter("queryname");
        //获取临时的内容（前端用户选择的角色）
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        //2.从userservice层获取用户列表
        UserServiceImpl userService = new UserServiceImpl();
        //第一次访问，一定是从第一页开始，页面大小是一定的(建议写入配置文件中)
        int pageSize=5;
        int currentPageNumber=1;

        int queryUserRole=0;
        if (queryUserName==null){
            queryUserName="";
        }
        //如果获取的查询用户角色不为空，且不为空白（前端是以0123来表示对应的数据）
        if (temp!=null && !temp.equals("")){
            //此处加int queryUserRole=0是为了规避temp默认值为null的情况，如果为null则进不去此判断语句
            queryUserRole=Integer.parseInt(temp);
        }
        if (pageIndex!=null){
            //如果对应的页码索引不为空，将页码索引转成int赋值给当前页码
           currentPageNumber= Integer.parseInt(pageIndex);

        }

        //3.获取用户的总数
        int totalCount= userService.getUserCount(queryUserName, queryUserRole);

        //总页数支持
        PageSupport pageSupport=new PageSupport();
        //设置当前的页码
        pageSupport.setCurrentPageNo(currentPageNumber);

        //设置页的大小
        pageSupport.setPageSize(pageSize);

        //设置页面的查询结果总数
        pageSupport.setTotalCount(totalCount);

        //总共有几页最大页数
        int totalPageCount = ((int)(totalCount/pageSize))+1;
        //4.控制首页和尾页

        if (totalPageCount<1){
            //如果用户想跳转到小于1的页面,让当前页面强制为1
            currentPageNumber=1;
        }else  if (currentPageNumber>totalPageCount){
            //如果当前页面的大小大于了总页数（也就是最后一页），强制当前页面为最后一页
            currentPageNumber=totalPageCount;
        }

        //获取用户列表展示(此处需要在请求中设置对应的属性携带信息)
       userList = userService.getUserList(queryUserName, queryUserRole, currentPageNumber, pageSize);
        req.setAttribute("userList",userList);

        //获取角色列表
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        req.setAttribute("roleList",roleList);

        //设置相应的页数与页码
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNumber);
        req.setAttribute("totalPageCount",totalPageCount);
        //设置相应的查询的名字与角色名
        req.setAttribute("queryUserName",queryUserName);
        req.setAttribute("queryUserRole",queryUserRole);
        //返回前端
        try {
            req.getRequestDispatcher("userlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
