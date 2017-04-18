/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogIn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * 登陆模块
 * @author a8756
 */
public class iChatLogin{
    private iChatUser user;             //当前登陆用户
    private LoginManager loginManager;  //登陆事件源
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
        //String updateOnlineStare = "UPDATE iChat.`user` SET Online_State = 1 WHERE iChat.`user`.`User_Name` = ?";
        
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
                    System.out.println("登陆成功！");
                    String userName = rs.getString(3);  //获取用户名
                    user.setUserName(userName);         //补充完整用户信息
                    
//                    pstmt = (PreparedStatement) conn.prepareStatement(updateOnlineStare);
//                    pstmt.setString(1,user.getUserName());
//                    if (pstmt.execute()) {
//                        System.out.println("在线状态更新成功！");
//                    }

                    //发出登陆事件
                    loginManager.activateLoginEvent();
                }
                else {
                    //密码错误
                    JOptionPane.showMessageDialog(null, "密码错误，请检查您的密码。");
                    System.out.println("密码错误！");
                }
            }
            else {
                //该用户不存在
                JOptionPane.showMessageDialog(null, "此用户不存在，请检查您的用户ID");
                System.out.println("该用户不存在！");
            }
        } catch (SQLException e) {
            //未知错误
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(iChatUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        iChatLogin login = new iChatLogin();
    }
}
