package com.market.service.role;

import com.market.dao.BaseDao;
import com.market.dao.role.RoleDao;
import com.market.dao.role.RoleDaoImpl;
import com.market.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lambda
 */
public class RoleServiceImpl implements RoleService{

    /**通过RoleDao来实现对数据库的操作*/
    private RoleDao roleDao;

    public RoleServiceImpl() {
        roleDao=new RoleDaoImpl();
    }

    /**获取角色的列表*/
    @Override
    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }finally {
            BaseDao.closeResources(connection,null,null);
        }

        return roleList;
    }


}
