package com.tfjybj.typing.model;

import lombok.Data;

/**
 * @program: typing_project_backend
 * @description: 战队Model
 * @author: 谷浩樟
 * @create: 2019-09-24 11:07
 **/
@Data
public class AllTeamModel {

    //用户id
    private String userId;

    //房间号
    private int teamNum;

    //战队名
    private String teamName;

    //队长名
    private String captainName;

    //文章名称
    private String articleName;

    //设定时间
    private int time;

    //房间人数
    private int personNum = 1;

    //房间状态
    private int status = 0;
}
