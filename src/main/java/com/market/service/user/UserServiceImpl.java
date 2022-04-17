package com.market.service.user;

import com.market.dao.BaseDao;
import com.market.dao.user.UserDao;
import com.market.dao.user.UserDaoImpl;
import com.market.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lambda
 */
public class UserServiceImpl implements UserService {
    /**业务层都会调用dao层*/
    private UserDao userDao;
    public UserServiceImpl(){
        //业务层一实现就创建了数据库层的对象
        userDao=new UserDaoImpl();
    }
    @Override
    public User login(String userCode, String password) {
        Connection connection=null;
        User user=null;
        connection=BaseDao.getConnection();
        try {
            //调用dao层进行查询。这个userCode和password都是从前端传过来的
            user=userDao.getLoginUser(connection,userCode);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }finally {
            BaseDao.closeResources(connection,null,null);
        }
    return user;
    }

    @Override
    public boolean upDate(int id, String pwd) {
        //创建连接
        Connection connection=null;
        boolean flag=false;
        //获取连接
        connection = BaseDao.getConnection();
        try {
            int i = userDao.upDatePwd(connection, id, pwd);
            if (i>0){
                flag=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //处理完毕之后需要关闭连接
            BaseDao.closeResources(connection,null,null);
        }
    return flag;
    }

    @Override
    public int getUserCount(String userName, int userRole) {
        //需要在业务层创建相应的连接
        Connection connection = null;
        connection=BaseDao.getConnection();
        int userCount = userDao.getUserCount(connection, userName, userRole);
        BaseDao.closeResources(connection,null,null);
        return userCount;

    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection, queryUserName,queryUserRole,currentPageNo,pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResources(connection,null,null);
        }
        return userList;
    }


}
