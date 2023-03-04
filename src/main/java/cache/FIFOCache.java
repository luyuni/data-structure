package cache;

import java.util.HashMap;
import java.util.LinkedList;

public class FIFOCache<K, V> {
    // 定义缓存的最大容量
    private int maxSize;
    // 定义当前缓存的容量
    private int curSize;
    // 用于存放缓存的key
    private LinkedList<K> cacheKey;
    // 用于存放缓存的value
    private HashMap<K, V> cacheValue;

    // 构造函数
    public FIFOCache(int maxSize) {
        this.maxSize = maxSize;
        this.curSize = 0;
        this.cacheKey = new LinkedList<K>();
        this.cacheValue = new HashMap<K, V>();
    }

    // 向缓存插入key-value
    public void put(K key, V value) {

        // 如果缓存已满，则删除最老的key
        if (curSize == maxSize) {
            K oldKey = cacheKey.removeFirst();
            cacheValue.remove(oldKey);
            curSize--;
        }
        // 插入key-value
        cacheKey.addLast(key);
        cacheValue.put(key, value);
        curSize++;

    }

    // 查询指定key的value
    public V get(K key) {
        return cacheValue.get(key);
    }

    public void printKeys() {
        System.out.println(this.cacheKey.toString());
    }

    public static void main(String[] args) {
        FIFOCache cache = new FIFOCache<String, String>(5);
        cache.put("A", "1");

        cache.put("B", "2");
        cache.put("C", "3");
        cache.put("D", "4");
        cache.put("E", "5");
        cache.printKeys();
        cache.put("F", "6");
        cache.printKeys();

        Object cacheObj1 = cache.get("G");
        System.out.println("cacheObj1=" + cacheObj1);

        Object cacheObj2 = cache.get("C");
        System.out.println("cacheObj2=" + cacheObj2);
    }


}
