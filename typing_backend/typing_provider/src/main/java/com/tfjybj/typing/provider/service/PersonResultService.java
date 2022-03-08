package com.tfjybj.typing.provider.service;

import com.dmsdbj.itoo.tool.base.service.BaseServicePlus;
import com.dmsdbj.itoo.tool.business.ItooResult;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.PersonResultEntity;
import com.tfjybj.typing.model.ImproveReportModel;
import com.tfjybj.typing.model.PersonIntegralModel;
import com.tfjybj.typing.model.PersonResultModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PersonResultService接口
 * personResult表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
public interface PersonResultService extends BaseServicePlus<PersonResultEntity> {


    /**
     * 插入学生成绩
     *
     * @param
     * @return AddActivePerson
     * @author 王闯
     * @since 2019年7月2日19:37:53
     */
    boolean insertPersonResult(@Param("personResultModel") PersonResultEntity personResultModel);


    /**
     * 分页查询个人所有成绩
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return 分页查询的结果
     * @author 王闯
     * @since ${version} 2019-04-11 10:44:52
     */
    PageInfo<PersonResultEntity> selectPersonResult(String userId, Integer pageNo, Integer pageSize);

    /**
     * 查询个人的积分和成绩
     *
     * @return
     */
    PersonIntegralModel selectPersonIntegral(String usercode);

    /**
     * @author Yara
     * @date 2020年10月26日10:20:48
     * 查询个人全部正确率和速度
     */
    List<PersonResultEntity> selectPersonRateAndSpeed(String userId);
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
     * @author: 郝龙飞
     * @version:
     * @param: []
     * @date: 2020/10/27
     * @time: 19:30
     * @description: 进步排名
     */
    List selectPersonProgress(Integer pageNo, Integer pageSize);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:根据班级查询排行榜
     */
    List selectPersonClassProgress(Integer pageNo, Integer pageSize, String className);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月27日
     * @time: 2021年9月27日08:28:46
     * @description:根据班级查询进步，班级模糊查询
     */
    List selectPersonClassProgressClass(Integer pageNo, Integer pageSize);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:根据分组进步排名
     */
    List selectPersonTeamProgress(Integer pageNo, Integer pageSize, String teamId,String className);

    /**
     * @author: 郝龙飞
     * @version:
     * @param: []
     * @date: 2020/10/27
     * @time: 19:33
     * @description: 整体排名
     */
    List selectPersonRanking(Integer pageNo, Integer pageSize);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:根据班级进步英雄榜排名
     */
    List selectPersonClassRanking(Integer pageNo, Integer pageSize, String className);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月27日
     * @time: 2021年9月27日08:28:46
     * @description:根据班级查询整体排名，班级模糊查询
     */
    List selectPersonClassRankingClass(Integer pageNo, Integer pageSize);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:根据分组整体排名
     */
    List selectPersonTeamRanking(Integer pageNo, Integer pageSize, String teamId,String className);


    ItooResult selectClassLevelCount(List<String> classNameList);


    ItooResult selectRiseCount();


    ItooResult selectTeamRanking(List<String> className);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:查询全部未练习人员
     */
    ItooResult selectAllNotRanking();
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:根据班级查询全部未练习人员
     */
    ItooResult selectAllNotRankingByClass(String className);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:查询全部积分排名-AR奖状接口
     */
    ItooResult selectAllTeamRanking();

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:根据班级查询积分排名--查询晋级榜单
     */
    ItooResult selectAllTeamRankingByClass(String className);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月15日
     * @time: 2021年11月15日11:35:01
     * @description:根据等级查询积分排名-晋级榜单
     */
    ItooResult selectAllTeamRankingByLevel(String level);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月15日
     * @time: 2021年11月15日11:35:01
     * @description:根据班级和等级查询积分排名-晋级榜单
     */
    ItooResult selectAllTeamRankingByClassLevel(String className,String level);
    /**
     * 查询晋级人员名单——播报
     *
     * @return 未播报的人员名单
     * @author Yara
     * @date 2020年11月1日15:41:01
     */
    List<String> selectPersonImprovement();

    /**
     * 查询晋级人员信息——滚屏
     *
     * @return 全部晋级人员名单
     * @author Yara
     * @date 2020年11月4日15:11:50
     */
    List<ImproveReportModel> selectImprovement();

    /**
     * 根据条件查询个人成绩
     *
     * @param createTime 开始时间
     * @param endTime    结束时间
     * @param level      等级
     * @return
     */
    ItooResult selectRankingByTimeLevel(String createTime, String endTime, String level, String className);

    /**
     * @author: 张艳伟
     * @version:按照条件查询成绩，班级模糊查询
     * @param: []
     * @date: 2021年9月26日
     * @time: 2021年9月26日19:54:10
     * @description:
     */
    ItooResult selectRankingByTimeLevelClass(String createTime, String endTime, String level);

    ItooResult selectGradeAndNum(String userCode);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:查询当前打字次数
     */
    ItooResult selectPersonResultSum(String userId);

    /**
     * @author: 张艳伟
     * @version:按照时间查询成绩-班级模糊查询
     * @param: []
     * @date: 2021年9月26日
     * @time: 2021年9月26日19:54:10
     * @description:
     */
    ItooResult selectRankingByTimeLevelAllClass(String createTime, String endTime);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月22日
     * @time: 2021年9月22日08:08:35
     * @description:
     */
    ItooResult selectRankingByTimeLevelAll(String createTime, String endTime, String className);

    /*
     * @version V1.0
     * Title: selectClass
     * @author LiuYanQiang
     * @description 查询当前用户的所在班级信息
     * @createTime  2021/9/19 17:47
     * @param [userCode]
     * @return com.dmsdbj.itoo.tool.business.ItooResult
     */

    ItooResult selectClass(String userCode);

    ItooResult selectImprovedByTime (String createTime, String endTime, String userId);



    ItooResult selectImproved(String createTime, String endTime);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月13日
     * @time: 2021年11月13日19:58:02
     * @description:教师端按照时间查询助力总次数
     */
    ItooResult selectAllImproved(String createTime, String endTime);
    ItooResult selectNumByTime(String beginTime, String endTime,String userCode);
    ItooResult selectImprovedByClass(String createTime, String endTime,String className);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月13日
     * @time: 2021年11月13日20:19:38
     * @description:教师端按照时间和班级查询助力总次数
     */
    ItooResult selectAllImprovedByClass(String createTime, String endTime,String className);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time: 2021年10月11日20:05:56
     * @description:根据学号查询班级前三名
     */
    ItooResult selectClassTop(String userCode);

    ItooResult selectTeamTop(String userCode);
    ItooResult selectByUserId(String userId);

}
