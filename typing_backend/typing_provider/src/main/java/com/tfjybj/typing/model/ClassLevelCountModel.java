package com.tfjybj.typing.model;

import lombok.Data;

/**
 * @Description
 * @Author 齐智
 * @Date: 2020/10/31 17:21
 * @Version: 1.0
 **/
@Data
public class ClassLevelCountModel {
    private String className;
    private String level;
    private Integer count=0;
}
