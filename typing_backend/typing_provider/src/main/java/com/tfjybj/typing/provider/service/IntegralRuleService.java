package com.tfjybj.typing.provider.service;

import com.dmsdbj.itoo.tool.base.service.BaseServicePlus;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.IntegralRuleEntity;

import java.util.List;

/**
 * IntegralRuleService接口
 *ty_integral_rule表
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IntegralRuleService extends BaseServicePlus<IntegralRuleEntity> {

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端增加积分规则
     */
    boolean insertIntegralRule( IntegralRuleEntity model);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端查询全部积分规则
     */
    PageInfo<IntegralRuleEntity> selectIntegralRule(Integer pageNo, Integer pageSize);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据等级查询积分规则
     */
    List<IntegralRuleEntity> selectIntegralRuleByLevel(String smallSource);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:删除积分规则
     */
    boolean deleteIntegralRule(String id);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:修改积分规则
     */
    Boolean updateIntegralRule( IntegralRuleEntity model);
}
