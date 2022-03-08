package com.tfjybj.typing.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.entity.NoticeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * NoticeDao接口
 * ty_notice表接口
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository("noticeDao")
public interface NoticeDao  extends BaseMapper<NoticeEntity> {

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端插入公告信息
     */
    boolean insertNotice(@Param("NoticeEntity") NoticeEntity model);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端查询全部公告信息
     */
    List<NoticeEntity> selectNotice();
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:学生端查询公告信息
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
    boolean deleteNotice(String id);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端更新公告信息
     */
    Boolean updateNotice(@Param("NoticeEntity") NoticeEntity model);
}
