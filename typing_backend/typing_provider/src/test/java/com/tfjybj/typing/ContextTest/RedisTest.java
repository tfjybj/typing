package com.tfjybj.typing.ContextTest;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tfjybj.typing.TestBaseProject;
import com.tfjybj.typing.model.PrepareModel;
import com.tfjybj.typing.model.TeamResultModel;
import com.tfjybj.typing.provider.dao.TeamResultDao;
import com.tfjybj.typing.utils.cache.RedisUtil;
import com.tfjybj.typing.utils.cache.TypingReids;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: typing_project_backend
 * @description:
 * @author: 谷浩樟
 * @create: 2019-09-24 17:01
 **/
public class RedisTest extends TestBaseProject {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    TeamResultDao teamResultDao;

    @Test
    public void testResdis(){
        String teamName = "花儿乐队";
        String userId = "12345";
        List<PrepareModel> list = redisUtil.lGet(TypingReids.Prepare + teamName, PrepareModel.class);
        List<PrepareModel> collect = list.stream().filter(s -> !s.getUserId().equals(userId)).collect(Collectors.toList());
        redisUtil.lSet(TypingReids.Prepare + teamName,collect);
        System.out.println("123");

    }
    @Test
    public void testjob(){
        Set<String> keys = redisUtil.keys(TypingReids.Finish + "*");
        List<TeamResultModel> list = new ArrayList<>();
        if(CollectionUtils.isEmpty(keys)) {
            return;
        }
        for (String str : keys) {
            String ave = redisUtil.hget(str, "Ave");
            TeamResultModel teamResultModel = JSON.parseObject(ave, TeamResultModel.class);
            teamResultModel.setId(IdWorker.getIdStr());
            list.add(teamResultModel);
        }
        if (list.size()!=0){
            teamResultDao.RedisToDb(list);
        }
        Set<String> keys1 = redisUtil.keys(TypingReids.Prepare + "*");
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
