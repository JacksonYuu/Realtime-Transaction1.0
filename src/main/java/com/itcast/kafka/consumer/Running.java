package com.itcast.kafka.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 消费者执行方法
 */
public class Running {

    public static String getMessage(String index) throws ExecutionException, InterruptedException {

        Map<Integer, Thread> threadMap = new HashMap<>();

        String[] s = {"江西省","北京市","天津市","上海市","重庆市","河北省","河南省","湖北省","湖南省","江苏省","辽宁省","吉林省","黑龙江省","陕西省","山西省","山东省","四川省","青海省","安徽省","海南省","广东省","贵州省","浙江省","福建省","台湾省","甘肃省","云南省","西藏自治区","宁夏回族自治区","广西壮族自治区","新疆维吾尔自治区","内蒙古自治区"};

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<List<String>> result = executorService.submit(new Configuration(index));

        if (result.isDone()) {

            executorService.shutdown();
        }

        Map<String, Integer> map = new HashMap<>();

        List<String> allMessage = result.get();

        String position = s[Integer.parseInt(index) - 1];

        List<String> remove = new ArrayList<>();

        remove.add(position);

        allMessage.removeAll(remove);

        int size = allMessage.size();

        if (size > 0) {

            for (int i = 0; i < size; i++) {

                countMessage(map, allMessage.get(i));
            }
        }

        return shuffixMessage(map, position);
    }

    /**
     * 计算每个省份的数量
     * @param map 保存键：省份，值：数量
     * @param message 省份
     * @return 计算之后的map
     */
    public static Map<String, Integer> countMessage(Map<String, Integer> map, String message) {

        Integer number = 1;

        if (map.size() > 1) {

            for (Map.Entry<String, Integer> m : map.entrySet()) {

                if (m.getKey().equals(message)) {

                    number = m.getValue() + 1;
                }
            }
        }

        map.put(message, number);

        return map;
    }

    /**
     * 将信息进行处理
     * @param map 保存键：省份，值：数量
     * @return 处理之后的信息
     */
    public static String shuffixMessage(Map<String, Integer> map, String s) {

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("[");

        for (Map.Entry<String, Integer> m : map.entrySet()) {

            stringBuffer.append("[{name:\'");

            stringBuffer.append(m.getKey() + "\',value:" + m.getValue() + "},");

            stringBuffer.append("{name:\'" + s + "\'}],");
        }

        if (stringBuffer.length() > 0) {

            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }

        stringBuffer.append("]");

        return stringBuffer.toString();
    }
}
