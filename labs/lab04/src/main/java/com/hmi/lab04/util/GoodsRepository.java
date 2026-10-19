package com.hmi.lab04.util;

import com.hmi.lab04.model.Goods;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 商品数据源（用静态集合模拟数据库）。
 */
public final class GoodsRepository {

    private static final Map<String, Goods> GOODS = new LinkedHashMap<>();

    static {
        GOODS.put("1001", new Goods("1001", "华为 Mate 60 手机", 6999.00));
        GOODS.put("1002", new Goods("1002", "小米平板 6", 2199.00));
        GOODS.put("1003", new Goods("1003", "联想拯救者笔记本", 8299.00));
        GOODS.put("1004", new Goods("1004", "索尼降噪耳机", 1499.00));
        GOODS.put("1005", new Goods("1005", "罗技无线鼠标", 129.00));
    }

    private GoodsRepository() {
    }

    /** 返回全部商品（保持插入顺序）。 */
    public static Collection<Goods> findAll() {
        return GOODS.values();
    }

    /** 根据商品编号查询商品，查不到返回 null。 */
    public static Goods findById(String id) {
        return GOODS.get(id);
    }
}
