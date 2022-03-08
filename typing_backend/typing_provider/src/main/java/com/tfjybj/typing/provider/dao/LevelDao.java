package com.tfjybj.typing.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.entity.LevelEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/11/26
 * Time: 18:06
 * Description:${DESCRIPTION}
 */
@Repository("levelDao")
public interface LevelDao extends BaseMapper<LevelEntity> {
    //查询
    List<LevelEntity> selectLevel();
    //增
    boolean insertLevel(@Param("levelEntity") LevelEntity levelEntity);
    //删除
    boolean updateLevelIsDelete(String id);
    //修改
    Boolean updateLevel(@Param("levelEntity") LevelEntity levelEntity);

}
