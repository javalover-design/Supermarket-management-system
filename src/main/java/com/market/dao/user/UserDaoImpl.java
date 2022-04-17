package com.market.dao.user;

import com.market.dao.BaseDao;
import com.market.pojo.User;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lambda
 */
public class UserDaoImpl  implements UserDao{
    /**实现用户登录操作*/
    @Override
    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        ResultSet resultSet=null;
        User user=null;
        PreparedStatement preparedStatement=null;
        //编写查询语句，具体的执行交给公共类，BaseDao
        String sql="SELECT * FROM smbms_user WHERE userCode=?";
        if (connection!=null){
                resultSet = BaseDao.execute(connection, sql, preparedStatement,userCode);
                if (resultSet.next()){
                    user=new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUserCode(resultSet.getString("userCode"));
                    user.setUserName(resultSet.getString("userName"));
                    user.setGender(resultSet.getInt("gender"));
                    user.setUserPassword(resultSet.getString("userPassword"));
                    user.setBirthday(resultSet.getDate("birthday"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setAddress(resultSet.getString("address"));
                    user.setUserRole(resultSet.getInt("userRole"));
                    user.setCreatedBy(resultSet.getInt("createdBy"));
                    user.setCreationDate(resultSet.getTimestamp("creationDate"));
                    user.setModifyBy(resultSet.getInt("modifyBy"));
                    user.setModifyDate(resultSet.getTimestamp("modifyDate"));

                }
                BaseDao.closeResources(null,preparedStatement,resultSet);
        }
        return user;
    }

    /**实现用户的修改密码操作*/
    @Override
    public int upDatePwd(Connection connection,int id, String password) throws SQLException {
        int update=0;
        if (connection!=null) {
            //首先修改密码需要编写修改的sql语句
            String sql = "UPDATE smbms_user SET userPassword=? WHERE id=?;";
            //之后还需要创建一个预编译语句
            PreparedStatement preparedStatement = null;

            //执行一个修改语句
             update = BaseDao.Update(connection, sql, id, password);
            BaseDao.closeResources(null, preparedStatement, null);

        }
        return update;
    }

    /**根据用户名或者角色查询用户总数*/
    @Override
    public int getUserCount(Connection connection, String userName, int userRole) {
        //由于需要查询，所以需要预编译语句或者结果集
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int count=0;
            if (connection!=null) {
                StringBuffer sql = new StringBuffer();
                sql.append("SELECT COUNT(1) as count FROM smbms_user u,smbms_role r WHERE u.userRole=r.id");

                //由于此处传入的参数需要在baseDao中的方法中作为形参传递，那么需要将参数以List的形式存储
                ArrayList<Object> list = new ArrayList<>();
                //此处需要进行判断
                if (!StringUtils.isNullOrEmpty(userName)) {
                    //如果传入的用户名不为空，需要在全部查询的基础上添加查询条件
                    sql.append(" AND u.userName like ?");
                    //添加%是为了后期的模糊查询操作
                    list.add("%" + userName + "%");//index：0

                }
                //表示用户角色
                if (userRole > 0) {
                    sql.append(" AND u.userRole=?");
                    list.add(userRole); //index：1

                }
                //将list转换成为一个数组
                Object[] params = list.toArray();
                System.out.println("UserDaoImpl->"+sql.toString());

                try {
                    resultSet = BaseDao.execute(connection, sql.toString(), preparedStatement, params);
                    if (resultSet.next()){
                        //如果结果集中有内容
                       count= resultSet.getInt("count");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                //关闭资源
                BaseDao.closeResources(null,preparedStatement,resultSet);
            }

            return count;
    }

    /**获取所有的用户列表并实现分页*/
    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");

            }
            if(userRole > 0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            sql.append(" order by r.creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, sql.toString(), pstm,params);
            while(rs.next()){
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString("userName"));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setPhone(rs.getString("phone"));
                _user.setUserRole(rs.getInt("userRole"));
                _user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDao.closeResources(null,pstm,rs);
        }
        return userList;
    }




}
