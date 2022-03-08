package com.tfjybj.typing.provider.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dmsdbj.itoo.tool.base.service.impl.BaseServicePlusImpl;
import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.model.LotteryModel;
import com.tfjybj.typing.provider.dao.LotteryDao;
import com.tfjybj.typing.provider.service.LotteryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: swx
 * @Date: 2021/10/21
 * @Modified By:
 * @Modify Date:
 */
@Service("LotteryService")
public class LotteryServiceImpl extends BaseServicePlusImpl<LotteryDao, LotteryModel> implements LotteryService {
    @Resource
    private LotteryDao lotteryDao;

    @Override
    public boolean insertLottery(@Param("LotteryModel") LotteryModel model) {
        //设置ID为雪花算法产生的ID
        model.setId(IdWorker.getIdStr());
        model.setCreateTime(new Date());
        lotteryDao.insertLottery(model);
        return true;
    }
    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据学号查询抽奖总次数
     */
    @Override
    public ItooResult selectByUserCode(String userCode) {
        List<LotteryModel> lotteryModels = lotteryDao.selectByUserCode(userCode);
        if (lotteryModels == null || lotteryModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", lotteryModels);
    }

    /**
     * @author: 张艳伟
     * @version:
     * @param: []
     * @date:
     * @time:
     * @description:根据学号查询奖励抽奖次数
     */
    @Override
    public ItooResult selectLotteryByUserCode(String userCode) {
        List<LotteryModel> lotteryModels = lotteryDao.selectLotteryByUserCode(userCode);
        if (lotteryModels == null || lotteryModels.size() == 0) {
            return new ItooResult(ItooResult.FAIL, "查询失败");
        }
        return new ItooResult(ItooResult.SUCCESS, "查询成功", lotteryModels);
    }


}
