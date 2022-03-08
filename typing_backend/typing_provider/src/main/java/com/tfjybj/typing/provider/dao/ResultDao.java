package com.tfjybj.typing.provider.dao;

import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author 齐智
 * @Date: 2020/10/21 17:11
 * @Version: 1.0
 **/
@Repository("resultDao")
public interface ResultDao {
    Double selectTeamResult(String userId);

}
