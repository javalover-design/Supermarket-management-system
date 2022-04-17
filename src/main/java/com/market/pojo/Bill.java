package com.market.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lambda
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    /**id*/
    private Integer id;
    /**账单编码*/
    private String billCode;
    /**商品名称*/
    private String productName;
    /**商品描述*/
    private String productDesc;
    /**商品单位*/
    private  String productUnit;
    /**商品数量*/
    private BigDecimal productCount;
    /**总金额*/
    private BigDecimal totalPrice;
    /**是否支付*/
    private Integer isPayment;
    /**供应商ID*/
    private  Integer providerId;
    /**创建者*/
    private  Integer createBy;
    /**创建时间*/
    private Date creationDate;
    /**更新者*/
    private  Integer modifyBy;
    /**更新时间*/
    private  Date modifyDate;
    /**供应商名称*/
   private String providerName;



}
