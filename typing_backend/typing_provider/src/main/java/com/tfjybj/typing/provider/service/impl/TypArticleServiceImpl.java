package com.tfjybj.typing.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dmsdbj.itoo.tool.base.service.impl.BaseServicePlusImpl;
import com.tfjybj.typing.entity.TypArticleEntity;
import com.tfjybj.typing.model.TypArticleModel;
import com.tfjybj.typing.provider.dao.TypArticleDao;
import com.tfjybj.typing.provider.service.TypArticleService;
import com.tfjybj.typing.utils.cache.RedisUtil;
import com.tfjybj.typing.utils.cache.TypingReids;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TypArticleService接口实现类
 * ${base}表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@Service("typArticleService")
public class TypArticleServiceImpl extends BaseServicePlusImpl<TypArticleDao, TypArticleEntity> implements TypArticleService {

    //region 模板生成
    @Resource
    private TypArticleDao typArticleDao;

    @Resource
    private RedisUtil redisUtil;


    //endregion

    /* **********************************以下为非模板生成的内容********************************* */

    /**
     * 查询所有文章名称
     *
     * @param
     * @return AddActivePerson
     * @author 王闯
     * @since 2019年9月24日19:37:53
     */
    @Override
    public List<String> selectAllArticles() {
        List<String> articleList = redisUtil.lGet(TypingReids.ALL_ARTICLE, String.class);
        if (!CollectionUtils.isEmpty(articleList)){
            return articleList;
        }
        List<String> strings = typArticleDao.selectAllArticles();
        redisUtil.lSet(TypingReids.ALL_ARTICLE ,strings);
        redisUtil.expire(TypingReids.ALL_ARTICLE ,24 * 3600);
        return strings;
    }

    /**
     * 查询对应文章内容
     *
     * @param
     * @return list
     * @author 王闯
     * @since 2019年9月22日19:37:53
     */
    @Override
    public List<String> selectAllArticleContent(String articleName) {
        //从redis中查询是否有对应文章的内容
        String articelContent = redisUtil.hget(TypingReids.ARTICLE_CONTENT,articleName);
        //判断redis中查找到的数据是否为空
        if (StringUtils.isEmpty(articelContent)) {
            //从数据库中根据文章名称查找对应文章内容
            List<String> articelContentList = typArticleDao.selectAllArticleContent(articleName);
            //将文章内容插入redis中
            redisUtil.hset(TypingReids.ARTICLE_CONTENT,articleName,JSON.toJSONString(articelContentList));
            //设置redis数据存在时间为12小时
            redisUtil.expire(TypingReids.ARTICLE_CONTENT,12 * 3600);
            return articelContentList;
        }
        //用json格式获取reids中的文章内容
        List<String> articelContentList = JSON.parseArray(articelContent,String.class);
        return articelContentList;
    }

    /**
     * 将英文文章句子按统一长度分行插入数据库
     *
     * @param filePath
     * @param lineLength
     * @return void
     * @author 杨燕梅
     * @since 2020/10/21 10:16
     */
    @Override
    public void InsertArticeleByLine(String filePath, int lineLength) {
        //定义一个typArticleList存放截取的句子和每个句子对应的值
        List<TypArticleModel> typArticleList=new ArrayList<>();
        //定义一个存放被截取文章名和对应字段值的实体
        TypArticleModel typArticle=new TypArticleModel();
        //设置Pid为雪花算法生成
        String Pid= IdWorker.getIdStr();
        //设置当前截取位置
        int currentIndex=0;
        //设置行标识
        int lineIndex=0;
        //读取文件
        String fileContent= null;
        File file=new File(filePath);
        try {
            fileContent = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将换行符替换为空格
        fileContent=fileContent.replaceAll("\r\n+"," ");
        //将字符串中多个空格替换成一个空格,避免多个空格插入数据库出现乱码
        fileContent=fileContent.replaceAll("\\s+", " ");
        //当当前截取位置小于文章长度（所有字母个数）
        while (currentIndex<fileContent.length()){
            //开始截取位置（第几个字母）
            int startIndex=currentIndex;
            //结束截取位置=开始截取位置+截取长度
            int maxEndIndex=startIndex+lineLength;
            //如果结束截取位置大于等于文章长度（所有字母个数）
            if(maxEndIndex>=fileContent.length()){
                //当前截取位置（第几个字母）等于文章长度（所有字母个数）
                currentIndex=fileContent.length();
            }else{
                //找到截取长度范围内的最后一个空格，避免将单词切分
                int i = fileContent.lastIndexOf(' ', maxEndIndex);
                //如果
                if(i<0 || i==startIndex){
                    currentIndex=maxEndIndex;
                }else{
                    currentIndex=i;
                }
                //当前截取位置加1
                currentIndex=currentIndex+1;
            }
            //行长度标识加1
            lineIndex=lineIndex+1;
            //截取句子等于从开始截取位置到当前截取位置的所有单词+标点符号+空格；
            String lineContent=fileContent.substring(startIndex,currentIndex);
            //定义一个存放截取句子和对应字段值的实体
            TypArticleModel typArticleModel=new TypArticleModel();
            //设置插入实体的各字段的值（截取的每个句子）
            typArticleModel.setId(Pid+lineIndex);
            typArticleModel.setPId(Pid);
            typArticleModel.setName(lineContent);
            typArticleModel.setChildOrder(lineIndex);
            //将截取出来的句子和各字段的值存入typArticleList中
            typArticleList.add(typArticleModel);
            System.out.println("第"+lineIndex+"内容");
            System.out.println(lineContent);
        }
        filePath.trim();
        //从文件路径中截取不包含后缀名的文件名作为文章名（replaceAll("[.][^.]+$", "")去掉获取到的文件名的后缀名）
        String ArticleName=file.getName().replaceAll("[.][^.]+$", "");
        //设置插入实体的各字段的值（文章名）
        typArticle.setId(Pid);
        //文章名实体的name为文章名
        typArticle.setName(ArticleName);
        //文章名对应的Pid必须为0
        typArticle.setPId("0");
        //将被截取的文章名和各字段的值存入typArticleList中
        typArticleList.add(typArticle);
        //确定typArticleList里有值（即size大于0）再插入到数据库中
        if (typArticleList.size()>0){
            typArticleDao.InsertArticeleByLine(typArticleList);
        }
    }
}
