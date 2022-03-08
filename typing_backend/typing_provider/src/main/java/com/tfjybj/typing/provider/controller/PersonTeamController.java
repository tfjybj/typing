package com.tfjybj.typing.provider.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.PersonEntity;
import com.tfjybj.typing.model.ClassNameModel;
import com.tfjybj.typing.model.PersonTeamModel;
import com.tfjybj.typing.provider.service.PersonTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/11/21
 * Time: 17:14
 * Description:${DESCRIPTION}
 */
@Api(tags = {"ty_person表接口"})
@RequestMapping(value = "/personTeam")
@RestController
public class PersonTeamController {

    @Resource
    private PersonTeamService personTeamService;

    @ApiOperation(value = "添加")
    @PostMapping(value = {"/insertPersonTeam"})
    public ItooResult insertPersonTeam(@RequestBody PersonEntity model) {
        boolean flag = personTeamService.insertPersonTeam(model);
        if (flag == true) {
            return ItooResult.build(ItooResult.SUCCESS, "添加成功", true);
        } else {
            return ItooResult.build(ItooResult.FAIL, "添加失败", false);
        }
    }


    @ApiOperation(value = "批量添加")
    @PostMapping(value = {"/batchInsertPersonTeam"})
    public ItooResult batchInsertPersonTeam(@RequestBody List<PersonTeamModel> list) {
        try {
//            List<PersonTeamModel> list = basePersonTeamModel.getPersonTeamModel();

            //校验数据
            personTeamService.checkExcel(list);

            personTeamService.importExcel(list);

        } catch (Exception e) {
            e.printStackTrace();
            return ItooResult.build(ItooResult.FAIL, e.getMessage());
        }
        return ItooResult.build(ItooResult.SUCCESS, "添加成功", true);
    }


    @ApiOperation(value = "批量添加")
    @RequestMapping(value = {"/insertPersonTeamAll"}, method = RequestMethod.POST)
    public ItooResult insertPersonTeamAll(HttpServletResponse response, MultipartFile file) throws Exception {

        personTeamService.getExcel(file);
        boolean flag = true;
        if (flag == true) {
            return ItooResult.build(ItooResult.SUCCESS, "添加成功", true);
        } else {
            return ItooResult.build(ItooResult.FAIL, "添加失败", false);
        }

    }

    @ApiOperation(value = "删除")
    @PostMapping(value = {"/deletePersonTeam"})
    public ItooResult deletePersonTeam(@RequestBody PersonEntity model) {
        boolean flag = personTeamService.deletePersonTeam(model);
        if (flag == true) {
            return ItooResult.build(ItooResult.SUCCESS, "删除成功", true);
        } else {
            return ItooResult.build(ItooResult.FAIL, "删除失败", false);
        }

    }

    @ApiOperation(value = "修改")
    @PostMapping(value = {"/updatePersonTeam"})
    public ItooResult updatePersonTeam(@RequestBody PersonEntity model) {
        boolean flag = personTeamService.updatePersonTeam(model);
        if (flag == true) {
            return ItooResult.build(ItooResult.SUCCESS, "修改成功", true);
        } else {
            return ItooResult.build(ItooResult.FAIL, "修改失败", false);
        }

    }

    @ApiOperation(value = "查询")
    @GetMapping(value = {"/querPersonTeam/{pageNo}/{pageSize}"})
    public ItooResult querPersonTeam(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                     @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize) {
        PageInfo<PersonEntity> list = personTeamService.querPersonTeam(pageNo, pageSize);
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
     * @description:根据分组名称查询分组人员名单
     */
    @ApiOperation(value = "根据分组名称查询分组人员名单")
    @GetMapping(value = {"/selectPersonTeam/{teamName}"})
    public ItooResult selectPersonTeam(@ApiParam(name = "teamName", value = "组名", required = true) @PathVariable String teamName) {
        PageInfo<PersonEntity> list = personTeamService.selectPersonTeam(teamName);
        if (list.getSize() != 0) {
            return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);
        } else {
            return ItooResult.build(ItooResult.FAIL, "查询失败", false);
        }
    }


    @ApiOperation(value = "查询班级")
    @GetMapping(value = {"/querClassName/{pageNo}/{pageSize}"})
    public ItooResult querPersonTeamClassName(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                     @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize) {
        PageInfo<ClassNameModel> list = personTeamService.queryClassName(pageNo, pageSize);
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
     * @date:2021年11月29日
     * @time:2021年11月29日08:45:25
     * @description:查询展示的班级
     */
    @ApiOperation(value = "查询展示的班级")
    @GetMapping(value = {"/selectClassName"})
    public ItooResult selectClassName()  {
        List<ClassNameModel> list = personTeamService.selectClassName();
            return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);

    }

    @ApiOperation(value = "更新班级为删除")
    @PostMapping(value = {"/updateClassNameIsDelete"})
    public ItooResult updateClassNameIsDelete(@RequestBody String className) {
        Boolean flag = personTeamService.updateClassNameIsDelete(className);
        if ( flag == true) {
            return ItooResult.build(ItooResult.SUCCESS,"修改成功",true);
        }else {
            return ItooResult.build(ItooResult.FAIL,"修改失败",false);
        }
    }

    @ApiOperation(value = "更新班级为显示")
    @PostMapping(value = {"/updateClassName"})
    public ItooResult updateClassName(@RequestBody String className) {
        Boolean flag = personTeamService.updateClassName(className);
        if ( flag == true) {
            return ItooResult.build(ItooResult.SUCCESS,"修改成功",true);
        }else {
            return ItooResult.build(ItooResult.FAIL,"修改失败",false);
        }
    }




}
