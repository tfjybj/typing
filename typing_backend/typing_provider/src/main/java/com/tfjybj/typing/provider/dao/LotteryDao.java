package com.tfjybj.typing.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.model.LotteryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("lotteryDao")
public interface LotteryDao extends BaseMapper<LotteryModel> {

    boolean insertLottery(@Param("LotteryModel") LotteryModel model);


    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据学号查询抽奖总次数
     */
    List<LotteryModel> selectByUserCode(@Param("userCode") String userCode );

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据学号查询奖励抽奖次数
     */
    List<LotteryModel> selectLotteryByUserCode(@Param("userCode") String userCode );
}
