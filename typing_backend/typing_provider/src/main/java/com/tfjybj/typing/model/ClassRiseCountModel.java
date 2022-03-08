package com.tfjybj.typing.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Flag
 * @Date: 2020/11/2 17:19
 * @Version: 1.0
 **/
@Data
public class ClassRiseCountModel  implements Serializable {
    private String className;
    private Integer riseCount;
}
