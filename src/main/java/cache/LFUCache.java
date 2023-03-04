package cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LFUCache {

    //定义缓存容量
    private  int capacity ;

    //存储key value
    private Map<String,String> cache ;

    //存储key的使用频次
    private Map<String, CacheObj> count;

    public LFUCache(int capacity){
        this.capacity = capacity;
        cache =  new HashMap<>();
        count =  new HashMap<>();
    }


    //存储
    public void put(String key, String value) {
        String cacheValue = cache.get(key);
        if (cacheValue == null) {
            //新元素插入，需要判断是否超过缓存容量大小
            if (cache.size() == capacity) {
                removeElement();
            }
            count.put(key, new CacheObj(key, 1, System.currentTimeMillis()));
        } else {
            addCount(key);
        }
        cache.put(key, value);
    }


    //读取
    public String get(String key) {
        String value = cache.get(key);
        if (value != null) {
            addCount(key);
            return value;
        }
        return null;
    }
    //删除元素
    private void removeElement() {
        CacheObj cacheObj  = Collections.min(count.values());
        cache.remove(cacheObj.key);
        count.remove(cacheObj.key);
    }

    //更新相关统计频次和时间
    private void addCount(String key) {
        CacheObj cacheObj = count.get(key);
        cacheObj.count = cacheObj.count + 1;
        cacheObj.lastTime = System.currentTimeMillis();
    }


    public void showInfo(){
        System.out.println(cache.toString());
        System.out.println(count.toString());
    }


    class CacheObj implements Comparable<CacheObj>{
        String key;
        int count;
        long lastTime;


        public CacheObj(String key, int count, long lastTime) {
            this.key = key;
            this.count = count;
            this.lastTime = lastTime;
        }

        //用于比较大小，如果使用次数一样，则比较时间大小
        @Override
        public int compareTo(CacheObj o) {
            int value = Integer.compare(this.count, o.count);
            return value == 0 ? Long.compare(this.lastTime, o.lastTime) : value;
        }

        @Override
        public String toString() {
            return "CacheObj{" +
                    "key=" + key +
                    ", count=" + count +
                    ", lastTime=" + lastTime +
                    '}';
        }
    }
}