/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import FileTransfer.FileReceiver;
import FileTransfer.FileTransferProgress;
import GroupChat.iChatGroupChatUI;
import PrivateChat.iChatPrivateChatUI;
import Program.MainUI;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 * 主程序IO模块 管理输入输出流，以及消息的发送和接收 管理私聊窗口和群聊窗口的消息接收
 * @author ZhouHeng
 */
public class iChatIOManager {
    private Socket socket;                              //服务器连接
    private iChatUser user;                             //当前登陆用户
    private MainUI mainUI;                              //主窗口
    private ObjectInputStream objectInputStream;        //对象输入流，接收消息
    private ObjectOutputStream objectOutputStream;      //对象输出流，发送消息
    private Map<iChatUser, iChatPrivateChatUI> privateWindows;       //私聊窗口容器 
    private iChatGroupChatUI groupChatWindow;                       //群聊窗口
    private ClientReceiveThread receiveThread;
    static private Logger logger = Logger.getLogger(iChatIOManager.class);
    
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

    public iChatIOManager(Socket socket, iChatUser user, MainUI mainUI) {
        this.socket = socket;
        this.user = user;
        this.mainUI = mainUI;
        this.privateWindows = new HashMap<iChatUser, iChatPrivateChatUI>();
        
        initIOManager();
    }
    
    /**
     * 初始化后台IO线程
     */
    private void initIOManager() {
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(user);       //发送自己的用户信息
        } catch (IOException ex) {
            logger.error("后台IO流初始化失败！",ex);
        }

