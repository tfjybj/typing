package com.tfjybj.typing.provider.service;


import com.dmsdbj.itoo.tool.base.service.BaseServicePlus;
import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.entity.KeyEntity;
import com.tfjybj.typing.model.KeyModel;
import com.tfjybj.typing.model.MsgModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KeyService接口
 * ty_key表
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0 2021年9月29日10:37:07
 */
public interface KeyService extends BaseServicePlus<KeyEntity> {

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月1日
     * @time:2021年10月1日20:34:55
     * @description:插入键位练习数据
     */
    ItooResult insertKeyResult(@Param("KeyModel") KeyEntity KeyModel);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月31日
     * @time:2021年10月31日19:11:31
     * @description:修改键位练习数据
     */
    boolean updateKeyResult( KeyEntity model);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月1日
     * @time:2021年10月1日20:34:55
     * @description:根据时间班级查询键位练习数据
     */
    ItooResult selectKeyByTimeClass(String createTime, String endTime,String className);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月10日
     * @time:2021年10月10日15:13:42
     * @description:按照班级查询未参加键位练习数据
     */
    ItooResult selectNotKeyByClass(String beginTime,String endTime,String className);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:按照学号时间查询每日键位练习时长
     */
    ItooResult selectSetTimeByTime(String userCode,String createTime);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:按照学号查询键位练习总时长
     */
    ItooResult selectTimeByUserCode(String userCode);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月1日
     * @time:2021年10月1日20:34:55
     * @description:根据时间查询键位练习数据
     */
    ItooResult selectKeyByTime(String createTime, String endTime);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:查询当前时间该学生是否有键位练习数据
     */
    ItooResult selectKeyByTimeUserCode(String createTime, String endTime,String userCode);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月10日
     * @time:2021年10月10日15:14:45
     * @description:查詢全部未參與键位练习的人員
     */
    ItooResult selectNotKey(String beginTime, String endTime);

    boolean sendMessage(MsgModel msgModel);

    List<KeyModel> selectPersonKeyRateAndSpeed(String userCode);
}
