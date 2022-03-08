package com.tfjybj.typing.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.entity.TeamDetailResultEntity;
import com.tfjybj.typing.model.TeamDetailResultModel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TeamDetailResultDao接口
 * teamDetailResult表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@Repository("teamDetailResultDao")
public interface TeamDetailResultDao extends BaseMapper<TeamDetailResultEntity> {
    //将redis完成比赛的人插入数据库
    void RedisToDb(List<TeamDetailResultModel> list);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time:2021年10月11日10:53:12
     * @description:添加邀请助力的成绩1
     */
    boolean insertTeamDetailResult(@Param("TeamDetailResultModel") TeamDetailResultModel model);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time:2021年10月11日10:53:12
     * @description:教师端查看邀请助力的详细成绩
     */

    List<TeamDetailResultModel> selectTeamByTimeClass(@Param("beginTime") String  beginTime,
                                        @Param("endTime") String  endTime,
                                        @Param("className") String className);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:学生端查看邀请助力的次数
     */
    List<TeamDetailResultModel> selectByTimeUserCode(@Param("userCode") String  userCode,
                                                      @Param("createTime") String  createTime);


    /**
     * @author: 宋文轩
     * @version:
     * @param: []
     * @date: 2021年11月13日
     * @time: 2021年11月13日21:19:42
     * @description:学生端查看邀请助力的详细成绩
     */
    List<TeamDetailResultModel> selectTeamByTimeAndUserCode(String beginTime, String endTime, String userCode);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端查看邀请助力的详细成绩
     */
    List<TeamDetailResultModel> selectTeamByTime(@Param("beginTime") String  beginTime,
                                                      @Param("endTime") String  endTime);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据时间和学号查询邀请助力数据
     */
    List<TeamDetailResultModel> selectTeamByTimeUserCode(@Param("beginTime") String  beginTime,
                                                 @Param("endTime") String  endTime,
                                                         @Param("userCode") String  userCode);



}
