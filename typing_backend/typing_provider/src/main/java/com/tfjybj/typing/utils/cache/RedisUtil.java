package com.tfjybj.typing.utils.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author ZENG.XIAO.YAN
 * @date 2018年6月7日
 */

@Component
public final class RedisUtil<T> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired(required = false)
    private ValueOperations<String, String> valueOperations;
    @Autowired(required = false)
    private HashOperations<String, String, Object> hashOperations;
    @Autowired(required = false)
    private ListOperations<String, Object> listOperations;
    @Autowired(required = false)
    private SetOperations<String, Object> setOperations;
    @Autowired(required = false)
    private ZSetOperations<String, Object> zSetOperations;

    // =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return 是否保存成功
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询多个key
     *
     * @param key 键
     * @return 所有key
     */
//    public Set<String> keys(String key) {
//        try {
//            Set<String> keys = redisTemplate.keys(key);
//            return keys;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    // ============================String=============================



    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, String value) {
        try {
            valueOperations.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, String value, long time) {
        try {
            if (time > 0) {
                valueOperations.set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return key == null ? null : (String) valueOperations.get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean setObj(String key, Object value) {
        try {
            valueOperations.set(key, JSON.toJSONString(value));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean setObj(String key, Object value, long time) {
        try {
            if (time > 0) {
                valueOperations.set(key, FastJsonWrapper.toJson(value), time, TimeUnit.SECONDS);
            } else {
                setObj(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public <T> T getObj(String key,Class<T> classz) {
        return key == null ? null : JSON.parseObject(valueOperations.get(key),classz);
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return 值
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return valueOperations.increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几
     * @return 值
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return valueOperations.increment(key, -delta);
    }

    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public String hget(String key, String item) {
        return key == null ? null : (String) hashOperations.get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<String, Object> hmget(String key) {
        return hashOperations.entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            hashOperations.putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Set<String> keys(String pattern) {
        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */

    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            hashOperations.putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, String value) {
        try {
            hashOperations.put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, String value, long time) {
        try {
            hashOperations.put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, String... item) {
        hashOperations.delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return hashOperations.hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return 值
     */
    public long hincr(String key, String item, long by) {
        if (by < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return hashOperations.increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return 值
     */
    public long hdecr(String key, String item, long by) {
        if (by < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return hashOperations.increment(key, item, -by);
    }
    // ============================set=============================


    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, String value) {
        try {
            return setOperations.isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个（Object类型必须一致）
     * @return 成功个数
     */
    public long sSet(String key, Set<T> values) {
        if(values == null || values.size() == 0){
            return 0;
        }
        try {
            Iterator<T> it = values.iterator();
            while (it.hasNext()) {
                T value = it.next();
                setOperations.add(key, JSON.toJSONString(value));
            }
            return values.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return 值
     */
    public Set<T> sGet(String key,Class<T> Classz) {
        try {
            Set<Object> setList = setOperations.members(key);
            Set<T> result = new HashSet<>();
            Iterator<Object> it = setList.iterator();
            while (it.hasNext()) {
                result.add(JSON.parseObject((String)it.next(),Classz));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Set<Object> values) {
        try {
            Long count = setOperations.add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return 值
     */
    public long sGetSetSize(String key) {
        try {
            return setOperations.size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long sRemove(String key, String... values) {
        try {
            Long count = setOperations.remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    //    // ===============================list=================================
//
    /*
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return  返回值是List<String></>
     */
    public List<Object> lRange(String key, long start, long end) {
        try {
            return listOperations.range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return listOperations.size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return listOperations.index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 用values覆盖之前的List
     *
     * @param key   键
     * @param values 值
     * @return
     */
    public boolean lSet(String key, List<T> values) {
        try {
            redisTemplate.delete(key);
            for(int i = 0;i<values.size();i++){
                listOperations.rightPush(key, JSON.toJSONString(values.get(i)));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * 将list右边取出第一个元素
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public <T> T lRightPop(String key, Class<T> classz) {
        try {
            return JSON.parseObject((String)listOperations.rightPop(key),classz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 将list左边取出第一个元素
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public <T> T lLeftPop(String key, Class<T> classz) {
        try {
            return JSON.parseObject((String)listOperations.leftPop(key),classz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 向队列右侧放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lRightSet(String key, String value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向队列右侧放入缓存
     *
     * @param key   键
     * @param values 值
     * @return
     */
    public boolean lRightSet(String key,List<T> values) {
        try {
            for(int i = 0;i<values.size();i++){
                listOperations.rightPush(key, JSON.toJSONString(values.get(i)));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * 将list左边取出第一个元素
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public <T> List<T> lGet(String key, Class<T> classz) {
        try {
            List<T> result = Lists.newArrayList();
            long leng = listOperations.size(key);
            List<Object> objects = listOperations.range(key,0,leng - 1);
            for(int i = 0;i<leng;i++){
                result.add(JSON.parseObject((String)objects.get(i),classz));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return 是否update成功
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            listOperations.set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = listOperations.remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
