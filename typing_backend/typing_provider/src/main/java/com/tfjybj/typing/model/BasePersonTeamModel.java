package com.tfjybj.typing.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author : [LiuYanQiang]
 * @version : [v1.0]
 * @className : BaseBatchInsertPersonTeam
 * @description : [批量添加请求类]
 * @createTime : [2021/9/21 21:22]
 * @updateUser : [LiuYanQiang]
 * @updateTime : [2021/9/21 21:22]
 * @updateRemark : [描述说明本次修改内容]
 */
@Data
@ApiModel(value="批量添加参数", description="批量添加参数")
public class BasePersonTeamModel {
    @ApiModelProperty(value = "要批量添加的数据列表", required = true)
    private List<PersonTeamModel> personTeamModel;
}
