package com.tfjybj.typing;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * @author quinn
 * @version 创建时间：2018/10/10 17:41
 */
public class TestExecTimeWatcher extends TestWatcher {
    private static final Logger logger = LoggerFactory.getLogger(TestExecTimeWatcher.class);

    private StopWatch sw = new StopWatch();

    protected void starting(Description description) {
        String name = description.getDisplayName(); //description.getTestClass() + " : " + description.getMethodName() + " _ " +
        logger.warn("");
        logger.warn("---------- Begin Test : " + name + "----------------------------------------------------------");
        sw.start();
    }

    protected void finished(Description description) {
        sw.stop();

        long elapsed = sw.getLastTaskTimeMillis();
        String name = description.getDisplayName(); //description.getTestClass() + " : " + description.getMethodName() + " _ " +
        logger.warn(">>>>>   执行时长: " + elapsed + ".  " + name + " <<<<<<<<<<<<<<<<<<<<");
    }
}