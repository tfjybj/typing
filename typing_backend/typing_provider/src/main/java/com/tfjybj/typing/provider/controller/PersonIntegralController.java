package com.tfjybj.typing.provider.controller;


import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.entity.PersonIntegralEntity;
import com.tfjybj.typing.provider.service.PersonIntegralService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * PersonIntegralController
 * personIntegral表
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0 2021年10月20日08:41:26
 */
@Api(tags = {"ty_person_integral表接口"})
@RequestMapping(value = "/ty_person_integral")
@RestController
public class PersonIntegralController {

    @Resource
    private PersonIntegralService personIntegralService;


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:学生证书获得积分
     */
    @ApiOperation(value = "学生证书获得积分")
    @PostMapping(value = {"/insertIntegral"})
    public ItooResult insertIntegral(@RequestBody PersonIntegralEntity model) {
        boolean flag = personIntegralService.insertIntegral(model);
        if (flag == true) {
            return ItooResult.build(ItooResult.SUCCESS, "添加成功", true);
        } else {
            return ItooResult.build(ItooResult.FAIL, "添加失败", false);
        }
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照学号和积分类型查询
     */
    @ApiOperation(value = "按照学号和积分类型查询")
    @GetMapping(value = "/selectByUserCode/{userCode}/{integralSource}/{smallSource}")
    public ItooResult selectByUserCode(@ApiParam(name = "userCode", value = "学号") @PathVariable String userCode,
                                       @ApiParam(name = "integralSource", value = "积分来源") @PathVariable String integralSource,
                                       @ApiParam(name = "smallSource", value = "来源细分") @PathVariable String smallSource) {
        return personIntegralService.selectByUserCode(userCode,integralSource,smallSource);
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:学生端按照学号查询打卡记录
     */
    @ApiOperation(value = "学生端按照学号查询打卡记录")
    @GetMapping(value = "/selectPunchByUserCode/{userCode}")
    public ItooResult selectPunchByUserCode(@ApiParam(name = "userCode", value = "学号", required = true) @PathVariable String userCode)
    {
        return personIntegralService.selectPunchByUserCode(userCode);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月24日
     * @time: 2021年10月24日19:28:17
     * @description:学生端按照学号查询证书记录
     */
    @ApiOperation(value = "学生端按照学号查询证书记录")
    @GetMapping(value = "/selectLevelByUserCode/{userCode}/{smallSource}")
    public ItooResult selectLevelByUserCode(@ApiParam(name = "userCode", value = "学号", required = true) @PathVariable String userCode,
                                            @ApiParam( name = "smallSource", value = "来源细分", required = true) @PathVariable String smallSource)
    {
        return personIntegralService.selectLevelByUserCode(userCode,smallSource);
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间查询全部每日打卡数据
     */
    @ApiOperation(value = "按照时间查询全部每日打卡数据-班级模糊查询")
    @GetMapping(value = "/selectPunchByTime/{beginTime}/{endTime}")
    public ItooResult selectPunchByTime(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                                       @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime)
    {
        return personIntegralService.selectPunchByTime(beginTime, endTime);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间班级查询全部每日打卡数据
     */
    @ApiOperation(value = "按照时间班级查询全部每日打卡数据")
    @GetMapping(value = "/selectPunchByTimeClass/{beginTime}/{endTime}/{className}")
    public ItooResult selectPunchByTimeClass(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                        @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                         @ApiParam(name = "className", value = "班级名称", required = true) @PathVariable String className)
    {
        return personIntegralService.selectPunchByTimeClass(beginTime, endTime,className);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间积分来源查询全部每日打卡数据
     */
    @ApiOperation(value = "按照时间积分来源查询全部每日打卡数据")
    @GetMapping(value = "/selectPunchByTimeSource/{beginTime}/{endTime}/{smallSource}")
    public ItooResult selectPunchByTimeSource(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                             @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                             @ApiParam(name = "smallSource", value = "来源细分", required = true) @PathVariable String smallSource)
    {
        return personIntegralService.selectPunchByTimeSource(beginTime, endTime,smallSource);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月21日
     * @time: 2021年10月21日19:45:36
     * @description:按照时间积分班级来源查询全部每日打卡数据
     */
    @ApiOperation(value = "按照时间积分班级来源查询全部每日打卡数据")
    @GetMapping(value = "/selectPunchByTimeSourceClass/{beginTime}/{endTime}/{smallSource}/{className}")
    public ItooResult selectPunchByTimeSourceClass(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                              @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                              @ApiParam(name = "smallSource", value = "来源细分", required = true) @PathVariable String smallSource,
                                                   @ApiParam(name = "className", value = "班级", required = true) @PathVariable String className)
    {
        return personIntegralService.selectPunchByTimeSourceClass(beginTime, endTime,smallSource,className);
    }







}
