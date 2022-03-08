package com.tfjybj.typing.provider.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.model.LotteryModel;
import com.tfjybj.typing.provider.service.LotteryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: swx
 * @Date: 2021/10/21
 * @Modified By:
 * @Modify Date:
 */
@Api(tags = {"ty_lottery表接口"})
@RequestMapping(value = "/lottery")
@RestController
public class LotteryController {
    @Resource
    private LotteryService lotteryService;

    @ApiOperation(value = "添加抽奖结果")
    @PostMapping(value = {"/insertLottery"})
    public ItooResult insertLottery(@RequestBody LotteryModel model) {
        boolean flag = lotteryService.insertLottery(model);
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
     * @description:根据学号查询抽奖总次数
     */

    @ApiOperation(value = "按照学号查询抽奖总次数")
    @GetMapping(value = "/selectByUserCode/{userCode}")
    public ItooResult selectByUserCode(@ApiParam(name = "userCode", value = "学号") @PathVariable String userCode) {
        return lotteryService.selectByUserCode(userCode);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:按照学号查询奖励抽奖次数
     */
    @ApiOperation(value = "按照学号查询奖励抽奖次数")
    @GetMapping(value = "/selectLotteryByUserCode/{userCode}")
    public ItooResult selectLotteryByUserCode(@ApiParam(name = "userCode", value = "学号") @PathVariable String userCode) {
        return lotteryService.selectLotteryByUserCode(userCode);
    }

}
