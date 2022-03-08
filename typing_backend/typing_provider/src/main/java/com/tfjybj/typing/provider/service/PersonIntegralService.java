package com.tfjybj.typing.provider.service;

import com.dmsdbj.itoo.tool.base.service.BaseServicePlus;
import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.entity.PersonIntegralEntity;

public interface PersonIntegralService  extends BaseServicePlus<PersonIntegralEntity> {

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:学生证书获得积分
     */
    boolean insertIntegral( PersonIntegralEntity model);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照学号和积分类型查询
     */
    ItooResult selectByUserCode(String userCode,String integralSource,String smallSource);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:学生端按照学号查询打卡记录
     */
    ItooResult selectPunchByUserCode(String userCode);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月24日
     * @time: 2021年10月24日19:28:17
     * @description:学生端按照学号查询证书记录
     */
    ItooResult selectLevelByUserCode(String userCode,String smallSource);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间查询全部每日打卡数据
     */
    ItooResult selectPunchByTime(String createTime, String endTime);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间班级查询全部每日打卡数据
     */
    ItooResult selectPunchByTimeClass(String createTime, String endTime,String className);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间积分来源查询全部每日打卡数据
     */
    ItooResult selectPunchByTimeSource(String createTime, String endTime,String smallSource);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间积分班级来源查询全部每日打卡数据
     */
    ItooResult selectPunchByTimeSourceClass(String createTime, String endTime,String smallSource,String className);



}
