package com.tfjybj.typing.model;

import lombok.Data;

/**
 * @Description
 * @Author 齐智
 * @Date: 2020/10/31 17:28
 * @Version: 1.0
 **/
@Data
public class ClassLevelModel {
    private String className;
    private Integer beginnerCount=0;
    private Integer primaryCount=0;
    private Integer seniorCount=0;
    private Integer keyManCount=0;
}
