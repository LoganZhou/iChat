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
    private boolean isGroupChatMsg = false; //标记消息是否来自群聊
    private Date msgSendTime;   //消息发送时间，由服务器决定
    private boolean isDisconnect = false;     //断开连接标志
    
    public static final boolean GROUP_MESSAGE = true; //群聊消息
    public static final boolean PRIVATE_MESSAGE = false; //私聊消息
    public static final boolean CLOSE_CONNECTION = true;
    
    /**
     * 私聊消息构造函数
     * @param target
     * @param source
     * @param msg 
     */
    public iChatMessage(iChatUser target, iChatUser source, String msg) {
        this.target = target;
        this.source = source;
        this.msg = msg;
        this.isGroupChatMsg = false;
        isDisconnect = false;
    }

    /**
     * 群聊消息构造函数
     * @param source
     * @param msg
     * @param isGroupChatMsg 
     */
    public iChatMessage(iChatUser source, String msg, boolean isGroupChatMsg) {
        this.target = null;
        this.source = source;
        this.msg = msg;
        this.isGroupChatMsg = isGroupChatMsg;
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

    public boolean getMsgType() {
        return isGroupChatMsg;
    }
    
}
