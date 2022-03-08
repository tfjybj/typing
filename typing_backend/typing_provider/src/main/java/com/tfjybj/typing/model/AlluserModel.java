package com.tfjybj.typing.model;

import lombok.Data;

/**
 * @program: typing_project_backend
 * @description: AlluserModel
 * @author: 谷浩樟
 * @create: 2019-09-24 11:08
 **/
@Data
public class AlluserModel {

    //用户id
    private String userId;

    //战队名称
    private String teamName;

    //是否为队长(1是，0不是)
    private int isCaptain = 1;
}
