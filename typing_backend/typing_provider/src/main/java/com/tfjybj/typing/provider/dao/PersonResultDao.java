package com.tfjybj.typing.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.entity.PersonResultEntity;
import com.tfjybj.typing.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PersonResultDao接口
 * personResult表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@Repository("personResultDao")
public interface PersonResultDao extends BaseMapper<PersonResultEntity> {
    /**
     * 添加打字记录
     *
     * @param
     * @return
     * @author 王闯
     * @since 2019年9月21日14:40:31
     */
    void insertPersonResult(@Param(value = "personResultEntity") PersonResultEntity personResultEntity);

    /**
     * 查询学生个人成绩
     *
     * @param
     * @return List<PersonResultModel>
     * @author 王闯
     * @since 2019年9月21日14:40:31
     */
    List<PersonResultEntity> selectPersonResult(String userId);

    /**
     * 查询个人打字速度以及正确率
     *
     * @param userId
     * @return List<PersonResultEntity>
     * @author Yara
     * @date 2020年10月26日10:43:49
     */
    List<PersonResultEntity> selectPersonRAS(String userId);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月26日11:37:55
     * @time: 2021年10月26日
     * @description:根据查询个人打字速度和正确率
     */
    List<PersonResultModel> selectPersonRateAndSpeedAVG(String userCode);


    /**
     * 查询最近一次该用户的打字结果
     *
     * @param userId 用户id
     * @return
     */
    PersonResultEntity selectOneNewPersonResultById(String userId);

    /**
     * @author: 郝龙飞
     * @version:
     * @param: []
     * @date: 2020/10/27
     * @time: 19:37
     * @description: 进步排名
     */
    List<PersonProgress> selectPersonProgress(Integer pageNo, Integer pageSize, @Param("flag") Integer flag);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:根据班级查询排行榜
     */
    List<PersonProgress> selectPersonClassProgress(Integer pageNo, Integer pageSize, String className);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月27日
     * @time: 2021年9月27日08:28:46
     * @description:根据班级查询进步，班级模糊查询
     */
    List<PersonProgress> selectPersonClassProgressClass(Integer pageNo, Integer pageSize);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:
     */
    List<PersonProgress> selectPersonTeamProgress(Integer pageNo, Integer pageSize, String teamId,String className);

    /**
     * @author: 郝龙飞
     * @version:
     * @param: []
     * @date: 2020/10/27
     * @time: 19:38
     * @description: 整体排名
     */
    List<PersonProgress> selectPersonRanking(Integer pageNo, Integer pageSize, @Param("flag") Integer flag);

    /**
     * @author: 张艳伟
     * @version:根据班级查询进步英雄榜排名
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:
     */
    List<PersonProgress> selectPersonClassRanking(Integer pageNo, Integer pageSize, String className);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月27日
     * @time: 2021年9月27日08:32:02
     * @description:
     */
    List<PersonProgress> selectPersonClassRankingClass(Integer pageNo, Integer pageSize);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:
     */
    List<PersonProgress> selectPersonTeamRanking(Integer pageNo, Integer pageSize, String teamId,String className);

    /**
     * 查询未播报记录
     *
     * @author Yara
     * @date 2020年11月1日15:41:01
     */
    List<ImproveReportModel> selectNoReport();

    /**
     * 更新查询过的人员名单
     *
     * @param list（查询过的人员名单）
     * @return 更改条数
     */
    void UpdateImprovement(List<ImproveReportModel> list);


    List<ClassLevelCountModel> selectClassLevelCount(@Param("className")List<String> className);

    /**
     * 查询进级让你输
     *
     * @param userId 如果传递用户id，则查询该用户的，查询全部则，需要传null
     * @return
     */
    List<RiseCountModel> selectRiseCount(@Param("userId") String userId);

    List<PersonRankingModel> selectRankingByTimeLevel(@Param("beginTime") String beginTime,
                                                      @Param("endTime") String endTime,
                                                      @Param("level") String level,
                                                      @Param("className") String className);
    /**
     * @author: 张艳伟
     * @version:按照条件查询成绩，班级模糊查询
     * @param: []
     * @date: 2021年9月26日
     * @time: 2021年9月26日19:54:10
     * @description:
     */
    List<PersonRankingModel> selectRankingByTimeLevelClass(@Param("beginTime") String beginTime,
                                                      @Param("endTime") String endTime,
                                                      @Param("level") String level
                                                      );

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月22日
     * @time: 2021年9月22日08:08:35
     * @description:
     */
    List<PersonRankingModel> selectRankingByTimeLevelAll(@Param("beginTime") String beginTime,
                                                         @Param("endTime") String endTime,
                                                         @Param("className") String className);
    /**
     * @author: 宋文轩
     * @version:
     * @param: []
     * @date: 2021年10月5日
     * @time: 2021年10月5日19:37:13
     * @description:
     */
    int selectImprovedByTime(@Param("beginTime") String beginTime,
                             @Param("endTime") String endTime,
                             @Param("userId") String userId);

    List<PersonRankingModel> selectImproved(@Param("beginTime") String beginTime,
                                                              @Param("endTime") String endTime);

    List<PersonRankingModel> selectAllImproved(@Param("beginTime") String beginTime,
                                            @Param("endTime") String endTime);
    List<PersonResultModel> selectNumByTime(@Param("beginTime") String beginTime,
                                            @Param("endTime") String endTime,
                                             @Param("userCode") String userCode);
    List<PersonRankingModel> selectImprovedByClass(@Param("beginTime") String beginTime,
                                            @Param("endTime") String endTime,
                                                   @Param("className") String className);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月13日
     * @time: 2021年11月13日20:19:38
     * @description:教师端按照时间和班级查询助力总次数
     */
    List<PersonRankingModel> selectAllImprovedByClass(@Param("beginTime") String beginTime,
                                                   @Param("endTime") String endTime,
                                                   @Param("className") String className);
    /**
     * @author: 张艳伟
     * @version:按照时间查询成绩-班级模糊查询
     * @param: []
     * @date: 2021年9月26日
     * @time: 2021年9月26日19:54:10
     * @description:
     */
    List<PersonRankingModel> selectRankingByTimeLevelAllClass(@Param("beginTime") String beginTime,
                                                         @Param("endTime") String endTime);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time: 2021年10月11日20:05:56
     * @description:根据学号查询班级前三名
     */
    List<PersonRankingModel> selectClassTop(@Param("userCode") String userCode);

    List<TeamRankingModel> selectTeamTop(@Param("userCode") String userCode);


    List<PersonResultModel> selectByUserId(@Param("userId") String userId);
}
