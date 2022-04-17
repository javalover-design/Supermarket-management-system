package com.market.dao.role;

import com.market.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * The interface Role dao.
 *
 * @author lambda
 */
public interface RoleDao {
    /**
     * 获取角色列表 @param connection the connection
     *
     * @param connection the connection
     * @return the role list
     * @throws SQLException the sql exception
     */
    List<Role> getRoleList(Connection connection) throws SQLException;

}
