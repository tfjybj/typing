package com.tfjybj.typing.provider.controller;

/*import com.dmsdbj.itoo.sso.utils.UserUtil;*/
import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.model.CreateTeamModel;
import com.tfjybj.typing.model.FinishModel;
import com.tfjybj.typing.model.IsBeforeInBattleTeamModel;
import com.tfjybj.typing.provider.service.TeamResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



/**
 * TeamResultController
 * teamResult表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@Api(tags = {"teamResult表接口"})
@RequestMapping(value = "/teamResult")
@RestController
public class TeamResultController {

    @Resource
    private TeamResultService teamResultService;

    //region 模板生成：基本增删改

    /**
     * 添加
     *
     * @param model TeamResultModel
     * @return 添加的结果
     * @author 闫伟强
     * @since 1.0.0 2019-09-23 10:10:56
     */
//    @ApiOperation(value = "添加")
//    @PostMapping(value = {"/create"})
//    public ItooResult create(@RequestBody TeamResultModel model) {
//        TeamResultEntity teamResultEntity = new TeamResultEntity();
//        BeanUtils.copyProperties(model, teamResultEntity);
//        teamResultService.save(teamResultEntity);
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
//        teamResultService.removeById(id);
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
//        teamResultService.removeByIds(ids);
//        return ItooResult.build(ItooResult.SUCCESS, "批量删除成功");
//    }
//
//    /**
//     * 修改
//     *
//     * @param model TeamResultModel
//     * @return 修改后的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "根据id修改teamResult")
//    @PutMapping(value = {"/modify"})
//    public ItooResult modify(@RequestBody TeamResultModel model) {
//        TeamResultEntity teamResultEntity = new TeamResultEntity();
//        BeanUtils.copyProperties(model, teamResultEntity);
//        teamResultService.updateById(teamResultEntity);
//        return ItooResult.build(ItooResult.SUCCESS, "修改成功");
//    }
//
//    /**
//     * 根据id查找TeamResult
//     *
//     * @param id 主键id
//     * @return 根据id查找的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "根据id查询")
//    @GetMapping(value = {"/findById/{id}"})
//    public ItooResult findById(@ApiParam(value = "主键id", required = true) @PathVariable String id) {
//        TeamResultEntity teamResultEntity = teamResultService.getById(id);
//        return ItooResult.build(ItooResult.SUCCESS, "查询成功", teamResultEntity);
//    }
//
//    /**
//     * 分页查询所有TeamResult
//     *
//     * @param pageNo   页码
//     * @param pageSize 每页条数
//     * @return 分页查询的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "分页查询所有TeamResult")
//    @GetMapping(value = {"/queryPageAll/{pageNo}/{pageSize}"})
//    public ItooResult queryPageAll(@ApiParam(name = "pageNo",value = "页码",required = true,example = "1")@PathVariable Integer pageNo,
//								   @ApiParam(name = "pageSize",value = "页数",required = true,example = "10")@PathVariable Integer pageSize) {
//        PageInfo<TeamResultEntity> teamResults = teamResultService.queryPageAll(pageNo, pageSize);
//        return ItooResult.build(ItooResult.SUCCESS, "查询成功", teamResults);
//    }
//
    //endregion

    /* **********************************以下为非模板生成的内容********************************* */

    /**
     * 查询是否之前在战队中
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @ApiOperation(value = "查询是否之前在战队中")
    @GetMapping(value = {"/IsBeforeInBattleTeam"})
    public ItooResult IsBeforeInBattleTeam(String userId) {
        IsBeforeInBattleTeamModel IsBeforeInBattleTeamModel = teamResultService.IsBeforeInBattleTeam(userId);
        return ItooResult.build(ItooResult.SUCCESS, "查询成功", IsBeforeInBattleTeamModel);
    }

    /**
     * 组队竞赛初始化
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @ApiOperation(value = "组队竞赛初始化")
    @GetMapping(value = {"/loadBattleTeamCard"})
    public ItooResult loadBattleTeamCard(String userId) {
        return teamResultService.loadBattleTeamCard(userId);

    }

    /**
     * 完成比赛
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @ApiOperation(value = "完成比赛")
    @PostMapping(value = {"/FinalBattle"})
    public ItooResult FinalBattle(@RequestBody FinishModel finishModel) {
        teamResultService.FinalBattle(finishModel);
        return ItooResult.build(ItooResult.SUCCESS, "执行成功");
    }

    /**
     * 开始比赛
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @ApiOperation(value = "开始比赛")
    @GetMapping(value = {"/BeginBattle"})
    public ItooResult BeginBattle(String teamName) {
        teamResultService.BeginBattle(teamName);
        return ItooResult.build(ItooResult.SUCCESS, "开始成功");
    }

    /**
     * 解散战队
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @ApiOperation(value = "解散战队")
    @DeleteMapping(value = {"/BreakBattleTeam"})
    public ItooResult BreakBattleTeam(String teamName) {
        teamResultService.BreakBattleTeam(teamName);
        return ItooResult.build(ItooResult.SUCCESS, "解散成功");
    }

    /**
     * 房间界面初始化
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @ApiOperation(value = "房间界面初始化")
    @GetMapping(value = {"/TeamByUserId"})
    public ItooResult TeamByUserId(String userId) {
        return teamResultService.TeamByUserId(userId);
    }

    /**
     * 查询队员
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @ApiOperation(value = "查询队员")
    @GetMapping(value = {"/findBattleMember"})
    public ItooResult findBattleMember(String teamName) {
        return teamResultService.findBattleMember(teamName);
    }


    /**
     * 快速进入房间（输入房间）
     *
     * @param
     * @return
     * @author 闫伟强
     * @since 1.0.0 2019-09-24 8:46:09
     */
    @ApiOperation(value = "快速进入房间（输入房间）")
    @GetMapping(value = {"/joinInBattleTeamByRoomId"})
    public ItooResult joinInBattleTeamByRoomId(String userId,Integer teamNums,String userName) {
        return teamResultService.joinInBattleTeamByRoomId(userId,teamNums,userName);
    }
     /**
     * 创建战队
     *
     * @param createTeamModel 创建实体
     * @return flag 创建战队是否成功
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    @ApiOperation(value = "创建战队")
    @PostMapping(value = {"/CreateBattleTeam"})
    public ItooResult CreateBattleTeam(@RequestBody CreateTeamModel createTeamModel){
    //public ItooResult CreateBattleTeam(@RequestBody CreateTeamModel createTeamModel){

//        String userId = UserUtil.getCurrentUser().getUserId();
        String userId=createTeamModel.getUserId();
        System.out.println(userId);
        boolean flag = teamResultService.CreateBattleTeam(userId, createTeamModel);
        if(!flag){
            return ItooResult.build(ItooResult.SUCCESS,"创建战队成功");
        }
        return ItooResult.build(ItooResult.FAIL,"战队名称重复");
    }

    /**
     * 退出战队
     *
     * @param teamName 战队名称
     * @return flag 退出战队
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    @ApiOperation(value = "退出战队")
    @DeleteMapping(value = {"/QuitBattleTeam"})
    public ItooResult QuitBattleTeam(String teamName,String userId ){
        //String userId = "12345";
//        String userId = UserUtil.getCurrentUser().getUserId();

        boolean flag = teamResultService.QuitBattleTeam(teamName,userId);
        if(!flag){
            return ItooResult.build(ItooResult.FAIL,"已不在战队中");
        }
        return ItooResult.build(ItooResult.SUCCESS,"成功退出战队");
    }

    /**
     * 打字页面初始化插入成绩
     *
     * @param finishModel 成绩实体
     * @return flag 插入成绩是否成功
     * @author 谷浩樟
     * @since 1.0.0 2019年9月24日
     */
    @ApiOperation(value = "打字页面初始化插入成绩")
    @PostMapping(value = {"/BattleInfo"})
    public ItooResult BattleInfo(@RequestBody FinishModel finishModel){

//        String userId = UserUtil.getCurrentUser().getUserId();
        String userId =finishModel.getUserId();
        boolean flag = teamResultService.BattleInfo(userId,finishModel);
        if(flag == true){
            return ItooResult.build(ItooResult.SUCCESS,"成功初始化成绩");
        }
            return ItooResult.build(ItooResult.FAIL,"初始化成绩失败");


    }

    /**
     * 队长删除误操作进入房间的队友
     * @param userId 队友id
     * @param teamName 战队名称
     * @return flag 队长删除队友
     * @author 宋文轩
     * @since 1.0.0 2019年9月24日
     */
    @ApiOperation(value = "删除误操作进入房间的队友")
    @DeleteMapping(value = {"/QuitBattleTeamPerson"})
    public ItooResult QuitBattleTeamPerson(String userId,String teamName){
        boolean flag = teamResultService.QuitBattleTeam(userId,teamName);
        if(!flag){
            return ItooResult.build(ItooResult.FAIL,"不在战队中");
        }
        return ItooResult.build(ItooResult.SUCCESS,"成功删除");
    }

}
