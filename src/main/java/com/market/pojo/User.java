package com.market.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lambda
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**对应的id*/
    private int id ;
    /**用户的编码*/
    private String userCode;
    /**用户名称*/
    private String userName;
    /**用户的密码*/
    private String userPassword;
    /**性别*/
    private  Integer gender;
    /**出生日期*/
    private Date birthday;
    /**电话*/
    private String phone;
    /**地址*/
    private String address;
    /**用户角色*/
    private  Integer userRole;
    /**创建者*/
    private  Integer createdBy;
    /**创建时间*/
    private Date creationDate;
    /**更新者*/
    private  Integer modifyBy;
    /**更新时间*/
    private Date modifyDate;
    /**年龄*/
    private  Integer age;
    /**用户角色名称*/
    private String userRoleName;

    public String getUserRoleName(){return userRoleName;}
    public void setUserRoleName(String userRoleName){
        this.userRoleName=userRoleName;
    }
    public Integer getAge(){
        Date date=new Date();
        Integer age=date.getYear()-birthday.getYear();
        return age;
    }
}
