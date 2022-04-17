package com.market.service.user;

import com.market.pojo.User;

import java.util.List;

/**
 * The interface User service.
 *
 * @author lambda
 */
public interface UserService {
    /**
     * 实现用户登录 @param userCode the user code
     *
     * @param userCode the user code
     * @param password the password
     * @return the user
     */
    User login(String userCode,String password);

    /**
     * 根据用户id来修改密码 @param id the id
     *
     * @param id  the id
     * @param pwd the pwd
     * @return the int
     */
    boolean upDate(int id,String pwd);

    /**
     * Gets user count.
     *
     * @param userName the user name
     * @param userRole the user role
     * @return the user count
     */
    int getUserCount(String userName,int userRole);

    /**
     * Gets user list.
     *
     * @param queryUserName the query user name
     * @param queryUserRole the query user role
     * @param currentPageNo the current page no
     * @param pageSize      the page size
     * @return the user list
     */
     List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);
}
