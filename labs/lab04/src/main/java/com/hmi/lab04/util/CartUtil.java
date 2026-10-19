package com.hmi.lab04.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车 Cookie 的编解码工具。
 * <p>Cookie 只能保存字符串，多个商品用英文逗号拼接，商品编号与数量用英文冒号分隔，
 * 形如：{@code 1001:2,1002:1}。为避免中文/特殊字符问题，整体再做一次 URL 编码。</p>
 */
public final class CartUtil {

    /** 购物车 Cookie 的名称。 */
    public static final String CART_COOKIE = "cart";

    /** Cookie 有效期：7 天（秒）。 */
    public static final int CART_MAX_AGE = 7 * 24 * 60 * 60;

    private CartUtil() {
    }

    /**
     * 把 Cookie 中的字符串解析成「商品编号 -> 数量」的有序 Map。
     */
    public static Map<String, Integer> parse(String cookieValue) {
        Map<String, Integer> cart = new LinkedHashMap<>();
        if (cookieValue == null || cookieValue.isEmpty()) {
            return cart;
        }
        String decoded = URLDecoder.decode(cookieValue, StandardCharsets.UTF_8);
        for (String pair : decoded.split(",")) {
            String[] kv = pair.split(":");
            if (kv.length == 2) {
                try {
                    cart.put(kv[0], Integer.parseInt(kv[1]));
                } catch (NumberFormatException ignored) {
                    // 忽略被篡改的非法数据
                }
            }
        }
        return cart;
    }

    /**
     * 把「商品编号 -> 数量」的有序 Map 序列化为可写入 Cookie 的字符串。
     */
    public static String serialize(Map<String, Integer> cart) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(entry.getKey()).append(':').append(entry.getValue());
        }
        return URLEncoder.encode(sb.toString(), StandardCharsets.UTF_8);
    }
}
