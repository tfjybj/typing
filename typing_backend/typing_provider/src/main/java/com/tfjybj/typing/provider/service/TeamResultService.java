package com.tfjybj.typing.provider.service;

import com.dmsdbj.itoo.tool.base.service.BaseServicePlus;
import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.entity.TeamResultEntity;
import com.tfjybj.typing.model.CreateTeamModel;
import com.tfjybj.typing.model.FinishModel;
import com.tfjybj.typing.model.IsBeforeInBattleTeamModel;


/**
 * TeamResultService接口
 * teamResult表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
public interface TeamResultService extends BaseServicePlus<TeamResultEntity> {

    /**
     * 是否之前在战队中
     *
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    IsBeforeInBattleTeamModel IsBeforeInBattleTeam(String userId);

    /**
     * 组队竞赛初始化
     *
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    ItooResult loadBattleTeamCard(String userId);

    /**
     * 完成比赛
     *
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    void FinalBattle(FinishModel finishModel);

    /**
     * 开始比赛
     *
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    void BeginBattle(String teamName);

    /**
     * 解散战队
     *
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    void BreakBattleTeam(String teamName);

    /**
     * 房间界面初始化
     *
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    ItooResult TeamByUserId(String userId);

    /**
     * 查询队员
     *
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    ItooResult findBattleMember(String teamName);

    /**
     * 快速进入房间（输入房间）
     *
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    ItooResult joinInBattleTeamByRoomId(String userId,Integer teamNums,String userName);

    /**
     * 创建战队
     *
     * @param createTeamModel 创建实体
     * @return flag 创建战队是否成功
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    boolean CreateBattleTeam(String userId, CreateTeamModel createTeamModel);

    /**
     * 退出战队
     *
     * @param teamName 战队名称
     * @return flag 退出战队
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    boolean QuitBattleTeam(String teamName,String userId);

    /**
     * 打字页面初始化插入成绩
     *
     * @param finishModel 成绩实体
     * @return flag 插入成绩是否成功
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    boolean BattleInfo(String userId,FinishModel finishModel);
}
