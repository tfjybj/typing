package com.tfjybj.typing.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BattleMember implements Serializable {
    private Integer state;
    private List<UserNameModel> isBeforeInBattleTeamModels;
}
