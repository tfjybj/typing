package com.tfjybj.typing.model;

import lombok.Data;

/**
 * @Description
 * @Author Flag
 * @Date: 2020/11/2 11:00
 * @Version: 1.0
 **/
@Data
public class TeamRankingModel {
    private String className;//班级名称
    private Integer ranking;//小组排名
    private String teamName;//小组名字
    private Double teamGrade;//小组成绩
    private String userNames;
    private Integer teamId;
    private String levels;//小组等级
    private Double  promotion;//小组的晋级率
    private String userName;
    private String userCode;
    private String level;



}
