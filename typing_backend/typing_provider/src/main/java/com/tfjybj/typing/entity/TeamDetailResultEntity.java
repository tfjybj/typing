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
 * TeamDetailResult实体
 * teamDetailResult
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@ApiModel(value = "TeamDetailResultEntity:teamDetailResult")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "team_detail_result")
public class TeamDetailResultEntity extends BaseEntity implements Serializable {

	/**
	 * 战队id
	 */
    @ApiModelProperty(value = "战队id")
	@Column(name = "team_id")
	private String teamId;

	/**
	 * 用户姓名
	 */
    @ApiModelProperty(value = "用户姓名")
	@Column(name = "user_name")
	private String userName;

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
	 * 实际使用时间
	 */
    @ApiModelProperty(value = "实际使用时间")
	@Column(name = "real_time")
	private Integer realTime;

	/**
	 * 文章名称
	 */
    @ApiModelProperty(value = "文章名称")
	@Column(name = "article_name")
	private String articleName;

	/**
	 * 个人成绩
	 */
    @ApiModelProperty(value = "个人成绩")
	@Column(name = "score")
	private Integer score;



	/**
	 * 创建时间
	 */
//	@ApiModelProperty(value = "设定时间")
//	@Column(name = "create_time")
//	private String  createTime;

	/**
	 * 学号
	 */
	@ApiModelProperty(value = "学号")
	@Column(name = "user_code")
	private String userCode;


	/**
	 * 战队名称
	 */
	@ApiModelProperty(value = "战队名称")
	@Column(name = "team_name")
	private String teamName;


	/**
	 * 助力成绩
	 */
	@ApiModelProperty(value = "助力成绩")
	@Column(name = "improved_score")
	private Integer improvedScore;

}
