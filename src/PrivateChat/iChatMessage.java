/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrivateChat;

import LogIn.iChatUser;
import java.io.Serializable;
import java.util.Date;

/**
 * 私聊消息类
 * 将User信息和信息打包
 * @author a8756
 */
public class iChatMessage implements Serializable{
    private iChatUser source;       //消息发送者
    private iChatUser target;       //消息接收者
    private String msg;             //消息内容
    private int messageType;        //标记消息类型，1=私聊，2=群聊，3=文件传输类消息
    private Date msgSendTime;   //消息发送时间，由服务器决定
    private boolean isDisconnect = false;     //断开连接标志
    
    public static final int GROUP_MESSAGE = 2; //群聊消息
    public static final int PRIVATE_MESSAGE = 1; //私聊消息
    public static final int SEND_FILE_MESSAGE = 3;//发送文件请求
    public static final int ACCEPT_FILE_MESSAGE = 4;//接受文件请求
    public static final int REJECT_FILE_MESSAGE = 5;//拒绝文件请求
    public static final boolean CLOSE_CONNECTION = true;
    
    /**
     * 私聊消息构造函数
     * 消息类别：1
     * @param target
     * @param source
     * @param msg 
     */
    public iChatMessage(iChatUser target, iChatUser source, String msg, int messageType) {
        this.target = target;
        this.source = source;
        this.msg = msg;
        this.messageType = messageType;
        isDisconnect = false;
    }

    /**
     * 群聊消息构造函数
     * 消息类别：2
     * @param source
     * @param msg
     */
    public iChatMessage(iChatUser source, String msg) {
        this.target = null;
        this.source = source;
        this.msg = msg;
        this.messageType = 2;
        isDisconnect = false;
    }
    
    
    /**
     * 关闭连接通知
     * @param isDisconnect 
     */
    public iChatMessage(boolean isDisconnect) {
        this.isDisconnect = isDisconnect;
    }
    
    
    
    public boolean isDisconnect() {
        return isDisconnect;
    }
    
    public void setMsgSendTime(Date msgSendTime) {
        this.msgSendTime = msgSendTime;
    }

    public Date getMsgSendTime() {
        return msgSendTime;
    }
    
    
    public iChatUser getTarget() {
        return target;
    }
    
    public iChatUser getSource() {
        return source;
    }

    public String getMsg() {
        return msg;
    }
    
    public void printMessage() {
        System.out.println("From: " + source.getUserName() + " To " + target.getUserName());
    }

    public int getMsgType() {
        return messageType;
    }
    
}
