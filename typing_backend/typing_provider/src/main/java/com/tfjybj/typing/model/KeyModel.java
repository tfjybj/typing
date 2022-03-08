package com.tfjybj.typing.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;

@ApiModel(value = "KeyModel:ty_key")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class KeyModel implements Serializable {



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
    private String userName;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String articleName;



    /**
     * 打字速度
     */
    @ApiModelProperty(value = "打字速度")
    private Integer speed;

    /**
     * 正确率
     */
    @ApiModelProperty(value = "正确率")
    private Integer rightRate;

    /**
     * 练习时间
     */
    @ApiModelProperty(value = "练习时间")
    private Integer setTime;


    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    private String createDay;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    private String createTime;
    /**
     * 练习总时长
     */
    @ApiModelProperty(value = "练习总时长")
    private Integer num;


    /**
     * 进度
     */
    @ApiModelProperty(value = "进度")
    @Column(name = "speed_progress")
    private Integer speedProgress;

    /**
     * 练习时间转换字符串格式
     */
    private String strTime;

    /**
     * 字符串时间，创建时间
     */
    private String time;
    /**
     * 账号对应的teamName
     */
    private String teamId;
    /**
     * 查询一个账号练习的总次数
     */

    private Integer sum;
    /**
     * 查询钉钉Id
     */
    private String dingId;


}
