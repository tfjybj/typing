package com.tfjybj.typing.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamModel implements Serializable {
    private String teamName;
    private Integer teamNum;
    private Integer personNum;
    private String articleName;
    private Integer time;
    private String captainName;
    private Integer status;
}
