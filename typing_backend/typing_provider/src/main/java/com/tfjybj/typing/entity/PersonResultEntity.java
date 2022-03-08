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
 * PersonResult实体
 * personResult
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@ApiModel(value = "PersonResultEntity:personResult")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "person_result")
public class PersonResultEntity extends BaseEntity implements Serializable {

	/**
	 * 文章名称
	 */
    @ApiModelProperty(value = "文章名称")
	@Column(name = "article_name")
	private String articleName;

	/**
	 * 用户id
	 */
    @ApiModelProperty(value = "用户id")
	@Column(name = "user_id")
	private String userId;

	/**
	 * 用户名称
	 */
    @ApiModelProperty(value = "用户名称")
	@Column(name = "user_name")
	private String userName;

	/**
	 * 字数
	 */
    @ApiModelProperty(value = "字数")
	@Column(name = "words")
	private Integer words;

	/**
	 * 正确字数
	 */
    @ApiModelProperty(value = "正确字数")
	@Column(name = "right_words")
	private Integer rightWords;

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
	 * 班级名称
	 */
	@ApiModelProperty(value = "班级名称")
	@Column(name = "class_name")
	private String className;


}
