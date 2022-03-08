package com.tfjybj.typing.provider.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dmsdbj.itoo.tool.base.service.impl.BaseServicePlusImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.LevelEntity;
import com.tfjybj.typing.provider.dao.LevelDao;
import com.tfjybj.typing.provider.service.LevelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/11/26
 * Time: 20:12
 * Description:${DESCRIPTION}
 */
@Service("LevelImpl")
public class LevelImpl extends BaseServicePlusImpl<LevelDao, LevelEntity> implements LevelService {

    @Resource
    private LevelDao levelDao;

    @Override
    public boolean insertLevel(LevelEntity model){
        model.setId(IdWorker.getIdStr());
        return levelDao.insertLevel(model);

    };
    @Override
    public boolean deleteLevel( String id){
        return levelDao.updateLevelIsDelete(id);
    };
    @Override
    public Boolean updateLevel( LevelEntity model){
        //System.out.println(levelDao.updateLevel(model));
        return levelDao.updateLevel(model);
    };

    @Override
    public PageInfo<LevelEntity> querLevel(Integer pageNo, Integer pageSize){

        PageHelper.startPage(pageNo,pageSize);
        List<LevelEntity> levelEntity=levelDao.selectLevel();
        return new PageInfo<>(levelEntity);
    };

}
