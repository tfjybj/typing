package com.tfjybj.typing;

import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author quinn
 * @version 创建时间：2018/10/10 16:53
 */
public abstract class TestBase {
    protected static final Logger logger = LoggerFactory.getLogger(TestBase.class);

    @Rule
    public TestExecTimeWatcher watcher = new TestExecTimeWatcher();

    public void logDebug(Object obj) {
        if (logger.isDebugEnabled()) {
            logger.debug(GlobalSetting.toDisplay(obj));
        }
    }

    public void logDebug(String msg, Object obj) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg);
            logger.debug(GlobalSetting.toDisplay(obj));
        }
    }

    public void logInfo(Object obj) {
        logger.info(GlobalSetting.toDisplay(obj));
    }

    public void logInfo(String msg, Object obj) {
        logger.info(msg);
        logger.info(GlobalSetting.toDisplay(obj));
    }

    public void logWarn(Object obj) {
        logger.warn(GlobalSetting.toDisplay(obj));
    }

    public void logWarn(String msg, Object obj) {
        logger.warn(msg);
        logger.warn(GlobalSetting.toDisplay(obj));
    }

    public void logInfoRaw(Object obj) {
        logger.info(GlobalSetting.toDisplay(obj));
    }

    public void logWarnRaw(Object obj) {
        logger.warn(GlobalSetting.toDisplay(obj));
    }

    public void logError(String msg, Exception ex) {
        logger.error(msg, ex);
    }

    /**
     * 此方法只会在运行所有单元测试前执行一次,
     * 通常用來获取数据库连接,Spring管理的Bean等等
     */
    //@BeforeClass
    public static void placeholderBeforeClass() {
    }

    /**
     * 此方法运行每个单元测试前都会执行,
     * 通常用来准备数据或获取单元测试依赖的数据或对象
     */
    // @Before
    public void placeHolderSetUp() {

    }

    /**
     * 请注意注解上的experced,使用该参数代表可以认为
     * 这个单元测试方法会抛出Exception的异常,若然没有视为不通过
     */
    // @Test(expected = Exception.class)
    public void placeHolderTestExpectedException() {
        // throw new Exception("placeholder");
    }

    /**
     * 请注意注解上的timeout,使用该参数代表该单元测试需要
     * 在1000毫秒内完成,否则视为不通过,可以用做简单的性能测试
     */
    @Test(timeout = 1000)
    public void placeHolderTestTimeout() {
    }

    /**
     * 此方法运行每个单元测试后都会执行,
     * 主要用来和setUp对应,清理获取的资源
     */
    // @After
    public void placeHolderTearDown() {
    }

    /**
     * 此方法会在运行所有单元测试后执行一次,
     * 通常用来释放资源,例如数据库连接,IO流等等
     */
    // @AfterClass
    public static void placeHolderDestroy() {
    }
}
