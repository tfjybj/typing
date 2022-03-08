package com.tfjybj.typing.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dmsdbj.itoo.tool.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;
import javax.persistence.Column;

/**
 * TeamResult实体
 * teamResult
 *
 * @author 闫伟强 
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@ApiModel(value = "TeamResultEntity:teamResult")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "team_result")
public class TeamResultEntity extends BaseEntity implements Serializable {

	/**
	 * 战队名称
	 */
    @ApiModelProperty(value = "战队名称")
	@Column(name = "team_name")
	private String teamName;

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
	 * 设定时间
	 */
    @ApiModelProperty(value = "设定时间")
	@Column(name = "set_time")
	private Integer setTime;

	/**
	 * 文章名称
	 */
    @ApiModelProperty(value = "文章名称")
	@Column(name = "article_name")
	private String articleName;

	/**
	 * 战队成绩
	 */
    @ApiModelProperty(value = "战队成绩")
	@Column(name = "team_score")
	private Integer teamScore;


}
