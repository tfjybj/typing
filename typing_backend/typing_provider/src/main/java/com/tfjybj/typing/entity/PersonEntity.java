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
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Person实体
 * 人员团队关系表
 *
 * @author Flag 
 * @version ${version}
 * @since ${version} 2020-10-26 08:25:40
 */
@ApiModel(value = "PersonEntity:人员团队关系表")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ty_person")
@Entity
public class PersonEntity extends BaseEntity implements Serializable {

	/**
	 * 团队id
	 */
    @ApiModelProperty(value = "团队id")
	@Column(name = "team_id")
	private String teamId;

	/**
	 * 学号
	 */
    @ApiModelProperty(value = "学号")
	@Column(name = "user_code")
	private String userCode;

	/**
	 * 用户姓名
	 */
    @ApiModelProperty(value = "用户姓名")
	@Column(name = "user_name")
	private String userName;

	/**
	 * 班级名称
	 */
	@ApiModelProperty(value = "班级名称")
	@Column(name = "class_name")
	private String className;

	///**
	//* 用户id
	//*/
	//
	//@ApiModelProperty(value = "tc_allUser表中的id")
	//@Column(name="id")
	//private String id;

}
