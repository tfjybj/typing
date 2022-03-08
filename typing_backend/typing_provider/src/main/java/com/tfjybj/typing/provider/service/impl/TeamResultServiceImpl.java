package com.tfjybj.typing.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.dmsdbj.itoo.sso.utils.UserUtil;
import com.dmsdbj.itoo.tool.base.service.impl.BaseServicePlusImpl;
import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.entity.TeamResultEntity;
import com.tfjybj.typing.model.*;
import com.tfjybj.typing.provider.dao.TeamResultDao;
import com.tfjybj.typing.provider.service.TeamResultService;
import com.tfjybj.typing.utils.cache.RedisUtil;
import com.tfjybj.typing.utils.cache.TypingReids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TeamResultService接口实现类
 * ${base}表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@Service("teamResultService")
public class TeamResultServiceImpl extends BaseServicePlusImpl<TeamResultDao, TeamResultEntity> implements TeamResultService {

    //region 模板生成
    @Resource
    private TeamResultDao teamResultDao;

    @Resource
    private RedisUtil redisUtil;


    /**
     * 创建战队
     *
     * @param createTeamModel 创建实体
     * @return flag 创建战队是否成功
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    @Override
    public boolean CreateBattleTeam(String userId, CreateTeamModel createTeamModel) {
        //实例化allTeamModel、alluserModel、prepareModel
        AllTeamModel allTeamModel = new AllTeamModel();
        AlluserModel alluserModel = new AlluserModel();
        PrepareModel prepareModel = new PrepareModel();

        //将传入参数插入实例化allTeamModel
        allTeamModel.setArticleName(createTeamModel.getArticleName());
        allTeamModel.setCaptainName(createTeamModel.getCaptainName());
        allTeamModel.setTeamName(createTeamModel.getTeamName());
        allTeamModel.setTeamNum(createTeamModel.getTeamNum());
        allTeamModel.setTime(createTeamModel.getTime());

        //将传入参数插入实例化alluserModel
        alluserModel.setTeamName(createTeamModel.getTeamName());
        alluserModel.setUserId(userId);

        //将传入参数插入实例化prepareModel
        prepareModel.setTeamName(createTeamModel.getTeamName());
        prepareModel.setUserId(userId);
        prepareModel.setUserName(createTeamModel.getCaptainName());

        //查询Allteam是否存在战队名
        boolean flag = redisUtil.hHasKey(TypingReids.Allteam, createTeamModel.getTeamName());
        //如果不存在重复战队名
        if (!flag) {
            //将创建战队信息存入redisAllteam、redisPrepare中
            redisUtil.hset(TypingReids.Allteam, createTeamModel.getTeamName(), JSON.toJSONString(allTeamModel));
            redisUtil.lRightSet(TypingReids.Prepare + createTeamModel.getTeamName(), JSON.toJSONString(prepareModel));
            redisUtil.hset(TypingReids.Alluser, userId, JSON.toJSONString(alluserModel));
        }

        return flag;
    }


    /**
     * 退出战队
     *
     * @param teamName 战队名称
     * @return flag 退出战队
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    @Override
    public boolean QuitBattleTeam(String teamName,String userId ) {

        //根据战队名teamName将准备中的所有人取出
        List<PrepareModel> list = redisUtil.lGet(TypingReids.Prepare + teamName, PrepareModel.class);

        //筛选出除了当前userId以外的当前战队人员
        List<PrepareModel> collect = list.stream().filter(s -> !s.getUserId().equals(userId)).collect(Collectors.toList());

        //将筛选出的数据存入redis
        redisUtil.lSet(TypingReids.Prepare + teamName, collect);

        //根据userId将redis.Alluser中的数据删除
        redisUtil.hdel(TypingReids.Alluser, userId);

        String hget = redisUtil.hget(TypingReids.Allteam, teamName);
        AllTeamModel allTeamModel = JSON.parseObject(hget,AllTeamModel.class);
        allTeamModel.setPersonNum(allTeamModel.getPersonNum()-1);
        redisUtil.hset(TypingReids.Allteam,teamName,JSON.toJSONString(allTeamModel));

        return true;
    }

    /**
     * 打字页面初始化插入成绩
     *
     * @param finishModel 成绩实体
     * @return flag 插入成绩是否成功
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    @Override
    public boolean BattleInfo(String userId, FinishModel finishModel) {
        //根据战队名称，userID,将默认成绩插入redis.Finish中
        boolean flag = redisUtil.hset(TypingReids.Finish + finishModel.getTeamName(), userId, JSON.toJSONString(finishModel));
        AllTeamResult allTeamResult = new AllTeamResult();
        allTeamResult.setArticleName(finishModel.getArticleName());
        allTeamResult.setSetTime(finishModel.getSetTime());
        allTeamResult.setTeamName(finishModel.getTeamName());

        redisUtil.hset(TypingReids.Finish + finishModel.getTeamName(), "Ave", JSON.toJSONString(allTeamResult));
        return flag;

    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 是否之前在战队中
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @Override
    public IsBeforeInBattleTeamModel IsBeforeInBattleTeam(String userId) {
//        String userId = UserUtil.getCurrentUser().getUserId();
//        String userId = "12345";
        System.out.println(userId);
        boolean flag = redisUtil.hHasKey(TypingReids.Alluser, userId);
        if (flag) {
            String value = redisUtil.hget(TypingReids.Alluser, userId);
            IsBeforeInBattleTeamModel isBeforeInBattleTeamModel = JSON.parseObject(value, IsBeforeInBattleTeamModel.class);
            String hget = redisUtil.hget(TypingReids.Allteam, isBeforeInBattleTeamModel.getTeamName());
            TeamModel teamModel = JSON.parseObject(hget, TeamModel.class);
            isBeforeInBattleTeamModel.setArticleName(teamModel.getArticleName());
            isBeforeInBattleTeamModel.setSetTime(teamModel.getTime());
            if (isBeforeInBattleTeamModel.getIsCaptain() == 1) {
                isBeforeInBattleTeamModel.setState(1);
                return isBeforeInBattleTeamModel;
            }
            isBeforeInBattleTeamModel.setState(0);
            return isBeforeInBattleTeamModel;
        }
        IsBeforeInBattleTeamModel isBeforeInBattleTeamModel = new IsBeforeInBattleTeamModel();
        isBeforeInBattleTeamModel.setState(2);
        isBeforeInBattleTeamModel.setIsCaptain(null);
        isBeforeInBattleTeamModel.setTeamName(null);
        return isBeforeInBattleTeamModel;
    }

    /**
     * 组队竞赛初始化
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @Override
    public ItooResult loadBattleTeamCard(String userId) {
//        String userId = UserUtil.getCurrentUser().getUserId();
//        String userId = "12345";
        System.out.println(userId);
        Map<String, String> hmget = redisUtil.hmget(TypingReids.Allteam);
        Collection<String> valueCollection = hmget.values();
        if (CollectionUtils.isEmpty(valueCollection)) {
            return ItooResult.build(ItooResult.SUCCESS, "暂无战队!");
        }
        List<String> valueList = new ArrayList<String>(valueCollection);
        List<TeamModel> teamModels = JSONObject.parseArray(valueList.toString(), TeamModel.class);
        List<TeamModel> collect = teamModels.stream().filter(s -> s.getStatus() == 0).collect(Collectors.toList());
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", collect);
    }

    /**
     * 完成比赛
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @Override
    public void FinalBattle(FinishModel finishModel) {
//        String userId = UserUtil.getCurrentUser().getUserId();
        String userId=finishModel.getUserId();
        System.out.println(userId);
        Integer score = 0;
        Integer speed = 0;
        Integer rightRate = 0;
//        String userId = "12345";
        redisUtil.hset(TypingReids.Finish + finishModel.getTeamName(), userId, JSON.toJSONString(finishModel));
        Map<String, String> hmget = redisUtil.hmget(TypingReids.Finish + finishModel.getTeamName());
        hmget.remove("Ave");
        for (String key : hmget.keySet()) {
            String value = hmget.get(key);
            FinishModel finishModel1 = JSON.parseObject(value, FinishModel.class);
//            System.out.println(value);
//            System.out.println(finishModel1.toString());
            score += finishModel1.getScore();
            speed += finishModel1.getSpeed();
            rightRate += finishModel1.getRightRate();
        }
        Integer scoreAve = score / hmget.size();
        Integer speedAve = speed / hmget.size();
        Integer rightRateAve = rightRate / hmget.size();
        TeamResultModel teamResultModel = new TeamResultModel();
        teamResultModel.setTeamName(finishModel.getTeamName());
        teamResultModel.setArticleName(finishModel.getArticleName());
        teamResultModel.setSetTime(finishModel.getSetTime());
        teamResultModel.setSpeed(speedAve);
        teamResultModel.setTeamScore(scoreAve);
        teamResultModel.setRightRate(rightRateAve);
        redisUtil.hset(TypingReids.Finish + finishModel.getTeamName(), "Ave", JSON.toJSONString(teamResultModel));
    }

    /**
     * 开始比赛
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @Override
    public void BeginBattle(String teamName) {
        String value = redisUtil.hget(TypingReids.Allteam, teamName);
        TeamModel teamModel = JSON.parseObject(value, TeamModel.class);
        teamModel.setStatus(1);
        redisUtil.hset(TypingReids.Allteam, teamName, JSON.toJSONString(teamModel));
        List<PrepareModel> list = redisUtil.lGet(TypingReids.Prepare + teamName, PrepareModel.class);
//        List<WordModel> wordModels = JSONObject.parseArray(List.toString(), WordModel.class);
        for (PrepareModel item : list) {
            redisUtil.hdel(TypingReids.Alluser, item.getUserId());
        }
        redisUtil.del(TypingReids.Prepare + teamName);
    }

    /**
     * 解散战队
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @Override
    public void BreakBattleTeam(String teamName) {
        List<PrepareModel> list = redisUtil.lGet(TypingReids.Prepare + teamName, PrepareModel.class);
//        List<WordModel> wordModels = JSONObject.parseArray(List.toString(), WordModel.class);
        for (PrepareModel item : list) {
            redisUtil.hdel(TypingReids.Alluser, item.getUserId());
        }
        redisUtil.del(TypingReids.Prepare + teamName);
        redisUtil.hdel(TypingReids.Allteam, teamName);
    }

    /**
     * 房间界面初始化
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @Override
    public ItooResult TeamByUserId(String userId) {
//        String userId = UserUtil.getCurrentUser().getUserId();
//        String userId = "12345";
        boolean flag = redisUtil.hHasKey(TypingReids.Alluser, userId);
        if (flag) {
            String str = redisUtil.hget(TypingReids.Alluser, userId);
            IsBeforeInBattleTeamModel isBeforeInBattleTeamModel = JSON.parseObject(str, IsBeforeInBattleTeamModel.class);
            String str1 = redisUtil.hget(TypingReids.Allteam, isBeforeInBattleTeamModel.getTeamName());
            TeamModel teamModel = JSON.parseObject(str1, TeamModel.class);
            return ItooResult.build(ItooResult.SUCCESS, "查询成功", teamModel);
        }
        return ItooResult.build("2222", "该队伍已解散!", null);
    }

    /**
     * 查询队员
     *
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @Override
    public ItooResult findBattleMember(String teamName) {
        boolean flag = redisUtil.hHasKey(TypingReids.Allteam, teamName);
        BattleMember battleMember = new BattleMember();
        if (flag) {
            String str = redisUtil.hget(TypingReids.Allteam, teamName);
            TeamModel teamModel = JSON.parseObject(str, TeamModel.class);
            if (teamModel.getStatus() == 0) {
                List<UserNameModel> list = redisUtil.lGet(TypingReids.Prepare + teamName, PrepareModel.class);
                battleMember.setIsBeforeInBattleTeamModels(list);
                battleMember.setState(0);
                return ItooResult.build(ItooResult.SUCCESS, "查询成功", battleMember);
            }
            battleMember.setState(1);
            return ItooResult.build(ItooResult.SUCCESS, "战队已开始!", battleMember);
        }
        battleMember.setState(2);
        return ItooResult.build(ItooResult.SUCCESS, "战队已解散!", battleMember);
    }

    /**
     * 快速进入房间（输入房间）
     *
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @Override
    public ItooResult joinInBattleTeamByRoomId(String userId,Integer teamNums, String userName) {
//        String userId = UserUtil.getCurrentUser().getUserId();
//        String userId = "12345";
        Map<String, String> hmget = redisUtil.hmget(TypingReids.Allteam);
        Collection<String> valueCollection = hmget.values();
        if (hmget!= null && hmget.size() != 0) {
            List<String> valueList = new ArrayList<String>(valueCollection);
            List<TeamModel> teamModels = JSONObject.parseArray(valueList.toString(), TeamModel.class);
            List<TeamModel> collect = teamModels.stream().filter(s -> s.getStatus() == 0).collect(Collectors.toList());
//            System.out.println(collect.get(0).getTeamNum());
            List<TeamModel> collect1 = collect.stream().filter(s -> s.getTeamNum().equals(teamNums)).collect(Collectors.toList());
            if (collect1.size() == 0) {
                return ItooResult.build(ItooResult.SUCCESS, "战队不存在!");
            }
            collect1.get(0).setPersonNum(collect1.get(0).getPersonNum() + 1);
            redisUtil.hset(TypingReids.Allteam,collect1.get(0).getTeamName(),JSON.toJSONString(collect1.get(0)));
            PrepareModel prepareModel = new PrepareModel();
            prepareModel.setTeamName(collect1.get(0).getTeamName());
            prepareModel.setUserName(userName);
            prepareModel.setUserId(userId);
            redisUtil.lRightSet(TypingReids.Prepare + collect1.get(0).getTeamName(), JSON.toJSONString(prepareModel));
            AlluserModel alluserModel = new AlluserModel();
            alluserModel.setIsCaptain(0);
            alluserModel.setTeamName(collect1.get(0).getTeamName());
            alluserModel.setUserId(userId);
            redisUtil.hset(TypingReids.Alluser, userId, JSON.toJSONString(alluserModel));
            return ItooResult.build(ItooResult.SUCCESS, "查询成功", collect1.get(0).getTeamName());
        }
        return ItooResult.build(ItooResult.SUCCESS, "战队不存在!");
    }
}
