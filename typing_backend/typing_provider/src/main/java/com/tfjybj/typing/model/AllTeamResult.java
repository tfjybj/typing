package com.tfjybj.typing.model;

import lombok.Data;

/**
 * @program: typing_project_backend
 * @description:
 * @author: 谷浩樟
 * @create: 2019-09-25 09:26
 **/
@Data
public class AllTeamResult {

    private int rank;

    private String articleName;

    private int rightRate =0;

    private int setTime;

    private int speed =0;

    private String teamName;

    private int teamScore =0;
}
