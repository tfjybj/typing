package com.tfjybj.typing.provider.common;

/**
 * Author: LangFordHao
 * Version:
 * Date: 2020/10/25
 * Time: 14:59
 * Description:${DESCRIPTION}
 */
public class Constants {
    public static final String XIAOBAI="小白键盘手";
    public static final String PARIMARY="初级键盘手";
    public static final String SENIOR="高级键盘手";
    public static final String KEYMAN="键盘侠";
    //速度
    public static final Integer XIAOBAI_SPEED=0;
    public static final Integer PARIMARY_SPEED=100;
    public static final Integer SENIOR_SPEED=160;
    public static final Integer KEYMAN_SPEED=180;
    //正确率
    public static final int XIAOBAI_ACCURACY=0;
    public static final int PARIMARY_ACCURACY=96;
    public static final int SENIOR_ACCURACY=97;
    public static final int KEYMAN_ACCURACY=98;
    //级差系数
    public static final Double XIAOBAI_GRADE=0.3;
    public static final Double PARIMARY_GRADE=0.2;
    public static final Double SENIOR_GRADE=0.1;
    public static final Double KEYMAN_GRADE=0.1;
    //及格分数
    public static final Integer PASS_SCORE=60;
    //基础分数
    public static final Integer XIAOBAI_SCORE=100;
    public static final Integer PARIMARY_SCORE=150;
    public static final Integer SENIOR_SCORE=180;
    public static final Integer KEYMAN_SCORE=200;

    //进级次数
    public static final Integer PARIMARY_RICE=1;
    public static final Integer SENIOR_RICE=2;
    public static final Integer KEYMAN_RICE=3;
    //  比较等级
    public static final int LevelOne=1;
    public static final int LevelTwo=2;
    public static final int LevelThree=3;
    public static final int LevelFrou=4;

    //Msg的URL
    public static final String SEND_MSG="http://msg.dmsd.tech:8002/dingmessage/send/groupMarkdownMsg";
}
