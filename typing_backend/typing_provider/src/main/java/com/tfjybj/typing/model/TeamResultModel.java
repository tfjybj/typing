package com.tfjybj.typing.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;
import javax.persistence.Column;

/**
 * TeamResultModel
 * teamResult
 *
 * @author 闫伟强 
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@ApiModel(value = "TeamResultModel:teamResult")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class TeamResultModel implements Serializable {

    //region 模板逆向生产来自TeamResultEntity的属性
	/**
	 * id
	 */
	@ApiModelProperty(value = "teamResult主键")
	@Column(name = "id")
	private String id;
   	/**
	 * 战队名称
	 */
    @ApiModelProperty(value = "战队名称")
	private String teamName;

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
	 * 设定时间
	 */
    @ApiModelProperty(value = "设定时间")
	private Integer setTime;

	/**
	 * 文章名称
	 */
    @ApiModelProperty(value = "文章名称")
	private String articleName;

	/**
	 * 战队成绩
	 */
    @ApiModelProperty(value = "战队成绩")
	private Integer teamScore;

    //endregion

     /* *****************************以下是非模板生成的内容************************************ */
}
