/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogIn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * iChat聊天服务器连接
 * 创建一个与聊天服务器的TCP连接
 * @author a8756
 */
public class iChatServerConnection {
    private Socket socket = null;                      //连接服务器Socket
    private BufferedReader in;                         //输入流
    private PrintWriter out;                           //输出流
    private ObjectOutputStream objectOut;              //对象输出流
    private iChatUser user;                            //连接用户

    public iChatServerConnection(iChatUser user) {
        /**
         * 初始化连接和IO流
         */
        this.user = user;
        try {
            socket = new Socket("127.0.0.1",4444);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "无法连接服务器！请检查您的网络连接。", "出错", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(iChatServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            objectOut = new ObjectOutputStream(socket.getOutputStream());   //创建对象输出流
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "创建IO流失败！", "出错", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        //给服务器发送用户ID，通知自己上线
        out.println(user.getUserID());
    }
    
    public void closeConnection() {
        /**
         * 关闭连接
         */
        if (socket != null) {
            out.close();
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(iChatServerConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(iChatServerConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
