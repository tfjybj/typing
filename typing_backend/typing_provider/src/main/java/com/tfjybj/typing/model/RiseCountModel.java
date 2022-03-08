package com.tfjybj.typing.model;

import lombok.Data;

/**
 * @Description 进级次数的实体
 * @Author 齐智
 * @Date: 2020/11/1 15:49
 * @Version: 1.0
 **/
@Data
public class RiseCountModel {
    private String level;
    private String className;
    private String userName;
    private Integer riseNumber=0;
}
