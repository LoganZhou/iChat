/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import LogIn.LoginEvent;
import LogIn.LoginListener;
import LogIn.LoginUI;
import Utils.iChatConnection;
import Utils.iChatUser;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import org.apache.log4j.Logger;

/**
 * 聊天准备主程序，也是客户端启动入口
 * 负责登陆验证等操作，登陆成功后由显示主界面窗口
 * @author ZhouHeng
 */
public class iChatKernel implements LoginListener{
    private Socket socket;                  //与服务器的连接
    private HashSet<iChatUser> onlineUsers; //在线用户列表
    private MainUI mainWindow;              //主界面窗口
    private LoginUI loginWindow;            //登陆窗口
    private iChatUser user;                 //当前用户
    static private Logger logger = Logger.getLogger(iChatKernel.class);
    
    public iChatKernel() {
        loginWindow = new LoginUI();
        loginWindow.setVisible(true);
        loginWindow.addLoginListener(this);
        onlineUsers = new HashSet<iChatUser>();
    }
    
    private void initSocket() {
        /**
         * 初始化连接
         */
        try {
            if (this.socket == null) {
                socket = new Socket("127.0.0.1",8756);
            }
        } catch (IOException e) {
            logger.error("连接聊天服务器失败！",e);
        }
        if (socket == null) {
            logger.debug("连接聊天服务器Socket为空！");
        }
        
    }
    
    public static void main(String[] args) {
        iChatKernel kernel = new iChatKernel();   
    }

    @Override
    public void loginEvent(LoginEvent event) {
        /**
         * 登陆事件监听接口实现
         * 调用登陆方法
         */
        this.user = loginWindow.getUser();
        this.loginWindow.dispose();
        initSocket();
        this.mainWindow = new MainUI(socket,user,onlineUsers);
        mainWindow.setVisible(true);
        updateListThread checkOnlineThread = new updateListThread();
        new Thread(checkOnlineThread).start();
    }
    
    /**
     * 更新在线用户线程
     */
    private class updateListThread implements Runnable {
        private String check = "SELECT iChat.`user`.`User_ID` , iChat.`user`.`User_Name` FROM iChat.`user` WHERE iChat.`user`.`Online_State` = 1";
        private Connection connection;
        private PreparedStatement pstmt;
        private ResultSet result;
        private boolean isRunning = true;
        /**
         * 初始化更新连接
         */
        public updateListThread() {
            connection = iChatConnection.getConn();
            onlineUsers = new HashSet<iChatUser>();
        }
        
        public void stopThread() {
            isRunning = false;
        }
        @Override
        public void run() {
            while (isRunning) {
                try {
                    pstmt = connection.prepareStatement(check);
                    result = pstmt.executeQuery();
                    HashSet<iChatUser> latest = new HashSet<iChatUser>();
                    while (result.next()) {
                        //用户名，ID
                        iChatUser user = new iChatUser(result.getString(2),result.getLong(1));
                        latest.add(user);
                    }
                    onlineUsers = latest;
                    mainWindow.updateOnlineList(onlineUsers);
                } catch (SQLException ex) {
                    logger.error("SQL执行出错！",ex);                    
                }
                try {
                    //每10s刷新一次
                    Thread.sleep(1000*10);
                } catch (InterruptedException ex) {
                    logger.error("更新在线用户数据线程休眠出错！",ex);
                }
            }
        }
    }
}
