package com.tfjybj.typing.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : [LiuYanQiang]
 * @version : [v1.0]
 * @className : PersonTeamModel
 * @description : [描述说明该类的功能]
 * @createTime : [2021/9/21 21:29]
 * @updateUser : [LiuYanQiang]
 * @updateTime : [2021/9/21 21:29]
 * @updateRemark : [描述说明本次修改内容]
 */
@Data
@ApiModel(value="添加用户所需参数", description="添加用户所需参数")
public class PersonTeamModel{
    private static final long serialVersionUID = 1L;
    /**
     * 团队id
     */
    @ApiModelProperty(value = "团队id", required=true)
    private String teamId;

    /**
     * 学号
     */
    @ApiModelProperty(value = "学号", required=true)
    private String userCode;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名", required=true)
    private String userName;

    /**
     * 班级名称
     */
    @ApiModelProperty(value = "班级名称", required=true)
    private String className;
}
