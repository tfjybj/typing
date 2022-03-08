package com.tfjybj.typing.provider.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tfjybj.typing.entity.IntegralEntity;
import com.tfjybj.typing.entity.PersonResultEntity;
import com.tfjybj.typing.entity.TeamEntity;
import com.tfjybj.typing.model.*;
import com.tfjybj.typing.provider.common.Constants;
import com.tfjybj.typing.provider.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/10/25
 * Time: 9:17
 * Description:${DESCRIPTION}
 * 判断用户等级与分数
 */
@Service("ratingServiceImpl")
public class RatingServiceImpl  {


    @Autowired
    private PersonResultDao personResultDao;
    @Autowired
    private IntegralDao integralDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private TeamDao teamDao;

    @Resource
    private PersonResultServiceImpl personResultService;

    @Autowired
    private AllUserDao allUserDao;


    /**
     * 计算个人得分
     * @param userId
     * @return
     */
    public PersonIntegralModel calculateIntegral(String userId)
    {
        PersonIntegralModel personIntegralModel = calculateOwnerIntegral(userId);

        //3.团队得分的计算

        Double teamGrade = calculateTeamIntegral(userId);

        if(personIntegralModel==null)
        {
            personIntegralModel=new PersonIntegralModel();
        }
        personIntegralModel.setTeamGrade(teamGrade);


        return personIntegralModel;
    }


