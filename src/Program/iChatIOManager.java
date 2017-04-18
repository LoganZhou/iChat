/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import GroupChat.iChatGroupChatUI;
import LogIn.iChatUser;
import PrivateChat.iChatMessage;
import PrivateChat.iChatPrivateChatUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 主程序模块
 * 管理输入输出流，以及消息的发送和接收
 * 管理私聊窗口和群聊窗口的消息接收
 * @author a8756
 */
public class iChatIOManager {
    private Socket socket;                              //服务器连接
    private iChatUser user;                             //当前登陆用户
    private ObjectInputStream objectInputStream;        //对象输入流，接收消息
    private ObjectOutputStream objectOutputStream;      //对象输出流，发送消息
    private Map<iChatUser,iChatPrivateChatUI> privateWindows;       //私聊窗口容器 
    private iChatGroupChatUI groupChatWindow;                       //群聊窗口
    private ClientReceiveThread receiveThread;
    public iChatUser getUser() {
        return user;
    }

    public void createGroupChatWindow(HashSet<iChatUser> onlineListSet) {
        if (groupChatWindow == null) {
            groupChatWindow = new iChatGroupChatUI(user, this, onlineListSet);
        }
        groupChatWindow.setVisible(true);
    }
    
    public void updateGroupChatOnlineList(HashSet<iChatUser> newList) {
        if (groupChatWindow != null) {
            groupChatWindow.updateOnlineList(newList);
        }
    }
    
    public iChatIOManager(Socket socket, iChatUser user) {
        this.socket = socket;
        this.user = user;
        this.privateWindows = new HashMap<iChatUser,iChatPrivateChatUI>();
        
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(user);       //发送自己的用户信息
        } catch (IOException ex) {
            Logger.getLogger(iChatIOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*开启消息接收线程*/
        receiveThread = new ClientReceiveThread();
        new Thread(receiveThread).start();
    }
    
    /**
     * 程序退出调用此方法
     * 关闭IO流对象，关闭Socket
     */
    public void closeIOManager() {
        receiveThread.stopThread();     //关闭接收线程
        try {
            objectOutputStream.close();
        } catch (IOException ex) {
            System.out.println("对象输出流关闭失败！");
            Logger.getLogger(iChatIOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            //关闭Socket
            socket.close();
        } catch (IOException ex) {
            System.out.println("Socket关闭失败！");
            Logger.getLogger(iChatIOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //关闭所有窗口资源
        if (groupChatWindow != null) {
            groupChatWindow.dispose();
        }
        Iterator iter = privateWindows.keySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            iChatPrivateChatUI window = (iChatPrivateChatUI) entry.getValue();
            window.dispose();
        }
    }
    
    /**
     * 发送消息
     * @param msg 
     */
    public void sendMessage(iChatMessage msg) {
        try {
            objectOutputStream.writeObject(msg);
        } catch (IOException ex) {
            System.out.println("消息发送失败！");
            ex.printStackTrace();
        }
    }
    
    /**
     * 添加私聊窗口到容器
     * @param user
     * @param privateChatUI 
     */
    public void putValue(iChatUser targetUser, iChatPrivateChatUI privateChatUI) {
        privateWindows.put(targetUser, privateChatUI);           
    }
    
    /**
     * 将私聊窗口从容器中删除
     * @param user 
     */
    public void removeValue(iChatUser user) {
        privateWindows.remove(user);
        System.out.println("现在还有 " + privateWindows.size() + " 个窗口");
    }
    
    /**
     * 返回客户端对象输出流
     * @return 
     */
    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }
    
    /**
     * 消息接收线程（内部类）
     */
    private class ClientReceiveThread implements Runnable {
        private boolean isRunning = true;
        
        /**
         * 结束消息接收，并关闭对象输入流
         */
        private void stopThread() {
            isRunning = false;
            try {
                objectInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        private void receiveMessage() throws IOException {
            /**
             * 接收来自服务端的消息，并显示输出
             */
            iChatMessage msg = null;
            while (isRunning) {
                try {
                    msg = (iChatMessage)objectInputStream.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!msg.isDisconnect()) {
                    System.out.println(user.getUserName() + ": 接收到消息！");
                    if (msg.getMsgType() == iChatMessage.GROUP_MESSAGE) {
                        showGroupMessage(msg);
                    } else {
                        showPrivateMessage(msg);
                    }
                } else {
                    //接收到服务器的安全断开消息后，关闭程序
                    stopThread();
                    closeIOManager();
                    System.exit(0);
                }
            }
        }
        
        /**
         * 将群聊消息显示到群聊窗口
         * @param msg 
         */
        private void showGroupMessage(iChatMessage msg) {
            if (groupChatWindow != null && groupChatWindow.isVisible()) {
                //如果窗口已经打开
                groupChatWindow.showMessage(msg);
            }
            else {
                System.out.println(user.getUserName() + ": 群聊窗口未打开");
            }
        }
        /**
         * 将私聊消息显示到对应的窗口上
         * @param msg 
         */
        private void showPrivateMessage(iChatMessage msg) {
            iChatUser targetUser = msg.getSource();
            System.out.println(user.getUserName() + ": 解析信息");
            msg.printMessage();
            if (privateWindows.containsKey(targetUser)) {
                //该窗口已经打开，在该窗口显示消息
                privateWindows.get(targetUser).setReceiveAreaMsg(msg);
            }
            else {
                //对方没打开窗口，则保存到本地消息记录
                System.out.println("没打开窗口！");
                
            }
        }
        @Override
        public void run() {
            try {
                receiveMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
