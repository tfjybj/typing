package com.tfjybj.typing.provider.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.NoticeEntity;
import com.tfjybj.typing.provider.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
/**
 * NoticeController
 * ty_notice表接口
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0
 */
@Api(tags = {"ty_notice表接口"})
@RequestMapping(value = "/ty_notice")
@RestController
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端增加公告信息
     */
    @ApiOperation(value = "添加")
    @PostMapping(value = {"/insertNotice"})
    public ItooResult insertNotice(@RequestBody NoticeEntity model) {
        boolean flag = noticeService.insertNotice(model);
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
     * @description:教师端查询全部公告信息
     */
    @ApiOperation(value = "查询")
    @GetMapping(value = {"/selectNotice/{pageNo}/{pageSize}"})
    public ItooResult selectNotice(@ApiParam(name = "pageNo", value = "页码", required = true, example = "1") @PathVariable Integer pageNo,
                                     @ApiParam(name = "pageSize", value = "每页数量", required = true, example = "10") @PathVariable Integer pageSize) {
        PageInfo<NoticeEntity> list = noticeService.selectNotice(pageNo, pageSize);
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
     * @description:学生端查询消息内容
     */
    @ApiOperation(value = "学生端查询消息内容")
    @GetMapping(value = {"/selectNoticeByName/{noticeName}"})
    public ItooResult selectNoticeByName(@ApiParam(name = "noticeName", value = "主题名称", required = true) @PathVariable String noticeName) {
        List<NoticeEntity> list = noticeService.selectNoticeByName(noticeName);

        return ItooResult.build(ItooResult.SUCCESS, "查询成功", list);

    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端更新公告信息
     */
    @ApiOperation(value = "教师端更新公告信息")
    @PostMapping(value = {"/updateNotice"})
    public ItooResult updateNotice(@RequestBody NoticeEntity model) {
        Boolean flag = noticeService.updateNotice(model);
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
     * @description:教师端删除公告信息
     */
    @ApiOperation(value = "删除")
    @PostMapping(value = {"/deleteNotice"})
    public ItooResult deleteNotice(String id) {
        boolean flag = noticeService.deleteNotice(id);
        if (flag == true) {
            return ItooResult.build(ItooResult.SUCCESS, "删除成功", true);
        } else {
            return ItooResult.build(ItooResult.FAIL, "删除失败", false);
        }

    }


}
