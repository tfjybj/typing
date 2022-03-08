package com.tfjybj.typing.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.entity.TeamResultEntity;
import com.tfjybj.typing.model.TeamResultModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TeamResultDao接口
 * teamResult表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@Repository("teamResultDao")
public interface TeamResultDao extends BaseMapper<TeamResultEntity> {
//    <!--将redis完成比赛的队伍插入数据库-->
    void RedisToDb(List<TeamResultModel> list);
}
