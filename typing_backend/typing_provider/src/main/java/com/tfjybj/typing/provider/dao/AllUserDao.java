package com.tfjybj.typing.provider.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author 齐智
 * @Date: 2020/10/27 18:55
 * @Version: 1.0
 **/
@Repository("allUserDao")
public interface AllUserDao {
    String selectUserNameById(String id);

    List<String> selectSchoolNoById(String id);
}
