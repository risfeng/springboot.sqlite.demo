package com.adc.sqlitedemo.repository.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ${description}
 * 
 * @author risfeng
 * @date 2019/11/02
 */
@ApiModel(value="com-adc-sqlitedemo-repository-entity-User")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @ApiModelProperty(value="null")
    private Integer id;

    @ApiModelProperty(value="null")
    private String userName;

    @ApiModelProperty(value="null")
    private String password;

    @ApiModelProperty(value="null")
    private String name;

    @ApiModelProperty(value="null")
    private String email;

    @ApiModelProperty(value="null")
    private String createdAt;

    @ApiModelProperty(value="null")
    private String createdBy;

    private static final long serialVersionUID = 1L;
}