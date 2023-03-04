package cache;


import java.util.ArrayList;
import java.util.HashMap;

public class LRUCache {
    //用于存储key-value数据
    private HashMap<String, String> map;
    //用于存储key的顺序
    private ArrayList<String> list;
    //数组的容量
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        list = new ArrayList<>();
    }

    /**
     * 查询key对应的value
     * @param key 键
     * @return value 值
     */
    public String get(String key) {
        //如果key存在，则将key移动到最前端
        if (map.containsKey(key)) {
            list.remove(key);
            list.add(0, key);
            return map.get(key);
        }
        return null;
    }

    /**
     * 向缓存中插入key-value
     * @param key 键
     * @param value 值
     */
    public void put(String key, String value) {
        //如果key存在，则将key移动到最前端
        if (map.containsKey(key)) {
            list.remove(key);
            list.add(0, key);
            map.put(key, value);
        } else {
            //如果key不存在，则添加key-value
            if (list.size() >= capacity) {
                //如果容量已满，则删除最后一个key
                String lastKey = list.get(list.size() - 1);
                list.remove(lastKey);
                map.remove(lastKey);
            }
            list.add(0, key);
            map.put(key, value);
        }
    }
    public void showList(){
        System.out.println(list.toString());
    }
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(5);
        cache.put("A", "学习springboot,在小滴课堂");

        cache.put("B", "架构大课是最强面试大课");
        cache.put("C", "Redis分布式缓存最主流");
        cache.put("D", "海量数据项目大课是营销短链平台项目");
        cache.put("E", "ElasticSearch是搜索框架");

        cache.showList();
        Object cacheObj2 = cache.get("C");
        System.out.println("cacheObj2=" + cacheObj2);
        //C被访问，被放置头部
        cache.showList();

        cache.put("F", "Flink实时计算");

        //新增了F，超过大小，A由于在尾部，被删除，F被放置头部
        cache.showList();

        //G节点不存在，所以不影响顺序
        Object cacheObj1 = cache.get("G");
        System.out.println("cacheObj1=" + cacheObj1);
        cache.showList();
    }
}
