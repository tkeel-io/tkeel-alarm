package com.alarm.tkeel.db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/07/15:22
 */

@Component
public class InitDB {

    @Value(value = "${spring.datasource.url}")
    private String url;
    @Value(value = "${spring.datasource.username}")
    private String username;
    @Value(value = "${spring.datasource.password}")
    private String password;

    @PostConstruct
    public void init() throws SQLException {
            url = url.replace("tkeel-alarm","sys");
        // 创建ScriptRunner，用于执行SQL脚本，如果想要初始化后创建数据库，那么一开始在此处初始化的时候需要连接一个已经存在的数据。
        try(Connection conn = DriverManager.getConnection(url, username, password);){
            SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
            Date date = new Date();// 获取当前时间
            // 项目启动初始化基本数据
            System.out.println("数据初始化开始:"+ sdf.format(date));
            conn.prepareStatement("create database  if not exists `tkeel-alarm` Character Set UTF8;");
            ScriptRunner runner = new ScriptRunner(conn);
            // 设置不自动提交
            runner.setAutoCommit(true);
            /*
             * setStopOnError参数作用：遇见错误是否停止；
             * （1）false，遇见错误不会停止，会继续执行，会打印异常信息，并不会抛出异常，当前方法无法捕捉异常无法进行回滚操作，
             * 无法保证在一个事务内执行； （2）true，遇见错误会停止执行，打印并抛出异常，捕捉异常，并进行回滚，保证在一个事务内执行；
             */
            runner.setStopOnError(true);
            /*
             * 按照那种方式执行 方式一：true则获取整个脚本并执行； 方式二：false则按照自定义的分隔符每行执行；
             */
            runner.setSendFullScript(false);
            // 设置是否输出日志，null不输出日志，不设置自动将日志输出到控制台
            runner.setLogWriter(null);
// 定义命令间的分隔符
            runner.setDelimiter(";");
            runner.setFullLineDelimiter(false);
            // 执行SQL脚本
            runner.runScript(Resources.getResourceAsReader("sql/InitDB.sql")); //这里如果找不到文件，加载一个classloader参数
            Date date2 = new Date();// 获取当前时间
            System.out.println("数据初始化结束:"+ sdf.format(date2));
        }catch (Exception e){
            System.out.println("init sql failed!"+ e.getMessage());
        }
    }

}
