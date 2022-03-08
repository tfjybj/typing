package com.tfjybj.typing.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.dmsdbj.itoo.tool.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;

@ApiModel(value = "KeyEntity:ty_key")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ty_key")
public class KeyEntity extends BaseEntity implements Serializable {

    /**
     * 学号
     */
    @ApiModelProperty(value = "学号")
    @Column(name = "user_code")
    private String userCode;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    @Column(name = "user_name")
    private String userName;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    @Column(name = "article_name")
    private String articleName;
    /**
     * 打字速度
     */
    @ApiModelProperty(value = "打字速度")
    @Column(name = "speed")
    private Integer speed;

    /**
     * 正确率
     */
    @ApiModelProperty(value = "正确率")
    @Column(name = "right_rate")
    private Integer rightRate;

    /**
     * 练习时间
     */
    @ApiModelProperty(value = "练习时间")
    @Column(name = "set_time")
    private Integer setTime;

    /**
     * 进度
     */
    @ApiModelProperty(value = "进度")
    @Column(name = "speed_progress")
    private Integer speedProgress;

}
