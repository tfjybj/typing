package com.tfjybj.typing.provider.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.PersonResultEntity;
import com.tfjybj.typing.model.ImproveReportModel;
import com.tfjybj.typing.model.PersonIntegralModel;
import com.tfjybj.typing.model.PersonResultModel;
import com.tfjybj.typing.provider.service.PersonResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * PersonResultController
 * personResult表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@Api(tags = {"personResult表接口"})
@RequestMapping(value = "/personResult")
@RestController
public class PersonResultController {

//    @Resource
    @Autowired
    private PersonResultService personResultService;

    //region 模板生成：基本增删改

    /**
     * 添加
     *
     * @param model PersonResultModel
     * @return 添加的结果
     * @author 闫伟强
     * @since 1.0.0 2019-09-23 10:10:56
     */
//    @ApiOperation(value = "添加")
//    @PostMapping(value = {"/create"})
//    public ItooResult create(@RequestBody PersonResultModel model) {
//        PersonResultEntity personResultEntity = new PersonResultEntity();
//        BeanUtils.copyProperties(model, personResultEntity);
//        personResultService.save(personResultEntity);
//        Boolean OK = personResultService.insertPersonResult(personResultEntity);
//        return ItooResult.build(ItooResult.SUCCESS, "添加成功");
//    }

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
//        personResultService.removeById(id);
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
//        personResultService.removeByIds(ids);
//        return ItooResult.build(ItooResult.SUCCESS, "批量删除成功");
//    }
//
//    /**
//     * 修改
//     *
//     * @param model PersonResultModel
//     * @return 修改后的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "根据id修改personResult")
//    @PutMapping(value = {"/modify"})
//    public ItooResult modify(@RequestBody PersonResultModel model) {
//        PersonResultEntity personResultEntity = new PersonResultEntity();
//        BeanUtils.copyProperties(model, personResultEntity);
//        personResultService.updateById(personResultEntity);
//        return ItooResult.build(ItooResult.SUCCESS, "修改成功");
//    }
//
//    /**
//     * 根据id查找PersonResult
//     *
//     * @param id 主键id
//     * @return 根据id查找的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "根据id查询")
//    @GetMapping(value = {"/findById/{id}"})
//    public ItooResult findById(@ApiParam(value = "主键id", required = true) @PathVariable String id) {
//        PersonResultEntity personResultEntity = personResultService.getById(id);
//        return ItooResult.build(ItooResult.SUCCESS, "查询成功", personResultEntity);
//    }
//
//    /**
//     * 分页查询所有PersonResult
//     *
//     * @param pageNo   页码
//     * @param pageSize 每页条数
//     * @return 分页查询的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "分页查询所有PersonResult")
//    @GetMapping(value = {"/queryPageAll/{pageNo}/{pageSize}"})
//    public ItooResult queryPageAll(@ApiParam(name = "pageNo",value = "页码",required = true,example = "1")@PathVariable Integer pageNo,
//								   @ApiParam(name = "pageSize",value = "页数",required = true,example = "10")@PathVariable Integer pageSize) {
//        PageInfo<PersonResultEntity> personResults = personResultService.queryPageAll(pageNo, pageSize);
//        return ItooResult.build(ItooResult.SUCCESS, "查询成功", personResults);
//    }

    //endregion

    /* **********************************以下为非模板生成的内容********************************* */

    /**
     * 添加打字成绩
     *
     * @param model personResultModel
     * @return 添加打字成绩
     * @author 王闯
     * @since ${version} 2019-09-21 10:35:06
     */
    @ApiOperation(value = "添加")
    @PostMapping(value = {"/insertPersonResult"})
    public ItooResult insertPersonResult(@RequestBody PersonResultEntity model) {

        boolean flag = personResultService.insertPersonResult(model);
        if (flag == true) {
            return ItooResult.build(ItooResult.SUCCESS, "添加成功", true);
        } else {
            return ItooResult.build(ItooResult.FAIL, "添加失败", false);
        }

    }


    /**
     * 分页查询所有个人成绩
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return 分页查询的结果
     * @author 王闯
     * @since ${version} 2019-09-23 10:44:52
     */
    @ApiOperation(value = "分页查询所有个人成绩")
    @GetMapping(value = {"/selectPersonResult/{userId}/{pageNo}/{pageSize}"})
    public ItooResult selectPersonResult(@ApiParam(name = "userId", value = "用户id", required = true, example = "") @PathVariable String userId,
                                         @ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                         @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize) {
        PageInfo<PersonResultEntity> personResult = personResultService.selectPersonResult(userId, pageNo, pageSize);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", personResult);
    }


