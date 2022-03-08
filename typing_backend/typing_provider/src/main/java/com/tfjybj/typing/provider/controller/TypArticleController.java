package com.tfjybj.typing.provider.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.tfjybj.typing.provider.service.TypArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


/**
 * TypArticleController
 * typArticle表
 *
 * @author 闫伟强
 * @version 1.0.0
 * @since 1.0.0 2019-09-23 10:10:56
 */
@Api(tags = {"typArticle表接口"})
@RequestMapping(value = "/typArticle")
@RestController
public class TypArticleController {

    @Resource
    private TypArticleService typArticleService;

    //region 模板生成：基本增删改

//    /**
//     * 添加
//     *
//     * @param model TypArticleModel
//     * @return 添加的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "添加")
//    @PostMapping(value = {"/create"})
//    public ItooResult create(@RequestBody TypArticleModel model) {
//        TypArticleEntity typArticleEntity = new TypArticleEntity();
//        BeanUtils.copyProperties(model, typArticleEntity);
//        typArticleService.save(typArticleEntity);
//        return ItooResult.build(ItooResult.SUCCESS, "添加成功");
//    }
//
//    /**
//     * 删除
//     *
//     * @param id 主键id
//     * @return ItooResult 是否删除成功
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "根据id删除（单个）")
//    @DeleteMapping(value = {"/delete/{id}"})
//    public ItooResult delete(@ApiParam(value = "主键id", required = true) @PathVariable String id) {
//        typArticleService.removeById(id);
//        return ItooResult.build(ItooResult.SUCCESS, "删除成功");
//    }
//
//    /**
//     * 批量删除
//     *
//     * @param ids ids
//     * @return ItooResult 批量删除是否成功结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "根据id批量删除")
//    @DeleteMapping(value = {"/deleteByIds"})
//    @ApiImplicitParam(name = "ids", value = "ids", dataType = "List<String>", required = true)
//    public ItooResult deleteByIds(@RequestBody List<String> ids) {
//        typArticleService.removeByIds(ids);
//        return ItooResult.build(ItooResult.SUCCESS, "批量删除成功");
//    }
//
//    /**
//     * 修改
//     *
//     * @param model TypArticleModel
//     * @return 修改后的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "根据id修改typArticle")
//    @PutMapping(value = {"/modify"})
//    public ItooResult modify(@RequestBody TypArticleModel model) {
//        TypArticleEntity typArticleEntity = new TypArticleEntity();
//        BeanUtils.copyProperties(model, typArticleEntity);
//        typArticleService.updateById(typArticleEntity);
//        return ItooResult.build(ItooResult.SUCCESS, "修改成功");
//    }
//
//    /**
//     * 根据id查找TypArticle
//     *
//     * @param id 主键id
//     * @return 根据id查找的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "根据id查询")
//    @GetMapping(value = {"/findById/{id}"})
//    public ItooResult findById(@ApiParam(value = "主键id", required = true) @PathVariable String id) {
//        TypArticleEntity typArticleEntity = typArticleService.getById(id);
//        return ItooResult.build(ItooResult.SUCCESS, "查询成功", typArticleEntity);
//    }
//
//    /**
//     * 分页查询所有TypArticle
//     *
//     * @param pageNo   页码
//     * @param pageSize 每页条数
//     * @return 分页查询的结果
//     * @author 闫伟强
//     * @since 1.0.0 2019-09-23 10:10:56
//     */
//    @ApiOperation(value = "分页查询所有TypArticle")
//    @GetMapping(value = {"/queryPageAll/{pageNo}/{pageSize}"})
//    public ItooResult queryPageAll(@ApiParam(name = "pageNo",value = "页码",required = true,example = "1")@PathVariable Integer pageNo,
//								   @ApiParam(name = "pageSize",value = "页数",required = true,example = "10")@PathVariable Integer pageSize) {
//        PageInfo<TypArticleEntity> typArticles = typArticleService.queryPageAll(pageNo, pageSize);
//        return ItooResult.build(ItooResult.SUCCESS, "查询成功", typArticles);
//    }
//
    //endregion

    /* **********************************以下为非模板生成的内容********************************* */

    /**
     * 查询所有结束的报名批次
     * @author 王闯
     * @since 2019年7月11日 15:03:29
     * @return
     */
    @ApiOperation(value = "查询文章名称")
    @GetMapping(value = {"SelectArticles"})
    public ItooResult SelectArticles() {
        //        在数据库中查询所有文章信息
        List<String> typArticleEntity = typArticleService.selectAllArticles();
        return ItooResult.build(ItooResult.SUCCESS, "查询成功",typArticleEntity);
    }

    /**
     * 查询对应文章内容
     * @author 王闯
     * @since 2019年7月11日 15:03:29
     * @return
     */
    @ApiOperation(value = "查询文章内容")
    @GetMapping(value = {"/SelectArticleContent/{articleName}"})
    public ItooResult SelectArticleContent(@ApiParam(value = "articleName", required = true)@PathVariable String articleName) {

        //        在数据库中查询对应文章内容
        List<String> typArticleContent = typArticleService.selectAllArticleContent(articleName);
        if (CollectionUtils.isEmpty(typArticleContent)){
            return ItooResult.build(ItooResult.FAIL, "查询失败",typArticleContent);
        }
        return ItooResult.build(ItooResult.SUCCESS, "查询成功",typArticleContent);
    }

    /**
     * 将英文文章句子按统一长度分行插入数据库
     *
     * @param
     * @return com.dmsdbj.itoo.tool.business.ItooResult
     * @author 杨燕梅
     * @since 2020/10/21 11:17
     */
    @ApiOperation(value = "将英文文章句子按统一长度分行插入数据库")
    @PutMapping(value ={"/InsertArticeleByLine"})
    //@ApiParam(value = "文件路径", required = true)@PathVariable String filePath,
    //@ApiParam(value = "每行截取长度", required = true)@PathVariable int lineLength
    public ItooResult InsertArticeleByLine() throws IOException {
        //需要截取的文件路径
        String filePath="D:\\【E】-Learning\\Project\\typeProject\\English\\E_General\\American personal hygiene.txt";
        //设置截取的每行最大长度
        int lineLength=60;
        typArticleService.InsertArticeleByLine(filePath,lineLength);
        return ItooResult.build(ItooResult.SUCCESS,"插入成功");
    }

    //@ApiOperation(value="统计每篇文章里英文字母和标点符号出现的次数")
    //@PutMapping(value ={"/InsertArticeleByLine/{filePath}/{lineLength}"})

}    
