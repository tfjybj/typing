package com.tfjybj.typing.provider.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.entity.KeyEntity;
import com.tfjybj.typing.model.KeyModel;
import com.tfjybj.typing.model.MsgModel;
import com.tfjybj.typing.provider.service.KeyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * KeyController
 * ty_key表
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0 2021年9月29日10:34:20
 */
@Api(tags = {"ty_key表接口"})
@RequestMapping(value = "/ty_key")
@RestController
public class KeyController {
    @Autowired
    private KeyService KeyService;

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月1日
     * @time:2021年10月1日20:34:55
     * @description:添加键位练习数据
     */
    @ApiOperation(value = "添加键位练习数据")
    @PostMapping(value = {"/insertKeyResult"})
    public ItooResult insertKeyResult(@RequestBody KeyEntity model) {

        ItooResult flag = KeyService.insertKeyResult(model);
            return ItooResult.build(ItooResult.SUCCESS, "插入成功", flag);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月31日
     * @time:2021年10月31日19:11:31
     * @description:修改键位练习数据
     */
    @ApiOperation(value = "修改键位练习的数据")
    @PostMapping(value = {"/updateKeyResult"})
    public ItooResult updateKeyResult(@RequestBody KeyEntity model) {
        boolean flag = KeyService.updateKeyResult(model);
        if (flag == true) {
            return ItooResult.build(ItooResult.SUCCESS, "修改成功", true);
        } else {
            return ItooResult.build(ItooResult.FAIL, "修改失败", false);
        }

    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月1日
     * @time:2021年10月1日20:34:55
     * @description:按照时间查询班级个人的键位练习成绩
     */
    @ApiOperation(value = "按照时间查询班级个人的键位练习成绩")
    @GetMapping(value = "/selectKeyByTimeClass/{beginTime}/{endTime}/{className}")
    public ItooResult selectKeyByTimeClass(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                                  @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                                  @ApiParam(name = "className", value = "班级") @PathVariable String className) {
        return KeyService.selectKeyByTimeClass(beginTime, endTime, className);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月10日
     * @time:2021年10月10日15:14:45
     * @description:按照班级查询未参加键位练习数据
     */
    @ApiOperation(value = "按照班级查询未参加键位练习数据")
    @GetMapping(value = "/selectNotKeyByClass/{beginTime}/{endTime}/{className}")
    public ItooResult selectNotKeyByClass(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                          @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                          @ApiParam(name = "className", value = "班级") @PathVariable String className) {
        return KeyService.selectNotKeyByClass(beginTime,endTime,className);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:按照学号时间查询每日键位练习时长
     */

    @ApiOperation(value = "按照学号时间查询每日键位练习时长")
    @GetMapping(value = "/selectSetTimeByTime/{userCode}/{createTime}")
    public ItooResult selectSetTimeByTime(@ApiParam(name = "userCode", value = "学号") @PathVariable String userCode,
                                          @ApiParam(name = "createTime", value = " 创建时间") @PathVariable String createTime) {
        return KeyService.selectSetTimeByTime(userCode,createTime);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:按照学号查询键位练习总时长
     */

    @ApiOperation(value = "按照学号查询键位练习总时长")
    @GetMapping(value = "/selectTimeByUserCode/{userCode}")
    public ItooResult selectTimeByUserCode(@ApiParam(name = "userCode", value = "学号") @PathVariable String userCode) {
        return KeyService.selectTimeByUserCode(userCode);
    }




    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:按照时间班级个人的键位练习成绩
     */
    @ApiOperation(value = "按照时间班级个人的键位练习成绩")
    @GetMapping(value = "/selectKeyByTime/{beginTime}/{endTime}")
    public ItooResult selectKeyByTime(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                           @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime) {
        return KeyService.selectKeyByTime(beginTime, endTime);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月10日
     * @time:2021年10月10日15:14:45
     * @description:根据时间查詢全部未參與键位练习的人員
     */
    @ApiOperation(value = "根据时间查詢全部未參與键位练习的人員")
    @GetMapping(value = "/selectNotKey/{beginTime}/{endTime}")
    public ItooResult selectNotKey(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                   @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime) {
        return KeyService.selectNotKey(beginTime, endTime);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:查询当前时间该学生是否有键位练习数据
     */

    @ApiOperation(value = "查询当前时间该学生是否有键位练习数据")
    @GetMapping(value = "/selectKeyByTimeUserCode/{beginTime}/{endTime}/{userCode}")
    public ItooResult selectKeyByTimeUserCode(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                      @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                      @ApiParam(name = "userCode", value = "学号", required = true) @PathVariable String userCode) {
        return KeyService.selectKeyByTimeUserCode(beginTime, endTime,userCode);
    }


    /**
     * @author: 宋文轩
     * @version:
     * @param: msgMoedl
     * @date: 2021年11月8日
     * @time:2021年11月8日17:22:38
     * @description:给未进行键位练习的人员发送消息
     */
    @ApiOperation(value = "未进行键位练习的人员发送消息")
    @PostMapping(value = {"/sendMessage"})
    public ItooResult sendMessage(@RequestBody MsgModel msgModel) {
        boolean code = KeyService.sendMessage(msgModel);
        if (code == true) {
            return ItooResult.build(ItooResult.SUCCESS, "发送成功", true);
        } else {
            return ItooResult.build(ItooResult.FAIL, "发送失败", false);
        }
    }

    /**
     * @author: 宋文轩
     * @version:
     * @param: []
     * @date: 2021年11月13日
     * @time: 2021年11月13日14:42:36
     * @description:查询个人键位练习速度和正确率
     */
    @ApiOperation(value = "查询个人键位练习速度和正确率")
    @GetMapping(value = {"/selectPersonKeyRateAndSpeed/{userCode}"})
    public ItooResult selectPersonKeyRateAndSpeed(@ApiParam(name = "userCode", value = "学号", required = true, example = "") @PathVariable String userCode) {
        List<KeyModel> list = KeyService.selectPersonKeyRateAndSpeed(userCode);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
    }
}
