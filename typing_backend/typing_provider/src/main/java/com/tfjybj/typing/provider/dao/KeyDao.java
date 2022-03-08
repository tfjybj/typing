package com.tfjybj.typing.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.entity.KeyEntity;
import com.tfjybj.typing.model.KeyModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * KeyDao接口
 * ty_key表
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository("KeyDao")
public interface KeyDao  extends BaseMapper<KeyEntity> {
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月1日
     * @time:2021年10月1日20:34:55
     * @description:插入键位练习数据
     */
    void insertKeyResult(@Param(value = "KeyEntity") KeyEntity KeyEntity);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月31日
     * @time:2021年10月31日19:11:31
     * @description:修改键位练习数据
     */
    boolean updateKeyResult(@Param("KeyEntity") KeyEntity model);


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月1日
     * @time:2021年10月1日20:34:55
     * @description:
     */
    List<KeyModel> selectKeyByTimeClass(@Param("beginTime") String beginTime,
                                                         @Param("endTime") String endTime,
                                                         @Param("className") String className);


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月10日
     * @time:2021年10月10日15:10:04
     * @description:按照班级查询未参加键位练习数据
     */
    List<KeyModel> selectNotKeyByClass(@Param("beginTime") String beginTime,
                                       @Param("endTime") String endTime,
                                       @Param("className") String className);


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:按照学号时间查询每日键位练习时长
     */
    List<KeyModel> selectSetTimeByTime(@Param("userCode") String userCode,
                                       @Param("createTime") String createTime );
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:按照学号查询键位练习总时长
     */
    List<KeyModel> selectTimeByUserCode(@Param("userCode") String userCode );


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月1日
     * @time:2021年10月1日20:34:55
     * @description:
     */
    List<KeyModel> selectKeyByTime(@Param("beginTime") String beginTime,
                                        @Param("endTime") String endTime);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:查询当前时间该学生是否有键位练习数据
     */
    List<KeyModel> selectKeyByTimeUserCode(@Param("beginTime") String beginTime,
                                   @Param("endTime") String endTime,
                                           @Param("userCode") String userCode);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月10日
     * @time:2021年10月10日15:14:45
     * @description:查詢全部未參與键位练习的人員
     */
    List<KeyModel> selectNotKey(@Param("beginTime") String beginTime,
                                @Param("endTime") String endTime);

    List<KeyModel> selectPersonKeyRateAndSpeed(String userCode);
}

