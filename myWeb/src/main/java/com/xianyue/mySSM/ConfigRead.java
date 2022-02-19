package com.xianyue.mySSM;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @auther xianyue
 * @date 2022/2/17 - 星期四 - 13:53
 **/
public class ConfigRead {
    public static String configPath = "config.properties";
    public static Properties properties = new Properties();

    public static final int PageCnt;

    public static final Map<String, String> MySqlConfig = new HashMap<>();

    static {
        loadConfig(configPath);
        configMysql();
        PageCnt = Integer.parseInt(properties.getProperty("pageCnt"));
    }

    static void configMysql() {
        MySqlConfig.put("driverClass", properties.getProperty("driverClass"));
        MySqlConfig.put("url", properties.getProperty("url"));
        MySqlConfig.put("user", properties.getProperty("user"));
        MySqlConfig.put("pwd", properties.getProperty("password"));
    }

    static void loadConfig(String configPath) {
        // FIXME: 2022/2/14
        // 使用 classLoader 进行资源加载时，path 与常规文件路径不太一样
        InputStream inputStream = ConfigRead
                .class
                .getClassLoader()
                .getResourceAsStream(configPath);
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
            System.out.println("加载配置文件失败！");
        }
    }
}
