/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import Utils.iChatUser;
import java.util.Vector;

/**
 * 私聊窗口关闭事件管理器
 * 当有私聊窗口关闭时，触发关闭动作
 * @author ZhouHeng
 */
public class ClosePrivateWindowManager {
    private static final Vector<ClosePrivateWindowListener> listeners
            = new Vector<ClosePrivateWindowListener>();        //主窗口监听
    private iChatUser targetUser;

    public void setTargetUser(iChatUser targetUser) {
        this.targetUser = targetUser;
    }
 
    
    /**
     * 添加事件
     * @param listener 需要添加关闭私聊窗口事件监听者
     */
    public void addClosePrivateWindowListener(ClosePrivateWindowListener listener) {
        listeners.add(listener);
    }
    
    /**
     * 移除事件
     * @param listener 需要移除私聊窗口事件监听者
     */
    public void removeClosePrivateWindowListener(ClosePrivateWindowListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * 触发事件
     */
    protected void activateLoginEvent() {
        Vector<ClosePrivateWindowListener> tempListeners = null;
        ClosePrivateWindowEvent e = new ClosePrivateWindowEvent(this,targetUser);
        synchronized(this) {
            tempListeners = (Vector)listeners.clone();
            for (int i=0;i<tempListeners.size();i++) {
                ClosePrivateWindowListener listener = (ClosePrivateWindowListener)tempListeners.elementAt(i);
                listener.closingPrivateWindow(e);
            }
        }
    }
    
    /**
     * 触发事件
     */
    public void notifyLoginListeners() {
        activateLoginEvent();
    }
}
