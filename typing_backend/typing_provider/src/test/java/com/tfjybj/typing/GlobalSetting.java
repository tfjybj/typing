package com.tfjybj.typing;


import com.tfjybj.typing.utils.cache.JSONUtils;

/**
 * @author quinn
 * @version 创建时间：2018/10/10 17:41
 */
public class GlobalSetting {
    /**
     * 对象对jsonstring 格式化且日期转为+8显示
     *
     * @param obj
     * @return
     */
    public static final String toDisplay(Object obj) {
        return JSONUtils.toJSONPrettyString(obj);
    }
}
