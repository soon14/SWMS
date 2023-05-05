package com.swms.user.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 渲染模板
 * </p>
 *
 * @author sws
 * @since 2021-3-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("添加模板参数")
public class RenderTemplate extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模板名称
     */
    @ApiModelProperty(name = "name", value = "模板名称")
    private String name;


    /**
     * 模板Code
     */
    @ApiModelProperty(name = "code", value = "模板Code")
    private String code;

    /**
     * 验证schema
     */
    @ApiModelProperty(name = "validateSchema", value = "模板验证Schema")
    private String validateSchema;

    /**
     * 所属系统
     */
    @ApiModelProperty(name = "systemCode", value = "所属系统")
    private String systemCode;

    /**
     * 描述
     */
    @ApiModelProperty(name = "description", value = "描述")
    private String description;
}
