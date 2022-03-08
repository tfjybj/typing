package com.tfjybj.typing.utils.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.alibaba.fastjson.util.IOUtils.FASTJSON_COMPATIBLEWITHFIELDNAME;
import static com.alibaba.fastjson.util.IOUtils.FASTJSON_COMPATIBLEWITHJAVABEAN;




/**
 * Created by will on 12/07/2017.
 */
public class FastJsonWrapper {

    static {
        System.setProperty(FASTJSON_COMPATIBLEWITHJAVABEAN, "true");
        System.setProperty(FASTJSON_COMPATIBLEWITHFIELDNAME, "true");
    }

    public static boolean isBadJson(String jsonStr) {
        return !isGoodJson(jsonStr);
    }

    private static SerializeFilter serializeFilter = new ContextValueFilter() {
        @Override
        public Object process(BeanContext context, Object object, String name, Object value) {

            if (value == null) {
                if (context == null && (object.getClass() == HashMap.class || object.getClass() == HashedMap.class)) {
                    return "";
                }
                if (context.getFieldClass() == String.class) {
                    return "";
                } else if (context.getFieldClass() == List.class) {
                    return Lists.newArrayList();
                } else if (context.getFieldClass() == Map.class) {
                    return Maps.newHashMap();
                } else if (context.getFieldClass() == Set.class) {
                    return Sets.newHashSet();
                } else if (context.getFieldClass() == boolean.class) {
                    return false;
                } else if (context.getFieldClass() == byte.class) {
                    return 0;
                } else if (context.getFieldClass() == char.class) {
                    return '\u0000';
                } else if (context.getFieldClass() == short.class) {
                    return 0;
                } else if (context.getFieldClass() == int.class) {
                    return 0;
                } else if (context.getFieldClass() == float.class) {
                    return 0f;
                } else if (context.getFieldClass() == long.class) {
                    return 0l;
                } else if (context.getFieldClass() == double.class) {
                    return 0d;
                } else if (context.getFieldClass() == Byte[].class) {
                    return new Byte[0];
                } else if (context.getFieldClass() == Character[].class) {
                    return new Character[0];
                } else if (context.getFieldClass() == Short[].class) {
                    return new Short[0];
                } else if (context.getFieldClass() == Integer[].class) {
                    return new Integer[0];
                } else if (context.getFieldClass() == Long[].class) {
                    return new Long[0];
                } else if (context.getFieldClass() == Boolean[].class) {
                    return new Boolean[0];
                } else if (context.getFieldClass() == Float[].class) {
                    return new Float[0];
                } else if (context.getFieldClass() == Double[].class) {
                    return new Double[0];
                } else if (context.getFieldClass() == String[].class) {
                    return new String[0];
                } else if (context.getFieldClass() == BigInteger[].class) {
                    return new BigInteger[0];
                } else if (context.getFieldClass() == BigDecimal[].class) {
                    return new BigDecimal[0];
                } else {
                    return null;
                }
            }
            return value;
        }
    };

    public static boolean isGoodJson(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return false;
        }
        try {
            new JsonParser().parse(jsonStr);
        } catch (JsonParseException e) {
            return false;
        }
        return true;
    }

    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return toJSONString(obj, serializeFilter);
    }

    public static String toPrettyJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return toJSONString(obj, serializeFilter, SerializerFeature.PrettyFormat);
    }

//    public static Map<String, Object> objToMap(Object entity) {
//        return toMap(GsonWrapper.toJson(entity));
//    }
//
//    public static List<Map<String, Object>> objToMapList(Object entity) {
//        return toMapList(GsonWrapper.toJson(entity));
//    }
//
//    public static List<Object> objToList(Object entity) {
//        return toObjList(GsonWrapper.toJson(entity));
//    }

    public static List<Object> toList(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }

        JSONArray jsonArr = JSON.parseArray(jsonStr);
        List<Object> list = new ArrayList<Object>();
        for (Iterator iterator = jsonArr.iterator(); iterator.hasNext(); ) {
            list.add(toMap(iterator.next().toString()));
        }
        return list;
    }

    public static List<Map<String, Object>> toMapList(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }

        JSONArray jsonArr = JSON.parseArray(jsonStr);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Iterator<Object> it = jsonArr.iterator();
        while (it.hasNext()) {
            list.add(toMap(it.next().toString()));
        }
        return list;
    }

    public static Map<String, Object> toMap(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }

        // 方法一
        //paramMap = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonStr), HashMap.class);

//        // 方法二
//        Map<String, Object> map = new HashMap<String, Object>();
//        // 最外层解析
//        JSONObject json = JSONObject.fromObject(jsonStr);
//        for (Object k : json.keySet()) {
//            map.put(k.toString(), parserValue(json.get(k)));
//        }
//        return map;
        return (Map<String, Object>) JSON.parse(jsonStr);
    }

//    public static <T> List<T> toList(String jsonString, Class<T> clazz) {
//        List<T> list = new ArrayList<T>();
//        try {
//            if (isGoodJson(jsonString)) {
//                list = JSON.parseArray(jsonString, clazz);
//            }
//        } catch (Exception e) {
//        }
//        return list;
//    }

    public static void main(String[] args) {

        Map<String, Object> map = new HashedMap();
        map.put("NAME", null);
        map.put("ADDRESS", "");
        map.put("test", null);
        map.put("phone", "123");
        map.put("myAge", 123);
        map.put("MyWeight", 23.45F);

        String json = FastJsonWrapper.toPrettyJson(map);
        System.out.println(json);
    }


}
