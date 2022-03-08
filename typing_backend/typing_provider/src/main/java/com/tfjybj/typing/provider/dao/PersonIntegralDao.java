package com.tfjybj.typing.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.entity.PersonIntegralEntity;
import com.tfjybj.typing.model.IntegralPersonModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("personIntegralDao")
public interface PersonIntegralDao extends BaseMapper<PersonIntegralEntity> {
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:学生证书获得积分
     */
    boolean insertIntegral(@Param("PersonIntegralEntity") PersonIntegralEntity model);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照学号和积分类型查询
     */
    List<IntegralPersonModel> selectByUserCode(@Param("userCode") String userCode,
                                               @Param("integralSource") String integralSource,
                                               @Param("smallSource") String smallSource);


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:学生端按照学号查询打卡记录
     */
    List<IntegralPersonModel> selectPunchByUserCode(@Param("userCode") String userCode);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月24日
     * @time: 2021年10月24日19:28:17
     * @description:学生端按照学号查询证书记录
     */
    List<IntegralPersonModel> selectLevelByUserCode(@Param("userCode") String userCode,
                                                    @Param("smallSource") String smallSource);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间查询全部每日打卡数据
     */
    List<IntegralPersonModel> selectPunchByTime(@Param("beginTime") String beginTime,
                                                              @Param("endTime") String endTime);


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间班级查询全部每日打卡数据
     */
    List<IntegralPersonModel> selectPunchByTimeClass(@Param("beginTime") String beginTime,
                                                @Param("endTime") String endTime,
                                                     @Param("className") String className);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间积分来源查询全部每日打卡数据
     */
    List<IntegralPersonModel> selectPunchByTimeSource(@Param("beginTime") String beginTime,
                                                     @Param("endTime") String endTime,
                                                     @Param("smallSource") String smallSource);


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间积分班级来源查询全部每日打卡数据
     */
    List<IntegralPersonModel> selectPunchByTimeSourceClass(@Param("beginTime") String beginTime,
                                                      @Param("endTime") String endTime,
                                                      @Param("smallSource") String smallSource,
                                                           @Param("className") String className);


}
