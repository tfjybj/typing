package com.tfjybj.typing.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;
import javax.persistence.Column;

/**
 * TypArticleModel
 * typArticle
 *
 * @author 闫伟强 
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@ApiModel(value = "TypArticleModel:typArticle")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class TypArticleModel implements Serializable {

    //region 模板逆向生产来自TypArticleEntity的属性
	/**
	 * id
	 */
	@ApiModelProperty(value = "typArticle主键")
	@Column(name = "id")
	private String id;
   	/**
	 * 段落顺序
	 */
    @ApiModelProperty(value = "段落顺序")
	private Integer childOrder;

	/**
	 * 文章名称或段落内容
	 */
    @ApiModelProperty(value = "文章名称或段落内容")
	private String name;

	/**
	 * 父id
	 */
    @ApiModelProperty(value = "父id")
	private String pId;

    //endregion

     /* *****************************以下是非模板生成的内容************************************ */
}
