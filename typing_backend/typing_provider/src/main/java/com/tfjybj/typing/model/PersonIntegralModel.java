package com.tfjybj.typing.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author 齐智
 * @Date: 2020/10/21 16:45
 * @Version: 1.0
 **/
@Data
public class PersonIntegralModel implements Serializable {

    //级别
    private String level="级别获取中";
    //团队积分
    private Double teamGrade=0.0;
    //本次得分
    private Double totalGrade=0.0;
    //进步积分
    private Double progressGrade=0.0;

    private String userId;
}