    private Double calculateTeamIntegral(String userId)
    {
        //3.团队得分的计算
        List<TeamIntegralModel> teamIntegralModels = personDao.selectTeamByUserId(userId);
        //如果没有团队，则团队成绩显示0
        if(teamIntegralModels.size()<=0)
        {
            return 0.0;
        }
        String teamId=teamIntegralModels.get(0).getTeamId().toString();
        double numberCount=0;
        double teamIntegral=0.0;
        for(TeamIntegralModel t: teamIntegralModels)
        {
            if(t.getTotalGrade()!=null)
            {
                teamIntegral+=t.getTotalGrade();
                numberCount++;
            }
        }
        teamIntegral=teamIntegral/numberCount;
        //4.将团队积分插入到团队表中
        //构造实体
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(IdWorker.getIdStr());
        teamEntity.setTeamId(teamId);
        String teamName = getTeamName(teamId);
        teamEntity.setTeamName(teamName);
        teamEntity.setGroupResults(teamIntegral);
        teamDao.updateTeamIsDelete(teamId);
        teamDao.insertTeam(teamEntity);



        return  teamIntegral;
    }
    private String getTeamName(String teamId) {
        String teamName = null;
        switch (teamId) {
            case "2101":
                teamName = "21级生物科学类本科1班第1组";
            case "2102":
                teamName = "21级生物科学类本科1班第2组";
            case "2103":
                teamName = "21级生物科学类本科1班第3组";
            case "2104":
                teamName = "21级生物科学类本科1班第4组";
            case "2105":
                teamName = "21级生物科学类本科1班第5组";
            case "2106":
                teamName = "21级生物科学类本科1班第6组";
            case "2107":
                teamName = "21级生物科学类本科1班第7组";
            case "2108":
                teamName = "21级生物科学类本科1班第8组";
            case "2109":
                teamName = "21级生物科学类本科1班第9组";
            case "2110":
                teamName = "21级生物科学类本科1班第10组";
            case "2111":
                teamName = "21级生物科学类本科1班第11组";
            case "2112":
                teamName = "21级生物科学类本科1班第12组";
            case "2113":
                teamName = "21级生物科学类本科2班第1组";
            case "2114":
                teamName = "21级生物科学类本科2班第2组";
            case "2115":
                teamName = "21级生物科学类本科2班第3组";
            case "2116":
                teamName = "21级生物科学类本科2班第4组";
            case "2117":
                teamName = "21级生物科学类本科2班第5组";
            case "2118":
                teamName = "21级生物科学类本科2班第6组";
            case "2119":
                teamName = "21级生物科学类本科2班第7组";
            case "2120":
                teamName = "21级生物科学类本科2班第8组";
            case "2121":
                teamName = "21级生物科学类本科2班第9组";
            case "2122":
                teamName = "21级生物科学类本科2班第10组";
            case "2123":
                teamName = "21级生物科学类本科2班第11组";
            case "2124":
                teamName = "21级生物科学类本科2班第12组";
            case "2125":
                teamName = "21级生物科学类本科3班第1组";
            case "2126":
                teamName = "21级生物科学类本科3班第2组";
            case "2127":
                teamName = "21级生物科学类本科3班第3组";
            case "2128":
                teamName = "21级生物科学类本科3班第4组";
            case "2129":
                teamName = "21级生物科学类本科3班第5组";
            case "2130":
                teamName = "21级生物科学类本科3班第6组";
            case "2131":
                teamName = "21级生物科学类本科3班第7组";
            case "2132":
                teamName = "21级生物科学类本科3班第8组";
            case "2133":
                teamName = "21级生物科学类本科3班第9组";
            case "2134":
                teamName = "21级生物科学类本科3班第10组";
            case "2135":
                teamName = "21级生物科学类本科3班第11组";
            case "2136":
                teamName = "21级生物科学类本科3班第12组";
            case "2137":
                teamName = "21级音乐表演本科1班第1组";
            case "2138":
                teamName = "21级音乐表演本科1班第2组";
            case "2139":
                teamName = "21级音乐表演本科1班第3组";
            case "2140":
                teamName = "21级音乐表演本科1班第4组";
            case "2141":
                teamName = "21级音乐表演本科1班第5组";
            case "2142":
                teamName = "21级音乐表演本科1班第6组";
            case "2143":
                teamName = "21级音乐表演本科1班第7组";
            case "2144":
                teamName = "21级音乐表演本科1班第8组";
            case "2145":
                teamName = "21级音乐表演本科1班第9组";
            case "2146":
                teamName = "21级音乐表演本科1班第10组";
            case "2147":
                teamName = "21级音乐表演本科1班第11组";
            case "2148":
                teamName = "21级音乐表演本科1班第12组";
            case "2149":
                teamName = "21级音乐表演本科1班第13组";

            case "2150":
                teamName = "计算机协会第1组";
            case "2151":
                teamName = "计算机协会第2组";
            case "2152":
                teamName = "计算机协会第3组";
            case "2153":
                teamName = "计算机协会第4组";
                case "2154":
                teamName = "计算机协会第5组";
                case "2155":
                teamName = "计算机协会第6组";
            case "2156":
                teamName = "计算机协会第7组";
            case "2157":
                teamName = "计算机协会第8组";
            case "2158":
                teamName = "计算机协会第9组";
            case "2159":
                teamName = "计算机协会第10组";
            case "2160":
                teamName = "计算机协会第11组";
            case "2161":
                teamName = "计算机协会第12组";
            case "2162":
                teamName = "计算机协会第13组";
            case "2163":
                teamName = "计算机协会第14组";
            case "2164":
                teamName = "计算机协会第15组";
            case "2165":
                teamName = "计算机协会第16组";
            case "2166":
                teamName = "计算机协会第17组";
                break;
            case "2167":
                teamName = "计算机协会第18组";
                break;
            case "2168":
                teamName = "计算机协会第19组";
                break;
            case "2169":
                teamName = "计算机协会第20组";
                break;
            case "2170":
                teamName = "计算机协会第21组";
                break;
            case "2171":
                teamName = "计算机协会第22组";
                break;
            case "2172":
                teamName = "计算机协会第23组";
                break;
            case "2173":
                teamName = "计算机协会第24组";
                break;
            case "2174":
                teamName = "计算机协会第25组";
                break;
            case "2175":
                teamName = "计算机协会第26组";
                break;
            case "2176":
                teamName = "计算机协会第27组";
                break;
            case "2177":
                teamName = "计算机协会第28组";
                break;
            case "2178":
                teamName = "计算机协会第29组";
                break;
            case "2179":
                teamName = "计算机协会第30组";
                break;
            case "2180":
                teamName = "计算机协会第31组";
                break;
            case "2181":
                teamName = "计算机协会第32组";
                break;
            case "2182":
                teamName = "计算机协会第33组";
                break;
            case "2183":
                teamName = "计算机协会第34组";
                break;
            case "2184":
                teamName = "计算机协会第35组";
                break;
            case "2185":
                teamName = "计算机协会第36组";
                break;
            case "2186":
                teamName = "计算机协会第37组";
                break;
            case "2187":
                teamName = "计算机协会第38组";
                break;
            case "2188":
                teamName = "计算机协会第39组";
                break;
            case "2189":
                teamName = "计算机协会第40组";
                break;
            case "2190":
                teamName = "计算机协会第41组";
                break;
            case "2191":
                teamName = "计算机协会第42组";
                break;
            case "2192":
                teamName = "计算机协会第43组";
                break;
            case "2193":
                teamName = "计算机协会第44组";
                break;
            case "2194":
                teamName = "计算机协会第45组";
                break;
            case "2195":
                teamName = "计算机协会第46组";
                break;
            case "2196":
                teamName = "计算机协会第47组";
                break;
            case "2197":
                teamName = "计算机协会第48组";
                break;
            case "2198":
                teamName = "计算机协会第49组";
                break;
            case "2199":
                teamName = "计算机协会第50组";
                break;

            case "2200":
                teamName = "计算机协会第51组";
                break;
            case "2201":
                teamName = "计算机协会第52组";
                break;
            case "2202":
                teamName = "计算机协会第53组";
                break;
            case "2203":
                teamName = "计算机协会第54组";
                break;
            case "2204":
                teamName = "计算机协会第55组";
                break;
            case "2205":
                teamName = "计算机协会第56组";
                break;
            case "2206":
                teamName = "计算机协会第57组";
                break;
            case "2207":
                teamName = "计算机协会第58组";
                break;
            case "2208":
                teamName = "计算机协会第59组";
                break;
            case "2209":
                teamName = "计算机协会第60组";
                break;
            case "2210":
                teamName = "计算机协会第61组";
                break;
            case "2211":
                teamName = "计算机协会第62组";
                break;
            case "2212":
                teamName = "计算机协会第63组";
                break;
            case "2213":
                teamName = "计算机协会第64组";
                break;
            case "2214":
                teamName = "计算机协会第65组";
                break;
            case "2215":
                teamName = "计算机协会第66组";
                break;
            case "2216":
                teamName = "计算机协会第67组";
                break;
            case "2217":
                teamName = "计算机协会第68组";
                break;
            case "2218":
                teamName = "计算机协会第69组";
                break;
            case "2219":
                teamName = "计算机协会第70组";
                break;
            case "2220":
                teamName = "计算机协会第71组";
                break;
            case "2221":
                teamName = "计算机协会第72组";
                break;
            case "2222":
                teamName = "计算机协会第73组";
                break;
            case "2223":
                teamName = "计算机协会第74组";
                break;
            case "2224":
                teamName = "计算机协会第75组";
                break;

        }
        return teamName;
    }
    /**
     *  计算个人得分
     * @param userId
     * @return
     */
    private PersonIntegralModel calculateOwnerIntegral(String userId)
    {
        //查询最近一次的打字结果：正确率和速度
        PersonResultEntity personResultEntity = personResultDao.selectOneNewPersonResultById(userId);
        if (personResultEntity == null)
        {
            //return ItooResult.build(ItooResult.FAIL,"查询失败，该用户没有打字记录");
            return null;
        }
        //根据打字的正确率和速度，计算结果
        RatingModel ratingModel = userRating(personResultEntity.getRightRate(), personResultEntity.getSpeed(),personResultEntity.getUserId());
        //将结果插入到数据库

        //1.将原始的数据，假删除
        integralDao.updatePersonIntegralIsDelete(userId);

        //2.构造实体，插入到Integra表中
        IntegralEntity integralEntity = makeIntegralEntityByRatingModel(ratingModel, userId);
        integralDao.insertIntegral(integralEntity);

        //3.构造返回值实体
        PersonIntegralModel personIntegralModel=new PersonIntegralModel();
        personIntegralModel.setLevel(integralEntity.getLevel());
        personIntegralModel.setTotalGrade(integralEntity.getTotalGrade());
        personIntegralModel.setProgressGrade(integralEntity.getProgressGrade());

        //如果打字等级提升，则将该数据插入到improveReport表中
        //查询删除记录中，日期是最近的一次打字记录的级别
        List<RiseCountModel> riseCountModels = integralDao.selectLevelRecord(userId);


        //查询不到记录，则返回空
        if (riseCountModels.size()==0){
            return personIntegralModel;
        }
        //选择查询到的第一条记录，即日期最近的打字记录
        RiseCountModel rc = riseCountModels.get(0);
        ImproveReportModel improveReportModel = new ImproveReportModel();
        improveReportModel.setLevel(personIntegralModel.getLevel());
        improveReportModel.setUserName(rc.getUserName());
        improveReportModel.setId(IdWorker.getIdStr());
        improveReportModel.setUserId(userId);
        improveReportModel.setCreateTime(LocalDateTime.now());

        //判断最近一次打字记录的级别与当前的新纪录想比是否晋级，如果是，则将新纪录插入到ty_improve_report表中
        Integer before = personResultService.calculateRiseCount(rc.getLevel());
        Integer now = personResultService.calculateRiseCount(personIntegralModel.getLevel());
        if (now > before)
        {
            integralDao.insertImprovement(improveReportModel);
        }
        /*if (rc.getLevel()==Constants.XIAOBAI && personIntegralModel.getLevel()==Constants.PARIMARY)
        {
            integralDao.insertImprovement(improveReportModel);
        } else if (rc.getLevel()==Constants.PARIMARY && personIntegralModel.getLevel()==Constants.SENIOR)
        {
            integralDao.insertImprovement(improveReportModel);
        } else if (rc.getLevel()==Constants.SENIOR && personIntegralModel.getLevel()==Constants.KEYMAN)
        {
            integralDao.insertImprovement(improveReportModel);
        }*/

        return personIntegralModel;

    }



