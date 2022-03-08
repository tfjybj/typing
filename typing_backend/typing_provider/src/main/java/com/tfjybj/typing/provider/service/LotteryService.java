package com.tfjybj.typing.provider.service;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.model.LotteryModel;

public interface LotteryService {

    boolean insertLottery(LotteryModel model);
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据学号查询抽奖总次数
     */
    ItooResult selectByUserCode(String userCode);

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据学号查询奖励抽奖次数
     */
    ItooResult selectLotteryByUserCode(String userCode);
}
