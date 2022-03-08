package com.tfjybj.typing.provider.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.LevelEntity;
import com.tfjybj.typing.provider.service.LevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/11/26
 * Time: 20:09
 * Description:${DESCRIPTION}
 */
@Api(tags = {"ty_level表接口"})
@RequestMapping(value = "/level")
@RestController
public class LevelController {

    @Resource
    private LevelService levelService;

    @ApiOperation(value = "添加")
    @PostMapping(value = {"/insertLevel"})
    public ItooResult insertPersonTeam(@RequestBody LevelEntity model) {
        boolean flag = levelService.insertLevel(model);
        if (flag == true) {
            return ItooResult.build(ItooResult.SUCCESS,"添加成功",true);
        }else {
            return ItooResult.build(ItooResult.FAIL,"添加失败",false);
        }

    }
    @ApiOperation(value = "删除")
    @PostMapping(value = {"/deleteLevel"})
    public ItooResult deletePersonTeam(String  id) {
        boolean flag = levelService.deleteLevel(id);
        if (flag == true) {
            return ItooResult.build(ItooResult.SUCCESS,"删除成功",true);
        }else {
            return ItooResult.build(ItooResult.FAIL,"删除失败",false);
        }

    }
    @ApiOperation(value = "修改")
    @PostMapping(value = {"/updateLevel"})
    public ItooResult updatePersonTeam(@RequestBody LevelEntity model) {
        Boolean flag = levelService.updateLevel(model);
        if ( flag == null) {
            return ItooResult.build(ItooResult.SUCCESS,"修改成功",true);
        }else {
            return ItooResult.build(ItooResult.FAIL,"修改失败",false);
        }

    }
    @ApiOperation(value = "查询")
    @GetMapping(value = {"/querLevel/{pageNo}/{pageSize}"})
    public ItooResult querPersonTeam(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                     @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize) {
        PageInfo<LevelEntity> list = levelService.querLevel( pageNo, pageSize);
        if (list.getSize() != 0) {
            return ItooResult.build(ItooResult.SUCCESS,"查询成功",list);
        }else {
            return ItooResult.build(ItooResult.FAIL,"查询失败",false);
        }

    }

}