    /**
     * 构造IntegralEntity实体
     * @param ratingModel
     * @param userId
     * @return
     */
    private IntegralEntity makeIntegralEntityByRatingModel(RatingModel ratingModel,String userId)
    {
        IntegralEntity integralEntity = new IntegralEntity();
        //id
        integralEntity.setId(IdWorker.getIdStr());
        //用户id
        integralEntity.setUserId(userId);
        //姓名
        //查询姓名：
        String userName = allUserDao.selectUserNameById(userId);
        integralEntity.setUserName(userName);
        //级别
        integralEntity.setLevel(ratingModel.getRatingName());
        //基础得分
        integralEntity.setBaseGrade(ratingModel.getBasicScore());
        //动态分
        integralEntity.setBonusGrade(ratingModel.getDynamicScore());
        //本次得分
        double sum=ratingModel.getBasicScore()+ratingModel.getDynamicScore();
        integralEntity.setTotalGrade(sum);

        //积分进步=这次得分-上次得分
        //1.查询上次的得分。
        Double lastTotalIntegral = integralDao.selectLastTotalIntegral(userId);
        if(lastTotalIntegral==null)
        {
            lastTotalIntegral=0.0;
        }
        //2.定义一个变量，存放进步积分
        double progressGrade=0.0;
        //3.如果进步积分大于0，就显示进步积分；否则显示0
        if(sum-lastTotalIntegral>0)
        {
            progressGrade=sum-lastTotalIntegral;
        }
        //4.设置进步进步
        integralEntity.setProgressGrade(progressGrade);

        return integralEntity;
    }

