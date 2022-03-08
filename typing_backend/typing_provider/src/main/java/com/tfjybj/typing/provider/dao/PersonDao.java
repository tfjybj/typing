package com.tfjybj.typing.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.entity.PersonEntity;
import com.tfjybj.typing.model.ClassNameModel;
import com.tfjybj.typing.model.TeamIntegralModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author 齐智
 * @Date: 2020/10/26 10:18
 * @Version: 1.0
 **/
@Repository("personDao")
public interface PersonDao extends BaseMapper<PersonEntity> {
    PersonEntity selectPersonEntityByUserId(String userId);
    List<TeamIntegralModel> selectTeamByUserId(String userId);

    boolean insertPersonTeam(@Param("PersonEntity") PersonEntity model);
    boolean deletePersonTeam( String userCode);
    boolean updatePersonTeam(@Param("PersonEntity") PersonEntity model);
    List<PersonEntity> querPersonTeam();
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据分组名称查询分组人员名单
     */
    List<PersonEntity> selectPersonTeam(String teamName);
    List<ClassNameModel>queryClassName();
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:2021年11月29日
     * @time:2021年11月29日08:45:25
     * @description:查询展示的班级
     */

    @Select("SELECT DISTINCT\n" +
            "\tp.`class_name` AS `className` \n" +
            "FROM\n" +
            "\t`ty_person` AS p \n" +
            "WHERE\n" +
            "\tis_delete = 0 \n" +
            "GROUP BY\n" +
            "\tp.`class_name`")
    List<ClassNameModel>selectClassName();
    Boolean updateClassNameIsDelete(String className);
    Boolean updateClassName(String className);
}
