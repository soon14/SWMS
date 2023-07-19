//package com.swms.user.repository.entity;
//
//import java.io.Serializable;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;
//
///**
// * <p>
// * 授权客户端表
// * </p>
// *
// * @author sws
// * @since 2020-12-31
// */
//@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
//public class OauthClientDetails extends BaseEntity implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 客户端ID
//     */
//    @TableId(value = "id", type = IdType.AUTO)
//    @ApiModelProperty("id")
//    private Long id;
//
//    @ApiModelProperty("客户端Id")
//    private String clientId;
//
//    /**
//     * 名称
//     */
//    @ApiModelProperty("名称")
//    private String name;
//
//    /**
//     * 资源ID集合,多个资源时用逗号(,)分隔
//     */
//    @ApiModelProperty("资源ID集合,多个资源时用逗号(,)分隔")
//    private String resourceIds;
//
//    /**
//     * 客户端密匙
//     */
//    @ApiModelProperty("客户端密匙")
//    private String clientSecret;
//
//    /**
//     * 客户端申请的权限范围
//     */
//    @ApiModelProperty("客户端申请的权限范围")
//    private String scope;
//
//    /**
//     * 客户端支持的grant_type
//     */
//    @ApiModelProperty("客户端支持的grant_type")
//    private String authorizedGrantTypes;
//
//    /**
//     * 重定向URI
//     */
//    @ApiModelProperty("重定向URI")
//    private String webServerRedirectUri;
//
//    /**
//     * 客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔
//     */
//    @ApiModelProperty("客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔")
//    private String authorities;
//
//    /**
//     * 访问令牌有效时间值(单位:秒)
//     */
//    @ApiModelProperty("访问令牌有效时间值(单位:秒)")
//    private Integer accessTokenValidity;
//
//    /**
//     * 更新令牌有效时间值(单位:秒)
//     */
//    @ApiModelProperty("更新令牌有效时间值(单位:秒)")
//    private Integer refreshTokenValidity;
//
//    /**
//     * 预留字段
//     */
//    @ApiModelProperty("预留字段")
//    private String additionalInformation;
//
//    /**
//     * 用户是否自动Approval操作
//     */
//    @ApiModelProperty("用户是否自动Approval操作")
//    private String autoapprove;
//
//    /**
//     * 描述
//     */
//    @ApiModelProperty("描述")
//    private String description;
//}