    //判断用户等级
    /*
    * @author: 郝龙飞
    * @version:
    * @param: [accuracy正确率, speed速度]
    * @date: 2020/10/25
    * @time: 9:30
    * @description:判断用户等级
    */

    public RatingModel userRating(int accuracy,double speed,String userId){

        //stream流筛选num大于10的等级
        //num大于10的等级之间对比，选出等级最大的返给前端，进行级别渲染
        List<GradeModel> gradeModels = integralDao.selectGrade(userId);


        /***************   算法 *****************/
        //   四个等级的次数相加: 小白= 小白.num+初级.num+高级.num+侠.num
        //                  初级=初级.num+ 高级.num+ 侠.num
        //                  高级=高级.num+ 侠.num
        //                  侠=侠.num
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

        RatingModel ratingModel=new RatingModel();
       //用户等级分小白键盘手，初级键盘手，高级键盘手，键盘侠
        if(accuracy>=Constants.KEYMAN_ACCURACY && speed>=Constants.KEYMAN_SPEED && Integer.parseInt(senior)>=10 ){
            //键盘侠
            ratingModel.setRatingName(Constants.KEYMAN);
            ratingModel.setBasicScore(Constants.KEYMAN_SCORE);
            ratingModel.setDynamicScore(keyManDynamicScore(accuracy, speed));

        }else if(accuracy>=Constants.SENIOR_ACCURACY && speed>=Constants.SENIOR_SPEED && Integer.parseInt(parimary)>=10){
            //高级键盘手
            ratingModel.setRatingName(Constants.SENIOR);
            ratingModel.setBasicScore(Constants.SENIOR_SCORE);
            ratingModel.setDynamicScore(seniorDynamicScore(accuracy, speed));

        }else if(accuracy>=Constants.PARIMARY_ACCURACY && speed>=Constants.PARIMARY_SPEED && Integer.parseInt(xiaobai)>=10){
            //初级键盘手
            ratingModel.setRatingName(Constants.PARIMARY);
            ratingModel.setBasicScore(Constants.PARIMARY_SCORE);
            ratingModel.setDynamicScore(primaryDynamicScore(accuracy, speed));

        }else {
            //小白键盘手
            ratingModel.setRatingName(Constants.XIAOBAI);
            ratingModel.setBasicScore(0);
            ratingModel.setDynamicScore(xiaobaiDynamicScore( accuracy, speed));
        }


        return ratingModel;
    }
    //计算动态得分

    //score= （当前打字速度-及格速度）/极差系数 +60
    //稽查系数=（基础速度-及格速度）/分差
    //分差=总分（100）-及格分数（60）

    //初级动态得分
    private double primaryDynamicScore(int accuracy,double speed){
        double score=0;
        if (accuracy>=Constants.SENIOR_ACCURACY){
            score=(speed-Constants.PARIMARY_SPEED)/Constants.PARIMARY_GRADE+Constants.PASS_SCORE;
        }

        return score;
    }
    //高级动态得分
    private double seniorDynamicScore(int accuracy,double speed){
        double score=0;
        if (accuracy>=Constants.KEYMAN_ACCURACY){
            score=(speed-Constants.SENIOR_SPEED)/Constants.KEYMAN_GRADE+Constants.PASS_SCORE;
        }

        return score;
    }
    //键盘侠动态得分
    private double keyManDynamicScore(int accuracy,double speed){
        double score=0;
        return score;
    }
    //小白动态得分
    private double xiaobaiDynamicScore(int accuracy,double speed){
        double score=0;
        if (accuracy>=Constants.XIAOBAI_ACCURACY){
           score=speed;
        }
        return score;
    }

}
