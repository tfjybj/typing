package com.tfjybj.typing.model;

import com.dmsdbj.itoo.tool.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;

/**
 * @Description:
 * @Author: swx
 * @Date: 2021/10/21
 * @Modified By:
 * @Modify Date:
 */
@ApiModel(value = "LotteryModel:lotteryModel")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class LotteryModel extends BaseEntity {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @Column(name = "user_name")
    private String userName;

    /**
     * 学号
     */
    @ApiModelProperty(value = "学号")
    @Column(name = "user_code")
    private String userCode;

    /**
     * 抽奖结果
     */
    @ApiModelProperty(value = "抽奖结果")
    @Column(name = "level")
    private String level;

    /**
     * 抽奖总次数
     */
    @ApiModelProperty(value = "抽奖总次数")
    @Column(name = "num")
    private String num;


}
