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
 * Team实体
 * 团队表
 *
 * @author Flag 
 * @version ${version}
 * @since ${version} 2020-10-26 08:25:40
 */
@ApiModel(value = "TeamEntity:团队表")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ty_team")
public class TeamEntity extends BaseEntity implements Serializable {

	/**
	 * 团队id
	 */
    @ApiModelProperty(value = "团队id")
	@Column(name = "team_id")
	private String teamId;

	/**
	 * 团队名字
	 */
    @ApiModelProperty(value = "团队名字")
	@Column(name = "team_name")
	private String teamName;

	/**
	 * 排名
	 */
    @ApiModelProperty(value = "排名")
	@Column(name = "ranking")
	private Integer ranking;

	/**
	 * 小组成绩
	 */
    @ApiModelProperty(value = "小组成绩")
	@Column(name = "group_results")
	private Double groupResults;


}
