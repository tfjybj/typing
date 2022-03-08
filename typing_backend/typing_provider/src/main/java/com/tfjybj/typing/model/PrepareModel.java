package com.tfjybj.typing.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: typing_project_backend
 * @description: 战队准备Model
 * @author: 谷浩樟
 * @create: 2019-09-24 10:48
 **/
@Data
public class PrepareModel implements Serializable {

    //战队名称
    private String teamName;

    //用户id
    private String userId;

    //用户名称
    private String userName;
}
