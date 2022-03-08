package com.tfjybj.typing.model;

import lombok.Data;

/**
 * @program: typing_project_backend
 * @description: 创建战队实体
 * @author: 谷浩樟
 * @create: 2019-09-24 09:22
 **/
@Data
public class CreateTeamModel {

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
    //用户Id
    private String  userId;

}
