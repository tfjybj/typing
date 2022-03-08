package com.tfjybj.typing.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.PersonEntity;
import com.tfjybj.typing.model.ClassNameModel;
import com.tfjybj.typing.model.PersonTeamModel;
import com.tfjybj.typing.provider.dao.PersonDao;
import com.tfjybj.typing.provider.service.PersonTeamService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/11/21
 * Time: 17:16
 * Description:${DESCRIPTION}
 */
@Service
public class PersonTeamServiceImpl extends ServiceImpl<PersonDao, PersonEntity> implements PersonTeamService {

    @Resource
    private PersonDao personTeamDao;

    @Resource
    private PersonDao personDao;

    /*
     * @version V1.0
     * Title: checkExcel
     * @author LiuYanQiang
     * @description 校验数据
     * @createTime  2021/9/22 8:40
     * @param [list]
     * @return void*/
    public void checkExcel(List<PersonTeamModel> list) throws RuntimeException{
        // 约定第二行开始导入
        int line = 1;
        StringBuffer sb = new StringBuffer();

        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException( "您导入的数据似乎是一个空表格！");
        }
        for(PersonTeamModel item :list){
            System.out.println(item.getClassName());

            PersonEntity personEntity =personDao.selectPersonEntityByUserId(item.getUserCode());
            if(personEntity!=null){
                sb.append("Excel表格的第" + line + "行，【" + item.getUserName() + "】的信息勿重复添加!"+"<br/>");
            }

            if(org.apache.commons.lang3.StringUtils.isBlank(item.getClassName())){
                sb.append("Excel表格的第" + line + "行，班级名称不能为空!");
            }

            if (org.apache.commons.lang3.StringUtils.isBlank(item.getTeamId())) {
                sb.append("Excel表格的第" + line + "行，团队不能为空!");
            }

            if (org.apache.commons.lang3.StringUtils.isBlank(item.getUserCode())) {
                sb.append("Excel表格的第" + line + "行，学号不能为空!");
            }

            if (org.apache.commons.lang3.StringUtils.isBlank(item.getUserName())) {
                sb.append("Excel表格的第" + line + "行，用户姓名不能为空!");
            }
            line++;
        }

        // 存在错误则以异常的形式抛出
        if (!"".equals(sb.toString())) {
            throw new RuntimeException(sb.toString());
        }
    }

    /*
     * @version V1.0
     * Title: importExcel
     * @author LiuYanQiang
     * @description 导入数据
     * @createTime  2021/9/22 10:11
     * @param [list]
     * @return int*/
    @Override
    public int importExcel(List<PersonTeamModel> list){
        int count = 0;
        try{
            for (PersonTeamModel item : list) {
                PersonEntity personEntity = new PersonEntity();
                //BeanUtils.copyProperties(a,  b)  将a拷贝到b
                BeanUtils.copyProperties(item,  personEntity);

                this.insertPersonTeam(personEntity);
                count++;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("导入出现问题，行：" + count + "，" + e.getMessage());
        }

        return count;
    }


    @Override
    public boolean insertPersonTeam(PersonEntity model){

        return personTeamDao.insertPersonTeam(model);
    };
    @Override
    public boolean deletePersonTeam( PersonEntity model){
        String userCode=model.getUserCode();

        return personTeamDao.deletePersonTeam(userCode);
    };
    @Override
    public boolean updatePersonTeam( PersonEntity model){
        return  personTeamDao.updatePersonTeam(model);
    };

    @Override
    public Boolean updateClassNameIsDelete(String className){
        return  personTeamDao.updateClassNameIsDelete(className);
    };

    @Override
    public Boolean updateClassName(String className){
        return  personTeamDao.updateClassName(className);
    };

    @Override
    public PageInfo<PersonEntity> querPersonTeam(Integer pageNo, Integer pageSize){

        PageHelper.startPage(pageNo,pageSize);
        List<PersonEntity> personEntity=personTeamDao.querPersonTeam();
        return new PageInfo<>(personEntity);
    };
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据分组名称查询分组人员名单
     */
    @Override
    public PageInfo<PersonEntity> selectPersonTeam(String teamName){

//        PageHelper.startPage(pageNo,pageSize);
        List<PersonEntity> personEntity=personTeamDao.selectPersonTeam(teamName);
        return new PageInfo<>(personEntity);
    };

    @Override
    public PageInfo<ClassNameModel> queryClassName(Integer pageNo, Integer pageSize){

        PageHelper.startPage(pageNo,pageSize);
        List<ClassNameModel> classNameModel=personTeamDao.queryClassName();
        return new PageInfo<>(classNameModel);
    };


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:2021年11月29日
     * @time:2021年11月29日08:45:25
     * @description:查询展示的班级
     */
    @Override
    public List<ClassNameModel>selectClassName(){
        return personTeamDao.selectClassName();
    };

    @Override
    public void getExcel( MultipartFile file) throws Exception {

        //1.得到上传的表
        Workbook workbook2 = WorkbookFactory.create(file.getInputStream());
        //2、获取test工作表
        Sheet sheet2 = workbook2.getSheet("Sheet1");
        //获取表的总行数
        int num = sheet2.getLastRowNum();
        //System.out.println(num);

        //遍历excel每一行
        for (int j = 1; j <= num-1; j++) {
            Row row1 = sheet2.getRow(j);

            //如果单元格中有数字或者其他格式的数据，则调用setCellType()转换为string类型
            //获取表中第i行，第2列的单元格
            Cell cell2 = row1.getCell(1);
            cell2.setCellType(CellType.STRING);
            //excel表的第i行，第3列的单元格
            Cell cell3 = row1.getCell(2);
            cell3.setCellType(CellType.STRING);
            Cell cell4 = row1.getCell(3);
            Cell cell5 = row1.getCell(4);
            cell5.setCellType(CellType.STRING);

            //判断账号是否为空，如果为空跳转到吓一条数据
            if (cell2.getStringCellValue().equals("")){
                //跳出当前循环进行下一循环
                continue;
            }
            //这里new 一个对象，用来装填从页面上传的Excel数据，字段根据上传的excel决定
            PersonEntity excel= new PersonEntity();
            //excel.setId(cell1.getStringCellValue());
            excel.setUserCode(cell2.getStringCellValue());//学号或者登录号
            excel.setUserName(cell3.getStringCellValue());//用户名
            excel.setClassName(cell4.getStringCellValue());//班级名称
            excel.setTeamId(cell5.getStringCellValue());//分组号
            try {
                personTeamDao.insertPersonTeam(excel);
            }catch (Exception e){
                System.out.println("插入异常"+cell3.getStringCellValue());
                //exportExcel(response, errList);
            }
        }

    }

}
