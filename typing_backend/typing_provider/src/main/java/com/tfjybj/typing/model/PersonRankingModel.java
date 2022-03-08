package com.tfjybj.typing.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author Flag
 * @Date: 2020/11/4 11:26
 * @Version: 1.0
 **/
@Data
public class PersonRankingModel {
    //创建时间
    private Date createTime;
    //用户名
    private String userName;
    //班级名字
    private String className;
    //排名
    private Integer ranking;
    //积分
    private Double integral;
    //字符串时间
    private String time;
    //等级
    private  String level;

    //小组
    private String teamId;
//免费邀请次数
    private int sum;
    //邀请扣分次数
//    private String sum2;
    //学号
    private String userCode;
    //团队名称
    private String teamName;
    //创建日期
    private String createDate;
    //速度
    private String speed;
    //加分
    private String words;


}
