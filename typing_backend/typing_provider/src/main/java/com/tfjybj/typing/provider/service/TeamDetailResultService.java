package com.tfjybj.typing.provider.service;

import com.dmsdbj.itoo.tool.base.service.BaseServicePlus;
import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.entity.TeamDetailResultEntity;
import com.tfjybj.typing.model.AllTeamResult;
import com.tfjybj.typing.model.FinishModel;
import com.tfjybj.typing.model.TeamDetailResultModel;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;


/**
 * TeamDetailResultService接口
 * teamDetailResult表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
public interface TeamDetailResultService extends BaseServicePlus<TeamDetailResultEntity> {

    /**
     * 查询战队英雄榜
     *
     * @param
     * @return
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    List<AllTeamResult> TeamResult();

    /**
     * 查询组内成绩
     *
     * @param
     * @return List<FinishModel>
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    List<FinishModel> TeamDetailResult(String teamName);
    /**
     * 查询团队进步前十的队伍
     * @return
     */
     ItooResult selectTeamGrowthChart(List<String> className);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time:2021年10月11日10:53:12
     * @description:添加邀请助力的成绩
     */
    boolean  insertTeamDetailResult(@Param("TeamDetailResultModel") TeamDetailResultModel model);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time:2021年10月11日10:53:12
     * @description:教师端查看邀请助力的详细成绩
     */
    ItooResult selectTeamByTimeClass(String  createTime, String  endTime, String className);

    /**
     * @author: 宋文轩
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:学生端查看邀请助力的详细信息
     */
    ItooResult selectTeamByTimeAndUserCode(String beginTime, String endTime, String userCode);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:学生端查看邀请助力的次数
     */
    ItooResult selectByTimeUserCode(String  userCode, String  createTime);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端查看邀请助力的详细成绩
     */
    ItooResult selectTeamByTime(String  createTime, String  endTime);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据时间和学号查询邀请助力数据
     */
    ItooResult selectTeamByTimeUserCode(String  createTime, String  endTime,String userCode);

}