        /*开启消息接收线程*/
        receiveThread = new ClientReceiveThread();
        new Thread(receiveThread).start();
    }
    
    /**
     * 程序退出调用此方法 关闭IO流对象，关闭Socket
     */
    public void closeIOManager() {
        receiveThread.stopThread();     //关闭接收线程
        try {
            objectOutputStream.close();
        } catch (IOException ex) {
            logger.error("对象输出流关闭失败！",ex);
        }

        try {
            //关闭Socket
            socket.close();
        } catch (IOException ex) {
            logger.error("Socket关闭失败！",ex);
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
     * @param msg 消息
     */
    public void sendMessage(iChatMessage msg) {
        try {
            objectOutputStream.writeObject(msg);
        } catch (IOException ex) {
            logger.error("消息发送失败！",ex);
        }
    }

    /**
     * 添加私聊窗口到容器
     * @param targetUser 目标用户
     * @param privateChatUI 私聊窗口
     */
    public void putValue(iChatUser targetUser, iChatPrivateChatUI privateChatUI) {
        privateWindows.put(targetUser, privateChatUI);
    }

    /**
     * 将私聊窗口从容器中删除
     * @param user 目标用户
     */
    public void removeValue(iChatUser user) {
        privateWindows.remove(user);
        logger.debug("现在还有 " + privateWindows.size() + " 个窗口");
    }

    /**
     * 获得客户端对象输出流
     * @return 客户端对象输出流
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
                logger.error("关闭对象输入流失败！",ex);
            }
        }
        
        /**
         * 接收来自服务端的消息，并显示输出
         * @throws IOException 
         */
        private void receiveMessage() throws IOException {
            Object obj = null;
            while (isRunning) {
                try {
                    obj = objectInputStream.readObject();
                    logger.info("接收到消息！");
                } catch (ClassNotFoundException ex) {
                    logger.error("读取对象失败！",ex);
                }
                if (obj instanceof iChatFileMessage) {
                    iChatFileMessage msg = (iChatFileMessage) obj;
                    //文件传输消息
                    switch (msg.getMsgType()) {
                        case iChatFileMessage.SEND_FILE_MESSAGE:
                            /**
                             * 发送请求
                             */
                            logger.info("接收到文件发送请求！");
                            handleSendRequest(msg);
                            break;

                        case iChatFileMessage.START_TO_TRANSFER:
                            /**
                             * 开始传输，检查消息的发送者是否是自己，是则代表自己是发送者， 调用发送方法，否则调用接收方法
                             */
                            if (msg.getSource().equals(user)) {
                                //自己是发送者
                                logger.debug("发送文件为" + msg.getSendFile().getAbsolutePath());
                                this.SendFile(msg);
                            } else {
                                //自己是接收者
                                logger.debug("保存文件为：" + msg.getSaveFile().getAbsolutePath());
                                startToSaveFile(msg.getSaveFile());
                            }
                            break;
                        case iChatFileMessage.REJECT_FILE_MESSAGE:
                            /**
                             * 拒绝请求，对方拒绝了文件传输 弹出对话框，显示对方拒绝该请求
                             */
                            JOptionPane.showMessageDialog(null, "对方拒绝了您的文件传输请求。");
                            break;
                    }
                } else {
                    iChatMessage msg = (iChatMessage) obj;
                    if (!msg.isDisconnect()) {
                        if (msg.getMsgType() == iChatMessage.GROUP_MESSAGE) {
                            showGroupMessage(msg);
                        } else if (msg.getMsgType() == iChatMessage.PRIVATE_MESSAGE) {
                            showPrivateMessage(msg);
                        }
                    } else {
                        //接收到服务器的安全断开消息后，关闭程序
                        stopThread();
                        closeIOManager();
                        logger.info("客户端关闭。");
                        System.exit(0);
                    }
                }
            }
        }

        /**
         * 启动文件接收，并显示传输进度 该消息由服务端发送，表示对方接受传输请求，并准备就绪
         * @param file
         */
        private void startToSaveFile(File file) {
            //显示接收对话框
            //receiveDialog.setVisible(true);
            FileTransferProgress saveProgress = new FileTransferProgress(FileTransferProgress.RECEIVE_PROGRESS);
            saveProgress.setVisible(true);
            //创建receiver
            FileReceiver receiver = new FileReceiver(saveProgress, file);
            //创建接收线程
            new Thread(receiver).start();
        }

        /**
         * 启动文件发送，并显示传输进度 该消息由服务端发送，表示对方接受传输请求，并准备就绪
         * @param msg
         */
        private void SendFile(iChatFileMessage msg) {
            //开始传输文件
            if (msg.getSendFile() == null) {
                logger.debug("发送文件为空！");
            }
            if (privateWindows.get(msg.getTarget()) == null) {
                logger.debug("目标窗口为空！目标ID为：" + msg.getTarget().getUserID());
            }
            privateWindows.get(msg.getTarget()).startToSendFile(msg.getSendFile());
        }

        /**
         * 处理发送请求，接收到此消息表示对方发送了一个文件发送请求 弹出对话框，
         * 是否接收文件 接收则弹出文件保存选择对话框
         * @param msg
         */
        private void handleSendRequest(iChatFileMessage msg) {
            iChatUser source = msg.getSource();
            String dialogStr = "用户 " + source.getUserName() + "(" + source.getUserID() + ")" + " 请求向您发送文件，是否接收该文件？";
            int choose = JOptionPane.showConfirmDialog(null, dialogStr, "文件请求", JOptionPane.YES_NO_OPTION);
            if (choose == 0) {
                //确定接收文件,弹出文件选择对话框
                JFileChooser saveFileChosser = new JFileChooser();
                saveFileChosser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                saveFileChosser.showSaveDialog(mainUI);
                File saveFile = saveFileChosser.getSelectedFile();

                //给服务器发送信号，表示接受请求
                sendMessage(new iChatFileMessage(msg.getTarget(), msg.getSource(),
                        iChatFileMessage.ACCEPT_FILE_MESSAGE, msg.getSendFile(), saveFile)); 
            } else {
                //拒绝接收文件
                sendMessage(new iChatFileMessage(source, user, iChatFileMessage.REJECT_FILE_MESSAGE, null));
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
            } else {
                logger.debug(user.getUserName() + "群聊窗口未打开");
            }
        }

        /**
         * 将私聊消息显示到对应的窗口上
         * @param msg
         */
        private void showPrivateMessage(iChatMessage msg) {
            iChatUser targetUser = msg.getSource();
            logger.info(user.getUserName() + ": 解析信息");
            msg.printMessage();
            if (privateWindows.containsKey(targetUser)) {
                //该窗口已经打开，在该窗口显示消息
                privateWindows.get(targetUser).setReceiveAreaMsg(msg);
            } else {
                //对方没打开窗口，则保存到本地消息记录
                logger.info("没打开窗口！");
            }
        }

        @Override
        public void run() {
            try {
                receiveMessage();
            } catch (IOException e) {
                logger.error("接收信息线程启动失败！",e);
            }
        }
    }
}
