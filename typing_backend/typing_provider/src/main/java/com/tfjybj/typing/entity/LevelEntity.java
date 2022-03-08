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

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/11/26
 * Time: 17:57
 * Description:${DESCRIPTION}
 */
@ApiModel(value = "LevelEntity:等级评选表")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ty_level")
public class LevelEntity extends BaseEntity implements Serializable {

    ///**
    //    // * 用户id
    //    // */
    //    //@ApiModelProperty(value = "用户id")
    //    //@Column(name = "id")
    //    //private String id;

    @ApiModelProperty(value = "最低速度")
    @Column(name = "beginspeed")
    private String beginspeed;

    @ApiModelProperty(value = "最高速度")
    @Column(name = "endspeed")
    private String endspeed;

    @ApiModelProperty(value = "最低正确率")
    @Column(name = "beginaccuracy")
    private String beginaccuracy;

    @ApiModelProperty(value = "最高正确率")
    @Column(name = "endaccuracy")
    private String endaccuracy;

    @ApiModelProperty(value = "称号")
    @Column(name = "level")
    private String level;

    @ApiModelProperty(value = "是否删除")
    @Column(name = "is_delete")
    private String is_delete;

    @ApiModelProperty(value = "级差系数")
    @Column(name = "coefficient")
    private String coefficient;

    @ApiModelProperty(value = "及格分数")
    @Column(name = "qualified")
    private String qualified;

    @ApiModelProperty(value = "基础分数")
    @Column(name = "basicscore")
    private String basicscore;


}
