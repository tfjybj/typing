package com.tfjybj.typing.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class FinishModel implements Serializable {

    private int rank = 0;

    private String articleName;

    /**
     * 战队名
     */
    private String teamName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 打字速度
     */
    private Integer speed = 0;

    /**
     * 正确率
     */
    private Integer rightRate = 0;

    /**
     * 设定时间
     */
    private Integer setTime = 0;

    /**
     * 实际使用时间
     */
    private Integer realTime = 0;

    private Integer score = 0;

    private String userId;

}
