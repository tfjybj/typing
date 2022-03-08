package com.tfjybj.typing.provider.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.entity.IntegralEntity;
import com.tfjybj.typing.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author 齐智
 * @Date: 2020/10/21 16:50
 * @Version: 1.0
 **/
@Repository("integralDao")
public interface IntegralDao extends BaseMapper<IntegralEntity> {
    /**
     * 查询用户的积分或者成绩
     * @param userId
     * @return
     */
    PersonIntegralModel selectPersonIntegral(String userId);

    /**
     * 将这个人的历史记录，更新为1
     * @param userId
     * @return
     */
    Integer updatePersonIntegralIsDelete(String userId);


    /**
     * 插入数据，到Integral表中
     * @param
     * @return
     */
    Integer insertIntegral(@Param("integralEntity") IntegralEntity integralEntity);

    Double selectLastTotalIntegral(String userId);


    Integer selectCountNumber();


    List<RiseCountModel> selectLevelRecord(String userId);

    Integer insertImprovement(@Param("improveReportModel")ImproveReportModel improveReportModel);

    List<ImproveReportModel> selectImproveInfo();
    List<GradeModel>  selectGrade(String userCode);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:查询当前打字次数
     */
    List<PersonResultModel>  selectPersonResultSum(String userId);


    List<GradeModel>  selectGradeUserId(String userId);
}
