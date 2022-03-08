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
 * TypArticle实体
 * typArticle
 *
 * @author 闫伟强 
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@ApiModel(value = "TypArticleEntity:typArticle")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "typ_article")
public class TypArticleEntity extends BaseEntity implements Serializable {

	/**
	 * 段落顺序
	 */
    @ApiModelProperty(value = "段落顺序")
	@Column(name = "child_order")
	private Integer childOrder;

	/**
	 * 文章名称或段落内容
	 */
    @ApiModelProperty(value = "文章名称或段落内容")
	@Column(name = "name")
	private String name;

	/**
	 * 父id
	 */
    @ApiModelProperty(value = "父id")
	@Column(name = "p_id")
	private String pId;


}
