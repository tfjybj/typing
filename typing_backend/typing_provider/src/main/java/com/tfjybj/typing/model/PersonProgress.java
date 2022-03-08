package com.tfjybj.typing.model;

import lombok.Data;

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/10/27
 * Time: 19:40
 * Description:${DESCRIPTION}
 */
@Data
public class PersonProgress {

    //id
    private Integer id;
    //时间
    private String progressTime;
    //文章标题
    private String articleName;
    //速度
    private double speed;
    //正确率
    private double rightRate;
    //得分
    private double score;
    //用户名
    private String userName;
    //用户id
    private String userId;
    //称号
    private String ratingName;
    //小组
    private String teamId;
    //班级
    private String className;
    //进步率
    private String grade;

}


