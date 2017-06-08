/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.File;

/**
 * 文件传输类消息
 * 消息类别：
 *      3：发送请求
 *      4：接受请求
 *      5：拒绝请求
 * @author ZhouHeng
 */
public class iChatFileMessage extends iChatMessage{
    private File sendFile = null;               //发送文件绝对路径
    private File saveFile = null;               //接收文件绝对路径
    public static final int SEND_FILE_MESSAGE = 3;//发送文件请求
    public static final int ACCEPT_FILE_MESSAGE = 4;//接受文件请求
    public static final int REJECT_FILE_MESSAGE = 5;//拒绝文件请求
    public static final int START_TO_TRANSFER = 6; //开始传送信号
    
    /**
     * 发送文件请求
     * @param target 消息接收方
     * @param source 消息发送方
     * @param messageType 消息类别
     * @param sendFile 发送方发送的文件绝对路径
     */
    public iChatFileMessage(iChatUser target, iChatUser source, int messageType, File sendFile) {
        super(target,source,null,messageType);
        this.sendFile = sendFile;
    }
   
    /**
     * 文件接收方发送的同意消息，其中的msg为发送方保存的文件绝对路径，用于返回时保存文件
     * @param target 消息接收方
     * @param source 消息发送方
     * @param messageType 消息类别
     * @param sendFile 发送方发送的文件绝对路径
     * @param saveFile 接收方保存的文件绝对路径
     */
    public iChatFileMessage(iChatUser target, iChatUser source, int messageType, File sendFile, File saveFile) {
        super(target,source,null,messageType);
        this.sendFile = sendFile;
        this.saveFile = saveFile;
    }

    public File getSendFile() {
        return sendFile;
    }

    public File getSaveFile() {
        return saveFile;
    }
}
