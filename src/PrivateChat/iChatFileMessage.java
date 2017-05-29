/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrivateChat;

import LogIn.iChatUser;
import java.io.File;
import java.net.InetAddress;

/**
 * 文件传输类消息
 * 消息类别：
 *      3：发送请求(msg=null)
 *      4：接受请求(msg=ip地址)
 *      5：拒绝请求(msg=null)
 * @author a8756
 */
public class iChatFileMessage extends iChatMessage{
    private File file = null;
    InetAddress address = null;
    public iChatFileMessage(iChatUser target, iChatUser source, int messageType, File file, InetAddress address) {
        super(target,source,null,messageType);
        this.file = file;
        this.address = address;
    }
    public iChatFileMessage(iChatUser target, iChatUser source, int messageType, File file) {
        super(target,source,null,messageType);
        this.file = file;
        this.address = null;
    }
    
    public File getFile() {
        return file;
    }
    
}
