package com.tfjybj.typing.provider.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dmsdbj.itoo.tool.base.service.impl.BaseServicePlusImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.NoticeEntity;
import com.tfjybj.typing.provider.dao.NoticeDao;
import com.tfjybj.typing.provider.service.NoticeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
 * NoticeService接口实现类
 * ${base}表
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0
 */
@Service("NoticeService")
public class NoticeServiceImpl extends BaseServicePlusImpl<NoticeDao, NoticeEntity> implements NoticeService {

    @Resource
    private NoticeDao noticeDao;

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端插入公告信息
     */
    @Override
    public boolean insertNotice(@Param("NoticeModel") NoticeEntity model) {
        //设置ID为雪花算法产生的ID
        model.setId(IdWorker.getIdStr());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        noticeDao.insertNotice(model);
        return true;

    };

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端查询全部公告信息
     */
    @Override
    public PageInfo<NoticeEntity> selectNotice(Integer pageNo, Integer pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        List<NoticeEntity> noticeEntity = noticeDao.selectNotice();
        return new PageInfo<>(noticeEntity);
    };
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:学生端查询公告信息
     */
    @Override
    public List<NoticeEntity> selectNoticeByName(String noticeName) {

        List<NoticeEntity> list = noticeDao.selectNoticeByName(noticeName);
        if (list.size() == 0) {
            return null;
        }
        return list;
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端删除公告信息
     */
    @Override
    public boolean deleteNotice(String id) {

        return noticeDao.deleteNotice(id);
    };
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端更新公告信息
     */
    @Override
    public Boolean updateNotice(NoticeEntity model) {
        return noticeDao.updateNotice(model);
    };


}
