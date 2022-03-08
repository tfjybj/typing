package com.tfjybj.typing.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;
@ApiModel(value = "IntegralRuleModel:ty_integral_rule")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class IntegralRuleModel implements Serializable {


    /**
     * 积分ID
     */
    @ApiModelProperty(value = "积分ID")
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
}
