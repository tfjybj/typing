package com.tfjybj.typing.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.entity.IntegralRuleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * IntegralRuleDao接口
 * ty_integral_rule表
 *
 * @author 张艳伟
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository("integralRuleDao")
public interface IntegralRuleDao extends BaseMapper<IntegralRuleEntity> {

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端增加积分规则
     */
    boolean insertIntegralRule(@Param("IntegralRuleEntity") IntegralRuleEntity model);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:教师端查询全部积分规则
     */
    List<IntegralRuleEntity> selectIntegralRule();
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据等级查询积分规则
     */
    List<IntegralRuleEntity> selectIntegralRuleByLevel( String smallSource);

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
    Boolean updateIntegralRule(@Param("IntegralRuleEntity") IntegralRuleEntity model);
}
