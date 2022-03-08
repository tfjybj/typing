package com.tfjybj.typing.provider.service;

import com.tfjybj.typing.entity.TypArticleEntity;
import com.dmsdbj.itoo.tool.base.service.BaseServicePlus;
import java.util.List;

/**
 * TypArticleService接口
 * typArticle表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
public interface TypArticleService extends BaseServicePlus<TypArticleEntity> {
    /**
     * 查找所有文章名称
     * @param
     * @return AddActivePerson
     * @author 王闯
     * @since 2019年9月24日19:37:53
     */
    List<String> selectAllArticles();



    /**
     * 查找对应文章内容
     * @param
     * @return AddActivePerson
     * @author 王闯
     * @since 2019年9月23日19:37:53
     */
    List<String> selectAllArticleContent(String articleName);

    /**
     * 将英文文章句子按统一长度分行插入数据库
     *
     * @param filePath
     * @param lineLength
     * @return void
     * @author 杨燕梅
     * @since 2020/10/21 10:16
     */
    void InsertArticeleByLine(String filePath,int lineLength);
}
