package com.tfjybj.typing.utils.job.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tfjybj.typing.model.TeamDetailResultModel;
import com.tfjybj.typing.model.TeamResultModel;
import com.tfjybj.typing.provider.dao.TeamDetailResultDao;
import com.tfjybj.typing.provider.dao.TeamResultDao;
import com.tfjybj.typing.utils.cache.RedisUtil;
import com.tfjybj.typing.utils.cache.TypingReids;
import com.tfjybj.typing.utils.job.IBaseJob;
import com.tfjybj.typing.utils.job.JobHander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

@JobHander("redisToDBJob")
public class RedisToDBJob implements IBaseJob {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    TeamResultDao teamResultDao;
    @Autowired
    TeamDetailResultDao teamDetailResultDao;

    @Scheduled(cron = "0/5 0 0 * * ?")
    @Override
    public void FinishToDB() {
        Set<String> keys = redisUtil.keys(TypingReids.Finish + "*");
        Set<String> keys1 = redisUtil.keys(TypingReids.Prepare + "*");
        List<TeamResultModel> list = new ArrayList<>();
        List<TeamDetailResultModel> teamDetailResultModelList = new ArrayList<>();
        for (String str : keys) {
            String ave = redisUtil.hget(str, "Ave");
            Map hmget = redisUtil.hmget(str);
            hmget.remove("Ave");
            Collection<String> valueCollection = hmget.values();
            if (hmget != null && hmget.size() != 0) {
                List<String> valueList = new ArrayList<String>(valueCollection);
                teamDetailResultModelList = JSONObject.parseArray(valueList.toString(), TeamDetailResultModel.class);
                for (TeamDetailResultModel teamDetailResultModel:teamDetailResultModelList){
                    teamDetailResultModel.setId(IdWorker.getIdStr());
                }
                teamDetailResultDao.RedisToDb(teamDetailResultModelList);
            }
            TeamResultModel teamResultModel = JSON.parseObject(ave, TeamResultModel.class);
            teamResultModel.setId(IdWorker.getIdStr());
            list.add(teamResultModel);
        }
        if (list.size() != 0) {
            teamResultDao.RedisToDb(list);
        }

        for (String str : keys) {
            redisUtil.del(str);
        }
        for (String str1 : keys1) {
            redisUtil.del(str1);
        }

        redisUtil.del(TypingReids.Alluser);
        redisUtil.del(TypingReids.Allteam);
    }
}
