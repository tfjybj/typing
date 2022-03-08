package com.tfjybj.typing.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfjybj.typing.entity.TypArticleEntity;
import com.tfjybj.typing.model.TypArticleModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TypArticleDao接口
 * typArticle表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@Repository("typArticleDao")
public interface TypArticleDao extends BaseMapper<TypArticleEntity> {


    /**
     * 查询所有文章名称
     * @return List<String>
     * @author 王闯
     * @since 2019年9月23日 15:07:08
     */
    List<String> selectAllArticles();


    /**
     * 查询对应文章内容
     * @return List<String>
     * @author 王闯
     * @since 2019年9月23日 15:07:08
     */
    List<String> selectAllArticleContent(@Param("articleName") String articleName);

    /**
     * 将英文文章句子按统一长度分行插入数据库
     *
     * @param typArticleList
     * @return java.util.List<com.tfjybj.typing.model.TypArticleModel>
     * @author 杨燕梅
     * @since 2020/10/21 10:16
     */
    void InsertArticeleByLine(List<TypArticleModel> typArticleList);

}