    @ApiOperation(value = "查询每个人的积分记录")
    @GetMapping(value = {"/selectPersonIntegral/{usercode}"})
    public ItooResult selectPersonIntegral(@ApiParam(name = "usercode", value = "用户id", required = true, example = "") @PathVariable String usercode) {
//        String userId = UserUtil.getCurrentUser().getUserId();
        PersonIntegralModel personIntegralModel = personResultService.selectPersonIntegral(usercode);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", personIntegralModel);
    }

    /**
     * 折线图查询个人打字速度和正确率
     *
     * @return 折线图查询结果
     * @author Yara
     * @date 2020年10月26日11:29:49
     */
    @ApiOperation(value = "折线图查询个人打字速度和正确率")
    @GetMapping(value = {"/selectPersonRateAndSpeed/{userId}"})
    public ItooResult selectPersonRateAndSpeed(@ApiParam(name = "userId", value = "用户id", required = true, example = "") @PathVariable String userId) {
        List<PersonResultEntity> list = personResultService.selectPersonRateAndSpeed(userId);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月26日11:37:55
     * @time: 2021年10月26日
     * @description:根据查询个人打字速度和正确率
     */
    @ApiOperation(value = "根据查询个人打字速度和正确率")
    @GetMapping(value = {"/selectPersonRateAndSpeedAVG/{userCode}"})
    public ItooResult selectPersonRateAndSpeedAVG(@ApiParam(name = "userCode", value = "学号", required = true, example = "") @PathVariable String userCode) {
        List<PersonResultModel> list = personResultService.selectPersonRateAndSpeedAVG(userCode);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
    }

    /**
     * @author: 郝龙飞
     * @version:
     * @param: []
     * @date: 2020/10/27
     * @time: 19:30
     * @description:
     */
    @ApiOperation(value = "查询进步")
    @GetMapping(value = {"/selectPersonProgress/{pageNo}/{pageSize}"})
    public ItooResult selectPersonProgress(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                           @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize) {
        List list = personResultService.selectPersonProgress(pageNo, pageSize);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:根据班级查询排行榜
     */
    @ApiOperation(value = "根据班级查询排行榜")
    @GetMapping(value = {"/selectPersonClassProgress/{pageNo}/{pageSize}/{className}"})
    public ItooResult selectPersonClassProgress(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                                @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize,
                                                @ApiParam(name = "className", value = "班级名称", required = true) @PathVariable String className) {
        List list = personResultService.selectPersonClassProgress(pageNo, pageSize, className);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:
     */
    @ApiOperation(value = "根据班级查询进步排行榜,班级模糊查询")
    @GetMapping(value = {"/selectPersonClassProgressClass/{pageNo}/{pageSize}"})
    public ItooResult selectPersonClassProgressClass(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                                @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize) {
        List list = personResultService.selectPersonClassProgressClass(pageNo, pageSize);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
    }



    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:
     */
    @ApiOperation(value = "根据分组查询进步")
    @GetMapping(value = {"/selectPersonTeamProgress/{pageNo}/{pageSize}/{teamId}/{className}"})
    public ItooResult selectPersonTeamProgress(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                               @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize,
                                               @ApiParam(name = "teamId", value = "分组名称", required = true) @PathVariable String teamId,
                                               @ApiParam(name = "className", value = "班级名称", required = true) @PathVariable String className) {
        List list = personResultService.selectPersonTeamProgress(pageNo, pageSize, teamId,className);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
    }

    /**
     * @author: 郝龙飞
     * @version:
     * @param: []
     * @date: 2020/10/27
     * @time: 19:30
     * @description:
     */
    @ApiOperation(value = "查询整体排名")
    @GetMapping(value = {"/selectPersonRanking/{pageNo}/{pageSize}"})
    public ItooResult selectPersonRanking(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                          @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize) {
        List list = personResultService.selectPersonRanking(pageNo, pageSize);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
    }

    /**
     * @author: 张艳伟
     * @version:根据班级查询进步英雄榜排名
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:
     */
    @ApiOperation(value = "根据班级查询进步英雄榜排名")
    @GetMapping(value = {"/selectPersonClassRanking/{pageNo}/{pageSize}/{className}"})
    public ItooResult selectPersonClassRanking(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                               @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize,
                                               @ApiParam(name = "className", value = "班级名称", required = true) @PathVariable String className) {
        List list = personResultService.selectPersonClassRanking(pageNo, pageSize, className);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月27日
     * @time: 2021年9月27日08:28:46
     * @description:
     */
    @ApiOperation(value = "根据班级查询排名-班级模糊查询")
    @GetMapping(value = {"/selectPersonClassRankingClass/{pageNo}/{pageSize}"})
    public ItooResult selectPersonClassRankingClass(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                               @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize) {
        List list = personResultService.selectPersonClassRankingClass(pageNo, pageSize);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:
     */
    @ApiOperation(value = "根据分组查询排名")
    @GetMapping(value = {"/selectPersonTeamRanking/{pageNo}/{pageSize}/{teamId}/{className}"})
    public ItooResult selectPersonTeamRanking(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                              @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize,
                                              @ApiParam(name = "teamId", value = "分组名称", required = true) @PathVariable String teamId,
                                              @ApiParam(name = "className", value = "班级名称", required = true) @PathVariable String className) {
        List list = personResultService.selectPersonTeamRanking(pageNo, pageSize, teamId,className);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
    }




    /**
     * @return
     * @Author Yara
     */
    @ApiOperation(value = "查询晋级人员名单——播报")
    @GetMapping(value = {"/selectPersonImprovement"})
    public ItooResult selectPersonImprovement() {
        List<String> report = personResultService.selectPersonImprovement();
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", report);
    }


    /**
     * @return
     * @Author Yara
     */
    @ApiOperation(value = "晋级人员信息显示——滚屏")
    @GetMapping(value = {"/selectImprovementList"})
    public ItooResult selectImprovementList() {
        List<ImproveReportModel> list = personResultService.selectImprovement();
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
    }


    /**
     * 查询各个班级每个段位的人数
     *
     * @return
     */
    @ApiOperation(value = "查询各个班级每个段位的人数")
    @PostMapping(value = {"/selectClassLevelCount"})
    public ItooResult selectClassLevelCount(@RequestBody List<String> className) {

        return personResultService.selectClassLevelCount(className);
    }

    @ApiOperation(value = "查询每个班级当天的进级人数")
    @GetMapping(value = {"/selectRiseCount"})
    public ItooResult selectRiseCount() {
        return personResultService.selectRiseCount();
    }


    @ApiOperation(value = "查询团队积分排名")
    @PostMapping(value = {"/selectTeamRanking"})
    public ItooResult selectTeamRanking(@RequestBody List<String> className) {
        return personResultService.selectTeamRanking(className);
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:查询全部未练习人员
     */
    @ApiOperation(value = "查询全部未练习人员")
    @GetMapping(value = "/selectAllNotRanking")
    public ItooResult selectAllNotRanking() {
        return personResultService.selectAllNotRanking();
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:根据班级查询全部未练习人员
     */
    @ApiOperation(value = "根据班级查询全部未练习人员")
    @GetMapping(value = "/selectAllNotRankingByClass/{className}")
    public ItooResult selectAllNotRankingByClass(@ApiParam(name = "className", value = "班级名称") @PathVariable String className) {
        return personResultService.selectAllNotRankingByClass(className);
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:查询全部积分排名-AR奖状接口
     */
    @ApiOperation(value = "查询全部积分排名-AR奖状接口")
    @GetMapping(value = "/selectAllTeamRanking")
    public ItooResult selectAllTeamRanking() {
        return personResultService.selectAllTeamRanking();
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:根据班级查询积分排名--查询晋级榜单
     */
    @ApiOperation(value = "根据班级查询积分排名")
    @GetMapping(value = "/selectAllTeamRankingByClass/{className}")
    public ItooResult selectAllTeamRankingByClass(@ApiParam(name = "className", value = "班级名称") @PathVariable String className) {
        return personResultService.selectAllTeamRankingByClass(className);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月15日
     * @time: 2021年11月15日11:35:01
     * @description:根据等级查询积分排名-晋级榜单
     */
    @ApiOperation(value = "根据等级查询积分排名-晋级榜单")
    @GetMapping(value = "/selectAllTeamRankingByLevel/{level}")
    public ItooResult selectAllTeamRankingByLevel(@ApiParam(name = "level", value = "等级") @PathVariable String level) {
        return personResultService.selectAllTeamRankingByLevel(level);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月15日
     * @time: 2021年11月15日11:35:01
     * @description:根据班级和等级查询积分排名-晋级榜单
     */
    @ApiOperation(value = "根据班级和等级查询积分排名-晋级榜单")
    @GetMapping(value = "/selectAllTeamRankingByClassLevel/{className}/{level}")
    public ItooResult selectAllTeamRankingByClassLevel(
            @ApiParam(name = "className", value = "班级名称") @PathVariable String className,
            @ApiParam(name = "level", value = "等级") @PathVariable String level) {
        return personResultService.selectAllTeamRankingByClassLevel(className,level);
    }

    @ApiOperation(value = "按照条件查询个人成绩")
    @GetMapping(value = "/selectRankingByTimeLevel/{beginTime}/{endTime}/{level}/{className}")
    public ItooResult selectRankingByTimeLevel(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                               @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                               @ApiParam(name = "level", value = "等级", required = false) @PathVariable String level,
                                               @ApiParam(name = "className", value = "班级名称") @PathVariable String className) {
        return personResultService.selectRankingByTimeLevel(beginTime, endTime, level, className);
    }
    /**
     * @author: 张艳伟
     * @version:按照条件查询成绩-班级模糊查询
     * @param: []
     * @date: 2021年9月26日
     * @time: 2021年9月26日19:54:10
     * @description:
     */
    @ApiOperation(value = "按照条件查询个人成绩-班级模糊查询")
    @GetMapping(value = "/selectRankingByTimeLevelClass/{beginTime}/{endTime}/{level}")
    public ItooResult selectRankingByTimeLevelClass(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                               @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                               @ApiParam(name = "level", value = "等级", required = false) @PathVariable String level)
                                           {
        return personResultService.selectRankingByTimeLevelClass(beginTime, endTime, level);
    }
    /**
     * @author: 张艳伟
     * @version:按照时间查询成绩-班级模糊查询
     * @param: []
     * @date: 2021年9月26日
     * @time: 2021年9月26日19:54:10
     * @description:
     */
    @ApiOperation(value = "按照时间查询全部成绩-班级模糊查询")
    @GetMapping(value = "/selectRankingByTimeLevelAllClass/{beginTime}/{endTime}")
    public ItooResult selectRankingByTimeLevelAllClass(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                                  @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime)
                                                   {
        return personResultService.selectRankingByTimeLevelAllClass(beginTime, endTime);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月22日
     * @time: 2021年9月22日08:08:35
     * @description:
     */
    @ApiOperation(value = "按照时间查询班级全部成绩")
    @GetMapping(value = "/selectRankingByTimeLevelAll/{beginTime}/{endTime}/{className}")
    public ItooResult selectRankingByTimeLevelAll(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                                  @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                                  @ApiParam(name = "className", value = "班级") @PathVariable String className) {
        return personResultService.selectRankingByTimeLevelAll(beginTime, endTime, className);
    }

    @ApiOperation(value = "查询当前用户等级")
    @GetMapping(value = {"/selectGradeAndNum"})
//    public List<GradeModel> selectGradeAndNum(@ApiParam(value = "学号", required = true) @RequestParam String userCode) {
//        return personResultService.selectGradeAndNum(userCode);
//    }
    public ItooResult selectGradeAndNum(@ApiParam(value = "学号", required = true) @RequestParam String userCode) {
        return personResultService.selectGradeAndNum(userCode);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:查询当前打字次数
     */
    @ApiOperation(value = "查询当前打字次数")
    @GetMapping(value = {"/selectPersonResultSum/{userId}"})

    public ItooResult selectPersonResultSum(@ApiParam(value = "userId", required = true) @RequestParam String userId) {
        return personResultService.selectPersonResultSum(userId);
    }

    /*
     * @version V1.0
     * Title: selectClass
     * @author LiuYanQiang
     * @description 查询当前用户的所在班级信息
     * @createTime  2021/9/19 17:52
     * @param [userCode]
     * @return com.dmsdbj.itoo.tool.business.ItooResult*/
    @ApiOperation(value = "查询当前用户所在班级")
    @GetMapping(value = {"/selectClass"})
    public ItooResult selectClass(@ApiParam(value = "学号", required = true) @RequestParam String userCode) {
        return personResultService.selectClass(userCode);
    }

    /**
     * @author: 宋文轩
     * @version:
     * @param: []
     * @date: 2021年10月5日
     * @time: 2021年10月5日19:20:40
     * @description:按照时间查询个人今天有没有邀请过助力
     */
    @ApiOperation(value = "按照时间查询个人今天有没有邀请过助力")
    @GetMapping(value = "/selectImprovedByTime/{beginTime}/{endTime}/{userId}")
    public ItooResult selectImprovedByTime(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                           @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                           @ApiParam(name = "userId", value = "用户id") @PathVariable String userId) {
        return personResultService.selectImprovedByTime(beginTime, endTime, userId);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月5日
     * @time: 2021年10月5日23:42:46
     * @description:教师端按照时间查询助力次数
     */
    @ApiOperation(value = "教师端按照时间查询助力次数")
    @GetMapping(value = "/selectImproved/{beginTime}/{endTime}")
    public ItooResult selectImproved(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                                       @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime)
    {
        return personResultService.selectImproved(beginTime, endTime);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月13日
     * @time: 2021年11月13日19:58:02
     * @description:教师端按照时间查询助力总次数
     */
    @ApiOperation(value = "教师端按照时间查询助力总次数")
    @GetMapping(value = "/selectAllImproved/{beginTime}/{endTime}")
    public ItooResult selectAllImproved(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                     @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime)
    {
        return personResultService.selectAllImproved(beginTime, endTime);
    }

    @ApiOperation(value = "根据时间和学号查询打字次数")
    @GetMapping(value = "/selectNumByTime/{beginTime}/{endTime}/{userCode}")
    public ItooResult selectNumByTime(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                     @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                      @ApiParam(name = "userCode", value = "学号", required = true) @PathVariable String userCode)
    {
        return personResultService.selectNumByTime(beginTime, endTime,userCode);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月5日
     * @time: 2021年10月5日23:42:46
     * @description:教师端按照时间班级查询助力次数
     */
    @ApiOperation(value = "教师端按照时间和班级查询助力次数")
    @GetMapping(value = "/selectImprovedByClass/{beginTime}/{endTime}/{className}")
    public ItooResult selectImprovedByClass(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                     @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                     @ApiParam(name = "className", value = "班级名称", required = true) @PathVariable String className)
    {
        return personResultService.selectImprovedByClass(beginTime, endTime,className);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月13日
     * @time: 2021年11月13日20:19:38
     * @description:教师端按照时间和班级查询助力总次数
     */
    @ApiOperation(value = "教师端按照时间和班级查询助力总次数")
    @GetMapping(value = "/selectAllImprovedByClass/{beginTime}/{endTime}/{className}")
    public ItooResult selectAllImprovedByClass(@ApiParam(name = "beginTime", value = "开始时间", required = true) @PathVariable String beginTime,
                                            @ApiParam(name = "endTime", value = "结束时间", required = true) @PathVariable String endTime,
                                            @ApiParam(name = "className", value = "班级名称", required = true) @PathVariable String className)
    {
        return personResultService.selectAllImprovedByClass(beginTime, endTime,className);
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time: 2021年10月11日20:05:56
     * @description:根据学号查询班级前三名
     */
    @ApiOperation(value = "根据学号查询班级前三名")
    @GetMapping(value = "/selectClassTop/{userCode}")
    public ItooResult selectClassTop(  @ApiParam(name = "userCode", value = "学号", required = true) @PathVariable String userCode)
    {
        return personResultService.selectClassTop(userCode);
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time: 2021年10月11日20:05:56
     * @description:根据学号查看班级内小组前三名
     */
    @ApiOperation(value = "根据学号查看班级内小组前三名")
    @GetMapping(value = "/selectTeamTop/{userCode}")
    public ItooResult selectTeamTop(  @ApiParam(name = "userCode", value = "学号", required = true) @PathVariable String userCode)
    {
        return personResultService.selectTeamTop(userCode);
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月22日
     * @time: 2021年10月22日19:16:40
     * @description:根据userId查询天数
     */
    @ApiOperation(value = "根据userId查询天数")
    @GetMapping(value = "/selectByUserId/{userId}")
    public ItooResult selectByUserId(  @ApiParam(name = "userId", value = "userId", required = true) @PathVariable String userId)
    {
        return personResultService.selectByUserId(userId);
    }


}
