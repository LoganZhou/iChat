/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogIn;

import java.util.Vector;

/**
 * 登陆事件源管理器
 * 相当于一个遥控器，管理监听者
 * @author ZhouHeng
 */
public class LoginManager {
    private Vector<LoginListener> listeners;
    
    /**
     * 添加事件监听
     * @param listener 监听者
     */
    public void addLoginListener(LoginListener listener) {
        if (listeners == null) {
            listeners = (Vector<LoginListener>)new Vector();
        }
        listeners.add(listener);
    }
    
    /**
     * 移除事件监听
     * @param listener 监听者
     */
    public void removeLoginListener(LoginListener listener) {
        if (listeners == null) {
            return;
        }
        listeners.remove(listener);
    }
    
    protected void activateLoginEvent() {
        Vector<LoginListener> tempListeners = null;
        LoginEvent e = new LoginEvent(this);
        synchronized(this) {
            tempListeners = (Vector)listeners.clone();
            for (int i=0;i<tempListeners.size();i++) {
                LoginListener listener = (LoginListener)tempListeners.elementAt(i);
                listener.loginEvent(e);
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
