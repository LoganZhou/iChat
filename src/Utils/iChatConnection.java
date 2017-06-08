/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * 数据库连接类
 * 返回连接对象
 * @author ZhouHeng
 */
public class iChatConnection {
    static private Logger logger = Logger.getLogger(iChatConnection.class);
    
    /**
     * 创建一个数据库连接
     * @return Connection 连接对象
     */
    public static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://119.29.8.35:3306/iChat?useSSL=false";
        String username = "iChatAdmin";
        String password = "hzauiChat8756";
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            logger.error("JDBC Driver未找到！",e);
        } catch (SQLException e) {
            logger.error("SQL执行错误：连接数据库出错！",e);
        }
        return conn;
    }
}
