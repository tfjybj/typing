package com.tfjybj.typing.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * TeamDetailResultModel
 * teamDetailResult
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@ApiModel(value = "TeamDetailResultModel:teamDetailResult")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class TeamDetailResultModel implements Serializable {

    //region 模板逆向生产来自TeamDetailResultEntity的属性
	/**
	 * id
	 */
	@ApiModelProperty(value = "teamDetailResult主键")
	@Column(name = "id")
	private String id;
   	/**
	 * 战队id
	 */
    @ApiModelProperty(value = "战队id")
	private String teamId;

	/**
	 * 用户姓名
	 */
    @ApiModelProperty(value = "用户姓名")
	private String userName;

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
	 * 实际使用时间
	 */
    @ApiModelProperty(value = "实际使用时间")
	private Integer realTime;

	/**
	 * 文章名称
	 */
    @ApiModelProperty(value = "文章名称")
	private String articleName;

	/**
	 * 个人成绩
	 */
    @ApiModelProperty(value = "个人成绩")
	private Integer score;

	/**
	 * 邀请助力的次数
	 */
	@ApiModelProperty(value = "邀请助力的次数")
	private Integer num;

    //endregion

     /* *****************************以下是非模板生成的内容************************************ */

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;


	/**
	 * 学号
	 */
	@ApiModelProperty(value = "学号")
	private String userCode;

	/**
	 * 战队名称
	 */
	@ApiModelProperty(value = "战队名称")
	private String teamName;


	/**
	 * 助力成绩
	 */
	@ApiModelProperty(value = "助力成绩")
	private Integer improvedScore;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private String  time;




}
