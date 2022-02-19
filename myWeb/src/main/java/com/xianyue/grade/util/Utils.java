package com.xianyue.grade.util;

import com.xianyue.grade.dao.Grade;
import com.xianyue.grade.dao.GradeDao;
import com.xianyue.grade.dao.GradeDaoImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther xianyue
 * @date 2022/2/12 - 星期六 - 19:49
 **/
public class Utils {
    public static void readTxt(String path) {
        try (FileReader reader = new FileReader(path);
             // 建立一个对象，它把文件内容转成计算机能读懂的语言
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeText() {
        try {
            File writeName = new File("output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write("我会写入文件啦1\r\n"); // \r\n即为换行
                out.write("我会写入文件啦2\r\n"); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description 解析东秦教务处信息系统的成绩信息，并将其写入数据库
     * @param path
     * @throws IOException
     */
    static void readHtml(String path) throws IOException {
        GradeDao dao = new GradeDaoImpl();

        File reader = new File(path);
        Document dom = Jsoup.parse(reader, "UTF-8", "http://example.com/");
        // System.out.println(dom);
        Elements tr = dom.getElementsByTag("tr");
        // System.out.println(tr);
        Map<String, Integer> map = new HashMap<>();
        boolean flag = true;
        for (Element element : tr) {
            Elements children = element.children();
            if (flag) {
                int idx = 0;
                for (Element k : children) {
                    String key = k.text();
                    map.put(key, idx++);
                }
                flag = false;
                System.out.println(map);
                continue;
            }
            List<String> strings = new ArrayList<>();
            for (Element child : children) {
                String s = child.text();

                strings.add("优秀".equals(s) ? "95" :
                        ("合格".equals(s) ? "80" :
                                ("良好".equals(s) ? "80" :
                                        ("".equals(s) || s.isEmpty() ? "0" : s))));
            }
            // System.out.println(strings.get(map.get("课程代码")) + ", " + strings.get(map.get("课程名称")) + ", "
            //         + strings.get(map.get("学分")) + ", " + strings.get(map.get("学年学期")) + ", " + strings.get(map.get("平时成绩")) + ", "
            //         + strings.get(map.get("期末成绩")) + ", " + strings.get(map.get("总评成绩")));

            String classCode = strings.get(map.get("课程代码"));
            String className = strings.get(map.get("课程名称"));
            Float credit = Float.valueOf(strings.get(map.get("学分")));
            String semester = strings.get(map.get("学年学期"));
            Float usualGrades = Float.valueOf(strings.get(map.get("平时成绩")));
            Float finalGrades = Float.valueOf(strings.get(map.get("期末成绩")));
            Float overallGrades = Float.valueOf(strings.get(map.get("总评成绩")));

            // System.out.println(strings);

            Grade grade = new Grade(classCode, className, credit, semester, usualGrades, finalGrades, overallGrades);
            dao.add(grade);
        }
    }
}
