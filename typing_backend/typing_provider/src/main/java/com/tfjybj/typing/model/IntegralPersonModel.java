package com.tfjybj.typing.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;


@ApiModel(value = "IntegralPersonModel:ty_person_integral")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class IntegralPersonModel implements Serializable {


    /**
     * 学号
     */
    @ApiModelProperty(value = "学号")
    @Column(name = "user_code")
    private String userCode;


    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    @Column(name = "user_name")
    private String userName;

    /**
     * 积分ID
     */
    @ApiModelProperty(value = "积分Id")
    @Column(name = "integral_id")
    private String integralId;

    /**
     * 积分来源
     */
    @ApiModelProperty(value = "积分来源")
    @Column(name = "integral_source")
    private String integralSource;


    /**
     * 来源细分
     */
    @ApiModelProperty(value = "来源细分")
    @Column(name = "small_source")
    private String smallSource;


    /**
     * 积分奖励
     */
    @ApiModelProperty(value = "积分奖励")
    @Column(name = "integral_reward")
    private String integralReward;


    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @Column(name = "create_day")
    private String createDay;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private String createTime;

    /**
     * 班级名称
     */
    @ApiModelProperty(value = "班级名称")
    @Column(name = "className")
    private String className;

}
