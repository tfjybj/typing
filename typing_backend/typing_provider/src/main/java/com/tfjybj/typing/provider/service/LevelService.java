package com.tfjybj.typing.provider.service;

import com.dmsdbj.itoo.tool.base.service.BaseServicePlus;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.LevelEntity;

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/11/26
 * Time: 20:11
 * Description:${DESCRIPTION}
 */
public interface LevelService extends BaseServicePlus<LevelEntity> {

    boolean insertLevel(LevelEntity model);
    boolean deleteLevel( String id);
    Boolean updateLevel( LevelEntity model);
    PageInfo<LevelEntity> querLevel(Integer pageNo, Integer pageSize);


}
