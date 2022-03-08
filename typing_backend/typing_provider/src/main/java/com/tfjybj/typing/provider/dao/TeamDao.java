package com.tfjybj.typing.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.entity.TeamEntity;
import com.tfjybj.typing.model.TeamGrowthChartModel;
import com.tfjybj.typing.model.TeamRankingModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author 齐智
 * @Date: 2020/10/26 10:14
 * @Version: 1.0
 **/
@Repository("teamDao")
public interface TeamDao extends BaseMapper<TeamEntity> {
    Integer updateTeamIsDelete(String teamId);


    Integer insertTeam(TeamEntity teamEntity);

    TeamEntity selectTeamById(String teamId);


    Double selectTeamResultByUserId(String  userId);

    List<TeamRankingModel> selectTeamRanking(@Param("className") List<String> className);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:查询全部未练习人员
     */
    List<TeamRankingModel> selectAllNotRanking();
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:根据班级查询全部未练习人员
     */
    List<TeamRankingModel> selectAllNotRankingByClass(String className);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:查询全部积分排名-AR奖状接口
     */
    List<TeamRankingModel> selectAllTeamRanking();
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:根据班级查询积分排名--查询晋级榜单
     */
    List<TeamRankingModel> selectAllTeamRankingByClass( String  className);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月15日
     * @time: 2021年11月15日11:35:01
     * @description:根据等级查询积分排名-晋级榜单
     */
    List<TeamRankingModel> selectAllTeamRankingByLevel( String  level);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月15日
     * @time: 2021年11月15日11:35:01
     * @description:根据班级和等级查询积分排名-晋级榜单
     */
    List<TeamRankingModel> selectAllTeamRankingByClassLevel(String  className, String  level);

    /**
     * 查询团队的进步记录
     * @return
     */
    List<TeamGrowthChartModel> selectTeamGrowthChart(@Param("className") List<String> className);
}
