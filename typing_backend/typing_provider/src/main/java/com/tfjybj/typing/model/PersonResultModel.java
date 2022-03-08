package com.tfjybj.typing.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * PersonResultModel
 * personResult
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@ApiModel(value = "PersonResultModel:personResult")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class PersonResultModel implements Serializable {

    //region 模板逆向生产来自PersonResultEntity的属性
	/**
	 * id
	 */
	@ApiModelProperty(value = "personResult主键")
	@Column(name = "id")
	private String id;
   	/**
	 * 文章名称
	 */
    @ApiModelProperty(value = "文章名称")
	private String articleName;

	/**
	 * 用户id
	 */
    @ApiModelProperty(value = "用户id")
	private String userId;

	/**
	 * 用户名称
	 */
    @ApiModelProperty(value = "用户名称")
	private String userName;

	/**
	 * 字数
	 */
    @ApiModelProperty(value = "字数")
	private Integer words;

	/**
	 * 正确字数
	 */
    @ApiModelProperty(value = "正确字数")
	private Integer rightWords;

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
	 * 班级名称
	 */
	@ApiModelProperty(value = "班级名称")
	@Column(name = "class_name")
	private String className;
	/**
	 * 打字次数
	 */
	@ApiModelProperty(value = "打字次数")
	@Column(name = "num")
	private String num;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private String createTime;


	/**
	 * 平均速度
	 */
	@ApiModelProperty(value = "平均速度")
	private String speedAvg;

	/**
	 * 平均正确率
	 */
	@ApiModelProperty(value = "平均正确率")
	private String rightRateAvg;


    //endregion

     /* *****************************以下是非模板生成的内容************************************ */
}
