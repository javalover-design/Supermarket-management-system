package com.market.dao.role;

import com.market.dao.BaseDao;
import com.market.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lambda
 */
public class RoleDaoImpl implements RoleDao{
    /**获取所有的用户角色列表*/
    @Override
    public List<Role> getRoleList(Connection connection) throws SQLException {
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        ArrayList<Role> roleList = new ArrayList<>();
        if (connection!=null){
            String sql="SELECT * FROM smbms_role;";
            Object[] params={};
            resultSet = BaseDao.execute(connection, sql, preparedStatement, params);
            while (resultSet.next()){
                Role role=new Role();
                //获取所有的角色所对应的字段并赋值给role
                role.setId(resultSet.getInt("id"));
                role.setRoleName(resultSet.getString("roleName"));
                role.setRoleCode(resultSet.getString("roleCode"));
                roleList.add(role);
            }
            //操作完毕之后关闭对应的流
            BaseDao.closeResources(null,preparedStatement,resultSet);

        }
        return roleList;
    }
}
