package com.tfjybj.typing.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/11/22
 * Time: 19:55
 * Description:${DESCRIPTION}
 */
@Data
public class LevelModel {
    //名称
    @ApiModelProperty(value="等级名称")
    @Column(name="levelName")
   public String levelName;
    //数量
    @ApiModelProperty(value="等级数量")
    @Column(name="leveNum")
   public String levelNum;

}
