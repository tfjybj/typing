package com.tfjybj.typing.model;

import lombok.Data;

/**
 * @Description
 * @Author Flag
 * @Date: 2020/11/3 11:38
 * @Version: 1.0
 **/
@Data
public class TeamGrowthChartModel {
    private Integer teamId;
    private String teamName;
    private Double growthIntegral;
    private String className;
}
