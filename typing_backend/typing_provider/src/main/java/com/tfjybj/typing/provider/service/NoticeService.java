package com.tfjybj.typing.provider.service;

import com.dmsdbj.itoo.tool.base.service.BaseServicePlus;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.NoticeEntity;

import java.util.List;

/**
 * NoticeService接口
 *ty_notice表接口
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0
 */
public interface NoticeService extends BaseServicePlus<NoticeEntity> {
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端增加公告信息
     */
    boolean insertNotice( NoticeEntity model);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端查询全部公告信息
     */
    PageInfo<NoticeEntity> selectNotice(Integer pageNo, Integer pageSize);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:学生端查询公告内容
     */
    List<NoticeEntity> selectNoticeByName(String noticeName);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端删除公告信息
     */
    boolean deleteNotice( String  id);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端更新公告信息
     */
    Boolean updateNotice( NoticeEntity model);

}
