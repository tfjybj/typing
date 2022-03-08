package com.tfjybj.typing.model;

import lombok.Data;

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/10/25
 * Time: 10:34
 * Description:${DESCRIPTION}
 */
@Data
public class RatingModel {
    //基础得分
    private int basicScore;
    //称号
    private String ratingName;
    //动态得分
    private double dynamicScore;
}
