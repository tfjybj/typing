package com.tfjybj.typing.provider.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dmsdbj.itoo.tool.base.service.impl.BaseServicePlusImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.IntegralRuleEntity;
import com.tfjybj.typing.provider.dao.IntegralRuleDao;
import com.tfjybj.typing.provider.service.IntegralRuleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
 * IntegralRuleService接口实现类
 * ${base}表
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0
 */
@Service("IntegralRuleService")
public class IntegralRuleServiceImpl  extends BaseServicePlusImpl<IntegralRuleDao, IntegralRuleEntity> implements IntegralRuleService {


    @Resource
    private IntegralRuleDao integralRuleDao;

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端增加积分规则
     */
    @Override
    public boolean insertIntegralRule(@Param("IntegralRuleModel") IntegralRuleEntity model) {
        //设置ID为雪花算法产生的ID
        model.setId(IdWorker.getIdStr());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        integralRuleDao.insertIntegralRule(model);
        return true;
    };
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端查询全部积分规则
     */
    @Override
    public PageInfo<IntegralRuleEntity> selectIntegralRule(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<IntegralRuleEntity> integralRuleEntity = integralRuleDao.selectIntegralRule();
        return new PageInfo<>(integralRuleEntity);
    };
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据等级查询积分规则
     */

    @Override
    public List<IntegralRuleEntity> selectIntegralRuleByLevel(String smallSource){

        List<IntegralRuleEntity> integralRuleEntity = integralRuleDao.selectIntegralRuleByLevel(smallSource);
        return integralRuleEntity;
    };
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:删除积分规则
     */
    @Override
    public boolean deleteIntegralRule(String id) {
        return integralRuleDao.deleteIntegralRule(id);
    };
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:修改积分规则
     */
    @Override
    public Boolean updateIntegralRule(IntegralRuleEntity model) {
        return integralRuleDao.updateIntegralRule(model);
    };



}
