package com.tfjybj.typing.provider.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dmsdbj.itoo.tool.base.service.impl.BaseServicePlusImpl;
import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.entity.KeyEntity;
import com.tfjybj.typing.model.KeyModel;
import com.tfjybj.typing.model.MsgModel;
import com.tfjybj.typing.provider.common.Constants;
import com.tfjybj.typing.provider.dao.KeyDao;
import com.tfjybj.typing.provider.service.KeyService;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * KeyService接口实现类
 * ${base}表
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0
 */
@Service("KeyService")
public class KeyServiceImpl extends BaseServicePlusImpl<KeyDao, KeyEntity> implements KeyService {


    @Resource
    private KeyDao KeyDao;


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月1日
     * @time:2021年10月1日20:34:55
     * @description:插入键位练习数据
     */
    @Override
    public ItooResult insertKeyResult(@Param("KeyModel") KeyEntity KeyModel) {

        //设置ID为雪花算法产生的ID
        KeyModel.setId(IdWorker.getIdStr());
        KeyModel.setCreateTime(new Date());
        KeyModel.setUpdateTime(new Date());
       KeyDao.insertKeyResult(KeyModel);
        return new ItooResult(ItooResult.SUCCESS, "插入成功", KeyModel);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月31日
     * @time:2021年10月31日19:11:31
     * @description:修改键位练习数据
     */
    @Override
    public boolean updateKeyResult( KeyEntity model){
        model.setUpdateTime(new Date());
        return  KeyDao.updateKeyResult(model);
    };

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月1日
     * @time:2021年10月1日20:34:55
     * @description:根据时间班级查询键位练习数据
     */
    @Override
    public ItooResult selectKeyByTimeClass(String createTime, String endTime, String className) {
        List<KeyModel> keyModels = KeyDao.selectKeyByTimeClass(createTime, endTime, className);
        if (keyModels == null || keyModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
//将time类型的练习时长转换为*分*秒的字符串类型
        for (KeyModel keyModel : keyModels) {
           Integer setTime = keyModel.getSetTime();
           Integer minute=setTime/60;
           Integer second=(setTime-minute*60);
            StringBuffer sb = new StringBuffer();
            if (minute > 0) {
                sb.append(minute + "分");
            }
            if (second > 0) {
                sb.append(second + "秒");
            }
            String strDate = sb.toString();
            keyModel.setStrTime(strDate);

        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", keyModels);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月10日
     * @time:2021年10月10日15:13:27
     * @description:按照班级查询未参加键位练习数据
     */
    @Override
    public ItooResult selectNotKeyByClass( String beginTime,String endTime,String className) {
        List<KeyModel> keyModels = KeyDao.selectNotKeyByClass(beginTime,endTime,className);
        if (keyModels == null || keyModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", keyModels);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:按照学号时间查询每日键位练习时长
     */
    @Override
    public ItooResult selectSetTimeByTime( String userCode,String createTime) {
        List<KeyModel> keyModels = KeyDao.selectSetTimeByTime(userCode,createTime);
        if (keyModels == null || keyModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", keyModels);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:按照学号查询键位练习总时长
     */
    @Override
    public    ItooResult selectTimeByUserCode(String userCode) {
        List<KeyModel> keyModels = KeyDao.selectTimeByUserCode(userCode);
        if (keyModels == null || keyModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", keyModels);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月1日
     * @time:2021年10月1日20:34:55
     * @description:根据时间查询键位练习数据
     */
    @Override
    public ItooResult selectKeyByTime(String createTime, String endTime) {
        List<KeyModel> keyModels = KeyDao.selectKeyByTime(createTime, endTime);
        if (keyModels == null || keyModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }

        //将time类型的练习时长转换为*分*秒的字符串类型
        for (KeyModel keyModel : keyModels) {
            Integer setTime = keyModel.getSetTime();
            Integer minute=setTime/60;
            Integer second=(setTime-minute*60);
            StringBuffer sb = new StringBuffer();
            if (minute > 0) {
                sb.append(minute + "分");
            }
            if (second > 0) {
                sb.append(second + "秒");
            }
            String strDate = sb.toString();
            keyModel.setStrTime(strDate);

        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", keyModels);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:查询当前时间该学生是否有键位练习数据
     */
    @Override
    public ItooResult selectKeyByTimeUserCode (String createTime, String endTime,String userCode) {
        List<KeyModel> keyModels = KeyDao.selectKeyByTimeUserCode(createTime, endTime,userCode);
        if (keyModels == null || keyModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", keyModels);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月10日
     * @time:2021年10月10日15:14:45
     * @description:查詢全部未參與键位练习的人員
     */
    @Override
    public ItooResult selectNotKey(String beginTime, String endTime) {
        List<KeyModel> keyModels = KeyDao.selectNotKey(beginTime,endTime);
        if (keyModels == null || keyModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }

        System.out.println(keyModels);
        return new ItooResult(ItooResult.SUCCESS, "查询成功", keyModels);
    }

    /**
     * @author: 宋文轩
     * @version:
     * @param: msgMoedl
     * @date: 2021年11月8日
     * @time:2021年11月8日17:22:38
     * @description:给未进行键位练习的人员发送消息
     */
    @Override
    public boolean sendMessage(MsgModel msgModel) {
        RestTemplate restTemplate=new RestTemplate();
        //发送消息
        List<String> dingIdlist=new ArrayList<>();
        for (int i = 0; i < msgModel.getDingIds().size(); i++) {
            dingIdlist.add(msgModel.getDingIds().get(i));
        }
        msgModel.setDingIds(dingIdlist);
        String content = JSONObject.toJSONString(msgModel);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<>(content, headers);
        String stringResponseEntity = restTemplate.postForObject(Constants.SEND_MSG, request, String.class);

        return true;
    }

    @Override
    public List<KeyModel> selectPersonKeyRateAndSpeed(String userCode) {
        List<KeyModel> keyModel = KeyDao.selectPersonKeyRateAndSpeed(userCode);
        return keyModel;
    }


}
