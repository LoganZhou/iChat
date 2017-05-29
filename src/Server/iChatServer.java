package Server;

import LogIn.iChatConnection;
import LogIn.iChatUser;
import PrivateChat.iChatMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.apache.logging.log4j.*;


/**
 * 私聊服务端
 * 客户端连接后，向服务端发送私聊请求，服务器判断私聊目标是否连接
 * 如果已连接，则转发信息；如果未连接，返回失败信息
 * Created by a8756 on 2017/4/8.
 */
public class iChatServer {
    private ServerSocket serverSocket;
    private Map<iChatUser, ServerThread> clientsMap = new HashMap<iChatUser, ServerThread>();       //连接线程，每个客户端一个线程
    private Logger serverLogger = Logger.getLogger("iChatServer");

    
    public static void main(String[] args) {
        iChatServer chatServer = new iChatServer();
        chatServer.launchServer();
    }

    public iChatServer() {
        /**
         * 初始化服务端
         */
        try {
            serverSocket = new ServerSocket(4444);
            serverLogger.info("服务端启动成功！");
        } catch (IOException e) {
            serverLogger.log(Level.SEVERE, "服务端启动失败，程序结束！");
            System.exit(1);
        }
    }

    public void launchServer() {
        /**
         * 启动服务端监听
         */
        Socket clientSocket = null;
        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                serverLogger.log(Level.SEVERE, "客户端连接失败，程序退出！");
                System.exit(1);
            }
            serverLogger.info("客户端连接成功！");
            ServerThread sThread = new ServerThread(clientSocket);
            new Thread(sThread).start();    //启动线程
        }
    }

    class ServerThread implements Runnable {
        /**
         * 服务端线程（内部类）
         * @param socket
         */

        private Socket clientSocket;                //当前线程所连接的客户端Socket
        private iChatUser user = null;              //客户端用户
        private boolean isRunning = true;           //线程运行状态标志
        private ObjectInputStream objectInputStream;  //对象输入流
        private ObjectOutputStream objectOutputStream;//对象输出流
        private Logger threadLogger = Logger.getLogger("iChatServer.ServerThread");
        public ServerThread(Socket socket) {
            this.clientSocket = socket;

            try {
                //连接顺序：先客户端发送自己的信息，然后建立OutputStream
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                threadLogger.log(Level.WARNING, "初始化IO流出错！", e);
            }
            try {
                user = (iChatUser) objectInputStream.readObject();      //接收客户端身份信息
                threadLogger.info("用户 " + user.getUserName() + " 已连接！");
                //System.out.println("用户 " + user.getUserName() + " 已连接！");
            } catch (IOException e) {
                threadLogger.log(Level.WARNING, "读取客户端信息出错！", e);
                //e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                threadLogger.log(Level.WARNING, "ClassNotFound！", ex);
                //Logger.getLogger(iChatServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            clientsMap.put(user,this);                                      //将线程添加到Map中
            threadLogger.info("Log: " + clientsMap.size() + " client connections.");
            //System.out.println("Log: " + clientsMap.size() + " client connections.");
            /*更新数据库中在线数据*/
            updateOnlineData(true);
        }
        
        private void updateOnlineData(boolean isOnline) {
            /*更新数据库中在线数据*/
            String updateOnlineStare = "UPDATE iChat.`user` SET Online_State = " + String.valueOf(isOnline) + " WHERE iChat.`user`.`User_Name` = ?";
            PreparedStatement pstmt;
            Connection conn = iChatConnection.getConn();
            try {
                pstmt = (PreparedStatement) conn.prepareStatement(updateOnlineStare);
                pstmt.setString(1,user.getUserName());
                    if (pstmt.execute()) {
                        threadLogger.info("用户在线状态更新成功！");
                        //System.out.println("在线状态更新成功！");
                    }
            } catch (SQLException ex) {
                threadLogger.log(Level.WARNING, "用户在线状态信息更新失败，访问数据库出错！", ex);
                //Logger.getLogger(iChatServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public String getUser() {
            return user.getUserName();
        }
        
        private void receiveMessage() throws IOException {
            /**
             * 服务端接收消息先检查是否存在目标客户端 如果存在，则发送消息
             */
            iChatMessage msg = null;
            iChatUser targetUser = null;
            ServerThread targetClient = null;

            while (isRunning) {
                try {
                    msg = (iChatMessage) objectInputStream.readObject();   //读取消息 
                } catch (ClassNotFoundException ex) {
                    threadLogger.log(Level.WARNING, "读取客户端消息失败！", ex);
                    //Logger.getLogger(iChatServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!msg.isDisconnect()) {
                    msg.setMsgSendTime(new Date());                        //设置消息时间
                    threadLogger.info("接收到来自客户端的消息！");
                    //System.out.println("接收到消息！");
                    if (msg.getMsgType() != iChatMessage.GROUP_MESSAGE) {
                        //该消息为私聊消息或文件传输类消息
                        targetUser = msg.getTarget();
                        targetClient = clientsMap.get(msg.getTarget());
                        threadLogger.info("发送给ID为" + msg.getTarget().getUserID());
                        //System.out.println("发送给ID为" + msg.getTarget().getUserID());

                        if (targetClient != null) {
                            sendMessage(targetClient, msg);  //发送消息到目标客户端
                        } else {
                            threadLogger.info("发送消息目标客户端不在线！");
                            //System.out.println("null pointer!");
                        }
                    } else if (msg.getMsgType() == iChatMessage.GROUP_MESSAGE){
                        //该消息为群聊消息，遍历所有连接，发送消息
                        Iterator iter = clientsMap.entrySet().iterator();
                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            ServerThread val = (ServerThread) entry.getValue();
                            sendMessage(val, msg);
                        }
                    }
                } else {  //关闭连接
                    objectOutputStream.writeObject(new iChatMessage(iChatMessage.CLOSE_CONNECTION));
                    isRunning = false;
                    break;
                }
            }
        }

        private void sendMessage(ServerThread target, iChatMessage msg) {
            try {
                /**
                 * 发送消息到目标客户端
                 */
                //System.out.println("准备发送消息！");
                target.objectOutputStream.writeObject(msg);
            } catch (IOException ex) {
                //System.out.println("Log: 发送消息失败！");
                threadLogger.log(Level.WARNING, "发送消息失败！", ex);
                //Logger.getLogger(iChatServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void killThread() {
            /**
             * 线程终止
             */
            isRunning = false;
            clientsMap.remove(user);
            threadLogger.info("用户 " + user.getUserName() + " 断开连接！");
            threadLogger.info(clientsMap.size() + " client connections.");
            //System.out.println("Log: 用户 " + user.getUserName() + " 断开连接！");
            //System.out.println("Log: " + clientsMap.size() + " client connections.");
            
            /*更新数据库在线用户信息*/
            updateOnlineData(false);
        }

        @Override
        public void run() {
                try {
                    receiveMessage();
                } catch (IOException e) {
                    threadLogger.log(Level.WARNING, "用户 " + user.getUserName() + " 意外中断！", e);
                    //System.out.println("Log: 用户 " + user.getUserName() + " 意外中断！");
                    //e.printStackTrace();
                } finally {
                    killThread();
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            threadLogger.log(Level.WARNING, "Socket关闭失败！", e);
                            //e.printStackTrace();
                        }
                    }
                }
            
        }
    }
}
