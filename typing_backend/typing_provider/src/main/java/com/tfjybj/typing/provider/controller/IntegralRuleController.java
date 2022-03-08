package com.tfjybj.typing.provider.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.IntegralRuleEntity;
import com.tfjybj.typing.provider.service.IntegralRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * IntegralRuleController
 * ty_integral_rule表
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0
 */
@Api(tags = {"ty_integral_rule表接口"})
@RequestMapping(value = "/ty_integral_rule")
@RestController
public class IntegralRuleController {

    @Resource
    private IntegralRuleService integralRuleService;

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端增加积分规则
     */
    @ApiOperation(value = "教师端增加积分规则")
    @PostMapping(value = {"/insertIntegralRule"})
    public ItooResult insertIntegralRule(@RequestBody IntegralRuleEntity model) {
        boolean flag = integralRuleService.insertIntegralRule(model);
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
     * @date:
     * @time:
     * @description:教师端查询全部积分规则
     */
    @ApiOperation(value = "教师端查询全部积分规则")
    @GetMapping(value = {"/selectIntegralRule/{pageNo}/{pageSize}"})
    public ItooResult selectIntegralRule(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                   @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize) {
        PageInfo<IntegralRuleEntity> list = integralRuleService.selectIntegralRule(pageNo, pageSize);
        if (list.getSize() != 0) {
            return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
        } else {
            return ItooResult.build(ItooResult.FAIL, "查询失败", false);
        }
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据等级查询积分规则
     */
    @ApiOperation(value = "根据等级查询积分规则")
    @GetMapping(value = {"/selectIntegralRuleByLevel/{smallSource}"})
    public ItooResult selectIntegralRuleByLevel(@ApiParam(name = "smallSource", value = "细分来源", required = true) @PathVariable String smallSource) {
        List<IntegralRuleEntity> list = integralRuleService.selectIntegralRuleByLevel(smallSource);

            return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);

    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:修改积分规则
     */
    @ApiOperation(value = "修改积分规则")
    @PostMapping(value = {"/updateIntegralRule"})
    public ItooResult updateIntegralRule(@RequestBody IntegralRuleEntity model) {
        Boolean flag = integralRuleService.updateIntegralRule(model);
        if (flag == null) {
            return ItooResult.build(ItooResult.SUCCESS, "修改成功", true);
        } else {
            return ItooResult.build(ItooResult.FAIL, "修改失败", false);
        }
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:删除积分规则
     */
    @ApiOperation(value = "删除积分规则")
    @PostMapping(value = {"/deleteIntegralRule"})
    public ItooResult deleteIntegralRule(String id) {
        boolean flag = integralRuleService.deleteIntegralRule(id);
        if (flag == true) {
            return ItooResult.build(ItooResult.SUCCESS, "删除成功", true);
        } else {
            return ItooResult.build(ItooResult.FAIL, "删除失败", false);
        }
    }




}
