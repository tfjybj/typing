package com.tfjybj.typing.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.PersonEntity;
import com.tfjybj.typing.model.ClassNameModel;
import com.tfjybj.typing.model.PersonTeamModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/11/21
 * Time: 17:16
 * Description:${DESCRIPTION}
 */
public interface PersonTeamService extends IService<PersonEntity> {


     /*
      * @version V1.0
      * Title: checkExcel
      * @author LiuYanQiang
      * @description 校验Excel
      * @createTime  2021/9/21 22:25
      * @param [list]
      * @return void*/
     void checkExcel(List<PersonTeamModel> list) throws RuntimeException;

     int importExcel(List<PersonTeamModel> list);

     boolean insertPersonTeam( PersonEntity model);
     boolean deletePersonTeam( PersonEntity model);
     boolean updatePersonTeam( PersonEntity model);
     PageInfo<PersonEntity> querPersonTeam(Integer pageNo, Integer pageSize);
     /**
      * @author: 张艳伟
      * @version:
      * @param: []
      * @date:
      * @time:
      * @description:根据分组名称查询分组人员名单
      */
     PageInfo<PersonEntity> selectPersonTeam(String teamName);
     //批量插入
     void getExcel(MultipartFile file) throws Exception;

     PageInfo<ClassNameModel> queryClassName(Integer pageNo, Integer pageSize);
     /**
      * @author: 张艳伟
      * @version:
      * @param: []
      * @date:2021年11月29日
      * @time:2021年11月29日08:45:25
      * @description:查询展示的班级
      */
     List<ClassNameModel> selectClassName();

     Boolean updateClassNameIsDelete(String className);

     Boolean updateClassName(String className);
}
