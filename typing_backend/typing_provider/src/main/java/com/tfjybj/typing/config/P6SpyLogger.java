package com.tfjybj.typing.config;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * p6spy打印日志输出格式修改
 * 1.只打印最终执行的sql.
 * 2.sql换到下一行
 * 3.结尾处增加分号,以标示sql结尾
 *
 * @author likun
 * @since 2017年10月31日
 */
public class P6SpyLogger implements MessageFormattingStrategy {
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        StringBuffer buffer = new StringBuffer();
        if (!("").equals(sql.trim())) {
            return buffer.append("/* ").append(format.format(new Date())).append(" | took ")
                    .append(elapsed).append("ms | ").append(category)
                    .append(" | connection ").append(connectionId).append(" */ \n ")
                    .append(sql).append(";").toString();
        }
        return "";
    }
}
