package com.tfjybj.typing.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/11/14
 * Time: 16:50
 * Description:${DESCRIPTION}
 */
@Data
public class GradeModel {
    @ApiModelProperty(value="用户名")
    @Column(name="user_name")
    private String userName;

    @ApiModelProperty(value="等级")
    @Column(name="level")
    private String level;

    @ApiModelProperty(value="获得某等级的次数")
    @Column(name="num")
    private String num;
}
