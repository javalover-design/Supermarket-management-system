package com.market.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lambda
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    /**对应的id*/
    private Integer id;
    /**角色编码*/
    private String roleCode;
    /**角色名称*/
    private  String roleName;
    /**创建者*/
    private  Integer createdBy;
    /**创建时间*/
    private Date creationDate;
    /**更新者*/
    private  Integer modifyBy;
    /**更新时间*/
    private Date modifyDate;

}
