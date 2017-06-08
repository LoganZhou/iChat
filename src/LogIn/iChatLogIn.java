/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogIn;

import Utils.iChatUser;
import Utils.iChatConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 * 登陆模块
 * @author ZhouHeng
 */
public class iChatLogin{
    private iChatUser user;             //当前登陆用户
    private LoginManager loginManager;  //登陆事件源
    static private Logger logger = Logger.getLogger(iChatLogin.class);
    
    public iChatLogin() {
        loginManager = new LoginManager();
    }
    
    public void addLoginListener(LoginListener listener) {
        loginManager.addLoginListener(listener);
    }
    
    public void setUser(iChatUser user) {
        /**
         * 由登陆模块设置的User
         * 只包含UserID和Password
         */
        this.user = user;
    }

    public void login() {
        /**
         * 登陆
         * 查询服务器用户信息
         */
        
        Connection conn = iChatConnection.getConn();
        String checkUserName = "SELECT 1 FROM iChat.`user` WHERE iChat.`user`.`User_ID` = ? LIMIT 1";
        String checkPassword = "SELECT * FROM iChat.`user` WHERE iChat.`user`.`User_ID` = ? AND iChat.`user`.`Password` = ?";
        
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(checkUserName);
            pstmt.setString(1,String.valueOf(user.getUserID()));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                /*如果非空，则存在该用户*/
                pstmt = (PreparedStatement) conn.prepareStatement(checkPassword);
                pstmt.setString(1,String.valueOf(user.getUserID()));
                pstmt.setString(2,user.getPassword());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    //登陆成功，更新在线用户数据库
                    logger.info("登录成功！");
                    String userName = rs.getString(3);  //获取用户名
                    user.setUserName(userName);         //补充完整用户信息
                    
                    //发出登陆事件
                    loginManager.activateLoginEvent();
                }
                else {
                    //密码错误
                    JOptionPane.showMessageDialog(null, "密码错误，请检查您的密码。");
                    logger.info("密码错误！");
                }
            }
            else {
                //该用户不存在
                JOptionPane.showMessageDialog(null, "此用户不存在，请检查您的用户ID");
                logger.info("该用户不存在！");
            }
        } catch (SQLException e) {
            //未知错误
            logger.error("SQL执行错误！",e);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                logger.error("关闭数据库连接出错！",ex);
            }
        }
    }
}
