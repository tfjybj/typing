package com.tfjybj.typing.provider.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dmsdbj.itoo.tool.base.service.impl.BaseServicePlusImpl;
import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.entity.PersonIntegralEntity;
import com.tfjybj.typing.model.IntegralPersonModel;
import com.tfjybj.typing.provider.dao.PersonIntegralDao;
import com.tfjybj.typing.provider.service.PersonIntegralService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("PersonIntegralService")
public class PersonIntegralServiceImpl extends BaseServicePlusImpl<PersonIntegralDao, PersonIntegralEntity> implements PersonIntegralService {

    @Resource
    private PersonIntegralDao personIntegralDao;
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:学生证书获得积分
     */
    @Override
    public boolean insertIntegral(@Param("IntegralPersonModel") PersonIntegralEntity model) {
        //设置ID为雪花算法产生的ID
        model.setId(IdWorker.getIdStr());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        personIntegralDao.insertIntegral(model);
        return true;
    };



    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照学号和积分类型查询
     */
    @Override
    public ItooResult selectByUserCode(String userCode, String integralSource, String smallSource){
        List<IntegralPersonModel> integralPersonModels = personIntegralDao.selectByUserCode(userCode,integralSource,smallSource);
        if (integralPersonModels == null || integralPersonModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", integralPersonModels);
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:学生端按照学号查询打卡记录
     */
    @Override
    public  ItooResult selectPunchByUserCode(String userCode){
        List<IntegralPersonModel> integralPersonModels = personIntegralDao.selectPunchByUserCode(userCode);
        if (integralPersonModels == null || integralPersonModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", integralPersonModels);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月24日
     * @time: 2021年10月24日19:28:17
     * @description:学生端按照学号查询证书记录
     */
    @Override
    public  ItooResult selectLevelByUserCode(String userCode,String smallSource){
        List<IntegralPersonModel> integralPersonModels = personIntegralDao.selectLevelByUserCode(userCode,smallSource);
        if (integralPersonModels == null || integralPersonModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", integralPersonModels);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间查询全部每日打卡数据
     */
    @Override
    public  ItooResult selectPunchByTime(String createTime, String endTime){
        List<IntegralPersonModel> integralPersonModels = personIntegralDao.selectPunchByTime(createTime, endTime);
        if (integralPersonModels == null || integralPersonModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", integralPersonModels);
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间班级查询全部每日打卡数据
     */
    @Override
    public  ItooResult selectPunchByTimeClass(String createTime, String endTime,String className){
        List<IntegralPersonModel> integralPersonModels = personIntegralDao.selectPunchByTimeClass(createTime, endTime,className);
        if (integralPersonModels == null || integralPersonModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", integralPersonModels);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间积分来源查询全部每日打卡数据
     */
    @Override
    public  ItooResult selectPunchByTimeSource(String createTime, String endTime,String smallSource){
        List<IntegralPersonModel> integralPersonModels = personIntegralDao.selectPunchByTimeSource(createTime, endTime,smallSource);
        if (integralPersonModels == null || integralPersonModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", integralPersonModels);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间积分班级来源查询全部每日打卡数据
     */
    @Override
    public  ItooResult selectPunchByTimeSourceClass(String createTime, String endTime,String smallSource,String className){
        List<IntegralPersonModel> integralPersonModels = personIntegralDao.selectPunchByTimeSourceClass(createTime, endTime,smallSource,className);
        if (integralPersonModels == null || integralPersonModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", integralPersonModels);
    }



}
