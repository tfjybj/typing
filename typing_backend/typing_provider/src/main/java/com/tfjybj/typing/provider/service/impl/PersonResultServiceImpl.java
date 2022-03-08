package com.tfjybj.typing.provider.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dmsdbj.itoo.tool.base.service.impl.BaseServicePlusImpl;
import com.dmsdbj.itoo.tool.business.ItooResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tfjybj.typing.entity.PersonEntity;
import com.tfjybj.typing.entity.PersonResultEntity;
import com.tfjybj.typing.model.*;
import com.tfjybj.typing.provider.common.Constants;
import com.tfjybj.typing.provider.dao.*;
import com.tfjybj.typing.provider.service.PersonResultService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * PersonResultService接口实现类
 * ${base}表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@Service("personResultService")
public class PersonResultServiceImpl extends BaseServicePlusImpl<PersonResultDao, PersonResultEntity> implements PersonResultService {

    @Value("${reportLevel.pri}")
    private String pri;

    @Value("${reportLevel.sen}")
    private String sen;

    @Value("${reportLevel.key}")
    private String key;

    @Resource
    private RatingServiceImpl ratingServiceImpl;

    @Resource
    private LevelJudgmentImpl levelJudgment;

    //region 模板生成
    @Resource
    private PersonResultDao personResultDao;


    @Resource
    private IntegralDao integralDao;

    @Resource
    private TeamDao teamDao;

    @Resource
    private AllUserDao allUserDao;

    @Resource
    PersonDao personDao;


    /**
     * 读取配置文件，如果为1，就只查询person表中的任
     */
    @Value("${rule.selectPersonRankingRule}")
    private Integer flag;





    /* **********************************以下为非模板生成的内容********************************* */


    /**
     * 添加成绩记录
     *
     * @param
     * @return AddActivePerson
     * @author 王闯
     * @since 2019年7月2日19:37:53
     */
    @Override
    public boolean insertPersonResult(@Param("personResultModel") PersonResultEntity personResultModel) {
        /*String userId = UserUtil.getCurrentUser().getUserId();
        personResultModel.setUserId(userId);*/

        //设置ID为雪花算法产生的ID
        personResultModel.setId(IdWorker.getIdStr());

        personResultDao.insertPersonResult(personResultModel);

        //之前是按照租户Id来确定不同的成绩规则，接入新版权限之后用同一种成绩规则
        //根据用户id查询用户的组织是师院的
//        List schooleNo = allUserDao.selectSchoolNoById(personResultModel.getUserId());

//        for (int i = 0; i < schooleNo.size(); i++) {
//            if (schooleNo.get(i).equals("666888")) {
//            if (schooleNo.get(i).equals("9876543266688899999999")) {
                //插入个人成绩，其他组织用户
                levelJudgment.calculateIntegral(personResultModel.getUserId());
//            } else {
                //插入个人成绩,师范学员学生
//                ratingServiceImpl.calculateIntegral(personResultModel.getUserId());
//            }
//        }
        return true;
    }
    //endregion


    /**
     * 分页查询所有Batch
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return 分页查询的结果
     * @author 王闯
     * @since ${version} 2019-04-11 10:44:52
     */
    @Override
    public PageInfo<PersonResultEntity> selectPersonResult(String userId, Integer pageNo, Integer pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        //获取USERID
//        String userId = UserUtil.getCurrentUser().getUserId();
        //查询学生个人记录
        List<PersonResultEntity> personResultModels = personResultDao.selectPersonResult(userId);
        return new PageInfo<>(personResultModels);
    }

    /**
     * 查询个人的积分和成绩
     *
     * @return
     */
    @Override
    public PersonIntegralModel selectPersonIntegral(String usercode) {
        //获取USERID
        String userId = usercode;//UserUtil.getCurrentUser().getUserId();

        //查询级别，本次积分，进步积分
        PersonIntegralModel personIntegralModel = integralDao.selectPersonIntegral(userId);
        if (personIntegralModel == null) {
            personIntegralModel = new PersonIntegralModel();
        }
        Double teamGrade = teamDao.selectTeamResultByUserId(userId);
        if (teamGrade == null) {
            teamGrade = 0.0;
        }
        personIntegralModel.setTeamGrade(teamGrade);
        return personIntegralModel;

    }

