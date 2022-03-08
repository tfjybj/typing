package com.tfjybj.typing.provider.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.entity.TeamDetailResultEntity;
import com.tfjybj.typing.model.AllTeamResult;
import com.tfjybj.typing.model.FinishModel;
import com.tfjybj.typing.model.TeamDetailResultModel;
import com.tfjybj.typing.provider.service.TeamDetailResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * TeamDetailResultController
 * teamDetailResult表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@Api(tags = {"teamDetailResult表接口"})
@RequestMapping(value = "/teamDetailResult")
@RestController
public class TeamDetailResultController {

    @Resource
    private TeamDetailResultService teamDetailResultService;

    //region 模板生成：基本增删改

    /**
     * 添加
     *
     * @param model TeamDetailResultModel
     * @return 添加的结果
     * @author 闫伟强
     * @since 1.0.0 2019-09-23 10:10:56
     */
//    @ApiOperation(value = "添加")
//    @PostMapping(value = {"/create"})
//    public ItooResult create(@RequestBody TeamDetailResultModel model) {
//        TeamDetailResultEntity teamDetailResultEntity = new TeamDetailResultEntity();
//        BeanUtils.copyProperties(model, teamDetailResultEntity);
//        teamDetailResultService.save(teamDetailResultEntity);
//        return ItooResult.build(ItooResult.SUCCESS, "添加成功");
//    }
//
//    /**
//     * 删除
//     *
//     * @param id 主键id
//     * @return ItooResult 是否删除成功
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "根据id删除（单个）")
//    @DeleteMapping(value = {"/delete/{id}"})
//    public ItooResult delete(@ApiParam(value = "主键id", required = true) @PathVariable String id) {
//        teamDetailResultService.removeById(id);
//        return ItooResult.build(ItooResult.SUCCESS, "删除成功");
//    }
//
//    /**
//     * 批量删除
//     *
//     * @param ids ids
//     * @return ItooResult 批量删除是否成功结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "根据id批量删除")
//    @DeleteMapping(value = {"/deleteByIds"})
//    @ApiImplicitParam(name = "ids", value = "ids", dataType = "List<String>", required = true)
//    public ItooResult deleteByIds(@RequestBody List<String> ids) {
//        teamDetailResultService.removeByIds(ids);
//        return ItooResult.build(ItooResult.SUCCESS, "批量删除成功");
//    }
//
//    /**
//     * 修改
//     *
//     * @param model TeamDetailResultModel
//     * @return 修改后的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "根据id修改teamDetailResult")
//    @PutMapping(value = {"/modify"})
//    public ItooResult modify(@RequestBody TeamDetailResultModel model) {
//        TeamDetailResultEntity teamDetailResultEntity = new TeamDetailResultEntity();
//        BeanUtils.copyProperties(model, teamDetailResultEntity);
//        teamDetailResultService.updateById(teamDetailResultEntity);
//        return ItooResult.build(ItooResult.SUCCESS, "修改成功");
//    }
//
//    /**
//     * 根据id查找TeamDetailResult
//     *
//     * @param id 主键id
//     * @return 根据id查找的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "根据id查询")
//    @GetMapping(value = {"/findById/{id}"})
//    public ItooResult findById(@ApiParam(value = "主键id", required = true) @PathVariable String id) {
//        TeamDetailResultEntity teamDetailResultEntity = teamDetailResultService.getById(id);
//        return ItooResult.build(ItooResult.SUCCESS, "查询成功", teamDetailResultEntity);
//    }
//
//    /**
//     * 分页查询所有TeamDetailResult
//     *
//     * @param pageNo   页码
//     * @param pageSize 每页条数
//     * @return 分页查询的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "分页查询所有TeamDetailResult")
//    @GetMapping(value = {"/queryPageAll/{pageNo}/{pageSize}"})
//    public ItooResult queryPageAll(@ApiParam(name = "pageNo",value = "页码",required = true,example = "1")@PathVariable Integer pageNo,
//								   @ApiParam(name = "pageSize",value = "页数",required = true,example = "10")@PathVariable Integer pageSize) {
//        PageInfo<TeamDetailResultEntity> teamDetailResults = teamDetailResultService.queryPageAll(pageNo, pageSize);
//        return ItooResult.build(ItooResult.SUCCESS, "查询成功", teamDetailResults);
//    }
//
    //endregion

    /* **********************************以下为非模板生成的内容********************************* */

    /**
     * 是否之前在战队中
     *
     * @param id
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping(value = {"/findById"})
    public ItooResult findById(@ApiParam(value = "主键id", required = true) String id) {
        TeamDetailResultEntity teamDetailResultEntity = teamDetailResultService.getById(id);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", teamDetailResultEntity);
    }

    /**
     * 查询战队英雄榜
     *
     * @param
     * @return
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    @ApiOperation(value = "查询战队英雄榜")
    @GetMapping(value = {"/TeamResult"})
    public ItooResult TeamResult(){
        List<AllTeamResult> allTeamResults = teamDetailResultService.TeamResult();
        if(CollectionUtils.isEmpty(allTeamResults)){
            return ItooResult.build(ItooResult.FAIL,"战队英雄榜暂无数据");
        }
        return ItooResult.build(ItooResult.SUCCESS,"查询成功",allTeamResults);
    }


    /**
     * 查询组内成绩
     *
     * @param teamName 战队名称
     * @return List<FinishModel>
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    @ApiOperation(value = "查询组内成绩")
    @GetMapping(value = "/TeamDetailResult")
    public ItooResult TeamDetailResult(String teamName){
        List<FinishModel> finishModels =  teamDetailResultService.TeamDetailResult(teamName);
        if(CollectionUtils.isEmpty(finishModels)){
            return ItooResult.build(ItooResult.FAIL,"查无此战队");
        }
        return ItooResult.build(ItooResult.SUCCESS,"查询成功",finishModels);
    }

    /**
     * 查询团队进步排名前十的队伍
     * @return
     */
    @ApiOperation(value = "查询团队进步排名前十的队伍")
    @PostMapping (value = "/selectTeamGrowthChart")
    public ItooResult selectTeamGrowthChart(@RequestBody List<String> className)
    {
        return  teamDetailResultService.selectTeamGrowthChart(className);
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time:2021年10月11日10:53:12
     * @description:添加邀请助力的成绩
     */
    @ApiOperation(value = "添加邀请助力的成绩")
    @PostMapping(value = {"/insertTeamDetailResult"})
    public ItooResult insertTeamDetailResult(@RequestBody TeamDetailResultModel model) {

        boolean flag = teamDetailResultService.insertTeamDetailResult(model);
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
     * @description:教师端查看邀请助力的详细成绩
     */
    @ApiOperation(value = "教师端查看邀请助力的详细成绩")
    @GetMapping(value = "/selectTeamByTimeClass/{beginTime}/{endTime}/{className}")
    public ItooResult selectTeamByTimeClass(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String  beginTime,
                                           @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String  endTime,
                                           @ApiParam(name = "className", value = "班级") @PathVariable String className) {
        return teamDetailResultService.selectTeamByTimeClass(beginTime, endTime, className);
    }

    /**
     * @author: 宋文轩
     * @version:
     * @param: []
     * @date:2021年11月13日
     * @time:2021年11月13日21:14:51
     * @description:学生端查看邀请助力的详细成绩
     */
    @ApiOperation(value = "学生端查看邀请助力的详细成绩")
    @GetMapping(value = "/selectTeamByTimeAndUserCode/{beginTime}/{endTime}/{userCode}")
    public ItooResult selectTeamByTimeAndUserCode(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String  beginTime,
                                            @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String  endTime,
                                            @ApiParam(name = "userCode", value = "用户code") @PathVariable String userCode) {
        return teamDetailResultService.selectTeamByTimeAndUserCode(beginTime, endTime, userCode);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:学生端查看邀请助力的次数
     */
    @ApiOperation(value = "学生端查看邀请助力的次数")
    @GetMapping(value = "/selectByTimeUserCode/{userCode}/{createTime}")
    public ItooResult selectByTimeUserCode(@ApiParam(name = "userCode", value = "学号", required = true) @PathVariable String  userCode,
                                            @ApiParam(name = "createTime", value = "创建日期", required = true) @PathVariable String  createTime) {
        return teamDetailResultService.selectByTimeUserCode(userCode, createTime);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端查看邀请助力的详细成绩
     */
    @ApiOperation(value = "教师端查看邀请助力的详细成绩")
    @GetMapping(value = "/selectTeamByTime/{beginTime}/{endTime}")
    public ItooResult selectTeamByTime(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String  beginTime,
                                            @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String  endTime) {
        return teamDetailResultService.selectTeamByTime(beginTime, endTime);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据时间和学号查询邀请助力数据
     */
    @ApiOperation(value = "根据时间和学号查询邀请助力数据")
    @GetMapping(value = "/selectTeamByTimeUserCode/{beginTime}/{endTime}/{userCode}")
    public ItooResult selectTeamByTimeUserCode(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String  beginTime,
                                       @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String  endTime,
                                               @ApiParam(name = "userCode", value = "学号", required = true) @PathVariable String  userCode) {
        return teamDetailResultService.selectTeamByTimeUserCode(beginTime, endTime,userCode);
    }

}
