package jiankunking.kafkajstorm.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jiankunking on 2017/4/20 14:20.
 *
 * @desc properties 资源文件解析工具
 */
public class PropertiesUtil {
    public Properties getProps() {
        return props;
    }

    private Properties props;
    /**
     * 是否是绝对路径
     */
    private boolean absolutePath = false;

    public PropertiesUtil(String fileName, boolean absolutePath) {
        if (absolutePath) {
            readPropertiesByAbsolutePath(fileName);
        } else {
            readProperties(fileName);
        }
    }



    private void readPropertiesByAbsolutePath(String fullFileName) {
        try {
            props = new Properties();
            InputStream in = new BufferedInputStream(new FileInputStream(fullFileName));
            props.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readProperties(String fileName) {
        try {
            props = new Properties();
            InputStream fis = getClass().getResourceAsStream(fileName);
            props.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取某个属性
     */
    public String getProperty(String key) {
        return props.getProperty(key);
    }

    /**
     * 获取所有属性，返回一个map,不常用
     * 可以试试props.putAll(t)
     */
    public Map getAllProperty() {
        Map map = new HashMap();
        Enumeration enu = props.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = props.getProperty(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 在控制台上打印出所有属性，调试时用。
     */
    public void printProperties() {
        props.list(System.out);
    }

    //public static void main(String[] args) {
    //    PropertiesUtil util=new PropertiesUtil("src/dbConfig.properties");
    //    util.writeProperties("dbtype", "MSSQL");
    //}
}
