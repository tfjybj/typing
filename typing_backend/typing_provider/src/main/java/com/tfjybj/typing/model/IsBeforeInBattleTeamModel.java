package com.tfjybj.typing.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class IsBeforeInBattleTeamModel implements Serializable {
    private Integer state;
    private Integer isCaptain;
    private String teamName;
    private String articleName;
    private Integer setTime;
}
