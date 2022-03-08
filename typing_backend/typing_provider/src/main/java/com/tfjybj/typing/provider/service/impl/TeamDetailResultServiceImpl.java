package com.tfjybj.typing.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dmsdbj.itoo.tool.base.service.impl.BaseServicePlusImpl;
import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.entity.TeamDetailResultEntity;
import com.tfjybj.typing.model.*;
import com.tfjybj.typing.provider.dao.TeamDao;
import com.tfjybj.typing.provider.dao.TeamDetailResultDao;
import com.tfjybj.typing.provider.service.TeamDetailResultService;
import com.tfjybj.typing.utils.cache.RedisUtil;
import com.tfjybj.typing.utils.cache.TypingReids;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TeamDetailResultService接口实现类
 * ${base}表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@Service("teamDetailResultService")
public class TeamDetailResultServiceImpl extends BaseServicePlusImpl<TeamDetailResultDao,TeamDetailResultEntity> implements TeamDetailResultService {

	//region 模板生成

    @Resource
    private TeamDetailResultDao teamDetailResultDao;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private TeamDao teamDao;


    //endregion

    /* **********************************以下为非模板生成的内容********************************* */

    /**
     * 查询战队英雄榜
     *
     * @param
     * @return
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    @Override
    public List<AllTeamResult> TeamResult() {

        Set<String> keys = redisUtil.keys(TypingReids.Finish  + "*");

        int i =1;
        List<AllTeamResult> list = new ArrayList<>();
        if(CollectionUtils.isEmpty(keys)){
            return null;
        }

        for(String str : keys){
            String ave = redisUtil.hget(str, "Ave");
            AllTeamResult allTeamResult = JSON.parseObject(ave,AllTeamResult.class);
            list.add(allTeamResult);
        }

        Comparator<AllTeamResult> allTeamResultComparator = Comparator.comparing(AllTeamResult::getTeamScore).reversed();
        Collections.sort(list,allTeamResultComparator);
        for (AllTeamResult allTeamResult:list){
            allTeamResult.setRank(i++);
        }
        return list;

    }

    /**
     * 查询组内成绩
     *
     * @param
     * @return List<FinishModel>
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    @Override
    public List<FinishModel> TeamDetailResult(String teamName) {
        Map<String,String> hmget = redisUtil.hmget(TypingReids.Finish+teamName);
        if(hmget == null || hmget.size() ==0){
            return null;
        }
        hmget.remove("Ave");
        Collection<String> valueCollection = hmget.values();
        if(CollectionUtils.isEmpty(valueCollection)){
            return null;
        }
        List<String> valueList = new ArrayList<String>(valueCollection);
        List<FinishModel> finishModels = JSONObject.parseArray(valueList.toString(),FinishModel.class);
        Comparator<FinishModel> finishModelComparable = Comparator.comparing(FinishModel::getScore).reversed();
        Collections.sort(finishModels,finishModelComparable);
        return finishModels;

    }


    /**
     * 查询进步排名前十的小组分组
     * @return
     */
    @Override
    public ItooResult selectTeamGrowthChart(List<String> className)
    {
        //查询前十个进步的团队的名字，id和进步的分数
        List<TeamGrowthChartModel> teamEntities = teamDao.selectTeamGrowthChart(className);
        //过滤掉分数小于0的。
        teamEntities=teamEntities.stream()
                .filter(item->item.getGrowthIntegral()>0.0)
//                .peek(item->item.setTeamName("第"+item.getTeamId()+"组"))
                .collect(Collectors.toList());
        return new ItooResult(ItooResult.SUCCESS,"查询成功",teamEntities);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time:2021年10月11日10:53:12
     * @description:添加邀请助力的成绩
     */
    @Override
    public boolean insertTeamDetailResult(@Param("TeamDetailResultModel") TeamDetailResultModel model) {


        //设置ID为雪花算法产生的ID
        model.setId(IdWorker.getIdStr());

        model.setCreateTime(new Date());
        teamDetailResultDao.insertTeamDetailResult(model);

        return true;
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time:2021年10月11日10:53:12
     * @description:教师端查看邀请助力的详细成绩
     */
    @Override
    public ItooResult selectTeamByTimeClass(String  createTime, String  endTime, String className) {
        List<TeamDetailResultModel> teamDetailResultModels = teamDetailResultDao.selectTeamByTimeClass(createTime, endTime, className);
        if (teamDetailResultModels == null || teamDetailResultModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        Integer i = 0;
        for (TeamDetailResultModel teamDetailResultModel : teamDetailResultModels) {
            Date time = teamDetailResultModel.getCreateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    //格式化规则
            String  strDate = sdf.format(time); //格式化成yyyy-MM-dd格式的时间字符串
            teamDetailResultModel.setTime(strDate);
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamDetailResultModels);
    }

    /**
     * @author: 宋文轩
     * @version:
     * @param: []
     * @date: 2021年11月13日
     * @time: 2021年11月13日21:17:11
     * @description:学生端查看邀请助力的详细成绩
     */
    @Override
    public ItooResult selectTeamByTimeAndUserCode(String  beginTime, String  endTime, String userCode) {
        List<TeamDetailResultModel> teamDetailResultModels = teamDetailResultDao.selectTeamByTimeAndUserCode(beginTime, endTime, userCode);
        if (teamDetailResultModels == null || teamDetailResultModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        Integer i = 0;
        for (TeamDetailResultModel teamDetailResultModel : teamDetailResultModels) {
            Date time = teamDetailResultModel.getCreateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    //格式化规则
            String  strDate = sdf.format(time); //格式化成yyyy-MM-dd格式的时间字符串
            teamDetailResultModel.setTime(strDate);
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamDetailResultModels);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:学生端查看邀请助力的次数
     */
    @Override
    public ItooResult selectByTimeUserCode(String  userCode, String  createTime) {
        List<TeamDetailResultModel> teamDetailResultModels = teamDetailResultDao.selectByTimeUserCode(userCode,createTime);
        if (teamDetailResultModels == null || teamDetailResultModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }

        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamDetailResultModels);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端查看邀请助力的详细成绩
     */
    @Override
    public ItooResult selectTeamByTime(String  createTime, String  endTime) {
        List<TeamDetailResultModel> teamDetailResultModels = teamDetailResultDao.selectTeamByTime(createTime, endTime);
        if (teamDetailResultModels == null || teamDetailResultModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        Integer i = 0;
        for (TeamDetailResultModel teamDetailResultModel : teamDetailResultModels) {
            Date time = teamDetailResultModel.getCreateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    //格式化规则
            String  strDate = sdf.format(time); //格式化成yyyy-MM-dd格式的时间字符串
            teamDetailResultModel.setTime(strDate);
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamDetailResultModels);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据时间和学号查询邀请助力数据
     */
    @Override
    public ItooResult selectTeamByTimeUserCode(String  createTime, String  endTime,String userCode) {
        List<TeamDetailResultModel> teamDetailResultModels = teamDetailResultDao.selectTeamByTimeUserCode(createTime, endTime,userCode);
        if (teamDetailResultModels == null || teamDetailResultModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }

        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamDetailResultModels);
    }

}
