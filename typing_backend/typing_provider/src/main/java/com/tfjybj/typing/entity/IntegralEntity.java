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
 * Integral实体
 * 积分表
 *
 * @author Flag 
 * @version ${version}
 * @since ${version} 2020-10-26 08:25:40
 */
@ApiModel(value = "IntegralEntity:积分表")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ty_integral")
public class IntegralEntity extends BaseEntity implements Serializable {

	/**
	 * 用户id
	 */
    @ApiModelProperty(value = "用户id")
	@Column(name = "user_id")
	private String userId;

	/**
	 * 用户名
	 */
    @ApiModelProperty(value = "用户名")
	@Column(name = "user_name")
	private String userName;

	/**
	 * 级别
	 */
    @ApiModelProperty(value = "级别")
	@Column(name = "level")
	private String level;

	/**
	 * 基础分
	 */
    @ApiModelProperty(value = "基础分")
	@Column(name = "base_grade")
	private Integer baseGrade;

	/**
	 * 动态加分
	 */
    @ApiModelProperty(value = "动态加分")
	@Column(name = "bonus_grade")
	private Double bonusGrade;

	/**
	 * 本次得分
	 */
    @ApiModelProperty(value = "本次得分")
	@Column(name = "total_grade")
	private Double totalGrade;

	/**
	 * 积分进步
	 */
    @ApiModelProperty(value = "积分进步")
	@Column(name = "progress_grade")
	private Double progressGrade;


}
