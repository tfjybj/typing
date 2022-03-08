package com.tfjybj.typing.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tfjybj.typing.model.FinishModel;
import com.tfjybj.typing.model.PrepareModel;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: typing_project_backend
 * @description:
 * @author: 谷浩樟
 * @create: 2019-09-24 16:55
 **/
public class UnContextTest {

    @Test
    public void test(){
        List<PrepareModel> list = Lists.newArrayList();
        PrepareModel prepareModel = new PrepareModel();
        prepareModel.setUserId("123");
        prepareModel.setUserName("234");
        list.add(prepareModel);

        PrepareModel prepareModel1 = new PrepareModel();
        prepareModel1.setUserId("1234");
        prepareModel1.setUserName("234");
        list.add(prepareModel1);
        String userId = "123";

        List<PrepareModel> collect = list.stream().filter(s -> !s.getUserId().equals(userId)).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test1(){
        String str = "{\"articleName\":\"Growing up on the road\",\"rank\":2,\"setTime\":1,\"teamName\":\"一百文创大厦\",\"userName\":\"王闯\"}";
        FinishModel finishModel = JSON.parseObject(str, FinishModel.class);
        System.out.println(finishModel);
    }
}