    @Override
    public List<PersonResultEntity> selectPersonRateAndSpeed(String userId) {

        List<PersonResultEntity> personResultModel = personResultDao.selectPersonRAS(userId);
        return personResultModel;
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月26日11:37:55
     * @time: 2021年10月26日
     * @description:根据查询个人打字速度和正确率
     */
    @Override
    public List<PersonResultModel> selectPersonRateAndSpeedAVG(String userCode) {
        List<PersonResultModel> personResultModel = personResultDao.selectPersonRateAndSpeedAVG(userCode);
        return personResultModel;
    }

    @Override
    public List selectPersonProgress(Integer pageNo, Integer pageSize) {
        List<PersonProgress> personResultModel = personResultDao.selectPersonProgress(pageNo, pageSize, flag);
        List list = calculateCount(personResultModel, pageNo, pageSize);
        return list;
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:根据班级查询排行榜
     */
    @Override
    public List selectPersonClassProgress(Integer pageNo, Integer pageSize, String className) {
        List<PersonProgress> personResultModel = personResultDao.selectPersonClassProgress(pageNo, pageSize, className);
        List list = calculateCount(personResultModel, pageNo, pageSize);
        return list;
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月27日
     * @time: 2021年9月27日08:28:46
     * @description:根据班级查询进步，班级模糊查询
     */
    @Override
    public List selectPersonClassProgressClass(Integer pageNo, Integer pageSize ) {
        List<PersonProgress> personResultModel = personResultDao.selectPersonClassProgressClass(pageNo, pageSize);
        List list = calculateCount(personResultModel, pageNo, pageSize);
        return list;
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:
     */
    @Override
    public List selectPersonTeamProgress(Integer pageNo, Integer pageSize, String teamId,String className) {
        List<PersonProgress> personResultModel = personResultDao.selectPersonTeamProgress(pageNo, pageSize, teamId,className);
        List list = calculateCount(personResultModel, pageNo, pageSize);
        return list;
    }


    @Override
    public List selectPersonRanking(Integer pageNo, Integer pageSize) {
        List<PersonProgress> personResultModel = personResultDao.selectPersonRanking(pageNo, pageSize, flag);
        List list = calculateCount(personResultModel, pageNo, pageSize);
        return list;
    }

    /**
     * @author: 张艳伟
     * @version:根据班级查询进步英雄榜排名
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:
     */
    @Override
    public List selectPersonClassRanking(Integer pageNo, Integer pageSize, String className) {
        List<PersonProgress> personResultModel = personResultDao.selectPersonClassRanking(pageNo, pageSize, className);
        List list = calculateCount(personResultModel, pageNo, pageSize);
        return list;
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月27日
     * @time: 2021年9月27日08:28:46
     * @description:根据班级查询整体排名，班级模糊查询
     */
    @Override
    public List selectPersonClassRankingClass(Integer pageNo, Integer pageSize) {
        List<PersonProgress> personResultModel = personResultDao.selectPersonClassRankingClass(pageNo, pageSize);
        List list = calculateCount(personResultModel, pageNo, pageSize);
        return list;
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月19日
     * @time: 16:21:45
     * @description:
     */
    @Override
    public List selectPersonTeamRanking(Integer pageNo, Integer pageSize, String teamId,String className) {
        List<PersonProgress> personResultModel = personResultDao.selectPersonTeamRanking(pageNo, pageSize, teamId,className);
        List list = calculateCount(personResultModel, pageNo, pageSize);
        return list;
    }


    /**
     * 播报晋级人员名单
     *
     * @return 拼接好的字符串集合
     * @author Yara
     * @date 2020年11月1日15:41:01
     */
    @Override
    public List<String> selectPersonImprovement() {
        //查询没有播报过的人员名单
        List<ImproveReportModel> list = personResultDao.selectNoReport();
        if (list.size() == 0) {
            return null;
        }
        //筛选出晋级为初级键盘手的人员名单
        List<ImproveReportModel> parimary = list.stream().filter(item -> item.getLevel().equals(Constants.PARIMARY)).collect(Collectors.toList());
        //筛选出晋级为高级键盘手的人员名单
        List<ImproveReportModel> senior = list.stream().filter(item -> item.getLevel().equals(Constants.SENIOR)).collect(Collectors.toList());
        //筛选出晋级为键盘侠的人员名单
        List<ImproveReportModel> keyMan = list.stream().filter(item -> item.getLevel().equals(Constants.KEYMAN)).collect(Collectors.toList());
        //定义3个播报字符串：初级，高级，侠
        String reportParimary = "";
        String reportSenior = "";
        String reportKeyman = "";
        //拼接晋级初级键盘手的全部人名
        for (int i = 0; i < parimary.size(); parimary.get(i++)) {
            ImproveReportModel improveReportModel = parimary.get(i);
            String name = improveReportModel.getUserName();
            reportParimary = reportParimary + " " + name;
        }
        //拼接晋级高级键盘手的全部人名
        for (int i = 0; i < senior.size(); senior.get(i++)) {
            ImproveReportModel improveReportModel = senior.get(i);
            String name = improveReportModel.getUserName();
            reportSenior = reportSenior + " " + name;
        }
        //拼接晋级键盘侠的全部人名
        for (int i = 0; i < keyMan.size(); keyMan.get(i++)) {
            ImproveReportModel improveReportModel = keyMan.get(i);
            String name = improveReportModel.getUserName();
            reportKeyman = reportKeyman + " " + name;
        }
        List<String> listreport = new ArrayList<>();
        //判断该级别是否无人晋级
        if (reportParimary != "") {
            //如果有人晋级，则将前缀拼接起来
            reportParimary = pri + reportParimary;
            //将拼接好的字段放到集合中
            listreport.add(reportParimary);
        }
        if (reportSenior != "") {
            reportSenior = sen + reportSenior;
            listreport.add(reportSenior);
        }
        if (reportKeyman != "") {
            reportKeyman = key + reportKeyman;
            listreport.add(reportKeyman);
        }
        personResultDao.UpdateImprovement(list);
        return listreport;
    }

    /**
     * 查询晋级人员名单——滚屏
     *
     * @return 全部晋级人员名单
     * @author Yara
     */
    @Override
    public List<ImproveReportModel> selectImprovement() {
        //查询晋级人员名单
        List<ImproveReportModel> list = integralDao.selectImproveInfo();
        if (list.size() == 0) {
            return null;
        }
        return list;
    }


    private List calculateCount(List<PersonProgress> list, Integer pageNo, Integer pageSize) {
//        Integer count=integralDao.selectCountNumber();
//        count=(int) (count*0.3);
//        if(list.size()>=45)
//        {
//            list=list.stream().limit(45).collect(Collectors.toList());
//        }
        int size = list.size();
        list = list.stream().skip((pageNo - 1) * pageSize).
                limit(pageSize).collect(Collectors.toList());
        Integer num = (pageNo - 1) * pageSize + 1;
        for (PersonProgress p : list) {
            p.setId(num++);
        }

        ArrayList returnList = new ArrayList();
        returnList.add(list);
        returnList.add(size);
        return returnList;
    }


    /**
     * 查询各个班各个级别的人数
     *
     * @return
     */
    @Override
    public ItooResult selectClassLevelCount(List<String> classNameList) {
        //核心sql：查询各个班级，各个等级的人数
        List<ClassLevelCountModel> countModels = personResultDao.selectClassLevelCount(classNameList);
        if (countModels.size() == 0) {
            //没有排名
            return new ItooResult(ItooResult.FAIL, "无记录");
        }
        //根据班级的名称，分组：key=班级名称，value=该班级对应的各条数据
        Map<String, List<ClassLevelCountModel>> stringListHashMap = countModels.
                stream().
                collect(Collectors.groupingBy(ClassLevelCountModel::getClassName));
        //获取到班级的集合
        Set<String> classNames = stringListHashMap.keySet();
        //判断班级是否为0
        if (classNames.size() == 0) {
            //没有排名
            return new ItooResult(ItooResult.FAIL, "无记录");
        }
        //准备一个集合，准备返回
        List<ClassLevelModel> returnList = new ArrayList<>();
        //遍历每一个班级
        for (String className : classNames) {
            //获取这个班级的各个等级的人数
            List<ClassLevelCountModel> classLevelCountModels = stringListHashMap.get(className);
            //将该班队各个等级的人数，塞到对应的遍历中
            ClassLevelModel classLevelModel = makeClassLevelModel(classLevelCountModels);
            //为对象设置班级名称
            classLevelModel.setClassName(className);
            //将这个班级的信息，添加到返回的集合中
            returnList.add(classLevelModel);
        }
        //将结果返回
        return new ItooResult(ItooResult.SUCCESS, "查询成功", returnList);
    }

    /**
     * 构造实体
     *
     * @param classLevelCountModels
     * @return
     */
    private ClassLevelModel makeClassLevelModel(List<ClassLevelCountModel> classLevelCountModels) {
        //声明返回实体
        ClassLevelModel classLevelModel = new ClassLevelModel();
        //遍历该班级对应的各条记录
        for (ClassLevelCountModel classLevelCountModel : classLevelCountModels) {

            switch (classLevelCountModel.getLevel()) {
                case Constants.XIAOBAI:
                    classLevelModel.setBeginnerCount(classLevelCountModel.getCount());
                    break;
                case Constants.PARIMARY:
                    classLevelModel.setPrimaryCount(classLevelCountModel.getCount());
                    break;
                case Constants.SENIOR:
                    classLevelModel.setSeniorCount(classLevelCountModel.getCount());
                    break;
                case Constants.KEYMAN:
                    classLevelModel.setKeyManCount(classLevelCountModel.getCount());
                    break;
            }
        }
        return classLevelModel;
    }


    /**
     * 查询每个班级中人员的进级次数
     *
     * @return
     */
    @Override
    public ItooResult selectRiseCount() {
        //查询最高段位
        List<RiseCountModel> riseCountModelList = selectMostLevel(null);
        if (riseCountModelList == null || riseCountModelList.size() == 0) {
            return new ItooResult(ItooResult.SUCCESS, "无数据");
        }
        //将每个人最高的记录，根据班级的名字进行分组
        Map<String, List<RiseCountModel>> collect = riseCountModelList.stream().collect(Collectors.groupingBy(RiseCountModel::getClassName));
        //获取每一个班级的名字
        Set<String> classNames = collect.keySet();
        //构造返回的实体
        List<ClassRiseCountModel> classRiseCountModels = new ArrayList<>();
        ClassRiseCountModel classRiseCountModel;
        //定义一个变量，用来存放每一个班级的进级次数
        int riseCount;
        //遍历每一个名字
        for (String className : classNames) {
            //每次将班级的进级次数置零
            riseCount = 0;
            //得到该班级中人员的打字集合
            List<RiseCountModel> riseCountModels = collect.get(className);
            //遍历这个集合
            for (RiseCountModel riseCountModel : riseCountModels) {
                //将排名的顺序进行累加
                riseCount += riseCountModel.getRiseNumber();
            }
            //每个班一组，构成一段记录，存到集合中
            classRiseCountModel = new ClassRiseCountModel();
            classRiseCountModel.setClassName(className);
            classRiseCountModel.setRiseCount(riseCount);
            classRiseCountModels.add(classRiseCountModel);
        }

        return new ItooResult(ItooResult.SUCCESS, "查询成功", classRiseCountModels);
    }


    /**
     * 查询最高段位
     *
     * @param userId 如果查询全部，则传null
     * @return
     */
    public List<RiseCountModel> selectMostLevel(String userId) {

        //查询每个人的等级
        List<RiseCountModel> riseCountModels = personResultDao.selectRiseCount(userId);
        if (riseCountModels.size() == 0) {
            return null;
        }
        //取出level为空的人员
        riseCountModels = riseCountModels.stream().filter(item -> item.getLevel() != null).collect(Collectors.toList());
        if (riseCountModels.size() == 0) {
            return null;
        }
        //将人员的等级转换为权重
        String levelName = "";
        Integer riseCount = 0;
        for (RiseCountModel riseCountModel : riseCountModels) {
            levelName = riseCountModel.getLevel();
            riseCount = calculateRiseCount(levelName);
            riseCountModel.setRiseNumber(riseCount);
        }
        //构造返回结果集
        List<RiseCountModel> returnList = new ArrayList<>();

        //根据用户名进行分组
        Map<String, List<RiseCountModel>> stringListMap = riseCountModels.stream().collect(Collectors.groupingBy(RiseCountModel::getUserName));
        //得到所有的用户名
        Set<String> userNames = stringListMap.keySet();
        //遍历每一个用户名
        for (String userName : userNames) {
            //根据用户名，得到对应的集合
            List<RiseCountModel> riseCountModelList = stringListMap.get(userName);
            //根据每次打字的进级次数，降序排列
            riseCountModelList = riseCountModelList.stream().sorted(Comparator.comparing(RiseCountModel::getRiseNumber).reversed()).collect(Collectors.toList());
            //判断结果集的size是否为0，如果为0，跳出该循环，防止空指针
            if (riseCountModelList.size() == 0) {
                continue;
            }
            //取第一条，放到集合中
            RiseCountModel riseCountModel = riseCountModelList.get(0);
            returnList.add(riseCountModel);
        }
        return returnList;
    }


    /**
     * 根据等级，计算分数
     *
     * @param level
     * @return
     */
    public Integer calculateRiseCount(String level) {
        Integer result = 0;
        switch (level) {
            case Constants.PARIMARY:
                result = Constants.PARIMARY_RICE;
                break;
            case Constants.SENIOR:
                result = Constants.SENIOR_RICE;
                break;
            case Constants.KEYMAN:
                result = Constants.KEYMAN_RICE;
                break;
        }
        return result;
    }

    /**
     * 查询团队积分排名
     *
     * @return
     */
    @Override
    public ItooResult selectTeamRanking(List<String> className) {
        //查询每个团队的积分，并排序
        List<TeamRankingModel> teamRankingEntities = teamDao.selectTeamRanking(className);
        //定义基础分数
        int ranking = 0;
        //遍历每一对象，为其添加名词
            //计算晋级率，根据小组的各个称号
        for (TeamRankingModel teamRankingModel : teamRankingEntities) {

           String s = teamRankingModel.getLevels();
            String[] split = s.split(",");
            int a=0;
            int b=0;
            int c=0;
            int d=0;
            int g=0;
            double e=0D;
           for(int j=0; j< split.length;j++) {
               e++;
                   if(split[j].equals("小白键盘手")){
                       a++;
                   };
                   if(split[j].equals("初级键盘手")){
                       b++;
                   };
                   if(split[j].equals("高级键盘手")){
                       c++;
                   };
                   if(split[j].equals("键盘侠")){
                       d++;
                   };
                    if(split[j].equals("")){
                        g++;
                    };
               }
            double f=(b+c+d)/e;
            teamRankingModel.setPromotion((double)Math.round(f* 100)/100);
            teamRankingModel.setRanking(++ranking);

          }

        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamRankingEntities);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:查询全部未练习人员
     */
    @Override
    public ItooResult selectAllNotRanking() {

        List<TeamRankingModel> teamRankingEntities = teamDao.selectAllNotRanking();
        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamRankingEntities);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:根据班级查询全部未练习人员
     */
    @Override
    public ItooResult selectAllNotRankingByClass(String className) {
        //查询每个团队的积分，并排序
        List<TeamRankingModel> teamRankingEntities = teamDao.selectAllNotRankingByClass(className);
        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamRankingEntities);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:查询全部积分排名-AR奖状接口
     */
    @Override
    public ItooResult selectAllTeamRanking() {
        //查询每个团队的积分，并排序
        List<TeamRankingModel> teamRankingEntities = teamDao.selectAllTeamRanking();
        if(teamRankingEntities==null || teamRankingEntities.size()==0){
            return  new ItooResult(ItooResult.FAIL, "查询失败");
        }
//        for循环给排行赋值
        Integer i=0;
        for(TeamRankingModel teamRankingModel:teamRankingEntities ){
            teamRankingModel.setRanking(++i);
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamRankingEntities);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月8日
     * @time: 2021年11月8日15:21:41
     * @description:根据班级查询积分排名--查询晋级榜单
     */
    @Override
    public ItooResult selectAllTeamRankingByClass(String className) {
        //查询每个团队的积分，并排序
        List<TeamRankingModel> teamRankingEntities = teamDao.selectAllTeamRankingByClass(className);
        if(teamRankingEntities==null || teamRankingEntities.size()==0){
            return  new ItooResult(ItooResult.FAIL, "查询失败");
        }
//        for循环给排行赋值
        Integer i=0;
        for(TeamRankingModel teamRankingModel:teamRankingEntities ){
            teamRankingModel.setRanking(++i);
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamRankingEntities);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月15日
     * @time: 2021年11月15日11:35:01
     * @description:根据等级查询积分排名-晋级榜单
     */
    @Override
    public ItooResult selectAllTeamRankingByLevel(String level) {
        //查询每个团队的积分，并排序
        List<TeamRankingModel> teamRankingEntities = teamDao.selectAllTeamRankingByLevel(level);
        if(teamRankingEntities==null || teamRankingEntities.size()==0){
            return  new ItooResult(ItooResult.FAIL, "查询失败");
        }
//        for循环给排行赋值
        Integer i=0;
        for(TeamRankingModel teamRankingModel:teamRankingEntities ){
            teamRankingModel.setRanking(++i);
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamRankingEntities);
    }


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月15日
     * @time: 2021年11月15日11:35:01
     * @description:根据班级和等级查询积分排名-晋级榜单
     */
    @Override
    public ItooResult selectAllTeamRankingByClassLevel(String className,String level) {
        //查询每个团队的积分，并排序
        List<TeamRankingModel> teamRankingEntities = teamDao.selectAllTeamRankingByClassLevel(className,level);
        if(teamRankingEntities==null || teamRankingEntities.size()==0){
            return  new ItooResult(ItooResult.FAIL, "查询失败");
        }
//        for循环给排行赋值
        Integer i=0;
        for(TeamRankingModel teamRankingModel:teamRankingEntities ){
            teamRankingModel.setRanking(++i);
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamRankingEntities);
    }

    /**
     * 根据条件查询个人成绩
     *
     * @param createTime 开始时间
     * @param endTime    结束时间
     * @param level      等级
     * @return
     */
    @Override
    public ItooResult selectRankingByTimeLevel(String createTime, String endTime, String level, String className) {
        List<PersonRankingModel> personRankingModels = personResultDao.selectRankingByTimeLevel(createTime, endTime, level, className);
        if (personRankingModels == null || personRankingModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        Integer i = 0;
        for (PersonRankingModel personRankingModel : personRankingModels) {
            personRankingModel.setRanking(++i);
            Date time = personRankingModel.getCreateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    //格式化规则
            String strDate = sdf.format(time); //格式化成yyyy-MM-dd格式的时间字符串
            personRankingModel.setTime(strDate);
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", personRankingModels);
    }

    /**
     * @author: 张艳伟
     * @version:按照条件查询成绩，班级模糊查询
     * @param: []
     * @date: 2021年9月26日
     * @time: 2021年9月26日19:54:10
     * @description:
     */
    @Override
    public ItooResult selectRankingByTimeLevelClass(String createTime, String endTime, String level) {
        List<PersonRankingModel> personRankingModels = personResultDao.selectRankingByTimeLevelClass(createTime, endTime, level);
        if (personRankingModels == null || personRankingModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        Integer i = 0;
        for (PersonRankingModel personRankingModel : personRankingModels) {
            personRankingModel.setRanking(++i);
            Date time = personRankingModel.getCreateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    //格式化规则
            String strDate = sdf.format(time); //格式化成yyyy-MM-dd格式的时间字符串
            personRankingModel.setTime(strDate);
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", personRankingModels);
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:查询当前打字次数
     */
    @Override
    public ItooResult selectPersonResultSum(String userId) {
        List<PersonResultModel> personResultModel = integralDao.selectPersonResultSum(userId);

        return new ItooResult(ItooResult.SUCCESS, "查询成功", personResultModel);
    }


    @Override
    public ItooResult selectGradeAndNum(String userCode) {
        //stream流筛选num大于10的等级
        //num大于10的等级之间对比，选出等级最大的返给前端，进行级别渲染
        List<GradeModel> gradeModels = integralDao.selectGrade(userCode);


        /***************   算法 *****************/
        //   四个等级的次数相加: 小白= 小白.num+初级.num+高级.num+侠.num
        //                  初级=初级.num+ 高级.num+ 侠.num
        //                  高级=高级.num+ 侠.num
        //                  侠=侠.num


        List<GradeModel> lastGreadeModels = new ArrayList<>();
        //设定四个等级的次数变量
        String xiaobai = "0";
        String parimary = "0";
        String senior = "0";
        String keyman = "0";
        //遍历查出的的数组，并给四个等级的变量赋值，得到每个等级的数量
        for (GradeModel grademodel : gradeModels) {
            if (grademodel.getLevel().equals(Constants.XIAOBAI)) {
                xiaobai = grademodel.getNum();
            }
            if (grademodel.getLevel().equals(Constants.PARIMARY)) {
                parimary = grademodel.getNum();
            }
            if (grademodel.getLevel().equals(Constants.SENIOR)) {
                senior = grademodel.getNum();
            }
            if (grademodel.getLevel().equals(Constants.KEYMAN)) {
                keyman = grademodel.getNum();
            }
        }


        //重新遍历数组，将数组中的等级数量按照算法相加后重新存入相应的字段中
        for (GradeModel grademodel : gradeModels) {
            if (grademodel.getLevel().equals(Constants.XIAOBAI)) {
                Integer xb = Integer.parseInt(xiaobai) + Integer.parseInt(senior) + Integer.parseInt(parimary) + Integer.parseInt(keyman);
                grademodel.setNum(xb.toString());
                lastGreadeModels.add(grademodel);
            }
            if (grademodel.getLevel().equals(Constants.PARIMARY)) {
                Integer pa = Integer.parseInt(senior) + Integer.parseInt(parimary) + Integer.parseInt(keyman);
                grademodel.setNum(pa.toString());
                lastGreadeModels.add(grademodel);
            }
            if (grademodel.getLevel().equals(Constants.SENIOR)) {
                Integer se = Integer.parseInt(senior) + Integer.parseInt(keyman);
                grademodel.setNum(se.toString());
                lastGreadeModels.add(grademodel);
            }
            if (grademodel.getLevel().equals(Constants.KEYMAN)) {
                grademodel.setNum(keyman);
                lastGreadeModels.add(grademodel);
            }
        }

        //筛选等级大于10的等级
        //List<GradeModel> gradRes = lastGreadeModels.stream().filter(gradeModel-> Integer.valueOf(gradeModel.getNum())>=10).collect(Collectors.toList());
        //如果通过筛选没有等级大于10的就默认等级是小白
        if (lastGreadeModels.size() == 0) {
            LevelModel levelModel = new LevelModel();
            levelModel.setLevelName(Constants.XIAOBAI);
            levelModel.setLevelNum("0");
            return new ItooResult(ItooResult.SUCCESS, "查询成功", levelModel);
        }
        //新建一个数组
        ArrayList res = new ArrayList();
        //循环筛选出等级的数组，将等级转换成数字，为后边排序做准备
        for (int i = 0; i < lastGreadeModels.size(); i++) {
            System.out.println(lastGreadeModels.get(i).getLevel());
            if (lastGreadeModels.get(i).getLevel().equals(Constants.XIAOBAI)) {
                res.add(Constants.LevelOne);
            }
            if (lastGreadeModels.get(i).getLevel().equals(Constants.PARIMARY)) {
                res.add(Constants.LevelTwo);
            }
            if (lastGreadeModels.get(i).getLevel().equals(Constants.SENIOR)) {
                res.add(Constants.LevelThree);
            }
            if (lastGreadeModels.get(i).getLevel().equals(Constants.KEYMAN)) {
                res.add(Constants.LevelFrou);
            }
        }

        //
        int resGrade = Integer.valueOf(res.get(0).toString());
        //循环数组选出一个最高的等级
        for (int j = 1; j < res.size(); j++) {
            if (resGrade < Integer.valueOf(res.get(j).toString())) {
                resGrade = Integer.valueOf(res.get(j).toString());
            }
        }
        String resLevel = "";
        //将数组等级转换成字符等级
        switch (resGrade) {
            case Constants.LevelOne:
                resLevel = Constants.XIAOBAI;
                break;
            case Constants.LevelTwo:
                resLevel = Constants.PARIMARY;
                break;
            case Constants.LevelThree:
                resLevel = Constants.SENIOR;
                break;
            case Constants.LevelFrou:
                resLevel = Constants.KEYMAN;
                break;
            default:
                resLevel = Constants.KEYMAN;
                break;
        }

        LevelModel levelModel = new LevelModel();
        for (GradeModel grademodel : lastGreadeModels) {
            if (grademodel.getLevel().equals(resLevel)) {

                levelModel.setLevelName(resLevel);
                levelModel.setLevelNum(grademodel.getNum());
            }

        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", levelModel);
    }
    /**
     * @author: 张艳伟
     * @version:按照时间查询成绩-班级模糊查询
     * @param: []
     * @date: 2021年9月26日
     * @time: 2021年9月26日19:54:10
     * @description:
     */
    @Override
    public ItooResult selectRankingByTimeLevelAllClass(String createTime, String endTime) {
        List<PersonRankingModel> personRankingModels = personResultDao.selectRankingByTimeLevelAllClass(createTime, endTime);
        if (personRankingModels == null || personRankingModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        Integer i = 0;
        for (PersonRankingModel personRankingModel : personRankingModels) {
            personRankingModel.setRanking(++i);
            Date time = personRankingModel.getCreateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    //格式化规则
            String strDate = sdf.format(time); //格式化成yyyy-MM-dd格式的时间字符串
            personRankingModel.setTime(strDate);
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", personRankingModels);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年9月22日
     * @time: 2021年9月22日08:08:35
     * @description:
     */
    @Override
    public ItooResult selectRankingByTimeLevelAll(String createTime, String endTime, String className) {
        List<PersonRankingModel> personRankingModels = personResultDao.selectRankingByTimeLevelAll(createTime, endTime, className);
        if (personRankingModels == null || personRankingModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        Integer i = 0;
        for (PersonRankingModel personRankingModel : personRankingModels) {
            personRankingModel.setRanking(++i);
            Date time = personRankingModel.getCreateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    //格式化规则
            String strDate = sdf.format(time); //格式化成yyyy-MM-dd格式的时间字符串
            personRankingModel.setTime(strDate);
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", personRankingModels);
    }


    /*
     * @version V1.0
     * Title: selectClass
     * @author LiuYanQiang
     * @description 查询当前用户的所在班级信息
     * @createTime  2021/9/19 17:58
     * @param [userCode]
     * @return com.dmsdbj.itoo.tool.business.ItooResult*/
    @Override
    public ItooResult selectClass(String userCode) {
        PersonEntity personEntity = personDao.selectPersonEntityByUserId(userCode);
        if (personEntity != null) {
            return new ItooResult(ItooResult.SUCCESS, "查询成功", personEntity);
        } else {
            return new ItooResult(ItooResult.FAIL, "查询信息不存在", personEntity);
        }
    }



    /**
     * @author: 宋文轩
     * @version:
     * @param: []beginTime, endTime, userId
     * @date: 2021年10月5日
     * @description:按照时间查询个人今天有没有邀请过助力
     */
    @Override
    public ItooResult selectImprovedByTime(String beginTime, String endTime, String userId) {
        int  num = personResultDao.selectImprovedByTime(beginTime, endTime, userId);
        if (num<0){
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", num);
    }



    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月5日
     * @time: 2021年10月5日23:46:39
     * @description:教师端按照时间查询邀请助力次数
     */
    @Override
    public ItooResult selectImproved(String createTime, String endTime) {
        List<PersonRankingModel> personRankingModels = personResultDao.selectImproved(createTime, endTime);
        List<PersonRankingModel> personRankingModels1 = new ArrayList<>();
        PersonRankingModel personRanking=new PersonRankingModel();
        if (personRankingModels == null || personRankingModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        int a=0;
        for (int i = 0; i <personRankingModels.size() ; i++) {
            a=0;
            for (int j = 0; j < personRankingModels.size(); j++) {

                if(personRankingModels.get(i).getUserCode().equals(personRankingModels.get(j).getUserCode())){
                    if(personRankingModels.get(i).getCreateDate().equals(personRankingModels.get(j).getCreateDate())){
                       a++;
                    }
                }
            }
            int b=0;
            for (int j = 0; j < personRankingModels1.size(); j++) {
                if(personRankingModels1.get(j).getUserCode().equals(personRankingModels.get(i).getUserCode())){
                    if(personRankingModels1.get(j).getCreateDate().equals(personRankingModels.get(i).getCreateDate())){
                        b++;
                    }

                }
            }
            if(b==0){
                personRankingModels.get(i).setSum(a);
                personRankingModels1.add(personRankingModels.get(i));
                System.out.println(personRankingModels1);
            }
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", personRankingModels1);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月13日
     * @time: 2021年11月13日19:58:02
     * @description:教师端按照时间查询助力总次数
     */
    @Override
    public ItooResult selectAllImproved(String createTime, String endTime) {
        List<PersonRankingModel> personRankingModels = personResultDao.selectAllImproved(createTime, endTime);
//        List<PersonRankingModel> personRankingModels1 = new ArrayList<>();
//        if (personRankingModels == null || personRankingModels.size() == 0) {
//            return new ItooResult(ItooResult.FAIL, "查询失败");
//        }
//        int a=0;
//        for (int i = 0; i <personRankingModels.size() ; i++) {
//            a=0;
//            for (int j = 0; j < personRankingModels.size(); j++) {
//                if(personRankingModels.get(i).getUserCode().equals(personRankingModels.get(j).getUserCode())){
////                    if(personRankingModels.get(i).getCreateDate().equals(personRankingModels.get(j).getCreateDate())){
//                        a++;
////                    }
//                }
//            }
//            int b=0;
//            for (int j = 0; j < personRankingModels1.size(); j++) {
//                if(personRankingModels1.get(j).getUserCode().equals(personRankingModels.get(i).getUserCode())){
//                    if(personRankingModels1.get(j).getCreateDate().equals(personRankingModels.get(i).getCreateDate())){
//                        b++;
//                    }
//
//                }
//            }
//            if(a==0){
//                personRankingModels.get(i).setSum(a);
//                personRankingModels1.add(personRankingModels.get(i));
//                System.out.println(personRankingModels1);
////            }
//        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", personRankingModels);
    }

    @Override
    public ItooResult selectNumByTime(String beginTime, String endTime,String userCode) {
        List<PersonResultModel> personResultModels = personResultDao.selectNumByTime(beginTime, endTime,userCode);

        if (personResultModels == null || personResultModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }

        return new ItooResult(ItooResult.SUCCESS, "查询成功", personResultModels);
    }




    /**
     * @author: 张艳伟
     * @version:教师端按照时间和班级查询邀请助力次数
     * @param: []
     * @date: 2021年10月5日
     * @time: 2021年10月5日23:46:39
     * @description:
     */
    @Override
    public ItooResult selectImprovedByClass(String createTime, String endTime,String className) {
        List<PersonRankingModel> personRankingModels = personResultDao.selectImprovedByClass(createTime, endTime,className);
        List<PersonRankingModel> personRankingModels1 = new ArrayList<>();
        PersonRankingModel personRanking=new PersonRankingModel();
        if (personRankingModels == null || personRankingModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }


        for (int i = 0; i < personRankingModels.size() ; i++) {
            int a=0;
            for (int j = 0; j < personRankingModels.size(); j++) {

                if(personRankingModels.get(i).getUserCode().equals(personRankingModels.get(j).getUserCode())){
                    if(personRankingModels.get(i).getCreateDate().equals(personRankingModels.get(j).getCreateDate())){
                        a++;
                    }
                }
            }
            int b=0;
            for (int j = 0; j < personRankingModels1.size(); j++) {
                if(personRankingModels1.get(j).getUserCode().equals(personRankingModels.get(i).getUserCode())){
                    if(personRankingModels1.get(j).getCreateDate().equals(personRankingModels.get(i).getCreateDate())){
                        b++;
                    }

                }
            }
            if(b==0){
                personRankingModels.get(i).setSum(a);
                personRankingModels1.add(personRankingModels.get(i));
                System.out.println(personRankingModels1);
            }
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", personRankingModels1);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年11月13日
     * @time: 2021年11月13日20:19:38
     * @description:教师端按照时间和班级查询助力总次数
     */
    @Override
    public ItooResult selectAllImprovedByClass(String createTime, String endTime,String className) {
        List<PersonRankingModel> personRankingModels = personResultDao.selectAllImprovedByClass(createTime, endTime,className);
        return new ItooResult(ItooResult.SUCCESS, "查询成功", personRankingModels);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time: 2021年10月11日20:05:56
     * @description:根据学号查询班级前三名
     */
    @Override
    public ItooResult selectClassTop(String userCode) {
        List<PersonRankingModel> personRankingModels = personResultDao.selectClassTop(userCode);
        if (personRankingModels == null || personRankingModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", personRankingModels);

    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date: 2021年10月11日
     * @time: 2021年10月11日20:05:56
     * @description:根据学号查询班级内小组前三名
     */
    @Override
    public ItooResult selectTeamTop(String userCode) {
        List<TeamRankingModel> teamRankingModels = personResultDao.selectTeamTop(userCode);
        if (teamRankingModels == null || teamRankingModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", teamRankingModels);
    }


    @Override
    public ItooResult selectByUserId(String userId ) {
        List<PersonResultModel> personResultModels = personResultDao.selectByUserId(userId);
        if (personResultModels == null || personResultModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", personResultModels);
    }



}
