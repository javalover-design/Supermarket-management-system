package com.market.dao.user;

import com.market.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * The interface User dao.
 */
public interface UserDao {
    /**
     * Gets login user.
     *
     * @param connection the connection
     * @param userCode   the user code
     * @return the login user
     * @throws SQLException the sql exception
     */
    User getLoginUser(Connection connection,String userCode) throws SQLException;

    /**
     * Up date pwd int.
     *
     * @param connection the connection
     * @param id         the id
     * @param password   the password
     * @return the int
     * @throws SQLException the sql exception
     */
    int upDatePwd( Connection connection,int id,String password) throws SQLException;

    /**
     * 查询用户总数 @param connection the connection
     *
     * @param connection the connection
     * @param userName   the user name
     * @param userRole   the user role
     * @return the user count
     */
    int getUserCount(Connection connection,String userName,int userRole);

    /**
     * 通过条件查询用户列表-userList
     *
     * @param connection    the connection
     * @param userName      the user name
     * @param userRole      the user role
     * @param currentPageNo the current page no
     * @param pageSize      the page size
     * @return user list
     * @throws Exception the exception
     */
    List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize)throws Exception;



}
